package com.darkndev.ktorauth.ui.auth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkndev.ktorauth.api.AuthResult
import com.darkndev.ktorauth.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val repository: AuthRepository
) : ViewModel() {

    init {
        authenticate()
    }

    var signUpUsername = state.get<String>("SIGN_UP_U") ?: ""
        set(value) {
            field = value
            state["SIGN_UP_U"] = value
        }

    var signUpPassword = state.get<String>("SIGN_UP_P") ?: ""
        set(value) {
            field = value
            state["SIGN_UP_P"] = value
        }

    var signInUsername = state.get<String>("SIGN_IN_U") ?: ""
        set(value) {
            field = value
            state["SIGN_IN_U"] = value
        }

    var signInPassword = state.get<String>("SIGN_IN_P") ?: ""
        set(value) {
            field = value
            state["SIGN_IN_P"] = value
        }

    fun signUpClicked() = viewModelScope.launch {
        statusChannel.send(true)
        val result = repository.signUp(signUpUsername, signUpPassword)
        statusChannel.send(false)
        resultChannel.send(result)
    }

    fun signInClicked() = viewModelScope.launch {
        statusChannel.send(true)
        val result = repository.signIn(signInUsername, signInPassword)
        statusChannel.send(false)
        resultChannel.send(result)
    }

    private fun authenticate() = viewModelScope.launch {
        statusChannel.send(true)
        val result = repository.authenticate()
        statusChannel.send(false)
        resultChannel.send(result)
    }

    private val resultChannel = Channel<AuthResult<String>>()
    val authResults = resultChannel.receiveAsFlow()

    private val statusChannel = Channel<Boolean>()
    val status = statusChannel.receiveAsFlow()

}