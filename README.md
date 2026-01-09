# AllergyFree - Cross-Platform Allergy Detection App

A mobile application to help users check if food is safe based on their allergies. Available for both iOS and Android.

## Features

- **Onboarding**: Select your allergies from a predefined list
- **Dashboard**: View your allergies and recent food scans
- **Food Safety Check**: Enter ingredients or scan food labels (text entry)
- **Results Screen**: See detailed analysis of detected allergens
- **Settings**: Manage allergies and toggle between light/dark themes

## Project Structure

This is a monorepo containing both iOS and Android implementations:

```
AllergyFree/
├── ios/                      # iOS App (SwiftUI)
│   ├── AllergyFree/
│   │   ├── AllergyFreeApp.swift
│   │   ├── ContentView.swift
│   │   ├── OnboardingView.swift
│   │   ├── DashboardView.swift
│   │   ├── InputSheetView.swift
│   │   ├── ResultsView.swift
│   │   ├── SettingsView.swift
│   │   ├── DesignSystem.swift
│   │   └── Item.swift
│   ├── AllergyFree.xcodeproj
│   └── AllergyFreeTests/
│
├── android/                  # Android App (Kotlin + Jetpack Compose)
│   ├── app/
│   │   ├── build.gradle.kts
│   │   └── src/main/
│   │       ├── AndroidManifest.xml
│   │       └── java/com/allergyfree/
│   │           ├── MainActivity.kt
│   │           ├── models/
│   │           │   └── Models.kt
│   │           ├── viewmodels/
│   │           │   └── AppViewModel.kt
│   │           └── ui/
│   │               ├── theme/
│   │               │   ├── Color.kt
│   │               │   ├── Theme.kt
│   │               │   └── Type.kt
│   │               ├── components/
│   │               │   └── CommonComponents.kt
│   │               └── screens/
│   │                   ├── OnboardingScreen.kt
│   │                   ├── DashboardScreen.kt
│   │                   ├── InputSheetScreen.kt
│   │                   ├── ResultsScreen.kt
│   │                   └── SettingsScreen.kt
│   ├── build.gradle.kts
│   ├── settings.gradle.kts
│   └── gradle.properties
│
└── README.md
```

## iOS App

### Requirements
- Xcode 15.0+
- iOS 17.0+
- Swift 5.9+

### Technologies
- SwiftUI for UI
- SwiftData for persistence
- Combine for state management

### Running the iOS App
1. Open `ios/AllergyFree.xcodeproj` in Xcode
2. Select your target device or simulator
3. Press `Cmd + R` to build and run

## Android App

### Requirements
- Android Studio Hedgehog (2023.1.1) or newer
- Android SDK 24+ (minimum)
- Android SDK 34 (target)
- Kotlin 1.9.20+

### Technologies
- Jetpack Compose for UI
- Material3 Design
- ViewModel for state management
- Kotlin Coroutines

### Running the Android App
1. Open the `android/` directory in Android Studio
2. Sync Gradle files
3. Select your target device or emulator
4. Press `Run` or `Shift + F10`

## Common Features

Both apps share the same:
- **Design System**: Matching colors, typography, and UI components
- **User Flow**: Identical navigation and screens
- **Functionality**: Same allergen detection logic
- **Allergen List**: Peanuts, Tree Nuts, Milk, Eggs, Wheat, Soy, Fish, Shellfish, Sesame, Mustard, Celery, Gluten

## Allergen Detection

The app currently uses simple string matching to detect allergens in ingredient lists. It compares user-selected allergies against entered ingredients (case-insensitive).

### Future Enhancements
- Camera-based ingredient scanning
- OCR for food labels
- API integration for comprehensive food database
- Nutritional information
- Barcode scanning
- Multi-language support

## Contributing

Feel free to contribute to either platform! Please ensure:
- Code follows platform conventions (SwiftUI for iOS, Compose for Android)
- UI matches the design system
- Features are implemented on both platforms

## License

MIT License - feel free to use this project for learning or as a starting point for your own apps.
