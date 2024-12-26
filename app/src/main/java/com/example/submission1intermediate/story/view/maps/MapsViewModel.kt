package com.example.submission1intermediate.story.view.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submission1intermediate.story.data.ResultState
import com.example.submission1intermediate.story.data.StoryRepository
import com.example.submission1intermediate.story.data.response.ListStoryItem

class MapsViewModel(private val repository: StoryRepository) : ViewModel() {

    fun getStoriesWithLocation(): LiveData<ResultState<List<ListStoryItem>>> {
        return repository.getStoriesWithLocation()
    }
}