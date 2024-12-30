package com.example.fitness_routine.presentation.ui.screen.cheat

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.presentation.component.BackButton
import com.example.fitness_routine.presentation.component.BottomBar
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.navigation.Screen
import com.example.fitness_routine.presentation.ui.screen.gym.BodyMeasurement
import com.example.fitness_routine.presentation.ui.theme.AppTheme
import com.example.fitness_routine.presentation.ui.theme.contentSpacing2
import com.example.fitness_routine.presentation.ui.theme.contentSpacing4
import com.example.fitness_routine.presentation.util.capitalize
import com.example.fitness_routine.presentation.util.toFormattedDate
import com.example.fitness_routine.presentation.util.toTimeStamp
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId
import java.util.Date


@Composable
fun CheatMealsScreen(
    viewModel: CheatMealsViewModel = hiltViewModel(),
    navigateToScreen: (Screen) -> Unit,
    navigateBack: () -> Unit
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
        is CheatMealsState.Content -> {
            CheatMealsContent(
                content = state,
                navigateToScreen = navigateToScreen,
                navigateBack = navigateBack
            )
        }
        CheatMealsState.Idle -> {
            LoadingBox()
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CheatMealsContent(
    content: CheatMealsState.Content,
    navigateToScreen: (Screen) -> Unit,
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Cheat Meals")
                },
                navigationIcon = { BackButton(navigateBack) }

            )
        },
        bottomBar = {
            BottomBar(
                onClick = { navigateToScreen(it) },
                currentScreen = Screen.Cheat
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(contentSpacing4)
                .padding(it),
        ) {

            MealsContainer(content.mealWithMeasurements)
        }

    }
}

@Composable
private fun MealsContainer(meals: List<MealWithMeasurement>) {

    val monthGroups = groupByMonth(meals)

    monthGroups.onEachIndexed { _, entry ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(contentSpacing2)
                )
                .padding(contentSpacing4)

        ) {
            MonthName(entry)
            Spacer(modifier = Modifier.height(contentSpacing4))

            val days = groupByDate(entry)

            days.onEachIndexed { index, day ->
                DailyCheatMeals(day.value)

                BodyMeasurement(measurement = day.value.first().measurement)

                if (index < days.size - 1) {
                    Spacer(modifier = Modifier.height(contentSpacing2))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(contentSpacing4))
                }
            }

        }

        Spacer(modifier = Modifier.height(contentSpacing2))
    }
}

@Composable
private fun DailyCheatMeals(
    cheatDay: List<MealWithMeasurement>
) {
    Text(text = cheatDay.first().date.toFormattedDate())
    cheatDay.forEach {
        it.meals?.forEach { meal ->
            Text(
                text = "• ${meal.meal}",
            )
        }
    }
}

@Composable
private fun groupByDate(entry: Map.Entry<Month, List<MealWithMeasurement>>) =
    entry.value.groupBy {
        val localDate = it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        localDate.dayOfYear
    }

@Composable
private fun MonthName(entry: Map.Entry<Month, List<MealWithMeasurement>>) {
    Text(text = entry.key.name.capitalize())
}

@Composable
private fun groupByMonth(meals: List<MealWithMeasurement>) =
    meals
        .groupBy {
            val localDate = it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            localDate.month
        }


@Composable
@Preview
private fun CheatMealsContentPreview() {
    AppTheme {
        CheatMealsContent(
            content = CheatMealsState.Content(
                mealWithMeasurements = generateMeals()
            ),
            navigateToScreen = {},
            navigateBack = {}
        )
    }

}

private fun generateMeals(): List<MealWithMeasurement> {
    return (1..10).map {
        val localDate = LocalDate.of(2024, if (it < 5) 1 else 2, it)
        val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        MealWithMeasurement(
            date = date,
            meals = generateMeals(date),
            measurement = BodyMeasurementDomainEntity(
                id = "",
                date = date.toTimeStamp(),
                weight = "80.Kg",
                fat = "10",
                metabolicAge = "",
                visceralFat = "",
                bmr = "",
                tbw = "",
                bmi = "",
                muscleMass = ""
            )
        )
    }
}


private fun generateMeals(date: Date): List<CheatMealDomainEntity> {
    return (0..4).map {
        CheatMealDomainEntity(
            id = "",
            meal = "burger",
            date = date
        )
    }
}