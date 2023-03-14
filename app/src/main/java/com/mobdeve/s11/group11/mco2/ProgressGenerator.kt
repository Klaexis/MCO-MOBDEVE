package com.mobdeve.s11.group11.mco2

class ProgressGenerator {
    companion object {
        fun loadProgress(): ArrayList<Progress> {
            val data = ArrayList<Progress>()

            data.add(Progress(1000, 60, 250.78f, "aleck@gmail.com"))
            data.add(Progress(750, 60, 120.56f, "aleck@gmail.com"))
            data.add(Progress(1300, 70, 270.32f, "aleck@gmail.com"))
            data.add(Progress(1900, 80, 320.82f, "aleck@gmail.com"))
            data.add(Progress(1250, 65, 263.22f, "aleck@gmail.com"))
            data.add(Progress(500, 20, 70.98f, "aleck@gmail.com"))

            return data
        }
    }
}
