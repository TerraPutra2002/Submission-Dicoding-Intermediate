package com.example.submission1intermediate.story.view.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission1intermediate.story.data.StoryRepository
import com.example.submission1intermediate.story.data.response.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class SignupViewModel(
    private val storyRepository: StoryRepository
) : ViewModel() {

    val signupResponse = MutableLiveData<String>()
    val errorResponse = MutableLiveData<String>()

    fun signup(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = storyRepository.signup(name, email, password)
                signupResponse.postValue(response.message)
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorMessage = Gson().fromJson(errorBody, ErrorResponse::class.java).message
                errorResponse.postValue(errorMessage)
            }
        }
    }
}