package com.example.fitness_routine.presentation.screen.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitness_routine.presentation.util.Calendar
import com.example.fitness_routine.presentation.util.Day
import com.example.fitness_routine.presentation.util.Month
import com.example.fitness_routine.presentation.ui.theme.FitnessroutineTheme
import com.example.fitness_routine.presentation.util.getCurrentDate
import com.example.fitness_routine.presentation.util.getCurrentDay
import com.example.fitness_routine.presentation.util.getCurrentMonth
import com.example.fitness_routine.presentation.util.getCurrentYear


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen() {

    var displayFilters by remember { mutableStateOf(false) }

    val currentYear = getCurrentYear()
    val currentMonth = getCurrentMonth()
    val currentDay = getCurrentDay()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Fitness Diary") },
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {

            val options = listOf(
                "Performed Workout",
                "Took Creatine",
                "Had any type of Cheat",
            )

            val selectedOption by remember { mutableStateOf(options[0]) }

            if (displayFilters) {
                Filters(
                    options = options,
                    selectedOption = selectedOption,
                    select = {}
                )
            }


            val calendar = Calendar().createCalendar(currentYear.toInt(), currentYear.toInt() + 1)

            val year = calendar.years[0]

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                YearlyCalendar(
                    months = year.months,
                    year = year.year,
                    currentYear = currentYear,
                    currentMonth = currentMonth
                )

            }
        }
    }

}

@Composable
private fun Filters(
    options: List<String>,
    selectedOption: String,
    select: (String) -> Unit
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
                text = option,
                modifier = Modifier.padding(start = 3.dp)
            )
        }
    }
}




@Composable
private fun Day(day: String) {
    Box(
        modifier = Modifier
            .padding(1.dp)
            .size(45.dp)
            .border(1.dp, Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Text(text = day)
    }
}

@Composable
private fun Week(days: List<Day>) {
    Row {
        days.forEach {
            Day(day = it.dayOfMonth.toString())
        }
    }
}


@Composable
private fun Month(
    weeks: List<List<Day>>,
    nameOfMonth: String,
    currentMonth: String
) {
    val isCurrentMonth = nameOfMonth == currentMonth
    Column {
        Text(text = nameOfMonth)
        weeks.forEach { week ->
            Week(days = week)
        }
    }
}


@Composable
private fun YearlyCalendar(
    months: List<Month>,
    year: Int,
    currentYear: String,
    currentMonth: String
) {
    val isCurrentYear = year == currentYear.toInt()

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
                currentMonth = currentMonth
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DayPreview() {
    Day(day = "1")
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun WeekPreview() {
    val calendar = Calendar().createCalendar(2024, 2025)
    val year = calendar.years[0]
    val month = year.months[0]
    val firstWeek = month.days.take(7)

    Week(days = firstWeek)
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MonthPreview() {
    val calendar = Calendar().createCalendar(2024, 2025)
    val year = calendar.years[0]
    val month = year.months[0]
    val firstWeek = month.days.take(7)
    val secondWeek = month.days.filter { it.dayOfMonth in 8..14 }
    val thirdWeek = month.days.filter { it.dayOfMonth in 15..21 }
    val fourthWeek = month.days.filter { it.dayOfMonth in 22 .. 28 }
    val lastWeek = month.days.filter { it.dayOfMonth > 28 }

    val weeks = listOf(firstWeek, secondWeek, thirdWeek, fourthWeek, lastWeek)

    Month(weeks = weeks, month.monthName, currentMonth = "1")
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun YearPreview() {
    FitnessroutineTheme {
        val calendar = Calendar().createCalendar(2024, 2025)

        val year = calendar.years[0]

        YearlyCalendar(
            months = year.months,
            year = year.year,
            currentYear = "2024",
            currentMonth = "1"
        )

    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun CalendarScreenPreview() {
    CalendarScreen()
}