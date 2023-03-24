package com.mobdeve.s11.group11.mco.DataHelper

import com.mobdeve.s11.group11.mco.Database.User

class UserData {
    companion object {
        fun loadUser(): ArrayList<User> {
            val data = ArrayList<User>()

            data.add(User(0,"Aleck", "Lim", 70, "aleck@gmail.com", "123"))
            data.add(User(1,"Abrience", "Liao", 70, "abrience@gmail.com", "1234"))

            return data
        }
    }
}