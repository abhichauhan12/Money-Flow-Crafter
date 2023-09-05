package com.abhishek.moneyflowcrafter.screens.addTransaction.model

import com.abhishek.moneyflowcrafter.R

data class TransactionTypeModel(
    var type: TransactionType = TransactionType.DEBIT,
    var isSelected: Boolean = false,
    var selectedColor: Int = R.color.red_400
)
