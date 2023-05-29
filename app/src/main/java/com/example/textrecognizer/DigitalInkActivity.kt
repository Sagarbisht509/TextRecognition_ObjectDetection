package com.example.textrecognizer

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.textrecognizer.StrokeManager.clear
import com.example.textrecognizer.StrokeManager.download
import com.example.textrecognizer.StrokeManager.recognize
import com.example.textrecognizer.databinding.ActivityDigitalInk2Binding
import com.google.mlkit.common.MlKitException
import com.google.mlkit.vision.digitalink.DigitalInkRecognition
import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModel
import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModelIdentifier
import com.google.mlkit.vision.digitalink.DigitalInkRecognizer
import com.google.mlkit.vision.digitalink.DigitalInkRecognizerOptions
import com.google.mlkit.vision.digitalink.Ink
import com.google.mlkit.vision.digitalink.RecognitionResult

class DigitalInkActivity : AppCompatActivity() {

    lateinit var binding: ActivityDigitalInk2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDigitalInk2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        hideTitleBar()

        download()
        binding.buttonRecognize.setOnClickListener {
            recognize(
                binding.textResult
            )
        }
        binding.buttonClear.setOnClickListener {
            binding.drawView.clear()
            clear()
            binding.textResult.text = ""
        }
    }

    private fun hideTitleBar() {
        this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

    override fun onResume() {
        super.onResume()
        hideTitleBar()
    }

    override fun onPause() {
        super.onPause()
        hideTitleBar()
    }

    override fun onStop() {
        super.onStop()

        hideTitleBar()
    }
}