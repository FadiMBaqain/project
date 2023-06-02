import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.project.DatabaseHelper
import com.example.project.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Admin : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var userIdEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var avg_edittext: EditText
    private lateinit var avgHoursEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin, container, false)

        userIdEditText = view.findViewById(R.id.userid_edittext)
        passwordEditText = view.findViewById(R.id.password_edittext)
        avg_edittext = view.findViewById(R.id.avg_edittext)
        avgHoursEditText = view.findViewById(R.id.avg_hours_edittext)
        priceEditText = view.findViewById(R.id.price_edittext)
        saveButton = view.findViewById(R.id.save_button)

        saveButton.setOnClickListener {
            val userId = userIdEditText.text.toString()
            val password = passwordEditText.text.toString()
            val avg = avg_edittext.text.toString().toDouble()
            val avgHours = avgHoursEditText.text.toString().toDouble()
            val price = priceEditText.text.toString().toDouble()


            val dbHelper = DatabaseHelper(requireContext())
            val insertUser = dbHelper.insertUser(userId, password, avg, avgHours, price)


            // Clear the input fields after saving
            userIdEditText.text.clear()
            passwordEditText.text.clear()
            avgHoursEditText.text.clear()
            priceEditText.text.clear()
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Admin().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
