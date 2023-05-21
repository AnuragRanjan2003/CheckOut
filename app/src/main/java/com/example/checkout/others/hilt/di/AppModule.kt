package com.example.checkout.others.hilt.di

import com.example.checkout.models.ItemModel
import com.example.checkout.repo.impl.RepositoryImpl
import com.example.checkout.repo.intf.Repository
import com.example.checkout.viewModels.ItemFragViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideRepo(impl : RepositoryImpl) : Repository = impl

    @Singleton
    @Provides
    fun provideFireStore(): FirebaseFirestore = Firebase.firestore

    @Singleton
    @Provides
    fun provideFireBaseUser(): FirebaseUser? = FirebaseAuth.getInstance().currentUser

    @Provides
    fun provideItemsList(vm : ItemFragViewModel): ArrayList<ItemModel> = TODO("get the list")
}