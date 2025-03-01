package nondas.pap.fitness_routine.domain.interactor.exercise

import nondas.pap.fitness_routine.DummyEntities
import nondas.pap.fitness_routine.exercise
import nondas.pap.fitness_routine.domain.repository.ExerciseRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetExercisesTest {

    private lateinit var getExercises: GetExercises

    @Mock
    private lateinit var exerciseRepository: ExerciseRepository

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        getExercises = GetExercises(exerciseRepository, dispatcher)
    }


    @Test
    fun execute_getExercises() = runTest {
        whenever(exerciseRepository.getExercises()).thenReturn(flowOf(listOf(exercise)))

        val result = getExercises.execute(Unit).first()

        assertEquals(
            Result.success(listOf(exercise)),
            result
        )
    }

    companion object {
        val exercise = DummyEntities.exercise
    }
}