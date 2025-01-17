package com.example.fitness_routine.presentation.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.domain.interactor.auth.PerformLogin
import com.example.fitness_routine.presentation.BlocViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    performLogin: PerformLogin
): BlocViewModel<LoginEvent, LoginContent>() {


    private val isLoadingFlow = MutableSharedFlow<Boolean>()

    override val _uiState: StateFlow<LoginContent> = isLoadingFlow.map {
        LoginContent(isLoading = it)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = LoginContent(isLoading = false)
        )


    init {

        on(LoginEvent.PerformLogin::class) {

        }

    }
}


interface LoginEvent {
    object PerformLogin: LoginEvent
}

data class LoginContent(
    val isLoading: Boolean
)


