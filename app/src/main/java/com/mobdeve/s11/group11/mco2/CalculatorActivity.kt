package com.mobdeve.s11.group11.mco2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.mobdeve.s11.group11.mco2.databinding.ActivityCalculatorBinding

class CalculatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityCalculatorBinding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val autoTextView = findViewById<AutoCompleteTextView>(R.id.actv_activitySelected)
        val activities = resources.getStringArray(R.array.activities)
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, activities)
        autoTextView.setAdapter(adapter)
    }
}