package com.example.sqlitecrud.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sqlitecrud.models.User

class MyDbHelper( var context: Context): SQLiteOpenHelper(context, DB_NAME, null, VERSION), DbInterface {
    companion object{
        const val DB_NAME = "my_contact"
        const val TABLE_NAME ="contact_table"
        const val VERSION = 1

        const val ID = "id"
        const val NAME = "name"
        const val NUMBER = "number"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "create table $TABLE_NAME ($ID integer not null primary key autoincrement unique , $NAME text, $NUMBER text not null )"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun addUsers(user: User) {
        val  database= this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, user.name)
        contentValues.put(NUMBER, user.number)
        database.insert(TABLE_NAME, null , contentValues)
        database.close()
    }

    override fun showUsers():List<User> {
        val list = ArrayList<User>()
        val database = this.readableDatabase
        val query = "select * from $TABLE_NAME"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()){
            do {
                val user = User(cursor.getString(1), cursor.getString(2), cursor.getInt(0))
                list.add(user)
                }while (cursor.moveToNext())
        }
        return list



    }

    override fun deleteUser(user: User) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$ID=?", arrayOf(user.id.toString()))
        database.close()
    }

    override fun editUser(user: User) {
     val database = this.writableDatabase
        val contentValues= ContentValues()
        contentValues.put(NAME, user.name)
        contentValues.put(NUMBER, user.number)
        database.update(TABLE_NAME, contentValues, "$ID=?", arrayOf(user.id.toString()))
        database.close()

    }
}