package com.example.hci

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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
        mainBinding.bottomNavigation.visibility = View.GONE
        mainBinding.topAppBar.menu?.findItem(R.id.search)?.isVisible = false
        setContentView(mainBinding.root)

        ldb = DBHelper(this)

        ldb?.insert_user("ciao@ok.oi", "Giacomo", "Rossi", "prova", "viale della liberazione 123", "33344455566")
        ldb?.insert_vendor("apple", "Sells electronics devices", "4.7/5", R.drawable.apple_logo)
        ldb?.insert_vendor("Facebook", "Social network", "4.1/5", R.drawable.facebook_logo)
        ldb?.insert_vendor("xiaomi", "Sells electronics devices", "4.5/5", R.drawable.xiaomi_logo)
        ldb?.insert_product(1 , "Ipad",
            "A nice Ipad" , 400F,100, 3F,  R.drawable.ipad)
        ldb?.insert_product(1 , "Airpods",
            "Beautiful headphones." , 290F,1000, 4.5F,  R.drawable.airpods)
        ldb?.insert_product(1 , "MacBook",
            "A nice laptop" , 1500F,50, 5F,  R.drawable.macbook)
        ldb?.insert_product(3,"Electric Scooter",
            "A powerful electric scooter" , 4000F, 1000 , 5F, R.drawable.monopattino)
        ldb?.insert_product(3,"Mi11",
            "A powerful phone" , 600F, 200 , 4.5F, R.drawable.mi11)


        
        mainBinding.bottomNavigation.setOnItemSelectedListener {   menuItem ->
            when (menuItem.itemId){
                R.id.home -> {
                    menuItem.isChecked = true
                    Navigation.findNavController(this,R.id.nav_host_fragment).navigate( R.id.scroll_products)
                }
                R.id.cart ->{
                    menuItem.isChecked = true
                    Navigation.findNavController(this , R.id.nav_host_fragment).navigate(R.id.cartFragment)
                }
                R.id.settings ->{
                    menuItem.isChecked = true
                    Navigation.findNavController(this , R.id.nav_host_fragment).navigate(R.id.settings)
                }
            }
            false
        }

        mainBinding.topAppBar.setNavigationOnClickListener {
            onBackPressed();
        }

        val searchItem = mainBinding.topAppBar.menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setIconifiedByDefault(false)
        searchView.queryHint = "Search a product"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(query: String): Boolean{
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean{
                val bundle = Bundle()
                bundle.putString("query" , query)
                bundle.putInt("filter", 0)
                //Navigation.createNavigateOnClickListener(R.id.searchFragment,bundle)
                Navigation.findNavController(this@MainActivity , R.id.nav_host_fragment).navigate(R.id.searchFragment,bundle)
                return true
            }
        })



    }

}