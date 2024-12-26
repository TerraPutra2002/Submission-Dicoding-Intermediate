package com.example.submission1intermediate.story.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.submission1intermediate.story.data.pref.UserPreference
import com.example.submission1intermediate.story.data.response.DetailStory
import com.example.submission1intermediate.story.data.response.ListStoryItem
import com.example.submission1intermediate.story.data.retrofit.ApiService
import com.example.submission1intermediate.story.data.response.LoginResponse
import com.example.submission1intermediate.story.data.response.RegisterResponse

class StoryRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun saveSession(token: String) {
        userPreference.saveSession(token)
    }

    fun getStories(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }

    suspend fun getStoryDetail(token: String, storyId: String): DetailStory {
        val response = apiService.getStoryDetail("Bearer $token", storyId)
        if (!response.error) {
            return response.story
        } else {
            throw Exception(response.message)
        }
    }

    fun getStoriesWithLocation(): LiveData<ResultState<List<ListStoryItem>>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.getStoriesWithLocation()
            val storiesWithLocation = response.listStory.filter { it.lat != null && it.lon != null }
            emit(ResultState.Success(storiesWithLocation))
        } catch (e: Exception) {
            emit(ResultState.Error(e.message ?: "An error occurred"))
        }
    }

    fun getSession() = userPreference.getSession()

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun signup(name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.login(email, password)
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null

        fun getInstance(apiService: ApiService, pref: UserPreference): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(pref, apiService).also { instance = it }
            }
    }
}