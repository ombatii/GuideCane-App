package com.ombati.guidecane.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import com.ombati.guidecane.viewmodel.UserViewModel
@Composable
fun ProfileScreen(navController: NavController, userViewModel: UserViewModel = hiltViewModel()) {
    val userProfiles by userViewModel.userProfiles.collectAsState()
    val isLoading by userViewModel.isLoading.collectAsState()
    val errorMessage by userViewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        userViewModel.fetchUserProfiles()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("add_user")
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    userProfiles.isEmpty() -> { // Show NoUserMessage if there are no profiles
                        NoUserMessage()
                    }
                    else -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(userProfiles) { profile ->
                                ProfileCard(
                                    smartCaneID = profile.smartCaneID,
                                    location = profile.location,
                                    batteryLevel = profile.batteryLevel.toFloatOrNull() ?: 0f, // Convert battery level to Float, defaulting to 0f if null or invalid
                                    geoFencing = profile.geoFencing,
                                    caregiverNumber = profile.caregiverNumber,
                                    emergencyStatus = profile.emergencyStatus,
                                    navController = navController,
                                    onDelete = {
                                        userViewModel.deleteUserProfile(profile.smartCaneID)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}


@Composable
fun ProfileCard(
    smartCaneID: String,
    location: String,
    batteryLevel: Float, // Change batteryLevel type to Float
    geoFencing: String,
    caregiverNumber: String,
    emergencyStatus: String,
    navController: NavController,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Smart Cane ID: $smartCaneID", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Location: $location", fontSize = 14.sp, color = Color.LightGray)
            Text(text = "GeoFencing: $geoFencing", fontSize = 14.sp, color = Color.LightGray)
            Text(text = "Caregiver Number: $caregiverNumber", fontSize = 14.sp, color = Color.LightGray)
            Text(text = "Emergency Status: $emergencyStatus", fontSize = 14.sp, color = Color.LightGray)

            // Display battery level using a progress bar
            Text(text = "Battery Level: ${batteryLevel.toInt()}%", fontSize = 14.sp, color = Color.LightGray)
            LinearProgressIndicator(
                progress = batteryLevel / 100f, // Use Float division
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .padding(top = 4.dp),
                color = when {
                    batteryLevel > 20 -> Color.Green // Color for battery levels above 20%
                    batteryLevel > 0 -> Color.Yellow // Color for battery levels between 1% and 20%
                    else -> Color.Red // Color for 0% battery
                },
                trackColor = Color.LightGray // Track color for the progress bar background
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    navController.navigate("edit_user/$smartCaneID")
                }) {
                    Text("Edit")
                }
                Button(
                    onClick = { onDelete() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Delete", color = Color.White)
                }
            }
        }
    }
}



@Composable
fun NoUserMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No user profiles found.",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
    }
}
