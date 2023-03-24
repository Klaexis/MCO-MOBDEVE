package com.mobdeve.s11.group11.mco.DataHelper

import com.mobdeve.s11.group11.mco.Database.Progress

class ProgressGenerator {
    companion object {
        fun loadProgress(): ArrayList<Progress> {
            val data = ArrayList<Progress>()

            data.add(Progress("Walking",1000, 60, 250.78f, "aleck@gmail.com"))
            data.add(Progress("Jogging",750, 60, 120.56f, "aleck@gmail.com"))
            data.add(Progress("Walking",1300, 70, 270.32f, "aleck@gmail.com"))
            data.add(Progress("Walking",1900, 80, 320.82f, "aleck@gmail.com"))
            data.add(Progress("Jogging",1250, 65, 263.22f, "aleck@gmail.com"))
            data.add(Progress("Jogging",500, 20, 70.98f, "aleck@gmail.com"))

            return data
        }
    }
}
