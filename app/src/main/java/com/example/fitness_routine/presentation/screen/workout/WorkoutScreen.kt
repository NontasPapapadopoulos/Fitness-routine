package com.example.fitness_routine.presentation.screen.workout

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.ExerciseDomainEntity
import com.example.fitness_routine.domain.entity.SetDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.presentation.component.BackButton
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.component.MusclesTrained
import com.example.fitness_routine.presentation.util.toFormattedDate
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date


@Composable
fun WorkoutScreen(
    viewModel: WorkoutViewModel = hiltViewModel(),
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

    when(val state = uiState) {
        is WorkoutState.Content -> WorkoutContent(
            content = state,
            navigateBack = navigateBack,
            onAddSet = { muscle, exercise -> viewModel.add(WorkoutEvent.AddNewSet(muscle, exercise)) },
            onDeleteSet = { viewModel.add(WorkoutEvent.DeleteSet(it)) },
            onSelectMuscle = { viewModel.add(WorkoutEvent.SelectMuscle(it)) }
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
    onSelectMuscle: (Muscle) -> Unit,
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
                        Text(text = "Workout")
                        Text(text = content.date.toFormattedDate())

                    }
                },
                navigationIcon = { BackButton(navigateBack) }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .padding(horizontal = 24.dp)
        ) {

            MusclesTrained(
                selectedMuscles = content.musclesTrained.map { it.name },
                onSelectMuscle = { onSelectMuscle(Muscle.valueOf(it)) }
            )


            content.musclesTrained.forEach { muscle ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = muscle.name)

                    AddBreak(addBreak = {})

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
                            delete = { onDeleteSet(set) }
                        )

                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AddExercise(addExercise = {})

                        AddSet(addSet = { onAddSet(muscle, exercise) })

                    }
                }

                if (setsByExercise.isEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        AddExercise(addExercise = {})

                    }
                }

            }
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

        IconButton(
            onClick = {}
        ) {
            Icon(Icons.Outlined.Timer, contentDescription = null)
        }
    }
}


@Composable
private fun Set(
    set: SetDomainEntity,
    index: Int,
    delete: (SetDomainEntity) -> Unit
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
            value = set.weight.toString(),
            onValueChange = {},
            singleLine = true,
            label = { Text(text = "Weight") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.weight(0.5f)
        )

        Spacer(modifier = Modifier.width(10.dp))

        OutlinedTextField(
            value = set.repeats.toString(),
            onValueChange = {},
            singleLine = true,
            label = { Text(text = "Repeats") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.weight(0.5f)
        )

        IconButton(onClick = { delete(set) } ) {
            Icon(Icons.Default.Delete, contentDescription = null)
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



        Text(text = "Add new Set")
        IconButton(
            onClick = addSet
        ) {
            Icon(Icons.Default.AddCircleOutline, contentDescription = null)
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



        Text(text = "Add Exercise")
        IconButton(
            onClick = addExercise
        ) {
            Icon(Icons.Default.AddCircleOutline, contentDescription = null)
        }
    }
}


@Preview
@Composable
private fun SetPreview() {
    val setDomainEntity = SetDomainEntity(
        id = 0,
        workoutDate = 1L,
        muscle = Muscle.Biceps,
        exercise = "Excercise",
        weight = 20,
        repeats = 8
    )
    
    Set(set = setDomainEntity, delete = {}, index = 1)
}


@Composable
private fun Break(
    time: String
) {

    Row {
        Text(text = "$time seconds")
    }

}


@Preview
@Composable
private fun WorkoutContentPreview() {
    WorkoutContent(
        content = WorkoutState.Content(
            sets = generateSets(),
            exercises = generateExercises(),
            date = 1728939600000,
            musclesTrained = listOf(Muscle.Biceps, Muscle.Chest),
            dailyReport = getDailyReport()
        ),
        navigateBack = {},
        onAddSet = {_, _ -> },
        onDeleteSet = {},
        onSelectMuscle = {}
    )

}


private fun generateSets(): List<SetDomainEntity> {
    return  (0..12).map {
        SetDomainEntity(
            id = it.toLong(),
            workoutDate = 100000L,
            muscle = if (it < 6 ) Muscle.Chest else Muscle.Biceps,
            exercise = if (it % 2 == 0) "Exercise 1" else "Exercise 2",
            weight = 30,
            repeats = 12
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
        date = date
    )
}
