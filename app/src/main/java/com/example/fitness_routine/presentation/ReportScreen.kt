package com.example.fitness_routine.presentation

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitness_routine.domain.Muscle


@Composable
fun ReportScreen() {

    Scaffold {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            
            Text(text = "Current day.... ")

            CheckBoxQuestion(text = "Workout: ", isChecked = true, onCheckedChange = {})


            Input(
                label = "Protein grams: ",
                value = "230",
                onValueChange = {}
            )

            Input(
                label = "Liters of water: ",
                value = "3",
                onValueChange = {}
            )

            Input(
                label = "Cardio minutes: ",
                value = "35",
                onValueChange = {}
            )


            SleepQuality(level = 3, onLevelChange = {})

            CheckBoxQuestion(text = "Creatine: ", isChecked = true, onCheckedChange = {})

            MusclesTrained()

            GymNotes(
                notes = "",
                onValueChange = {}
            )

            CheckBoxQuestion(text = "Cheat Meal: ", isChecked = true, onCheckedChange = {})


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
        modifier = Modifier.clickable {  }
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
private fun WaterIntake(
    liters: String,
    onValueChange: (String) -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Liters of water:",
            modifier = Modifier.weight(1f)
            )

        TextField(
            value = liters,
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
    ReportScreen()
}