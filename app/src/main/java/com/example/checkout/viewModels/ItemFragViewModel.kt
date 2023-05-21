package com.example.checkout.viewModels

import androidx.lifecycle.MutableLiveData
import com.example.checkout.models.ItemModel
import com.example.checkout.repo.intf.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ItemFragViewModel @Inject constructor(private val repo : Repository) {
    private val list : MutableLiveData<ArrayList<ItemModel>> by lazy { MutableLiveData<ArrayList<ItemModel>>() }

}