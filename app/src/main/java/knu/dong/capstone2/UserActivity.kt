package knu.dong.capstone2

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.edit
import knu.dong.capstone2.common.HttpRequestHelper
import knu.dong.capstone2.databinding.ActivityUserBinding
import knu.dong.capstone2.dto.UserDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private lateinit var binding: ActivityUserBinding
    private lateinit var sharedUserInfo: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        job = Job()
        sharedUserInfo = getSharedPreferences("user_info", MODE_PRIVATE)

        binding.titleBar.btnBack.setOnClickListener{
            onBackPressed()
        }
        binding.titleBar.title.text = "유저 페이지"
        binding.titleBar.btnAccount.visibility = View.INVISIBLE

        binding.btnLogout.setOnClickListener {
            logout()
        }

        showUserInfo()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun logout() {
        launch(Dispatchers.Main) {
            HttpRequestHelper(this@UserActivity).post("api/users/logout")
            sharedUserInfo.edit {
                remove("id")
            }

            val intent = Intent(this@UserActivity, LoginActivity::class.java)
            startActivity(intent)
            ActivityCompat.finishAffinity(this@UserActivity)
        }
    }

    private fun showUserInfo() {
        val userId = sharedUserInfo.getLong("id", -1)

        launch(Dispatchers.Main) {
            val user = HttpRequestHelper(this@UserActivity)
                .get("api/users", UserDto::class.java) ?: return@launch

            binding.ratelimit.text = "${user?.currentUsedCount}/${user?.rateLimit}"
        }
    }
}