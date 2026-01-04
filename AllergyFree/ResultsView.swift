//
//  ResultsView.swift
//  AllergyFree
//
//  Created by Ali Abdelkhalek on 04/01/2026.
//


import SwiftUI

struct ResultsView: View {
    let result: ScanResult
    @ObservedObject var appState: AppState
    
    var isDark: Bool {
        appState.theme == .dark
    }
    
    var isSafe: Bool {
        result.status == .safe
    }
    
    var headerGradient: LinearGradient {
        if isSafe {
            return LinearGradient(
                colors: [Color(hex: "10b981"), Color(hex: "34d399")],
                startPoint: .topLeading,
                endPoint: .bottomTrailing
            )
        } else {
            return LinearGradient(
                colors: [Color(hex: "ef4444"), Color(hex: "f87171")],
                startPoint: .topLeading,
                endPoint: .bottomTrailing
            )
        }
    }
    
    var body: some View {
        ScrollView {
            VStack(spacing: 0) {
                // Header
                ZStack(alignment: .topLeading) {
                    Rectangle()
                        .fill(headerGradient)
                        .frame(height: 400)
                        .cornerRadius(32, corners: [.bottomLeft, .bottomRight])
                    
                    VStack(spacing: 24) {
                        // Back Button
                        HStack {
                            Button {
                                withAnimation(.spring(response: 0.4, dampingFraction: 0.8)) {
                                    appState.currentScreen = .dashboard
                                }
                            } label: {
                                Image(systemName: "arrow.left")
                                    .font(.system(size: 20, weight: .semibold))
                                    .foregroundColor(Color(hex: "1f2937"))
                                    .frame(width: 40, height: 40)
                                    .background(.white)
                                    .clipShape(Circle())
                                    .shadow(radius: 8)
                            }
                            
                            Spacer()
                        }
                        .padding(.top, 60)
                        
                        // Icon
                        ZStack {
                            Circle()
                                .fill(.white)
                                .frame(width: 120, height: 120)
                                .shadow(radius: 12)
                            
                            Image(systemName: isSafe ? "checkmark.circle.fill" : "xmark.circle.fill")
                                .font(.system(size: 64))
                                .foregroundColor(isSafe ? Color(hex: "06b6d4") : Color(hex: "ef4444"))
                        }
                        
                        // Title
                        Text(isSafe ? "Safe to eat!" : "Not safe to eat")
                            .font(.system(size: 28, weight: .bold))
                            .foregroundColor(.white)
                        
                        // Allergens or Safe Message
                        if isSafe {
                            Text("No allergens detected in this food")
                                .font(.system(size: 17))
                                .foregroundColor(.white.opacity(0.9))
                        } else {
                            VStack(spacing: 8) {
                                ForEach(result.allergens, id: \.self) { allergenId in
                                    if let allergy = allergiesList.first(where: { $0.id == allergenId }) {
                                        HStack(spacing: 8) {
                                            Text(allergy.emoji)
                                                .font(.system(size: 20))
                                            Text("Contains \(allergy.name)")
                                                .font(.system(size: 15, weight: .semibold))
                                                .foregroundColor(Color(hex: "ef4444"))
                                        }
                                        .padding(.horizontal, 16)
                                        .padding(.vertical, 8)
                                        .background(.white)
                                        .cornerRadius(20)
                                        .shadow(radius: 8)
                                    }
                                }
                            }
                        }
                    }
                    .padding(.horizontal, 24)
                }
                
                // Ingredients Analysis
                VStack(alignment: .leading, spacing: 16) {
                    Text("Ingredient Analysis")
                        .font(.system(size: 20, weight: .semibold))
                        .foregroundColor(isDark ? .white : Color(hex: "111827"))
                        .padding(.top, 24)
                    
                    VStack(spacing: 10) {
                        ForEach(result.ingredients, id: \.self) { ingredient in
                            IngredientRow(
                                ingredient: ingredient,
                                isAllergen: result.allergens.contains { allergenId in
                                    ingredient.localizedCaseInsensitiveContains(allergenId) ||
                                    ingredient.localizedCaseInsensitiveContains(allergiesList.first(where: { $0.id == allergenId })?.name ?? "")
                                },
                                isDark: isDark
                            )
                        }
                    }
                }
                .padding(.horizontal, 24)
                .padding(.top, -48)
                
                // Action Buttons
                VStack(spacing: 12) {
                    ActionButton(
                        icon: "bookmark",
                        title: "Save to History",
                        isDark: isDark
                    ) {
                        // Save action
                    }
                    
                    ActionButton(
                        icon: "square.and.arrow.up",
                        title: "Share Result",
                        isDark: isDark
                    ) {
                        // Share action
                    }
                    
                    Button {
                        withAnimation(.spring(response: 0.4, dampingFraction: 0.8)) {
                            appState.currentScreen = .dashboard
                            DispatchQueue.main.asyncAfter(deadline: .now() + 0.3) {
                                appState.showInputSheet = true
                            }
                        }
                    } label: {
                        Text("Check Another Food")
                            .font(.system(size: 17, weight: .semibold))
                            .foregroundColor(.white)
                            .frame(maxWidth: .infinity)
                            .frame(height: 52)
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
                }
                .padding(.horizontal, 24)
                .padding(.top, 24)
                .padding(.bottom, 32)
            }
        }
        .background(isDark ? Color.black : Color(hex: "f8fafc"))
        .ignoresSafeArea(edges: .top)
    }
}

struct IngredientRow: View {
    let ingredient: String
    let isAllergen: Bool
    let isDark: Bool
    
    var body: some View {
        HStack(spacing: 12) {
            Image(systemName: isAllergen ? "xmark.circle.fill" : "checkmark.circle.fill")
                .font(.system(size: 16))
                .foregroundColor(isAllergen ? Color(hex: "ef4444") : Color(hex: "10b981"))
                .frame(width: 24, height: 24)
            
            Text(ingredient)
                .font(.system(size: 15, weight: isAllergen ? .semibold : .regular))
                .foregroundColor(
                    isAllergen
                    ? Color(hex: "ef4444")
                    : (isDark ? Color(hex: "d1d5db") : Color(hex: "374151"))
                )
            
            Spacer()
        }
        .padding(12)
        .background(
            isAllergen
            ? Color(hex: "ef4444").opacity(0.08)
            : (isDark ? Color(hex: "9ca3af").opacity(0.05) : Color(hex: "9ca3af").opacity(0.1))
        )
        .cornerRadius(12)
    }
}

struct ActionButton: View {
    let icon: String
    let title: String
    let isDark: Bool
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            HStack(spacing: 12) {
                Image(systemName: icon)
                    .foregroundColor(isDark ? .gray : Color(hex: "4b5563"))
                
                Text(title)
                    .font(.system(size: 17, weight: .semibold))
                    .foregroundColor(isDark ? .white : Color(hex: "111827"))
            }
            .frame(maxWidth: .infinity)
            .frame(height: 52)
            .background(isDark ? Color(hex: "1c1c1e") : .white)
            .overlay(
                RoundedRectangle(cornerRadius: 14)
                    .stroke(isDark ? Color(hex: "3a3a3c") : Color(hex: "e5e7eb"), lineWidth: 2)
            )
            .cornerRadius(14)
        }
    }
}
