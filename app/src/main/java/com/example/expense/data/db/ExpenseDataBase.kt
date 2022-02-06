package com.example.expense.data.db

import android.content.Context
import androidx.room.*
import com.example.expense.data.converters.Converters
import com.example.expense.data.db.dao.CategoryDao
import com.example.expense.data.db.dao.PaymentDao
import com.example.expense.data.db.dao.TransactionsDao
import com.example.expense.data.db.entities.Category
import com.example.expense.data.db.entities.Payment
import com.example.expense.data.db.entities.Transactions


@Database(entities = [Category::class, Payment::class, Transactions::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ExpenseDataBase: RoomDatabase() {

    abstract fun getCategoryDao(): CategoryDao
    abstract fun getPaymentDao(): PaymentDao
    abstract fun getTransactionDao(): TransactionsDao

    companion object {
        @Volatile
        private var INSTANCE: ExpenseDataBase? = null

        fun getDatabase(context: Context): ExpenseDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseDataBase::class.java,
                    "expense_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}