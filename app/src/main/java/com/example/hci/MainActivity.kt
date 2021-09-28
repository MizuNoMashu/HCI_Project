package com.example.hci

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.hci.databinding.ActivityMainBinding
import com.example.hci.model.User

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
                R.id.cart ->{
                    Navigation.findNavController(this , R.id.nav_host_fragment).navigate(R.id.cartFragment)
                }
                R.id.settings ->{
                    Navigation.findNavController(this , R.id.nav_host_fragment).navigate(R.id.settings)
                }

            }
            false
        }
        ldb = DBHelper(this)
        ldb?.insert_user("ciao@ok.oi", "Giacomino", "Prova", "ciaociao", "via prova 123", "33344455566")
        ldb?.insert_vendor("apple")
        ldb?.insert_vendor("xiaomi")
        ldb?.insert_product(1 , "pisello marrone con rivestimento di cioccolato al caramello",
            "un oggetto bellissimo" , 4000F,10000, 5F,  R.drawable.image1)
        ldb?.insert_product(1,"pisello marrone con rivestimento di cioccolato al caramello e pistacchio panna briciole adsalskalskalskalskalkslask",
            "un oggetto poco bello" , 400F, 10000 , 5F, R.drawable.image1)
        ldb?.insert_product(2,"pisello marrone con rivestimento di cioccolato al caramello e pistacchio panna briciole adsalskalskalskalskalkslask",
            "un oggetto poco bello" , 80F, 1120 , 3.4F, R.drawable.image1)

    }

}