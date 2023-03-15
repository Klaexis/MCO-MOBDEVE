package com.mobdeve.s11.group11.mco2

class UserData {
    companion object {
        fun loadUser(): ArrayList<User> {
            val data = ArrayList<User>()

            data.add(User("Aleck", "Lim", 70, "aleck@gmail.com", "123"))
            data.add(User("Abrience", "Liao", 70, "abrience@gmail.com", "1234"))

            return data
        }
    }
}