package knu.dong.capstone2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import knu.dong.capstone2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, SelectChatbotActivity::class.java)
        startActivity(intent)
        finish()
    }
}