//
//  SettingsView.swift
//  AllergyFree
//
//  Created by Ali Abdelkhalek on 04/01/2026.
//


import SwiftUI

struct SettingsView: View {
    @ObservedObject var appState: AppState
    @State private var selectedAllergies: Set<String>
    
    init(appState: AppState) {
        self.appState = appState
        _selectedAllergies = State(initialValue: Set(appState.userAllergies))
    }
    
    var isDark: Bool {
        appState.theme == .dark
    }
    
    var body: some View {
        ZStack {
            (isDark ? Color.black : Color(hex: "f8fafc"))
                .ignoresSafeArea()
            
            VStack(spacing: 0) {
                // Header
                HStack {
                    Button {
                        saveAndExit()
                    } label: {
                        Image(systemName: "arrow.left")
                            .font(.system(size: 20))
                            .foregroundColor(isDark ? .white : Color(hex: "374151"))
                            .frame(width: 40, height: 40)
                            .background(isDark ? Color(hex: "2c2c2e") : Color(hex: "f3f4f6"))
                            .clipShape(Circle())
                    }
                    
                    Spacer()
                    
                    Text("Settings")
                        .font(.system(size: 28, weight: .bold))
                        .foregroundColor(isDark ? .white : Color(hex: "111827"))
                    
                    Spacer()
                    
                    Color.clear.frame(width: 40)
                }
                .padding()
                .background(isDark ? Color(hex: "1c1c1e") : .white)
                .overlay(
                    Rectangle()
                        .fill(isDark ? Color(hex: "3a3a3c") : Color(hex: "e5e7eb"))
                        .frame(height: 0.5),
                    alignment: .bottom
                )
                
                ScrollView {
                    VStack(alignment: .leading, spacing: 24) {
                        // Allergies Section
                        VStack(alignment: .leading, spacing: 12) {
                            Text("Your Allergies")
                                .font(.system(size: 20, weight: .semibold))
                                .foregroundColor(isDark ? .white : Color(hex: "111827"))
                            
                            Text("Select all that apply to you")
                                .font(.system(size: 15))
                                .foregroundColor(isDark ? .gray : Color(hex: "6b7280"))
                        }
                        .padding(.top, 24)
                        
                        // Allergies List
                        VStack(spacing: 0) {
                            ForEach(Array(allergiesList.enumerated()), id: \.element.id) { index, allergy in
                                AllergySettingRow(
                                    allergy: allergy,
                                    isSelected: selectedAllergies.contains(allergy.id),
                                    isLast: index == allergiesList.count - 1,
                                    isDark: isDark
                                ) {
                                    withAnimation(.spring(response: 0.3, dampingFraction: 0.7)) {
                                        if selectedAllergies.contains(allergy.id) {
                                            selectedAllergies.remove(allergy.id)
                                        } else {
                                            selectedAllergies.insert(allergy.id)
                                        }
                                    }
                                }
                            }
                        }
                        .background(isDark ? Color(hex: "1c1c1e") : .white)
                        .cornerRadius(24)
                        .shadow(color: isDark ? .black.opacity(0.3) : .black.opacity(0.06), radius: 4, y: 2)
                        
                        // Info Card
                        HStack(spacing: 12) {
                            Text("💡")
                                .font(.system(size: 20))
                            
                            VStack(alignment: .leading, spacing: 2) {
                                Text("Tip: ")
                                    .font(.system(size: 15, weight: .medium))
                                    .foregroundColor(isDark ? Color(hex: "d1d5db") : Color(hex: "374151"))
                                + Text("Make sure to select all your allergies for accurate food safety checks.")
                                    .font(.system(size: 15))
                                    .foregroundColor(isDark ? Color(hex: "d1d5db") : Color(hex: "4b5563"))
                            }
                        }
                        .padding(16)
                        .background(Color(hex: "06b6d4").opacity(isDark ? 0.1 : 0.05))
                        .overlay(
                            RoundedRectangle(cornerRadius: 16)
                                .stroke(Color(hex: "06b6d4").opacity(isDark ? 0.3 : 0.2), lineWidth: 1)
                        )
                        .cornerRadius(16)
                        
                        // Count
                        Text("\(selectedAllergies.count) \(selectedAllergies.count == 1 ? "allergy" : "allergies") selected")
                            .font(.system(size: 15))
                            .foregroundColor(isDark ? .gray : Color(hex: "6b7280"))
                            .frame(maxWidth: .infinity, alignment: .center)
                        
                        // Appearance Section
                        VStack(alignment: .leading, spacing: 12) {
                            Text("Appearance")
                                .font(.system(size: 20, weight: .semibold))
                                .foregroundColor(isDark ? .white : Color(hex: "111827"))
                        }
                        .padding(.top, 8)
                        
                        // Theme Toggle
                        HStack(spacing: 12) {
                            ZStack {
                                Circle()
                                    .fill(isDark ? Color(hex: "2c2c2e") : Color(hex: "f3f4f6"))
                                    .frame(width: 40, height: 40)
                                
                                Image(systemName: isDark ? "moon.fill" : "sun.max.fill")
                                    .foregroundColor(Color(hex: "06b6d4"))
                            }
                            
                            VStack(alignment: .leading, spacing: 2) {
                                Text(isDark ? "Dark Mode" : "Light Mode")
                                    .font(.system(size: 17, weight: .medium))
                                    .foregroundColor(isDark ? .white : Color(hex: "111827"))
                                
                                Text(isDark ? "Easier on the eyes" : "Classic appearance")
                                    .font(.system(size: 13))
                                    .foregroundColor(isDark ? .gray : Color(hex: "6b7280"))
                            }
                            
                            Spacer()
                            
                            Toggle("", isOn: Binding(
                                get: { isDark },
                                set: { newValue in
                                    withAnimation(.spring(response: 0.3, dampingFraction: 0.7)) {
                                        appState.theme = newValue ? .dark : .light
                                    }
                                }
                            ))
                            .labelsHidden()
                            .tint(Color(hex: "06b6d4"))
                        }
                        .padding(20)
                        .background(isDark ? Color(hex: "1c1c1e") : .white)
                        .cornerRadius(24)
                        .shadow(color: isDark ? .black.opacity(0.3) : .black.opacity(0.06), radius: 4, y: 2)
                    }
                    .padding(.horizontal, 20)
                    .padding(.bottom, 100)
                }
                
                // Save Button
                VStack {
                    Button {
                        saveAndExit()
                    } label: {
                        Text("Save Changes")
                            .font(.system(size: 17, weight: .semibold))
                            .foregroundColor(.white)
                            .frame(maxWidth: .infinity)
                            .frame(height: 56)
                            .background(
                                LinearGradient(
                                    colors: [Color(hex: "06b6d4"), Color(hex: "0e7490")],
                                    startPoint: .leading,
                                    endPoint: .trailing
                                )
                            )
                            .cornerRadius(14)
                            .shadow(color: Color(hex: "06b6d4").opacity(0.3), radius: 6, y: 4)
                    }
                    .padding(.horizontal, 20)
                    .padding(.vertical, 16)
                    .background(isDark ? Color(hex: "1c1c1e") : .white)
                    .overlay(
                        Rectangle()
                            .fill(isDark ? Color(hex: "3a3a3c") : Color(hex: "e5e7eb"))
                            .frame(height: 0.5),
                        alignment: .top
                    )
                }
            }
        }
    }
    
