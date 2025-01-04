package com.example.fitness_routine.presentation.ui.screen.report

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitness_routine.domain.entity.CheatMealDomainEntity
import com.example.fitness_routine.domain.entity.DailyReportDomainEntity
import com.example.fitness_routine.domain.entity.enums.Choice
import com.example.fitness_routine.presentation.component.Input
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.CHEAT_MEAL_CHECK_BOX
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.CHEAT_MEAL_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.CREATINE_CHECK_BOX
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.PROTEIN_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.SLEEP_QUALITY
import com.example.fitness_routine.presentation.ui.screen.report.ReportScreenConstants.Companion.WATER_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.theme.AppTheme
import com.example.fitness_routine.presentation.ui.theme.contentSpacing1
import com.example.fitness_routine.presentation.ui.theme.contentSpacing2
import com.example.fitness_routine.presentation.util.asTextFieldValue
import com.example.fitness_routine.presentation.util.getIcon
import java.util.Date


@Composable
fun NutritionTab(
    dailyReport: DailyReportDomainEntity,
    cheatMeals: List<CheatMealDomainEntity>,
    onUpdateCheckField: (Boolean, CheckBoxField) -> Unit,
    onUpdateTextField: (String, Field) -> Unit,
    onUpdateMeal: (CheatMealDomainEntity, String) -> Unit,
    onDeleteCheatMeal: (CheatMealDomainEntity) -> Unit,
    onAddCheatMeal: () -> Unit,
) {

    Column {
        Spacer(modifier = Modifier.height(contentSpacing2))

        SleepQuality(
            level = if (dailyReport.sleepQuality.isNotEmpty()) dailyReport.sleepQuality.toInt() else 0,
            onLevelChange = { onUpdateTextField(it.toString(), Field.SleepQuality) }
        )

        Spacer(modifier = Modifier.height(contentSpacing2))


        Input(
            label = "Protein: ",
            unit = "gr",
            value = dailyReport.proteinGrams,
            onValueChange = { onUpdateTextField(it, Field.ProteinGrams) },
            testTag = PROTEIN_TEXT_FIELD,
        )

        Spacer(modifier = Modifier.height(contentSpacing2))


        Input(
            label = "Water: ",
            unit = "Liters",
            value = dailyReport.litersOfWater,
            onValueChange = { onUpdateTextField(it, Field.LitersOfWater) },
            testTag = WATER_TEXT_FIELD
        )

        Spacer(modifier = Modifier.height(contentSpacing2))

        ChoiceCheckBoxItem(
            option = Choice.Creatine,
            isChecked = dailyReport.hadCreatine,
            onCheckedChange = { onUpdateCheckField(it, CheckBoxField.Creatine) },
            testTag = CREATINE_CHECK_BOX,
            icon = Choice.Creatine.getIcon()
        )

        Spacer(modifier = Modifier.height(contentSpacing2))

        ChoiceCheckBoxItem(
            option = Choice.Cheat,
            isChecked = dailyReport.hadCheatMeal,
            onCheckedChange = { onUpdateCheckField(it, CheckBoxField.CheatMeal) },
            testTag = CHEAT_MEAL_CHECK_BOX,
            icon = Choice.Cheat.getIcon()
        )


        Spacer(modifier = Modifier.height(contentSpacing2))

        //if (dailyReport.hadCheatMeal) {
            cheatMeals.forEach {
                CheatMeal(
                    meal = it,
                    onUpdateMeal = { meal, value -> onUpdateMeal(meal, value) },
                    onAddCheatMeal = onAddCheatMeal,
                    onDeleteCheatMeal = { onDeleteCheatMeal(it) }
                )
            }

     //   }

    }

}



@Composable
private fun CheatMeal(
    meal: CheatMealDomainEntity,
    onUpdateMeal: (CheatMealDomainEntity, String) -> Unit,
    onAddCheatMeal: () -> Unit,
    onDeleteCheatMeal: (CheatMealDomainEntity) -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = contentSpacing1)
    ) {
        val value = meal.meal.asTextFieldValue()

        OutlinedTextField(
            value = value,
            onValueChange = { textFieldValue ->
                onUpdateMeal(meal, textFieldValue.text)
            },
            singleLine = true,
            label = { Text(text = "Meal") },
            modifier = Modifier
                .weight(0.8f)
                .testTag(CHEAT_MEAL_TEXT_FIELD)
        )


        Row(modifier = Modifier.weight(0.2f)) {
            IconButton(onClick = { onAddCheatMeal() }) {
                Icon(
                    imageVector = Icons.Default.AddCircleOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }

            IconButton(onClick = { onDeleteCheatMeal(meal) } ) {
                Icon(
                    imageVector = Icons.Default.RemoveCircleOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
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
            val color = if ( it <= level) Color.Yellow else Color.LightGray

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
private fun MeasurementsButton() {

}


@Preview
@Composable
private fun NutritionTabPreview() {
    AppTheme {
        NutritionTab(
            dailyReport = DailyReportDomainEntity(
            proteinGrams = "140",
            sleepQuality = "3",
            performedWorkout = true,
            hadCreatine = false,
            hadCheatMeal = true,
            musclesTrained = listOf(),
            litersOfWater = "2.5",
            date = Date(),
        ),
            onUpdateTextField = { _, _ -> },
            onUpdateCheckField = { _, _ -> },
            onUpdateMeal = { _, _, -> },
            onAddCheatMeal = {},
            onDeleteCheatMeal = {},
            cheatMeals = generateCheatMeals()
        )
    }
}


fun generateCheatMeals() =
    (1..3).map {
        CheatMealDomainEntity(
            id = "",
            date = Date(),
            meal = "Burger"
        )
    }
