package com.example.fitness_routine.presentation.screen.gym

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness_routine.presentation.component.BottomBar
import com.example.fitness_routine.presentation.component.LoadingBox
import com.example.fitness_routine.presentation.navigation.Screen
import com.example.fitness_routine.presentation.util.getCurrentDate
import dagger.hilt.android.lifecycle.HiltViewModel


@Composable
fun GymSessionsScreen(
    viewModel: GymSessionsViewModel = hiltViewModel(),
    navigateToScreen: (Screen) -> Unit,
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

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is GymSessionsState.Content -> {
            GymSessionsContent(
                content = state,
                navigateToScreen = navigateToScreen
            )
        }
        GymSessionsState.Idle -> {
            LoadingBox()
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GymSessionsContent(
    content: GymSessionsState.Content,
    navigateToScreen: (Screen) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(text = "Fitness Diary")

                    }
                },

            )
        },
        bottomBar = {
            BottomBar(
                onClick = { navigateToScreen(it) },
                currentScreen = Screen.Gym
            )
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Gym Sessions")
        }

    }
}




@Composable
@Preview
private fun GymSessionsContentPreview() {
    GymSessionsContent(
        content = GymSessionsState.Content(
            xx = "xx"
        ),
        navigateToScreen = {}
    )
}