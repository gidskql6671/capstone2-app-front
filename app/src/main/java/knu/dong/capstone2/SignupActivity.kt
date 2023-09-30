package knu.dong.capstone2

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import knu.dong.capstone2.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkAllInputConfirm()

        binding.btnSignup.setOnClickListener {
            val email = binding.editEmail.text
            val password = binding.editPassword.text

            finish()
        }

        binding.titleBar.btnBack.setOnClickListener{
            onBackPressed()
        }
    }

    private fun checkAllInputConfirm() {
        val textWatcher = object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val email = binding.editEmail.text.toString()
                val emailPass = email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

                val password = binding.editPassword.text.toString()
                val confirmPassword = binding.editPasswordConfirm.text.toString()
                val passwordPass = password.isNotBlank() && password == confirmPassword

                if (emailPass && passwordPass) {
                    binding.btnSignup.backgroundTintList = ColorStateList.valueOf(getThemeColor(androidx.appcompat.R.attr.colorPrimary))
                    binding.btnSignup.setTextColor(getThemeColor(com.google.android.material.R.attr.colorOnPrimary))
                    binding.btnSignup.isEnabled = true
                }
                else {
                    binding.btnSignup.backgroundTintList = ColorStateList.valueOf(getThemeColor(R.attr.colorDisabledButton))
                    binding.btnSignup.setTextColor(getThemeColor(R.attr.colorDisabledButtonText))
                    binding.btnSignup.isEnabled = false
                }
            }
        }

        binding.editPassword.addTextChangedListener(textWatcher)
        binding.editEmail.addTextChangedListener(textWatcher)
        binding.editPasswordConfirm.addTextChangedListener(textWatcher)
    }

}