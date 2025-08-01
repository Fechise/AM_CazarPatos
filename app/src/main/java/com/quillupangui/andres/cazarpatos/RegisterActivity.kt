package com.quillupangui.andres.cazarpatos

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth


class RegisterActivity : AppCompatActivity() {

    private lateinit var buttonRegister: Button
    private lateinit var buttonBackToLogin: Button
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonRegister = findViewById(R.id.buttonRegister)
        buttonBackToLogin = findViewById(R.id.buttonBackToLogin)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextConfirmPassword = findViewById(R.id.editTextPasswordConfirmation)

        //Eventos clic
        buttonRegister.setOnClickListener {
            val email = editTextEmail.text.toString()
            val clave = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()
            //Validaciones de datos requeridos y formatos
            if (!validateRequiredData())
                return@setOnClickListener
            RegistrarUsuario(email, clave)
        }
        buttonBackToLogin.setOnClickListener {
            finish()
        }
    }

    private fun validateRequiredData(): Boolean {
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()
        val confirmPassword = editTextConfirmPassword.text.toString()
        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.error_email_required))
            editTextEmail.requestFocus()
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError(getString(R.string.error_email_invalid))
            editTextEmail.requestFocus()
            return false
        }
        if (password.isEmpty()) {
            editTextPassword.setError(getString(R.string.error_password_required))
            editTextPassword.requestFocus()
            return false
        }
        if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.setError(getString(R.string.error_confirm_password_required))
            editTextConfirmPassword.requestFocus()
            return false
        }
        if (password.length < 6) {
            editTextPassword.setError(getString(R.string.error_password_min_length))
            editTextPassword.requestFocus()
            return false
        }
        if (password != confirmPassword) {
            editTextConfirmPassword.setError(getString(R.string.error_passwords_do_not_match))
            editTextConfirmPassword.requestFocus()
            return false
        }
        return true
    }

    private fun RegistrarUsuario(email: String, clave: String) {
        auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, clave)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    finish()
                } else {
                    Toast.makeText(this, R.string.error_firebase_authentication, Toast.LENGTH_LONG).show()
                }
            }
    }
}