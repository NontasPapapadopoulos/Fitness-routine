package nondas.pap.fitness_routine.presentation.ui.screen.gym

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.SettingsAccessibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nondas.pap.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import nondas.pap.fitness_routine.domain.entity.CardioDomainEntity
import nondas.pap.fitness_routine.domain.entity.DailyReportDomainEntity
import nondas.pap.fitness_routine.domain.entity.enums.Cardio
import nondas.pap.fitness_routine.domain.entity.enums.Muscle
import nondas.pap.fitness_routine.domain.repository.BodyMeasurementRepository
import nondas.pap.fitness_routine.presentation.component.BackButton
import nondas.pap.fitness_routine.presentation.component.BottomBar
import nondas.pap.fitness_routine.presentation.component.LoadingBox
import nondas.pap.fitness_routine.presentation.component.MusclesTrained
import nondas.pap.fitness_routine.presentation.navigation.Screen
import nondas.pap.fitness_routine.presentation.ui.theme.AppTheme
import nondas.pap.fitness_routine.presentation.ui.theme.contentSpacing2
import nondas.pap.fitness_routine.presentation.ui.theme.contentSpacing4
import nondas.pap.fitness_routine.presentation.util.capitalize
import nondas.pap.fitness_routine.presentation.util.toFormattedDate
import nondas.pap.fitness_routine.presentation.util.toTimeStamp
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date


@Composable
fun GymSessionsScreen(
    viewModel: GymSessionsViewModel = hiltViewModel(),
    navigateToScreen: (Screen) -> Unit,
    navigateToWorkoutScreen: (Long) -> Unit,
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
        is GymSessionsState.Content -> {
            GymSessionsContent(
                content = state,
                navigateToScreen = navigateToScreen,
                navigateToWorkoutScreen = navigateToWorkoutScreen,
                navigateBack = navigateBack,
                onSelectMuscles = { viewModel.add(GymSessionsEvent.SelectMuscle(it)) }
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
    navigateToWorkoutScreen: (Long) -> Unit,
    navigateBack: () -> Unit,
    onSelectMuscles: (Muscle) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Workout Sessions")
                },
                navigationIcon = { BackButton(navigateBack) }
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

        ) {

            MusclesTrained(
                onSelectMuscle = onSelectMuscles,
                selectedMuscles = content.selectedMuscles,
                testTag = GymSessionsScreenConstants.MUSCLE_ITEM
            )

            Spacer(modifier = Modifier.height(contentSpacing4))


            val sessions = if (content.selectedMuscles.isEmpty()) content.workoutSessions
            else content.workoutSessions.filter { it.report.musclesTrained.containsAll(content.selectedMuscles) }

            SessionsContainer(
                sessions = sessions,
                navigateToWorkoutScreen = navigateToWorkoutScreen,
            )

        }
    }
}


@Composable
private fun SessionsContainer(
    sessions: List<WorkoutSession>,
    navigateToWorkoutScreen: (Long) -> Unit,
) {

    val monthGroups = sessions
        .filter { it.report.performedWorkout || it.measurement != null}
        .groupBy {
            val localDate = it.report.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
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

            entry.value.onEachIndexed { index, session ->

                SessionItem(
                    index = index,
                    session = session,
                    modifier = Modifier.clickable { navigateToWorkoutScreen(session.report.date.toTimeStamp()) }

                )
                if (index < entry.value.size -1) {
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
private fun SessionItem(
    index: Int,
    session: WorkoutSession,
    modifier: Modifier
) {


    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "${index + 1}")

        Spacer(modifier = Modifier.width(contentSpacing4))
        Column {
            val musclesTrained = if (session.report.musclesTrained.isNotEmpty()) " - " + session.report.musclesTrained.joinToString() else ""
            Text(
                text = "${session.report.date.toFormattedDate()} $musclesTrained",
            )

            val hasCardio = session.cardios.isNotEmpty() &&
                    session.cardios.filter {
                        it.type.isNotEmpty() &&
                    it.minutes.isNotEmpty()
                    }.size > 1


            if (hasCardio) {
                session.cardios.forEach { cardio ->
                    if (cardio.type.isNotEmpty()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.MonitorHeart,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(contentSpacing2))
                            Text(text = "${cardio.type} - ${cardio.minutes} minutes")
                        }
                    }
                }
            }
            BodyMeasurement(session.measurement)
        }
    }

}

@Composable
fun BodyMeasurement(
    measurement: BodyMeasurementDomainEntity?
) {
    if (measurement != null) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Weight: ${measurement.weight}kg - Fat:${measurement.fat}%",
                color = MaterialTheme.colorScheme.primary
            )

            Icon(
                Icons.Default.SettingsAccessibility, null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Composable
@Preview
private fun GymSessionsContentPreview() {
    AppTheme {
        GymSessionsContent(
            content = GymSessionsState.Content(
                workoutSessions = workoutSessions(),
                selectedMuscles = listOf(Muscle.Chest, Muscle.Biceps)
            ),
            navigateToScreen = {},
            navigateToWorkoutScreen = {},
            navigateBack = {},
            onSelectMuscles = {}
        )
    }

}

private fun workoutSessions(): List<WorkoutSession> {
    return (1..10).map {
        val localDate = LocalDate.of(2024, 1, it)
        val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        WorkoutSession(
            report = DailyReportDomainEntity(
                performedWorkout = if (it > 3) true else false,
                hadCheatMeal = if (it > 2) true else false,
                hadCreatine = true,
                litersOfWater = "2.5",
                musclesTrained = if (it == 4) listOf() else listOf(Muscle.Legs),
                sleepQuality = "4",
                proteinGrams = "120",
                date = date,
            ),
            cardios = listOf(
                CardioDomainEntity(id = "", type = Cardio.Walking.name, minutes = "30", date = date)
            ),
            measurement = if (it == 4) BodyMeasurementDomainEntity(
                weight = "82",
                fat = "15",
                muscleMass = "70",
                bmi = "",
                tbw = "",
                metabolicAge = "",
                visceralFat = "",
                bmr = "",
                id = "",
                date = date.toTimeStamp()
            ) else null
        )
    }
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
            musclesTrained = if (it == 1) listOf() else listOf(Muscle.Legs),
            sleepQuality = "4",
            proteinGrams = "120",
            date = date,
        )
    }
}


class GymSessionsScreenConstants private constructor() {
    companion object {
        const val MUSCLE_ITEM = "muscle_item_"
    }
}