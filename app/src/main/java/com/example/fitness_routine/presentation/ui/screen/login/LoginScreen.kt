package com.example.fitness_routine.presentation.ui.screen.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness_routine.R
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.component.Logo
import com.example.fitness_routine.presentation.ui.theme.AppTheme
import com.example.fitness_routine.presentation.ui.theme.contentSize8
import com.example.fitness_routine.presentation.ui.theme.contentSpacing2
import com.example.fitness_routine.presentation.ui.theme.contentSpacing3
import com.example.fitness_routine.presentation.ui.theme.contentSpacing5
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    navigateToCalendarScreen: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
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

    LaunchedEffect(Unit) {
        viewModel.navigationFlow.collect { navigate ->
            if (navigate)
                navigateToCalendarScreen()
        }
    }


    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    LoginContent(
        navigateToCalendarScreen = navigateToCalendarScreen,
        performLogin = { viewModel.add(LoginEvent.PerformLogin(it)) },
        skipLogin = { viewModel.add(LoginEvent.SkipLogin) },
        isLoading = state.isLoading
    )

}


@Composable
private fun LoginContent(
    navigateToCalendarScreen: () -> Unit,
    performLogin: (Credential) -> Unit,
    skipLogin: () -> Unit,
    isLoading: Boolean
) {

    Scaffold(
        contentColor = MaterialTheme.colorScheme.primary,
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Logo()

            Spacer(modifier = Modifier.weight(1f))


            GoogleButton(onRequestResult = { performLogin(it) })

            Spacer(modifier = Modifier.height(contentSpacing3))

            SkipLoginButton(skipLogin)

            Spacer(modifier = Modifier.height(contentSpacing5))
        }

        if (isLoading) {
            LoadingBox()
        }
    }
}

@Composable
private fun SkipLoginButton(onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(
            text = "Continue without creating account",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun GoogleButton(onRequestResult: (Credential) -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Button(
        onClick = { coroutineScope.launch { launchCredManButtonUI(context, onRequestResult) } },
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = contentSpacing3)
    ) {


        Image(
            painter = painterResource(id = R.drawable.google),
            contentDescription = null,
            modifier = Modifier.size(contentSize8)
        )

        Spacer(modifier = Modifier.width(contentSpacing2))

        Text(
            text = "Sign in with Google",
            style = MaterialTheme.typography.titleMedium
        )
        
    }
}


private suspend fun launchCredManButtonUI(
    context: Context,
    onRequestResult: (Credential) -> Unit
) {
    try {
        val signInWithGoogleOption = GetSignInWithGoogleOption
            .Builder(serverClientId = context.getString(R.string.default_web_client_id))
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()

        val result = CredentialManager.create(context).getCredential(
            request = request,
            context = context
        )

        onRequestResult(result.credential)
    } catch (e: NoCredentialException) {
        Log.d("ERROR", e.message.orEmpty())
    } catch (e: GetCredentialException) {
        Log.d("ERROR", e.message.orEmpty())
    }
}


@Preview
@Composable
private fun LoginScreenPreview() {
    AppTheme {
        LoginContent(
            navigateToCalendarScreen = {},
            performLogin = {},
            skipLogin = {},
            isLoading = false
        )
    }
}