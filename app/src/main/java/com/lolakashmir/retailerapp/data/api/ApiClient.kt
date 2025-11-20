package com.lolakashmir.retailerapp.data.api

import com.lolakashmir.retailerapp.data.manager.TokenManager
import com.lolakashmir.retailerapp.data.model.ApiResponse
import com.lolakashmir.retailerapp.data.model.BaseResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClient @Inject constructor(
   private val tokenManager: TokenManager) {


    private val BASE_URL = "http://10.0.2.2:3000/api/" //"https://ecommerce-api-one-gamma.vercel.app/api/" // Replace with your API base URL
    private var retrofit: Retrofit? = null

    // Create OkHttpClient with logging interceptor
    private val httpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val authInterceptor = Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()

            // Get token from TokenManager
            tokenManager.getToken()?.let { token ->
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }

            requestBuilder.method(original.method, original.body)
            chain.proceed(requestBuilder.build())
        }

        OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
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
   suspend fun <T> safeApiCall(apiCall: suspend () -> Response<BaseResponse<T>>): ApiResponse<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let { baseResponse ->
                if (baseResponse.success) {
                    baseResponse.data?.let { data ->
                        ApiResponse.Success(data)
                    } ?: ApiResponse.Error("No data in response")
                } else {
                    ApiResponse.Error(baseResponse.message ?: "Request failed")
                }
            } ?: ApiResponse.Error("Response body is null")
        } else {
            ApiResponse.Error("Network error: ${response.code()}")
        }
    } catch (e: Exception) {
        ApiResponse.Error(e.message ?: "Unknown error occurred")
    }
}
}
