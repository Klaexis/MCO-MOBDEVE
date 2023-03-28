package com.mobdeve.s11.group11.mco.Database

import java.util.*

class Progress{
    var activityMET: String
        private set
    var distanceTraveled: Int
        private set
    var timeElapsed: Int
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
                timeElapsed: Int,
                caloriesBurned: Float,
                date: String,
                email: String,
                id : Long){
        this.activityMET = activityMET
        this.distanceTraveled = distanceTraveled
        this.timeElapsed = timeElapsed
        this.caloriesBurned = caloriesBurned
        this.date = date
        this.email = email
        this.id = id
    }
}
