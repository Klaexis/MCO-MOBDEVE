package com.mobdeve.s11.group11.mco2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobdeve.s11.group11.mco2.databinding.ActivityGooglemapsBinding

class MapsTrackerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityGooglemapsBinding = ActivityGooglemapsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.stopBtn.setOnClickListener{
            val stopIntent: Intent = Intent(this@MapsTrackerActivity, ProgressTrackerActivity::class.java)
            startActivity(stopIntent)
            finish()
        }
    }
}