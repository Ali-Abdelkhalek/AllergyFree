//
//  InputSheetView.swift
//  AllergyFree
//
//  Created by Ali Abdelkhalek on 04/01/2026.
//


import SwiftUI

struct InputSheetView: View {
    @ObservedObject var appState: AppState
    @Environment(\.dismiss) var dismiss
    @State private var ingredients = ""
    @State private var country = ""
    @State private var showImagePicker = false
    @State private var isAnalyzing = false
    
    var isDark: Bool {
        appState.theme == .dark
    }
    
    var body: some View {
        NavigationView {
            ZStack {
                (isDark ? Color(hex: "1c1c1e") : .white)
                    .ignoresSafeArea()
                
                VStack(spacing: 0) {
                    // Custom Header
                    HStack {
                        Spacer()
                        
                        Text("Check Food Safety")
                            .font(.system(size: 20, weight: .semibold))
                            .foregroundColor(isDark ? .white : Color(hex: "111827"))
                        
                        Spacer()
                    }
                    .overlay(
                        Button {
                            dismiss()
                        } label: {
                            Image(systemName: "xmark")
                                .font(.system(size: 16, weight: .semibold))
                                .foregroundColor(isDark ? .white : Color(hex: "4b5563"))
                                .frame(width: 32, height: 32)
                                .background(isDark ? Color(hex: "2c2c2e") : Color(hex: "f3f4f6"))
                                .clipShape(Circle())
                        },
                        alignment: .trailing
                    )
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
                            // Image Section
                            VStack(alignment: .leading, spacing: 8) {
                                Text("Food Image (Optional)")
                                    .font(.system(size: 15, weight: .medium))
                                    .foregroundColor(isDark ? .gray : Color(hex: "4b5563"))
                                
                                Button {
                                    showImagePicker = true
                                } label: {
                                    VStack(spacing: 12) {
                                        Image(systemName: "camera.fill")
                                            .font(.system(size: 36))
                                            .foregroundColor(Color(hex: "06b6d4"))
                                        
                                        Text("Tap to capture or select photo")
                                            .font(.system(size: 15))
                                            .foregroundColor(isDark ? .white : Color(hex: "111827"))
                                        
                                        Text("We'll analyze the ingredients")
                                            .font(.system(size: 13))
                                            .foregroundColor(.gray)
                                    }
                                    .frame(maxWidth: .infinity)
                                    .frame(height: 180)
                                    .background(isDark ? Color(hex: "2c2c2e") : Color(hex: "f8fafc"))
                                    .overlay(
                                        RoundedRectangle(cornerRadius: 16)
                                            .stroke(
                                                isDark ? Color(hex: "3a3a3c") : Color(hex: "d1d5db"),
                                                style: StrokeStyle(lineWidth: 2, dash: [8])
                                            )
                                    )
                                    .cornerRadius(16)
                                }
                            }
                            
                            // Country Field
                            VStack(alignment: .leading, spacing: 8) {
                                Text("Country of origin (Optional)")
                                    .font(.system(size: 15, weight: .medium))
                                    .foregroundColor(isDark ? .gray : Color(hex: "4b5563"))
                                
                                TextField("e.g., Italy 🇮🇹", text: $country)
                                    .font(.system(size: 17))
                                    .padding()
                                    .background(isDark ? Color(hex: "2c2c2e") : .white)
                                    .overlay(
                                        RoundedRectangle(cornerRadius: 12)
                                            .stroke(isDark ? Color(hex: "3a3a3c") : Color(hex: "d1d5db"), lineWidth: 1)
                                    )
                                    .cornerRadius(12)
                            }
                            
                            // Ingredients Field
                            VStack(alignment: .leading, spacing: 8) {
                                Text("Enter ingredients or describe the meal")
                                    .font(.system(size: 15, weight: .medium))
                                    .foregroundColor(isDark ? .gray : Color(hex: "4b5563"))
                                
                                ZStack(alignment: .topLeading) {
                                    if ingredients.isEmpty {
                                        Text("e.g., Wheat flour, sugar, eggs, butter, milk, vanilla extract...")
                                            .font(.system(size: 17))
                                            .foregroundColor(isDark ? Color(hex: "4b5563") : Color(hex: "9ca3af"))
                                            .padding(.horizontal, 16)
                                            .padding(.vertical, 14)
                                    }
                                    
                                    TextEditor(text: $ingredients)
                                        .font(.system(size: 17))
                                        .frame(height: 120)
                                        .scrollContentBackground(.hidden)
                                        .padding(8)
                                }
                                .background(isDark ? Color(hex: "2c2c2e") : .white)
                                .overlay(
                                    RoundedRectangle(cornerRadius: 12)
                                        .stroke(isDark ? Color(hex: "3a3a3c") : Color(hex: "d1d5db"), lineWidth: 1)
                                )
                                .cornerRadius(12)
                                
                                HStack {
                                    Spacer()
                                    Text("\(ingredients.count) characters")
                                        .font(.system(size: 13))
                                        .foregroundColor(.gray)
                                }
                            }
                        }
                        .padding(24)
                        .padding(.bottom, 80)
                    }
                    
                    // Analyze Button
                    VStack {
                        Button {
                            analyzeFood()
                        } label: {
                            HStack(spacing: 8) {
                                if isAnalyzing {
                                    ProgressView()
                                        .progressViewStyle(CircularProgressViewStyle(tint: .white))
                                    Text("Analyzing...")
                                } else {
                                    Text("Analyze Ingredients")
                                }
                            }
                            .font(.system(size: 17, weight: .semibold))
                            .foregroundColor(.white)
                            .frame(maxWidth: .infinity)
                            .frame(height: 56)
                            .background(
                                LinearGradient(
                                    colors: ingredients.isEmpty
                                        ? [Color.gray.opacity(0.4), Color.gray.opacity(0.4)]
                                        : [Color(hex: "06b6d4"), Color(hex: "0e7490")],
                                    startPoint: .leading,
                                    endPoint: .trailing
                                )
                            )
                            .cornerRadius(14)
                            .shadow(
                                color: ingredients.isEmpty ? .clear : Color(hex: "06b6d4").opacity(0.3),
                                radius: 6,
                                y: 4
                            )
                        }
                        .disabled(ingredients.isEmpty || isAnalyzing)
                        .padding(.horizontal, 24)
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
    }
    
    func analyzeFood() {
        isAnalyzing = true
        
        // Simulate analysis
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
            let ingredientList = ingredients.split(separator: ",").map { String($0).trimmingCharacters(in: .whitespaces) }
            let detectedAllergens = appState.userAllergies.filter { allergyId in
                ingredientList.contains { ingredient in
                    ingredient.localizedCaseInsensitiveContains(allergyId) ||
                    ingredient.localizedCaseInsensitiveContains(allergiesList.first(where: { $0.id == allergyId })?.name ?? "")
                }
            }
            
            let result = ScanResult(
                timestamp: Date(),
                status: detectedAllergens.isEmpty ? .safe : .danger,
                preview: ingredients.prefix(50) + "...",
                allergens: detectedAllergens,
                ingredients: ingredientList
            )
            
            appState.currentResult = result
            appState.recentScans.insert(result, at: 0)
            isAnalyzing = false
            dismiss()
            
            withAnimation(.spring(response: 0.4, dampingFraction: 0.8)) {
                appState.currentScreen = .results
            }
        }
    }
}