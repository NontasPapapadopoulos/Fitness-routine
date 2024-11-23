package com.example.fitness_routine.presentation.ui.screen.workout

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness_routine.R
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.entity.SetDomainEntity
import com.example.fitness_routine.domain.entity.SettingsDomainEntity
import com.example.fitness_routine.domain.entity.enums.Choice
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.presentation.component.BackButton
import com.example.fitness_routine.presentation.component.BottomBar
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.component.MusclesTrained
import com.example.fitness_routine.presentation.navigation.Screen
import com.example.fitness_routine.presentation.ui.theme.AppTheme
import com.example.fitness_routine.presentation.util.asTextFieldValue
import com.example.fitness_routine.presentation.util.toFormattedDate
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date


@Composable
fun WorkoutScreen(
    viewModel: WorkoutViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    onNavigateToExercises: (Muscle) -> Unit,
    onNavigateToScreen: (Screen) -> Unit
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

    LaunchedEffect(Unit) {
        viewModel.navigateToExercisesFlow.collectLatest { muscle ->
            onNavigateToExercises(muscle)
        }
    }


    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when(val state = uiState) {
        is WorkoutState.Content -> WorkoutContent(
            content = state,
            navigateBack = navigateBack,
            onAddSet = { muscle, exercise -> viewModel.add(WorkoutEvent.AddNewSet(muscle, exercise)) },
            onDeleteSet = { viewModel.add(WorkoutEvent.DeleteSet(it)) },
            onSelectMuscle = { viewModel.add(WorkoutEvent.SelectMuscle(it)) },
            onShowDialog = { viewModel.add(WorkoutEvent.ShowDialog(it)) },
            onDismissDialog = { viewModel.add(WorkoutEvent.DismissDialog) },
            onAddExercise = { muscle, exercise -> viewModel.add(WorkoutEvent.AddNewExercise(muscle, exercise)) },
            onUpdateSet = { set, field, value -> viewModel.add(WorkoutEvent.UpdateSet(set, field, value)) },
            onNavigateToExercises = { viewModel.add(WorkoutEvent.NavigateToExercises(it)) },
            onNavigateToScreen = onNavigateToScreen
        )
        WorkoutState.Idle -> { LoadingBox() }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WorkoutContent(
    content: WorkoutState.Content,
    navigateBack: () -> Unit,
    onAddSet: (Muscle, String) -> Unit,
    onDeleteSet: (SetDomainEntity) -> Unit,
    onUpdateSet: (SetDomainEntity, SetField, String) -> Unit,
    onSelectMuscle: (Muscle) -> Unit,
    onShowDialog: (Dialog) -> Unit,
    onDismissDialog: () -> Unit,
    onAddExercise: (Muscle, String) -> Unit,
    onNavigateToExercises: (Muscle) -> Unit,
    onNavigateToScreen: (Screen) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                        ,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column {
                            Text(text= "Workout")
                            Text(text = content.date.toFormattedDate())
                        }

                        AddBreak(addBreak = { onShowDialog(Dialog.Break) })

                    }
                },
                navigationIcon = { BackButton(navigateBack) }
            )
        },
        bottomBar = {
            BottomBar(
                onClick = { onNavigateToScreen(it) },
                currentScreen = Screen.Workout
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {

            MusclesTrained(
                selectedMuscles = content.musclesTrained.map { it.name },
                onSelectMuscle = { onSelectMuscle(Muscle.valueOf(it)) },
                testTag = "xx"
            )


            content.musclesTrained.forEach { muscle ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = muscle.name)

                    AddExercise(addExercise = { onShowDialog(Dialog.AddExercise(muscle)) })

                }

                val setsByExercise = content.sets
                    .filter { it.muscle == muscle }
                    .groupBy { it.exercise }

                setsByExercise.forEach { (exercise, sets) ->
                    Text(text = exercise)

                    sets.forEachIndexed { index, set ->
                        Set(
                            set = set,
                            index = index + 1,
                            delete = { onDeleteSet(set) },
                            update = { set, field, value -> onUpdateSet(set, field, value ) }
                        )

                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        AddSet(addSet = { onAddSet(muscle, exercise) })
                    }
                }

            }


            when (content.dialog) {
                is Dialog.AddExercise -> {
                    AddExerciseDialog(
                        onDismissDialog = onDismissDialog,
                        exercises = content.exercises.filter { it.muscle == content.dialog.muscle },
                        selectedMuscle = content.dialog.muscle,
                        onAddExercise = { muscle, exercise -> onAddExercise(muscle, exercise) },
                        onNavigateToExercises =  { onNavigateToExercises(it) }
                    )
                }

                Dialog.Break -> {
                    BreakDialog(
                        breakDuration = content.breakTimeDuration,
                        onDismissDialog = onDismissDialog
                    )
                }

                null -> {}
            }
        }
    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExerciseDialog(
    onDismissDialog: () -> Unit,
    exercises: List<ExerciseDomainEntity>,
    selectedMuscle: Muscle,
    onAddExercise: (Muscle, String) -> Unit,
    onNavigateToExercises: (Muscle) -> Unit
) {

    LaunchedEffect(Unit) {
        if (exercises.isEmpty())
            onNavigateToExercises(selectedMuscle)
    }


    if (exercises.isNotEmpty()) {
        var expanded by remember { mutableStateOf(false) }
        var selectedOption by remember { mutableStateOf(exercises[0].name) }

        AlertDialog(
            onDismissRequest = onDismissDialog
            ,
            title = {
                Text(text = "Select Exercise")
            },
            icon = {
                Icon(Icons.Filled.FitnessCenter, null)
            },
            text = {
                Column {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            value = selectedOption,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(selectedMuscle.name) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            exercises.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option.name) },
                                    onClick = {
                                        selectedOption = option.name
                                        expanded = false
                                    }
                                )
                            }
                        }

                    }
                }
            },
            confirmButton = {

                Row {
                    Button(onClick = { onNavigateToExercises(selectedMuscle) }) {
                        Text(text = "Add more")
                    }

                }

            },
            dismissButton = {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = onDismissDialog,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.weight(0.1f))

                    Button(
                        onClick = {
                            onAddExercise(selectedMuscle, selectedOption)
                            onDismissDialog()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Confirm")
                    }
                }
            }
        )
    }
}



