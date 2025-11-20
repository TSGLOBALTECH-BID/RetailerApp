// app/src/main/java/com/lolakashmir/retailerapp/di/AppModule.kt
package com.lolakashmir.retailerapp.di

import android.content.Context
import com.lolakashmir.retailerapp.data.manager.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }
}