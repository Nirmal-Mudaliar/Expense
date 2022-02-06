package com.example.expense.data.repositories

import androidx.lifecycle.LiveData
import com.example.expense.data.db.dao.TransactionsDao
import com.example.expense.data.db.dao.utilities.CategoryAndAmount
import com.example.expense.data.db.entities.Transactions
import kotlinx.coroutines.flow.Flow
import java.util.*

class TransactionsRepository(private val transactionDao: TransactionsDao) {
    val getAllTransactions: LiveData<List<Transactions>> = transactionDao.getAllTransactions()

    suspend fun insertTransaction(transactions: Transactions) {
        transactionDao.insertTransaction(transactions)
    }

    suspend fun deleteAllTransaction() {
        transactionDao.deleteAllTransactions()
    }

    suspend fun deleteSingleTransaction(transactions: Transactions) {
        transactionDao.deleteSingleTransaction(transactions)
    }

    suspend fun updateTransaction(transactions: Transactions) {
        transactionDao.updateTransaction(transactions)
    }
    fun searchTransaction(searchQuery: String): Flow<List<Transactions>> {
        return transactionDao.searchTransaction(searchQuery)
    }
    fun getThisMonthAmount(startDate: Date, lastDate: Date): LiveData<Int> {
        return transactionDao.getThisMonthAmount(startDate, lastDate)
    }

    fun getTransactionDataFromTo(startDate: Date, lastDate: Date): LiveData<List<CategoryAndAmount>> {
        return transactionDao.getTransactionDataFromTo(startDate, lastDate)
    }

}