@SuppressLint("DefaultLocale", "RememberReturnType")
@Composable
fun BreakDialog(
    breakDuration: String,
    onDismissDialog: () -> Unit
) {
    val context = LocalContext.current
    val mediaPlayer = remember {
        MediaPlayer.create(context, R.raw.bell_ring)
    }

    var isRunning by remember { mutableStateOf(false) }
    var remainingSeconds by remember { mutableStateOf(breakDuration.toInt()) }

    val minutes = remainingSeconds / 60
    val displaySeconds = remainingSeconds % 60

    val time = String.format("%02d:%02d", minutes, displaySeconds)

    LaunchedEffect(isRunning, remainingSeconds) {
        if (isRunning) {
            while (remainingSeconds > 0) {
                delay(1000L)
                remainingSeconds -= 1
            }
            if (remainingSeconds == 0) {
                isRunning = false
                mediaPlayer.start()
            }
        }
    }


    AlertDialog(
        onDismissRequest = {},
        title = {
            Text(text = "Break")
        },
        icon = {
            Icon(Icons.Outlined.Timer, contentDescription = null)
        },
        text = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = time,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        },
        confirmButton = {
            val text = if (isRunning) "Reset" else "Start"
            Button(
                onClick = {
                    // reset
                    if (remainingSeconds > 0) {
                        if (isRunning) {
                            remainingSeconds = breakDuration.toInt()
                        } 
                        else {
                            isRunning = true
                        }
                    } else {
                        remainingSeconds = breakDuration.toInt()
                        isRunning = true
                    }

//                    if (isRunning && remainingSeconds > 0 ) {
//                        remainingSeconds = breakDuration.toInt()
//                    } else if (!isRunning && remainingSeconds > 0) {
//                        isRunning = true
//                    } else {
//                        remainingSeconds = breakDuration.toInt()
//                        isRunning = true
//                    }

                }
            ) {
                Text(text)
            }
        },
        dismissButton = {
            val text = if (isRunning) "Stop" else "Close"
            Button(
                onClick = {
                        if (isRunning) isRunning = false else onDismissDialog()
                }
            ) {
                Text(text)
            }
        },
        properties = DialogProperties(dismissOnClickOutside = true)
    )

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
}

