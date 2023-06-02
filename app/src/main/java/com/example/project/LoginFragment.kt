package com.example.project

import Admin
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
    private lateinit var imageView2: ImageView

    private var avg: Double by Delegates.notNull()
    private var hours: Int by Delegates.notNull()

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
            ),
        )

        val view = inflater.inflate(R.layout.fragment_login, container, false)

        passwordEditText = view.findViewById(R.id.editTextTextPassword)
        idEditText = view.findViewById(R.id.editTextNumberDecimal)
        loginButton = view.findViewById(R.id.button)
        imageView2 = view.findViewById(R.id.imageView2)

        loginButton.setOnClickListener {
            val password = passwordEditText.text.toString()
            val id = idEditText.text.toString()

            if (password.isNotEmpty() && id.isNotEmpty()) {
                try {
                    if (password == "Admin1" && id == "20200100") {
                        passwordEditText.text.clear()
                        idEditText.text.clear()

                        val transaction = requireActivity().supportFragmentManager.beginTransaction()

                        val adminFragment = Admin()
                        transaction.replace(R.id.fragmentContainer, adminFragment)
                        transaction.commit()

                        passwordEditText.visibility = View.GONE
                        idEditText.visibility = View.GONE
                        loginButton.visibility = View.GONE
                        imageView2.visibility = View.GONE
                    } else {
                        val userData = usersData.find { it["userId"].toString() == id && it["password"] == password }

                        if (userData != null) {
                            passwordEditText.text.clear()
                            idEditText.text.clear()

                            val transaction = requireActivity().supportFragmentManager.beginTransaction()

                            val avg = userData["avg"].toString()
                            val hours = userData["hours"].toString()

                            val gpaFragment = GpaFragment.newInstance(avg, hours)
                            transaction.replace(R.id.fragmentContainer, gpaFragment)
                            transaction.commit()

                            passwordEditText.visibility = View.GONE
                            idEditText.visibility = View.GONE
                            loginButton.visibility = View.GONE
                            imageView2.visibility = View.GONE
                        } else {
                            showToast("Invalid password or ID")
                        }
                    }
                } catch (e: Exception) {
                    showToast("Something went wrong. Please try again.")
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

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
