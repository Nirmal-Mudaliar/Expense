package com.example.expense.ui.settings.managePayment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expense.R
import com.example.expense.data.adapters.ManagePaymentAdapter
import com.example.expense.data.viewModel.PaymentViewModel
import com.example.expense.databinding.FragmentManagePaymentBinding


class ManagePaymentFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentManagePaymentBinding? = null
    private val binding get() = _binding!!

    private val paymentAdapter = ManagePaymentAdapter()

    private lateinit var paymentViewModel: PaymentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentManagePaymentBinding.inflate(inflater, container, false)
        val view = binding.root

        // setup actionbar
        setupActionBar()

        // Listener for add payment button
        onClickAddPayment()

        // setup recycler view
        setupRecyclerView()

        // Listener for back button
        onClickBackButton()

        // Listener for delete all payment
        onClickDeleteAllButton()

        // setup search view
        val searchView = binding.searchViewPayment
        searchView.setOnQueryTextListener(this)

        return view
    }

    private fun onClickAddPayment() {
        binding.fbAddPayment.setOnClickListener {
            findNavController().navigate(R.id.action_managePaymentFragment_to_addPaymentFragment)
        }
    }

    private fun setupRecyclerView() {
        val rcyManagePayment = binding.rcyManagePayment
        rcyManagePayment.adapter = paymentAdapter
        rcyManagePayment.layoutManager = LinearLayoutManager(requireContext())

        // rcyManagePayment.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))


        rcyManagePayment.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

                if (dy > 0 && binding.fbAddPayment.isExtended) {
                    binding.fbAddPayment.shrink()
                } else if (dy < 0 && !binding.fbAddPayment.isExtended) {
                    binding.fbAddPayment.extend()
                }
            }
        })

        paymentViewModel = ViewModelProvider(this)[PaymentViewModel::class.java]
        paymentViewModel.getAllPayment().observe(viewLifecycleOwner,  Observer {payment ->
            paymentAdapter.setData(payment)
//            rcyManagePayment.startLayoutAnimation()
        })


    }

    private fun setupActionBar() {
        val tbManagePayment = binding.tbManagePayment
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(tbManagePayment)
        }
    }

    private fun onClickBackButton() {
        binding.bckBtnManagePayment.setOnClickListener {
            findNavController().navigate(R.id.action_managePaymentFragment_to_settingsFragment)
        }
    }

    private fun onClickDeleteAllButton() {
        binding.deleteAllPayment.setOnClickListener {
            deleteAllPayment()
        }
    }

    private fun deleteAllPayment() {
        val alert = AlertDialog.Builder(requireContext())
        alert.setPositiveButton("Yes") {_, _ ->
            paymentViewModel.deleteAllPayment()
            Log.d("Deleted all payment --> ", "true")
            Toast.makeText(requireContext(), "Deleted successfully!", Toast.LENGTH_LONG).show()
        }
        alert.setNegativeButton("No") {_, _ ->

        }
        alert.setTitle("Delete")
        alert.setMessage("Are you sure you want to delete all payment?")
        alert.create().show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchPayment(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchPayment(query)
        }
        return true
    }

    private fun searchPayment(query: String) {
        val searchQuery = "$query%"
        paymentViewModel.searchPayment(searchQuery).observe(this, {list ->
            list.let {
                paymentAdapter.setData(it)
            }
        })
    }

}