package com.example.fitness_routine.presentation.ui.screen.login

import android.app.Application
import android.content.IntentSender
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.CreateCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.domain.interactor.auth.PerformLogin
import com.example.fitness_routine.domain.interactor.auth.SkipLogin
import com.example.fitness_routine.presentation.BlocViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val performLogin: PerformLogin,
    private val skipLogin: SkipLogin,
): BlocViewModel<LoginEvent, LoginContent>() {

    private val _navigationFlow = MutableSharedFlow<Boolean>()
    val navigationFlow: SharedFlow<Boolean> = _navigationFlow


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
            isLoadingFlow.emit(true)
            performLogin.execute(PerformLogin.Params(it.credential)).fold(
                onSuccess = { navigate() },
                onFailure = { addError(it) }
            )
        }

        on(LoginEvent.SkipLogin::class) {
            skipLogin.execute(Unit).fold(
                onSuccess = { navigate() },
                onFailure = { addError(it) }
            )
        }

    }

    private suspend fun navigate() {
        _navigationFlow.emit(true)
    }

}




interface LoginEvent {
    data class PerformLogin(val credential: Credential): LoginEvent
    object SkipLogin: LoginEvent
}

data class LoginContent(
    val isLoading: Boolean
)


