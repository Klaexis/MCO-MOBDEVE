package com.mobdeve.s11.group11.mco

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group11.mco.databinding.ActivityProgressBinding

class ProgressTrackerActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set viewBinding to activity_progress.xml
        val viewBinding : ActivityProgressBinding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        /* [EXPERIMENT CODE FOR GETTING VALUE FROM MapsTrackerActivity.kt] P.S. DOES NOT WORK
        [Hypothesis, notifyDataSetChanged() crashes the code due to the lateinit of myAdapter.
        Also the onCreate may be the problem due to the fact that it creates/loads the same data
        again and again when activity starts */
//        val progressIntent = intent
//
//        val activityMet: String = progressIntent.getStringExtra(MapsTrackerActivity.ACTIVITY_KEY).toString()
//        val distance: Int = progressIntent.getIntExtra(MapsTrackerActivity.DISTANCE_KEY, 0)
//        val time: Int = progressIntent.getIntExtra(MapsTrackerActivity.TIME_KEY, 0)
//        val calBurn : Float = progressIntent.getFloatExtra(MapsTrackerActivity.CAL_BURNED_KEY, 0f)
//        val email : String = progressIntent.getStringExtra(MapsTrackerActivity.EMAIL_KEY).toString()
//        Toast.makeText(this@ProgressTrackerActivity, distance.toString(), Toast.LENGTH_SHORT).show()
//
//        val progress = Progress(activityMet, distance, time, calBurn, email)
//        ProgressGenerator.loadProgress().add(progress)
//
//        this.myAdapter.notifyDataSetChanged()

        //Set the recyclerView and myAdapter
        this.recyclerView = viewBinding.recyclerView
        this.myAdapter = MyAdapter(ProgressGenerator.loadProgress())
        this.recyclerView.adapter = myAdapter
        this.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}