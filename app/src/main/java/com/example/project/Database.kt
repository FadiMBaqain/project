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
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UserDatabase.db"
        private const val TABLE_NAME = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_AVG = "avg"
        private const val COLUMN_HOURS = "hours"
        private const val COLUMN_COURSE_PRICE = "course_price"
    }


    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_PASSWORD TEXT, " +
                "$COLUMN_AVG REAL, " +
                "$COLUMN_HOURS INTEGER, " +
                "$COLUMN_COURSE_PRICE REAL)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertUser(userId: Int, password: String, avg: Double, hours: Int, coursePrice: Double): Long {
        val values = ContentValues().apply {
            put(COLUMN_ID, userId)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_AVG, avg)
            put(COLUMN_HOURS, hours)
            put(COLUMN_COURSE_PRICE, coursePrice)
        }

        val db = writableDatabase
        val rowId = db.insert(TABLE_NAME, null, values)
        db.close()

        return rowId
    }


    @SuppressLint("Range")
    fun getUserById (userId: Int): User? {

        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_ID, COLUMN_PASSWORD, COLUMN_AVG, COLUMN_HOURS, COLUMN_COURSE_PRICE),
            "$COLUMN_ID = ?",
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
