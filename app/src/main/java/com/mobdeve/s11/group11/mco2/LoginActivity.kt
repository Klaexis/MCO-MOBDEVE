package com.mobdeve.s11.group11.mco2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s11.group11.mco2.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(){
    companion object {
        const val EMAIL_KEY = "EMAIL_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        var et_email = findViewById<EditText>(R.id.et_login_email)
        var et_password = findViewById<EditText>(R.id.et_login_password)
        var tv_signup = findViewById<TextView>(R.id.signup_link)

        //Login Button to go to the profile activity
        viewBinding.loginBtn.setOnClickListener {
            val email : String = et_email.text.toString()
            val password : String = et_password.text.toString()

            for (i in 0 until UserData.loadUser().count()){
                val getUser = UserData.loadUser().get(i)

                if(email == getUser.Email) {
                    if (password == getUser.Password) {
                        val loginIntent: Intent = Intent(this@LoginActivity, ProfileActivity::class.java)

                        loginIntent.putExtra(EMAIL_KEY, email)
                        startActivity(loginIntent)

                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Incorrect email or password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        //Sign up link to go to Sign Up page and activity
        tv_signup.setOnClickListener {
            val signupIntent : Intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(signupIntent)
            finish()
        }
    }
}