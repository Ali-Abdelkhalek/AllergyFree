package com.allergyfree.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.allergyfree.ui.components.AllergyPill
import com.allergyfree.ui.components.IconCircleButton
import com.allergyfree.ui.components.ScaleButton
import com.allergyfree.ui.theme.*
import com.allergyfree.viewmodels.AppViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DashboardScreen(viewModel: AppViewModel) {
    val isDark = viewModel.theme == Theme.DARK
    val backgroundColor = if (isDark) Dark900 else Gray50

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }

        // Header Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .shadow(
                        elevation = if (isDark) 8.dp else 4.dp,
                        shape = RoundedCornerShape(24.dp),
                        spotColor = if (isDark) Color.Black.copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.08f)
                    ),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isDark) Dark800.copy(alpha = 0.95f) else Color.White.copy(alpha = 0.95f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Title and Settings
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "AllergyFree",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Cyan500
                            )
                            Text(
                                text = "Stay safe, eat freely",
                                fontSize = 15.sp,
                                color = if (isDark) Gray400 else Gray500
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        IconCircleButton(
                            icon = Icons.Default.Settings,
                            onClick = { viewModel.navigateTo(Screen.SETTINGS) },
                            isDark = isDark
                        )
                    }

                    // Active Allergies
                    if (viewModel.userAllergies.isNotEmpty()) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(viewModel.userAllergies) { allergyId ->
                                allergiesList.find { it.id == allergyId }?.let { allergy ->
                                    AllergyPill(
                                        emoji = allergy.emoji,
                                        name = allergy.name,
                                        isDark = isDark
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Check Food Button
        item {
            ScaleButton(
                onClick = { viewModel.showInputSheet = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = if (isDark) 12.dp else 8.dp,
                            shape = RoundedCornerShape(24.dp),
                            spotColor = if (isDark) Color.Black.copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.12f)
                        ),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isDark) Dark800 else Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .shadow(
                                    elevation = 6.dp,
                                    shape = CircleShape,
                                    spotColor = Cyan500.copy(alpha = 0.3f)
                                )
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(Cyan500, Cyan700)
                                    ),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Check Food Safety",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDark) Color.White else Gray900
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Scan or enter ingredients",
                            fontSize = 15.sp,
                            color = if (isDark) Gray400 else Gray500
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Recent Scans Section
        item {
            Row(
                modifier = Modifier.padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = if (isDark) Gray400 else Gray600,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Recent Scans",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDark) Color.White else Gray900
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Recent Scans List
        if (viewModel.recentScans.isEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(vertical = 48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = if (isDark) Dark600 else Gray300,
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "No scans yet",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (isDark) Gray400 else Gray500
                    )
                    Text(
                        text = "Your recent checks will appear here",
                        fontSize = 15.sp,
                        color = if (isDark) Gray600 else Gray400
                    )
                }
            }
        } else {
            items(viewModel.recentScans.size) { index ->
                RecentScanCard(
                    scan = viewModel.recentScans[index],
                    isDark = isDark,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun RecentScanCard(
    scan: ScanResult,
    isDark: Boolean,
    modifier: Modifier = Modifier
) {
    val statusColor = when (scan.status) {
        ScanStatus.SAFE -> Green500
        ScanStatus.WARNING -> Amber500
        ScanStatus.DANGER -> Red500
    }

    val statusIcon = when (scan.status) {
        ScanStatus.SAFE -> Icons.Default.CheckCircle
        ScanStatus.WARNING -> Icons.Default.Warning
        ScanStatus.DANGER -> Icons.Default.Cancel
    }

    val statusText = when (scan.status) {
        ScanStatus.SAFE -> "SAFE"
        ScanStatus.WARNING -> "WARNING"
        ScanStatus.DANGER -> "DANGER"
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (isDark) 4.dp else 2.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = if (isDark) Color.Black.copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.06f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) Dark800 else Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Left accent bar
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(statusColor)
            )

            // Content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Status Badge
                Box(
                    modifier = Modifier
                        .background(
                            color = statusColor.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = statusText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusColor
                    )
                }

                // Preview
                Text(
                    text = scan.preview,
                    fontSize = 15.sp,
                    color = if (isDark) Color.White else Gray900,
                    maxLines = 2
                )

                // Timestamp
                Text(
                    text = getRelativeTime(scan.timestamp),
                    fontSize = 13.sp,
                    color = Gray400
                )
            }

            // Status Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .shadow(
                        elevation = if (isDark) 4.dp else 2.dp,
                        shape = CircleShape,
                        spotColor = if (isDark) Color.Black.copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.08f)
                    )
                    .background(
                        color = if (isDark) Dark700 else Color.White,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = statusIcon,
                    contentDescription = null,
                    tint = statusColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

fun getRelativeTime(date: Date): String {
    val now = Date()
    val diff = now.time - date.time
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        seconds < 60 -> "just now"
        minutes < 60 -> "$minutes min ago"
        hours < 24 -> "$hours hr ago"
        days < 7 -> "$days days ago"
        else -> SimpleDateFormat("MMM dd", Locale.getDefault()).format(date)
    }
}
