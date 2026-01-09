package com.allergyfree.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.allergyfree.models.Allergy
import com.allergyfree.models.Screen
import com.allergyfree.models.allergiesList
import com.allergyfree.ui.components.ScaleButton
import com.allergyfree.ui.theme.*
import com.allergyfree.viewmodels.AppViewModel

@Composable
fun OnboardingScreen(viewModel: AppViewModel) {
    var selectedAllergies by remember { mutableStateOf(setOf<String>()) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredAllergies = if (searchQuery.isEmpty()) {
        allergiesList
    } else {
        allergiesList.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Cyan500, Cyan700, Emerald400)
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp, bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(64.dp)
                )

                Text(
                    text = "Welcome to AllergyFree",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Select your allergies to get started",
                    fontSize = 17.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 24.dp),
                placeholder = { Text("Search allergies...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Gray500
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White.copy(alpha = 0.95f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.95f),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp)
            )

            // Allergy Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(filteredAllergies) { allergy ->
                    AllergyCard(
                        allergy = allergy,
                        isSelected = selectedAllergies.contains(allergy.id)
                    ) {
                        selectedAllergies = if (selectedAllergies.contains(allergy.id)) {
                            selectedAllergies - allergy.id
                        } else {
                            selectedAllergies + allergy.id
                        }
                    }
                }
            }
        }

        // Continue Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.2f))
                    )
                )
                .padding(20.dp)
        ) {
            ContinueButton(
                count = selectedAllergies.size,
                enabled = selectedAllergies.isNotEmpty()
            ) {
                viewModel.updateAllergies(selectedAllergies.toList())
                viewModel.navigateTo(Screen.DASHBOARD)
            }
        }
    }
}

@Composable
fun AllergyCard(
    allergy: Allergy,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val shadowElevation by animateFloatAsState(
        targetValue = if (isSelected) 10f else 6f,
        animationSpec = spring(dampingRatio = 0.7f),
        label = "shadow"
    )

    ScaleButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .shadow(
                    elevation = shadowElevation.dp,
                    shape = RoundedCornerShape(20.dp),
                    spotColor = if (isSelected) Cyan500.copy(alpha = 0.25f) else Color.Black.copy(alpha = 0.12f)
                )
                .background(
                    color = Color.White.copy(alpha = if (isSelected) 1.0f else 0.98f),
                    shape = RoundedCornerShape(20.dp)
                )
                .border(
                    width = 3.dp,
                    color = if (isSelected) Cyan500 else Color.White.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = allergy.emoji,
                    fontSize = 48.sp
                )

                Text(
                    text = allergy.name,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isSelected) Cyan500 else Gray800
                )
            }

            if (isSelected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 8.dp, y = (-8).dp)
                        .size(24.dp)
                        .shadow(4.dp, CircleShape)
                        .background(Cyan500, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ContinueButton(
    count: Int,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val gradient = if (enabled) {
        Brush.horizontalGradient(colors = listOf(Cyan500, Cyan700))
    } else {
        Brush.horizontalGradient(colors = listOf(Gray400.copy(alpha = 0.4f), Gray400.copy(alpha = 0.4f)))
    }

    ScaleButton(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .shadow(
                    elevation = if (enabled) 12.dp else 0.dp,
                    shape = RoundedCornerShape(14.dp),
                    spotColor = Cyan500.copy(alpha = 0.3f)
                )
                .background(brush = gradient, shape = RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Continue ($count)",
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
