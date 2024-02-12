package com.android.orderapp.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseProvider {

    @Provides
    fun ProvideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}