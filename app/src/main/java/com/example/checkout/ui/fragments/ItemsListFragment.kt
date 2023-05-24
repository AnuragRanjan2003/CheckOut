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
import com.example.checkout.ui.adapters.ItemsAdapter
import com.example.checkout.ui.adapters.ShopAdapter
import com.example.checkout.viewModels.ItemFragViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ItemsListFragment : Fragment() {
    private lateinit var binding: FragmentItemsListBinding

    // setting the owner as activity as fragment is completely destroyed on configuration change
    private val viewModel by viewModels<ItemFragViewModel>({ requireActivity() })
    private var pos = -1

    @Inject
    lateinit var itemAdapter: ItemsAdapter

    @Inject
    lateinit var shopAdapter: ShopAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemsListBinding.inflate(inflater, container, false)


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


        viewModel.getFilteredItemListLiveData().observe(viewLifecycleOwner) {
            e("filtered list", "$it")
            itemAdapter.updateList(it)
        }

        viewModel.getShopsListLiveData().observe(viewLifecycleOwner) {
            e("shops", "$it")
            shopAdapter.updateList(it)
        }

        viewModel.getSelected().observe(viewLifecycleOwner) {
            pos = it
            e("pos", "$pos")
        }


        shopAdapter.setOnItemClickListener {
            shopSelected(it)
        }

        return binding.root
    }

    private fun shopSelected(position: Int) {
        val shop = shopAdapter.list[position]
        e("shop", "${shop.name}")

        viewModel.select(position)
        if ( pos==-1 ) {
            shopAdapter.unselectAll()
        } else {
            shopAdapter.selectPos(pos)
        }

    }


}