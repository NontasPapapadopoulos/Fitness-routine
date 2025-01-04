package com.example.fitness_routine.domain.interactor

import com.example.fitness_routine.DummyEntities
import com.example.fitness_routine.cheatMeal
import com.example.fitness_routine.domain.interactor.cheat.GetCheatMeals
import com.example.fitness_routine.domain.repository.CheatMealRepository
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
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetCheatMealsTest {

    private lateinit var getCheatMeals: GetCheatMeals

    @Mock
    private lateinit var cheatMealRepository: CheatMealRepository

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        getCheatMeals = GetCheatMeals(cheatMealRepository, dispatcher)
    }

    @Test
    fun execute_getCheatDays() = runTest {
        whenever(cheatMealRepository.getCheatMeals()).thenReturn(flowOf(meals))

        val result = getCheatMeals.execute(GetCheatMeals.Params(100L)).first()

        assertEquals(
            Result.success(meals),
            result
        )

    }


    companion object {
        val meals = listOf(DummyEntities.cheatMeal)
    }
}