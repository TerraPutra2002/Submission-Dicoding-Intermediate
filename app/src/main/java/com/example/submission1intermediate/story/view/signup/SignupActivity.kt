package com.example.submission1intermediate.story.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.submission1intermediate.R
import com.example.submission1intermediate.databinding.ActivitySignupBinding
import com.example.submission1intermediate.story.view.ViewModelFactory

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val viewModel by viewModels<SignupViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.btnReg.setOnClickListener {
            val name = binding.edtTextNama.text.toString()
            val email = binding.edtTextEmail.text.toString()
            val password = binding.edtTextPass.text.toString()

            showLoading(true)

            viewModel.signup(name, email, password)

            viewModel.signupResponse.observe(this) { message ->
                showLoading(false)
                AlertDialog.Builder(this).apply {
                    setTitle("Registration Successful")
                    setMessage("Akun dengan $email berhasil dibuat!")
                    setPositiveButton("Login") { _, _ -> finish() }
                    create()
                    show()
                }
            }

            viewModel.errorResponse.observe(this) { errorMessage ->
                showLoading(false)
                AlertDialog.Builder(this).apply {
                    setTitle("Error")
                    setMessage(errorMessage)
                    setPositiveButton("OK", null)
                    create()
                    show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnReg.isEnabled = !isLoading
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val titletext = ObjectAnimator.ofFloat(binding.tvSignupTitle, View.ALPHA, 1f).setDuration(500)
        val nametext = ObjectAnimator.ofFloat(binding.tvNama, View.ALPHA, 1f).setDuration(500)
        val nameedit = ObjectAnimator.ofFloat(binding.tfNamaEditLayout, View.ALPHA, 1f).setDuration(500)
        val emailtext = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(500)
        val emailedit = ObjectAnimator.ofFloat(binding.tfEmailEditLayout, View.ALPHA, 1f).setDuration(500)
        val passwordtext = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(500)
        val passwordedit = ObjectAnimator.ofFloat(binding.tfPassEditLayout, View.ALPHA, 1f).setDuration(500)
        val daftar = ObjectAnimator.ofFloat(binding.btnReg, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(titletext, nametext, nameedit, emailtext, emailedit, passwordtext, passwordedit, daftar)
            start()
        }
    }
}