package nondas.pap.fitness_routine.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import nondas.pap.fitness_routine.presentation.navigation.Navigation
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import nondas.pap.fitness_routine.presentation.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import nondas.pap.fitness_routine.data.NotificationHandler


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissionsForNotifications()
        NotificationHandler(this)
            .createNotificationChannelAndSchedule()

        setContent {
            enableEdgeToEdge()
            SetStatusBarColor()

            AppTheme {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.navigationBars)) { innerPadding ->
                    innerPadding
                    Navigation()
                }
            }
        }
    }

    private fun requestPermissionsForNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS), 100
                )
            }
        }
    }

}

@Composable
private fun SetStatusBarColor() {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        darkIcons = false,
        color = MaterialTheme.colorScheme.primary
    )
}


