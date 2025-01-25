package com.example.fitness_routine.presentation.ui.screen.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitness_routine.presentation.component.Logo
import com.example.fitness_routine.presentation.navigation.Screen
import com.example.fitness_routine.presentation.ui.theme.AppTheme
import com.example.fitness_routine.presentation.ui.theme.contentSize10
import com.example.fitness_routine.presentation.ui.theme.contentSize25
import com.example.fitness_routine.presentation.ui.theme.contentSpacing4
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest


@Composable
fun SplashScreen(
    navigateToLoginScreen: () -> Unit,
    navigateToCalendarScreen: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.navigationFlow.collectLatest { target ->
            delay(1000)
            when (target) {
                SplashViewModel.NavigationTarget.Login -> navigateToLoginScreen()
                SplashViewModel.NavigationTarget.Calendar -> navigateToCalendarScreen()
            }
        }
    }


    Scaffold(
        contentColor = MaterialTheme.colorScheme.primary,
        containerColor = MaterialTheme.colorScheme.primary
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .semantics { contentDescription = Screen.Splash.name },

            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Logo()
            }

        }
    }

}




@Preview
@Composable
private fun SplashScreenPreview() {
    AppTheme(darkTheme = true) {
        SplashScreen(
            navigateToCalendarScreen = {},
            navigateToLoginScreen = {}
        )
    }
}