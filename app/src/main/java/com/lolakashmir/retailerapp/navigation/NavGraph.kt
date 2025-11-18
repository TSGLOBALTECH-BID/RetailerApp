package com.lolakashmir.retailerapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.lolakashmir.retailerapp.ui.screens.home.HomeScreen
import com.lolakashmir.retailerapp.ui.screens.signup.SignupScreen
import com.lolakashmir.retailerapp.ui.screens.login.LoginScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object SignUp : Screen("signup")
    object Login : Screen("login")
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onSignUpClick = {
                    navController.navigate(Screen.SignUp.route)
                }
            )
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
                onLoginSuccess = { /* Navigate to home */ },
                onNavigateToSignup = { navController.navigate(Screen.SignUp.route) }
            )
        }
    }
}
