package com.mobdeve.s11.group11.mco2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.mobdeve.s11.group11.mco2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    companion object {

    }

    private val profileResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        // Check to see if the result returned is appropriate (i.e. OK)
        if(result.resultCode == RESULT_OK) {
            var
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //Login Button to go to the profile activity
        viewBinding.loginBtn.setOnClickListener {
            val intent : Intent = Intent(this@MainActivity, ProfileActivity::class.java)
            this.profileResultLauncher.launch(intent)
        }
    }
}