// app/src/main/java/com/lolakashmir/retailerapp/ui/screens/login/LoginScreen.kt
package com.lolakashmir.retailerapp.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*

import com.lolakashmir.retailerapp.ui.theme.RetailerAppTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


import androidx.compose.ui.tooling.preview.Preview
import com.lolakashmir.retailerapp.ui.components.AppButton
import com.lolakashmir.retailerapp.ui.components.AppInput

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignup: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isLoginSuccessful) {
        if (uiState.isLoginSuccessful) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Welcome Text
        Text(
            text = "Welcome Back!",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Email Input
        AppInput(
            value = uiState.email,
            onValueChange = viewModel::onEmailChange,
            label = "Email",
            placeholder = "Enter your email",
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            isError = uiState.error != null
        )

        // Password Input
        AppInput(
            value = uiState.password,
            onValueChange = viewModel::onPasswordChange,
            label = "Password",
            placeholder = "Enter your password",
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            isPasswordField = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            isError = uiState.error != null
        )

        // Error Message
        if (uiState.error != null) {
            Text(
                text = uiState.error!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Login Button
        AppButton(
            text = "Login",
            onClick = { viewModel.onLoginClick() },
            isLoading = uiState.isLoading,
            enabled = uiState.email.isNotBlank() && uiState.password.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Sign Up Link
        TextButton(
            onClick = onNavigateToSignup,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "Don't have an account? Sign up",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
