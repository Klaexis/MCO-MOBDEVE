package com.mobdeve.s11.group11.mco2

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s11.group11.mco2.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityProfileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val profileIntent = intent
        val getEmail = profileIntent.getStringExtra(LoginActivity.EMAIL_KEY)

        var i : Int = 0
        UserData.loadUser().forEach {
            if(UserData.loadUser().get(i).Email.equals(getEmail))
            {
                val getUser = UserData.loadUser().get(i)

                viewBinding.tvProfileFirstName.text = getUser.firstName
                viewBinding.tvProfileLastName.text = getUser.lastName
                viewBinding.tvProfileEmail.text = getUser.Email

                val weightEditable : TextView = findViewById(R.id.et_profile_weight)
                weightEditable.text = getUser.weight.toString()

                viewBinding.UpdateWeightButton.setOnClickListener{
                    var et_weight = findViewById<EditText>(R.id.et_profile_weight)
                    var weight = et_weight.text.toString()

                    getUser.weight = weight.toInt()
                }
            }
            i++
        }

        viewBinding.historyBtn.setOnClickListener {
            val historyIntent : Intent = Intent(this@ProfileActivity, ProgressTrackerActivity::class.java)
            startActivity(historyIntent)
        }

        viewBinding.startTrackingBtn.setOnClickListener {
            val startTrackingIntent: Intent = Intent(this@ProfileActivity, MapsTrackerActivity::class.java)
            startActivity(startTrackingIntent)
        }

        viewBinding.calculatorBtn.setOnClickListener{
            val calculatorIntent: Intent = Intent(this@ProfileActivity, CalculatorActivity::class.java)
            startActivity(calculatorIntent)
        }
    }
}