package com.marwinjidopi.attendancesystem.ui.registerlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.marwinjidopi.attendancesystem.MainActivity
import com.marwinjidopi.attendancesystem.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            when {
                email.isEmpty() -> {
                    binding.etEmail.error = "This field is required"
                    binding.etEmail.requestFocus()
                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    binding.etPassword.error = "This field is required"
                    binding.etPassword.requestFocus()
                    return@setOnClickListener
                }
                password.length < 6 -> {
                    binding.etPassword.error = "Password at least 6 character"
                    binding.etPassword.requestFocus()
                    return@setOnClickListener
                }
                else -> {
                    mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                // sign in success, move to home activity
                                Toast.makeText(this, "You're logged in", Toast.LENGTH_LONG).show()
                                startActivity(Intent(this, MainActivity::class.java))
                                finishAffinity()
                            } else {
                                binding.btnLogin.visibility = View.VISIBLE
                                Toast.makeText(
                                    this,
                                    "Your E-Mail or Password are Wrong!",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        }
                }
            }
        }
        binding.tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }
    }
}