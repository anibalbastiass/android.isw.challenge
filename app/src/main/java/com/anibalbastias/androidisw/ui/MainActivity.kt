package com.anibalbastias.androidisw.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.anibalbastias.androidisw.R

class MainActivity : AppCompatActivity() {

    var layoutId = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
    }

}
