package com.achmea.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.achmea.core.Route
import com.achmea.designsystem.MaterialTheme
import com.achmea.presentation.list.EmployersListScreen
import com.achmea.presentation.list.EmployersListViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

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
                            val vm = getViewModel<EmployersListViewModel>()
                            EmployersListScreen(navController, vm, innerPadding)
                        }
                        composable<Route.EmployerDetails> {
                            val employer = it.toRoute<Route.EmployerDetails>()
                            Text(
                                "EmployerDetails, ${employer.id}",
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}