package com.lolakashmir.retailerapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * A generic class that holds a value with its loading status.
 * @param T The type of data that is expected from the API response
 */
sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(val message: String, val code: Int = -1) : ApiResponse<Nothing>()
    object Loading : ApiResponse<Nothing>() 
    object Empty : ApiResponse<Nothing>()
}

/**
 * Base response model for all API responses
 */
data class BaseResponse<T>(
    @SerializedName("success")
    val success: Boolean,
    
    @SerializedName("message")
    val message: String? = null,
    
    @SerializedName("data")
    val data: T? = null,
    
    @SerializedName("error")
    val error: String? = null,
    
    @SerializedName("status")
    val status: Int? = null
)
