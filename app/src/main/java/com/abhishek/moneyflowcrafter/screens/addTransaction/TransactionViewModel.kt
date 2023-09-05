package com.abhishek.moneyflowcrafter.screens.addTransaction

import com.abhishek.moneyflowcrafter.R
import com.abhishek.moneyflowcrafter.screens.addTransaction.model.TransactionDataModel
import com.abhishek.moneyflowcrafter.screens.addTransaction.model.TransactionType
import com.abhishek.moneyflowcrafter.screens.addTransaction.model.TransactionTypeModel
import com.abhishek.moneyflowcrafter.screens.addTransaction.repository.TransactionRepository
import com.abhishek.moneyflowcrafter.utils.ResultClass
import com.abhishek.moneyflowcrafter.utils.commonMethods.getFirstDateForMonthYear
import com.abhishek.moneyflowcrafter.utils.commonMethods.getLastDateForMonthYear
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class TransactionViewModel : ViewModel() {

    var selectedMonthYear: MutableLiveData<String?> = MutableLiveData()
    var selectedDate: MutableLiveData<Date> = MutableLiveData()

    var allTransactions: MutableLiveData<ResultClass?>? = null
        get() {
            if (field == null) {
                field = MutableLiveData()
            }
            return field
        }

    fun fetchAllTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            allTransactions!!.postValue(TransactionRepository.getAllTransactions())
        }
    }

    fun fetchTransactionsForThisMonthYear() {
        viewModelScope.launch(Dispatchers.IO) {
            selectedMonthYear.value?.let {
                val firstDate = getFirstDateForMonthYear(it)
                val lastDate = getLastDateForMonthYear(it)
                allTransactions!!.postValue(
                    TransactionRepository.getTransactionBetweenTimestamps(
                        firstDate.time,
                        lastDate.time
                    )
                )
            }
        }
    }

    fun deleteAllTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            TransactionRepository.deleteAllTransactions()
            fetchTransactionsForThisMonthYear()
        }
    }

    fun deleteThisTransactions(transactionModel: TransactionDataModel) {
        viewModelScope.launch(Dispatchers.IO) {
            TransactionRepository.deleteThisTransactions(transactionModel.id)
            fetchTransactionsForThisMonthYear()
        }
    }

    fun addTransaction(transaction: TransactionDataModel) {
        viewModelScope.launch(Dispatchers.IO) {
            TransactionRepository.addTransaction(transaction)
            fetchTransactionsForThisMonthYear()
        }
    }

    fun getTransactionTypes(): ArrayList<TransactionTypeModel> {
        val response = ArrayList<TransactionTypeModel>()

        response.add(TransactionTypeModel(TransactionType.DEBIT, true, R.color.red_300))
        response.add(TransactionTypeModel(TransactionType.CREDIT, false, R.color.green_300))
        response.add(TransactionTypeModel(TransactionType.LOAN_GIVEN, false, R.color.blue_300))
        response.add(TransactionTypeModel(TransactionType.LOAN_TAKEN, false, R.color.purple_300))

        return response
    }
}