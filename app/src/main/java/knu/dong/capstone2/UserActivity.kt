package knu.dong.capstone2

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
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
    private lateinit var user: UserDto


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

        binding.btnPremium.setOnClickListener {
            togglePremium()
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

    private fun togglePremium() {
        launch(Dispatchers.Main) {
            if (user.isPremium) {
                HttpRequestHelper(this@UserActivity).delete("api/users/premium")
                binding.btnPremium.text = "프리미엄 구독하기"
                binding.btnPremium.setBackgroundColor(Color.GRAY)
            } else {
                HttpRequestHelper(this@UserActivity).post("api/users/premium")
                binding.btnPremium.text = "프리미엄"
                binding.btnPremium.setBackgroundColor(getColor(R.color.primary))
            }

            user = HttpRequestHelper(this@UserActivity)
                .get("api/users", UserDto::class.java) ?: return@launch

            binding.ratelimit.text = "${user?.currentUsedCount}/${user?.rateLimit}"
        }
    }

    private fun showUserInfo() {
        launch(Dispatchers.Main) {
            user = HttpRequestHelper(this@UserActivity)
                .get("api/users", UserDto::class.java) ?: return@launch

            binding.ratelimit.text = "${user?.currentUsedCount}/${user?.rateLimit}"
            if (user.isPremium) {
                binding.btnPremium.text = "프리미엄"
                binding.btnPremium.setBackgroundColor(getColor(R.color.primary))
            } else {
                binding.btnPremium.text = "프리미엄 구독하기"
                binding.btnPremium.setBackgroundColor(Color.GRAY)
            }
        }
    }
}