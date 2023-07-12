package com.darkndev.ktorauth.data

import com.darkndev.ktorauth.api.AuthApi
import com.darkndev.ktorauth.api.AuthResult
import com.darkndev.ktorauth.models.AuthRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authApi: AuthApi
) {

    suspend fun signUp(username: String, password: String): AuthResult<String> {
        val result = authApi.signUp(AuthRequest(username, password))
        if (result is AuthResult.Authorized) {
            return signIn(username, password)
        }
        return result
    }

    suspend fun signIn(username: String, password: String): AuthResult<String> {
        val result = authApi.signIn(AuthRequest(username, password))
        if (result is AuthResult.Authorized)
            return authenticate()
        return result
    }

    suspend fun authenticate(): AuthResult<String> {
        return authApi.getUserId()
    }
}