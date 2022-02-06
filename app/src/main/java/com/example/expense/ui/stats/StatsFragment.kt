package com.example.expense.ui.stats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.expense.databinding.FragmentStatsBinding
import java.text.SimpleDateFormat
import java.util.*


class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!

    private var cal = Calendar.getInstance()
    private var i = 0

    private var year = cal.weekYear
    private var yYear = cal.weekYear

    private var cal2 = Calendar.getInstance().time
    val formatter = SimpleDateFormat("MMMM")
    private val curMonth = formatter.format(cal2)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.spPeriodMode.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (adapterView?.getItemAtPosition(position).toString() == "Monthly") {
                   setMonths()
                }
                else if (adapterView?.getItemAtPosition(position).toString() == "Yearly") {
                    setYear()
                }
                Toast.makeText(requireContext(), "You selected: ${adapterView?.getItemAtPosition(position).toString()}", Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        return view
    }

    private fun setYear() {
        binding.period.visibility = View.GONE
        loadYear()
    }
    private fun loadYear() {
        yYear = cal.weekYear
        binding.monthYear.setText(yYear.toString())
        binding.leftArrow.setOnClickListener {
            yYear -= 1
            binding.monthYear.setText(yYear.toString())
        }
        binding.rightArrow.setOnClickListener {
            yYear += 1
            binding.monthYear.setText(yYear.toString())
        }


    }

    private fun loadMonths() {
        binding.period.setText(curMonth.toString())
        binding.monthYear.setText(year.toString())
        binding.rightArrow.setOnClickListener {
            i += 1
            if (i == 12) {
                i = 0
                year += 1

            }
            val representations: Map<String, Int> =
                cal.getDisplayNames(Calendar.MONTH, Calendar.ALL_STYLES, Locale.ENGLISH)
            val key = representations.filterValues { it == i }.keys
            if (i == 4) {
                binding.period.setText(key.elementAt(0))

            }
            else {
                binding.period.setText(key.elementAt(1))

            }
            binding.monthYear.setText(year.toString())
            Log.d("values: ${key.toString()} year: ", year.toString() )


            getDateObject(i, year)
        }

        binding.leftArrow.setOnClickListener {
            i -= 1
            if (i == -1) {
                i = 11
                year -= 1
            }
            val representations: Map<String, Int> =
                cal.getDisplayNames(Calendar.MONTH, Calendar.ALL_STYLES, Locale.ENGLISH)
            val key = representations.filterValues { it == i }.keys
            if (i == 4) {
                binding.period.setText(key.elementAt(0))
            }
            else {
                binding.period.setText(key.elementAt(1))
            }
            binding.monthYear.setText(year.toString())
            Log.d("values: ${key.toString()} year: ", year.toString() )

            getDateObject(i, year)
        }
    }

    private fun getDateObject(m: Int, y: Int) {
        val cal3 = Calendar.getInstance()
        cal3.set(Calendar.MONTH, m)
        cal3.set(Calendar.YEAR, y)
        cal3.set(Calendar.DAY_OF_MONTH, cal3.getActualMinimum(Calendar.DAY_OF_MONTH))
        setTimeToBeginningOfDay(cal3)
        val startingDate = cal3.time

        cal3.set(Calendar.MONTH, m)
        cal3.set(Calendar.YEAR, y)
        cal3.set(Calendar.DAY_OF_MONTH, cal3.getActualMaximum(Calendar.DAY_OF_MONTH))
        setTimeToEndOfDay(cal3)
        val endingDate = cal3.time
        Log.d("Starintg date", startingDate.toString())
        Log.d("Ending date", endingDate.toString())


    }

    private fun setTimeToBeginningOfDay(cal: Calendar) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }
    private fun setTimeToEndOfDay(cal: Calendar) {
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
    }

    private fun setMonths() {
        binding.period.visibility = View.VISIBLE
        loadMonths()
    }


}