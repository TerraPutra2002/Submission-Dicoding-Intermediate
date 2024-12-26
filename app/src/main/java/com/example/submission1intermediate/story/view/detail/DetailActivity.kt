package com.example.submission1intermediate.story.view.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.submission1intermediate.R
import com.example.submission1intermediate.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storyName = intent.getStringExtra(EXTRA_EVENT_NAME)
        val storyDescription = intent.getStringExtra(EXTRA_EVENT_DESCRIPTION)
        val storyMediaCover = intent.getStringExtra(EXTRA_EVENT_PHOTO)

        binding.storyTitle.text = storyName
        binding.storyDescription.text = HtmlCompat.fromHtml(storyDescription ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
        Glide.with(this)
            .load(storyMediaCover)
            .into(binding.storyImage)

        binding.btnBackToMain.setOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        const val EXTRA_EVENT_NAME = "extra_story_name"
        const val EXTRA_EVENT_DESCRIPTION = "extra_story_description"
        const val EXTRA_EVENT_PHOTO = "extra_story_photo"
    }
}