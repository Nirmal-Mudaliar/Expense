package com.example.expense.ui.settings.manageCategory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.expense.data.db.entities.Category
import com.example.expense.data.viewModel.CategoryViewModel
import com.example.expense.databinding.FragmentUpdateCategoryBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class UpdateCategoryFragment : BottomSheetDialogFragment() {

    // Setup view binding
    private var _binding: FragmentUpdateCategoryBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateCategoryFragmentArgs>()
    private lateinit var categoryViewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateCategoryBinding.inflate(inflater, container, false)
        val view = binding.root

        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        // load data
        loadData()

        // Listener for submit button
        onClickSubmit()

        // Listener for delete button
        deleteSingleCategory()


        return view
    }

    private fun loadData() {
        binding.edtUpdateCategory.setText(args.currentCategory.category)
    }

    private fun updateCategory() {
        val view = binding.edtUpdateCategory.text.toString()
        val category = Category(args.currentCategory.id, view)
        categoryViewModel.updateCategory(category)
        Log.d("Updated to category_table --> ", view)
        Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()
    }

    private fun onClickSubmit() {
        binding.btnUpdateCategory.setOnClickListener {
            updateCategory()
        }
    }

    private fun deleteSingleCategory() {
        binding.deleteCategory.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setPositiveButton("Yes") {_, _ ->
                categoryViewModel.deleteSingleCategory(args.currentCategory)
                Log.d("Deleted category --> ", args.currentCategory.category)
                binding.edtUpdateCategory.setText("")
                Toast.makeText(requireContext(), "Deleted Successfully!", Toast.LENGTH_LONG).show()
            }
            builder.setNegativeButton("No") {_, _ ->

            }
            builder.setTitle("Delete")
            builder.setMessage("Are you sure you want to delete ${args.currentCategory.category}?")
            builder.create().show()

        }
    }


}