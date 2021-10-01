package com.example.hci.model

class User(nid: Int, nemail: String, nname: String, nsurname: String, npassword: String, naddress: String, nphone: String) {
    val id  = nid
    val email = nemail
    val name = nname
    val surname = nsurname
    val password = npassword
    val address = naddress
    val phone = nphone
    val image = null

    override fun toString(): String {
        return "User(id='$id', email='$email', name='$name', surname='$surname', password='$password', address='$address', phone='$phone')"
    }
}