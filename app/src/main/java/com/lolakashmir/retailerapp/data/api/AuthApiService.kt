// data/api/AuthApiService.kt
package com.lolakashmir.retailerapp.data.api

import com.lolakashmir.retailerapp.data.model.SignupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/signup") // Replace with your actual signup endpoint
    suspend fun signUp(@Body signupRequest: SignupRequest): Response<SignupResponse>
}

data class SignupResponse(
    val success: Boolean,
    val message: String,
    val data: UserData?
)

data class UserData(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val token: String
)