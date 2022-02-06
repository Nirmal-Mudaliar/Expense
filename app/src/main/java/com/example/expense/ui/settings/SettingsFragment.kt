package com.example.expense.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.example.expense.R
import com.example.expense.databinding.FragmentSettingsBinding
import com.google.android.material.button.MaterialButtonToggleGroup


class SettingsFragment : Fragment() {

    //Setup View Binding
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root

        // Setup Action bar
        setupActionBar()

        // Listener for back button
        onClickBackButton()

        // Listener for ManageCategory
        onClickManageCategory()

        // Listener for ManagePayment
        onClickManagePayment()

        // load checked button from preference file
        loadDataForAppTheme()

        // Listener for app theme button
        onClickAppTheme()

        return view
    }

    private fun onClickAppTheme() {
        binding.cvAppTheme.setOnClickListener {

            if (binding.btgTheme.visibility == View.GONE) {
                binding.btgTheme.visibility = View.VISIBLE
                binding.rightArrow.visibility = View.GONE
                binding.downArrow.visibility = View.VISIBLE
                setTheme()
            }
            else if (binding.btgTheme.visibility == View.VISIBLE) {
                binding.btgTheme.visibility = View.GONE
                binding.rightArrow.visibility = View.VISIBLE
                binding.downArrow.visibility = View.GONE
            }
        }
    }

    private fun loadDataForAppTheme() {
        val sp1: SharedPreferences? = activity?.getPreferences(Context.MODE_PRIVATE)
        val appTheme: Int? = sp1?.getInt("theme", -1)
        if (appTheme == -1) {
            binding.btgTheme.check(R.id.btnDefault)
            Log.d("Inside Default --> ", binding.btnDefault.isSelected.toString())
        }
        else if (appTheme == 2) {
            binding.btgTheme.check(R.id.btnDark)
            Log.d("Inside Dark --> ", binding.btnDark.isSelected.toString())
        }
        else {
            binding.btgTheme.check(R.id.btnLight)
            Log.d("Inside Light --> ", binding.btnLight.isSelected.toString())
        }
    }

    private fun setTheme() {
        binding.btgTheme.addOnButtonCheckedListener { group, checkedId, isChecked ->
            val theme = when (checkedId) {
                R.id.btnDefault -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                R.id.btnDark -> AppCompatDelegate.MODE_NIGHT_YES
                R.id.btnLight -> AppCompatDelegate.MODE_NIGHT_NO
                else -> {}
            }
            Log.d("isChecked: ", "$isChecked Theme: $theme")
            val sp: SharedPreferences? = activity?.getPreferences(Context.MODE_PRIVATE)
            val spEditor = sp?.edit()
            spEditor?.putInt("theme", theme as Int)
            spEditor?.commit()

            AppCompatDelegate.setDefaultNightMode(theme as Int)
        }

    }

    private fun onClickManageCategory() {
        binding.cvManageCategory.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_manageCategoryFragment)
        }
    }

    private fun onClickBackButton() {
        binding.bckBtnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_homeFragment)
        }
    }

    private fun setupActionBar() {
        val tbSettings = binding.tbSettings
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(tbSettings)
        }
    }

    private fun onClickManagePayment() {
        binding.cvManagePayment.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_managePaymentFragment)
        }
    }




}