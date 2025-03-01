package nondas.pap.fitness_routine.domain.interactor.exercise

import nondas.pap.fitness_routine.domain.repository.ExerciseRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class EditExerciseTest {

    private lateinit var editExercise: EditExercise

    @Mock
    private lateinit var exerciseRepository: ExerciseRepository

    private var dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        editExercise = EditExercise(exerciseRepository, dispatcher)
    }

    @Test
    fun execute_editExercise() = runTest {
        whenever(exerciseRepository.edit(any(), any())).thenReturn(Unit)

        val result = editExercise.execute(EditExercise.Params("exercise1", "exercise2"))

        assertEquals(
            result,
            Result.success(Unit)
        )
    }
}