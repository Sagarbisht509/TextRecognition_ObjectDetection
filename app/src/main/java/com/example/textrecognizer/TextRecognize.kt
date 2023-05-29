package com.example.textrecognizer

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.textrecognizer.databinding.ActivityMainBinding
import com.example.textrecognizer.databinding.ActivityTextRecognizeBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.IOException

class TextRecognize : AppCompatActivity() {
    private lateinit var binding: ActivityTextRecognizeBinding
    private var imageUri: Uri? = null
    private val pickImage = 100

    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTextRecognizeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pickImage.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {

            if (data == null) {
                Toast.makeText(this, "please select image", Toast.LENGTH_SHORT).show()
            } else {
                imageUri = data.data
                binding.imageView.setImageURI(imageUri)
                recognizeText()
            }

        }
    }

    private fun recognizeText() {
        try {
            val inputImage = imageUri?.let { InputImage.fromFilePath(this, it) }

            val result = inputImage?.let {
                recognizer.process(it)
                    .addOnSuccessListener { visionText ->
                        val recognizedText = visionText.text
                        binding.resultTxt.text = recognizedText
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                    }
            }
        } catch (e: IOException) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}