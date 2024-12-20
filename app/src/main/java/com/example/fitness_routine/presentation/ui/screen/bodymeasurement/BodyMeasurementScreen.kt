package com.example.fitness_routine.presentation.ui.screen.bodymeasurement

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import com.example.fitness_routine.presentation.component.BackButton
import com.example.fitness_routine.presentation.component.Input
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.ui.screen.bodymeasurement.MeasurementsScreenConstants.Companion.BMI_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.screen.bodymeasurement.MeasurementsScreenConstants.Companion.BMR_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.screen.bodymeasurement.MeasurementsScreenConstants.Companion.FAT_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.screen.bodymeasurement.MeasurementsScreenConstants.Companion.METABOLIC_AGE_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.screen.bodymeasurement.MeasurementsScreenConstants.Companion.MUSCLE_MASS_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.screen.bodymeasurement.MeasurementsScreenConstants.Companion.TBW_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.screen.bodymeasurement.MeasurementsScreenConstants.Companion.VISCERAL_FAT_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.screen.bodymeasurement.MeasurementsScreenConstants.Companion.WEIGHT_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.theme.AppTheme
import com.example.fitness_routine.presentation.ui.theme.contentSpacing1
import com.example.fitness_routine.presentation.ui.theme.contentSpacing6
import com.example.fitness_routine.presentation.util.getCurrentDate
import com.example.fitness_routine.presentation.util.toFormattedDate


@Composable
fun BodyMeasurementScreen(
    navigateBack: () -> Unit,
    viewModel: BodyMeasurementViewModel = hiltViewModel()
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
        MeasurementState.Idle -> { LoadingBox() }
        is MeasurementState.Content -> {
            BodyMeasurementContent(
                content = state,
                navigateBack = navigateBack
            )
        }
    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BodyMeasurementContent(
    content: MeasurementState.Content,
    navigateBack: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Date: ${content.bodyMeasurement.date.toFormattedDate()}")
                    }
                },
                navigationIcon = { BackButton(navigateBack) }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(contentSpacing6)
        ) {

            Input(
                label = "Weight",
                unit = "kg",
                value = content.bodyMeasurement.weight.toString(),
                onValueChange = {},
                testTag = WEIGHT_TEXT_FIELD,
                modifier = Modifier.padding(vertical = contentSpacing1)
            )

            Input(
                label = "Fat",
                unit = "%",
                value = content.bodyMeasurement.fat.toString(),
                onValueChange = {},
                testTag = FAT_TEXT_FIELD,
                modifier = Modifier.padding(vertical = contentSpacing1)
            )

            Input(
                label = "Muscle Mass",
                unit = "kg",
                value = content.bodyMeasurement.muscleMass.toString(),
                onValueChange = {},
                testTag = MUSCLE_MASS_TEXT_FIELD,
                modifier = Modifier.padding(vertical = contentSpacing1)
            )

            Input(
                label = "BMI",
                unit = "%",
                value = content.bodyMeasurement.bmi.toString(),
                onValueChange = {},
                testTag = BMI_TEXT_FIELD,
                modifier = Modifier.padding(vertical = contentSpacing1)
            )

            Input(
                label = "TBW",
                unit = "",
                value = content.bodyMeasurement.tbw.toString(),
                onValueChange = {},
                testTag = TBW_TEXT_FIELD,
                modifier = Modifier.padding(vertical = contentSpacing1)
            )

            Input(
                label = "BMR",
                unit = "",
                value = content.bodyMeasurement.bmr.toString(),
                onValueChange = {},
                testTag = BMR_TEXT_FIELD,
                modifier = Modifier.padding(vertical = contentSpacing1)
            )

            Input(
                label = "Visceral Fat",
                unit = "",
                value = content.bodyMeasurement.visceralFat.toString(),
                onValueChange = {},
                testTag = VISCERAL_FAT_TEXT_FIELD,
                modifier = Modifier.padding(vertical = contentSpacing1)
            )

            Input(
                label = "Metabolic Age",
                unit = "",
                value = content.bodyMeasurement.metabolicAge.toString(),
                onValueChange = {},
                testTag = METABOLIC_AGE_TEXT_FIELD,
                modifier = Modifier.padding(vertical = contentSpacing1)
            )

        }

    }
}


@Composable
@Preview
private fun MeasurementsContentPreview() {
    AppTheme {
        BodyMeasurementContent(
            content = MeasurementState.Content(
                bodyMeasurement = BodyMeasurementDomainEntity(
                    id = "",
                    date = getCurrentDate(),
                    weight = 80.0F,
                    fat = 12F,
                    muscleMass = 64F,
                    bmi = 12F,
                    tbw = 12F,
                    bmr = 10F,
                    visceralFat = 3,
                    metabolicAge = 15
                ),
            ),
            navigateBack = {}
        )
    }
}


class MeasurementsScreenConstants private constructor() {
    companion object {
        const val WEIGHT_TEXT_FIELD = "weight_text_field"
        const val FAT_TEXT_FIELD = "far_text_field"
        const val MUSCLE_MASS_TEXT_FIELD = "muscle_mass_text_field"
        const val BMI_TEXT_FIELD = "bmi_text_field"
        const val TBW_TEXT_FIELD = "tbw_text_field"
        const val BMR_TEXT_FIELD = "bmr_text_field"
        const val VISCERAL_FAT_TEXT_FIELD = "bmr_mass_text_field"
        const val METABOLIC_AGE_TEXT_FIELD = "metabolic_age_mass_text_field"
    }
}