@Composable
private fun AddBreak(
    addBreak: () -> Unit
) {
    Row(
        modifier = Modifier.clickable { addBreak() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Break")
        Spacer(modifier = Modifier.width(4.dp))
        Icon(Icons.Outlined.Timer, contentDescription = null)
    }
}


@Composable
private fun Set(
    set: SetDomainEntity,
    index: Int,
    delete: (SetDomainEntity) -> Unit,
    update: (SetDomainEntity, SetField, String) -> Unit,
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = index.toString()
        )

        Spacer(modifier = Modifier.width(10.dp))


        OutlinedTextField(
            value = set.weight.asTextFieldValue(),
            onValueChange = { update(set, SetField.Weight, it.text) },
            singleLine = true,
            label = { Text(text = "Weight") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.weight(0.5f)
        )

        Spacer(modifier = Modifier.width(10.dp))

        OutlinedTextField(
            value = set.repeats.asTextFieldValue(),
            onValueChange = { update(set, SetField.Repeat, it.text) },
            singleLine = true,
            label = { Text(text = "Repeats") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.weight(0.5f)
        )


        IconButton(onClick = { delete(set) } ) {
            Icon(
                Icons.Default.RemoveCircleOutline,
                tint = MaterialTheme.colorScheme.onError,
                contentDescription = null)
        }
    }
}


@Composable
private fun AddSet(
    addSet: () -> Unit,

) {

    Row(
        modifier = Modifier.clickable { addSet() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Add new Set",
            color = MaterialTheme.colorScheme.primary
        )
        IconButton(
            onClick = addSet
        ) {
            Icon(
                Icons.Default.AddCircleOutline,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
        }
    }
}


@Composable
private fun AddExercise(
    addExercise: () -> Unit,
) {

    Row(
        modifier = Modifier.clickable { addExercise() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Add Exercise",
            color = MaterialTheme.colorScheme.primary
        )
        IconButton(
            onClick = addExercise
        ) {
            Icon(
                Icons.Default.AddCircleOutline,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
        }
    }
}


@Preview
@Composable
private fun SetPreview() {
    val setDomainEntity = SetDomainEntity(
        id = "",
        workoutDate = 1L,
        muscle = Muscle.Biceps,
        exercise = "Excercise",
        weight = "20",
        repeats = "8"
    )
    
    Set(set = setDomainEntity, delete = {}, index = 1, update = { _, _, _-> })
}



@Preview
@Composable
private fun WorkoutContentPreview() {
    AppTheme(darkTheme = true) {
        WorkoutContent(
            content = WorkoutState.Content(
                sets = generateSets(),
                exercises = generateExercises(),
                date = 1728939600000,
                musclesTrained = listOf(Muscle.Biceps, Muscle.Chest),
                dailyReport = getDailyReport(),
                dialog = null,
                breakTimeDuration = "60"
            ),
            navigateBack = {},
            onAddSet = { _, _ -> },
            onDeleteSet = {},
            onSelectMuscle = {},
            onShowDialog = {},
            onDismissDialog = {},
            onAddExercise = { _, _ -> },
            onNavigateToExercises = {},
            onNavigateToScreen = {},
            onUpdateSet = { _, _, _ -> }
        )
    }
}


@Preview
@Composable
private fun BreakDialogPreview() {
    BreakDialog(
        onDismissDialog = {},
        breakDuration = "60"
    )
}


@Preview
@Composable
private fun AddExerciseDialogPreview() {
    AppTheme(darkTheme = true) {
        AddExerciseDialog(
            onDismissDialog = {},
            exercises = generateExercises(),
            selectedMuscle = Muscle.Chest,
            onAddExercise = {_, _ ->},
            onNavigateToExercises = {}
        )
    }

}


private fun generateSets(): List<SetDomainEntity> {
    return  (0..12).map {
        SetDomainEntity(
            id = "",
            workoutDate = 100000L,
            muscle = if (it < 6 ) Muscle.Chest else Muscle.Biceps,
            exercise = if (it % 2 == 0) "Exercise 1" else "Exercise 2",
            weight = "30",
            repeats = "12"
        )
    }

}

private fun generateExercises(): List<ExerciseDomainEntity> {
    return (0..10).map {
        ExerciseDomainEntity(
            muscle = if (it < 5) Muscle.Chest else Muscle.Biceps,
            name = "exercise name",
//            index = it
        )
    }
}


private fun getDailyReport(): DailyReportDomainEntity {
    val localDate = LocalDate.of(2024, 1, 1)
    val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())

    return DailyReportDomainEntity(
        performedWorkout = true,
        hadCheatMeal = false,
        hadCreatine = true,
        litersOfWater = "2.5",
        gymNotes = "",
        musclesTrained = listOf(Muscle.Legs.name),
        sleepQuality = "4",
        proteinGrams = "120",
        cardioMinutes = "30",
        date = date,
        meal = "",
        cardio = ""
    )
}
//8 Pro API 35 is already running. If that is not the case, delete /home/nondas/.android/avd/Pixel_8_Pro_API_35.avd/*.lock and try again