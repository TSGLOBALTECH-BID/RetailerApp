package com.lolakashmir.retailerapp.ui.screens.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.lolakashmir.retailerapp.data.api.ApiClient
import com.lolakashmir.retailerapp.data.manager.TokenManager
import com.lolakashmir.retailerapp.data.model.ApiResponse
import com.lolakashmir.retailerapp.data.model.auth.LoginRequest
import com.lolakashmir.retailerapp.data.model.auth.LoginResponse
import com.lolakashmir.retailerapp.data.model.auth.SignupRequest
import com.lolakashmir.retailerapp.data.model.auth.SignupResponse
import com.lolakashmir.retailerapp.data.repository.AuthRepository
import com.lolakashmir.retailerapp.ui.theme.RetailerAppTheme
import kotlinx.coroutines.delay


//@Preview(showBackground = true)
//@Composable
//fun SignupScreenPreview() {
//    val context = LocalContext.current
//    val testRepository = object : AuthRepository(ApiClient(TokenManager(context))) {
//        suspend fun signup(request: SignupRequest): ApiResponse<SignupResponse> {
//            delay(1000) // Simulate network delay
//            return ApiResponse.Success(SignupResponse(null))
//        }
//    }
//
//    val mockViewModel = remember {
//        SignupViewModel(testRepository)
//    }
//
//    RetailerAppTheme(darkTheme = false, dynamicColor = false) {
//        androidx.compose.material3.Surface {
//            SignupScreen(
//                viewModel = mockViewModel,
//                onSignUpSuccess = {},
//                onLoginClick = {}
//            )
//        }
//    }
//}