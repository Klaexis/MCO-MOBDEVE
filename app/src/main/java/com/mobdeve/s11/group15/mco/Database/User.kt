package com.mobdeve.s11.group15.mco.Database

class User{
    var firstName: String
        private set
    var lastName: String
        private set
    var weight: Int
        private set
    var email: String
        private set
    var password: String
        private set
    var id: Long = 0
        private set

    constructor(firstName: String,
                lastName: String,
                weight: Int,
                email: String,
                password: String,
                id : Long) {
        this.lastName = lastName
        this.firstName = firstName
        this.weight = weight
        this.email = email
        this.password = password
        this.id = id
    }
}