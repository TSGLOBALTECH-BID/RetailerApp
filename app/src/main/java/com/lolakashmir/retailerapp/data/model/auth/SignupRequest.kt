// data/model/auth/SignupRequest.kt
package com.lolakashmir.retailerapp.data.model.auth

data class SignupRequest(
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val password_confirmation: String
)