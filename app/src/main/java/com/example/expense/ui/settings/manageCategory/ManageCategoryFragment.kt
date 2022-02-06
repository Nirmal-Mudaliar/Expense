package com.example.expense.ui.settings.manageCategory

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
import com.example.expense.R
import com.example.expense.data.adapters.ManageCategoryAdapter
import com.example.expense.data.viewModel.CategoryViewModel
import com.example.expense.databinding.FragmentManageCategoryBinding
import androidx.recyclerview.widget.RecyclerView

class ManageCategoryFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentManageCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoryViewModel: CategoryViewModel
    private val manageCategoryAdapter = ManageCategoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentManageCategoryBinding.inflate(inflater, container, false)
        val view = binding.root

        // Setup Action bar
        setupActionBar()



        // Load data
        setupRecyclerView()

        //Setup back button
        onClickBackButton()

        // Listener for add category
        onClickFb()

        // setup search view
        val searchView = binding.searchViewCategory
        searchView.setOnQueryTextListener(this)

        // Delete all category
        deleteAllCategory()



        return view
    }

    private fun setupActionBar() {
        val tbManageCategory = binding.tbManageCategory
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(tbManageCategory)
        }
    }

    private fun setupRecyclerView() {
        val rcyManageCategory = binding.rcyManageCategory

        // Managing expanding and shrinking of extended floating button
        rcyManageCategory.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

                if (dy > 0 && binding.fbAddCategory.isExtended) {
                    binding.fbAddCategory.shrink()
                } else if (dy < 0 && !binding.fbAddCategory.isExtended) {
                    binding.fbAddCategory.extend()
                }
            }
        })



        rcyManageCategory.adapter = manageCategoryAdapter
        rcyManageCategory.layoutManager = LinearLayoutManager(requireContext())
        // rcyManageCategory.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))

        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        categoryViewModel.getAllCategory().observe(viewLifecycleOwner, Observer {category ->
            manageCategoryAdapter.setData(category)
//            rcyManageCategory.startLayoutAnimation()
        })

    }

    private fun onClickFb() {
        binding.fbAddCategory.setOnClickListener {
            findNavController().navigate(R.id.action_manageCategoryFragment_to_addCategoryFragment)
        }
    }

    private fun onClickBackButton() {
        binding.bckBtnManageCategory.setOnClickListener {
            findNavController().navigate(R.id.action_manageCategoryFragment_to_settingsFragment)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchCategory(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchCategory(query)
        }
        return true
    }

    private fun searchCategory(query: String) {
        val searchQuery = "$query%"

        categoryViewModel.searchCategory(searchQuery).observe(this
        ) { list ->
            list.let {
                manageCategoryAdapter.setData(it)
            }
        }

    }

    private fun deleteAllCategory() {
        binding.deleteAll.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes") {_, _ ->
                categoryViewModel.deleteAllCategory()
                Log.d("Deleted all category --> ", "True")

                Toast.makeText(requireContext(), "Deleted Successfully!", Toast.LENGTH_LONG).show()
            }
            builder.setNegativeButton("No") {_, _ ->

            }
            builder.setTitle("Delete")
            builder.setMessage("Are you sure you want to delete all category?")
            builder.create().show()
        }
    }
}