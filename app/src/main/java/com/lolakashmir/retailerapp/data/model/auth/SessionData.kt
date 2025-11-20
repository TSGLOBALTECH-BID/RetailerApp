package com.lolakashmir.retailerapp.data.model.auth

data class SessionData (
   val access_token: String,
   val expires_in: Int,
   val refresh_token: String
)