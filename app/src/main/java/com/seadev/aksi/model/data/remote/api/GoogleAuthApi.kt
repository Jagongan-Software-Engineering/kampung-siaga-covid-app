package com.seadev.aksi.model.data.remote.api

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.seadev.aksi.model.BuildConfig

object GoogleAuthApi{
    val signRequest =  BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                // Your server's client ID, not your Android client ID.
                .setServerClientId(BuildConfig.WEB_CLIENT_AUTH)
                // Only show accounts previously used to sign in.
                .setFilterByAuthorizedAccounts(false)
                .build())
        .build()
}