package com.lolakashmir.retailerapp


import android.app.Application
import com.lolakashmir.retailerapp.data.api.ApiClient
import com.lolakashmir.retailerapp.data.manager.TokenManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class RetailerApplication : Application(){
   
    @Inject
    lateinit var apiClient: ApiClient

    override fun onCreate() {
        super.onCreate()
         // No need to manually initialize ApiClient as it's already handled by Hilt
        // The ApiClient will be created with the TokenManager automatically
    }
}
