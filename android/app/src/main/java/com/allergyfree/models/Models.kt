package com.allergyfree.models

import java.util.Date
import java.util.UUID

// Allergy Model
data class Allergy(
    val id: String,
    val name: String,
    val emoji: String
)

// Predefined list of allergies
val allergiesList = listOf(
    Allergy("peanuts", "Peanuts", "🥜"),
    Allergy("tree-nuts", "Tree Nuts", "🌰"),
    Allergy("milk", "Milk", "🥛"),
    Allergy("eggs", "Eggs", "🥚"),
    Allergy("wheat", "Wheat", "🌾"),
    Allergy("soy", "Soy", "🫘"),
    Allergy("fish", "Fish", "🐟"),
    Allergy("shellfish", "Shellfish", "🦐"),
    Allergy("sesame", "Sesame", "🫒"),
    Allergy("mustard", "Mustard", "🌭"),
    Allergy("celery", "Celery", "🥬"),
    Allergy("gluten", "Gluten", "🍞")
)

// Scan Result Status
enum class ScanStatus {
    SAFE, WARNING, DANGER
}

// Scan Result Model
data class ScanResult(
    val id: String = UUID.randomUUID().toString(),
    val timestamp: Date = Date(),
    val status: ScanStatus,
    val preview: String,
    val allergens: List<String>,
    val ingredients: List<String>
)

// Screen Navigation
enum class Screen {
    ONBOARDING, DASHBOARD, RESULTS, SETTINGS
}

// Theme
enum class Theme {
    LIGHT, DARK
}
