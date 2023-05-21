package com.example.checkout.repo.impl

import com.example.checkout.models.ItemModel
import com.example.checkout.repo.intf.DataRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(private val storage : FirebaseFirestore) : DataRepository {

    override val getItemRef: CollectionReference
        get() = storage.collection("items")


    override val getShopRef: CollectionReference
        get() = storage.collection("shops")

    override suspend fun getItemData(): ArrayList<ItemModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getShopsData(): ArrayList<Any> {
        TODO("Not yet implemented")
    }

}