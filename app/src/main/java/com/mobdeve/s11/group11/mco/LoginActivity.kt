package com.mobdeve.s11.group11.mco

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s11.group11.mco.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(){
    companion object {
        const val EMAIL_KEY = "EMAIL_KEY"
    }

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

        var isTrue = false
        //Login Button to go to the profile activity
        viewBinding.loginBtn.setOnClickListener {
            val email : String = et_email.text.toString()
            val password : String = et_password.text.toString()

            //Check every user
            for (i in 0 until UserData.loadUser().count()){
                val getUser = UserData.loadUser().get(i) //Get every user

                if(email == getUser.Email) { //Check email of every user if match
                    if (password == getUser.Password) { //Check password of the user with the matched email if password matches
                        //Send intent to ProfileActivity.kt
                        val loginIntent: Intent = Intent(this@LoginActivity, ProfileActivity::class.java)

                        isTrue = true

                        loginIntent.putExtra(EMAIL_KEY, email) //Send the email of user to ProfileActivity.kt
                        startActivity(loginIntent)

                        finish()
                    }
                }
            }

            if(!isTrue){ //If email/password is wrong then show toast
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