package com.example.project

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class User(
    val userId: Int,
    val password: String,
    val avg: Double,
    val hours: Int,
    val coursePrice: Double
)

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "UserDatabase"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USERS = "Users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USER_ID = "userId"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_AVG = "avg"
        private const val COLUMN_HOURS = "hours"
        private const val COLUMN_COURSE_PRICE = "coursePrice"
    }


    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_USERS ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_USER_ID INTEGER, $COLUMN_PASSWORD TEXT, $COLUMN_AVG REAL, $COLUMN_HOURS INTEGER, $COLUMN_COURSE_PRICE REAL);"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_USERS;"
        db.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertUser(userId: String, password: String, avg: Double, hours: Double, coursePrice: Double): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_ID, userId)
        values.put(COLUMN_PASSWORD, password)
        values.put(COLUMN_AVG, avg)
        values.put(COLUMN_HOURS, hours)
        values.put(COLUMN_COURSE_PRICE, coursePrice)

        val rowId = db.insert(TABLE_USERS, null, values)
        db.close()

        return rowId != -1L
    }




    @SuppressLint("Range")
    fun getUserById (userId: Int): User? {

        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS, // Updated table name
            arrayOf(COLUMN_ID, COLUMN_PASSWORD, COLUMN_AVG, COLUMN_HOURS, COLUMN_COURSE_PRICE),
            "$COLUMN_USER_ID = ?",
            arrayOf(userId.toString()),
            null,
            null,
            null
        )



        var user: User? = null

        if (cursor.moveToFirst()) {
            val password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
            val avg = cursor.getDouble(cursor.getColumnIndex(COLUMN_AVG))
            val hours = cursor.getInt(cursor.getColumnIndex(COLUMN_HOURS))
            val coursePrice = cursor.getDouble(cursor.getColumnIndex(COLUMN_COURSE_PRICE))

            user = User(userId, password, avg, hours, coursePrice)
        }

        cursor.close()
        db.close()

        return user
    }

}
