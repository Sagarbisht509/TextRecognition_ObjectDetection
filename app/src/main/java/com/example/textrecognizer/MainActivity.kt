package com.example.textrecognizer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.textrecognizer.databinding.ActivityMainBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    }
}