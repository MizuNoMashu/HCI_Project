package com.example.hci.model

class Cart(cuid: Int , cvid: Int , cptitle: String , cpprice: Float, cpimage: Int , cquantity: Int) {
    val uid = cuid
    val vid = cvid
    val ptitle = cptitle
    val pprice = cpprice
    val pimage = cpimage
    val quantity = cquantity

    override fun toString(): String {
        return "Cart(uid=$uid, vid=$vid, ptitle='$ptitle', pprice=$pprice, pimage=$pimage, quantity=$quantity)"
    }
}