    func saveAndExit() {
        appState.userAllergies = Array(selectedAllergies)
        withAnimation(.spring(response: 0.4, dampingFraction: 0.8)) {
            appState.currentScreen = .dashboard
        }
    }
}

struct AllergySettingRow: View {
    let allergy: Allergy
    let isSelected: Bool
    let isLast: Bool
    let isDark: Bool
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            HStack(spacing: 16) {
                Text(allergy.emoji)
                    .font(.system(size: 28))
                
                Text(allergy.name)
                    .font(.system(size: 17, weight: .medium))
                    .foregroundColor(isDark ? .white : Color(hex: "111827"))
                
                Spacer()
                
                ZStack {
                    Circle()
                        .fill(isSelected ? Color(hex: "06b6d4") : (isDark ? Color(hex: "3a3a3c") : Color(hex: "e5e7eb")))
                        .frame(width: 28, height: 28)
                    
                    if isSelected {
                        Image(systemName: "checkmark")
                            .font(.system(size: 12, weight: .bold))
                            .foregroundColor(.white)
                    }
                }
            }
            .padding(20)
            .background(isDark ? Color(hex: "1c1c1e") : .white)
            .overlay(
                Group {
                    if !isLast {
                        Rectangle()
                            .fill(isDark ? Color(hex: "3a3a3c") : Color(hex: "e5e7eb"))
                            .frame(height: 0.5)
                            .padding(.leading, 76)
                    }
                },
                alignment: .bottom
            )
        }
        .buttonStyle(PlainButtonStyle())
    }
}
