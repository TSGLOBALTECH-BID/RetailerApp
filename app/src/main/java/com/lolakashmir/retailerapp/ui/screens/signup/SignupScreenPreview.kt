package com.lolakashmir.retailerapp.ui.screens.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.lolakashmir.retailerapp.data.model.ApiResponse
import com.lolakashmir.retailerapp.data.model.auth.SignupRequest
import com.lolakashmir.retailerapp.data.repository.AuthRepository
import com.lolakashmir.retailerapp.ui.theme.RetailerAppTheme
import kotlinx.coroutines.delay

// Create a test implementation of AuthRepository
class TestAuthRepository : AuthRepository() {
    override suspend fun signUp(request: SignupRequest): ApiResponse<Any> {
        delay(1000) // Simulate network delay
        return ApiResponse.Success(Unit)
    }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    val context = LocalContext.current
    val testRepository = TestAuthRepository()

    val mockViewModel = remember {
        SignupViewModel(testRepository)
    }

    RetailerAppTheme(darkTheme = false, dynamicColor = false) {
        androidx.compose.material3.Surface {
            SignupScreen(
                viewModel = mockViewModel,
                onSignUpSuccess = {},
                onLoginClick = {}
            )
        }
    }
}