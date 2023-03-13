package com.mobdeve.s11.group11.mco2

import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s11.group11.mco2.databinding.ActivityCalculatorBinding


class CalculatorActivity : AppCompatActivity() {
    private lateinit var calBurn: EditText
    private fun isEmpty(text: EditText): Boolean {
        val str: CharSequence = text.text.toString()
        return TextUtils.isEmpty(str)
    }
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

        val viewBinding : ActivityCalculatorBinding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        var radioAction = findViewById<RadioGroup>(R.id.radioActivity)
        var et_weight = findViewById<EditText>(R.id.et_calculator_weightInput)
        var et_time = findViewById<EditText>(R.id.et_calculator_timeInput)

        this.calBurn = findViewById<EditText>(R.id.et_calculator_caloriesOutput)
        this.calBurn.isEnabled = false

        viewBinding.calculateBtn.setOnClickListener{
            var isFilled = true

            // get selected radio button from radioGroup
            var selectedId: Int = radioAction.checkedRadioButtonId

            // find the radiobutton by returned id
            var radioActionButton = findViewById<RadioButton>(selectedId)

            if(isEmpty(et_weight) || isEmpty(et_time)){
                isFilled = false
                Toast.makeText(this@CalculatorActivity, "Please Fill All Blanks", Toast.LENGTH_SHORT).show()
            }

            if(isFilled){
                Toast.makeText(this@CalculatorActivity, radioActionButton.text, Toast.LENGTH_SHORT).show()
                val calculated = calculateCalBurn(radioActionButton.text.toString(), et_weight.text.toString().toInt(), et_time.text.toString().toInt())
                calBurn.setText(calculated.toString())
            }
        }
    }
}