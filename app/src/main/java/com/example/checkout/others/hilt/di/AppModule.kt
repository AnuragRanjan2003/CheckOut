package com.example.checkout.others.hilt.di

import com.example.checkout.repo.impl.DataRepositoryImpl
import com.example.checkout.repo.impl.RepositoryImpl
import com.example.checkout.repo.intf.DataRepository
import com.example.checkout.repo.intf.Repository
import com.example.checkout.ui.adapters.BottomRecAdapter
import com.example.checkout.ui.adapters.ItemsAdapter
import com.example.checkout.ui.adapters.ShopAdapter
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
    fun provideRepo(impl: RepositoryImpl): Repository = impl

    @Provides
    fun provideDataRepo(impl: DataRepositoryImpl): DataRepository = impl

    @Singleton
    @Provides
    fun provideFireStore(): FirebaseFirestore = Firebase.firestore

    @Singleton
    @Provides
    fun provideFireBaseUser(): FirebaseUser? = FirebaseAuth.getInstance().currentUser

    @Provides
    fun provideItemAdapter(): ItemsAdapter = ItemsAdapter(ArrayList()) {}

    @Provides
    fun provideShopAdapter(): ShopAdapter = ShopAdapter(ArrayList()) {}

    @Provides
    fun provideBottomAdapter() : BottomRecAdapter = BottomRecAdapter(mutableMapOf())


}