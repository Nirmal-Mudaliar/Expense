package com.example.expense.ui.settings.manageCategory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.expense.data.db.entities.Category
import com.example.expense.data.viewModel.CategoryViewModel
import com.example.expense.databinding.FragmentAddCategoryBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar


class AddCategoryFragment : BottomSheetDialogFragment() {

    // Setup view binding
    private var _binding: FragmentAddCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        val view = binding.root

        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        // Listener for submit button
        onClickSubmit()


        return view
    }
    
    private fun onClickSubmit() {
        binding.btnAddCategory.setOnClickListener {
            insertDataToDatabase()
        }
    }

    private fun insertDataToDatabase() {
        val value = binding.edtCategory.text.toString()
        val category = Category(0, value)
        categoryViewModel.insertCategory(category)
        Log.d("Inserted to category_table --> ", category.toString())
        Toast.makeText(requireContext(), "Successfully inserted!", Toast.LENGTH_LONG).show()
        binding.edtCategory.setText("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}