package com.example.fitness_routine.presentation.ui.screen.exercise

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.presentation.component.BackButton
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.ui.screen.exercise.ExerciseScreenConstants.Companion.ADD_EXERCISE_BUTTON
import com.example.fitness_routine.presentation.ui.screen.exercise.ExerciseScreenConstants.Companion.DELETE_EXERCISE
import com.example.fitness_routine.presentation.ui.screen.exercise.ExerciseScreenConstants.Companion.EDIT_EXERCISE
import com.example.fitness_routine.presentation.ui.screen.exercise.ExerciseScreenConstants.Companion.EXERCISE_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.screen.exercise.ExerciseScreenConstants.Companion.MUSCLE_GROUP_DROPDOWN
import com.example.fitness_routine.presentation.ui.screen.exercise.ExerciseScreenConstants.Companion.MUSCLE_GROUP_DROPDOWN_ITEM
import com.example.fitness_routine.presentation.ui.theme.AppTheme
import com.example.fitness_routine.presentation.ui.theme.contentSpacing3
import com.example.fitness_routine.presentation.ui.theme.contentSpacing6

@Composable
fun ExerciseScreen(
    navigateBack: () -> Unit,
    viewModel: ExerciseViewModel = hiltViewModel(),
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


    when(val state = uiState) {
        is ExerciseState.Content -> {
            ExerciseContent(
                content = state,
                onNavigateBack = navigateBack,
                onAddExercise = { viewModel.add(ExerciseEvent.Add(it)) },
                onDeleteExercise = { viewModel.add(ExerciseEvent.Delete(it)) },
                onTextChanged = { viewModel.add(ExerciseEvent.TextChanged(it)) },
                onSelectExercise = { viewModel.add(ExerciseEvent.SelectExercise(it)) },
                onUpdateExercise = { viewModel.add(ExerciseEvent.UpdateExercise) },
                onNewExerciseNameTextChanged = { viewModel.add(ExerciseEvent.NewExerciseNameTextChanged(it)) },
                onDismissDialog = { viewModel.add(ExerciseEvent.DismissDialog) }
            )
        }
        ExerciseState.Idle -> { LoadingBox() }
    }

}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExerciseContent(
    content: ExerciseState.Content,
    onNavigateBack: () -> Unit,
    onAddExercise: (Muscle) -> Unit,
    onDeleteExercise: (ExerciseDomainEntity) -> Unit,
    onTextChanged: (String) -> Unit,
    onSelectExercise: (ExerciseDomainEntity) -> Unit,
    onUpdateExercise: () -> Unit,
    onNewExerciseNameTextChanged: (String) -> Unit,
    onDismissDialog: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(text = "Exercises")

                    }
                },
                navigationIcon = { BackButton(onNavigateBack) }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val muscles = Muscle.entries
            var expanded by remember { mutableStateOf(false) }
            var selectedOption by remember {
                mutableStateOf(content.preSelectedMuscle?.name ?:muscles[0].name)
            }


            val focusRequester = remember {
                FocusRequester()
            }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.testTag(MUSCLE_GROUP_DROPDOWN)
            ) {
                OutlinedTextField(
                    value = selectedOption,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Muscle group") },
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
                    muscles.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.name) },
                            onClick = {
                                selectedOption = option.name
                                expanded = false
                            },
                            modifier = Modifier.testTag(MUSCLE_GROUP_DROPDOWN_ITEM + option.name)
                        )
                    }
                }
            }

            val muscleExercises = content.exercises.filter { it.muscle.name == selectedOption }

            muscleExercises.forEach {
                Exercise(
                    exercise = it,
                    delete = { onDeleteExercise(it) },
                    onSelectExercise = onSelectExercise
                )
            }

            Spacer(modifier = Modifier.height(contentSpacing6))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = content.newExercise,
                    onValueChange = { onTextChanged(it) },
                    modifier = Modifier
                        .weight(0.7f)
                        .testTag(EXERCISE_TEXT_FIELD)
                        .focusRequester(focusRequester),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(contentSpacing3))

                Button(
                    onClick = { onAddExercise(Muscle.valueOf(selectedOption)) },
                    enabled = content.newExercise.isNotEmpty(),
                    modifier = Modifier
                        .weight(0.3f)
                        .testTag(ADD_EXERCISE_BUTTON)
                ) {
                    Text(text = "Add")
                }
            }


            Spacer(modifier = Modifier.weight(1f))


            if (content.selectedExercise != null) {
                EditExerciseDialog(
                    selectedExercise = content.selectedExercise.name,
                    onUpdateExercise = onUpdateExercise,
                    onNewExerciseNameTextChanged = onNewExerciseNameTextChanged,
                    onDismissDialog = onDismissDialog,
                    newName = content.newName
                )
            }

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        }

    }
}

