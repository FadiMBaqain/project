package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MoneyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoneyFragment : Fragment() {
    private lateinit var priceTextView: TextView
    private lateinit var calculateButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_money, container, false)
        priceTextView = view.findViewById(R.id.priceTextView)
        calculateButton = view.findViewById(R.id.Calculate)

        calculateButton.setOnClickListener {
            calculateTotalPay()
        }

        return view
    }

    private fun calculateTotalPay() {
        val hoursInput = view?.findViewById<EditText>(R.id.editTextNumberDecimal2)?.text?.toString()
        val hours = hoursInput?.toDoubleOrNull() ?: 0.0
        val payRate = 110.0

        val totalPay = hours * payRate

        priceTextView.text = totalPay.toString()
    }
}
