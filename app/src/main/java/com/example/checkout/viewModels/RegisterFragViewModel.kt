package com.example.checkout.viewModels
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.checkout.others.utils.AuthUtils
import com.example.checkout.others.utils.Completion
import com.example.checkout.repo.intf.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class RegisterFragViewModel @Inject constructor(private val repo : Repository) : ViewModel() {

    private val email  = MutableLiveData<String>("")
    private val pass = MutableLiveData<String>("")

    fun setEmail(s: String){
        email.value = s
    }

    fun setPass(s: String){
        pass.value = s
    }

    fun register(comp: Completion){
        viewModelScope.launch(Dispatchers.IO) {
            val x = repo.register(email.value,pass.value)
            Log.e("repo_x", x.toString())
            withContext(Dispatchers.Main){
                if(AuthUtils.isUid(x)){
                    comp.onComplete()
                }else{
                    comp.onFail("register",x.toString())
                }

            }
        }
    }

}