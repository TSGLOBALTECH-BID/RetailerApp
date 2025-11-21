package com.lolakashmir.retailerapp.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.lolakashmir.retailerapp.ui.components.AppDrawer
import com.lolakashmir.retailerapp.ui.screens.home.HomeScreen
import com.lolakashmir.retailerapp.ui.screens.signup.SignupScreen
import com.lolakashmir.retailerapp.ui.screens.login.LoginScreen
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object SignUp : Screen("signup")
    object Login : Screen("login")
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Login.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Home.route) {
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            AppDrawer(
            drawerState = drawerState,
            drawerContent = {
            // Your drawer content
            }
            ) {
                HomeScreen(
                onMenuClick = {
                scope.launch { drawerState.open() }
                }
                )
            }
        }
        
        composable(Screen.SignUp.route) {
            SignupScreen(
                onSignUpSuccess = {
                    navController.popBackStack()
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Screen.Home.route) {
                // Clear back stack to prevent going back to login
                popUpTo(Screen.Login.route) { inclusive = true }
            } },
                onNavigateToSignup = { navController.navigate(Screen.SignUp.route) }
            )
        }
    }
}
