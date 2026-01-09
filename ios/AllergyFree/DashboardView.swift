//
//  DashboardView.swift
//  AllergyFree
//
//  Created by Ali Abdelkhalek on 04/01/2026.
//


import SwiftUI

struct DashboardView: View {
    @ObservedObject var appState: AppState
    @State private var showInputSheet = false
    
    var isDark: Bool {
        appState.theme == .dark
    }
    
    var body: some View {
        ScrollView {
            VStack(spacing: 24) {
                // Header Card
                VStack(alignment: .leading, spacing: 16) {
                    HStack {
                        VStack(alignment: .leading, spacing: 4) {
                            Text("AllergyFree")
                                .font(.system(size: 28, weight: .bold))
                                .foregroundColor(Color(hex: "06b6d4"))
                            
                            Text("Stay safe, eat freely")
                                .font(.system(size: 15))
                                .foregroundColor(isDark ? .gray : Color(hex: "6b7280"))
                        }
                        
                        Spacer()
                        
                        Button {
                            withAnimation(.spring(response: 0.3, dampingFraction: 0.7)) {
                                appState.currentScreen = .settings
                            }
                        } label: {
                            Image(systemName: "gearshape.fill")
                                .font(.system(size: 20))
                                .foregroundColor(isDark ? .white : Color(hex: "4b5563"))
                                .frame(width: 40, height: 40)
                                .background(isDark ? Color(hex: "2c2c2e") : Color(hex: "f3f4f6"))
                                .clipShape(Circle())
                        }
                    }
                    
                    // Active Allergies
                    ScrollView(.horizontal, showsIndicators: false) {
                        HStack(spacing: 8) {
                            ForEach(appState.userAllergies, id: \.self) { allergyId in
                                if let allergy = allergiesList.first(where: { $0.id == allergyId }) {
                                    AllergyPill(allergy: allergy, isDark: isDark)
                                }
                            }
                        }
                    }
                }
                .padding(24)
                .background(isDark ? Color(hex: "1c1c1e").opacity(0.95) : Color.white.opacity(0.95))
                .cornerRadius(24)
                .shadow(color: isDark ? .black.opacity(0.3) : .black.opacity(0.08), radius: 8, y: 4)
                .padding(.horizontal, 20)
                .padding(.top, 32)
                
                // Check Food Button
                Button {
                    showInputSheet = true
                } label: {
                    VStack(spacing: 16) {
                        ZStack {
                            Circle()
                                .fill(
                                    LinearGradient(
                                        colors: [Color(hex: "06b6d4"), Color(hex: "0e7490")],
                                        startPoint: .topLeading,
                                        endPoint: .bottomTrailing
                                    )
                                )
                                .frame(width: 64, height: 64)
                                .shadow(color: Color(hex: "06b6d4").opacity(0.3), radius: 6, y: 4)
                            
                            Image(systemName: "sparkles")
                                .font(.system(size: 28))
                                .foregroundColor(.white)
                        }
                        
                        Text("Check Food Safety")
                            .font(.system(size: 24, weight: .bold))
                            .foregroundColor(isDark ? .white : Color(hex: "111827"))
                        
                        Text("Scan or enter ingredients")
                            .font(.system(size: 15))
                            .foregroundColor(isDark ? Color(hex: "9ca3af") : Color(hex: "6b7280"))
                    }
                    .frame(maxWidth: .infinity)
                    .frame(height: 200)
                    .background(isDark ? Color(hex: "1c1c1e") : .white)
                    .cornerRadius(24)
                    .shadow(color: isDark ? .black.opacity(0.3) : .black.opacity(0.12), radius: 12, y: 8)
                }
                .buttonStyle(ScaleButtonStyle())
                .padding(.horizontal, 20)
                
                // Recent Scans
                VStack(alignment: .leading, spacing: 16) {
                    HStack(spacing: 8) {
                        Image(systemName: "clock.fill")
                            .foregroundColor(isDark ? .gray : Color(hex: "4b5563"))
                        
                        Text("Recent Scans")
                            .font(.system(size: 20, weight: .semibold))
                            .foregroundColor(isDark ? .white : Color(hex: "111827"))
                    }
                    
                    if appState.recentScans.isEmpty {
                        VStack(spacing: 12) {
                            Image(systemName: "clock")
                                .font(.system(size: 48))
                                .foregroundColor(isDark ? Color(hex: "3a3a3c") : Color(hex: "d1d5db"))
                            
                            Text("No scans yet")
                                .font(.system(size: 17, weight: .medium))
                                .foregroundColor(isDark ? .gray : Color(hex: "6b7280"))
                            
                            Text("Your recent checks will appear here")
                                .font(.system(size: 15))
                                .foregroundColor(isDark ? Color(hex: "4b5563") : Color(hex: "9ca3af"))
                        }
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 48)
                    } else {
                        ForEach(appState.recentScans) { scan in
                            RecentScanCard(scan: scan, isDark: isDark)
                        }
                    }
                }
                .padding(.horizontal, 20)
                .padding(.bottom, 32)
            }
        }
        .background(isDark ? Color.black : Color(hex: "f8fafc"))
        .sheet(isPresented: $showInputSheet) {
            InputSheetView(appState: appState)
        }
    }
}

