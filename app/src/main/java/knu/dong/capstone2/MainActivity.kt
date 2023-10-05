package knu.dong.capstone2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import knu.dong.capstone2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var userInfo: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userInfo = getSharedPreferences("user_info", MODE_PRIVATE)

        
        startActivity(getNextIntent())
        finish()
    }

    private fun getNextIntent(): Intent {
        val userId = userInfo.getLong("id", -1)

        return if (userId == -1L) {
            Intent(this, LoginActivity::class.java)
        } else {
            Intent(this, SelectChatbotActivity::class.java).also {
                it.putExtra("userId", userId)
            }
        }
    }
}