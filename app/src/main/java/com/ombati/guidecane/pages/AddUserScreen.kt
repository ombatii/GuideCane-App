package com.ombati.guidecane.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AddUserScreen(navController: NavController) {
    var userID by remember { mutableStateOf("") }
    var securityKey by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Center the text "Adding new user", make it bold, and increase the font size
        Text(
            text = "Adding new user",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = androidx.compose.ui.Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // User ID input
        OutlinedTextField(
            value = userID,
            onValueChange = { userID = it },
            label = { Text("User ID") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Security Key input
        OutlinedTextField(
            value = securityKey,
            onValueChange = { securityKey = it },
            label = { Text("Security Key") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.weight(1f)) // Push buttons to the bottom

        // Save and Cancel buttons in a row
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                // Handle save action here (e.g., save to database)
                // Then navigate back to the profile screen
                navController.popBackStack() // Navigate back to profile
            }) {
                Text("Save")
            }

            Button(onClick = {
                navController.popBackStack()
            }) {
                Text("Cancel")
            }
        }
    }
}
