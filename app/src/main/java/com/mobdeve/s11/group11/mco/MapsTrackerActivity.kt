package com.mobdeve.s11.group11.mco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mobdeve.s11.group11.mco.databinding.ActivityGooglemapsBinding

class MapsTrackerActivity : AppCompatActivity() {
    companion object {
        //Set of KEYs for String name of the values that are about to be sent to ProgressTrackerActivity.kt
        const val ACTIVITY_KEY = "ACTIVITY_KEY"
        const val DISTANCE_KEY = "DISTANCE_KEY"
        const val TIME_KEY = "TIME_KEY"
        const val CAL_BURNED_KEY = "CAL_BURNED_KEY"
        const val EMAIL_KEY = "EMAIL_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set viewBinding to activity_googlemaps.xml
        val viewBinding : ActivityGooglemapsBinding = ActivityGooglemapsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.stopBtn.setOnClickListener{
            //When STOP button is clicked send values to ProgressTrackerActivity.kt
            val stopIntent: Intent = Intent(this@MapsTrackerActivity, ProgressTrackerActivity::class.java)

            //Send these values [P.S. these are just sample data]
            stopIntent.putExtra(ACTIVITY_KEY, "Jogging")
            stopIntent.putExtra(DISTANCE_KEY, 2000)
            stopIntent.putExtra(TIME_KEY, 120)
            stopIntent.putExtra(CAL_BURNED_KEY, 520.38f)
            stopIntent.putExtra(EMAIL_KEY, "aleck@gmail.com")

            startActivity(stopIntent)

            finish()
        }
    }
}