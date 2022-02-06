package com.example.expense.data.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.expense.data.converters.Converters
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
@Entity(tableName = "transactions_table")
data class Transactions(
    @PrimaryKey(autoGenerate = true)
    val tid: Int,
    val date: Date,
    val amount: Int,
    val category: String,
    val paymentMode: String

    ) : Parcelable
