package com.example.fitness_routine.presentation.ui.screen.login

import android.app.Application
import android.content.IntentSender
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness_routine.domain.interactor.auth.PerformLogin
import com.example.fitness_routine.presentation.BlocViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException


@HiltViewModel
class LoginViewModel @Inject constructor(
    performLogin: PerformLogin,
    application: Application
): BlocViewModel<LoginEvent, LoginContent>() {



    private lateinit var googleSignInClient: GoogleSignInClient

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(CLIENT_ID)
        .requestEmail()
        .build()

    private var oneTapClient = Identity.getSignInClient(application.applicationContext)

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
        googleSignInClient = GoogleSignIn.getClient(application.applicationContext, gso)


        on(LoginEvent.PerformLogin::class) {
            signIn()
        }

    }


    private suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(buildSignInRequest()).await().let { signInResult ->
                signInResult // Automatically closes the resource after this block
            }
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(CLIENT_ID)
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()
    }




    companion object {
        private const val CLIENT_ID = "673481352767-kb4v5ep1le5q3j0ath41j5cdpt0t253g.apps.googleusercontent.com"
    }

}




interface LoginEvent {
    object PerformLogin: LoginEvent
}

data class LoginContent(
    val isLoading: Boolean
)


