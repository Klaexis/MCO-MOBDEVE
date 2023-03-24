package com.mobdeve.s11.group11.mco.Database

class User(val id : Long = 0,
           val firstName: String,
           val lastName: String,
           var weight: Int,
           val email: String,
           val password: String)