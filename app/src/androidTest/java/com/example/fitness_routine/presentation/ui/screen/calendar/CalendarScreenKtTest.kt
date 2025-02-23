package com.example.fitness_routine.presentation.ui.screen.calendar

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.fitness_routine.domain.entity.enums.Choice
import com.example.fitness_routine.presentation.component.AppSurface
import com.example.fitness_routine.presentation.util.toTimeStamp
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.Date

@RunWith(AndroidJUnit4::class)
class CalendarScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()


    @Mock
    private lateinit var viewModel: CalendarViewModel


    @Before
    fun setUp() {
        whenever(viewModel.errorFlow).thenReturn(MutableSharedFlow())
    }


    @Test
    fun xxxx() {

    }




    companion object {

        val defaultContent = CalendarState.Content(
            reports = listOf(),
            currentDate = "25/03/2025",
            selectedChoice = Choice.Workout
        )
    }


}