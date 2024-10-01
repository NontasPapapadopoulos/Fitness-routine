package com.example.fitness_routine.presentation.screen.report

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.presentation.component.BackButton
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.util.getCurrentDate
import java.util.Date

@Composable
fun ReportScreen(
    viewModel: ReportViewModel = hiltViewModel(),
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
        is ReportState.Content -> {
            Content(
                content = state,
                navigateBack = navigateBack
            )
        }

        ReportState.Idle -> { LoadingBox() }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    content: ReportState.Content,
    navigateBack: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(text = "Fitness Diary")

                        Text(text = getCurrentDate())
                    }
                },
                navigationIcon = { BackButton(navigateBack) }
            )
        },

    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            
            CheckBoxQuestion(text = "Workout: ", isChecked = content.performedWorkout, onCheckedChange = {})


            Input(
                label = "Protein grams: ",
                value = content.proteinGrams,
                onValueChange = {}
            )

            Input(
                label = "Liters of water: ",
                value = content.litersOfWater,
                onValueChange = {}
            )

            Input(
                label = "Cardio minutes: ",
                value = content.cardioMinutes,
                onValueChange = {}
            )


            SleepQuality(level = content.sleepQuality.toInt(), onLevelChange = {})

            CheckBoxQuestion(text = "Creatine: ", isChecked = content.hadCreatine, onCheckedChange = {})

            MusclesTrained()

            GymNotes(
                notes = content.notes,
                onValueChange = {}
            )

            CheckBoxQuestion(text = "Cheat Meal: ", isChecked = content.hadCheatMeal, onCheckedChange = {})


        }
    }



}


@Composable
private fun SleepQuality(
    level: Int,
    onLevelChange: (Int) -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Sleep quality: ",
            modifier = Modifier.weight(1f)
        )

        Stars(
            level = level,
            onLevelChange = onLevelChange,
            modifier = Modifier.weight(1f)
        )

    }



}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MusclesTrained() {

    val trainedMuscles = listOf(Muscle.Chest, Muscle.Biceps)

    FlowRow(
    ) {
        Muscle.entries.forEach {
            Muscle(
                muscle = it,
                isSelected = trainedMuscles.contains(it),
                select = {}
            )
        }
    }


}

@Composable
private fun Muscle(
    muscle: Muscle,
    isSelected: Boolean,
    select: () -> Unit
) {

    val color = if (isSelected) Color.Red else Color.Black

    Text(
        text = muscle.name,
        modifier = Modifier
            .padding(3.dp)
            .clickable { select() }
            .border(BorderStroke(1.dp, color = color), shape = CircleShape)
            .padding(horizontal = 10.dp, vertical = 5.dp)

    )

}


@Composable
private fun GymNotes(
    notes: String,
    onValueChange: (String) -> Unit
) {
    Column {
        Text(text = "Gym notes: ")

        TextField(
            value = notes,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

    }

}

@Composable
private fun Stars(
    level: Int,
    onLevelChange: (Int) -> Unit,
    modifier: Modifier
) {
    Row(modifier = modifier) {
        (1..5).forEach {
            val color = if ( it <= level) Color.Yellow else Color.Black

            Star(
                sequence = it,
                color = color,
                onLevelChange = onLevelChange
            )
        }
    }
}


@Composable
private fun Star(
    sequence: Int,
    color: Color,
    onLevelChange: (Int) -> Unit
) {
    Icon(
        Icons.Default.Star,
        contentDescription = null,
        tint = color,
        modifier = Modifier.clickable { onLevelChange(sequence) }
    )
}

@Composable
private fun Input(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f)
        )

        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f)
        )
    }

}


@Composable
private fun CheckBoxQuestion(
    text: String,
    isChecked: Boolean,
    onCheckedChange:  (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text)

        Checkbox(checked = isChecked, onCheckedChange = onCheckedChange)
    }
}




@Composable
@Preview
private fun ReportPreview() {
    Content(
        content = ReportState.Content(
            date = getCurrentDate(),
            notes = "WoW",
            proteinGrams = "140",
            sleepQuality = "2",
            performedWorkout = false,
            hadCreatine = false,
            hadCheatMeal = false,
            trainedMuscles = "",
            litersOfWater = "2.5",
            cardioMinutes = "30",
            dailyReport = DailyReportDomainEntity(
                gymNotes = "WoW",
                proteinGrams = "140",
                sleepQuality = "3",
                performedWorkout = false,
                hadCreatine = false,
                hadCheatMeal = false,
                musclesTrained = listOf(),
                litersOfWater = "2.5",
                cardioMinutes = "30",
                date = Date()
            )
        ),
        navigateBack = {}
    )
}