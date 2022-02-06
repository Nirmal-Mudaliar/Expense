package com.example.expense.ui.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import androidx.navigation.fragment.findNavController
import com.example.expense.R
import com.example.expense.data.db.entities.Category
import com.example.expense.data.db.entities.Transactions
import com.example.expense.data.viewModel.CategoryViewModel
import com.example.expense.data.viewModel.PaymentViewModel
import com.example.expense.data.viewModel.TransactionsViewModel
import com.example.expense.databinding.FragmentAddTransactionBinding
import com.google.android.material.snackbar.Snackbar

import java.text.SimpleDateFormat
import java.util.*


class AddTransactionFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!

    private lateinit var transactionViewModel: TransactionsViewModel
    private lateinit var manageCategoryViewModel: CategoryViewModel
    private lateinit var managePaymentViewModel: PaymentViewModel

    private lateinit var finalDate: Date

    override fun onResume() {
        super.onResume()
        setupCategorySpinner()
        setupPaymentSpinner()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentAddTransactionBinding.inflate(inflater, container, false)
        val view = binding.root

        transactionViewModel = ViewModelProvider(this)[TransactionsViewModel::class.java]
        manageCategoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        managePaymentViewModel = ViewModelProvider(this)[PaymentViewModel::class.java]

        // setting up action bar
        setupActionBar()

        // listener for back button
        onClickBackButton()

        // load date
        loadDate()

        // listener for add transaction
        onSubmit()

        // Listener for date button
        onClickDate()



        return view
    }

    private fun onClickBackButton() {
        binding.bckBtnAddTransaction.setOnClickListener {
            findNavController().navigate(R.id.action_addTransactionFragment_to_homeFragment)
        }
    }
    private fun setupCategorySpinner() {
        var categoryList = emptyList<String>()
        manageCategoryViewModel.getOnlyCategoryName().observe(viewLifecycleOwner){
            categoryList = it
            Log.d("List: ", categoryList.toString())
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.spinner_layout, categoryList)

            if (categoryList.isEmpty()) {
                binding.spCategory.setHint("Category list is empty!")
            }
            else {
                binding.spCategory.setText(categoryList[0])
                binding.spCategory.setAdapter(arrayAdapter)
            }

        }

    }

    private fun setupPaymentSpinner() {
        var paymentList = emptyList<String>()
        managePaymentViewModel.getOnlyPaymentName().observe(viewLifecycleOwner){
            paymentList = it
            Log.d("List: ", paymentList.toString())
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.spinner_layout, paymentList)

            if (paymentList.isEmpty()) {
                binding.spPayment.setHint("Payment list is empty!")
            }
            else {
                binding.spPayment.setText(paymentList[0])
                binding.spPayment.setAdapter(arrayAdapter)
            }

        }

    }
    private fun setupActionBar() {
        val tbAddTransaction = binding.tbAddTransaction
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(tbAddTransaction)
        }
    }

    private fun loadDate() {
        val date = Calendar.getInstance().time
        finalDate = date

       val formatter = SimpleDateFormat("dd/M/YYYY")
       val s = formatter.format(date)

        binding.edtDate.setText(s)
    }
    private fun onSubmit() {
        binding.btnAddTransaction.setOnClickListener {

            val category = binding.spCategory.text.toString()
            val amount = binding.edtAmount.text.toString().toInt()
            val paymentMode = binding.spPayment.text.toString()

            val transaction = Transactions(0, finalDate, amount, category, paymentMode)
            transactionViewModel.insertTransaction(transaction)
            Snackbar.make(binding.lytAddTransaction, "Successfully inserted!", Snackbar.LENGTH_LONG).show()
            Log.d("Successfully inserted -> ", transaction.toString())


            binding.spCategory.setText("")
            binding.spPayment.setText("")
            binding.edtAmount.setText("")

        }
    }

    private fun onClickDate() {
        binding.edtDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val year = cal.get(Calendar.YEAR)
            DatePickerDialog(requireContext(), this, year, month, day).show()
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
        finalDate = cal.time
        Log.d("Date-: ", finalDate.toString())

        binding.edtDate.setText(dateStr)
    }





}
