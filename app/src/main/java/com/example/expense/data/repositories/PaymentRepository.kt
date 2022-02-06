package com.example.expense.data.repositories

import androidx.lifecycle.LiveData
import com.example.expense.data.db.dao.PaymentDao
import com.example.expense.data.db.entities.Payment
import kotlinx.coroutines.flow.Flow

class PaymentRepository(private val paymentDao: PaymentDao) {

    val getAllPayment: LiveData<List<Payment>> = paymentDao.getAllPayment()

    suspend fun insertPayment(payment: Payment) {
        paymentDao.insertPayment(payment)
    }

    fun getOnlyPaymentName(): LiveData<List<String>> {
        return paymentDao.getOnlyPaymentName()
    }

    suspend fun updatePayment(payment: Payment) {
        paymentDao.updatePayment(payment)
    }

    suspend fun deleteAllPayment() {
        paymentDao.deleteAllPayment()
    }

    suspend fun deleteSinglePayment(payment: Payment) {
        paymentDao.deleteSinglePayment(payment)
    }

    fun searchPayment(searchQuery: String): Flow<List<Payment>>  {
        return paymentDao.searchPayment(searchQuery)
    }
}