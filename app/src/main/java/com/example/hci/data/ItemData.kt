package com.example.hci.data

import com.example.hci.R
import com.example.hci.model.Item

class ItemData {
    fun loadItem(): List<Item> {
        return listOf<Item>(
                Item(
                        R.drawable.image1,
                        5F,
                        10000,
                        "pisello marrone con rivestimento di cioccolato al caramello",
                        4000
                ),
                Item(
                        R.drawable.image1,
                        5F,
                        10000,
                        "pisello marrone con rivestimento di cioccolato al caramello e pistacchio panna briciole adsalskalskalskalskalkslask",
                        4000
                ),
                Item(
                        R.drawable.image1,
                        3.4F,
                        10000,
                        "pisello marrone con rivestimento di cioccolato al caramello",
                        4000
                ),
                Item(
                        R.drawable.image1,
                        5F,
                        10000,
                        "pisello marrone con rivestimento di cioccolato al caramello",
                        4000
                ),
                Item(
                        R.drawable.image1,
                        4F,
                        10000,
                        "pisello marrone con rivestimento di cioccolato al caramello",
                        4000
                )
        )
    }
}