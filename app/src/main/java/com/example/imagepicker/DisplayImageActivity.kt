package com.example.imagepicker

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class DisplayImageActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_image)

        imageView = findViewById(R.id.displayImageView)

        val selectedImage = intent.getParcelableExtra<Bitmap>("selectedImage")
        selectedImage?.let {
            imageView.setImageBitmap(it)
        }
    }
}
