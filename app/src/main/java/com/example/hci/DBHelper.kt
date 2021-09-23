package com.example.hci

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(var context: Context): SQLiteOpenHelper(context, database_name, null, 1) {

    override fun onCreate(db: SQLiteDatabase){
        val create = "CREATE TABLE IF NOT EXISTS '${table_name}' ("+
                "'${id_name}' INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "'${email_name}' TEXT NOT NULL,"+
                "'${name_name}' TEXT NOT NULL,"+
                "'${surname_name}' TEXT NOT NULL," +
                "'${password_name}' TEXT NOT NULL," +
                "'${address_name}' TEXT NOT NULL," +
                "'${phone_name}' TEXT NOT NULL," +
                "'${image_name}' INTEGER," +
                "UNIQUE (${email_name}))"

        //db.execSQL("DROP TABLE IF EXISTS '${table_name}'" )
        db.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS '${table_name}'" )
        onCreate(db)
    }

    fun getAll(): String{
        val db = this.readableDatabase
        var ret = ""
        val cursor = db.rawQuery("SELECT * FROM '${table_name}'", null)
        if(cursor.moveToFirst()){
            do{
                ret += cursor.getString(cursor.getColumnIndex(id_name))+" "
                ret += cursor.getString(cursor.getColumnIndex(email_name))+" "
                ret += cursor.getString(cursor.getColumnIndex(name_name))+" "
                ret += cursor.getString(cursor.getColumnIndex(surname_name))+" "
                ret += cursor.getString(cursor.getColumnIndex(password_name))+" "
                ret += cursor.getString(cursor.getColumnIndex(address_name))+" "
                ret += cursor.getString(cursor.getColumnIndex(phone_name))+"\n"
            }while (cursor.moveToNext())
        }
        cursor.close()
        //db.close()
        return ret
    }

    fun insert(nemail: String, nname: String, nsurname: String, npassword: String, naddress: String, nphone: String): Int{
        //if(!this.select(nemail).equals("")) return 1
        val db = this.writableDatabase
        val query = "INSERT INTO '${table_name}' ('${email_name}', '${name_name}','${surname_name}',  '${password_name}', '${address_name}', '${phone_name}')VALUES " +
                "('${nemail}', '${nname}', '${nsurname}', '${npassword}', '${naddress}', '${nphone}')"
        try{
            db.execSQL(query)
        } catch (e: Exception){
            return 1
        }
        //db.close()
        return 0
    }

    fun select(nemail: String): User? {
        val db = this.readableDatabase
        //var ret = ""
        val usr: User
        val cursor = db.rawQuery("SELECT * FROM '${table_name}' WHERE email == '${nemail}'", null)
        when(cursor.count){
            0 -> {
                cursor.close()
                //db.close()
                return null
            }
            1 -> {
                cursor.moveToFirst()
                usr = User(cursor.getString(cursor.getColumnIndex(id_name)), cursor.getString(cursor.getColumnIndex(email_name)), cursor.getString(cursor.getColumnIndex(
                    name_name)), cursor.getString(cursor.getColumnIndex(
                    surname_name)), cursor.getString(cursor.getColumnIndex(password_name)), cursor.getString(cursor.getColumnIndex(address_name)), cursor.getString(cursor.getColumnIndex(
                    phone_name)))
                /*ret += cursor.getString(cursor.getColumnIndex(id_name))+" "
                ret += cursor.getString(cursor.getColumnIndex(email_name))+" "
                ret += cursor.getString(cursor.getColumnIndex(name_name))+" "
                ret += cursor.getString(cursor.getColumnIndex(password_name))+"\n"*/
                //ret = usr
            }
            else ->{
                cursor.close()
                //db.close()
                return null
            }
        }
        cursor.close()
        //db.close()
        return usr
    }

    companion object{
        private const val database_name = "database.db"
        private const val table_name = "users"
        private const val id_name = "id"
        private const val email_name = "email"
        private const val name_name = "name"
        private const val surname_name = "surname"
        private const val image_name = "image"
        private const val address_name = "address"
        private const val phone_name = "phone"
        private const val password_name = "password"
    }
}