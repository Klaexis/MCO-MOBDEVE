package com.mobdeve.s11.group11.mco.DataHelper

import com.mobdeve.s11.group11.mco.Database.Progress

class ProgressGenerator {
    companion object {
        fun loadProgress(): ArrayList<Progress> {
            val data = ArrayList<Progress>()

            data.add(Progress("Walking",1000, 60, 250.78f, "March 20, 2023", "aleck@gmail.com", 0))
            data.add(Progress("Jogging",750, 60, 120.56f,  "March 21, 2023", "aleck@gmail.com", 1))
            data.add(Progress("Walking",1300, 70, 270.32f,  "March 22, 2023", "aleck@gmail.com", 2))
            data.add(Progress("Walking",1900, 80, 320.82f, "March 23, 2023", "aleck@gmail.com",  3))
            data.add(Progress("Jogging",1250, 65, 263.22f,  "March 24, 2023", "aleck@gmail.com", 4))
            data.add(Progress("Jogging",500, 20, 70.98f,  "March 25, 2023", "aleck@gmail.com", 5))

            return data
        }
    }
}
