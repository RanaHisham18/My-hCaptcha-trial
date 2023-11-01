package com.example.myhcaptcha

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myhcaptcha.databinding.ActivityResult2Binding


class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
          var binding: ActivityResult2Binding = ActivityResult2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // Get captcha value from the intent
        val captchaValue = intent.getStringExtra("captcha")
        // Handle the captcha value as per your application logic
        if (!captchaValue.isNullOrEmpty()) {
            // Captcha verification successful, perform appropriate actions
            binding.resultTextView.text = "Captcha Value: $captchaValue"
        } else {
            // Captcha verification failed or empty captcha value, handle accordingly
            binding.resultTextView.text = "Captcha verification failed"
        }
    }
}