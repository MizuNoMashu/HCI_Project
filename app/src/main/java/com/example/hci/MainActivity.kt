package com.example.hci

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.hci.databinding.ActivityMainBinding

@SuppressLint("StaticFieldLeak")
var ldb: DBHelper? = null

var logged_user: User? = null

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)



        mainBinding.bottomNavigation.setOnItemSelectedListener {   menuItem ->
            when (menuItem.itemId){
                R.id.home -> {
                    Navigation.findNavController(this,R.id.nav_host_fragment).navigate( R.id.scroll_products)

                }
                R.id.settings ->{
                    Navigation.findNavController(this , R.id.nav_host_fragment).navigate(R.id.settings)
                }

            }
            false
        }
        ldb = DBHelper(this)
        ldb?.insert("ciao@ok.oi", "Giacomino", "Prova", "ciaociao", "via prova 123", "33344455566")
    }

}