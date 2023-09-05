package com.abhishek.moneyflowcrafter.screens.home

data class MonthDataModel(
    var month: String? = null,
    var creditAmount: Double = 0.0,
    var debitAmount: Double = 0.0,
    var loanTakenAmount: Double = 0.0,
    var loanGivenAmount: Double = 0.0
    )