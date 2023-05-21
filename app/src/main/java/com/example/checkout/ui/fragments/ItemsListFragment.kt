package com.example.checkout.ui.fragments

import android.os.Bundle
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.checkout.databinding.FragmentItemsListBinding
import com.example.checkout.models.ShopModel
import com.example.checkout.ui.adapters.ItemsAdapter
import com.example.checkout.ui.adapters.ShopAdapter
import com.example.checkout.viewModels.ItemFragViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemsListFragment : Fragment() {
    private lateinit var binding: FragmentItemsListBinding
    private val viewModel by viewModels<ItemFragViewModel>()
    private lateinit var itemAdapter: ItemsAdapter
    private lateinit var shopAdapter: ShopAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemsListBinding.inflate(inflater, container, false)

        itemAdapter = ItemsAdapter(ArrayList()) {}
        shopAdapter = ShopAdapter(ArrayList()) {}
        //viewModel.putData(ItemModel("burger", "food", "", "100"))
        /*viewModel.putShop(
            ShopModel(
                "Domino's",
                "Bokaro",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3e/Domino%27s_pizza_logo.svg/640px-Domino%27s_pizza_logo.svg.png"
            )
        )
        viewModel.putShop(
            ShopModel(
                "Burger King",
                "Dhanbad",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Burger_King_logo_%281999%29.svg/2024px-Burger_King_logo_%281999%29.svg.png"
            )
        )*/

        binding.menuRec.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.menuRec.hasFixedSize()
        binding.menuRec.adapter = itemAdapter

        binding.placeRec.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.placeRec.hasFixedSize()
        binding.placeRec.adapter = shopAdapter

        viewModel.getItemList()
        viewModel.getShopList()

        viewModel.getItemsListLiveData().observe(viewLifecycleOwner) {
            e("list", "$it")
            itemAdapter.updateList(it)
        }

        viewModel.getShopsListLiveData().observe(viewLifecycleOwner) {
            e("shops", "$it")
            shopAdapter.updateList(it)
        }

        return binding.root
    }


}