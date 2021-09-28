package com.example.hci.model

class Product (pid: Int , ptitle: String , pdescription: String , pprice: Float, ppreview: Int, pprating: Float, pimage: Int){
    val id = pid
    val title= ptitle
    val description = pdescription
    val price = pprice
    val review = ppreview
    val rating = pprating
    val image = pimage

    override fun toString(): String {
        return "Product(id=$id, title='$title', description='$description', price=$price, review=$review, rating=$rating, image=$image)"
    }

}

