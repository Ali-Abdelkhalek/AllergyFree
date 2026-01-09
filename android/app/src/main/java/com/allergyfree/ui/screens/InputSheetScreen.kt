package com.allergyfree.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputSheetScreen(
    viewModel: AppViewModel,
    onDismiss: () -> Unit
) {
    var ingredients by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var isAnalyzing by remember { mutableStateOf(false) }
    val isDark = viewModel.theme == Theme.DARK
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDark) Dark800 else Color.White)
    ) {
        // Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = if (isDark) Dark800 else Color.White,
            shadowElevation = 0.5.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Check Food Safety",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDark) Color.White else Gray900,
                    modifier = Modifier.align(Alignment.Center)
                )

                ScaleButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = if (isDark) Dark700 else Gray100,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = if (isDark) Color.White else Gray600,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // Content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Image Section
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Food Image (Optional)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isDark) Gray400 else Gray600
                )

                ScaleButton(onClick = { /* Image picker */ }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .background(
                                color = if (isDark) Dark700 else Gray50,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .border(
                                width = 2.dp,
                                color = if (isDark) Dark600 else Gray300,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = null,
                                tint = Cyan500,
                                modifier = Modifier.size(36.dp)
                            )

                            Text(
                                text = "Tap to capture or select photo",
                                fontSize = 15.sp,
                                color = if (isDark) Color.White else Gray900
                            )

                            Text(
                                text = "We'll analyze the ingredients",
                                fontSize = 13.sp,
                                color = Gray400
                            )
                        }
                    }
                }
            }

            // Country Field
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Country of origin (Optional)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isDark) Gray400 else Gray600
                )

                OutlinedTextField(
                    value = country,
                    onValueChange = { country = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("e.g., Italy 🇮🇹") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = if (isDark) Dark700 else Color.White,
                        unfocusedContainerColor = if (isDark) Dark700 else Color.White,
                        focusedBorderColor = if (isDark) Dark600 else Gray300,
                        unfocusedBorderColor = if (isDark) Dark600 else Gray300
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            // Ingredients Field
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Enter ingredients or describe the meal",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isDark) Gray400 else Gray600
                )

                OutlinedTextField(
                    value = ingredients,
                    onValueChange = { ingredients = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    placeholder = { Text("e.g., Wheat flour, sugar, eggs, butter, milk, vanilla extract...") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = if (isDark) Dark700 else Color.White,
                        unfocusedContainerColor = if (isDark) Dark700 else Color.White,
                        focusedBorderColor = if (isDark) Dark600 else Gray300,
                        unfocusedBorderColor = if (isDark) Dark600 else Gray300
                    ),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 6
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "${ingredients.length} characters",
                        fontSize = 13.sp,
                        color = Gray400
                    )
                }
            }
        }

        // Analyze Button
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = if (isDark) Dark800 else Color.White,
            shadowElevation = 0.5.dp
        ) {
            Box(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                GradientButton(
                    text = "Analyze Ingredients",
                    onClick = {
                        scope.launch {
                            isAnalyzing = true
                            delay(1500)

                            // Simulate analysis
                            val ingredientList = ingredients.split(",").map { it.trim() }
                            val detectedAllergens = viewModel.userAllergies.filter { allergyId ->
                                ingredientList.any { ingredient ->
                                    val allergyName = allergiesList.find { it.id == allergyId }?.name ?: ""
                                    ingredient.contains(allergyId, ignoreCase = true) ||
                                            ingredient.contains(allergyName, ignoreCase = true)
                                }
                            }

                            val result = ScanResult(
                                status = if (detectedAllergens.isEmpty()) ScanStatus.SAFE else ScanStatus.DANGER,
                                preview = ingredients.take(50) + "...",
                                allergens = detectedAllergens,
                                ingredients = ingredientList
                            )

                            viewModel.addScanResult(result)
                            isAnalyzing = false
                            onDismiss()
                            viewModel.navigateTo(Screen.RESULTS)
                        }
                    },
                    enabled = ingredients.isNotBlank(),
                    isLoading = isAnalyzing
                )
            }
        }
    }
}
