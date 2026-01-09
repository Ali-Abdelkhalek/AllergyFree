package com.allergyfree.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.allergyfree.models.Allergy
import com.allergyfree.models.Screen
import com.allergyfree.models.Theme
import com.allergyfree.models.allergiesList
import com.allergyfree.ui.components.GradientButton
import com.allergyfree.ui.components.ScaleButton
import com.allergyfree.ui.theme.*
import com.allergyfree.viewmodels.AppViewModel

@Composable
fun SettingsScreen(viewModel: AppViewModel) {
    var selectedAllergies by remember { mutableStateOf(viewModel.userAllergies.toSet()) }
    val isDark = viewModel.theme == Theme.DARK

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDark) Dark900 else Gray50)
    ) {
        // Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = if (isDark) Dark800 else Color.White,
            shadowElevation = 0.5.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ScaleButton(
                    onClick = {
                        viewModel.updateAllergies(selectedAllergies.toList())
                        viewModel.navigateTo(Screen.DASHBOARD)
                    }
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
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = if (isDark) Color.White else Gray700,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Settings",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isDark) Color.White else Gray900
                )

                Spacer(modifier = Modifier.weight(1f))

                Spacer(modifier = Modifier.width(40.dp))
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Allergies Section Header
            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Your Allergies",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isDark) Color.White else Gray900
                    )
                    Text(
                        text = "Select all that apply to you",
                        fontSize = 15.sp,
                        color = if (isDark) Gray400 else Gray500
                    )
                }
            }

            // Allergies List
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = if (isDark) 4.dp else 2.dp,
                            shape = RoundedCornerShape(24.dp),
                            spotColor = if (isDark) Color.Black.copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.06f)
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isDark) Dark800 else Color.White
                    )
                ) {
                    Column {
                        allergiesList.forEachIndexed { index, allergy ->
                            AllergySettingRow(
                                allergy = allergy,
                                isSelected = selectedAllergies.contains(allergy.id),
                                isLast = index == allergiesList.lastIndex,
                                isDark = isDark
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
            }

            // Info Card
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Cyan500.copy(alpha = if (isDark) 0.1f else 0.05f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Cyan500.copy(alpha = if (isDark) 0.3f else 0.2f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = "💡", fontSize = 20.sp)

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                                append("Tip: ")
                            }
                            append("Make sure to select all your allergies for accurate food safety checks.")
                        },
                        fontSize = 15.sp,
                        color = if (isDark) Gray300 else Gray600
                    )
                }
            }

            // Count
            item {
                Text(
                    text = "${selectedAllergies.size} ${if (selectedAllergies.size == 1) "allergy" else "allergies"} selected",
                    fontSize = 15.sp,
                    color = if (isDark) Gray400 else Gray500,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Appearance Section
            item {
                Text(
                    text = "Appearance",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDark) Color.White else Gray900,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Theme Toggle
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = if (isDark) 4.dp else 2.dp,
                            shape = RoundedCornerShape(24.dp),
                            spotColor = if (isDark) Color.Black.copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.06f)
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isDark) Dark800 else Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
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
                                imageVector = if (isDark) Icons.Default.DarkMode else Icons.Default.LightMode,
                                contentDescription = null,
                                tint = Cyan500
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = if (isDark) "Dark Mode" else "Light Mode",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (isDark) Color.White else Gray900
                            )
                            Text(
                                text = if (isDark) "Easier on the eyes" else "Classic appearance",
                                fontSize = 13.sp,
                                color = if (isDark) Gray400 else Gray500
                            )
                        }

                        Switch(
                            checked = isDark,
                            onCheckedChange = { viewModel.toggleTheme() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Cyan500,
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = Gray300
                            )
                        )
                    }
                }
            }
        }

        // Save Button
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = if (isDark) Dark800 else Color.White,
            shadowElevation = 0.5.dp
        ) {
            Box(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                GradientButton(
                    text = "Save Changes",
                    onClick = {
                        viewModel.updateAllergies(selectedAllergies.toList())
                        viewModel.navigateTo(Screen.DASHBOARD)
                    }
                )
            }
        }
    }
}

@Composable
fun AllergySettingRow(
    allergy: Allergy,
    isSelected: Boolean,
    isLast: Boolean,
    isDark: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(if (isDark) Dark800 else Color.White)
            .padding(20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = allergy.emoji,
            fontSize = 28.sp
        )

        Text(
            text = allergy.name,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
            color = if (isDark) Color.White else Gray900,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier
                .size(28.dp)
                .background(
                    color = if (isSelected) Cyan500 else (if (isDark) Dark600 else Gray200),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }

    if (!isLast) {
        Divider(
            modifier = Modifier.padding(start = 76.dp),
            color = if (isDark) Dark600 else Gray200,
            thickness = 0.5.dp
        )
    }
}
