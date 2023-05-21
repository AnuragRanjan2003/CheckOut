package com.example.checkout.others.utils

object AuthUtils {

    fun verifyCredentialsLengths(email: String?, password: String?): Int {
        if (email.isNullOrBlank()) return EMAIL_EMPTY
        if (password.isNullOrBlank()) return PASS_EMPTY
        if (!email.contains('@')) return EMAIL_INVALID
        return if (password.length < 6) PASS_SHORT
        else CRED_OK
    }

    fun getMessage(x : Int): String {
        return when(x){
            EMAIL_EMPTY-> MSG_EMAIL_EMPTY
            PASS_EMPTY-> MSG_PASS_EMPTY
            EMAIL_INVALID-> MSG_EMAIL_INVALID
            PASS_SHORT-> MSG_PASS_SHORT
            else->""
        }
    }

    fun isUid(s: String?):Boolean{
        if(s.isNullOrBlank()) return false
        if(s.contains(" ") || s.contains(".")) return false
        return when(s){
            MSG_EMAIL_EMPTY-> false
            MSG_PASS_EMPTY-> false
            MSG_EMAIL_INVALID-> false
            MSG_PASS_SHORT-> false
            else->true
        }
    }
}