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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import com.example.fitness_routine.BuildConfig
import com.example.fitness_routine.R
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.enums.Choice
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.presentation.component.BottomBar
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.navigation.Screen
import com.example.fitness_routine.presentation.ui.screen.calendar.CalendarScreenConstants.Companion.DAY
import com.example.fitness_routine.presentation.ui.screen.calendar.CalendarScreenConstants.Companion.SIDE_MENU_BUTTON
import com.example.fitness_routine.presentation.ui.screen.sidemenu.SideMenu
import com.example.fitness_routine.presentation.ui.screen.sidemenu.toggleDrawerState
import com.example.fitness_routine.presentation.ui.theme.AppTheme
import com.example.fitness_routine.presentation.ui.theme.contentSize1
import com.example.fitness_routine.presentation.ui.theme.contentSize2
import com.example.fitness_routine.presentation.ui.theme.contentSpacing1
import com.example.fitness_routine.presentation.ui.theme.contentSpacing2
import com.example.fitness_routine.presentation.ui.theme.contentSpacing3
import com.example.fitness_routine.presentation.ui.theme.contentSpacing4
import com.example.fitness_routine.presentation.ui.theme.contentSpacing6
import com.example.fitness_routine.presentation.util.Calendar
import com.example.fitness_routine.presentation.util.Day
import com.example.fitness_routine.presentation.util.Month
import com.example.fitness_routine.presentation.util.getCurrentDay
import com.example.fitness_routine.presentation.util.getCurrentMonth
import com.example.fitness_routine.presentation.util.getCurrentYear
import com.example.fitness_routine.presentation.util.getDate
import com.example.fitness_routine.presentation.util.getDayOfWeek
import com.example.fitness_routine.presentation.util.getIcon
import com.example.fitness_routine.presentation.util.toDate
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
                onSelect = { viewModel.add(CalendarEvent.SelectChoice(it)) },
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
    onSelect: (Choice) -> Unit
) {

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)


    ModalNavigationDrawer(
        drawerContent = {
            SideMenu(coroutineScope, drawerState, navigateToScreen)
        },
        drawerState = drawerState,
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Fitness Diary",
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.titleLarge
                        )
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

            val currentYear = getCurrentYear().toInt()
            val currentMonth = getCurrentMonth()
            val currentDay = getCurrentDay()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
                    .semantics { contentDescription = Screen.Calendar.name },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val calendar =
                    Calendar().createCalendar(currentYear - 1 , currentYear + 1)
                val months = calendar.years
                    .map { it.months }
                    .flatten()

                YearlyCalendar(
                    months = months,
                    currentYear = currentYear,
                    currentMonth = currentMonth,
                    currentDay = currentDay,
                    dailyReports = content.reports,
                    navigateToDailyReport = navigateToDailyReport,
                    state = listState,
                    selectedChoice = content.selectedChoice
                )

                Spacer(modifier = Modifier.weight(1f))

                Choices(onSelect, content)

                Spacer(modifier = Modifier.height(contentSpacing3))
            }

            LaunchedEffect(Unit) {
                val calendar = Calendar().createCalendar(currentYear - 1, currentYear + 1)
                val currentMonthIndex = calendar.years.map { it.months }
                    .flatten()
                    .indexOfFirst {
                        it.monthName == currentMonth && it.year == currentYear
                    }

                if (currentMonthIndex != -1) {
                    coroutineScope.launch {
                        listState.scrollToItem(currentMonthIndex)
                    }
                }
            }
        }
    }
}




@Composable
private fun Choices(
    onSelect: (Choice) -> Unit,
    content: CalendarState.Content
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ){
        Choice.entries.forEach {
            ChoiceItem(
                choice = it,
                onSelect = { onSelect(it) },
                isSelected = content.selectedChoice == it
            )
        }
    }
}



