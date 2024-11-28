import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testlogin.R

class MainActivity : AppCompatActivity() {
    
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        emailEditText = findViewById(R.id.enterEmail)
        passwordEditText = findViewById(R.id.enterPassword)
        nextButton = findViewById(R.id.nextButton)

        // Set click listener for the next button
        nextButton.setOnClickListener {
            // Validate login
            if (validateLogin()) {
                // If login is successful, you can start a new activity or perform other actions
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                // Example: Intent to start another activity
                // startActivity(Intent(this, HomeActivity::class.java))
            } else {
                // Show error message
                Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateLogin(): Boolean {
        // Get entered email and password
        val enteredEmail = emailEditText.text.toString().trim()
        val enteredPassword = passwordEditText.text.toString().trim()

        // Check against the predefined credentials
        return enteredEmail == "test@te.st" && enteredPassword == "1234"
    }
}
