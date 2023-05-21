package com.example.checkout.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.checkout.models.ItemModel
import com.example.checkout.models.ShopModel
import com.example.checkout.repo.intf.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ItemFragViewModel @Inject constructor(private val repo: DataRepository) : ViewModel() {
    private val itemList: MutableLiveData<ArrayList<ItemModel>> by lazy { MutableLiveData<ArrayList<ItemModel>>() }
    private val shopList: MutableLiveData<ArrayList<ShopModel>> by lazy { MutableLiveData<ArrayList<ShopModel>>() }

    fun getItemList() {
        viewModelScope.launch(Dispatchers.IO) {
            val x = repo.getItemData()
            withContext(Dispatchers.Main) {
                itemList.value = x
            }
        }
    }

    fun putData(item: ItemModel) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repo.storeItem(item)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun putShop(shop: ShopModel) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repo.storeShop(shop)
            }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun getShopList() {
        viewModelScope.launch(Dispatchers.IO) {
            val x = repo.getShopsData()
            withContext(Dispatchers.Main) {
                shopList.value = x
            }
        }
    }


    fun getItemsListLiveData(): LiveData<ArrayList<ItemModel>> {
        return itemList
    }

    fun getShopsListLiveData(): LiveData<ArrayList<ShopModel>> {
        return shopList
    }

}