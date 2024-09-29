package com.example.fitness_routine.presentation.screen.calendar


import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.util.Calendar
import com.example.fitness_routine.presentation.util.Day
import com.example.fitness_routine.presentation.util.Month
import com.example.fitness_routine.presentation.util.getCurrentDate
import com.example.fitness_routine.presentation.util.getCurrentMonth
import com.example.fitness_routine.presentation.util.getCurrentYear


@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel(),
    navigateToDailyReport: (Long) -> Unit
) {

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.errorFlow.collect { error ->
            Toast.makeText(
                context,
                error.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(val state = uiState) {
        is CalendarState.Content -> {
            Content(
                content = state,
                onSelectChoice = { viewModel.add(CalendarEvent.SelectChoice(it)) },
                navigateToDailyReport = { navigateToDailyReport(it) }
            )
        }

        CalendarState.Idle -> { LoadingBox() }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    content: CalendarState.Content,
    onSelectChoice: (Choice) -> Unit,
    navigateToDailyReport: (Long) -> Unit,
) {

    var displayFilters by remember { mutableStateOf(false) }

    val currentYear = getCurrentYear()
    val currentMonth = getCurrentMonth()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(text = "Fitness Diary")

                        Text(text = getCurrentDate())
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { displayFilters = !displayFilters }
                    ) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            if (displayFilters) {
                Filters(
                    options = Choice.entries,
                    selectedOption = content.selectedChoice,
                    select = onSelectChoice
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val calendar = Calendar().createCalendar(currentYear.toInt(), currentYear.toInt() + 1)
                val year = calendar.years[0]

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    YearlyCalendar(
                        months = year.months,
                        year = year.year,
                        currentYear = currentYear,
                        currentMonth = currentMonth,
                        navigateToDailyReport = navigateToDailyReport
                    )

                }
            }
        }

    }

}

@Composable
private fun Filters(
    options: List<Choice>,
    selectedOption: Choice,
    select: (Choice) -> Unit
) {

    Text(text = "Select an option: ")

    options.forEach { option ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(3.dp)
        ) {
            RadioButton(
                selected = (option == selectedOption),
                onClick = {
                    select(option)
                }
            )

            Text(
                text = option.value,
                modifier = Modifier.padding(start = 3.dp)
            )
        }
    }

}




@Composable
private fun Day(
    day: Day,
    navigateToDailyReport: (Long) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(1.dp)
            .size(45.dp)
            .border(1.dp, Color.Red)
            .clickable { navigateToDailyReport(day.date) },
        contentAlignment = Alignment.Center
    ) {
        Text(text = day.dayOfWeekName)
    }
}

@Composable
private fun Week(
    days: List<Day>,
    navigateToDailyReport: (Long) -> Unit
) {
    Row {
        days.forEach {
            Day(
                day = it,
                navigateToDailyReport = navigateToDailyReport
            )
        }
    }
}


@Composable
private fun Month(
    weeks: List<List<Day>>,
    nameOfMonth: String,
    currentMonth: String,
    navigateToDailyReport: (Long) -> Unit
) {

    val isCurrentMonth = currentMonth == nameOfMonth

    Column {
        Text(text = nameOfMonth)
        weeks.forEach { week ->
            Week(
                days = week,
                navigateToDailyReport = navigateToDailyReport
            )
        }
    }
}


@Composable
private fun YearlyCalendar(
    months: List<Month>,
    year: Int,
    currentYear: String,
    currentMonth: String,
    navigateToDailyReport: (Long) -> Unit
) {
    val isCurrentYear = year.toString() == currentYear

    Column {
        Text(text = year.toString())
        months.forEach {
            val firstWeek = it.days.take(7)
            val secondWeek = it.days.filter { it.dayOfMonth in 8..14 }
            val thirdWeek = it.days.filter { it.dayOfMonth in 15..21 }
            val fourthWeek = it.days.filter { it.dayOfMonth in 22 .. 28 }
            val lastWeek = it.days.filter { it.dayOfMonth > 28 }

            val weeks = listOf(firstWeek,secondWeek,   thirdWeek, fourthWeek, lastWeek)

            Month(
                weeks = weeks,
                nameOfMonth = it.monthName,
                currentMonth = currentMonth,
                navigateToDailyReport = navigateToDailyReport
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

    }
}



@Preview
@Composable
private fun CalendarScreenPreview() {
    CalendarScreen(
        navigateToDailyReport = {}
    )
}