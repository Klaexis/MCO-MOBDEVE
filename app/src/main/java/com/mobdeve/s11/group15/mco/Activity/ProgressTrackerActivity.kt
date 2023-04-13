package com.mobdeve.s11.group15.mco.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group15.mco.Database.ProgressDbHelper
import com.mobdeve.s11.group15.mco.Activity.IntentKeys
import com.mobdeve.s11.group15.mco.Adapter.ProgressAdapter
import com.mobdeve.s11.group15.mco.databinding.ActivityProgressBinding

class ProgressTrackerActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressAdapter: ProgressAdapter
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
        this.progressAdapter = ProgressAdapter(progressDbHelper.getAllProgress(getEmail.toString())) // Set the recyclerView to all progress
        this.recyclerView.adapter = progressAdapter
        this.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}