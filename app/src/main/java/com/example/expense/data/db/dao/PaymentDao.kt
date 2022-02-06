package com.example.expense.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.expense.data.db.entities.Payment
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {
    @Query("SELECT * FROM payment_table")
    fun getAllPayment(): LiveData<List<Payment>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPayment(payment: Payment)

    @Query("SELECT payment from payment_table")
    fun getOnlyPaymentName(): LiveData<List<String>>

    @Update
    suspend fun updatePayment(payment: Payment)

    @Delete
    suspend fun deleteSinglePayment(payment: Payment)

    @Query("DELETE FROM payment_table")
    suspend fun deleteAllPayment()

    @Query("SELECT * FROM payment_table WHERE payment LIKE :searchQuery")
    fun searchPayment(searchQuery: String): Flow<List<Payment>>



}