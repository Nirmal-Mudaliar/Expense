package com.example.expense.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expense.R
import com.example.expense.data.adapters.ManageCategoryAdapter
import com.example.expense.data.adapters.ManageTransactionsAdapter
import com.example.expense.data.viewModel.CategoryViewModel
import com.example.expense.data.viewModel.TransactionsViewModel
import com.example.expense.databinding.FragmentHomeBinding
import com.google.android.material.animation.AnimationUtils
import java.util.*


class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var transactionViewModel: TransactionsViewModel

    private val manageTransactionAdapter = ManageTransactionsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        transactionViewModel = ViewModelProvider(this)[TransactionsViewModel::class.java]

        // setup recycler view
        setupRecyclerView()

        // listener for add transaction
        onClickAddTransaction()

        loadThisMonthAmount()

        val searchViewTransaction = binding.searchViewTransaction
        searchViewTransaction.setOnQueryTextListener(this)


        return view
    }

    private fun loadThisMonthAmount() {
        val cal_start = Calendar.getInstance()
        cal_start.set(Calendar.DAY_OF_MONTH, cal_start.getActualMinimum(Calendar.DAY_OF_MONTH))
        setTimeToBeginningOfDay(cal_start)
        val startingDate: Date = cal_start.time
        Log.d("First day =: ", startingDate.toString())

        val cal_end = Calendar.getInstance()
        cal_end.set(Calendar.DAY_OF_MONTH, cal_end.getActualMaximum(Calendar.DAY_OF_MONTH))
        setTimeToEndOfDay(cal_end)
        val endingDate: Date = cal_end.time
        Log.d("Last day =: ", endingDate.toString())

        transactionViewModel.getThisMonthAmount(startingDate, endingDate).observe(viewLifecycleOwner) {
            binding.tvThisMonthAmount.setText(it.toString())
            binding.tvThisMonthAmount.startAnimation(android.view.animation.AnimationUtils.loadAnimation(requireContext(), R.anim.this_month_amount))
        }

    }
    private fun setTimeToBeginningOfDay(cal: Calendar) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }
    private fun setTimeToEndOfDay(cal: Calendar) {
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
    }

    private fun onClickAddTransaction() {
        binding.fbAddTransaction.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addTransactionFragment)
        }
    }

    private fun setupRecyclerView() {
        val rcyManageTransaction = binding.rcyManageTransaction

        // Managing expanding and shrinking of extended floating button
        rcyManageTransaction.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
//                    binding.fbAddCategory.extend()
//                }
//                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                if (dy > 0 || dy < 0 && binding.fbAddCategory.isExtended) {
//                    binding.fbAddCategory.shrink()
//                }

                if (dy > 0 && binding.fbAddTransaction.isExtended) {
                    binding.fbAddTransaction.shrink()
                } else if (dy < 0 && !binding.fbAddTransaction.isExtended) {
                    binding.fbAddTransaction.extend()
                }
            }
        })



        rcyManageTransaction.adapter = manageTransactionAdapter
        rcyManageTransaction.layoutManager = LinearLayoutManager(requireContext())

        //rcyManageTransaction.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))

        transactionViewModel = ViewModelProvider(this)[TransactionsViewModel::class.java]
        transactionViewModel.getAllTransaction().observe(viewLifecycleOwner, Observer {transaction ->

            manageTransactionAdapter.setData(transaction)
            rcyManageTransaction.startLayoutAnimation()
        })



    }

//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        setupRecyclerView()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        setupRecyclerView()
//    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchTransaction(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchTransaction(query)
        }
        return true
    }

    private fun searchTransaction(query: String) {
        val searchQuery = "$query%"

        transactionViewModel.searchTransaction(searchQuery).observe(this
        ) { list ->
            list.let {
                manageTransactionAdapter.setData(it)
            }
        }

    }


}