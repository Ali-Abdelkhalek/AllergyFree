package com.allergyfree.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.allergyfree.models.ScanResult
import com.allergyfree.models.Screen
import com.allergyfree.models.Theme

class AppViewModel : ViewModel() {
    var currentScreen by mutableStateOf(Screen.ONBOARDING)
    var userAllergies by mutableStateOf<List<String>>(emptyList())
    var theme by mutableStateOf(Theme.LIGHT)
    var recentScans by mutableStateOf<List<ScanResult>>(emptyList())
    var currentResult by mutableStateOf<ScanResult?>(null)
    var showInputSheet by mutableStateOf(false)

    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }

    fun updateAllergies(allergies: List<String>) {
        userAllergies = allergies
    }

    fun toggleTheme() {
        theme = if (theme == Theme.LIGHT) Theme.DARK else Theme.LIGHT
    }

    fun addScanResult(result: ScanResult) {
        recentScans = listOf(result) + recentScans
        currentResult = result
    }
}
