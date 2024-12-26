package com.example.submission1intermediate.story.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.submission1intermediate.story.data.ResultState
import com.example.submission1intermediate.story.data.StoryRepository
import com.example.submission1intermediate.story.data.response.ListStoryItem
import kotlinx.coroutines.launch

class MainViewModel(private val repository: StoryRepository) : ViewModel() {

    fun getStories(): LiveData<PagingData<ListStoryItem>> {
        return repository.getStories()
    }

    fun getSession() = repository.getSession().asLiveData()

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}