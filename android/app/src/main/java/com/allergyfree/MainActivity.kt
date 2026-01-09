package com.allergyfree

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.allergyfree.models.Screen
import com.allergyfree.models.Theme
import com.allergyfree.ui.screens.*
import com.allergyfree.ui.theme.AllergyFreeTheme
import com.allergyfree.viewmodels.AppViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AllergyFreeTheme(darkTheme = viewModel.theme == Theme.DARK) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AllergyFreeApp(viewModel)
                }
            }
        }
    }
}

@Composable
fun AllergyFreeApp(viewModel: AppViewModel) {
    when (viewModel.currentScreen) {
        Screen.ONBOARDING -> OnboardingScreen(viewModel)
        Screen.DASHBOARD -> {
            DashboardScreen(viewModel)

            // Show Input Sheet if needed
            if (viewModel.showInputSheet) {
                InputSheetScreen(
                    viewModel = viewModel,
                    onDismiss = { viewModel.showInputSheet = false }
                )
            }
        }
        Screen.RESULTS -> {
            viewModel.currentResult?.let { result ->
                ResultsScreen(viewModel = viewModel, result = result)
            }
        }
        Screen.SETTINGS -> SettingsScreen(viewModel)
    }
}
