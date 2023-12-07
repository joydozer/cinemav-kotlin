package com.example.cinemav

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_page)

        val btnBack = findViewById<ImageView>(R.id.imageView2)
        btnBack.setOnClickListener{
            finish()
        }
    }
}