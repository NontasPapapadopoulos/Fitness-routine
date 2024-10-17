package com.example.fitness_routine.presentation.screen.calendar


import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.presentation.component.BottomBar
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.navigation.Screen
import com.example.fitness_routine.presentation.toDate
import com.example.fitness_routine.presentation.toTimeStamp
import com.example.fitness_routine.presentation.util.Calendar
import com.example.fitness_routine.presentation.util.Day
import com.example.fitness_routine.presentation.util.Month
import com.example.fitness_routine.presentation.util.getCurrentDate
import com.example.fitness_routine.presentation.util.getCurrentDay
import com.example.fitness_routine.presentation.util.getCurrentMonth
import com.example.fitness_routine.presentation.util.getCurrentYear
import com.squaredem.composecalendar.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel(),
    navigateToDailyReport: (Long) -> Unit,
    navigateToScreen: (Screen) -> Unit,
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

    when (val state = uiState) {
        is CalendarState.Content -> {
            Content(
                content = state,
                onSelectChoice = { viewModel.add(CalendarEvent.SelectChoice(it)) },
                navigateToDailyReport = { navigateToDailyReport(it) },
                navigateToScreen = { navigateToScreen(it) }
            )
        }

        CalendarState.Idle -> {
            LoadingBox()
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    content: CalendarState.Content,
    onSelectChoice: (Choice) -> Unit,
    navigateToDailyReport: (Long) -> Unit,
    navigateToScreen: (Screen) -> Unit
) {

    val currentYear = getCurrentYear()
    val currentMonth = getCurrentMonth()
    val currentDay = getCurrentDay()

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)


    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(250.dp)
            ) {
                Column {

                    Icon(
                        Icons.Filled.FitnessCenter,
                        null,
                        modifier = Modifier.size(100.dp)
                    )

                    Text("Your Fitness App", modifier = Modifier.padding(16.dp))

                    HorizontalDivider()

                    NavigationDrawerItem(
                        label = { Text(text = "Exercises") },
                        selected = false,
                        onClick = {
                            coroutineScope.launch { toggleDrawerState(drawerState) }
                            navigateToScreen(Screen.Exercise)
                        }
                    )

                    Filters(
                        options = Choice.entries,
                        selectedOption = content.selectedChoice,
                        select = {
                            onSelectChoice(it)
                            coroutineScope.launch { toggleDrawerState(drawerState) }
                        }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(text = "version: 1.0.0") // make this dynamic

                }

            }

        },
        drawerState = drawerState,
    ) {
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
                            onClick = { coroutineScope.launch { toggleDrawerState(drawerState) } }
                        ) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            bottomBar = {
                BottomBar(
                    onClick = { navigateToScreen(it) },
                    currentScreen = Screen.Calendar
                )
            }
        ) {

            Column(
                modifier = Modifier
                    .padding(it))
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    val calendar =
                        Calendar().createCalendar(currentYear.toInt(), currentYear.toInt() + 1)
                    val year = calendar.years[0]

                    YearlyCalendar(
                        months = year.months,
                        year = year.year,
                        currentYear = currentYear,
                        currentMonth = currentMonth,
                        currentDay = currentDay,
                        dailyReports = content.reports,
                        navigateToDailyReport = navigateToDailyReport,
                        state = listState,

                        )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        val calendar = Calendar().createCalendar(currentYear.toInt(), currentYear.toInt() + 1)
        val currentMonthIndex =
            calendar.years[0].months.indexOfFirst { it.monthName == currentMonth }

        if (currentMonthIndex != -1) {
            coroutineScope.launch {
                listState.scrollToItem(currentMonthIndex)
            }
        }
    }

}

private suspend fun toggleDrawerState(drawerState: DrawerState) {
    if (drawerState.isOpen)
        drawerState.close()
    else drawerState.open()
}

fun DrawerState.toggle(scope: CoroutineScope) {
    if (this.isOpen)
        scope.launch { close() }
    else
        scope.launch { open() }
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
    currentDay: String,
    isCurrentMonth: Boolean,
    performedWorkout: Boolean,
    navigateToDailyReport: (Long) -> Unit
) {

    val isCurrentDay = currentDay.toInt() == day.dayOfMonth

    Box(
        modifier = Modifier
            .padding(1.dp)
            .size(45.dp)
            .border(1.dp, Color.Red)
            .background(color = if (isCurrentDay && isCurrentMonth || performedWorkout) Color.Red else Color.White)
            .clickable { navigateToDailyReport(day.date) },
        contentAlignment = Alignment.Center
    ) {
        Text(text = day.dayOfMonth.toString())
    }

}

@Composable
private fun Week(
    days: List<Day>,
    currentDay: String,
    isCurrentMonth: Boolean,
    dailyReports: List<DailyReportDomainEntity>,
    navigateToDailyReport: (Long) -> Unit
) {
    Row {
        days.forEach { day ->
            val reportForDay = dailyReports.find { it.date == day.date.toDate() }
            val performedWorkout = reportForDay?.performedWorkout ?: false

            Day(
                day = day,
                currentDay = currentDay,
                isCurrentMonth = isCurrentMonth,
                performedWorkout = performedWorkout,
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
    currentDay: String,
    dailyReports: List<DailyReportDomainEntity>,
    navigateToDailyReport: (Long) -> Unit
) {

    val isCurrentMonth = currentMonth == nameOfMonth

    Column {
        Text(text = nameOfMonth)
        weeks.forEach { week ->
            Week(
                days = week,
                currentDay = currentDay,
                isCurrentMonth = isCurrentMonth,
                dailyReports = dailyReports,
                navigateToDailyReport = navigateToDailyReport,
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
    currentDay: String,
    dailyReports: List<DailyReportDomainEntity>,
    navigateToDailyReport: (Long) -> Unit,
    state: LazyListState
) {
    val isCurrentYear = year.toString() == currentYear

    LazyColumn(
        state = state
    ) {

        months.forEachIndexed { index, month ->
            val firstWeek = month.days.take(7)
            val secondWeek = month.days.filter { it.dayOfMonth in 8..14 }
            val thirdWeek = month.days.filter { it.dayOfMonth in 15..21 }
            val fourthWeek = month.days.filter { it.dayOfMonth in 22..28 }
            val lastWeek = month.days.filter { it.dayOfMonth > 28 }

            val weeks = listOf(firstWeek, secondWeek, thirdWeek, fourthWeek, lastWeek)

            item(key = index) {
                Text(text = year.toString())
                Month(
                    weeks = weeks,
                    nameOfMonth = month.monthName,
                    currentMonth = currentMonth,
                    currentDay = currentDay,
                    dailyReports = dailyReports,
                    navigateToDailyReport = navigateToDailyReport
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
        }

    }
}


//@Preview
//@Composable
//private fun CalendarScreenPreview() {
//    Content(
//        content = CalendarState.Content(
//            currentDate = getCurrentDate(),
//            selectedChoice = Choice.Workout,
//            reports = generateReports()
//        ),
//        onSelectChoice = {},
//        navigateToDailyReport = {},
//        navigateToScreen = {}
//    )
//}


fun generateReports(): List<DailyReportDomainEntity> {


    return (1..10).map {

        val localDate = LocalDate.of(2024, 1, it)

        val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())

        DailyReportDomainEntity(
            performedWorkout = true,
            hadCheatMeal = false,
            hadCreatine = true,
            litersOfWater = "2.5",
            gymNotes = "",
            musclesTrained = listOf(Muscle.Legs.name),
            sleepQuality = "4",
            proteinGrams = "120",
            cardioMinutes = "30",
            date = date
        )
    }
}