package com.example.expense.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.expense.R
import com.example.expense.data.db.entities.Transactions
import com.example.expense.ui.home.HomeFragment
import com.example.expense.ui.home.HomeFragmentDirections
import java.text.SimpleDateFormat

class ManageTransactionsAdapter: RecyclerView.Adapter<ManageTransactionsAdapter.MyViewHolder>() {

    private var transactionsList = emptyList<Transactions>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val amount = itemView.findViewById<TextView>(R.id.tvMasterAmount)
        val tvDate = itemView.findViewById<TextView>(R.id.tvMasterDate)
        val categoryName = itemView.findViewById<TextView>(R.id.tvMasterCategory)
        val paymentMode = itemView.findViewById<TextView>(R.id.tvMasterPaymentMode)
        val masterLayout = itemView.findViewById<ConstraintLayout>(R.id.cvMasterTransactionLayout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row_transaction, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTransaction = transactionsList[position]
        val formatter = SimpleDateFormat("dd/M/YYYY")
        val dateStr = formatter.format(currentTransaction.date)
        holder.tvDate.text = dateStr
        holder.categoryName.text = currentTransaction.category
        holder.amount.text = currentTransaction.amount.toString()
        holder.paymentMode.text = currentTransaction.paymentMode

        holder.masterLayout.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToUpdateTransactionFragment(currentTransaction)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return transactionsList.size
    }
    fun setData(transList: List<Transactions>) {
        this.transactionsList = transList
        notifyDataSetChanged()
    }

}