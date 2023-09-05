package com.abhishek.moneyflowcrafter.services

import com.abhishek.moneyflowcrafter.utils.MyApp
import com.abhishek.moneyflowcrafter.utils.ObjectFactory
import android.content.Context.MODE_PRIVATE

import android.content.SharedPreferences

object SharedPreferenceHelper {
    private var sharedPreferences: SharedPreferences? = null

    init {
        sharedPreferences =
            MyApp.getApplicationContext().getSharedPreferences("Crafter", MODE_PRIVATE)
    }

    fun getUserName(): String {
        return sharedPreferences?.getString(USER_NAME, "Crafter") ?: "Crafter"
    }

    fun setUserName(userName: String) {
        sharedPreferences?.apply {
            val editor = edit()
            editor.putString(USER_NAME, userName)
            editor.apply()
        }
        ObjectFactory.userNameChangeMutableLiveData.value = true
    }

    private val USER_NAME = "userName"
    private val READ_SMS = "readSms"
}