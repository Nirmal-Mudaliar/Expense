package com.example.expense.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.expense.data.db.ExpenseDataBase
import com.example.expense.data.db.entities.Payment
import com.example.expense.data.repositories.PaymentRepository
import kotlinx.coroutines.launch

class PaymentViewModel(application: Application): AndroidViewModel(application) {
    val getAllPayment: LiveData<List<Payment>>
    private val paymentRepository: PaymentRepository

    init {
        val paymentDao = ExpenseDataBase.getDatabase(application).getPaymentDao()
        paymentRepository = PaymentRepository(paymentDao)
        getAllPayment = paymentRepository.getAllPayment
    }
    fun getOnlyPaymentName(): LiveData<List<String>> {
        return paymentRepository.getOnlyPaymentName()
    }
    fun getAllPayment(): LiveData<List<Payment>> {
        return getAllPayment
    }

    fun insertPayment(payment: Payment) = viewModelScope.launch {
        paymentRepository.insertPayment(payment)
    }

    fun updatePayment(payment: Payment) = viewModelScope.launch {
        paymentRepository.updatePayment(payment)
    }

    fun deleteAllPayment() = viewModelScope.launch {
        paymentRepository.deleteAllPayment()
    }

    fun deleteSinglePayment(payment: Payment) = viewModelScope.launch {
        paymentRepository.deleteSinglePayment(payment)
    }

    fun searchPayment(searchQuery: String): LiveData<List<Payment>> {
        return paymentRepository.searchPayment(searchQuery).asLiveData()
    }
}