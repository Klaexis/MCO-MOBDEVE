package com.mobdeve.s11.group11.mco2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioGroup

import com.mobdeve.s11.group11.mco2.databinding.ActivityCalculatorBinding

class CalculatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityCalculatorBinding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val radioActivityGroup = findViewById<RadioGroup>(R.id.radioActivity)
    }
}