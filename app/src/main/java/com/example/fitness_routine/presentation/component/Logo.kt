package com.example.fitness_routine.presentation.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.fitness_routine.presentation.ui.theme.contentSize25
import com.example.fitness_routine.presentation.ui.theme.contentSpacing4

@Composable
fun Logo() {
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