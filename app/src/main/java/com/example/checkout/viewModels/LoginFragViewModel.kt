package com.example.checkout.viewModels

import android.util.Log.e
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.checkout.others.utils.Completion
import com.example.checkout.others.utils.Resource
import com.example.checkout.repo.intf.Repository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class LoginFragViewModel @Inject constructor(private val repo: Repository) : ViewModel() {

    private val fUser: MutableLiveData<FirebaseUser> by lazy { MutableLiveData<FirebaseUser>() }
    private val pass = MutableLiveData<String>("")
    private val email = MutableLiveData<String>("")


    fun setEmail(s: String) {
        email.value = s
    }

    fun setPass(s: String) {
        pass.value = s
    }

    fun login(comp: Completion) {
        viewModelScope.launch(Dispatchers.IO) {
            val x = repo.login(email.value, pass.value)
            e("repo_x", x.toString())
            if (x is Resource.Success)
                withContext(Dispatchers.Main) {
                    comp.onComplete()
                }
            else comp.onFail("login", x.msg.toString())

        }
    }
}