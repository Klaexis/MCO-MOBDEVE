package com.mobdeve.s11.group11.mco.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserDbHelper(context: Context?) :
    SQLiteOpenHelper(context, DbReferences.DATABASE_NAME, null, DbReferences.DATABASE_VERSION) {

    // The singleton pattern design
    companion object {
        private var instance: UserDbHelper? = null

        @Synchronized
        fun getInstance(context: Context): UserDbHelper? {
            if (instance == null) {
                instance = UserDbHelper(context.applicationContext)
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
    fun addUser(user: User) {
        val database = this.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DbReferences.COLUMN_NAME_LAST_NAME, user.lastName)
        values.put(DbReferences.COLUMN_NAME_FIRST_NAME, user.firstName)
        values.put(DbReferences.COLUMN_NAME_WEIGHT, user.weight)
        values.put(DbReferences.COLUMN_NAME_EMAIL, user.email)
        values.put(DbReferences.COLUMN_NAME_PASSWORD, user.password)

        // Insert the new row
        // Inserting returns the primary key value of the new row, but we can ignore that if we don’t need it
        database.insert(DbReferences.TABLE_NAME, null, values)
        database.close()
    }

    fun getAllUserDefault(): ArrayList<User>  {
        val database: SQLiteDatabase = this.readableDatabase

        val c : Cursor = database.query(
            DbReferences.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            DbReferences.COLUMN_NAME_LAST_NAME + " ASC, " + DbReferences.COLUMN_NAME_FIRST_NAME + " ASC",
            null
        )

        val user : ArrayList<User>  = ArrayList()

        while(c.moveToNext()) {
            user.add(User(
                c.getLong(c.getColumnIndexOrThrow(DbReferences._ID)),
                c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_NAME_FIRST_NAME)),
                c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_NAME_LAST_NAME)),
                c.getInt(c.getColumnIndexOrThrow(DbReferences.COLUMN_NAME_WEIGHT)),
                c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_NAME_EMAIL)),
                c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_NAME_PASSWORD))
            ))
        }

        c.close()
        database.close()

        return user
    }

    private object DbReferences {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "User_Database.db"

        const val TABLE_NAME = "User"
        const val _ID = "id"
        const val COLUMN_NAME_FIRST_NAME = "first_name"
        const val COLUMN_NAME_LAST_NAME = "last_name"
        const val COLUMN_NAME_WEIGHT = "weight"
        const val COLUMN_NAME_EMAIL = "email"
        const val COLUMN_NAME_PASSWORD = "password"

        const val CREATE_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_FIRST_NAME + " TEXT, " +
                    COLUMN_NAME_LAST_NAME + " TEXT, " +
                    COLUMN_NAME_WEIGHT + " TEXT, " +
                    COLUMN_NAME_EMAIL + " TEXT, " +
                    COLUMN_NAME_PASSWORD + " TEXT)"

        const val DROP_TABLE_STATEMENT = "DROP TABLE IF EXISTS " + TABLE_NAME
    }
}