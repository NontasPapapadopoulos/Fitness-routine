package nondas.pap.fitness_routine.presentation.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun BackButton(
    navigateBack: () -> Unit
) {
    IconButton(
        onClick = navigateBack
    ) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back")
    }
}



@Composable
@Preview
private fun BackButtonPreview() {
    BackButton(navigateBack = {})
}