package com.example.fitness_routine.presentation.ui.screen.settings

import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.domain.entity.SettingsDomainEntity
import com.example.fitness_routine.domain.entity.enums.Choice
import com.example.fitness_routine.domain.interactor.settings.GetSettings
import com.example.fitness_routine.presentation.BlocViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    getSettings: GetSettings
): BlocViewModel<SettingsEvent, SettingsState>(){



    private val choiceFlow = getSettings.execute(Unit)
        .map { it.getOrThrow() }
        .catch { addError(it) }

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
//            choiceFlow.emit(it.choice)
        }

        on(SettingsEvent.TextChanged::class) {
//            breakSecondsFlow.emit(it.text)
        }

        on(SettingsEvent.ToggleDarkMode::class) {
            onState<SettingsState.Content> { state ->
//                isDarkModeEnabled.emit(!state.settings.isDarkModeEnabled)
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



