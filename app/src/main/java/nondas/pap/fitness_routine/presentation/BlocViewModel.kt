package nondas.pap.fitness_routine.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.reflect.KClass


abstract class BlocViewModel<in E : Any, out S> : ViewModel() {

    protected abstract val _uiState: StateFlow<S>
    open val uiState: StateFlow<S> get() = _uiState

    private val _errorFlow = MutableSharedFlow<Throwable>()
    open val errorFlow: SharedFlow<Throwable> = _errorFlow.asSharedFlow()

    private val eventHandlers = HashMap<KClass<*>, suspend (Any) -> Unit>()

    @Suppress("UNCHECKED_CAST")
    protected fun <T : E> on(clazz: KClass<T>, handler: suspend (event: T) -> Unit) {
        eventHandlers[clazz] = handler as (suspend (Any) -> Unit)
    }

    open fun <T : E> add(event: T) = viewModelScope.launch {
        val handler = eventHandlers.getValue(event::class)
        handler(event)
    }

    protected open suspend fun addError(throwable: Throwable) {
        _errorFlow.emit(throwable)
    }

    inline fun <reified T> onState(block: (state: T) -> Unit) {
        val state = uiState.value
        if (state is T) {
            block(state)
        }
    }
}

abstract class DebugBloc<in E : Any, out S>() : BlocViewModel<E, S>() {

    override fun <T : E> add(event: T) = viewModelScope.launch {
        println("$TAG Bloc Event(${_uiState.value!!::class.simpleName}): $event")
        super.add(event)
    }

    override suspend fun addError(throwable: Throwable) {
        println("$TAG Bloc Error(${_uiState.value!!::class.simpleName}): $throwable")
        throwable.printStackTrace()
        super.addError(throwable)
    }

    companion object {
        private val TAG = this::class.simpleName
    }
}