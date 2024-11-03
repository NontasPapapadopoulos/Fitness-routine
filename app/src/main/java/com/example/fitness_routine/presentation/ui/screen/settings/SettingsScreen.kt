package com.example.fitness_routine.presentation.ui.screen.settings

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role.Companion.Switch
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness_routine.domain.entity.SettingsDomainEntity
import com.example.fitness_routine.domain.entity.enums.Choice
import com.example.fitness_routine.presentation.component.BackButton
import com.example.fitness_routine.presentation.component.LoadingBox


@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
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
        is SettingsState.Content -> {
            SettingsContent(
                content = state,
                onNavigateBack =  navigateBack,
                onSelectChoice = { viewModel.add(SettingsEvent.SelectChoice(it)) },
                onTextChanged = { viewModel.add(SettingsEvent.TextChanged(it)) },
                onToggleDarkMode = { viewModel.add(SettingsEvent.ToggleDarkMode) }
            )
        }

        SettingsState.Idle -> { LoadingBox() }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsContent(
    content: SettingsState.Content,
    onNavigateBack: () -> Unit,
    onSelectChoice: (Choice) -> Unit,
    onTextChanged: (String) -> Unit,
    onToggleDarkMode: () -> Unit

) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = { BackButton(onNavigateBack) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            Filters(
                options = Choice.entries,
                selectedOption = Choice.valueOf(content.settings.choice),
                select = onSelectChoice
            )



            OutlinedTextField(
                value = content.settings.breakDuration,
                onValueChange = { onTextChanged(it) },
                label = { Text(text = "Break time duration in seconds")},
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )




            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Enable dark mode: ")


                Switch(
                    checked = content.settings.isDarkModeEnabled,
                    onCheckedChange = { onToggleDarkMode() }
                )
            }

        }
    }
}


@Composable
private fun Filters(
    options: List<Choice>,
    selectedOption: Choice,
    select: (Choice) -> Unit
) {

    Text(text = "Select an option: ")

    options.forEach { option ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(3.dp)
        ) {
            RadioButton(
                selected = (option == selectedOption),
                onClick = {
                    select(option)
                },
                modifier = Modifier.testTag(option.name)
            )

            Text(
                text = option.value,
                modifier = Modifier.padding(start = 3.dp)
            )
        }
    }

}


@Preview
@Composable
private fun SettingsContentPreview() {
    SettingsContent(
        content = SettingsState.Content(
            SettingsDomainEntity(
            choice = Choice.Workout.name,
            breakDuration = "60",
            isDarkModeEnabled = true
            )
        ),
        onNavigateBack = {},
        onSelectChoice = {},
        onToggleDarkMode = {},
        onTextChanged = {},
    )
}
