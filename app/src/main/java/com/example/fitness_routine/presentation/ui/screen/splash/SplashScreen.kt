package com.example.fitness_routine.presentation.ui.screen.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitness_routine.presentation.navigation.Screen


@Composable
fun SplashScreen() {

    Scaffold {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .semantics { contentDescription = Screen.Splash.name },

            contentAlignment = Alignment.Center
        ) {

            Text(text = "Fitness routine")

        }
    }

}




@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen()
}