package com.example.expense.ui.home

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.expense.R
import com.example.expense.data.db.entities.Transactions
import com.example.expense.data.viewModel.CategoryViewModel
import com.example.expense.data.viewModel.PaymentViewModel
import com.example.expense.data.viewModel.TransactionsViewModel
import com.example.expense.databinding.FragmentUpdateTransactionBinding
import com.example.expense.ui.settings.manageCategory.UpdateCategoryFragmentArgs
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration.Companion.hours


class UpdateTransactionFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private var _binding: FragmentUpdateTransactionBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateTransactionFragmentArgs>()

    private lateinit var finalDate: Date

    private lateinit var transactionViewModel: TransactionsViewModel
    private lateinit var manageCategoryViewModel: CategoryViewModel
    private lateinit var managePaymentViewModel: PaymentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateTransactionBinding.inflate(inflater, container, false)
        val view = binding.root

        transactionViewModel = ViewModelProvider(this)[TransactionsViewModel::class.java]
        manageCategoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        managePaymentViewModel = ViewModelProvider(this)[PaymentViewModel::class.java]

        // loading the data which is clicked
        loadData()

        // listener for back button
        onClickBackBtn()

        // listener for date
        onClickDate()

        // listener for delete single transaction
        onClickDelete()

        // listener for submit button
        onSubmit()

        return view
    }

    private fun loadData() {
        val formatter = SimpleDateFormat("dd/M/YYYY")
        finalDate = args.currentTransaction.date
        val dateStr = formatter.format(finalDate)
        binding.edtUpdateDate.setText(dateStr)
        binding.spUpdateCategory.setText(args.currentTransaction.category)
        binding.edtUpdateAmount.setText(args.currentTransaction.amount.toString())
        binding.spUpdatePayment.setText(args.currentTransaction.paymentMode)


    }
    override fun onResume() {
        super.onResume()
        setupCategorySpinner()
        setupPaymentSpinner()

    }
    private fun setupPaymentSpinner() {
        var paymentList = emptyList<String>()
        managePaymentViewModel.getOnlyPaymentName().observe(viewLifecycleOwner){
            paymentList = it
            Log.d("List: ", paymentList.toString())
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.spinner_layout, paymentList)

            binding.spUpdatePayment.setAdapter(arrayAdapter)

        }

    }

    private fun setupCategorySpinner() {
        var categoryList = emptyList<String>()
        manageCategoryViewModel.getOnlyCategoryName().observe(viewLifecycleOwner){
            categoryList = it
            Log.d("List: ", categoryList.toString())
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.spinner_layout, categoryList)

            binding.spUpdateCategory.setAdapter(arrayAdapter)

        }

    }
    private fun onSubmit() {
        binding.btnUpdateTransaction.setOnClickListener {

            Log.d("final date: ", finalDate.toString())

            val category = binding.spUpdateCategory.text.toString()
            val amount = binding.edtUpdateAmount.text.toString().toInt()
            val paymentMode = binding.spUpdatePayment.text.toString()
            Log.d("Final: ", category + amount + paymentMode)

            val transaction = Transactions(args.currentTransaction.tid, finalDate, amount, category, paymentMode)
            transactionViewModel.updateTransaction(transaction)
            Snackbar.make(binding.lytUpdateTransaction, "Updated successfully!", Snackbar.LENGTH_LONG).show()
            Log.d("Updated transaction: ", transaction.toString())
        }

    }

    private fun onClickDate() {
        binding.edtUpdateDate.setOnClickListener {
            val day = finalDate.date
            val month = finalDate.month
            val formatter = SimpleDateFormat("YYYY")
            val year = formatter.format(finalDate)
            Log.d("Day: ", year)
            DatePickerDialog(requireContext(), this, year.toInt(), month, day).show()

        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        val year = p1
        val month = p2
        val textMonth = p2+1
        val day = p3

        val dateStr = "$day/$textMonth/$year"

        val cal = Calendar.getInstance()
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.YEAR, p1)
        cal.set(Calendar.DAY_OF_MONTH, p3)
        cal.set(Calendar.HOUR_OF_DAY, finalDate.hours)
        cal.set(Calendar.MINUTE, finalDate.minutes)
        cal.set(Calendar.SECOND, finalDate.seconds)
        finalDate = cal.time
        Log.d("Date-: ", finalDate.toString())

        binding.edtUpdateDate.setText(dateStr)
    }

    private fun onClickDelete() {
        binding.deleteSingleTransaction.setOnClickListener {
            val alert = AlertDialog.Builder(requireContext())
            alert.setPositiveButton("Yes") {_, _ ->
                transactionViewModel.deleteSingleTransaction(args.currentTransaction)
                Log.d("Deleted transaction -> ", args.currentTransaction.toString())

                findNavController().navigate(R.id.action_updateTransactionFragment_to_homeFragment)
            }
            alert.setNegativeButton("No") {_, _ ->

            }
            alert.setTitle("Delete")
            alert.setMessage("Are you sure you want to delete ${args.currentTransaction.category}?")
            alert.show()
        }
    }

    private fun onClickBackBtn() {
        binding.bckBtnUpdateTransaction.setOnClickListener {
            findNavController().navigate(R.id.action_updateTransactionFragment_to_homeFragment)
        }
    }



}