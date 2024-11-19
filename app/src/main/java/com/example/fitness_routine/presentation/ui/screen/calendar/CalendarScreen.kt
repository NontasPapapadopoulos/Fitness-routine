package com.example.fitness_routine.presentation.ui.screen.calendar


import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness_routine.R
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.enums.Choice
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.presentation.component.BottomBar
import com.example.fitness_routine.presentation.component.ContentWithDivider
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.navigation.Screen
import com.example.fitness_routine.presentation.ui.screen.calendar.CalendarScreenConstants.Companion.DAY
import com.example.fitness_routine.presentation.ui.screen.calendar.CalendarScreenConstants.Companion.SIDE_MENU_BUTTON
import com.example.fitness_routine.presentation.ui.theme.AppTheme
import com.example.fitness_routine.presentation.ui.theme.contentSpacing4
import com.example.fitness_routine.presentation.ui.theme.contentSpacing6
import com.example.fitness_routine.presentation.util.toDate
import com.example.fitness_routine.presentation.util.Calendar
import com.example.fitness_routine.presentation.util.Day
import com.example.fitness_routine.presentation.util.Month
import com.example.fitness_routine.presentation.util.getDate
import com.example.fitness_routine.presentation.util.getCurrentDay
import com.example.fitness_routine.presentation.util.getCurrentMonth
import com.example.fitness_routine.presentation.util.getCurrentYear
import com.example.fitness_routine.presentation.util.getDayOfWeek
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

    BackHandler {
        (context as Activity).finish()
    }

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
                navigateToDailyReport = { navigateToDailyReport(it) },
                navigateToScreen = { navigateToScreen(it) },
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
    navigateToDailyReport: (Long) -> Unit,
    navigateToScreen: (Screen) -> Unit,
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


                    NavigationDrawerItem(
                        label = { Text(text = "Settings") },
                        selected = false,
                        onClick = {
                            coroutineScope.launch { toggleDrawerState(drawerState) }
                            navigateToScreen(Screen.Settings)
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

//                            Text(text = getDate())
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { coroutineScope.launch { toggleDrawerState(drawerState) } },
                            modifier = Modifier.testTag(SIDE_MENU_BUTTON)
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
                    .padding(it)
                    .semantics { contentDescription = Screen.Calendar.name }
            ) {
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
                        selectedChoice = content.selectedChoice
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




@Composable
private fun Day(
    day: Day,
    currentDay: String,
    isCurrentMonth: Boolean,
    performedWorkout: Boolean,
    hadCheatMeal: Boolean,
    hadCreatine: Boolean,
    navigateToDailyReport: (Long) -> Unit,
    selectedChoice: Choice,
    modifier: Modifier
) {

    val isCurrentDay = currentDay.toInt() == day.dayOfMonth

    val isChoiceCompleted = when (selectedChoice) {
        Choice.Workout -> performedWorkout
        Choice.Creatine -> hadCreatine
        Choice.Cheat -> hadCheatMeal
    }

    val borderColor = if (isCurrentDay && isCurrentMonth) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.onPrimaryContainer


    val backgroundColor =  if (isCurrentDay && isCurrentMonth) MaterialTheme.colorScheme.onPrimaryContainer
    else if (isChoiceCompleted) {
        when (selectedChoice) {
            Choice.Workout -> MaterialTheme.colorScheme.primary
            Choice.Creatine -> colorResource(R.color.creatine)
            Choice.Cheat -> colorResource(R.color.cheat_meal)
        }
    }
    else
        MaterialTheme.colorScheme.onPrimaryContainer


    val textColor = if (isCurrentDay && isCurrentMonth) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary

    Box(
        modifier = modifier
            .padding(2.dp)
            .size(40.dp)
            .border(
                if (isCurrentDay && isCurrentMonth || isChoiceCompleted) 1.dp else 0.dp,
                borderColor,
                shape = CircleShape
            )
            .background(
                color = backgroundColor,
                shape = CircleShape
            )

            .clickable { navigateToDailyReport(day.date) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.dayOfMonth.toString(),
            color = textColor,
            style = MaterialTheme.typography.bodyLarge
            )
    }

}



@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Month(
    days: List<Day>,
    nameOfMonth: String,
    currentMonth: String,
    currentDay: String,
    dailyReports: List<DailyReportDomainEntity>,
    navigateToDailyReport: (Long) -> Unit,
    selectedChoice: Choice,
    emptyBoxes: Int
) {

    val isCurrentMonth = currentMonth == nameOfMonth
    FlowRow(
        maxItemsInEachRow = 7
    ) {
        (0..<emptyBoxes).forEach {
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .size(40.dp)
            )
        }
        days.forEach { day ->
            val reportForDay = dailyReports.find { it.date == day.date.toDate() }
            Day(
                day = day,
                currentDay = currentDay,
                isCurrentMonth = isCurrentMonth,
                performedWorkout = reportForDay?.performedWorkout ?: false,
                hadCreatine = reportForDay?.hadCreatine ?: false,
                hadCheatMeal = reportForDay?.hadCheatMeal ?: false,
                navigateToDailyReport = navigateToDailyReport,
                selectedChoice = selectedChoice,
                modifier = Modifier.testTag(DAY + nameOfMonth)
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
    state: LazyListState,
    selectedChoice: Choice
) {
    val isCurrentYear = year.toString() == currentYear
//    Text(text = year.toString())

    LazyColumn(
        state = state
    ) {

        months.forEachIndexed { index, month ->
            val emptyBoxes = getEmptyBoxes(month.days.first())


            item(key = index) {
                MonthContainer(
                    currentMonth = currentMonth,
                    month = month,
                    currentDay = currentDay,
                    dailyReports = dailyReports,
                    navigateToDailyReport = navigateToDailyReport,
                    selectedChoice = selectedChoice,
                    emptyBoxes = emptyBoxes
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
        }

    }
}

@Composable
private fun MonthContainer(
    currentMonth: String,
    month: Month,
    currentDay: String,
    dailyReports: List<DailyReportDomainEntity>,
    navigateToDailyReport: (Long) -> Unit,
    selectedChoice: Choice,
    emptyBoxes: Int
) {
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = RoundedCornerShape(contentSpacing4)
            )
            .padding(contentSpacing6)


    ) {
        val isCurrentMonth = currentMonth == month.monthName
        if (isCurrentMonth)
            Text(
                text = "${getDayOfWeek().take(3)}, ${month.monthName.take(3)} $currentDay",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        else
            Text(text = month.monthName)


        DaysHeader()

        Month(
            days = month.days,
            nameOfMonth = month.monthName,
            currentMonth = currentMonth,
            currentDay = currentDay,
            dailyReports = dailyReports,
            navigateToDailyReport = navigateToDailyReport,
            selectedChoice = selectedChoice,
            emptyBoxes = emptyBoxes
        )
    }
}


private fun getEmptyBoxes(firstDay: Day): Int {
    return when(firstDay.dayOfWeekName.lowercase()) {
        "monday" -> 0
        "tuesday" -> 1
        "wednesday" -> 2
        "thursday" -> 3
        "friday" -> 4
        "saturday" -> 5
        "sunday" -> 6
        else -> 0
    }
}

@Composable
private fun DaysHeader() {
    val dayHeaders = listOf("M", "T", "W", "T", "F", "S", "S")

    Row {
        dayHeaders.forEach { dayName ->
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = dayName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}






@Preview
@Composable
private fun CalendarScreenPreview() {
    AppTheme(darkTheme = true) {
        Content(
            content = CalendarState.Content(
                currentDate = getDate(),
                selectedChoice = Choice.Workout,
                reports = generateReports()
            ),
            navigateToDailyReport = {},
            navigateToScreen = {},
        )
    }

}


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


class CalendarScreenConstants private constructor() {
    companion object {
        const val SIDE_MENU_BUTTON = "side_menu_button"
        const val DAY = "day"
    }
}