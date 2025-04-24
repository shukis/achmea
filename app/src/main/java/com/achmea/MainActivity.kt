package com.achmea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.achmea.core.Route
import com.achmea.designsystem.MaterialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController, startDestination = Route.EmployersList) {
                        composable<Route.EmployersList> {
                            Text("EmployersList", modifier = Modifier
                                .clickable {
                                    navController.navigate(Route.EmployerDetails(""))
                                }
                                .padding(innerPadding))
                        }
                        composable<Route.EmployerDetails> {
                            Text("EmployerDetails", modifier = Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }
}