package com.example.expense.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.expense.data.db.ExpenseDataBase
import com.example.expense.data.db.entities.Category
import com.example.expense.data.repositories.CategoryRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application): AndroidViewModel(application) {
    val getAllCategory: LiveData<List<Category>>
    // val getOnlyCategoryName: Flow<List<String>>

    private val categoryRepository: CategoryRepository

    init {
        val categoryDao = ExpenseDataBase.getDatabase(application).getCategoryDao()
        categoryRepository = CategoryRepository(categoryDao)
        getAllCategory = categoryRepository.getAllCategory
        // getOnlyCategoryName = categoryRepository.getOnlyCategoryName()

    }
    fun getAllCategory(): LiveData<List<Category>> {
        return getAllCategory
    }
    fun getOnlyCategoryName(): LiveData<List<String>> {
        return categoryRepository.getOnlyCategoryName()
    }
    fun insertCategory(category: Category) = viewModelScope.launch {
        categoryRepository.insertCategory(category)
    }
    fun updateCategory(category: Category) = viewModelScope.launch {
        categoryRepository.updateCategory(category)
    }
    fun deleteSingleCategory(category: Category) = viewModelScope.launch {
        categoryRepository.deleteSingleCategory(category)
    }
    fun deleteAllCategory() = viewModelScope.launch {
        categoryRepository.deleteAllCategory()
    }
    fun searchCategory(searchQuery: String): LiveData<List<Category>> {
        return categoryRepository.searchCategory(searchQuery).asLiveData()
    }


}