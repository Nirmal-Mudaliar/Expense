package com.example.expense.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.expense.data.db.ExpenseDataBase
import com.example.expense.data.db.dao.utilities.CategoryAndAmount
import com.example.expense.data.db.entities.Transactions
import com.example.expense.data.repositories.TransactionsRepository
import kotlinx.coroutines.launch
import java.util.*

class TransactionsViewModel(application: Application): AndroidViewModel(application) {
    val getAllTransactions: LiveData<List<Transactions>>
    private val transactionRepository: TransactionsRepository

    init {
        val transactionDao = ExpenseDataBase.getDatabase(application).getTransactionDao()
        transactionRepository = TransactionsRepository(transactionDao)
        getAllTransactions = transactionRepository.getAllTransactions
    }

    fun getAllTransaction(): LiveData<List<Transactions>> {
        return getAllTransactions
    }

    fun insertTransaction(transactions: Transactions) = viewModelScope.launch {
        transactionRepository.insertTransaction(transactions)
    }

    fun updateTransaction(transactions: Transactions) = viewModelScope.launch {
        transactionRepository.updateTransaction(transactions)
    }

    fun deleteSingleTransaction(transactions: Transactions) = viewModelScope.launch {
        transactionRepository.deleteSingleTransaction(transactions)
    }

    fun deleteAllTransactions() = viewModelScope.launch {
        transactionRepository.deleteAllTransaction()
    }

    fun searchTransaction(searchQuery: String): LiveData<List<Transactions>> {
        return transactionRepository.searchTransaction(searchQuery).asLiveData()
    }
    fun getThisMonthAmount(startDate: Date, lasDate: Date): LiveData<Int> {
        return transactionRepository.getThisMonthAmount(startDate, lasDate)
    }

    fun getTransactionDataFromTo(startDate: Date, lasDate: Date): LiveData<List<CategoryAndAmount>> {
        return transactionRepository.getTransactionDataFromTo(startDate, lasDate)
    }
}