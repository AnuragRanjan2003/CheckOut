package com.example.checkout.ui.fragments

import android.os.Bundle
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.checkout.R
import com.example.checkout.databinding.FragmentRegisterBinding
import com.example.checkout.others.utils.Completion
import com.example.checkout.viewModels.RegisterFragViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterFragViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.etEmail.doAfterTextChanged { viewModel.setEmail(it.toString()) }
        binding.etPass.doAfterTextChanged { viewModel.setPass(it.toString()) }

        binding.btnRegister.setOnClickListener { viewModel.register(comp) }


        return binding.root
    }

    private val comp = object : Completion {
        override fun onComplete() {
            findNavController().navigate(R.id.itemsListFragment)
        }

        override fun onFail(name: String, msg: String) {
            e(name, msg)
        }
    }


}