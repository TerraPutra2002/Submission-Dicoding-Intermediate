package com.example.submission1intermediate.story.di

import android.content.Context
import com.example.submission1intermediate.story.data.StoryRepository
import com.example.submission1intermediate.story.data.pref.UserPreference
import com.example.submission1intermediate.story.data.pref.dataStore
import com.example.submission1intermediate.story.data.retrofit.ApiClient

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val userPreference = UserPreference.getInstance(context.dataStore)
        val apiService = ApiClient.getApiService(userPreference)

        return StoryRepository.getInstance(apiService, userPreference)
    }
}