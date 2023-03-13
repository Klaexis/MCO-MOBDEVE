package com.mobdeve.s11.group11.mco2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

class CalculatorActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val autotextview = findViewById<AutoCompleteTextView>(R.id.actv_activitySelected)
        val activities = resources.getStringArray(R.array.activities)
        val adapter = ArrayAdapter(this,R.layout.activity_list, activities)
        autotextview.setAdapter(adapter)

    }
}