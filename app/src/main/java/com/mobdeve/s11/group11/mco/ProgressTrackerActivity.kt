package com.mobdeve.s11.group11.mco

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group11.mco.Database.ProgressDbHelper
import com.mobdeve.s11.group11.mco.databinding.ActivityProgressBinding

class ProgressTrackerActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private lateinit var progressDbHelper: ProgressDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set viewBinding to activity_progress.xml
        val viewBinding : ActivityProgressBinding = ActivityProgressBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //Get intent from previous activity
        val progressTrackerIntent = intent
        val getEmail = progressTrackerIntent.getStringExtra(IntentKeys.EMAIL_KEY.name) //Get email from intent

        //Set the recyclerView and myAdapter
        this.recyclerView = viewBinding.recyclerView
        progressDbHelper = ProgressDbHelper.getInstance(this@ProgressTrackerActivity)!! // Initialize ProgressDbHelper
        this.myAdapter = MyAdapter(progressDbHelper.getAllProgress(getEmail.toString())) // Set the recyclerView to all progress
        this.recyclerView.adapter = myAdapter
        this.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}