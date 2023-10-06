package knu.dong.capstone2

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import knu.dong.capstone2.common.HttpRequestHelper
import knu.dong.capstone2.common.ok
import knu.dong.capstone2.databinding.ActivitySignupBinding
import knu.dong.capstone2.dto.SendVerifyRequestDto
import knu.dong.capstone2.dto.SignupDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SignupActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        job = Job()

        checkAllInputConfirm()

        binding.btnVerifyCode.setOnClickListener {
            val email = binding.editEmail.text.toString()

            if (validateEmail(email)) {
                sendVerify(email)
            }
            else {
                Toast.makeText(this, "이메일 형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSignup.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val verifyCode = binding.editVerifyCode.text.toString()
            val password = binding.editPassword.text.toString()
            val passwordCofirm = binding.editPasswordConfirm.text.toString()

            if (!validateEmail(email)) {
                Toast.makeText(this@SignupActivity, "이메일 형식이 맞지 않습니다.", Toast.LENGTH_SHORT).show()
            }
            else if (!validateVerifyCode(verifyCode)) {
                Toast.makeText(this@SignupActivity, "인증 코드가 비어있습니다.", Toast.LENGTH_SHORT).show()
            }
            else if (!validatePassword(password)) {
                Toast.makeText(this@SignupActivity, "비밀번호가 비어있습니다.", Toast.LENGTH_SHORT).show()
            }
            else if (!validatePasswordConfirm(password, passwordCofirm)) {
                Toast.makeText(this@SignupActivity, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                signup(email, verifyCode.toInt(), password)
            }

        }

        binding.titleBar.btnBack.setOnClickListener{
            onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun signup(email: String, verifyCode: Int, password: String) {
        launch(Dispatchers.Main) {
            val res = HttpRequestHelper(this@SignupActivity).post("api/users"){
                contentType(ContentType.Application.Json)
                setBody(SignupDto(email, verifyCode, password))
            }

            if (res?.ok() == true) {
                Toast.makeText(this@SignupActivity, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
            else {
                Toast.makeText(this@SignupActivity, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendVerify(email: String) {
        launch(Dispatchers.Main) {
            val res = HttpRequestHelper(this@SignupActivity).post("api/users/sendVerifyEmail") {
                contentType(ContentType.Application.Json)
                setBody(SendVerifyRequestDto(email))
            }

            if (res?.ok() == true) {
                Toast.makeText(this@SignupActivity, "인증 코드를 전송했습니다.", Toast.LENGTH_SHORT).show()
                binding.btnVerifyCode.isEnabled = false
                binding.editVerifyCode.isEnabled = true
            }
            else {
                Toast.makeText(this@SignupActivity, "인증 코드 전송이 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
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
                val verifyCode = binding.editVerifyCode.text.toString()
                val password = binding.editPassword.text.toString()
                val confirmPassword = binding.editPasswordConfirm.text.toString()

                binding.btnSignup.isEnabled = validateEmail(email)
                        && validateVerifyCode(verifyCode)
                        && validatePassword(password)
                        && validatePasswordConfirm(password, confirmPassword)
            }
        }

        binding.editEmail.addTextChangedListener(textWatcher)
        binding.editVerifyCode.addTextChangedListener(textWatcher)
        binding.editPassword.addTextChangedListener(textWatcher)
        binding.editPasswordConfirm.addTextChangedListener(textWatcher)
    }

    private fun validateEmail(email: String) =
        email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun validateVerifyCode(code: String) = code.isNotBlank()

    private fun validatePassword(password: String) = password.isNotBlank()

    private fun validatePasswordConfirm(password: String, confirmPassword: String) =
        password == confirmPassword

}