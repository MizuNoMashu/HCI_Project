package com.example.hci.model

class Order(ouid: Int, ovid: Int, optitle: String, oImage: Int , oquantity: Int,otime: String) {
    val uid = ouid
    val vid = ovid
    val ptitle = optitle
    val pimage = oImage
    val pquantity = oquantity
    val time = otime
    override fun toString(): String {
        return "Order(uid=$uid, vid=$vid, ptitle='$ptitle', pimage=$pimage, pquantity=$pquantity,time = $time )"
    }

}

