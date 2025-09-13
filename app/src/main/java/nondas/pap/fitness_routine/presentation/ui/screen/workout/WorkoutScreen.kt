package nondas.pap.fitness_routine.presentation.ui.screen.workout

import android.annotation.SuppressLint
import android.media.MediaPlayer
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
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import nondas.pap.fitness_routine.R
import nondas.pap.fitness_routine.domain.entity.DailyReportDomainEntity
import nondas.pap.fitness_routine.domain.entity.ExerciseDomainEntity
import nondas.pap.fitness_routine.domain.entity.SetDomainEntity
import nondas.pap.fitness_routine.domain.entity.enums.Muscle
import nondas.pap.fitness_routine.presentation.component.BackButton
import nondas.pap.fitness_routine.presentation.component.BottomBar
import nondas.pap.fitness_routine.presentation.component.LoadingBox
import nondas.pap.fitness_routine.presentation.component.MusclesTrained
import nondas.pap.fitness_routine.presentation.ui.theme.AppTheme
import nondas.pap.fitness_routine.presentation.ui.theme.contentSpacing4
import nondas.pap.fitness_routine.presentation.util.asTextFieldValue
import nondas.pap.fitness_routine.presentation.util.isCurrentDate
import nondas.pap.fitness_routine.presentation.util.toFormattedDate
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import nondas.pap.fitness_routine.presentation.navigation.Workout
import nondas.pap.fitness_routine.presentation.ui.screen.workout.WorkoutScreenConstants.Companion.EXERCISE_DIALOG
import nondas.pap.fitness_routine.presentation.util.getCurrentDate
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date


