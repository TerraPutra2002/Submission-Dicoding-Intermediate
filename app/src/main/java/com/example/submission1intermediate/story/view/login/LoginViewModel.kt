package com.example.submission1intermediate.story.view.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission1intermediate.story.data.StoryRepository
import com.example.submission1intermediate.story.data.response.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(
    private val storyRepository: StoryRepository
) : ViewModel() {
    val loginResponse = MutableLiveData<String>()
    val errorResponse = MutableLiveData<String>()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = storyRepository.login(email, password)
                storyRepository.saveSession(response.loginResult.token)
                loginResponse.postValue("Login berhasil")
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = Gson().fromJson(errorBody, ErrorResponse::class.java).message
                errorResponse.postValue(errorMessage)
            }
        }
    }
}