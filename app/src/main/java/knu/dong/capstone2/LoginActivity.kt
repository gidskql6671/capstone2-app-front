package knu.dong.capstone2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import knu.dong.capstone2.databinding.ActivityLoginBinding

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLoginBinding.inflate(layoutInflater);
        setContentView(binding.root);
    }
}