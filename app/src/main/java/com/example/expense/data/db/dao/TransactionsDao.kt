package com.example.expense.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.expense.data.db.dao.utilities.CategoryAndAmount
import com.example.expense.data.db.entities.Transactions
import kotlinx.coroutines.flow.Flow
import java.util.*


@Dao
interface TransactionsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(transaction: Transactions)

    @Update
    suspend fun updateTransaction(transaction: Transactions)

    @Delete
    suspend fun deleteSingleTransaction(transaction: Transactions)

    @Query("DELETE FROM transactions_table")
    suspend fun deleteAllTransactions()

    @Query("SELECT * FROM transactions_table ORDER BY date DESC")
    fun getAllTransactions(): LiveData<List<Transactions>>

    @Query("SELECT * FROM transactions_table WHERE category LIKE :searchQuery ORDER BY date DESC")
    fun searchTransaction(searchQuery: String): Flow<List<Transactions>>

    @Query("SELECT SUM(amount) FROM transactions_table WHERE date >= :startDate AND date <= :lastDate")
    fun getThisMonthAmount(startDate: Date, lastDate: Date): LiveData<Int>

    @Query("SELECT category AS category, SUM(amount) AS amount FROM transactions_table WHERE date >= :startDate AND date <= :lastDate GROUP BY category")
    fun getTransactionDataFromTo(startDate: Date, lastDate: Date): LiveData<List<CategoryAndAmount>>
}