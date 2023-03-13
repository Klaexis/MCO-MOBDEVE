package com.mobdeve.s11.group11.mco2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.mobdeve.s11.group11.mco2.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity(){
    companion object {
        const val EMAIL_KEY = "EMAIL_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        var et_email = findViewById<EditText>(R.id.et_login_email)
        var et_password = findViewById<EditText>(R.id.et_login_password)

        //Login Button to go to the profile activity
        viewBinding.loginBtn.setOnClickListener {
            val email : String = et_email.text.toString()
            val password : String = et_password.text.toString()

            if((email == "sample@gmail.com") && (password == "123")){
                val intent : Intent = Intent(this@LoginActivity, ProfileActivity::class.java)
                startActivity(intent).apply{
                    intent.putExtra(EMAIL_KEY, email)
                }
            } else {
                Toast.makeText(this@LoginActivity, "Incorrect email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}