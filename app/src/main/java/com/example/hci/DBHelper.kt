package com.example.hci

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.hci.model.Cart
import com.example.hci.model.Product
import com.example.hci.model.User

class DBHelper(var context: Context): SQLiteOpenHelper(context, database_name, null, 1) {

    override fun onConfigure(db: SQLiteDatabase?) {
        if (db != null) {
            db.setForeignKeyConstraintsEnabled(true)
        }
        super.onConfigure(db)
    }
    override fun onCreate(db: SQLiteDatabase){
        var create = "CREATE TABLE IF NOT EXISTS '${table_user}' ("+
                "'${id_user}' INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "'${email_user}' TEXT NOT NULL,"+
                "'${name_user}' TEXT NOT NULL,"+
                "'${surname_user}' TEXT NOT NULL," +
                "'${password_user}' TEXT NOT NULL," +
                "'${address_user}' TEXT NOT NULL," +
                "'${phone_user}' TEXT NOT NULL," +
                "'${image_user}' TEXT," +
                "UNIQUE (${email_user}))"

        //db.execSQL("DROP TABLE IF EXISTS '${table_user}'" )
        db.execSQL(create)

        create = "CREATE TABLE IF NOT EXISTS '${table_vendor}'(" +
                "'${id_vendor}' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'${name_vendor}' TEXT NOT NULL," +
                "UNIQUE (${name_vendor}))"

        db.execSQL(create)

        create = "CREATE TABLE IF NOT EXISTS '${table_product}'(" +
                "'${vid_product}' INTEGER NOT NULL,"+
                "'${title_product}' TEXT NOT NULL," +
                "'${description_product}' TEXT NOT NULL,"+
                "'${price_product}' FLOAT NOT NULL," +
                "'${review_product}' INTEGER NOT NULL," +
                "'${rating_product}' FLOAT NOT NULL," +
                "'${image_product}' INTEGER NOT NULL," +
                "FOREIGN KEY (${vid_product}) REFERENCES '${table_vendor}' (${id_vendor})," +
                "PRIMARY KEY (${vid_product},${title_product}))"

        db.execSQL(create)

        create = "CREATE TABLE IF NOT EXISTS '${table_cart}'(" +
                "'${uid_cart}' INTEGER NOT NULL," +
                "'${vid_cart}' INTEGER NOT NULL," +
                "'${ptitle_cart}' TEXT NOT NULL," +
                "'${pprice_cart}' FLOAT NOT NULL," +
                "'${pimage_cart}' INTEGER NOT NULL," +
                "'${quantity_cart}' INTEGER NOT NULL," +
                "FOREIGN KEY (${uid_cart}) REFERENCES '${table_user}' (${id_user})," +
                "FOREIGN KEY (${vid_cart},${ptitle_cart}) REFERENCES '${table_product}' (${vid_product},${title_product}))"

        db.execSQL(create)

        create = "CREATE TABLE IF NOT EXISTS '${table_message}'(" +
                "'${id_message}' INTEGER NOT NULL,"+
                "'${vid_message}' TEXT NOT NULL,"+
                "'${uid_message}' TEXT NOT NULL,"+
                "'${message}' TEXT NOT NULL,"+
                "FOREIGN KEY (${vid_message}) REFERENCES '${table_vendor}' (${name_vendor}),"+
                "FOREIGN KEY (${uid_message}) REFERENCES '${table_user}' (${name_user}),"+
                "PRIMARY KEY (${id_message} ,${vid_message},${uid_message}))"
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


    fun add_to_cart(uid: Int , vid: Int , ptitle: String , pprice: Float, pimage: Int, quantity: Int): Int{
        val db = this.writableDatabase
        val query = "INSERT INTO '${table_cart}' ('${uid_cart}', '${vid_cart}', '${ptitle_cart}', '${pprice_cart}'," +
                "'${pimage_cart}', '${quantity_cart}') VALUES " +
                "( '${uid}', '${vid}', '${ptitle}', '${pprice}', '${pimage}', '${quantity}')"
        try{
            db.execSQL(query)
        } catch (e: Exception){
            return 1
        }
        return 0
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

    fun insert_message(message_id :Int ,message_vid: String ,message_uid:String ,messages: String,): Int {
        val db = this.writableDatabase
        val query = "INSERT INTO '${table_message}' ('${id_message}', '${vid_message}','${uid_message}',  '${message}')VALUES " +
                "('${message_id}', '${message_vid}', '${message_uid}', '${messages}')"
        try{
            db.execSQL(query)
        } catch (e: Exception){
            return 1
        }
        return 0
    }
    fun load_message(message_vid: String ,message_uid:String ): ArrayList<String> {
        val db = this.readableDatabase
        val messageList = ArrayList<String>()
        val cursor = db.rawQuery(
            "SELECT * FROM '${table_message}' WHERE vid == '${message_vid}' AND uid == '${message_uid}' ORDER BY '${id_message}'",
            null
        )
        while(cursor.moveToNext()){
            val messages = cursor.getString(3)
            messageList.add(messages)
        }

        return messageList
    }



    fun insert_product(vid: Int , title: String, description: String, price: Float, review: Int, rating: Float, image: Int): Int {
        val db = this.writableDatabase
        val query = "INSERT INTO '${table_product}' " +
                "('${vid_product}' ," +
                " '${title_product}'," +
                "'${description_product}'," +
                "'${price_product}'," +
                "'${review_product}'," +
                "'${rating_product}'," +
                "'${image_product}') VALUES " +
                "( '${vid}', '${title}', '${description}' , '${price}' , '${review}' , '${rating}' , '${image}')"
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

    fun select_product(): MutableList<Product> {
        val db = this.readableDatabase
        val prodList: MutableList<Product> = ArrayList()
        val cursor = db.rawQuery("SELECT * FROM '${table_product}' ORDER BY '${review_product}' DESC,'${rating_product}' DESC LIMIT 10" , null)
        var prod : Product
        while(cursor.moveToNext()){
            prod = Product(cursor.getInt(0),cursor.getString(1),cursor.getString(2)
                ,cursor.getFloat(3),cursor.getInt(4),cursor.getFloat(5),cursor.getInt(6))
            prodList.add(prod)
        }
        cursor.close()
        return prodList
    }


    fun select_from_cart(uid: Int): MutableList<Cart> {
        val db = this.readableDatabase
        val cartList: MutableList<Cart> = ArrayList()
        val cursor = db.rawQuery("SELECT * FROM '${table_cart}' WHERE $uid_cart == '${uid}'" , null)
        var prod : Cart
        while(cursor.moveToNext()){
            prod = Cart(cursor.getInt(0),cursor.getInt(1),cursor.getString(2)
                ,cursor.getFloat(3),cursor.getInt(4),cursor.getInt(5))
            cartList.add(prod)
        }
        cursor.close()
        return cartList
    }

    fun select_vendor(vid: Int): String {
        val db = this.readableDatabase
        val vendor_name: String
        val cursor = db.rawQuery("SELECT * FROM '${table_vendor}' WHERE $id_vendor == '${vid}'" , null)

        cursor.moveToFirst()
        vendor_name = cursor.getString(1)
        cursor.close()
        return vendor_name


    }

    fun select_user(nemail: String): User? {
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
                usr = User(cursor.getInt(cursor.getColumnIndex(id_user)), cursor.getString(cursor.getColumnIndex(
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

    fun getImage(nemail: String): String? {
        val db = this.readableDatabase
        val ret: String?
        val cursor = db.rawQuery("SELECT ${image_user} FROM '${table_user}' WHERE email == '${nemail}'", null)
        when(cursor.count){
            0 -> {
                cursor.close()
                return null
            }
            1 -> {
                cursor.moveToFirst()
                ret = cursor.getString(cursor.getColumnIndex(image_user))
            }
            else ->{
                cursor.close()
                return null
            }
        }
        cursor.close()
        return ret
    }

    fun update(uemail: String, uname: String, usurname: String, uaddress: String, uphone: String){
        val db = this.writableDatabase
        val query = "UPDATE '${table_user}' SET ${name_user} = '${uname}', ${surname_user} = '${usurname}', ${address_user} = '${uaddress}'," +
                " ${phone_user} = '${uphone}'  WHERE ${email_user} = '${uemail}';"
        db.execSQL(query)
    }

    fun updateImage(uemail: String, image: String){
        val db = this.writableDatabase
        val query = "UPDATE '${table_user}' SET ${image_user} = '${image}' WHERE ${email_user} = '${uemail}';"
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
        private const val vid_product = "id"
        private const val title_product = "title"
        private const val description_product = "description"
        private const val price_product = "price"
        private const val review_product = "review"
        private const val rating_product = "rating"
        private const val image_product = "image"

        private const val table_cart = "cart"
        private const val uid_cart = "uid"
        private const val vid_cart = "vid"
        private const val ptitle_cart = "title"
        private const val pprice_cart = "price"
        private const val pimage_cart = "image"
        private const val quantity_cart = "quantity"

        private const val table_message = "message"
        private const val id_message = "id"
        private const val vid_message = "vid"
        private const val uid_message = "uid"
        private const val message = "messages"
    }
}