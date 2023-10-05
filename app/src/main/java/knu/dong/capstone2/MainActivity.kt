package knu.dong.capstone2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import knu.dong.capstone2.common.HttpRequestHelper
import knu.dong.capstone2.databinding.ActivityMainBinding
import knu.dong.capstone2.dto.UserDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private lateinit var userInfo: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        job = Job()
        userInfo = getSharedPreferences("user_info", MODE_PRIVATE)

        startApp()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun startApp() {
        launch(Dispatchers.Main) {
            val userId = checkLogin()

            val intent =
                if (userId == -1L) {
                    Intent(this@MainActivity, LoginActivity::class.java)
                } else {
                    userInfo.edit {
                        putLong("id", userId)
                    }

                    Intent(this@MainActivity, SelectChatbotActivity::class.java)
                }

            startActivity(intent)
            finish()
        }
    }

    private suspend fun checkLogin(): Long {
        val user = HttpRequestHelper(this@MainActivity).get("api/users", UserDto::class.java)

        return user?.id ?: -1
    }
}