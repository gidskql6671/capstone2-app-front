package knu.dong.capstone2

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import knu.dong.capstone2.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        job = Job()

        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)

            startActivity(intent)
        }

        binding.titleBar.btnBack.visibility = View.INVISIBLE

        binding.btnLogin.setOnClickListener {

        }

        checkAllInputConfirm()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
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
                val passwordPass = password.isNotBlank()

                binding.btnLogin.isEnabled = emailPass && passwordPass
            }
        }

        binding.editEmail.addTextChangedListener(textWatcher)
        binding.editPassword.addTextChangedListener(textWatcher)
    }

    private fun login() {
        launch(Dispatchers.Main) {
//            val res = HttpRequestHelper()
//                .get("api/users/login", GetChatbotsDto::class.java)
//                ?: GetChatbotsDto()
        }
    }
}