package com.lolakashmir.retailerapp.data.api

import com.google.gson.Gson
import com.lolakashmir.retailerapp.data.model.ApiResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val BASE_URL = "https://ecommerce-api-one-gamma.vercel.app/api/" // Replace with your API base URL
    private var retrofit: Retrofit? = null

    // Create OkHttpClient with logging interceptor
    private val httpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // Initialize Retrofit instance
    private fun getRetrofitInstance(): Retrofit {
        return retrofit ?: synchronized(this) {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create()) // For handling String responses
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .also { retrofit = it }
        }
    }

    // API service instance with lazy initialization
    val authApiService: AuthApiService by lazy {
        getRetrofitInstance().create(AuthApiService::class.java)
    }

    /**
     * Handles API calls and wraps the response in ApiResponse
     */
    suspend fun <T> safeApiCall(apiCall: suspend () -> retrofit2.Response<T>): ApiResponse<T> {
        return try {
            val response = apiCall()
            
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    ApiResponse.Success(data)
                } ?: ApiResponse.Error("Response body is null")
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMsg = try {
                    // Try to parse error message from error body
                    Gson().fromJson(errorBody, Map::class.java)?.get("message")?.toString()
                        ?: "Unknown error occurred"
                } catch (e: Exception) {
                    "Error: ${response.code()} - ${response.message()}"
                }
                ApiResponse.Error(errorMsg, response.code())
            }
        } catch (e: Exception) {
            // Handle network errors
            ApiResponse.Error(e.message ?: "Network error occurred")
        }
    }
}
