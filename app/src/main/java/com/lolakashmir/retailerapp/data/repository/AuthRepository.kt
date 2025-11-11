package com.lolakashmir.retailerapp.data.repository

import com.lolakashmir.retailerapp.data.api.ApiClient
import com.lolakashmir.retailerapp.data.api.AuthApiService
import com.lolakashmir.retailerapp.data.model.ApiResponse
import com.lolakashmir.retailerapp.data.model.BaseResponse
import com.lolakashmir.retailerapp.data.model.auth.SignupRequest

/**
 * Repository class that handles data operations.
 * Acts as a single source of truth for data in the application.
 */
class AuthRepository {
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
     * Example function to post data to an API endpoint
     */
    suspend fun singnUp(request: SignupRequest): ApiResponse<Any> {
        return ApiClient.safeApiCall {
            authApiService.signUp(request)
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
