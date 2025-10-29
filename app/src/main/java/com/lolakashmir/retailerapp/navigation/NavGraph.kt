package com.lolakashmir.retailerapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lolakashmir.retailerapp.ui.screens.home.HomeScreen
import com.lolakashmir.retailerapp.ui.screens.signup.SignupScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object SignUp : Screen("signup")
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
                onSignUpClick = { name, email, phone, password ->
                    // Handle sign up logic here
                    navController.popBackStack()
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
