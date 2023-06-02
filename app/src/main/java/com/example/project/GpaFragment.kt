package com.example.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class GpaFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var courseHours: Double = 0.0
    private var expectedMark: Double = 0.0

    private lateinit var avgEditText: EditText
    private lateinit var hoursAvgEditText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gpa, container, false)
        avgEditText = view.findViewById(R.id.editTextNumber)
        hoursAvgEditText = view.findViewById(R.id.editTextNumber2)

        val arguments = arguments
        if (arguments != null) {
            val avgValue = arguments.getString(ARG_PARAM1)
            val hoursValue = arguments.getString(ARG_PARAM2)

            avgEditText.setText(avgValue)
            hoursAvgEditText.setText(hoursValue)
        }

        return view
    }











    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addButton: Button = view.findViewById(R.id.ADD)
        val tableLayout: TableLayout = view.findViewById(R.id.tableLayout)
        val choursEditText: EditText = view.findViewById(R.id.chours)
        val emarkEditText: EditText = view.findViewById(R.id.Emark)

        addButton.setOnClickListener {
            val choursTextt = choursEditText.text.toString()
            val emarkTextt = emarkEditText.text.toString()

            courseHours += choursTextt.toDoubleOrNull() ?: 0.0
            expectedMark += (emarkTextt.toDoubleOrNull() ?: 0.0) * (choursTextt.toDoubleOrNull() ?: 0.0)

            val choursText0 = choursEditText.text.toString()
            val choursText ="       $choursText0            "
            val emarkText = emarkEditText.text.toString()
            val tableRow = TableRow(requireContext())
            val choursTextView = TextView(requireContext())
            choursTextView.text = choursText
            val emarkTextView = TextView(requireContext())
            emarkTextView.text = emarkText
            tableRow.addView(choursTextView)
            tableRow.addView(emarkTextView)
            tableLayout.addView(tableRow)
            choursEditText.text = null
            emarkEditText.text = null
        }

        val calcButton: Button = view.findViewById(R.id.calc)

        calcButton.setOnClickListener {
            val avg: EditText = view.findViewById(R.id.editTextNumber)
            val hoursavg: EditText = view.findViewById(R.id.editTextNumber2)

            val hoursavgg = hoursavg.text.toString()
            val avgg = avg.text.toString()

            var avggg = avgg.toDouble()
            val hoursavggg = hoursavgg.toDouble()
            avggg *=  hoursavggg
            val cc = courseHours + (hoursavgg.toDoubleOrNull() ?: 0.0)
            val ee = expectedMark + avggg

            val average = ee / cc
            val textView: TextView = view.findViewById(R.id.textView7)
            textView.text = average.toString()
        }
        val resetButton: Button = view.findViewById(R.id.reset)
        val tableLayou: TableLayout = view.findViewById(R.id.tableLayout)


        resetButton.setOnClickListener(){
            courseHours = 0.0
            expectedMark = 0.0
            tableLayout.removeAllViews()

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GpaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }
}
