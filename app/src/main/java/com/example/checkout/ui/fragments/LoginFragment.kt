package com.example.checkout.ui.fragments

import android.os.Bundle
import android.util.Log.e
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.checkout.R
import com.example.checkout.databinding.FragmentLoginBinding
import com.example.checkout.others.utils.Completion
import com.example.checkout.viewModels.LoginFragViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment  : Fragment() {
    private lateinit var binding : FragmentLoginBinding
    private val viewModel by viewModels<LoginFragViewModel>()
    private val fUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fUser?.apply {
            findNavController().navigate(R.id.itemsListFragment)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater,container,false)


        binding.etEmail.doAfterTextChanged { viewModel.setEmail(it.toString()) }
        binding.etPass.doAfterTextChanged { viewModel.setPass(it.toString()) }

        binding.btnLogin.setOnClickListener {
            viewModel.login(comp)
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }







        return binding.root
    }

    private val comp = object : Completion{
        override fun onComplete() {
            findNavController().navigate(R.id.itemsListFragment)
        }

        override fun onFail(name: String, msg: String) {
            e(name,msg)
        }
    }


}