@Composable
private fun EditExerciseDialog(
    selectedExercise: String,
    newName: String,
    onUpdateExercise: () -> Unit,
    onNewExerciseNameTextChanged: (String) -> Unit,
    onDismissDialog: () -> Unit
) {
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

                Column {
                    Text(
                        text = "Rename $selectedExercise: ",
                        style = MaterialTheme.typography.bodyLarge,
                    )


                    OutlinedTextField(
                        value = newName,
                        onValueChange = { onNewExerciseNameTextChanged(it) },
                        label = { Text(text = "New exercise name") },
                        singleLine = true,

                    )
                }


            }
        },
        confirmButton = {
            Button(
                onClick = onUpdateExercise
            ) {
                Text("Rename")
            }
        },
        dismissButton = {
            Button(onClick = onDismissDialog) {
                Text(text = "Cancel")
            }
        },
        properties = DialogProperties(dismissOnClickOutside = true)
    )
}

@Composable
private fun Exercise(
    exercise: ExerciseDomainEntity,
    delete: (ExerciseDomainEntity) -> Unit,
    onSelectExercise: (ExerciseDomainEntity) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = exercise.name,
            modifier = Modifier.weight(0.7f)
        )

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.weight(0.3f)
        ) {
            IconButton(
                onClick =  { onSelectExercise(exercise) },
                modifier = Modifier.testTag(EDIT_EXERCISE + exercise.name)) {
                Icon(Icons.Default.Edit, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
            }

            IconButton(
                onClick =  { delete(exercise) },
                modifier = Modifier.testTag(DELETE_EXERCISE + exercise.name)) {
                Icon(Icons.Default.RemoveCircleOutline, contentDescription = null, tint = MaterialTheme.colorScheme.onError)
            }
        }

    }

}


@Preview
@Composable
private fun ExerciseContentPreview() {
    AppTheme(darkTheme = true) {
        ExerciseContent(
            content = ExerciseState.Content(
                exercises = generateExercises(),
                newExercise = "bench press",
                preSelectedMuscle = Muscle.Chest,
                selectedExercise = null,
                newName = ""
            ),
            onNavigateBack = {},
            onAddExercise = {},
            onDeleteExercise = {},
            onTextChanged = {},
            onUpdateExercise = {},
            onSelectExercise = {},
            onNewExerciseNameTextChanged = {},
            onDismissDialog = {},
        )
    }

}

private fun generateExercises(): List<ExerciseDomainEntity> {
    return (0..10).map {

        ExerciseDomainEntity(
            muscle = if (it < 5) Muscle.Chest else Muscle.Biceps,
            name = "exercise name exercise name",
        )
    }
}

class ExerciseScreenConstants private constructor() {
    companion object {
        const val EXERCISE_TEXT_FIELD = "exercise_text_field"
        const val ADD_EXERCISE_BUTTON = "add_exercise_button"
        const val DELETE_EXERCISE = "delete_exercise"
        const val EDIT_EXERCISE = "edit_exercise"
        const val MUSCLE_GROUP_DROPDOWN = "muscle_group_drop_down"
        const val MUSCLE_GROUP_DROPDOWN_ITEM = "muscle_group_drop_down_item"
    }
}