// data/model/auth/LoginResponse.kt
package com.lolakashmir.retailerapp.data.model.auth

data class LoginResponse(
    val user: UserData? = null,
    val session: SessionData? = null
)