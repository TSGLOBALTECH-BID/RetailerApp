// app/src/main/java/com/lolakashmir/retailerapp/ui/screens/signup/SignupViewModel.kt
package com.lolakashmir.retailerapp.ui.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lolakashmir.retailerapp.data.model.ApiResponse
import com.lolakashmir.retailerapp.data.model.auth.SignupRequest
import com.lolakashmir.retailerapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI state for the Signup screen
 */
sealed class SignupUiState {
    object Initial : SignupUiState()
    object Loading : SignupUiState()
    data class Error(val message: String) : SignupUiState()
    object Success : SignupUiState()
}

/**
 * ViewModel for handling signup logic and managing UI state
 */
@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<SignupUiState>(SignupUiState.Initial)
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    // Form fields
    var name by mutableStateOf("")
        private set
    
    var email by mutableStateOf("")
        private set
    
    var phone by mutableStateOf("")
        private set
    
    var password by mutableStateOf("")
        private set
    
    var confirmPassword by mutableStateOf("")
        private set
    
    var passwordVisible by mutableStateOf(false)
        private set

    fun onNameChange(newName: String) {
        name = newName
    }

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPhoneChange(newPhone: String) {
        phone = newPhone
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        confirmPassword = newConfirmPassword
    }

    fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible
    }

    fun onSignUp() {
        if (validateForm()) {
            _uiState.value = SignupUiState.Loading
            viewModelScope.launch {
                try {
                    val request = SignupRequest(
                        fullName = name,
                        username = name,
                        email = email,
                        phone = phone,
                        password = password,
                        password_confirmation = confirmPassword
                    )
                    when (val response = authRepository.signUp(request)) {
                        is ApiResponse.Success -> {
                            _uiState.value = SignupUiState.Success
                        }
                        is ApiResponse.Error -> {
                            _uiState.value = SignupUiState.Error(
                                response.message
                            )
                        }
                        else -> {
                            _uiState.value = SignupUiState.Error("Unexpected response from server")
                        }
                    }
                } catch (e: Exception) {
                    _uiState.value = SignupUiState.Error(
                        e.message ?: "An error occurred. Please try again."
                    )
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        return when {
            name.isBlank() -> {
                _uiState.value = SignupUiState.Error("Please enter your name")
                false
            }
            email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _uiState.value = SignupUiState.Error("Please enter a valid email")
                false
            }
            phone.isBlank() || phone.length < 10 -> {
                _uiState.value = SignupUiState.Error("Please enter a valid phone number")
                false
            }
            password.length < 6 -> {
                _uiState.value = SignupUiState.Error("Password must be at least 6 characters long")
                false
            }
            password != confirmPassword -> {
                _uiState.value = SignupUiState.Error("Passwords do not match")
                false
            }
            else -> true
        }
    }
}