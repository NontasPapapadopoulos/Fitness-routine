package com.example.fitness_routine.presentation.ui.screen.report

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.enums.Muscle
import com.example.fitness_routine.presentation.component.BackButton
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.component.MusclesTrained
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.CARDIO_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.CHEAT_MEAL_CHECK_BOX
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.CREATINE_CHECK_BOX
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.DELETE_BUTTON
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.GYM_NOTES_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.MUSCLE_ITEM
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.PROTEIN_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.SLEEP_QUALITY
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.WATER_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.WORKOUT_CHECK_BOX
import com.example.fitness_routine.presentation.util.toFormattedDate
import java.util.Date

@Composable
fun ReportScreen(
    viewModel: ReportViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToWorkout: (Long) -> Unit
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
        is ReportState.Content -> {
            Content(
                content = state,
                navigateBack = navigateBack,
                navigateToWorkout = { navigateToWorkout(it) },
                onUpdateCheckField = { isChecked, field -> viewModel.add(ReportEvent.UpdateCheckBox(isChecked = isChecked, checkBoxField = field)) },
                onUpdateTextField = { value, field ->  viewModel.add(ReportEvent.UpdateField(value = value, field = field)) },
                onSelectMuscle = { viewModel.add(ReportEvent.SelectMuscle(it)) }
            )
        }

        ReportState.Idle -> { LoadingBox() }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    content: ReportState.Content,
    navigateBack: () -> Unit,
    navigateToWorkout: (Long) -> Unit,
    onUpdateCheckField: (Boolean, CheckBoxField) -> Unit,
    onUpdateTextField: (String, Field) -> Unit,
    onSelectMuscle: (String) -> Unit
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

                        Text(text = content.date.toFormattedDate())
                        Icon(Icons.Default.Delete, contentDescription = null,
                            modifier = Modifier.testTag(DELETE_BUTTON))
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
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            
            CheckBoxQuestion(
                text = "Workout: ",
                isChecked = content.dailyReport.performedWorkout,
                onCheckedChange = { onUpdateCheckField(it, CheckBoxField.Workout) },
                testTag = WORKOUT_CHECK_BOX
            )

            CheckBoxQuestion(
                text = "Cheat Meal: ",
                isChecked = content.dailyReport.hadCheatMeal,
                onCheckedChange = { onUpdateCheckField(it, CheckBoxField.CheatMeal) },
                testTag = CHEAT_MEAL_CHECK_BOX
            )


            Input(
                label = "Protein grams: ",
                value = content.dailyReport.proteinGrams,
                onValueChange = { onUpdateTextField(it, Field.ProteinGrams) },
                testTag = PROTEIN_TEXT_FIELD
            )

            Input(
                label = "Liters of water: ",
                value = content.dailyReport.litersOfWater,
                onValueChange = { onUpdateTextField(it, Field.LitersOfWater) },
                testTag = WATER_TEXT_FIELD
            )

            Input(
                label = "Cardio minutes: ",
                value = content.dailyReport.cardioMinutes,
                onValueChange = { onUpdateTextField(it, Field.CardioMinutes) },
                testTag = CARDIO_TEXT_FIELD
            )


            SleepQuality(
                level = if (content.dailyReport.sleepQuality.isNotEmpty()) content.dailyReport.sleepQuality.toInt() else 0,
                onLevelChange = { onUpdateTextField(it.toString(), Field.SleepQuality) }
            )

            CheckBoxQuestion(
                text = "Creatine: ",
                isChecked = content.dailyReport.hadCreatine,
                onCheckedChange = { onUpdateCheckField(it, CheckBoxField.Creatine) },
                testTag = CREATINE_CHECK_BOX
            )

            MusclesTrained(
                selectedMuscles = content.dailyReport.musclesTrained,
                onSelectMuscle = { onSelectMuscle(it) },
                testTag = MUSCLE_ITEM
            )

            GymNotes(
                notes = content.dailyReport.gymNotes,
                onValueChange = { onUpdateTextField(it, Field.GymNotes) }
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = { navigateToWorkout(content.date) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(text = "Note workout details ")
            }

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
                .height(100.dp)
                .testTag(GYM_NOTES_TEXT_FIELD)
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
        modifier = Modifier
            .clickable { onLevelChange(sequence) }
            .testTag(SLEEP_QUALITY)
    )
}

@Composable
private fun Input(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    testTag: String
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
            modifier = Modifier
                .weight(1f)
                .testTag(testTag),
            singleLine = true,
            keyboardOptions = keyboardOptions
        )
    }

}


@Composable
private fun CheckBoxQuestion(
    text: String,
    isChecked: Boolean,
    onCheckedChange:  (Boolean) -> Unit,
    testTag: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text)

        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.testTag(testTag)
        )
    }
}




@Composable
@Preview
private fun ReportPreview() {
    Content(
        content = ReportState.Content(
            date = 1728939600000,
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
        navigateBack = {},
        onUpdateTextField = { _, _ -> },
        onUpdateCheckField = { _, _ -> },
        navigateToWorkout = {},
        onSelectMuscle = {},
    )
}

class ReportScreenConstants private constructor() {
    companion object {
        const val WORKOUT_CHECK_BOX = "workout_check_box"
        const val CHEAT_MEAL_CHECK_BOX = "cheat_meal_check_box"
        const val CREATINE_CHECK_BOX = "creatine_check_box"
        const val PROTEIN_TEXT_FIELD = "protein_text_field"
        const val WATER_TEXT_FIELD = "water_text_field"
        const val CARDIO_TEXT_FIELD = "cardio_text_field"
        const val MUSCLE_ITEM = "muscle_item_"
        const val SLEEP_QUALITY = "sleep_quality"
        const val GYM_NOTES_TEXT_FIELD = "gym_notes_text_field"
        const val DELETE_BUTTON = "delete_button"

    }
}