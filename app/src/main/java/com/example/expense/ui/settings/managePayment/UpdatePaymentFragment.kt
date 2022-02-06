package com.example.expense.ui.settings.managePayment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.expense.R
import com.example.expense.data.db.entities.Payment
import com.example.expense.data.viewModel.PaymentViewModel
import com.example.expense.databinding.FragmentUpdatePaymentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UpdatePaymentFragment : BottomSheetDialogFragment() {

    // setup view binding
    private var _binding: FragmentUpdatePaymentBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdatePaymentFragmentArgs>()
    private lateinit var paymentViewModel: PaymentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdatePaymentBinding.inflate(inflater, container, false)
        val view = binding.root

        // setup payment view model
        paymentViewModel = ViewModelProvider(this)[PaymentViewModel::class.java]

        // load data
        loadData()

        // Listener for update Payment button
        onClickSubmitButton()

        // Listener for delete single payment
        onClickDeleteSinglePayment()


        return view
    }

    private fun loadData() {
        val value = args.currentPayment.payment
        binding.edtUpdatePayment.setText(value)
    }

    private fun onClickSubmitButton() {
        binding.btnUpdatePayment.setOnClickListener {
            updatePaymentToDatabase()
        }
    }

    private fun updatePaymentToDatabase() {
        val value = binding.edtUpdatePayment.text.toString()
        val payment = Payment(args.currentPayment.pid, value)
        paymentViewModel.updatePayment(payment)
        Log.d("Updated successfully --> ", value)
        Toast.makeText(requireContext(), "Updated successfully!", Toast.LENGTH_LONG).show()
    }

    private fun deleteSinglePayment() {

        val alert = AlertDialog.Builder(requireContext())
        alert.setPositiveButton("Yes") {_, _ ->
            paymentViewModel.deleteSinglePayment(args.currentPayment)
            Log.d("Deleted payment successfully --> ", args.currentPayment.payment)
            Toast.makeText(requireContext(), "Deleted successfully", Toast.LENGTH_LONG).show()
            binding.edtUpdatePayment.setText("")
        }
        alert.setNegativeButton("No") {_, _ ->

        }
        alert.setTitle("Delete")
        alert.setMessage("Are you sure you want to delete ${args.currentPayment.payment}?")
        alert.create().show()



    }

    private fun onClickDeleteSinglePayment() {
        binding.deletePayment.setOnClickListener {
            deleteSinglePayment()
        }
    }
}