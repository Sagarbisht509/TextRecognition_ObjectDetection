package com.example.textrecognizer

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.textrecognizer.databinding.ActivityMainBinding
import com.example.textrecognizer.databinding.ActivityObjectDetectBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.ObjectDetector
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import com.google.mlkit.vision.objects.defaults.PredefinedCategory
import java.io.IOException

class ObjectDetectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityObjectDetectBinding
    private val pickImage = 100
    private var imageUri: Uri? = null

    private lateinit var objectDetector : ObjectDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityObjectDetectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val options = ObjectDetectorOptions.Builder()
            .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
            .enableMultipleObjects()
            .enableClassification()  // Optional
            .build()

        objectDetector = ObjectDetection.getClient(options)

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

                detectObject()
            }

        }
    }

    private fun detectObject() {
        try {
            val inputImage = imageUri?.let { InputImage.fromFilePath(this, it) }

            if (inputImage != null) {
                objectDetector.process(inputImage)
                    .addOnSuccessListener { detectedObjects ->
                        var objects : String = ""
                        for (detectedObject in detectedObjects) {
                            val boundingBox = detectedObject.boundingBox
                            val trackingId = detectedObject.trackingId
                            for (label in detectedObject.labels) {
                                /*val index = label.index
                                if (PredefinedCategory.FOOD_INDEX == index) {

                                }*/
                                val text = label.text
                                if (PredefinedCategory.FOOD == text) {
                                    objects = text + "\n"
                                }
                                else if(PredefinedCategory.FASHION_GOOD == text) {
                                    objects += text + "\n"
                                }
                                else if(PredefinedCategory.PLACE == text) {
                                    objects += text + "\n"
                                }
                                else if(PredefinedCategory.PLANT == text) {
                                    objects += text + "\n"
                                }
                            }
                        }

                        binding.resultTxt.text = objects

                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
                    }
            }
        } catch (e: IOException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()

        }
    }
}