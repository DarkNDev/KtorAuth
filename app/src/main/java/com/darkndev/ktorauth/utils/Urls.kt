package com.darkndev.ktorauth.utils

object Urls {
    //Must Change BASE_URL
    private const val BASE_URL = "http://192.168.1.10:8080"
    const val SIGN_UP = "$BASE_URL/signup"
    const val SIGN_IN = "$BASE_URL/signin"
    const val AUTHENTICATE = "$BASE_URL/authenticate"
    const val USER_SECRET = "$BASE_URL/secret"
}