package com.allergyfree.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.allergyfree.ui.theme.*

// Scale Button Effect (like ScaleButtonStyle in iOS)
@Composable
fun ScaleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = 0.7f, stiffness = 300f),
        label = "scale"
    )

    Box(
        modifier = modifier
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

// Gradient Button
@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    val gradient = if (enabled) {
        Brush.horizontalGradient(colors = listOf(Cyan500, Cyan700))
    } else {
        Brush.horizontalGradient(colors = listOf(Gray400, Gray400))
    }

    ScaleButton(
        onClick = onClick,
        enabled = enabled && !isLoading,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .shadow(
                    elevation = if (enabled) 6.dp else 0.dp,
                    shape = RoundedCornerShape(14.dp),
                    spotColor = Cyan500.copy(alpha = 0.3f)
                )
                .background(brush = gradient, shape = RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                    Text(
                        text = "Analyzing...",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            } else {
                Text(
                    text = text,
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

// Icon Button (like the settings button)
@Composable
fun IconCircleButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDark: Boolean = false
) {
    ScaleButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = if (isDark) Dark700 else Gray100,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isDark) Color.White else Gray600,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// Allergy Pill Component
@Composable
fun AllergyPill(
    emoji: String,
    name: String,
    isDark: Boolean = false
) {
    Row(
        modifier = Modifier
            .background(
                color = if (isDark) Dark800 else Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 2.dp,
                color = Cyan500,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = emoji,
            fontSize = 16.sp
        )
        Text(
            text = name,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Cyan500
        )
    }
}
