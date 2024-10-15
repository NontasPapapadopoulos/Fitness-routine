package com.example.fitness_routine.presentation.screen.exercise

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.domain.interactor.exercise.AddExercise
import com.example.fitness_routine.presentation.component.BackButton
import com.example.fitness_routine.presentation.component.LoadingBox

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
                navigateBack = navigateBack,
                addExercise = { viewModel.add(ExerciseEvent.Add(it)) },
                deleteExercise = { viewModel.add(ExerciseEvent.Delete(it)) },
                textChanged = { viewModel.add(ExerciseEvent.TextChanged(it)) }
            )
        }
        ExerciseState.Idle -> { LoadingBox() }
    }

}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExerciseContent(
    content: ExerciseState.Content,
    navigateBack: () -> Unit,
    addExercise: (Muscle) -> Unit,
    deleteExercise: (ExerciseDomainEntity) -> Unit,
    textChanged: (String) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(text = "Workout")

                    }
                },
                navigationIcon = { BackButton(navigateBack) }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            val muscles = Muscle.entries
            var expanded by remember { mutableStateOf(true) }
            var selectedOption by remember { mutableStateOf(muscles[0].name) }


            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedOption,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Dropdown") },
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
                            }
                        )
                    }
                }
            }

            val muscleExercises = content.exercises.filter { it.muscle.name == selectedOption }

            muscleExercises.forEach {
                Exercise(exercise = it, delete = { deleteExercise(it) })
            }


            OutlinedTextField(
                value = content.newExercise,
                onValueChange = { textChanged(it) }
            )

            Button(
                onClick = { addExercise(Muscle.valueOf(selectedOption)) }
            ) {
                Text(text = "Add Exercise")
            }

        }

    }
}

@Composable
private fun Exercise(
    exercise: ExerciseDomainEntity,
    delete: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = exercise.name)

        IconButton(onClick = delete) {
            Icon(Icons.Default.Delete, contentDescription = null)
        }
    }

}


@Preview
@Composable
private fun ExerciseContentPreview() {
    ExerciseContent(
        content = ExerciseState.Content(
            exercises = generateExercises(),
            newExercise = "bench press"
        ),
        navigateBack = {},
        addExercise = {},
        deleteExercise = {},
        textChanged = {}
    )
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