package com.example.fitness_routine.domain.interactor.exercise

import com.example.fitness_routine.DummyEntities
import com.example.fitness_routine.exercise
import com.example.fitness_routine.domain.repository.ExerciseRepository
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
class AddExerciseTest {

    private lateinit var addExercise: AddExercise

    @Mock
    private lateinit var exerciseRepository: ExerciseRepository

    private val dispatcher = UnconfinedTestDispatcher()


    @Before
    fun setUp() {
        addExercise = AddExercise(exerciseRepository, dispatcher)
    }


    @Test
    fun execute_addExercise() = runTest {
        whenever(exerciseRepository.add(any())).thenReturn(Unit)

        val result = addExercise.execute(AddExercise.Params(exercise))

        assertEquals(
            Result.success(Unit),
            result
        )
    }

    companion object {
        val exercise = DummyEntities.exercise
    }
}