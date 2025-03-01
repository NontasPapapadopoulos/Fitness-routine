package nondas.pap.fitness_routine.presentation.ui.screen.settings

import androidx.lifecycle.viewModelScope
import nondas.pap.fitness_routine.domain.entity.SettingsDomainEntity
import nondas.pap.fitness_routine.domain.entity.enums.Choice
import nondas.pap.fitness_routine.domain.interactor.settings.ChangeSettings
import nondas.pap.fitness_routine.domain.interactor.settings.GetSettings
import nondas.pap.fitness_routine.presentation.BlocViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
open class SettingsViewModel @Inject constructor(
    private val getSettings: GetSettings,
    private val changeSettings: ChangeSettings
): BlocViewModel<SettingsEvent, SettingsState>(){

    override val _uiState: StateFlow<SettingsState> = getSettings.execute(Unit)
        .map { it.getOrThrow() }
        .catch { addError(it) }
        .map { settings ->
            SettingsState.Content(settings)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = SettingsState.Idle
    )


    init {
        on(SettingsEvent.SelectChoice::class) {
            onState<SettingsState.Content> { state ->
                changeSettings.execute(ChangeSettings.Params(state.settings.copy(choice = it.choice.name))).fold(
                    onSuccess = {},
                    onFailure = { addError(it) }
                )
            }
        }

        on(SettingsEvent.TextChanged::class) {
            onState<SettingsState.Content> { state ->
                changeSettings.execute(ChangeSettings.Params(state.settings.copy(breakDuration = it.text))).fold(
                    onSuccess = {},
                    onFailure = { addError(it) }
                )
            }
        }

        on(SettingsEvent.ToggleDarkMode::class) {
            onState<SettingsState.Content> { state ->
                val isEnabled = !state.settings.isDarkModeEnabled
                changeSettings.execute(ChangeSettings.Params(state.settings.copy(isDarkModeEnabled = isEnabled))).fold(
                    onSuccess = {},
                    onFailure = { addError(it) }
                )
            }

        }

    }

}

sealed interface SettingsEvent {
    data class SelectChoice(val choice: Choice): SettingsEvent
    data class TextChanged(val text: String): SettingsEvent
    object ToggleDarkMode: SettingsEvent
}


sealed interface SettingsState {
    object Idle: SettingsState

    data class Content(
        val settings: SettingsDomainEntity
    ): SettingsState
}



