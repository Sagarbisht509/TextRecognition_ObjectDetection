package com.example.textrecognizer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.textrecognizer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textDetect.setOnClickListener {
            intent = Intent(this, TextRecognize::class.java)
            startActivity(intent)
        }

        binding.objectDetect.setOnClickListener {
            intent = Intent(this, ObjectDetectActivity::class.java)
            startActivity(intent)
        }

        binding.digitalInk.setOnClickListener {
            intent = Intent(this, DigitalInkActivity::class.java)
            startActivity(intent)
        }

        binding.realtimeObjectDetect.setOnClickListener {
            intent = Intent(this, RealtimeObjectDetectionActivity::class.java)
            startActivity(intent)
        }
    }
}