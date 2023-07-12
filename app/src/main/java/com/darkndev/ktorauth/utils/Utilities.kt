package com.darkndev.ktorauth.utils

import android.util.Log
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

inline fun <T> response(
    request: () -> HttpResponse,
    success: (HttpResponse) -> T,
    error: (HttpResponse?, HttpStatusCode?, Throwable) -> T
) = try {
    success(request())
} catch (e: RedirectResponseException) {
    //3xx - responses
    Log.e("AuthApiImpl", "RedirectResponseException: ", e)
    error(e.response, e.response.status, e)
} catch (e: ClientRequestException) {
    //4xx - responses
    Log.e("AuthApiImpl", "ClientRequestException: ", e)
    error(e.response, e.response.status, e)
} catch (e: ServerResponseException) {
    //5xx - responses
    Log.e("AuthApiImpl", "ServerResponseException: ", e)
    error(e.response, e.response.status, e)
} catch (e: Exception) {
    //others - responses
    Log.e("AuthApiImpl", "Exception: ", e)
    error(null, null, e)
}