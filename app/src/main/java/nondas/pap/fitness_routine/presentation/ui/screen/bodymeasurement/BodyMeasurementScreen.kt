package nondas.pap.fitness_routine.presentation.ui.screen.bodymeasurement

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nondas.pap.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import nondas.pap.fitness_routine.presentation.component.BackButton
import nondas.pap.fitness_routine.presentation.component.Input
import nondas.pap.fitness_routine.presentation.component.LoadingBox
import nondas.pap.fitness_routine.presentation.ui.screen.bodymeasurement.MeasurementsScreenConstants.Companion.BMI_TEXT_FIELD
import nondas.pap.fitness_routine.presentation.ui.screen.bodymeasurement.MeasurementsScreenConstants.Companion.BMR_TEXT_FIELD
import nondas.pap.fitness_routine.presentation.ui.screen.bodymeasurement.MeasurementsScreenConstants.Companion.BUTTON
import nondas.pap.fitness_routine.presentation.ui.screen.bodymeasurement.MeasurementsScreenConstants.Companion.FAT_TEXT_FIELD
import nondas.pap.fitness_routine.presentation.ui.screen.bodymeasurement.MeasurementsScreenConstants.Companion.METABOLIC_AGE_TEXT_FIELD
import nondas.pap.fitness_routine.presentation.ui.screen.bodymeasurement.MeasurementsScreenConstants.Companion.MUSCLE_MASS_TEXT_FIELD
import nondas.pap.fitness_routine.presentation.ui.screen.bodymeasurement.MeasurementsScreenConstants.Companion.TBW_TEXT_FIELD
import nondas.pap.fitness_routine.presentation.ui.screen.bodymeasurement.MeasurementsScreenConstants.Companion.VISCERAL_FAT_TEXT_FIELD
import nondas.pap.fitness_routine.presentation.ui.screen.bodymeasurement.MeasurementsScreenConstants.Companion.WEIGHT_TEXT_FIELD
import nondas.pap.fitness_routine.presentation.ui.theme.AppTheme
import nondas.pap.fitness_routine.presentation.ui.theme.contentSpacing1
import nondas.pap.fitness_routine.presentation.ui.theme.contentSpacing6
import nondas.pap.fitness_routine.presentation.util.getCurrentDate
import nondas.pap.fitness_routine.presentation.util.toFormattedDate


@Composable
fun BodyMeasurementScreen(
    date: Long,
    viewModel: BodyMeasurementViewModel = hiltViewModel<BodyMeasurementViewModel, BodyMeasurementViewModel.Factory>(
        creationCallback = { factory -> factory.create(date = date) }
    ),
    navigateBack: () -> Unit,
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
                navigateBack = navigateBack,
                onUpdateField = { field, value -> viewModel.add(MeasurementEvent.UpdateField(field, value)) },
                onAddMeasurement = { viewModel.add(MeasurementEvent.Add) },
                onUpdateMeasurement = { viewModel.add(MeasurementEvent.Update) }
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BodyMeasurementContent(
    content: MeasurementState.Content,
    navigateBack: () -> Unit,
    onUpdateField: (MeasurementField, String) -> Unit,
    onAddMeasurement: () -> Unit,
    onUpdateMeasurement: () -> Unit,
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
                value = content.bodyMeasurement.weight,
                onValueChange = { onUpdateField(MeasurementField.Weight, it) },
                testTag = WEIGHT_TEXT_FIELD,
                modifier = Modifier.padding(vertical = contentSpacing1)
            )

            Input(
                label = "Fat",
                unit = "%",
                value = content.bodyMeasurement.fat,
                onValueChange = { onUpdateField(MeasurementField.Fat, it) },
                testTag = FAT_TEXT_FIELD,
                modifier = Modifier.padding(vertical = contentSpacing1)
            )

            Input(
                label = "Muscle Mass",
                unit = "kg",
                value = content.bodyMeasurement.muscleMass,
                onValueChange = { onUpdateField(MeasurementField.MuscleMass, it) },
                testTag = MUSCLE_MASS_TEXT_FIELD,
                modifier = Modifier.padding(vertical = contentSpacing1)
            )

            Input(
                label = "BMI",
                unit = "%",
                value = content.bodyMeasurement.bmi,
                onValueChange = { onUpdateField(MeasurementField.BMI, it) },
                testTag = BMI_TEXT_FIELD,
                modifier = Modifier.padding(vertical = contentSpacing1)
            )

            Input(
                label = "TBW",
                unit = "",
                value = content.bodyMeasurement.tbw,
                onValueChange = { onUpdateField(MeasurementField.TBW, it) },
                testTag = TBW_TEXT_FIELD,
                modifier = Modifier.padding(vertical = contentSpacing1)
            )

            Input(
                label = "BMR",
                unit = "",
                value = content.bodyMeasurement.bmr,
                onValueChange = { onUpdateField(MeasurementField.BMR, it) },
                testTag = BMR_TEXT_FIELD,
                modifier = Modifier.padding(vertical = contentSpacing1)
            )

            Input(
                label = "Visceral Fat",
                unit = "",
                value = content.bodyMeasurement.visceralFat,
                onValueChange = { onUpdateField(MeasurementField.VisceralFat, it) },
                testTag = VISCERAL_FAT_TEXT_FIELD,
                modifier = Modifier.padding(vertical = contentSpacing1)
            )

            Input(
                label = "Metabolic Age",
                unit = "",
                value = content.bodyMeasurement.metabolicAge,
                onValueChange = { onUpdateField(MeasurementField.MetabolicAge, it) },
                testTag = METABOLIC_AGE_TEXT_FIELD,
                modifier = Modifier.padding(vertical = contentSpacing1)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = if (content.hasBodyMeasurement) onUpdateMeasurement else onAddMeasurement,
                modifier = Modifier.fillMaxWidth()
                    .testTag(BUTTON)
            ) {
                Text(text = if (content.hasBodyMeasurement) "Update Measurement" else "Add Measurement")
            }

        }

    }
}


@Composable
@Preview
private fun MeasurementsContentPreview() {
    AppTheme {
        BodyMeasurementContent(
            content = MeasurementState.Content(
                hasBodyMeasurement = true,
                bodyMeasurement = BodyMeasurementDomainEntity(
                    id = "",
                    date = getCurrentDate(),
                    weight = "",
                    fat = "",
                    muscleMass = "",
                    bmi = "",
                    tbw = "",
                    bmr = "",
                    visceralFat = "",
                    metabolicAge = ""
                ),
            ),
            navigateBack = {},
            onUpdateField = { _, _, -> },
            onAddMeasurement = {},
            onUpdateMeasurement = {}
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
        const val BUTTON = "button"
    }
}