package com.mobdeve.s11.group15.mco.Activity

import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s11.group15.mco.Database.UserDbHelper
import com.mobdeve.s11.group15.mco.Activity.IntentKeys
import com.mobdeve.s11.group15.mco.R
import com.mobdeve.s11.group15.mco.databinding.ActivityCalculatorBinding
import kotlin.math.roundToInt


class CalculatorActivity : AppCompatActivity() {
    private lateinit var calBurn: EditText
    private lateinit var userDbHelper: UserDbHelper // Variable for UserDbHelper

    //Check if edit text is empty, return true if it is empty, otherwise false
    private fun isEmpty(text: EditText): Boolean {
        val str: CharSequence = text.text.toString()
        return TextUtils.isEmpty(str)
    }

    //Function for calculating the total calories burned
    private fun calculateCalBurn(Activity: String, weight: Int, minutes: Int, seconds: Int): Float {
        var totalCalBurn: Float
        var MET: Float = 0f

        var time: Float = minutes.toFloat() + (seconds.toFloat() / 60)

        if (Activity == "Walking") {
            MET = 3.5F
        } else if (Activity == "Jogging") {
            MET = 7.0F
        }

        totalCalBurn = (time * (MET * 3.5 * weight) / 200).toFloat()

        return (totalCalBurn * 100f).roundToInt() / 100f
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set viewbinding to activity_calculator.xml
        val viewBinding : ActivityCalculatorBinding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //Variables for the inputs
        var radioAction = findViewById<RadioGroup>(R.id.radioActivity)
        var et_weight = findViewById<EditText>(R.id.et_calculator_weightInput)
        var et_time_minutes = findViewById<EditText>(R.id.et_calculator_timeInputMinutes)
        var et_time_seconds = findViewById<EditText>(R.id.et_calculator_timeInputSeconds)
        this.calBurn = findViewById<EditText>(R.id.et_calculator_caloriesOutput)
        this.calBurn.isEnabled = false

        //Get intent from previous activity
        val calculatorIntent = intent
        val getEmail = calculatorIntent.getStringExtra(IntentKeys.EMAIL_KEY.name) //Email from ProfileActivity.kt

        userDbHelper = UserDbHelper.getInstance(this@CalculatorActivity)!! // Initialize UserDbHelper
        val getUser = userDbHelper.getUser(getEmail.toString()) // Get the user details according to the email
        et_weight.setText(getUser.weight.toString()) //Set the text to the weight of the user
        et_weight.isEnabled = false // Set to false so user cannot change

        viewBinding.calculateBtn.setOnClickListener{
            var isFilled = true

            // get selected radio button from radioAction
            var selectedId: Int = radioAction.checkedRadioButtonId

            // find the radiobutton by returned id
            var radioActionButton = findViewById<RadioButton>(selectedId)

            if(isEmpty(et_weight) || isEmpty(et_time_minutes)){ //If empty then don't calculate
                isFilled = false
                Toast.makeText(this@CalculatorActivity, "Please Fill Minutes Elapsed", Toast.LENGTH_SHORT).show()
            }

            if(isFilled){ //If filled then calculate

                var calculated = if(isEmpty(et_time_seconds)){
                    calculateCalBurn(
                        radioActionButton.text.toString(),
                        et_weight.text.toString().toInt(),
                        et_time_minutes.text.toString().toInt(),
                        0)
                } else {
                    calculateCalBurn(
                        radioActionButton.text.toString(),
                        et_weight.text.toString().toInt(),
                        et_time_minutes.text.toString().toInt(),
                        et_time_seconds.text.toString().toInt())
                }

                calBurn.setText(calculated.toString())
            }
        }
    }
}