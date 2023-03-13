package com.mobdeve.s11.group11.mco2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobdeve.s11.group11.mco2.databinding.ActivityProgressBinding

class ProgressTrackerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityProgressBinding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //Recycler view
    }
}