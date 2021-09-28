package com.example.hci

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(var context: Context): SQLiteOpenHelper(context, database_name, null, 1) {

    override fun onCreate(db: SQLiteDatabase){
        var create = "CREATE TABLE IF NOT EXISTS '${table_user}' ("+
                "'${id_user}' INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "'${email_user}' TEXT NOT NULL,"+
                "'${name_user}' TEXT NOT NULL,"+
                "'${surname_user}' TEXT NOT NULL," +
                "'${password_user}' TEXT NOT NULL," +
                "'${address_user}' TEXT NOT NULL," +
                "'${phone_user}' TEXT NOT NULL," +
                "'${image_user}' INTEGER," +
                "UNIQUE (${email_user}))"

        //db.execSQL("DROP TABLE IF EXISTS '${table_user}'" )
        db.execSQL(create)

        create = "CREATE TABLE IF NOT EXISTS '${table_vendor}'(" +
                "'${id_vendor}' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'${name_vendor}' TEXT NOT NULL," +
                "UNIQUE (${name_vendor}))"

        db.execSQL(create)

        create = "CREATE TABLE IF NOT EXISTS '${table_product}'(" +
                "'${id_product}' INTEGER ,"+
                "'${title_product}' TEXT NOT NULL," +
                "'${description_product}' TEXT NOT NULL,"+
                "'${price_product}' FLOAT NOT NULL," +
                "'${review_product}' INTEGER NOT NULL," +
                "'${rating_product}' FLOAT NOT NULL," +
                "'${image_product}' INTEGER NOT NULL," +
                "FOREIGN KEY (${id_product}) REFERENCES '${table_vendor}' (${id_vendor})," +
                "PRIMARY KEY (${id_product},${title_product}))"

        db.execSQL(create)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS '${table_user}'" )
        onCreate(db)
    }

    fun getAll(): String{
        val db = this.readableDatabase
        var ret = ""
        val cursor = db.rawQuery("SELECT * FROM '${table_user}'", null)
        if(cursor.moveToFirst()){
            do{
                ret += cursor.getString(cursor.getColumnIndex(id_user))+" "
                ret += cursor.getString(cursor.getColumnIndex(email_user))+" "
                ret += cursor.getString(cursor.getColumnIndex(name_user))+" "
                ret += cursor.getString(cursor.getColumnIndex(surname_user))+" "
                ret += cursor.getString(cursor.getColumnIndex(password_user))+" "
                ret += cursor.getString(cursor.getColumnIndex(address_user))+" "
                ret += cursor.getString(cursor.getColumnIndex(phone_user))+"\n"
            }while (cursor.moveToNext())
        }
        cursor.close()
        return ret
    }

    fun insert_vendor(name: String): Int {
        val db = this.writableDatabase
        val query = "INSERT INTO '${table_vendor}' ('${name_vendor}') VALUES " +
                "( '${name}')"
        try{
            db.execSQL(query)
        } catch (e: Exception){
            return 1
        }
        return 0
    }


    fun insert_product(id: Int , title: String, description: String, price: Float, review: Int, rating: Float, image: Int): Int {
        val db = this.writableDatabase
        val query = "INSERT INTO '${table_product}' " +
                "('${id_product}' ," +
                " '${title_product}'," +
                "'${description_product}'," +
                "'${price_product}'," +
                "'${review_product}'," +
                "'${rating_product}'," +
                "'${image_product}') VALUES " +
                "( '${id}', '${title}', '${description}' , '${price}' , '${review}' , '${rating}' , '${image}')"
        try{
            db.execSQL(query)
        } catch (e: Exception){
            return 1
        }
        return 0
    }

    fun insert_user(email: String, name: String, surname: String, password: String, address: String, phone: String): Int{
        //if(!this.select(nemail).equals("")) return 1
        val db = this.writableDatabase
        val query = "INSERT INTO '${table_user}' ('${email_user}', '${name_user}','${surname_user}',  '${password_user}', '${address_user}', '${phone_user}')VALUES " +
                "('${email}', '${name}', '${surname}', '${password}', '${address}', '${phone}')"
        try{
            db.execSQL(query)
        } catch (e: Exception){
            return 1
        }
        return 0
    }

    fun select(nemail: String): User? {
        val db = this.readableDatabase
        val usr: User
        val cursor = db.rawQuery("SELECT * FROM '${table_user}' WHERE email == '${nemail}'", null)
        when(cursor.count){
            0 -> {
                cursor.close()
                return null
            }
            1 -> {
                cursor.moveToFirst()
                usr = User(cursor.getString(cursor.getColumnIndex(id_user)), cursor.getString(cursor.getColumnIndex(
                    email_user)), cursor.getString(cursor.getColumnIndex(
                    name_user)), cursor.getString(cursor.getColumnIndex(
                    surname_user)), cursor.getString(cursor.getColumnIndex(password_user)), cursor.getString(cursor.getColumnIndex(
                    address_user)), cursor.getString(cursor.getColumnIndex(
                    phone_user)))
            }
            else ->{
                cursor.close()
                return null
            }
        }
        cursor.close()
        return usr
    }

    fun update(uemail: String, uname: String, usurname: String, uaddress: String, uphone: String){
        val db = this.writableDatabase
        val query = "UPDATE '${table_user}' SET ${name_user} = '${uname}', ${surname_user} = '${usurname}', ${address_user} = '${uaddress}'," +
                " ${phone_user} = '${uphone}'  WHERE ${email_user} = '${uemail}';"
        db.execSQL(query)
    }

    fun remove(remail: String){
        val db = this.readableDatabase
        val query = "DELETE FROM '${table_user}' WHERE '${email_user}' = '${remail}'"
        db.execSQL(query)
    }

    companion object{
        private const val database_name = "database.db"
        private const val table_user = "users"
        private const val id_user = "id"
        private const val email_user = "email"
        private const val name_user = "name"
        private const val surname_user = "surname"
        private const val image_user = "image"
        private const val address_user = "address"
        private const val phone_user = "phone"
        private const val password_user = "password"

        private const val table_vendor = "vendor"
        private const val id_vendor = "id"
        private const val name_vendor = "name"

        private const val table_product = "product"
        private const val id_product = "id"
        private const val title_product = "title"
        private const val description_product = "description"
        private const val price_product = "price"
        private const val review_product = "review"
        private const val rating_product = "rating"
        private const val image_product = "image"


    }
}