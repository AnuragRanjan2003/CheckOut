package com.example.checkout.repo.intf

import com.example.checkout.models.ItemModel
import com.google.firebase.firestore.CollectionReference

interface DataRepository {

    val getItemRef : CollectionReference

    val getShopRef : CollectionReference

    suspend fun getItemData() : ArrayList<ItemModel>

    suspend fun getShopsData() : ArrayList<Any>


}