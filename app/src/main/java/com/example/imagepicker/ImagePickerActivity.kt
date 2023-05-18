package com.example.imagepicker

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class ImagePickerActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_GALLERY = 1
    private val REQUEST_IMAGE_CAMERA = 2

    private lateinit var imageView: ImageView

    private var selectedImage: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_picker)

        imageView = findViewById(R.id.imageView)

        val galleryButton: Button = findViewById(R.id.galleryButton)
        galleryButton.setOnClickListener {
            openGallery()
        }

        val cameraButton: Button = findViewById(R.id.cameraButton)
        cameraButton.setOnClickListener {
            openCamera()
        }
    }

    private fun openGallery() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_IMAGE_GALLERY
            )
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
        }
    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_IMAGE_CAMERA
            )
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_IMAGE_CAMERA)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_GALLERY -> {
                    data?.data?.let { uri ->
                        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                        displaySelectedImage(bitmap)
                    }
                }
                REQUEST_IMAGE_CAMERA -> {
                    data?.extras?.get("data")?.let { image ->
                        if (image is Bitmap) {
                            displaySelectedImage(image)
                        }
                    }
                }
            }
        }
    }

    private fun displaySelectedImage(bitmap: Bitmap) {
        selectedImage = bitmap
        imageView.setImageBitmap(bitmap)
    }

    fun onNextButtonClicked(view: View) {
        // Pass the selected image data to another activity
        val intent = Intent(this, DisplayImageActivity::class.java)
        intent.putExtra("selectedImage", selectedImage)
        startActivity(intent)
    }
}
