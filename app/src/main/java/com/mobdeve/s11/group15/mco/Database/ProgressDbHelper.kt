package com.mobdeve.s11.group15.mco.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ProgressDbHelper(context: Context?) :
    SQLiteOpenHelper(context, DbReferences.DATABASE_NAME, null, DbReferences.DATABASE_VERSION) {

    // The singleton pattern design
    companion object {
        private var instance: ProgressDbHelper? = null

        @Synchronized
        fun getInstance(context: Context): ProgressDbHelper? {
            if (instance == null) {
                instance = ProgressDbHelper(context.applicationContext)
            }
            return instance
        }
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(DbReferences.CREATE_TABLE_STATEMENT)
    }

    // Called when a new version of the DB is present; hence, an "upgrade" to a newer version
    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL(DbReferences.DROP_TABLE_STATEMENT)
        onCreate(sqLiteDatabase)
    }

    @Synchronized
    fun addProgress(progress: Progress) {
        val database = this.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DbReferences.COLUMN_NAME_ACTIVITY_MET, progress.activityMET)
        values.put(DbReferences.COLUMN_NAME_DISTANCE_TRAVELED, progress.distanceTraveled)
        values.put(DbReferences.COLUMN_NAME_TIME_ELAPSED, progress.timeElapsed)
        values.put(DbReferences.COLUMN_NAME_CALORIES_BURNED, progress.caloriesBurned)
        values.put(DbReferences.COLUMN_NAME_DATE, progress.date)
        values.put(DbReferences.COLUMN_NAME_EMAIL, progress.email)

        // Insert the new row
        // Inserting returns the primary key value of the new row, but we can ignore that if we don’t need it
        database.insert(DbReferences.TABLE_NAME, null, values)
        database.close()
    }

    // Function for deleting the progress record
    @Synchronized
    fun deleteProgress(id: Long, email: String){
        val database = this.writableDatabase
        val whereClause = "id=? AND email=?"
        val whereArgs = arrayOf<String>(id.toString(), email)

        // Delete progress record according to the id of the record and email of the user
        database.delete(DbReferences.TABLE_NAME, whereClause, whereArgs)

        database.close()
    }

    // Get all progress records of the user
    fun getAllProgress(email: String): ArrayList<Progress>  {
        val database: SQLiteDatabase = this.readableDatabase
        val whereClause = "email=?"
        val whereArgs = arrayOf<String>(email)

        // Query to get progress record according to the user's email
        val c : Cursor = database.query(
            DbReferences.TABLE_NAME,
            arrayOf("activity_met", "distance_traveled", "time_elapsed", "calories_burned", "date", "email", "id"),
            whereClause,
            whereArgs,
            null,
            null,
            DbReferences._ID + " DESC",
            null
        )

        val contacts : ArrayList<Progress>  = ArrayList()

        // Add existing progress records into an array list
        while(c.moveToNext()) {
            contacts.add(Progress(
                c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_NAME_ACTIVITY_MET)),
                c.getInt(c.getColumnIndexOrThrow(DbReferences.COLUMN_NAME_DISTANCE_TRAVELED)),
                c.getInt(c.getColumnIndexOrThrow(DbReferences.COLUMN_NAME_TIME_ELAPSED)),
                c.getFloat(c.getColumnIndexOrThrow(DbReferences.COLUMN_NAME_CALORIES_BURNED)),
                c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_NAME_DATE)),
                c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_NAME_EMAIL)),
                c.getLong(c.getColumnIndexOrThrow(DbReferences._ID))
            ))
        }

        c.close()
        database.close()

        return contacts
    }

    private object DbReferences {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Progress_Database.db"

        const val TABLE_NAME = "Progress"
        const val _ID = "id"
        const val COLUMN_NAME_ACTIVITY_MET = "activity_met"
        const val COLUMN_NAME_DISTANCE_TRAVELED = "distance_traveled"
        const val COLUMN_NAME_TIME_ELAPSED = "time_elapsed"
        const val COLUMN_NAME_CALORIES_BURNED = "calories_burned"
        const val COLUMN_NAME_DATE = "date"
        const val COLUMN_NAME_EMAIL = "email"

        const val CREATE_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_ACTIVITY_MET + " TEXT, " +
                    COLUMN_NAME_DISTANCE_TRAVELED + " TEXT, " +
                    COLUMN_NAME_TIME_ELAPSED + " TEXT, " +
                    COLUMN_NAME_CALORIES_BURNED + " TEXT, " +
                    COLUMN_NAME_DATE + " TEXT, " +
                    COLUMN_NAME_EMAIL + " TEXT)"

        const val DROP_TABLE_STATEMENT = "DROP TABLE IF EXISTS " + TABLE_NAME
    }

}