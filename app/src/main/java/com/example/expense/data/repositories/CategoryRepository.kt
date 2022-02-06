package com.example.expense.data.repositories

import androidx.lifecycle.LiveData
import com.example.expense.data.db.dao.CategoryDao
import com.example.expense.data.db.entities.Category
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryDao: CategoryDao) {
    val getAllCategory: LiveData<List<Category>> = categoryDao.getAllCategory()

    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    fun getOnlyCategoryName(): LiveData<List<String>> {
        return categoryDao.getOnlyCategoryName()
    }

    suspend fun deleteSingleCategory(category: Category) {
        categoryDao.deleteSingleCategory(category)
    }
    suspend fun deleteAllCategory() {
        categoryDao.deleteAllCategory()
    }
    suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category)
    }
    fun searchCategory(searchQuery: String): Flow<List<Category>> {
        return categoryDao.searchCategory(searchQuery)
    }
}