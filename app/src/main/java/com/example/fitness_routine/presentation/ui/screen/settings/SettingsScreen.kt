package com.example.fitness_routine.presentation.ui.screen.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness_routine.domain.entity.SettingsDomainEntity
import com.example.fitness_routine.domain.entity.enums.Choice
import com.example.fitness_routine.presentation.component.BackButton
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.ui.screen.settings.SettingsScreenConstants.Companion.BREAK_DURATION_TEXT_FIELD
import com.example.fitness_routine.presentation.ui.screen.settings.SettingsScreenConstants.Companion.CHOICE_RADIO_BUTTON
import com.example.fitness_routine.presentation.ui.screen.settings.SettingsScreenConstants.Companion.DARK_MODE_SWITCH
import com.example.fitness_routine.presentation.ui.theme.AppTheme
import com.example.fitness_routine.presentation.ui.theme.contentSpacing2
import com.example.fitness_routine.presentation.ui.theme.contentSpacing3
import com.example.fitness_routine.presentation.ui.theme.contentSpacing4
import com.example.fitness_routine.presentation.util.getColor
import com.example.fitness_routine.presentation.util.getIcon


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
                .padding(24.dp),

        ) {

            Filters(
                options = Choice.entries,
                selectedOption = Choice.valueOf(content.settings.choice),
                select = onSelectChoice,
                testTag = CHOICE_RADIO_BUTTON
            )



            OutlinedTextField(
                value = content.settings.breakDuration,
                onValueChange = { onTextChanged(it) },
                label = { Text(text = "Break time duration in seconds")},
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.testTag(BREAK_DURATION_TEXT_FIELD)
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Enable dark mode: ")


                Switch(
                    checked = content.settings.isDarkModeEnabled,
                    onCheckedChange = { onToggleDarkMode() },
                    modifier = Modifier.testTag(DARK_MODE_SWITCH)

                )
            }

        }
    }
}


@Composable
private fun Filters(
    options: List<Choice>,
    selectedOption: Choice,
    select: (Choice) -> Unit,
    testTag: String
) {

    Text(text = "Select an option: ")

    options.forEach { option ->
        ChoiceRadioButtonItem(
            option = option,
            selectedOption = selectedOption,
            select = select,
            icon = option.getIcon()
        )
        Spacer(modifier = Modifier.height(contentSpacing2))
    }

}

@Composable
private fun ChoiceRadioButtonItem(
    icon: ImageVector,
    option: Choice,
    selectedOption: Choice,
    select: (Choice) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.onPrimaryContainer, shape =  RoundedCornerShape(contentSpacing2))
            .padding(contentSpacing4),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {



        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier =
                Modifier.background(color = option.getColor(), shape = RoundedCornerShape(contentSpacing4))
                    .padding(contentSpacing3)
            )

            Spacer(modifier = Modifier.width(contentSpacing2))

            Text(
                text = option.value,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyLarge
            )

        }
        RadioButton(
            selected = (option == selectedOption),
            onClick = { select(option) },
//            modifier = Modifier.testTag(testTag + option.name)
        )

    }

}

@Preview
@Composable
private fun SettingsContentPreview() {
    AppTheme(darkTheme = true) {
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
}


class SettingsScreenConstants private constructor() {
    companion object {
        const val BREAK_DURATION_TEXT_FIELD = "break_duration"
        const val DARK_MODE_SWITCH = "dark_mode_switch"
        const val CHOICE_RADIO_BUTTON = "choice_radio_button"
    }
}
