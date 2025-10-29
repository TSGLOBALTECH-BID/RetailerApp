package com.lolakashmir.retailerapp.data.api

import com.lolakashmir.retailerapp.data.model.BaseResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * Define your API endpoints here
 */
interface ApiService {
    // Example endpoints - replace with your actual API endpoints
    
    // Example GET request
    @GET("endpoint")
    suspend fun getData(@Query("param") param: String): Response<BaseResponse<Any>>
    
    // Example POST request
    @POST("endpoint")
    suspend fun postData(@Body request: Any): Response<BaseResponse<Any>>
    
    // Example PUT request
    @PUT("endpoint/{id}")
    suspend fun updateData(@Path("id") id: String, @Body request: Any): Response<BaseResponse<Any>>
    
    // Example DELETE request
    @DELETE("endpoint/{id}")
    suspend fun deleteData(@Path("id") id: String): Response<BaseResponse<Unit>>
}
