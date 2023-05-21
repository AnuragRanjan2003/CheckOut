package com.example.checkout.others.utils

sealed class Resource<T>(val data: T?, val msg: String?) {
    class Success<T>(data: T?) : Resource<T>(data, null)
    class Failure<T>(msg: String?) : Resource<T>(null, msg)
}
