// app/src/main/java/com/lolakashmir/retailerapp/ui/screens/login/LoginViewModel.kt
package com.lolakashmir.retailerapp.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lolakashmir.retailerapp.data.manager.TokenManager
import com.lolakashmir.retailerapp.data.model.ApiResponse
import com.lolakashmir.retailerapp.data.model.auth.LoginRequest
import com.lolakashmir.retailerapp.data.model.auth.LoginResponse
import com.lolakashmir.retailerapp.data.model.auth.SignupRequest
import com.lolakashmir.retailerapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoginSuccessful: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email, error = null)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password, error = null)
    }

    private fun saveToken(token: String) {
            viewModelScope.launch {
                try {
                    tokenManager.saveToken(token)
                } catch (e: Exception) {
                    // Handle token saving error
                    _uiState.update {
                        it.copy(error = "Failed to save authentication token")
                    }
                }
            }
        }

    fun onLoginClick() {
        val email = _uiState.value.email
        val password = _uiState.value.password

        if (email.isBlank() || password.isBlank()) {
            _uiState.value = _uiState.value.copy(
                error = "Please fill in all fields"
            )
            return
        }
        
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val request = LoginRequest(
                   email = email,
                    password = password
                )

                when (val result = authRepository.login(request)) {
                    is ApiResponse.Success -> {
                        // Handle success
                        result.data.session?.access_token?.let { saveToken(it) }
                        _uiState.update {
                            it.copy(isLoading = false, isLoginSuccessful = true)
                        }
                    }
                    is ApiResponse.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false, error = result.message)
                        }
                    }
                    else -> {
                        _uiState.update { it.copy(isLoading = false) }
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "An error occurred. Please try again."
                )
            }
        }
    }
}