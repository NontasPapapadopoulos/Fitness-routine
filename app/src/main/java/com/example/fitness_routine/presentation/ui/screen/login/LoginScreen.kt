package com.example.fitness_routine.presentation.ui.screen.login

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness_routine.R
import com.example.fitness_routine.presentation.component.Logo
import com.example.fitness_routine.presentation.ui.theme.AppTheme
import com.example.fitness_routine.presentation.ui.theme.contentSize8
import com.example.fitness_routine.presentation.ui.theme.contentSpacing2
import com.example.fitness_routine.presentation.ui.theme.contentSpacing3
import com.example.fitness_routine.presentation.ui.theme.contentSpacing5


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {

    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    LoginContent(
        performLogin = { viewModel.add(LoginEvent.PerformLogin) },
        isLoading = state.isLoading
    )

}


@Composable
private fun LoginContent(
    performLogin: () -> Unit,
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

            GoogleButton(performLogin)

            Spacer(modifier = Modifier.height(contentSpacing5))
        }
    }
}

@Composable
private fun GoogleButton(onClick: () -> Unit) {

    Button(
        onClick = onClick,
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


@Preview
@Composable
private fun LoginScreenPreview() {
    AppTheme {
        LoginContent(
            performLogin = {},
            isLoading = false
        )
    }
}