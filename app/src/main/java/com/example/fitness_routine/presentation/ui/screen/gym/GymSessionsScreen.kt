package com.example.fitness_routine.presentation.ui.screen.gym

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.presentation.component.BottomBar
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.navigation.Screen
import com.example.fitness_routine.presentation.ui.theme.contentSpacing2
import com.example.fitness_routine.presentation.ui.theme.contentSpacing4
import com.example.fitness_routine.presentation.util.capitalize
import com.example.fitness_routine.presentation.util.toFormattedDate
import com.example.fitness_routine.presentation.util.toTimeStamp
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date


@Composable
fun GymSessionsScreen(
    viewModel: GymSessionsViewModel = hiltViewModel(),
    navigateToScreen: (Screen) -> Unit,
    navigateToWorkoutScreen: (Long) -> Unit
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
        is GymSessionsState.Content -> {
            GymSessionsContent(
                content = state,
                navigateToScreen = navigateToScreen,
                navigateToWorkoutScreen = { navigateToWorkoutScreen(it) }
            )
        }
        GymSessionsState.Idle -> {
            LoadingBox()
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GymSessionsContent(
    content: GymSessionsState.Content,
    navigateToScreen: (Screen) -> Unit,
    navigateToWorkoutScreen: (Long) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(text = "Workout Sessions")
                    }
                },

            )
        },
        bottomBar = {
            BottomBar(
                onClick = { navigateToScreen(it) },
                currentScreen = Screen.Gym
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(contentSpacing4)
                .padding(it),
            //.semantics { contentDescription = Screen.Gym.name },

        ) {

            SessionsContainer(
                content.dailyReports,
                navigateToWorkoutScreen = { navigateToWorkoutScreen(it) }
            )

        }
    }
}


@Composable
private fun SessionsContainer(
    reports: List<DailyReportDomainEntity>,
    navigateToWorkoutScreen: (Long) -> Unit
) {

    val monthGroups = reports
        .filter { it.performedWorkout }
        .groupBy {
            val localDate = it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            localDate.month
        }

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
            Text(text = entry.key.name.capitalize())
            Spacer(modifier = Modifier.height(contentSpacing4))

            entry.value.onEachIndexed { index, report ->
                Text(
                    text = "${index + 1} - ${report.date.toFormattedDate()} - ${report.musclesTrained.joinToString()}",
                    modifier = Modifier.clickable { navigateToWorkoutScreen(report.date.toTimeStamp()) }
                )

                if (index < entry.value.size -1 )
                    Spacer(modifier = Modifier.height(contentSpacing4))

            }
        }
        Spacer(modifier = Modifier.height(contentSpacing2))
    }
}



@Composable
@Preview
private fun GymSessionsContentPreview() {
    GymSessionsContent(
        content = GymSessionsState.Content(
            dailyReports = generateReports()
        ),
        navigateToScreen = {},
        navigateToWorkoutScreen = {}
    )
}

private fun generateReports(): List<DailyReportDomainEntity> {
    return (1..10).map {
        val localDate = LocalDate.of(2024, 1, it)
        val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        DailyReportDomainEntity(
            performedWorkout = if (it > 3) true else false,
            hadCheatMeal = if (it > 2) true else false,
            hadCreatine = true,
            litersOfWater = "2.5",
            gymNotes = "",
            musclesTrained = listOf(Muscle.Legs.name),
            sleepQuality = "4",
            proteinGrams = "120",
            cardioMinutes = "30",
            date = date,
            meal = ""
        )
    }
}