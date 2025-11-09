package com.lolakashmir.retailerapp.di

import com.lolakashmir.retailerapp.ui.screens.signup.SignupViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {
    
    @Provides
    @ViewModelScoped
    fun provideSignupViewModel(): SignupViewModel {
        return SignupViewModel()
    }
    
    // Add other ViewModel providers here as needed
}
