package com.mobdeve.s11.group15.mco.database

import java.util.*

class Progress{
    var activityMET: String
        private set
    var distanceTraveled: Int
        private set
    var timeElapsedMinutes: Int
        private set
    var timeElapsedSeconds: Int
        private set
    var caloriesBurned: Float
        private set
    var date: String
        private set
    var email: String
        private set
    var id: Long = 0
        private set

    constructor(activityMET: String,
                distanceTraveled: Int,
                timeElapsedMinutes: Int,
                timeElapsedSeconds : Int,
                caloriesBurned: Float,
                date: String,
                email: String,
                id : Long){
        this.activityMET = activityMET
        this.distanceTraveled = distanceTraveled
        this.timeElapsedMinutes = timeElapsedMinutes
        this.timeElapsedSeconds = timeElapsedSeconds
        this.caloriesBurned = caloriesBurned
        this.date = date
        this.email = email
        this.id = id
    }
}
