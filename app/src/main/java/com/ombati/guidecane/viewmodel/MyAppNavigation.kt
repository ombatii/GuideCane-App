package com.ombati.guidecane.viewmodel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ombati.guidecane.pages.HomePage
import com.ombati.guidecane.pages.LoginPage
import com.ombati.guidecane.pages.SignupPage

@Composable
fun MyAppNavigation(modifier: Modifier) {
    val navController = rememberNavController()

    // Directly navigate to HomePage without any auth checks
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomePage() // Adjust as necessary for your HomePage parameters
        }
    }
}

@Composable
fun SplashScreen(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(modifier: Modifier, message: String) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Error: $message")
    }
}
