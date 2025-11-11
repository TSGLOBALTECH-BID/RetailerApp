package com.lolakashmir.retailerapp.ui.screens.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lolakashmir.retailerapp.R
import com.lolakashmir.retailerapp.data.model.ApiResponse
import com.lolakashmir.retailerapp.data.model.auth.SignupRequest
import com.lolakashmir.retailerapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
class SignupViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {
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
    
    var confirmPasswordVisible by mutableStateOf(false)
        private set
    
    // Form validation state
    val isFormValid: Boolean
        get() = name.isNotBlank() && 
                email.isNotBlank() && 
                phone.isNotBlank() && 
                password.isNotBlank() && 
                confirmPassword.isNotBlank() &&
                password == confirmPassword

    // UI state
    private val _uiState = MutableStateFlow<SignupUiState>(SignupUiState.Initial)
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    /**
     * Updates the name field
     */
    fun updateName(newName: String) {
        name = newName
    }

    /**
     * Updates the email field
     */
    fun updateEmail(newEmail: String) {
        email = newEmail
    }

    /**
     * Updates the phone field
     */
    fun updatePhone(newPhone: String) {
        phone = newPhone
    }

    /**
     * Updates the password field
     */
    fun updatePassword(newPassword: String) {
        password = newPassword
    }

    /**
     * Updates the confirm password field
     */
    fun updateConfirmPassword(newConfirmPassword: String) {
        confirmPassword = newConfirmPassword
    }

    /**
     * Toggles the password visibility
     */
    fun togglePasswordVisibility() {
        passwordVisible = !passwordVisible
    }

    /**
     * Toggles the confirm password visibility
     */
    fun toggleConfirmPasswordVisibility() {
        confirmPasswordVisible = !confirmPasswordVisible
    }

    /**
     * Handles the signup process
     */
    fun signUp(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        if (!isFormValid) {
            onError("Please fill in all fields correctly")
            return
        }

        _uiState.value = SignupUiState.Loading

        viewModelScope.launch {
            try {
                val signupRequest = SignupRequest(
                    name = name,
                    email = email,
                    phone = phone,
                    password = password,
                    password_confirmation = confirmPassword
                )
                
                when (val result = authRepository.singnUp(signupRequest)) {
                    is ApiResponse.Success -> {
                        _uiState.value = SignupUiState.Success
                        onSuccess()
                    }
                    is ApiResponse.Error -> {
                        _uiState.value = SignupUiState.Error(result.message)
                        onError(result.message)
                    }
                    else -> {
                        _uiState.value = SignupUiState.Error("Unexpected response from server")
                        onError("Unexpected response from server")
                    }
                }
            } catch (e: Exception) {
                _uiState.value = SignupUiState.Error(e.message ?: "An unknown error occurred")
                onError(e.message ?: "An unknown error occurred")
            }
        }
    }

    /**
     * Resets the form
     */
    fun resetForm() {
        name = ""
        email = ""
        phone = ""
        password = ""
        confirmPassword = ""
        passwordVisible = false
        confirmPasswordVisible = false
        _uiState.value = SignupUiState.Initial
    }
}
