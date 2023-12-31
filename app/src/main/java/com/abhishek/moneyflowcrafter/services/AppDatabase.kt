package com.abhishek.moneyflowcrafter.services

import com.abhishek.moneyflowcrafter.screens.addTransaction.repository.TransactionDao
import com.abhishek.moneyflowcrafter.screens.addTransaction.model.TransactionDataModel
import com.abhishek.moneyflowcrafter.utils.DATABASE_NAME
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TransactionDataModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var databaseInstance : AppDatabase? = null

        @Synchronized
        fun getInstance(context :  Context) : AppDatabase {
            if (databaseInstance == null) {
                databaseInstance = Room.databaseBuilder(context, AppDatabase::class.java,
                DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return databaseInstance!!
        }
    }

    abstract fun transactionDao() : TransactionDao
}