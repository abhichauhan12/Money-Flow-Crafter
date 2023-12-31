package com.abhishek.moneyflowcrafter.screens.addTransaction.repository

import com.abhishek.moneyflowcrafter.screens.addTransaction.model.TransactionDataModel
import com.abhishek.moneyflowcrafter.utils.TABLE_TRANSACTION
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TransactionDao {

    @Query("select * from `$TABLE_TRANSACTION`")
    fun getAllTransactions() : List<TransactionDataModel>

    @Query("select * from `$TABLE_TRANSACTION` where date between :fromTimestamp and :toTimestamp")
    fun getTransactionsBetweenTimestamp(fromTimestamp: Long, toTimestamp: Long) : List<TransactionDataModel>

    @Insert
    fun addTransaction(transactionDataModel: TransactionDataModel)

    @Query("delete from `$TABLE_TRANSACTION`")
    fun deleteAllTransactions()

    @Query("delete from `$TABLE_TRANSACTION` where id = :transactionId")
    fun deleteTransactionById(transactionId: Int)
}