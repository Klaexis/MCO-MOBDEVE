package com.mobdeve.s11.group11.mco.DataHelper

import com.mobdeve.s11.group11.mco.Database.Progress

class ProgressGenerator {
    companion object {
        fun loadProgress(): ArrayList<Progress> {
            val data = ArrayList<Progress>()

            data.add(Progress(0,"Walking",1000, 60, 250.78f, "aleck@gmail.com", "March 20, 2023"))
            data.add(Progress(1,"Jogging",750, 60, 120.56f, "aleck@gmail.com", "March 21, 2023"))
            data.add(Progress(2,"Walking",1300, 70, 270.32f, "aleck@gmail.com", "March 22, 2023"))
            data.add(Progress(3,"Walking",1900, 80, 320.82f, "aleck@gmail.com", "March 23, 2023"))
            data.add(Progress(4,"Jogging",1250, 65, 263.22f, "aleck@gmail.com", "March 24, 2023"))
            data.add(Progress(5,"Jogging",500, 20, 70.98f, "aleck@gmail.com", "March 25, 2023"))

            return data
        }
    }
}
