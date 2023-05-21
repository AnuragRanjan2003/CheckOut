package com.example.checkout.repo.impl

import com.example.checkout.models.ItemModel
import com.example.checkout.models.ShopModel
import com.example.checkout.repo.intf.DataRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(private val storage: FirebaseFirestore) :
    DataRepository {

    override val getItemRef: CollectionReference
        get() = storage.collection("items")


    override val getShopRef: CollectionReference
        get() = storage.collection("shops")

    override suspend fun getItemData(): ArrayList<ItemModel> {
        val x = getItemRef.get().await().documents
        val list = ArrayList<ItemModel>()
        for (doc in x) {
            try {
                val item = doc.toObject(ItemModel::class.java)
                item?.apply { list.add(this) }

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

        return list
    }

    override suspend fun storeItem(item: ItemModel) {
        getItemRef.add(item).await()
    }

    override suspend fun storeShop(shop: ShopModel) {
        getShopRef.add(shop).await()
    }

    override suspend fun getShopsData(): ArrayList<ShopModel> {
        val x = getShopRef.get().await().documents
        val list = ArrayList<ShopModel>()

        for(doc in x){
            try{
                val item = doc.toObject(ShopModel::class.java)
                item?.apply { list.add(this) }
            }catch (e : Exception){
                e.printStackTrace()
            }
        }

        return list
    }

}