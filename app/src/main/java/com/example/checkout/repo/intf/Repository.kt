package com.example.checkout.repo.intf

import com.google.firebase.auth.FirebaseUser

interface Repository {


    val fUser : FirebaseUser?

    suspend fun login(email : String? , password: String?) : String?

    suspend fun register(email: String? , password: String?) : String?

    fun logOut()


}