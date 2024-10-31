package com.ombati.guidecane

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.ombati.guidecane.ui.theme.GuideCaneTheme
import com.ombati.guidecane.viewmodel.AuthViewModel
import com.ombati.guidecane.viewmodel.MyAppNavigation
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            GuideCaneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MyAppNavigation(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
