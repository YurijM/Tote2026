package com.mu.tote2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.mu.tote2026.presentation.navigation.NavGraph
import com.mu.tote2026.ui.theme.Tote2026Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            Tote2026Theme {
                Scaffold(
                    /*containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,*/
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    //MainScreen()
                    NavGraph(navController = navController)

                }
            }
        }
    }
}
