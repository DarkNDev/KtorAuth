package com.darkndev.ktorauth.api

import com.darkndev.ktorauth.data.PreferencesManager
import com.darkndev.ktorauth.models.AuthRequest
import com.darkndev.ktorauth.models.AuthResponse
import com.darkndev.ktorauth.utils.Urls.AUTHENTICATE
import com.darkndev.ktorauth.utils.Urls.SIGN_IN
import com.darkndev.ktorauth.utils.Urls.SIGN_UP
import com.darkndev.ktorauth.utils.Urls.USER_SECRET
import com.darkndev.ktorauth.utils.response
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthApi @Inject constructor(
    private val client: HttpClient,
    private val prefs: PreferencesManager
) {

    suspend fun signUp(request: AuthRequest): AuthResult<String> =
        response(
            request = {
                client.post {
                    url(SIGN_UP)
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }
            },
            success = {
                AuthResult.Authorized("Success")
            },
            error = { status, code, e ->
                val message = status?.bodyAsText() ?: code?.description
                ?: e.localizedMessage
                ?: "Unknown Error"
                AuthResult.Unauthorized(message)
            }
        )


    suspend fun signIn(request: AuthRequest): AuthResult<String> =
        response(
            request = {
                client.post {
                    url(SIGN_IN)
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }
            },
            success = {
                val token = it.body<AuthResponse>().token
                prefs.updateToken(token)
                AuthResult.Authorized("Authorised")
            },
            error = { status, code, e ->
                val message = status?.bodyAsText() ?: code?.description
                ?: e.localizedMessage
                ?: "Unknown Error"
                AuthResult.Unauthorized(message)
            }
        )

    suspend fun authenticate(): AuthResult<String> {
        val token =
            prefs.token.first() ?: return AuthResult.Unauthorized("Unauthorised")
        return response(
            request = {
                client.get {
                    url(AUTHENTICATE)
                    header("Authorization", "Bearer $token")
                }
            }, success = {
                AuthResult.Authorized("Authorised")
            },
            error = { _, code, e ->
                val message = code?.description
                    ?: e.localizedMessage
                    ?: "Unknown Error"
                AuthResult.Unauthorized(message)
            }
        )
    }

    suspend fun getUserId(): AuthResult<String> {
        val token =
            prefs.token.first() ?: return AuthResult.Unauthorized("Unauthorised")
        return response(
            request = {
                client.get {
                    url(USER_SECRET)
                    header("Authorization", "Bearer $token")
                }
            }, success = {
                AuthResult.Authorized(it.bodyAsText())
            },
            error = { _, code, e ->
                val message = code?.description
                    ?: e.localizedMessage
                    ?: "Unknown Error"
                AuthResult.Unauthorized(message)
            }
        )
    }
}