package com.example.expense.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.expense.data.db.entities.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)

    @Query("DELETE FROM category_table")
    suspend fun deleteAllCategory()

    @Query("SELECT category from category_table")
    fun getOnlyCategoryName(): LiveData<List<String>>

    @Delete
    suspend fun deleteSingleCategory(category: Category)

    @Query("SELECT * FROM category_table")
    fun getAllCategory(): LiveData<List<Category>>

    @Query("SELECT * FROM category_table WHERE category LIKE :searchQuery")
    fun searchCategory(searchQuery: String): Flow<List<Category>>

}