package com.example.checkout.repo.impl

import com.example.checkout.others.utils.AuthUtils
import com.example.checkout.others.utils.CRED_OK
import com.example.checkout.others.utils.Resource
import com.example.checkout.repo.intf.Repository
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val mAuth: FirebaseAuth) : Repository {

    override val fUser: FirebaseUser?
        get() = mAuth.currentUser

    override suspend fun login(email: String?, password: String?): Resource<FirebaseUser> {
        val res = AuthUtils.verifyCredentialsLengths(email, password)
        if (res != CRED_OK) return Resource.Failure(AuthUtils.getMessage(res)) else return try {
            val x = mAuth.signInWithEmailAndPassword(email!!, password!!).await().user
            if (x != null) return Resource.Success<FirebaseUser>(x)
            else return Resource.Failure("no user found")

        } catch (e: FirebaseException) {
            return Resource.Failure<FirebaseUser>(e.message)
        }
    }

    override suspend fun register(email: String?, password: String?): Resource<FirebaseUser> {
        val res = AuthUtils.verifyCredentialsLengths(email, password)
        return if (res != CRED_OK) {
            return Resource.Failure(AuthUtils.getMessage(res))
        } else {
            return try {
                val x = mAuth.createUserWithEmailAndPassword(email!!, password!!).await().user
                if(x!=null) return Resource.Success(x)
                else return Resource.Failure("could not create")
            } catch (e: FirebaseException) {
                return Resource.Failure(e.message)
            }

        }
    }

    override fun logOut() {
        mAuth.signOut()
    }
}