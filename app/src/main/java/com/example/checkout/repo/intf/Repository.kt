package com.example.checkout.repo.intf

import com.example.checkout.others.utils.Resource
import com.google.firebase.auth.FirebaseUser

interface Repository {


    val fUser : FirebaseUser?

    suspend fun login(email : String? , password: String?) : Resource<FirebaseUser>

    suspend fun register(email: String? , password: String?) : Resource<FirebaseUser>

    fun logOut()


}