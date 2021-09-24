package com.example.hci

class User(nid: String, nemail: String, nname: String, nsurname: String, npassword: String, naddress: String, nphone: String) {
    val id  = nid
    val email = nemail
    val name = nname
    val surname = nsurname
    val password = npassword
    val address = naddress
    val phone = nphone

    override fun toString(): String {
        return "User(id='$id', email='$email', name='$name', surname='$surname', password='$password', address='$address', phone='$phone')"
    }
}