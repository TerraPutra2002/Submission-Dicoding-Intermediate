package com.example.submission1intermediate.story.data.response

data class DetailStoryResponse(
    val error: Boolean,
    val message: String,
    val story: DetailStory
)

data class DetailStory(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val createdAt: String,
    val lat: Double?,
    val lon: Double?
)