// app/src/main/java/com/lolakashmir/retailerapp/ui/screens/login/LoginScreenPreview.kt
package com.lolakashmir.retailerapp.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.lolakashmir.retailerapp.data.api.ApiClient
import com.lolakashmir.retailerapp.data.manager.TokenManager
import com.lolakashmir.retailerapp.data.model.ApiResponse
import com.lolakashmir.retailerapp.data.model.auth.LoginRequest
import com.lolakashmir.retailerapp.data.model.auth.LoginResponse
import com.lolakashmir.retailerapp.data.repository.AuthRepository
import com.lolakashmir.retailerapp.ui.theme.RetailerAppTheme
import kotlinx.coroutines.delay

//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    val context = LocalContext.current
//    val tokenManager = TokenManager(context)
//    val testRepository = object : AuthRepository(ApiClient(tokenManager)) {
//        override suspend fun login(request: LoginRequest): ApiResponse<LoginResponse> {
//            delay(1000) // Simulate network delay
//            return ApiResponse.Success(LoginResponse(null, "Test User"))
//        }
//    }
//
//    val mockViewModel = remember {
//        LoginViewModel(testRepository, tokenManager)
//    }
//
//    RetailerAppTheme {
//        LoginScreen(
//            viewModel = mockViewModel,
//            onLoginSuccess = { /* Handle login success */ },
//            onNavigateToSignup = {}
//        )
//    }
//}