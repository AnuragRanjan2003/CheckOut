package com.example.checkout.viewModels

import android.util.Log.d
import android.util.Log.e
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

    private val filteredItemList: MutableLiveData<ArrayList<ItemModel>> by lazy { MutableLiveData<ArrayList<ItemModel>>() }
    private val selected = MutableLiveData(-1)
    private val selectedItems: MutableLiveData<MutableMap<ItemModel, Int>> by lazy { MutableLiveData<MutableMap<ItemModel, Int>>() }

    init {
        selectedItems.value = mutableMapOf()
    }


    fun getItemList() {
        if (itemList.isInitialized) return
        e("get Item list", "${itemList.value}")
        viewModelScope.launch(Dispatchers.IO) {
            val x = repo.getItemData()
            withContext(Dispatchers.Main) {
                itemList.value = x
                filteredItemList.value = x

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
        if (shopList.isInitialized) return
        e("get shop list", "${shopList.value}")
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

    fun getFilteredItemListLiveData(): LiveData<ArrayList<ItemModel>> {
        return filteredItemList
    }

    private fun filterItemList() {
        val pos = selected.value ?: -1
        if (pos == -1) {
            filteredItemList.value = itemList.value
            return
        }
        val str = shopList.value?.get(pos)?.name
        val list = itemList.value
        if (!str.isNullOrBlank())
            filteredItemList.value = list?.filter { it.shop == str }?.let { ArrayList(it) }
    }

    fun select(i: Int) {
        if (selected.value == i) {
            selected.value = -1
        } else {
            selected.value = i
        }
        filterItemList()
    }

    fun getSelected(): LiveData<Int> {
        return selected
    }

    fun removeItem(item: ItemModel) {
        var x = selectedItems.value
        if (x == null || x.isEmpty()) x = mutableMapOf()
        else {
            x.apply {
                remove(item)
            }
        }

        selectedItems.value = x


    }


    fun addItem(item: ItemModel) {
        var x = selectedItems.value
        if (x == null) x = mutableMapOf()
        x.apply {
            putIfAbsent(item, 1)
        }

        selectedItems.value = x

    }

    fun getSelectedItems(): LiveData<MutableMap<ItemModel, Int>> {
        return selectedItems
    }

    fun incrementAmount(item: ItemModel) {
        selectedItems.value!![item]?.apply {
            selectedItems.value!![item] = this + 1
            println("vm / incremented  ( ${item.name} , ${selectedItems.value!![item]} )")
        }
    }

    fun getTotal(): Int {
        d("vm/tot" , "total" )
        var tot = 0
        selectedItems.value!!.forEach { (item, amt) ->
            tot += item.price.toInt() * amt
        }
        return tot
    }


}