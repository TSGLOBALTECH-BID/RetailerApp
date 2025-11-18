// app/src/main/java/com/lolakashmir/retailerapp/ui/screens/login/LoginScreenPreview.kt
package com.lolakashmir.retailerapp.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.lolakashmir.retailerapp.data.model.ApiResponse
import com.lolakashmir.retailerapp.data.repository.AuthRepository
import com.lolakashmir.retailerapp.ui.theme.RetailerAppTheme
import kotlinx.coroutines.delay

// Test implementation for Login
class TestLoginRepository : AuthRepository() {
    override suspend fun login(email: String, password: String): ApiResponse<Any> {
        delay(1000) // Simulate network delay
        return ApiResponse.Success(Unit)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val context = LocalContext.current
    val testRepository = TestLoginRepository()
    
    val mockViewModel = remember {
        LoginViewModel(testRepository)
    }
    
    RetailerAppTheme(darkTheme = false, dynamicColor = false) {
        androidx.compose.material3.Surface {
            LoginScreen(
                onLoginSuccess = {},
                onNavigateToSignup = {},
                viewModel = mockViewModel
            )
        }
    }
}