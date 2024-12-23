package com.example.fitness_routine.presentation.ui.screen.analytics

import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness_routine.domain.entity.CardioDomainEntity
import com.example.fitness_routine.domain.entity.SetDomainEntity
import com.example.fitness_routine.domain.entity.enums.Cardio
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.presentation.component.BackButton
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.component.MusclesTrained
import com.example.fitness_routine.presentation.ui.icons.FitnessDiary
import com.example.fitness_routine.presentation.ui.theme.AppTheme
import com.example.fitness_routine.presentation.ui.theme.contentSpacing2
import com.example.fitness_routine.presentation.ui.theme.contentSpacing4
import com.example.fitness_routine.presentation.util.toFormattedDate
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@Composable
fun AnalyticsScreen(
    navigateBack: () -> Unit,
    viewModel: AnalyticsViewModel = hiltViewModel()
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
        is AnalyticsState.Content -> {
            AnalyticsContent(
                content = state,
                navigateBack = navigateBack,
                onGenerateReport = { viewModel.add(AnalyticsEvent.GenerateReport) },
                onSelectDateFrom = { viewModel.add(AnalyticsEvent.SelectDateFrom(it)) },
                onSelectDateTo = { viewModel.add(AnalyticsEvent.SelectDateTo(it)) },
                onToggleGymSessions = { viewModel.add(AnalyticsEvent.ToggleGymSessions(it)) },
                onToggleCardios = { viewModel.add(AnalyticsEvent.ToggleCardios(it)) },
                onSelectMuscle = { viewModel.add(AnalyticsEvent.SelectMuscle(it)) }
            )
        }
        AnalyticsState.Idle -> { LoadingBox() }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnalyticsContent(
    content: AnalyticsState.Content,
    navigateBack: () -> Unit,
    onGenerateReport: () -> Unit,
    onSelectDateFrom: (String) -> Unit,
    onSelectDateTo: (String) -> Unit,
    onToggleGymSessions: (Boolean) -> Unit,
    onToggleCardios: (Boolean) -> Unit,
    onSelectMuscle: (Muscle) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Analytics")

                        IconButton(onClick = onGenerateReport) {
                            Icon(
                                Icons.Default.PictureAsPdf,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                navigationIcon = { BackButton(navigateBack) }
            )
        }

    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(contentSpacing4)
                .verticalScroll(rememberScrollState())
        ) {

            var showDateFromPicker by remember { mutableStateOf(false) }
            val dateFromPickerState = rememberDatePickerState()

            var showDateToPicker by remember { mutableStateOf(false) }
            val dateToPickerState = rememberDatePickerState()


            MusclesTrained(
                selectedMuscles = listOf(),
                onSelectMuscle = { onSelectMuscle(Muscle.valueOf(it)) },
                testTag = ""
            )


            Row {
                CheckboxItem(
                    text = "Show Gym sessions",
                    isChecked = content.showWorkoutSessions,
                    onCheckedChange = { onToggleGymSessions(it) }
                )

                CheckboxItem(
                    text = "Show cardio",
                    isChecked = content.showCardios,
                    onCheckedChange = { onToggleCardios(it) }
                )
            }


            DatePickerDocked(
                text = "from",
                selectedDate = content.fromDate,
                showDatePicker = showDateFromPicker,
                state = dateFromPickerState,
                toggleDateTimePicker = { showDateFromPicker = it },
                onValueChanged = onSelectDateFrom
            )

            DatePickerDocked(
                text = "to",
                selectedDate = content.toDate,
                showDatePicker = showDateToPicker,
                state = dateToPickerState,
                toggleDateTimePicker = { showDateToPicker = it },
                onValueChanged = onSelectDateTo
            )

            Spacer(modifier = Modifier.height(contentSpacing4))


            content.workouts.forEach {
                WorkoutItem(
                    workout = it,
                    showCardio = content.showCardios,
                    showGymSessions = content.showWorkoutSessions
                )
            }

        }
    }

}

@Composable
private fun WorkoutItem(
    workout: Workout,
    showCardio: Boolean,
    showGymSessions: Boolean
) {

    Column {
        Text(
            text = workout.date.toFormattedDate(),
            style = MaterialTheme.typography.bodyLarge
            )

        if (showGymSessions) {
            val muscles = workout.gymSession?.groupBy { it.muscle }

           muscles?.onEachIndexed { index, muscle ->
               Column {
                   MuscleName(muscle)

                   val exerciseGroup = muscle.value
                       .groupBy { it.exercise }

                   exerciseGroup.forEach { exercise ->
                       ExerciseName(exercise)
                       Set(exercise)
                   }
               }

           }
        }

        if (showCardio) {
            workout.cardios?.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.MonitorHeart,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(contentSpacing2))
                    Text(text = "${it.type} ${it.minutes} minutes")
                }
            }
        }
    }
}

@Composable
private fun ExerciseName(it: Map.Entry<String, List<SetDomainEntity>>) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.FitnessCenter,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(contentSpacing2))
        Text(text = it.key)
    }
}

@Composable
private fun Set(it: Map.Entry<String, List<SetDomainEntity>>) {
    it.value.forEach {
        Text(text = "• ${it.weight} kg - ${it.repeats} repeats")

    }
}

@Composable
private fun MuscleName(entry: Map.Entry<Muscle, List<SetDomainEntity>>) {
    Text(
        text = entry.key.name,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
private fun CheckboxItem(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(text = text)

        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(
    text: String,
    selectedDate: String,
    showDatePicker: Boolean,
    state: DatePickerState,
    toggleDateTimePicker: (Boolean) -> Unit,
    onValueChanged: (String) -> Unit
) {

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = onValueChanged,
            label = { Text(text) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { toggleDateTimePicker(!showDatePicker) }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { toggleDateTimePicker(false) },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = state,
                        showModeToggle = false
                    )
                }
            }
        }
    }
}


@Composable
@Preview
private fun AnalyticsContentPreview() {
    AppTheme {
        AnalyticsContent(
            content = AnalyticsState.Content(
                workouts = generateWorkouts(),
                showCardios = true,
                showWorkoutSessions = true,
                fromDate = "",
                toDate = "",
                selectedMuscles = Muscle.entries
            ),
            navigateBack = {},
            onSelectMuscle = {},
            onGenerateReport = {},
            onSelectDateTo = {},
            onSelectDateFrom = {},
            onToggleCardios = {},
            onToggleGymSessions = {}
        )
    }
}

private fun generateWorkouts(): List<Workout> {
    return (1..11).map {
        val localDate = LocalDate.of(2024, 1, it)
        val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())

        Workout(
            date = date,
            cardios = listOf(
                CardioDomainEntity(id = "", type = Cardio.Walking.name, minutes = "30", date = date)
            ),
            gymSession = (1..11).map {
                SetDomainEntity(
                    id = "",
                    date = 0L,
                    muscle = if (it < 5) Muscle.Chest else Muscle.Biceps,
                    exercise = "Exercise $it",
                    weight = "50",
                    repeats = "12"
                )
            }
        )
    }

}
