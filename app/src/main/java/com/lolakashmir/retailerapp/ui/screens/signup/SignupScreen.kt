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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lolakashmir.retailerapp.R
import com.lolakashmir.retailerapp.ui.components.AppButton
import com.lolakashmir.retailerapp.ui.components.AppInput
import com.lolakashmir.retailerapp.ui.components.ButtonVariant
import com.lolakashmir.retailerapp.ui.theme.RetailerAppTheme

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    onSignUpClick: (String, String, String, String) -> Unit = { _, _, _, _ -> },
    onLoginClick: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    
    val isFormValid = name.isNotBlank() && 
                     email.isNotBlank() && 
                     phone.isNotBlank() && 
                     password.isNotBlank() && 
                     confirmPassword.isNotBlank() &&
                     password == confirmPassword

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
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
                value = name,
                onValueChange = { name = it },
                label = stringResource(R.string.full_name),
                placeholder = stringResource(R.string.enter_your_name),
                modifier = Modifier.fillMaxWidth()
            )

            // Email Field
            AppInput(
                value = email,
                onValueChange = { email = it },
                label = stringResource(R.string.email),
                placeholder = stringResource(R.string.enter_your_email),
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                modifier = Modifier.fillMaxWidth()
            )

            // Phone Field
            AppInput(
                value = phone,
                onValueChange = { phone = it },
                label = stringResource(R.string.phone_number),
                placeholder = stringResource(R.string.enter_your_phone),
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next,
                modifier = Modifier.fillMaxWidth()
            )

            // Password Field
            AppInput(
                value = password,
                onValueChange = { password = it },
                label = stringResource(R.string.password),
                placeholder = stringResource(R.string.enter_password),
                isPasswordField = true,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
                modifier = Modifier.fillMaxWidth()
            )

            // Confirm Password Field
            AppInput(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = stringResource(R.string.confirm_password),
                placeholder = stringResource(R.string.confirm_your_password),
                isPasswordField = true,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                isError = confirmPassword.isNotBlank() && password != confirmPassword,
                errorMessage = if (confirmPassword.isNotBlank() && password != confirmPassword) {
                    stringResource(R.string.passwords_do_not_match)
                } else {
                    null
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sign Up Button
        AppButton(
            text = stringResource(R.string.sign_up),
            onClick = { onSignUpClick(name, email, phone, password) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            enabled = isFormValid
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Login Link
        AppButton(
            text = stringResource(R.string.already_have_an_account_login),
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            variant = ButtonVariant.Outlined
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    RetailerAppTheme(darkTheme = false, dynamicColor = false) {
        Surface {
            SignupScreen()
        }
    }
}
