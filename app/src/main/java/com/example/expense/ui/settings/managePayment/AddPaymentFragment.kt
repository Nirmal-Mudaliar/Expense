package com.example.expense.ui.settings.managePayment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.expense.data.db.entities.Payment
import com.example.expense.data.viewModel.PaymentViewModel
import com.example.expense.databinding.FragmentAddPaymentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddPaymentFragment : BottomSheetDialogFragment() {

    // Setup view binding
    private var _binding: FragmentAddPaymentBinding? = null
    private val binding get() = _binding!!

    // viewModel
    private lateinit var paymentViewModel: PaymentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddPaymentBinding.inflate(inflater, container, false)
        val view = binding.root

        // Setup view model
        paymentViewModel = ViewModelProvider(this)[PaymentViewModel::class.java]

        // listener for add payment button
        onClickSubmit()

        return view
    }

    private fun onClickSubmit() {
        binding.btnAddPayment.setOnClickListener {
            insertPayment()
        }

    }

    private fun insertPayment() {
        val value = binding.edtPayment.text.toString()
        val payment = Payment(0, value)
        paymentViewModel.insertPayment(payment)
        Log.d("Inserted to payment_table --> ", value)
        Toast.makeText(requireContext(), "Succesfully inserted!", Toast.LENGTH_LONG).show()
        binding.edtPayment.setText("")
    }

}