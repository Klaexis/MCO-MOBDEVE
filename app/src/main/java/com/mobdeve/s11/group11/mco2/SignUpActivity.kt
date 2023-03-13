package com.mobdeve.s11.group11.mco2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.mobdeve.s11.group11.mco2.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivitySignupBinding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        var tv_login = findViewById<TextView>(R.id.login_link)

        //SignUp Button set data of user and go to the Login Page and Activity
        viewBinding.signupBtn.setOnClickListener {
            val signupIntent : Intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(signupIntent)
        }

        //Login link to go to Login page and activity
        tv_login.setOnClickListener {
            val loginIntent : Intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }
}