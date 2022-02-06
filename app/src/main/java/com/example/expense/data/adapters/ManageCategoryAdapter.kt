package com.example.expense.data.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.expense.R
import com.example.expense.data.db.entities.Category
import com.example.expense.ui.settings.manageCategory.ManageCategoryFragmentDirections

class ManageCategoryAdapter: RecyclerView.Adapter<ManageCategoryAdapter.MyViewHolder>() {

    private var categoryList = emptyList<Category>()


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvCategory = itemView.findViewById(R.id.cvMasterTitle) as TextView
        val cvMasterLayout = itemView.findViewById(R.id.cv_cvMasterLayout) as ConstraintLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_master, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = categoryList[position]
        holder.tvCategory.text = currentItem.category
        holder.cvMasterLayout.setOnClickListener {
            val action = ManageCategoryFragmentDirections.actionManageCategoryFragmentToUpdateCategoryFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun setData(catList: List<Category>) {
        this.categoryList = catList
        notifyDataSetChanged()
    }
}