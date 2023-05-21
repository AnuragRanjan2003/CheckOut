package com.example.checkout.repo.intf

import com.example.checkout.models.ItemModel
import com.example.checkout.models.ShopModel
import com.google.firebase.firestore.CollectionReference

interface DataRepository {

    val getItemRef : CollectionReference

    val getShopRef : CollectionReference

    suspend fun getItemData() : ArrayList<ItemModel>

    suspend fun storeItem(item:ItemModel)

    suspend fun storeShop(shop : ShopModel)

    suspend fun getShopsData() : ArrayList<ShopModel>


}