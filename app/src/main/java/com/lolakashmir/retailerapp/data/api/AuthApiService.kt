// data/api/AuthApiService.kt
package com.lolakashmir.retailerapp.data.api

import com.lolakashmir.retailerapp.data.model.BaseResponse
import com.lolakashmir.retailerapp.data.model.auth.LoginRequest
import com.lolakashmir.retailerapp.data.model.auth.LoginResponse
import com.lolakashmir.retailerapp.data.model.auth.SignupRequest
import com.lolakashmir.retailerapp.data.model.auth.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/signup")
    suspend fun signUp(@Body signupRequest: SignupRequest): Response<BaseResponse<SignupResponse>>

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<BaseResponse<LoginResponse>>
}



