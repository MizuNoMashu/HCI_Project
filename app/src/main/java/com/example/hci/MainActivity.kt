package com.example.hci

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.hci.databinding.ActivityMainBinding

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
    }

}