package com.example.submission1intermediate.story.view.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission1intermediate.story.data.ResultState
import com.example.submission1intermediate.story.data.StoryRepository
import com.example.submission1intermediate.story.data.response.DetailStory
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: StoryRepository) : ViewModel() {

    private val _storyDetail = MutableLiveData<ResultState<DetailStory>>()
    val storyDetail: LiveData<ResultState<DetailStory>> = _storyDetail

    fun getStoryDetail(token: String, storyId: String) {
        _storyDetail.value = ResultState.Loading

        viewModelScope.launch {
            try {
                val detail = repository.getStoryDetail(token, storyId)
                _storyDetail.value = ResultState.Success(detail)
            } catch (e: Exception) {
                Log.e("DetailViewModel", "Error fetching story detail", e)
                _storyDetail.value = ResultState.Error(e.message ?: "Unknown error")
            }
        }
    }
}