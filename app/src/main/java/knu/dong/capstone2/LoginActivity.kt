package knu.dong.capstone2

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import knu.dong.capstone2.common.HttpRequestHelper
import knu.dong.capstone2.databinding.ActivityLoginBinding
import knu.dong.capstone2.dto.LoginDto
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
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if (!validateEmail(email)) {
                Toast.makeText(this, "이메일 형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show()
            }
            else if (!validatePassword(password)) {
                Toast.makeText(this, "비밀번호 입력창이 비어있습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                login(email, password)
            }
        }

        initTextWatcher()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun initTextWatcher() {
        val textWatcher = object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val email = binding.editEmail.text.toString()
                val password = binding.editPassword.text.toString()

                binding.btnLogin.isEnabled = validateEmail(email) && validatePassword(password)
            }
        }

        binding.editEmail.addTextChangedListener(textWatcher)
        binding.editPassword.addTextChangedListener(textWatcher)
    }

    private fun validateEmail(email: String): Boolean =
        email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun validatePassword(password: String): Boolean = password.isNotBlank()

    private fun login(email: String, password: String) {
        launch(Dispatchers.Main) {
            val res = HttpRequestHelper().post("api/users/login"){
                contentType(ContentType.Application.Json)
                setBody(LoginDto(email, password))
            }

            if (res?.status?.value?.div(100) == 2) {
                val intent = Intent(this@LoginActivity, SelectChatbotActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                Toast.makeText(this@LoginActivity, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}