@Composable
private fun Day(
    day: Day,
    currentDay: String,
    isCurrentMonth: Boolean,
    isCurrentYear: Boolean,
    reportForDay: DailyReportDomainEntity?,
    navigateToDailyReport: (Long) -> Unit,
    selectedChoice: Choice,
    modifier: Modifier
) {

    val isCurrentDay = currentDay.toInt() == day.dayOfMonth && isCurrentYear && isCurrentMonth

    val isChoiceCompleted = when (selectedChoice) {
        Choice.Workout -> reportForDay?.performedWorkout ?: false
        Choice.Creatine -> reportForDay?.hadCreatine ?: false
        Choice.Cheat -> reportForDay?.hadCheatMeal ?: false
    }

    val borderColor = if (isCurrentDay) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer
    val textColor = if (isCurrentDay) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary


    val backgroundColor =  if (isCurrentDay) MaterialTheme.colorScheme.onPrimaryContainer
    else if (isChoiceCompleted) {
        when (selectedChoice) {
            Choice.Workout -> MaterialTheme.colorScheme.primary
            Choice.Creatine -> colorResource(R.color.creatine)
            Choice.Cheat -> colorResource(R.color.cheat_meal)
        }
    }
    else
        MaterialTheme.colorScheme.onPrimaryContainer


    Box(
        modifier = modifier
            .padding(2.dp)
            .size(40.dp)
            .border(
                if (isCurrentDay || isChoiceCompleted) 1.dp else 0.dp,
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
    isCurrentYear: Boolean,
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
                isCurrentYear = isCurrentYear,
                reportForDay = reportForDay,
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
    currentYear: Int,
    currentMonth: String,
    currentDay: String,
    dailyReports: List<DailyReportDomainEntity>,
    navigateToDailyReport: (Long) -> Unit,
    state: LazyListState,
    selectedChoice: Choice
) {

    LazyRow (
        state = state,
        modifier = Modifier.fillMaxWidth()
    ) {

        months.forEachIndexed { index, month ->
            val emptyBoxes = getEmptyBoxes(month.days.first())


            item(key = index) {
                MonthContainer(
                    currentMonth = currentMonth,
                    month = month,
                    currentDay = currentDay,
                    isCurrentYear = currentYear == month.year,
                    dailyReports = dailyReports,
                    navigateToDailyReport = navigateToDailyReport,
                    selectedChoice = selectedChoice,
                    emptyBoxes = emptyBoxes,
                    modifier = Modifier
                        .fillParentMaxWidth(1f)
                        .padding(contentSpacing4)
                )

                Spacer(modifier = Modifier.height(contentSpacing4))
            }
        }

    }
}

@Composable
private fun MonthContainer(
    currentMonth: String,
    month: Month,
    currentDay: String,
    isCurrentYear: Boolean,
    dailyReports: List<DailyReportDomainEntity>,
    navigateToDailyReport: (Long) -> Unit,
    selectedChoice: Choice,
    emptyBoxes: Int,
    modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = RoundedCornerShape(contentSpacing4)
            )
            .padding(vertical = contentSpacing6)
    ) {

        val isCurrentMonth = currentMonth == month.monthName && isCurrentYear
        MonthHeader(isCurrentMonth, month, currentDay)

        DaysHeader()

        Month(
            days = month.days,
            nameOfMonth = month.monthName,
            currentMonth = currentMonth,
            currentDay = currentDay,
            isCurrentYear = isCurrentYear,
            dailyReports = dailyReports,
            navigateToDailyReport = navigateToDailyReport,
            selectedChoice = selectedChoice,
            emptyBoxes = emptyBoxes
        )
    }

}

@Composable
private fun Selections() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier,
    ) {

        SelectionItem(color = MaterialTheme.colorScheme.primary, text = Choice.Workout.value)

        Spacer(modifier = Modifier.width(contentSpacing3))
        SelectionItem(color = colorResource(R.color.creatine), text = Choice.Creatine.value)

        Spacer(modifier = Modifier.width(contentSpacing3))
        SelectionItem(color = colorResource(R.color.cheat_meal), text = Choice.Cheat.value)

    }
}

@Composable
private fun SelectionItem(color: Color, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(contentSize2)
                .background(color, shape = CircleShape)
        )

        Spacer(modifier = Modifier.width(contentSize1))

        Text(
            text = text,
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Composable
private fun MonthHeader(
    isCurrentMonth: Boolean,
    month: Month,
    currentDay: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = contentSpacing6, bottom = contentSpacing6),
        horizontalArrangement = Arrangement.Start
    ) {
        if (isCurrentMonth)
            Text(
                text = "${getDayOfWeek().take(3)}, ${month.monthName.take(3)} $currentDay",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        else
            Text(text = month.monthName)
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


@Composable
private fun ChoiceItem(
    choice: Choice,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(contentSpacing1)
            .background(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = RoundedCornerShape(contentSpacing2)
            )
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(contentSpacing2)
                    )
                } else Modifier
            )
            .padding(contentSpacing2)
            .clickable { onSelect() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        val color = when (choice) {
            Choice.Workout -> MaterialTheme.colorScheme.primary
            Choice.Creatine -> colorResource(R.color.creatine)
            Choice.Cheat -> colorResource(R.color.cheat_meal)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                choice.getIcon(),
                contentDescription = null,
                modifier =
                Modifier
                    .background(color = color, shape = RoundedCornerShape(contentSpacing2))
                    .padding(contentSpacing1)
            )

            Spacer(modifier = Modifier.width(contentSpacing2))

            Text(
                text = choice.name,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyMedium
            )

        }
    }
}



@Preview
@Composable
private fun CalendarScreenPreviewDark() {
    Column {
        AppTheme(darkTheme = true) {
            Content(
                content = CalendarState.Content(
                    currentDate = getDate(),
                    selectedChoice = Choice.Workout,
                    reports = generateReports()
                ),
                navigateToDailyReport = {},
                navigateToScreen = {},
                onSelect = {},
            )
        }

        AppTheme {
            Content(
                content = CalendarState.Content(
                    currentDate = getDate(),
                    selectedChoice = Choice.Workout,
                    reports = generateReports()
                ),
                navigateToDailyReport = {},
                navigateToScreen = {},
                onSelect = {},
            )
        }
    }
}

@Preview
@Composable
private fun CalendarScreenPreview() {
    Column {
        AppTheme() {
            Content(
                content = CalendarState.Content(
                    currentDate = getDate(),
                    selectedChoice = Choice.Workout,
                    reports = generateReports()
                ),
                navigateToDailyReport = {},
                navigateToScreen = {},
                onSelect = {},
            )
        }
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
            musclesTrained = listOf(Muscle.Legs.name),
            sleepQuality = "4",
            proteinGrams = "120",
            date = date,
        )
    }
}


class CalendarScreenConstants private constructor() {
    companion object {
        const val SIDE_MENU_BUTTON = "side_menu_button"
        const val DAY = "day"
    }
}