package com.mobdeve.s11.group11.mco

import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s11.group11.mco.DataHelper.UserData
import com.mobdeve.s11.group11.mco.databinding.ActivityCalculatorBinding


class CalculatorActivity : AppCompatActivity() {
    private lateinit var calBurn: EditText

    //Check if edit text is empty return true if it is otherwise false
    private fun isEmpty(text: EditText): Boolean {
        val str: CharSequence = text.text.toString()
        return TextUtils.isEmpty(str)
    }

    //Function for calculating the total calories burned
    private fun calculateCalBurn(Activity: String, weight: Int, time : Int): Float{
        var totalCalBurn: Float
        var MET: Float = 0f
        if(Activity == "Walking"){
            MET = 3.5F
        } else if(Activity == "Jogging"){
            MET = 7.0F
        }

        totalCalBurn = (time * (MET * 3.5 * weight) / 200).toFloat()
        return totalCalBurn
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set viewbinding to activity_calculator.xml
        val viewBinding : ActivityCalculatorBinding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //Variables for the inputs
        var radioAction = findViewById<RadioGroup>(R.id.radioActivity)
        var et_weight = findViewById<EditText>(R.id.et_calculator_weightInput)
        var et_time = findViewById<EditText>(R.id.et_calculator_timeInput)
        this.calBurn = findViewById<EditText>(R.id.et_calculator_caloriesOutput)
        this.calBurn.isEnabled = false

        //Get intent from previous activity
        val calculatorIntent = intent
        val getEmail = calculatorIntent.getStringExtra(LoginActivity.EMAIL_KEY) //Email from profile

        var i : Int = 0
        UserData.loadUser().forEach {
            if(UserData.loadUser().get(i).Email.equals(getEmail)) //Check email from data
            {
                val getUser = UserData.loadUser().get(i) //Get the user with the corresponding email

                et_weight.setText(getUser.weight.toString()) //Set the text to the weight of the user
                et_weight.isEnabled = false
            }
            i++
        }


        viewBinding.calculateBtn.setOnClickListener{
            var isFilled = true

            // get selected radio button from radioAction
            var selectedId: Int = radioAction.checkedRadioButtonId

            // find the radiobutton by returned id
            var radioActionButton = findViewById<RadioButton>(selectedId)

            if(isEmpty(et_weight) || isEmpty(et_time)){ //If empty then don't calculate
                isFilled = false
                Toast.makeText(this@CalculatorActivity, "Please Fill All Blanks", Toast.LENGTH_SHORT).show()
            }

            if(isFilled){ //If filled then calculate
                Toast.makeText(this@CalculatorActivity, radioActionButton.text, Toast.LENGTH_SHORT).show()
                val calculated = calculateCalBurn(radioActionButton.text.toString(), et_weight.text.toString().toInt(), et_time.text.toString().toInt())
                calBurn.setText(calculated.toString())
            }
        }
    }
}