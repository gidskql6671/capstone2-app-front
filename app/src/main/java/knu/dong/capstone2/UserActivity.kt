package knu.dong.capstone2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import knu.dong.capstone2.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.titleBar.btnBack.setOnClickListener{
            onBackPressed()
        }
        binding.titleBar.title.text = "유저 페이지"
        binding.titleBar.btnAccount.visibility = View.INVISIBLE
    }
}