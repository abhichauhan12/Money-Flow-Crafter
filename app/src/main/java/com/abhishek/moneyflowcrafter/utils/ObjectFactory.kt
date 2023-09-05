package com.abhishek.moneyflowcrafter.utils

import com.abhishek.moneyflowcrafter.services.AppDatabase
import androidx.lifecycle.MutableLiveData

object ObjectFactory {

    val appDatabaseInstance: AppDatabase by lazy {
        AppDatabase.getInstance(MyApp.getApplicationContext())
    }

    val globalRefreshMutableLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }

    val userNameChangeMutableLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }
}