package com.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.ViewModelFactory
import com.example.bottomnavyt.MainActivity
import com.example.bottomnavyt.R
import com.example.bottomnavyt.databinding.ActivityRegisterBinding
import com.ui.login.Login
import com.ui.login.LoginViewModel
import com.data.Result

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val viewModelFactory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val registerViewModel: RegisterViewModel by viewModels {
            viewModelFactory
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        binding.btnLogin.setOnClickListener {
            val name = binding.NameEditText.text.toString().trim()
            val email = binding.EmailText.text.toString().trim()
            val password = binding.PasswordText.text.toString().trim()
            val address = binding.AddressEditText.text.toString().trim()
            val noHp = binding.PhoneEditText.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua formulir harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerViewModel.register(name, email, password, address, noHp)
        }


        registerViewModel.registResult.observe(this, Observer {result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val intent = Intent(this, Login::class.java)
                        startActivity(intent)

                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, result.error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })


    }
}