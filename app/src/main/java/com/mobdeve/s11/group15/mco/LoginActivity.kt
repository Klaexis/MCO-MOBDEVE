package com.mobdeve.s11.group15.mco

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s11.group15.mco.Database.UserDbHelper
import com.mobdeve.s11.group15.mco.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(){
    private lateinit var userDbHelper: UserDbHelper // Variable for UserDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set viewBinding to activity_login.xml
        val viewBinding : ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //Get IDs of EditText in activity_login.xml
        var et_email = findViewById<EditText>(R.id.et_login_email)
        var et_password = findViewById<EditText>(R.id.et_login_password)

        //Text signup link to go to signup activity
        var tv_signup = findViewById<TextView>(R.id.signup_link)

        //Login Button to go to the profile activity
        viewBinding.loginBtn.setOnClickListener {
            val email : String = et_email.text.toString()
            val password : String = et_password.text.toString()

            userDbHelper = UserDbHelper.getInstance(this@LoginActivity)!! //Initialize UserDbHelper
            // Check email and password that was inputted, if True then authenticate, otherwise don't
            if(userDbHelper.checkLogin(email, password)){
                val loginIntent: Intent = Intent(this@LoginActivity, ProfileActivity::class.java)

                loginIntent.putExtra(IntentKeys.EMAIL_KEY.name, email) //Send the email of user to ProfileActivity.kt
                startActivity(loginIntent)

                finish()
            } else {
                Toast.makeText(this@LoginActivity, "Incorrect email or password", Toast.LENGTH_SHORT).show()
            }
        }

        //Image button to show/hide password if clicked
        var isHidden = true
        viewBinding.loginShowHideBtn.setOnClickListener{
            isHidden = if(isHidden){
                viewBinding.etLoginPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                viewBinding.loginShowHideBtn.setBackgroundResource(R.drawable.baseline_visibility_24)
                false
            } else {
                viewBinding.etLoginPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                viewBinding.loginShowHideBtn.setBackgroundResource(R.drawable.baseline_visibility_off_24)
                true
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