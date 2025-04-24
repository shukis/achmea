package com.achmea.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.achmea.core.Route
import com.achmea.designsystem.MaterialTheme
import com.achmea.presentation.details.EmployerDetailsScreen
import com.achmea.presentation.details.EmployerDetailsViewModel
import com.achmea.presentation.list.EmployersListScreen
import com.achmea.presentation.list.EmployersListViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { viewModel.isLoading.value }
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
                            val employerId = it.toRoute<Route.EmployerDetails>().id
                            val vm = getViewModel<EmployerDetailsViewModel>()
                            LaunchedEffect(employerId) {
                                vm.loadEmployer(employerId)
                            }
                            EmployerDetailsScreen(navController, vm, innerPadding)
                        }
                    }
                }
            }
        }
    }
}