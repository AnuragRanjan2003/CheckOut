package com.example.checkout.repo.impl

import com.example.checkout.others.utils.AuthUtils
import com.example.checkout.others.utils.CRED_OK
import com.example.checkout.repo.intf.Repository
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val mAuth: FirebaseAuth) : Repository {

    override val fUser: FirebaseUser?
        get() = mAuth.currentUser

    override suspend fun login(email: String?, password: String?): String? {
        val res = AuthUtils.verifyCredentialsLengths(email, password)
        return if (res != CRED_OK) {
            return AuthUtils.getMessage(res)
        } else {
            return try {
                mAuth.signInWithEmailAndPassword(email!!, password!!).await().user?.uid
            } catch (e: FirebaseException) {
                return e.message
            }

        }
    }

    override suspend fun register(email: String?, password: String?): String? {
        val res = AuthUtils.verifyCredentialsLengths(email, password)
        return if (res != CRED_OK) {
            return AuthUtils.getMessage(res)
        } else {
            return try {
                mAuth.createUserWithEmailAndPassword(email!!, password!!).await().user?.uid
            } catch (e: FirebaseException) {
                return e.message
            }

        }
    }

    override fun logOut() {
        mAuth.signOut()
    }
}