@Composable
fun WorkoutScreen(
    viewModel: WorkoutViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    onNavigateToExercises: (Muscle) -> Unit,
    onNavigateToScreen: (NavKey) -> Unit
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
    onNavigateToScreen: (NavKey) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = contentSpacing4),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column {
                            Text(text= "Workout")
                            Text(text = content.date.toFormattedDate())
                        }

                        if (content.date.isCurrentDate())
                            Break(
                                addBreak = { onShowDialog(Dialog.Break) },
                                testTag = WorkoutScreenConstants.BREAK_BUTTON
                            )

                    }
                },
                navigationIcon = { BackButton(navigateBack) }
            )
        },
        bottomBar = {
            if (content.date.isCurrentDate()) {
                BottomBar(
                    onClick = { onNavigateToScreen(it) },
                    currentScreen = Workout(content.date)
                )
            }
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
                .padding(contentSpacing4)
        ) {

            MusclesTrained(
                selectedMuscles = content.musclesTrained,
                onSelectMuscle = { onSelectMuscle(it) },
                testTag = WorkoutScreenConstants.MUSCLE
            )


            content.musclesTrained.forEachIndexed { muscleIndex, muscle ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = muscle.name,
                        modifier = Modifier.testTag(WorkoutScreenConstants.MUSCLE_TEXT+muscle)
                    )

                    AddExercise(
                        addExercise = { onShowDialog(Dialog.AddExercise(muscle)) },
                        testTag = WorkoutScreenConstants.ADD_EXERCISE + muscle
                    )

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

                    val isLastExercise = content.musclesTrained.size - 1 == muscleIndex
                            && setsByExercise[exercise] == setsByExercise.entries.last()

                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(bottom = if (isLastExercise) contentSpacing4 else 0.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,

                    ) {

                        AddSet(
                            addSet = { onAddSet(muscle, exercise) },
                            testTag = WorkoutScreenConstants.ADD_SET+muscle
                        )
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
                        onNavigateToExercises =  { onNavigateToExercises(it) },
                        testTag = EXERCISE_DIALOG
                    )
                }

                is Dialog.Break -> {
                    BreakDialog(
                        breakDuration = content.breakTimeDuration,
                        onDismissDialog = onDismissDialog,
                        testTag = WorkoutScreenConstants.BREAK_DIALOG
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
    onNavigateToExercises: (Muscle) -> Unit,
    testTag: String
) {

    LaunchedEffect(Unit) {
        if (exercises.isEmpty())
            onNavigateToExercises(selectedMuscle)
    }


    if (exercises.isNotEmpty()) {
        var expanded by remember { mutableStateOf(false) }
        var selectedOption by remember { mutableStateOf(exercises[exercises.size-1].name) }

        AlertDialog(
            modifier = Modifier.testTag(testTag),
            onDismissRequest = onDismissDialog,
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
                        OutlinedTextField(
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
                        Text(text = "More")
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
    onDismissDialog: () -> Unit,
    testTag: String
) {
    val context = LocalContext.current
    val mediaPlayer = remember {
        MediaPlayer.create(context, R.raw.bell_ring)
    }

    var isRunning by rememberSaveable { mutableStateOf(false) }
    var remainingSeconds by rememberSaveable { mutableStateOf(breakDuration.toInt()) }

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
        modifier = Modifier.testTag(testTag),
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
private fun Break(
    addBreak: () -> Unit,
    testTag: String
) {
    Row(
        modifier = Modifier.clickable { addBreak() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Break")
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            Icons.Outlined.Timer,
            contentDescription = null,
            modifier = Modifier.testTag(testTag)
        )
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
            onValueChange = { if (it.text.isDigitsOnly()) update(set, SetField.Weight, it.text) },
            singleLine = true,
            label = { Text(text = "Weight") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.weight(0.5f)
                .testTag(WorkoutScreenConstants.WEIGHT_TEXT_FIELD+set.id)
        )

        Spacer(modifier = Modifier.width(10.dp))

        OutlinedTextField(
            value = set.repeats.asTextFieldValue(),
            onValueChange = { if (it.text.isDigitsOnly()) update(set, SetField.Repeat, it.text) },
            singleLine = true,
            label = { Text(text = "Repeats") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.weight(0.5f)
                .testTag(WorkoutScreenConstants.REPEATS_TEXT_FIELD+set.id)
        )


        IconButton(
            onClick = { delete(set) } ,
            modifier = Modifier.testTag(WorkoutScreenConstants.DELETE_BUTTON+set.muscle+set.id)
        ) {
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
    testTag: String
) {

    Row(
        modifier = Modifier.clickable { addSet() }
            .testTag(testTag),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Add Set",
            color = MaterialTheme.colorScheme.primary
        )

        Icon(
            Icons.Default.AddCircleOutline,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null,
            modifier = Modifier.padding(contentSpacing4)
        )

    }
}


@Composable
private fun AddExercise(
    addExercise: () -> Unit,
    testTag: String
) {

    Row(
        modifier = Modifier.clickable { addExercise() }
            .testTag(testTag),
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
        date = 1L,
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
                date = getCurrentDate(),
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


//@Preview
//@Composable
//private fun BreakDialogPreview() {
//    BreakDialog(
//        onDismissDialog = {},
//        breakDuration = "60"
//    )
//}


@Preview
@Composable
private fun AddExerciseDialogPreview() {
    AppTheme(darkTheme = true) {
        AddExerciseDialog(
            onDismissDialog = {},
            exercises = generateExercises(),
            selectedMuscle = Muscle.Chest,
            onAddExercise = {_, _ ->},
            onNavigateToExercises = {},
            testTag = ""
        )
    }

}


private fun generateSets(): List<SetDomainEntity> {
    return  (0..6).map {
        SetDomainEntity(
            id = "",
            date = 100000L,
            muscle = if (it > 4 ) Muscle.Chest else Muscle.Biceps,
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
        musclesTrained = listOf(Muscle.Legs),
        sleepQuality = "4",
        proteinGrams = "120",
        date = date,
    )
}


class WorkoutScreenConstants private constructor() {
    companion object {
        const val MUSCLE = "muscle_"
        const val BREAK_BUTTON = "break_button"
        const val BREAK_DIALOG = "break_dialog"
        const val ADD_EXERCISE = "add_exercise_"
        const val ADD_EXERCISE_DIALOG = "add_exercise_dialog"
        const val EXERCISE_DIALOG = "exercise_dialog"
        const val ADD_SET = "add_set_"
        const val DELETE_BUTTON = "delete_button_"
        const val REPEATS_TEXT_FIELD = "repeats_text_field_"
        const val WEIGHT_TEXT_FIELD = "weight_text_field_"
        const val MUSCLE_TEXT = "muscle_text_"
    }
}