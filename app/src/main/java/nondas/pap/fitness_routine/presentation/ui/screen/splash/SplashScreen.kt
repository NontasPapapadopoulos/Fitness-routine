package nondas.pap.fitness_routine.presentation.ui.screen.splash

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import nondas.pap.fitness_routine.presentation.ui.screen.splash.SplashScreenConstants.Companion.SPLASH_SCREEN_TAG
import nondas.pap.fitness_routine.presentation.ui.theme.AppTheme
import nondas.pap.fitness_routine.presentation.ui.theme.contentSize25
import nondas.pap.fitness_routine.presentation.ui.theme.contentSpacing4


@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToCalendarScreen: () -> Unit
) {

    LaunchedEffect(Unit) {
        delay(1200)
        navigateToCalendarScreen()
    }


    Scaffold(
        contentColor = MaterialTheme.colorScheme.primary,
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.testTag(SPLASH_SCREEN_TAG)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
//                .semantics { contentDescription = NavigationTarget.Splash.name },

            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    imageVector = Icons.Default.FitnessCenter,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(contentSize25)
                )

                Spacer(modifier = Modifier.height(contentSpacing4))

                Text(
                    text = "Fitness routine\ntracker",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
            }





        }
    }

}


class SplashScreenConstants private constructor() {
    companion object {
        const val SPLASH_SCREEN_TAG = "splash_screen_tag"
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    AppTheme(darkTheme = true) {
        SplashScreen(
            navigateToCalendarScreen = {}
        )
    }
}