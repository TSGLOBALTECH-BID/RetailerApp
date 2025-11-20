package com.lolakashmir.retailerapp.data.repository

import com.lolakashmir.retailerapp.data.api.ApiClient
import com.lolakashmir.retailerapp.data.model.ApiResponse
import com.lolakashmir.retailerapp.data.model.auth.LoginRequest
import com.lolakashmir.retailerapp.data.model.auth.LoginResponse
import com.lolakashmir.retailerapp.data.model.auth.SignupRequest
import com.lolakashmir.retailerapp.data.model.auth.SignupResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class AuthRepository @Inject constructor(
    private val apiClient: ApiClient
) {
    /**
     * Function to handle user signup
     */
    open suspend fun signUp(request: SignupRequest): ApiResponse<SignupResponse> {
    return apiClient.safeApiCall {
        apiClient.authApiService.signUp(request)
    }
}

    /**
     * Function to handle user login
     */
    open suspend fun login(request: LoginRequest): ApiResponse<LoginResponse> {
        return apiClient.safeApiCall {
            apiClient.authApiService.login(request)
        }
    }

    /**
     * Function to check if user is logged in
     */
//    fun isUserLoggedIn(): Boolean {
//        return apiClient.getToken() != null
//    }

    /**
     * Function to log out user
     */
//    fun logout() {
//        apiClient.clearToken()
//    }
    open suspend fun signup(request: LoginRequest): ApiResponse<LoginResponse> {
        TODO("Not yet implemented")
    }
}