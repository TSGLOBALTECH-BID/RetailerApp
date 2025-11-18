package com.lolakashmir.retailerapp.data.repository

import com.lolakashmir.retailerapp.data.api.ApiClient
import com.lolakashmir.retailerapp.data.model.ApiResponse
import com.lolakashmir.retailerapp.data.model.auth.SignupRequest
import javax.inject.Inject

/**
 * Repository class that handles data operations.
 * Acts as a single source of truth for data in the application.
 */
open class AuthRepository @Inject constructor() {
    private val authApiService = ApiClient.authApiService

    /**
     * Example function to fetch data from an API endpoint
     */
//    suspend fun fetchData(param: String): ApiResponse<Any> {
//        return ApiClient.safeApiCall {
//            apiService.getData(param)
//        }
//    }

    /**
     * Function to post data(Signup) to an API endpoint
     */
    open suspend fun signUp(request: SignupRequest): ApiResponse<Any> {
        return ApiClient.safeApiCall {
            authApiService.signUp(request)
        }
    }

    /**
     * Function to post data(Login) to an API endpoint
     */
    open suspend fun login(email: String, password: String): ApiResponse<Any> {
        return ApiClient.safeApiCall {
            authApiService.login(email, password)
        }
    }

    /**
     * Example function to update data
     */
//    suspend fun updateData(id: String, request: Any): ApiResponse<Any> {
//        return ApiClient.safeApiCall {
//            apiService.updateData(id, request)
//        }
//    }

    /**
     * Example function to delete data
     */
//    suspend fun deleteData(id: String): ApiResponse<BaseResponse<Unit>> {
//        return ApiClient.safeApiCall {
//            apiService.deleteData(id)
//        }
//    }
}
