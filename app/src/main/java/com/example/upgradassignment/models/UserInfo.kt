package com.example.upgradassignment.models

import com.google.gson.annotations.Expose


class UserInfo {


    @Expose
    var items: List<Items> = ArrayList<Items>()
        private set


    fun setItems(Items: ArrayList<Items>) {
        this.items = Items
    }


}