package nondas.pap.fitness_routine.presentation.component

import android.app.ActivityManager.AppTask
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import nondas.pap.fitness_routine.presentation.ui.theme.AppTheme

@Composable
fun AppSurface(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    AppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}



@Preview
@Composable
fun AppSurfacePreview() {
    AppSurface {
        Text(text = "Hello android!")
    }
}