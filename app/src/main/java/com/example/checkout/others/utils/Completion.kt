package com.example.checkout.others.utils

interface Completion {

    fun onComplete()

    fun onFail(name: String,msg: String)

}
