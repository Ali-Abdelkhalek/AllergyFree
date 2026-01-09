package com.allergyfree.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.allergyfree.models.ScanResult
import com.allergyfree.models.ScanStatus
import com.allergyfree.models.Screen
import com.allergyfree.models.Theme
import com.allergyfree.models.allergiesList
import com.allergyfree.ui.components.GradientButton
import com.allergyfree.ui.components.ScaleButton
import com.allergyfree.ui.theme.*
import com.allergyfree.viewmodels.AppViewModel

@Composable
fun ResultsScreen(
    viewModel: AppViewModel,
    result: ScanResult
) {
    val isDark = viewModel.theme == Theme.DARK
    val isSafe = result.status == ScanStatus.SAFE

    val headerGradient = if (isSafe) {
        Brush.linearGradient(colors = listOf(Green500, Green400))
    } else {
        Brush.linearGradient(colors = listOf(Red500, Red400))
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDark) Dark900 else Gray50)
    ) {
        // Header Section
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(
                        brush = headerGradient,
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Back Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        ScaleButton(onClick = { viewModel.navigateTo(Screen.DASHBOARD) }) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .shadow(8.dp, CircleShape)
                                    .background(Color.White, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Gray800,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Icon
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .shadow(12.dp, CircleShape)
                            .background(Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isSafe) Icons.Default.CheckCircle else Icons.Default.Cancel,
                            contentDescription = null,
                            tint = if (isSafe) Cyan500 else Red500,
                            modifier = Modifier.size(64.dp)
                        )
                    }

                    // Title
                    Text(
                        text = if (isSafe) "Safe to eat!" else "Not safe to eat",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    // Allergens or Safe Message
                    if (isSafe) {
                        Text(
                            text = "No allergens detected in this food",
                            fontSize = 17.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            result.allergens.forEach { allergenId ->
                                allergiesList.find { it.id == allergenId }?.let { allergy ->
                                    Row(
                                        modifier = Modifier
                                            .shadow(8.dp, RoundedCornerShape(20.dp))
                                            .background(Color.White, RoundedCornerShape(20.dp))
                                            .padding(horizontal = 16.dp, vertical = 8.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = allergy.emoji, fontSize = 20.sp)
                                        Text(
                                            text = "Contains ${allergy.name}",
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Red500
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Ingredients Analysis Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .offset(y = (-48).dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Ingredient Analysis",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDark) Color.White else Gray900
                )

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    result.ingredients.forEach { ingredient ->
                        val isAllergen = result.allergens.any { allergenId ->
                            val allergyName = allergiesList.find { it.id == allergenId }?.name ?: ""
                            ingredient.contains(allergenId, ignoreCase = true) ||
                                    ingredient.contains(allergyName, ignoreCase = true)
                        }
                        IngredientRow(ingredient = ingredient, isAllergen = isAllergen, isDark = isDark)
                    }
                }
            }
        }

        // Action Buttons
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ActionButton(
                    icon = Icons.Default.Bookmark,
                    title = "Save to History",
                    isDark = isDark,
                    onClick = { /* Save action */ }
                )

                ActionButton(
                    icon = Icons.Default.Share,
                    title = "Share Result",
                    isDark = isDark,
                    onClick = { /* Share action */ }
                )

                GradientButton(
                    text = "Check Another Food",
                    onClick = {
                        viewModel.navigateTo(Screen.DASHBOARD)
                        viewModel.showInputSheet = true
                    }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun IngredientRow(
    ingredient: String,
    isAllergen: Boolean,
    isDark: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = when {
                    isAllergen -> Red500.copy(alpha = 0.08f)
                    isDark -> Gray400.copy(alpha = 0.05f)
                    else -> Gray400.copy(alpha = 0.1f)
                },
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (isAllergen) Icons.Default.Cancel else Icons.Default.CheckCircle,
            contentDescription = null,
            tint = if (isAllergen) Red500 else Green500,
            modifier = Modifier.size(16.dp)
        )

        Text(
            text = ingredient,
            fontSize = 15.sp,
            fontWeight = if (isAllergen) FontWeight.SemiBold else FontWeight.Normal,
            color = when {
                isAllergen -> Red500
                isDark -> Gray300
                else -> Gray700
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    isDark: Boolean,
    onClick: () -> Unit
) {
    ScaleButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(
                    color = if (isDark) Dark800 else Color.White,
                    shape = RoundedCornerShape(14.dp)
                )
                .border(
                    width = 2.dp,
                    color = if (isDark) Dark600 else Gray200,
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isDark) Gray400 else Gray600
            )

            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isDark) Color.White else Gray900
            )
        }
    }
}
