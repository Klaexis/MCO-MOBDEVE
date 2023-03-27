package com.mobdeve.s11.group11.mco.DataHelper

import com.mobdeve.s11.group11.mco.Database.User

class UserData {
    companion object {
        fun loadUser(): ArrayList<User> {
            val data = ArrayList<User>()

            data.add(User("Aleck", "Lim", 70, "aleck@gmail.com", "123",0))
            data.add(User("Abrience", "Liao", 70, "abrience@gmail.com", "1234",1))

            return data
        }
    }
}