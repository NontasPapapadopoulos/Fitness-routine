package com.example.fitness_routine.presentation.ui.screen.measurements

import android.widget.Toast
import androidx.compose.foundation.clickable
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
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.ui.theme.AppTheme
import com.example.fitness_routine.presentation.ui.theme.contentSpacing6
import com.example.fitness_routine.presentation.util.toFormattedDate

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
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        //Text(text = "Date: ${content.bodyMeasurement.date.toFormattedDate()}")
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
private fun MeasurementItem(
    measurement: BodyMeasurementDomainEntity,
    onNavigateToMeasurement: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToMeasurement() }
    ) {
        Text(text = measurement.date.toString())
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
        BodyMeasurementDomainEntity(
            id = "",
            weight = 0f,
            fat = 0f,
            metabolicAge = 0,
            muscleMass = 0f,
            bmr = 0f,
            bmi = 0f,
            tbw = 0f,
            visceralFat = 0,
            date = 0L
        )
    }