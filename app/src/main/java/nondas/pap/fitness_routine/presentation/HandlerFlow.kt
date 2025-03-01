package nondas.pap.fitness_routine.presentation



import nondas.pap.fitness_routine.domain.SuspendUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*


fun <T : Any, P> Flow<Handler.Event<P>>.flatMapMergeWith(
    useCase: SuspendUseCase<T, P>,
    initialParams: P?,
    value: Handler.State<T>? = null,
): Flow<Handler.State<T>> =
    flatMapMergeWith(
        useCase = useCase,
        initialEvent = initialParams?.let { Handler.Event.Execute(it) },
        value = value
    )

fun <T : Any, P> Flow<Handler.Event<P>>.flatMapMergeWith(
    useCase: SuspendUseCase<T, P>,
    initialEvent: Handler.Event<P>? = null,
    value: Handler.State<T>? = null,
): Flow<Handler.State<T>> =
    flatMapMergeWith(initialEvent = initialEvent) { params -> useCase.execute(params).getOrThrow() }
        .onStart { value?.let { emit(it) } }

@OptIn(FlowPreview::class)
fun <T, P> Flow<Handler.Event<P>>.flatMapMergeWith(
    initialEvent: Handler.Event<P>? = null,
    block: suspend (P) -> T,
): Flow<Handler.State<T>> =
    this.onStart { initialEvent?.let { event -> emit(event) } }
        .flatMapMerge { event ->
            when (event) {
                is Handler.Event.Execute -> asHandlerFlow { block(event.params) }
                is Handler.Event.Clear -> flowOf(Handler.State.Idle)
            }
        }

fun <T> asHandlerFlow(block: suspend () -> T) = flow {
    emit(Handler.State.Running)
    try {
        emit(Handler.State.Success(block()))
    } catch (t: Throwable) {
        emit(Handler.State.Error(t))
    }
}

sealed interface Handler {
    sealed interface Event<in P> {
        data class Execute<P>(val params: P) : Event<P>
        object Clear : Event<Any>
    }

    sealed interface State<out T> {
        object Idle : State<Nothing>
        object Running : State<Nothing>
        data class Success<out T>(val result: T) : State<T>
        data class Error(val error: Throwable) : State<Nothing>
    }
}

fun <T> Handler.State<T>.toResult(): Result<T>? = when (this) {
    is Handler.State.Success -> Result.success(this.result)
    is Handler.State.Error -> Result.failure(this.error)
    Handler.State.Idle, Handler.State.Running -> null
}