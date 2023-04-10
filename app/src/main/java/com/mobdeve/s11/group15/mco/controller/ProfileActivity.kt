package com.mobdeve.s11.group15.mco.controller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s11.group15.mco.database.UserDbHelper
import com.mobdeve.s11.group15.mco.R
import com.mobdeve.s11.group15.mco.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity() {
    private lateinit var userDbHelper: UserDbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set viewBinding to activity_profile.xml
        val viewBinding : ActivityProfileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //Get email from login to determine who is the current user
        val profileIntent = intent
        val getEmail = profileIntent.getStringExtra(IntentKeys.EMAIL_KEY.name)

        userDbHelper = UserDbHelper.getInstance(this@ProfileActivity)!! // Initialize UserDbHelper
        val getUser = userDbHelper.getUser(getEmail.toString()) //Find the email of user

        //Set TextViews to the User's credentials
        viewBinding.tvProfileFirstName.text = getUser.firstName
        viewBinding.tvProfileLastName.text = getUser.lastName
        viewBinding.tvProfileEmail.text = getUser.email

        //For editing current weight
        val weightEditable : TextView = findViewById(R.id.et_profile_weight)
        weightEditable.text = getUser.weight.toString()

        viewBinding.UpdateWeightButton.setOnClickListener{
            //When update button is click set the new weight
            var et_weight = findViewById<EditText>(R.id.et_profile_weight)
            var weight = et_weight.text.toString()

            // Update the weight of user in the database
            userDbHelper.updateWeight(getEmail.toString(), weight.toInt())

            // Upon clicking update button hide the keyboard and editable text will not be focused
            hideKeyboard(this@ProfileActivity)
        }

        viewBinding.historyBtn.setOnClickListener {
            //When clicked go to the progress history activity
            val historyIntent : Intent = Intent(this@ProfileActivity, ProgressTrackerActivity::class.java)
            historyIntent.putExtra(IntentKeys.EMAIL_KEY.name, getEmail) //Send email to ProgressTrackerActivity.kt
            startActivity(historyIntent)
        }

        viewBinding.startTrackingBtn.setOnClickListener {
            //When clicked go to the tracking/googlemaps activity
            val startTrackingIntent: Intent = Intent(this@ProfileActivity, MapsTrackerActivity::class.java)
            startTrackingIntent.putExtra(IntentKeys.EMAIL_KEY.name, getEmail) //Send email to MapsTrackerActivity.kt
            startActivity(startTrackingIntent)
        }

        viewBinding.calculatorBtn.setOnClickListener{
            //When clicked go to the calculator activity
            val calculatorIntent: Intent = Intent(this@ProfileActivity, CalculatorActivity::class.java)
            calculatorIntent.putExtra(IntentKeys.EMAIL_KEY.name, getEmail) //Send email to CalculatorActivity.kt
            startActivity(calculatorIntent)
        }

        viewBinding.logoutBtn.setOnClickListener{
            // When clicked destroy activity and go back to LoginActivity
            val logoutIntent : Intent = Intent(this@ProfileActivity, LoginActivity::class.java)
            startActivity(logoutIntent)
            finish()
        }
    }

    // Function to hide keyboard and clear focus on edit text
    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val view: View? = activity.currentFocus
        if (view != null) {
            view.clearFocus()
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }
    }
}