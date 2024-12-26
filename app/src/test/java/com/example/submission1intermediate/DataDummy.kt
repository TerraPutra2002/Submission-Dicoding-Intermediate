package com.example.submission1intermediate

import com.example.submission1intermediate.story.data.response.ListStoryItem

object DataDummy {
    fun generateDummyListStory(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                photoUrl = "https://example.com/photo$i.jpg",
                createdAt = "2024-12-05T12:34:56Z",
                name = "Author $i",
                description = "Description $i",
                lon = 112.6 + i,
                lat = -7.9 + i,
                id = i.toString()
            )
            items.add(story)
        }
        return items
    }
}