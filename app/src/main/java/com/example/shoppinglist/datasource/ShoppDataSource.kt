package com.example.shoppinglist.datasource

import com.example.shoppinglist.model.Shopp

object ShoppDataSource {
    private val list = arrayListOf<Shopp>()

    fun getList() = list.toList()

    fun insertShopp(shopp: Shopp) {
        if(shopp.id ==0) {
            list.add(shopp.copy(id = list.size + 1))
        } else {
            list.remove(shopp)
            list.add(shopp)
        }
    }

    fun finById(shoppId: Int) = list.find {it.id == shoppId}

    fun deleteShopp(shopp: Shopp) {
        list.remove(shopp)
    }

}

