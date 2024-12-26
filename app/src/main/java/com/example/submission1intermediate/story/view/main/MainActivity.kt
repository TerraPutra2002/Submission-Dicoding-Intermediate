package com.example.submission1intermediate.story.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1intermediate.R
import com.example.submission1intermediate.databinding.ActivityMainBinding
import com.example.submission1intermediate.story.data.LoadingStateAdapter
import com.example.submission1intermediate.story.data.ResultState
import com.example.submission1intermediate.story.data.StoryAdapter
import com.example.submission1intermediate.story.view.ViewModelFactory
import com.example.submission1intermediate.story.view.addstory.AddStoryActivity
import com.example.submission1intermediate.story.view.detail.DetailActivity
import com.example.submission1intermediate.story.view.maps.MapsActivity
import com.example.submission1intermediate.story.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var storyAdapter: StoryAdapter
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var loadingStateAdapter: LoadingStateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupFab()
        setupRecyclerView()
        observeSession()
    }

    private fun setupView() {
        supportActionBar?.show()
    }

    private fun setupFab() {
        binding.fabAddStory.setOnClickListener {
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        loadingStateAdapter = LoadingStateAdapter { storyAdapter.retry() }

        storyAdapter = StoryAdapter { story ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_EVENT_NAME, story.name)
                putExtra(DetailActivity.EXTRA_EVENT_DESCRIPTION, story.description)
                putExtra(DetailActivity.EXTRA_EVENT_PHOTO, story.photoUrl)
            }
            startActivity(intent)
        }

        binding.rvStoryList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = storyAdapter
        }
    }

    private fun observeSession() {
        viewModel.getSession().observe(this) { token ->
            if (token == null) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                observeStories()
            }
        }
    }

    private fun observeStories() {
        viewModel.getStories().observe(this) { pagingData ->
            storyAdapter.submitData(lifecycle, pagingData)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_logout -> {
                logoutUser()
                true
            }

            R.id.btn_map -> {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logoutUser() {
        viewModel.logout()
        showToast("Anda telah logout.")
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}