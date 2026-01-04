//
//  OnboardingView.swift
//  AllergyFree
//
//  Created by Ali Abdelkhalek on 04/01/2026.
//


import SwiftUI

struct OnboardingView: View {
    @ObservedObject var appState: AppState
    @State private var selectedAllergies: Set<String> = []
    @State private var searchQuery = ""
    
    var filteredAllergies: [Allergy] {
        if searchQuery.isEmpty {
            return allergiesList
        }
        return allergiesList.filter { $0.name.localizedCaseInsensitiveContains(searchQuery) }
    }
    
    var body: some View {
        ZStack {
            // Gradient Background
            LinearGradient(
                colors: [
                    Color(hex: "06b6d4"),
                    Color(hex: "0e7490"),
                    Color(hex: "a7f3d0")
                ],
                startPoint: .topLeading,
                endPoint: .bottomTrailing
            )
            .ignoresSafeArea()
            
            // Dark overlay at bottom
            VStack {
                Spacer()
                LinearGradient(
                    colors: [.clear, Color(hex: "0e7490").opacity(0.3)],
                    startPoint: .top,
                    endPoint: .bottom
                )
                .frame(height: 300)
            }
            .ignoresSafeArea()
            
            VStack(spacing: 0) {
                // Header
                VStack(spacing: 12) {
                    Image(systemName: "shield.fill")
                        .font(.system(size: 64))
                        .foregroundColor(.white)
                        .padding(.top, 60)
                    
                    Text("Welcome to AllergyFree")
                        .font(.system(size: 34, weight: .bold))
                        .foregroundColor(.white)
                    
                    Text("Select your allergies to get started")
                        .font(.system(size: 17))
                        .foregroundColor(.white.opacity(0.8))
                }
                .padding(.bottom, 32)
                
                // Search Bar
                HStack {
                    Image(systemName: "magnifyingglass")
                        .foregroundColor(.gray)
                    
                    TextField("Search allergies...", text: $searchQuery)
                        .font(.system(size: 17))
                }
                .padding()
                .background(.white.opacity(0.95))
                .cornerRadius(12)
                .padding(.horizontal, 20)
                .padding(.bottom, 24)
                
                // Allergy Grid
                ScrollView {
                    LazyVGrid(columns: [
                        GridItem(.flexible(), spacing: 16),
                        GridItem(.flexible(), spacing: 16)
                    ], spacing: 16) {
                        ForEach(filteredAllergies) { allergy in
                            AllergyCard(
                                allergy: allergy,
                                isSelected: selectedAllergies.contains(allergy.id)
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
                    .padding(.horizontal, 20)
                    .padding(.bottom, 100)
                }
            }
            
            // Continue Button
            VStack {
                Spacer()
                
                Button {
                    withAnimation(.spring(response: 0.4, dampingFraction: 0.8)) {
                        appState.userAllergies = Array(selectedAllergies)
                        appState.currentScreen = .dashboard
                    }
                } label: {
                    Text("Continue (\(selectedAllergies.count))")
                        .font(.system(size: 17, weight: .semibold))
                        .foregroundColor(.white)
                        .frame(maxWidth: .infinity)
                        .frame(height: 56)
                        .background(
                            LinearGradient(
                                colors: selectedAllergies.isEmpty
                                    ? [Color.gray.opacity(0.4), Color.gray.opacity(0.4)]
                                    : [Color(hex: "06b6d4"), Color(hex: "0e7490")],
                                startPoint: .leading,
                                endPoint: .trailing
                            )
                        )
                        .cornerRadius(14)
                        .shadow(color: selectedAllergies.isEmpty ? .clear : Color(hex: "06b6d4").opacity(0.3), radius: 12, y: 4)
                }
                .disabled(selectedAllergies.isEmpty)
                .padding(.horizontal, 20)
                .padding(.bottom, 20)
                .background(
                    LinearGradient(
                        colors: [.clear, .black.opacity(0.2)],
                        startPoint: .top,
                        endPoint: .bottom
                    )
                )
            }
        }
    }
}

struct AllergyCard: View {
    let allergy: Allergy
    let isSelected: Bool
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            VStack(spacing: 12) {
                Text(allergy.emoji)
                    .font(.system(size: 48))
                
                Text(allergy.name)
                    .font(.system(size: 17, weight: .semibold))
                    .foregroundColor(isSelected ? Color(hex: "06b6d4") : Color(hex: "1f2937"))
                    .shadow(color: .black.opacity(0.1), radius: 1, y: 1)
            }
            .frame(maxWidth: .infinity)
            .frame(height: 140)
            .background(.white.opacity(isSelected ? 1.0 : 0.98))
            .cornerRadius(20)
            .overlay(
                RoundedRectangle(cornerRadius: 20)
                    .stroke(
                        isSelected ? Color(hex: "06b6d4") : Color.white.opacity(0.3),
                        lineWidth: 3
                    )
            )
            .shadow(
                color: isSelected ? Color(hex: "06b6d4").opacity(0.25) : .black.opacity(0.12),
                radius: isSelected ? 10 : 6,
                y: 4
            )
            .overlay(
                Group {
                    if isSelected {
                        Image(systemName: "checkmark")
                            .font(.system(size: 12, weight: .bold))
                            .foregroundColor(.white)
                            .frame(width: 24, height: 24)
                            .background(Color(hex: "06b6d4"))
                            .clipShape(Circle())
                            .shadow(radius: 4)
                            .offset(x: 8, y: -8)
                    }
                },
                alignment: .topTrailing
            )
        }
        .buttonStyle(ScaleButtonStyle())
    }
}
