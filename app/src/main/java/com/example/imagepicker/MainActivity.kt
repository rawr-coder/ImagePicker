package com.example.imagepicker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imagePickerButton: Button = findViewById(R.id.imagePickerButton)
        imagePickerButton.setOnClickListener {
            val intent = Intent(this, ImagePickerActivity::class.java)
            startActivity(intent)
        }

    }
}