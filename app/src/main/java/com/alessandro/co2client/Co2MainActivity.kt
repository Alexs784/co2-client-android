package com.alessandro.co2client

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Co2MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_co2_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}