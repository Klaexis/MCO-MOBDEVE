package com.mobdeve.s11.group11.mco2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.mobdeve.s11.group11.mco2.databinding.ActivityGooglemapsBinding

class MapsTrackerActivity : AppCompatActivity() {
//    companion object {
//        const val ACTIVITY_KEY = "ACTIVITY_KEY"
//        const val DISTANCE_KEY = "DISTANCE_KEY"
//        const val TIME_KEY = "TIME_KEY"
//        const val CAL_BURNED_KEY = "CAL_BURNED_KEY"
//        const val EMAIL_KEY = "EMAIL_KEY"
//    }
//    private lateinit var myAdapter: MyAdapter

//    private val mapsTrackerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
//    { result: ActivityResult ->
//        // Check to see if the result returned is appropriate (i.e. OK)
//        if (result.resultCode == RESULT_OK) {
//            val activityMet: String = result.data!!.getStringExtra(MapsTrackerActivity.ACTIVITY_KEY).toString()
//            val distance: Int = result.data!!.getIntExtra(MapsTrackerActivity.DISTANCE_KEY, 0)
//            val time: Int = result.data!!.getIntExtra(MapsTrackerActivity.TIME_KEY, 0)
//            val calBurn : Float = result.data!!.getFloatExtra(MapsTrackerActivity.CAL_BURNED_KEY, 0f)
//            val email : String = result.data!!.getStringExtra(MapsTrackerActivity.EMAIL_KEY).toString()
//
//            val progress = Progress(activityMet, distance, time, calBurn, email)
//            ProgressGenerator.loadProgress().add(progress)
//
//            this.myAdapter.notifyDataSetChanged()
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityGooglemapsBinding = ActivityGooglemapsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.stopBtn.setOnClickListener{
            val stopIntent: Intent = Intent(this@MapsTrackerActivity, ProgressTrackerActivity::class.java)

//            stopIntent.putExtra(ACTIVITY_KEY, "Jogging")
//            stopIntent.putExtra(DISTANCE_KEY, 2000)
//            stopIntent.putExtra(TIME_KEY, 120)
//            stopIntent.putExtra(CAL_BURNED_KEY, 520.38f)
//            stopIntent.putExtra(EMAIL_KEY, "aleck@gmail.com")

            setResult(Activity.RESULT_OK, stopIntent)
            startActivity(stopIntent)
//            this.mapsTrackerLauncher.launch(stopIntent)

            finish()
        }
    }
}