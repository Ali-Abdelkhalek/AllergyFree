//
//  ContentView.swift
//  AllergyFree
//
//  Created by Ali Abdelkhalek on 04/01/2026.
//

import SwiftUI
import Combine

struct ContentView: View {
    @StateObject private var appState = AppState()
    
    var body: some View {
        ZStack {
            switch appState.currentScreen {
            case .onboarding:
                OnboardingView(appState: appState)
            case .dashboard:
                DashboardView(appState: appState)
            case .results:
                if let result = appState.currentResult {
                    ResultsView(result: result, appState: appState)
                }
            case .settings:
                SettingsView(appState: appState)
            }
        }
        .preferredColorScheme(appState.theme == .dark ? .dark : .light)
    }
}

// MARK: - App State
class AppState: ObservableObject {
    @Published var currentScreen: Screen = .onboarding
    @Published var userAllergies: [String] = []
    @Published var theme: Theme = .light
    @Published var recentScans: [ScanResult] = []
    @Published var currentResult: ScanResult?
    @Published var showInputSheet = false
    
    enum Screen {
        case onboarding, dashboard, results, settings
    }
    
    enum Theme {
        case light, dark
    }
}

// MARK: - Models
struct ScanResult: Identifiable {
    let id = UUID()
    let timestamp: Date
    let status: ScanStatus
    let preview: String
    let allergens: [String]
    let ingredients: [String]
    
    enum ScanStatus {
        case safe, warning, danger
    }
}

struct Allergy: Identifiable {
    let id: String
    let name: String
    let emoji: String
}

let allergiesList = [
    Allergy(id: "peanuts", name: "Peanuts", emoji: "🥜"),
    Allergy(id: "tree-nuts", name: "Tree Nuts", emoji: "🌰"),
    Allergy(id: "milk", name: "Milk", emoji: "🥛"),
    Allergy(id: "eggs", name: "Eggs", emoji: "🥚"),
    Allergy(id: "wheat", name: "Wheat", emoji: "🌾"),
    Allergy(id: "soy", name: "Soy", emoji: "🫘"),
    Allergy(id: "fish", name: "Fish", emoji: "🐟"),
    Allergy(id: "shellfish", name: "Shellfish", emoji: "🦐"),
    Allergy(id: "sesame", name: "Sesame", emoji: "🫒"),
    Allergy(id: "mustard", name: "Mustard", emoji: "🌭"),
    Allergy(id: "celery", name: "Celery", emoji: "🥬"),
    Allergy(id: "gluten", name: "Gluten", emoji: "🍞")
]
