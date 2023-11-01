package com.example.myhcaptcha

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.hcaptcha.sdk.HCaptcha
import com.hcaptcha.sdk.HCaptchaException
import com.hcaptcha.sdk.HCaptchaTokenResponse

class MainActivity : AppCompatActivity() {

    private val SITE_KEY = "77caa5ca-2925-4510-b4b3-68941015666f"
    private val hCaptcha = HCaptcha.getClient(this)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hCaptchaButton = findViewById<View>(R.id.hCaptchaButton)
        hCaptchaButton.setOnClickListener {
            // Trigger hCaptcha verification when the button is clicked
            hCaptcha.setup(SITE_KEY).verifyWithHCaptcha()
                .addOnSuccessListener { response: HCaptchaTokenResponse ->
                    val userResponseToken = response.tokenResult
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra("captcha", userResponseToken)
                    launcher.launch(intent)
                }
                .addOnFailureListener { e: HCaptchaException ->
                    // Error handling here: trigger another verification, display a toast, etc.
                    Log.d("hCaptcha", "hCaptcha failed: ${e.message} (${e.statusCode})")
                }
                .addOnOpenListener {
                    // Useful for analytics purposes
                    Log.d("hCaptcha", "hCaptcha is now visible.")
                }
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val captchaValue = data.getStringExtra("captcha") ?: ""
                        // Handle the captcha value here
                        Log.d("hCaptcha", "Captcha Value: $captchaValue")
                    }
                }
                Activity.RESULT_CANCELED -> {
                    Log.d("hCaptcha", "hCaptcha failed")
                }
            }
        }
}