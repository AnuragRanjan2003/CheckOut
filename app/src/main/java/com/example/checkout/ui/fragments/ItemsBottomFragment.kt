package com.example.checkout.ui.fragments

import android.os.Bundle
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.checkout.databinding.FragmentItemsBottomBinding
import com.example.checkout.ui.adapters.BottomRecAdapter
import com.example.checkout.viewModels.ItemFragViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ItemsBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentItemsBottomBinding
    private val viewModel by viewModels<ItemFragViewModel>({ requireActivity() })

    @Inject
    lateinit var bottomAdapter: BottomRecAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemsBottomBinding.inflate(inflater, container, false)
        binding.btmRec.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.btmRec.hasFixedSize()
        binding.btmRec.adapter = bottomAdapter

        bottomAdapter.onChangeListener = {
            viewModel.incrementAmount(it)
            binding.total.text  = viewModel.getTotal().toString()
        }

        bottomAdapter.onRemoveListener = { it ->
            viewModel.removeItem(it)
            binding.total.text = bottomAdapter.total.toString()
        }



        viewModel.getSelectedItems().observe(viewLifecycleOwner) {

            e("bottom_frag/map", "$it")

            bottomAdapter.updateMap(it)


            e("bottom_total", "${bottomAdapter.total}")
            binding.total.text = bottomAdapter.total.toString()


        }



        return binding.root
    }


}