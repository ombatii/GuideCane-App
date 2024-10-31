package com.ombati.guidecane.pages


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.ombati.guidecane.nav.NavItem
import com.ombati.guidecane.viewmodel.UserViewModel

@Composable
fun AddUserScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    var smartCaneID by remember { mutableStateOf("") }
    var securityKey by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessage by userViewModel.errorMessage.collectAsState()
    val filteredFeeds by userViewModel.filteredFeeds.collectAsState()
    val isLoading by userViewModel.isLoading.collectAsState()

    LaunchedEffect(errorMessage) {
        if (errorMessage.isNotEmpty()) {
            snackbarHostState.showSnackbar(errorMessage)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(padding)
        ) {
            Text(
                text = "Adding New User",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, fontSize = 24.sp),
                modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = smartCaneID,
                onValueChange = { smartCaneID = it },
                label = { Text("Smart Cane ID") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = securityKey,
                onValueChange = { securityKey = it },
                label = { Text("Security Key") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { userViewModel.fetchFilteredFeeds(securityKey.trim(), smartCaneID.trim()) },
                    enabled = !isLoading,
                    modifier = Modifier.weight(1f)
                ) {
                    if (isLoading) CircularProgressIndicator(modifier = Modifier.size(16.dp)) else Text("Fetch User Details")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (filteredFeeds.isEmpty()) {
                Text(text = "No user details found.", color = Color.Gray)
            } else {
                Text(
                    text = "User Details Loaded Successfully",
                    color = Color.Green,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Display details for each feed
                filteredFeeds.forEach { feed ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(text = "Location: ${feed.field1 ?: "N/A"}")
                        Text(text = "Battery Level: ${feed.field7 ?: "N/A"}")
                        Text(text = "Geo Fencing: ${feed.field4 ?: "N/A"}")
                        Text(text = "Caregiver Number: ${feed.field6 ?: "N/A"}")
                        Text(text = "Emergency Status: ${feed.field5 ?: "N/A"}")
                        Divider(modifier = Modifier.padding(vertical = 4.dp))
                    }
                }

                // Add button to save user data to Room and navigate to Profile
                Button(
                    onClick = {
                        filteredFeeds.forEach { feed ->
                            userViewModel.addUserProfile(feed)
                        }
                        navController.navigate(NavItem.Profile.route) // Navigate to Profile after adding
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Save User Data and Go to Profile")
                }
            }
        }
    }
}
