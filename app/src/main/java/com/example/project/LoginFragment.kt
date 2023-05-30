package com.example.project

import DatabaseHelper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlin.properties.Delegates

class LoginFragment : Fragment() {
    private lateinit var passwordEditText: EditText
    private lateinit var idEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var editTextNumber: EditText
    private lateinit var editTextNumber2: EditText
    private lateinit var imageView2: ImageView

    private var avg by Delegates.notNull<Double>()
    private var hours by Delegates.notNull<Int>()

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        val dbHelper = DatabaseHelper(requireContext())
        val usersData = listOf(
            mapOf(
                "userId" to 20200123,
                "password" to "Fadi1",
                "avg" to 85.0,
                "hours" to 20,
                "coursePrice" to 9.99
            ),
            mapOf(
                "userId" to 20200456,
                "password" to "Nadeem1",
                "avg" to 75.5,
                "hours" to 15,
                "coursePrice" to 12.99
            ),
            mapOf(
                "userId" to 20200789,
                "password" to "Hassan1",
                "avg" to 90.0,
                "hours" to 25,
                "coursePrice" to 14.99
            )
        )
        for (userData in usersData) {
            val userId = userData["userId"] as Int
            val password = userData["password"] as String
            avg = userData["avg"] as Double  // Remove the "val" keyword here
            hours = userData["hours"] as Int  // Remove the "val" keyword here
            val coursePrice = userData["coursePrice"] as Double
            dbHelper.insertUser(userId, password, avg, hours, coursePrice)
        }

        val view = inflater.inflate(R.layout.fragment_login, container, false)

        passwordEditText = view.findViewById(R.id.editTextTextPassword)
        idEditText = view.findViewById(R.id.editTextNumberDecimal)
        loginButton = view.findViewById(R.id.button)

        loginButton.setOnClickListener {
            val pic = view.findViewById<ImageView>(R.id.imageView2)

            val password = passwordEditText.text.toString()
            val id = idEditText.text.toString()

            if (password.isNotEmpty() && id.isNotEmpty()) {
                if (isValidPassword(password) && isValidId(id)) {
                    passwordEditText.text.clear()
                    idEditText.text.clear()

                    val fragment = GpaFragment.newInstance(avg.toString(), hours.toString())
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragmentContainer, fragment)
                    transaction.commit()

                    passwordEditText.visibility = View.GONE
                    idEditText.visibility = View.GONE
                    loginButton.visibility = View.GONE
                    pic.visibility = View.GONE
                } else {
                    showToast("Invalid password or ID")
                }
            }
        }


        return view
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 2

    }

    private fun isValidId(id: String): Boolean {

        return id.matches("[0-9]+".toRegex())
    }

    private fun navigateToGpaFragment() {
        val pic = view?.findViewById<ImageView>(R.id.imageView2)

        val fragment = GpaFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()

        // Hide the login views after navigation
        imageView2.visibility= View.GONE
        passwordEditText.visibility = View.GONE
        idEditText.visibility = View.GONE
        if (pic != null) {
            pic.visibility = View.GONE
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


}
