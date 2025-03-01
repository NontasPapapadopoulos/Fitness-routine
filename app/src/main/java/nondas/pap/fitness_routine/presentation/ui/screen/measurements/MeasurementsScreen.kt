package nondas.pap.fitness_routine.presentation.ui.screen.measurements

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SettingsAccessibility
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nondas.pap.fitness_routine.domain.entity.BodyMeasurementDomainEntity
import nondas.pap.fitness_routine.presentation.component.BackButton
import nondas.pap.fitness_routine.presentation.component.LoadingBox
import nondas.pap.fitness_routine.presentation.ui.theme.AppTheme
import nondas.pap.fitness_routine.presentation.ui.theme.contentSpacing2
import nondas.pap.fitness_routine.presentation.ui.theme.contentSpacing3
import nondas.pap.fitness_routine.presentation.ui.theme.contentSpacing6
import nondas.pap.fitness_routine.presentation.util.toFormattedDate
import nondas.pap.fitness_routine.presentation.util.toTimeStamp
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

@Composable
fun MeasurementsScreen(
    navigateBack: () -> Unit,
    navigateToBodyMeasurement: (Long) -> Unit,
    viewModel: MeasurementsViewModel = hiltViewModel()
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
        is MeasurementsState.Content -> {
            MeasurementsContent(
                content = state,
                navigateBack = navigateBack,
                onNavigateToMeasurement = navigateToBodyMeasurement

            )
        }
        MeasurementsState.Idle -> { LoadingBox() }
    }


}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MeasurementsContent(
    content: MeasurementsState.Content,
    navigateBack: () -> Unit,
    onNavigateToMeasurement: (Long) -> Unit
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
                        Text(text = "Measurements History")

//                        IconButton(onClick = {  }) {
//                            Icon(Icons.Outlined.AddCircle, null)
//                        }
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
                .padding(horizontal = contentSpacing6)
                .verticalScroll(rememberScrollState())
        ) {

            NoMeasurementsMessage(isEmpty =
            content.measurements.isEmpty())

            content.measurements.forEach {
                MeasurementItem(
                    measurement = it,
                    onNavigateToMeasurement = { onNavigateToMeasurement(it.date) }
                )
            }
        }

    }


}

@Composable
private fun NoMeasurementsMessage(
    isEmpty: Boolean
) {
    if (isEmpty) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(text = "There are no measurements documented.")
        }

    }
}

@Composable
private fun MeasurementItem(
    measurement: BodyMeasurementDomainEntity,
    onNavigateToMeasurement: () -> Unit
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(contentSpacing3)
            .clickable { onNavigateToMeasurement() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = measurement.date.toFormattedDate()
        )

        Spacer(modifier = Modifier.width(contentSpacing6))

        Column{

            Text(
                text = "Weight: ${measurement.weight}"
            )

            Text(
                text = "Fat: ${measurement.fat}"
            )

        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            Icons.Default.SettingsAccessibility,
            contentDescription = null
        )
    }


}

@Composable
@Preview
private fun MeasurementsContentPreview() {
    AppTheme {
        MeasurementsContent(
            content = MeasurementsState.Content(
                measurements = generateMeasurements()
            ),
            navigateBack = {},
            onNavigateToMeasurement = {}
        )
    }
}


private fun generateMeasurements() =
(1..11).map {
    val localDate = LocalDate.of(2024, if (it < 5) 1 else 2, it)
    val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        BodyMeasurementDomainEntity(
            id = "",
            weight = "",
            fat = "",
            metabolicAge = "",
            muscleMass = "",
            bmr = "",
            bmi = "",
            tbw = "",
            visceralFat = "",
            date = date.toTimeStamp()
        )
    }