package com.lolakashmir.retailerapp.ui.screens.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lolakashmir.retailerapp.R
import com.lolakashmir.retailerapp.ui.components.AppButton
import com.lolakashmir.retailerapp.ui.components.AppInput
import com.lolakashmir.retailerapp.ui.components.ButtonVariant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    viewModel: SignupViewModel = hiltViewModel(),
    onSignUpSuccess: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    // Collect UI state from ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    // Show error message when there's an error
    LaunchedEffect(uiState) {
        when (val currentState = uiState) {
            is SignupUiState.Error -> {
                snackbarHostState.showSnackbar(
                    message = currentState.message,
                    withDismissAction = true
                )
            }
            is SignupUiState.Success -> {
                snackbarHostState.showSnackbar(
                    message = "Sign up successful",
                    withDismissAction = true
                )
                onSignUpSuccess()
            }
            else -> { /* Do nothing */ }
        }
    }

    // Show loading dialog when signing up
    if (uiState is SignupUiState.Loading) {
        AlertDialog(
            onDismissRequest = { /* No-op - don't allow dismissing */ },
            title = { Text(text = "Creating Account") },
            text = { 
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Please wait...")
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Header
            Text(
                text = stringResource(R.string.create_account),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Form
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Name Field
                AppInput(
                    value = viewModel.name,
                    onValueChange = { viewModel.onNameChange(it) },
                    label = stringResource(R.string.full_name),
                    placeholder = stringResource(R.string.enter_your_name),
                    modifier = Modifier.fillMaxWidth()
                )

                // Email Field
                AppInput(
                    value = viewModel.email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    label = stringResource(R.string.email),
                    placeholder = stringResource(R.string.enter_your_email),
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    modifier = Modifier.fillMaxWidth()
                )

                // Phone Field
                AppInput(
                    value = viewModel.phone,
                    onValueChange = { viewModel.onPhoneChange(it) },
                    label = stringResource(R.string.phone_number),
                    placeholder = stringResource(R.string.enter_your_phone),
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next,
                    modifier = Modifier.fillMaxWidth()
                )

                // Password Field
                AppInput(
                    value = viewModel.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    label = stringResource(R.string.password),
                    placeholder = stringResource(R.string.enter_password),
                    isPasswordField = !viewModel.passwordVisible,
//                    trailingIcon = {
//                        val image = if (viewModel.passwordVisible) {
//                            androidx.compose.material.icons.filled.Visibility
//                        } else {
//                            androidx.compose.material.icons.filled.VisibilityOff
//                        }
//                        androidx.compose.material3.IconButton(
//                            onClick = { viewModel.togglePasswordVisibility() }
//                        ) {
//                            androidx.compose.material3.Icon(
//                                imageVector = image,
//                                contentDescription = if (viewModel.passwordVisible) "Hide password" else "Show password"
//                            )
//                        }
//                    },
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                    modifier = Modifier.fillMaxWidth()
                )

                // Confirm Password Field
                AppInput(
                    value = viewModel.confirmPassword,
                    onValueChange = { viewModel.onConfirmPasswordChange(it) },
                    label = stringResource(R.string.confirm_password),
                    placeholder = stringResource(R.string.confirm_your_password),
                    isPasswordField = !viewModel.passwordVisible,
                    isError = viewModel.confirmPassword.isNotBlank() &&
                            viewModel.password != viewModel.confirmPassword,
                    errorMessage = if (viewModel.confirmPassword.isNotBlank() &&
                        viewModel.password != viewModel.confirmPassword
                    ) {
                        stringResource(R.string.passwords_do_not_match)
                    } else {
                        null
                    },
//                    trailingIcon = {
//                        val image = if (viewModel.confirmPasswordVisible) {
//                            androidx.compose.material.icons.filled.Visibility
//                        } else {
//                            androidx.compose.material.icons.filled.VisibilityOff
//                        }
//                        androidx.compose.material3.IconButton(
//                            onClick = { viewModel.toggleConfirmPasswordVisibility() }
//                        ) {
//                            androidx.compose.material3.Icon(
//                                imageVector = image,
//                                contentDescription = if (viewModel.confirmPasswordVisible) "Hide password" else "Show password"
//                            )
//                        }
//                    },
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sign Up Button
            AppButton(
                onClick = { viewModel.onSignUp() },
                text = "Sign Up",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                enabled = viewModel.name.isNotBlank() &&
                        viewModel.email.isNotBlank() &&
                        viewModel.phone.isNotBlank() &&
                        viewModel.password.isNotBlank() &&
                        viewModel.password == viewModel.confirmPassword
            )


            Spacer(modifier = Modifier.height(16.dp))

            // Login Link
            AppButton(
                text = stringResource(R.string.already_have_an_account_login),
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                variant = ButtonVariant.Outlined,
                enabled = uiState !is SignupUiState.Loading
            )
        }
    }
}

