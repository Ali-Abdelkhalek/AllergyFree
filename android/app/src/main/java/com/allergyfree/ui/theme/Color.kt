package com.allergyfree.ui.theme

import androidx.compose.ui.graphics.Color

// Primary Colors
val Cyan500 = Color(0xFF06b6d4)
val Cyan700 = Color(0xFF0e7490)
val Emerald400 = Color(0xFFa7f3d0)
val Green500 = Color(0xFF10b981)
val Green400 = Color(0xFF34d399)
val Amber500 = Color(0xFFf59e0b)
val Red500 = Color(0xFFef4444)
val Red400 = Color(0xFFf87171)

// Neutral Colors - Light Mode
val Gray50 = Color(0xFFf8fafc)
val Gray100 = Color(0xFFf3f4f6)
val Gray200 = Color(0xFFe5e7eb)
val Gray300 = Color(0xFFd1d5db)
val Gray400 = Color(0xFF9ca3af)
val Gray500 = Color(0xFF6b7280)
val Gray600 = Color(0xFF4b5563)
val Gray700 = Color(0xFF374151)
val Gray800 = Color(0xFF1f2937)
val Gray900 = Color(0xFF111827)

// Dark Mode Colors
val Dark900 = Color(0xFF000000)
val Dark800 = Color(0xFF1c1c1e)
val Dark700 = Color(0xFF2c2c2e)
val Dark600 = Color(0xFF3a3a3c)
val Dark500 = Color(0xFF48484a)

// Utility function to parse hex colors (like the iOS extension)
fun Color.Companion.fromHex(hex: String): Color {
    val cleanHex = hex.removePrefix("#")
    return Color(android.graphics.Color.parseColor("#$cleanHex"))
}
