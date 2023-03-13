package com.mobdeve.s11.group11.mco2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobdeve.s11.group11.mco2.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityProfileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.historyBtn.setOnClickListener {
            val historyIntent : Intent = Intent(this@ProfileActivity, ProgressTrackerActivity::class.java)
            startActivity(historyIntent)
            finish()
        }

        viewBinding.startTrackingBtn.setOnClickListener {
            val startTrackingIntent: Intent = Intent(this@ProfileActivity, MapsTrackerActivity::class.java)
            startActivity(startTrackingIntent)
            finish()
        }

        viewBinding.calculatorBtn.setOnClickListener{
            val calculatorIntent: Intent = Intent(this@ProfileActivity, CalculatorActivity::class.java)
            startActivity(calculatorIntent)
            finish()
        }
    }
}