struct AllergyPill: View {
    let allergy: Allergy
    let isDark: Bool
    
    var body: some View {
        HStack(spacing: 6) {
            Text(allergy.emoji)
                .font(.system(size: 16))
            
            Text(allergy.name)
                .font(.system(size: 15, weight: .medium))
                .foregroundColor(Color(hex: "06b6d4"))
        }
        .padding(.horizontal, 12)
        .padding(.vertical, 6)
        .background(isDark ? Color(hex: "1c1c1e") : .white)
        .overlay(
            RoundedRectangle(cornerRadius: 20)
                .stroke(Color(hex: "06b6d4"), lineWidth: 2)
        )
        .cornerRadius(20)
    }
}

struct RecentScanCard: View {
    let scan: ScanResult
    let isDark: Bool
    
    var statusColor: Color {
        switch scan.status {
        case .safe: return Color(hex: "10b981")
        case .warning: return Color(hex: "f59e0b")
        case .danger: return Color(hex: "ef4444")
        }
    }
    
    var statusIcon: String {
        switch scan.status {
        case .safe: return "checkmark.circle.fill"
        case .warning: return "exclamationmark.triangle.fill"
        case .danger: return "xmark.circle.fill"
        }
    }
    
    var statusText: String {
        switch scan.status {
        case .safe: return "SAFE"
        case .warning: return "WARNING"
        case .danger: return "DANGER"
        }
    }
    
    var body: some View {
        HStack(spacing: 16) {
            VStack(alignment: .leading, spacing: 8) {
                Text(statusText)
                    .font(.system(size: 12, weight: .bold))
                    .foregroundColor(statusColor)
                    .padding(.horizontal, 12)
                    .padding(.vertical, 4)
                    .background(statusColor.opacity(0.15))
                    .cornerRadius(12)
                
                Text(scan.preview)
                    .font(.system(size: 15))
                    .foregroundColor(isDark ? .white : Color(hex: "111827"))
                    .lineLimit(2)
                
                Text(scan.timestamp, style: .relative)
                    .font(.system(size: 13))
                    .foregroundColor(.gray)
            }
            
            Spacer()
            
            Image(systemName: statusIcon)
                .font(.system(size: 24))
                .foregroundColor(statusColor)
                .frame(width: 48, height: 48)
                .background(isDark ? Color(hex: "2c2c2e") : .white)
                .clipShape(Circle())
                .shadow(color: isDark ? .black.opacity(0.3) : .black.opacity(0.08), radius: 4)
        }
        .padding(16)
        .background(isDark ? Color(hex: "1c1c1e") : .white)
        .cornerRadius(16)
        .overlay(
            Rectangle()
                .fill(statusColor)
                .frame(width: 4)
                .cornerRadius(2),
            alignment: .leading
        )
        .shadow(color: isDark ? .black.opacity(0.3) : .black.opacity(0.06), radius: 4, y: 2)
    }
}
