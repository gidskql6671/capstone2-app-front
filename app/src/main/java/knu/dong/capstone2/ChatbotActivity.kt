package knu.dong.capstone2

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import knu.dong.capstone2.adapter.ChatsAdapter
import knu.dong.capstone2.common.HttpRequestHelper
import knu.dong.capstone2.common.getSerializable
import knu.dong.capstone2.databinding.ActivityChatbotBinding
import knu.dong.capstone2.dto.Chat
import knu.dong.capstone2.dto.Chatbot
import knu.dong.capstone2.dto.GetChatsDto
import knu.dong.capstone2.dto.SendChatReqDto
import knu.dong.capstone2.dto.SendChatResDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.Serializable
import kotlin.coroutines.CoroutineContext


class ChatbotActivity: AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private lateinit var binding: ActivityChatbotBinding
    private val chats = mutableListOf<Chat>()


    inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        job = Job()

        val chatbot = intent.getSerializable("chatbot", Chatbot::class.java)
        if (chatbot == null) {
            finish()
            return
        }

        initRecyclerView()
        binding.titleBar.btnBack.setOnClickListener{
            onBackPressed()
        }
        binding.btnSend.setOnClickListener {
            val message = binding.editQuestion.text.toString()
            if (message.isBlank()) {
                return@setOnClickListener
            }
            binding.editQuestion.setText("")
            val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(
                currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )


            chats.add(Chat(message, true))
            binding.recyclerView.adapter?.notifyItemInserted(chats.size - 1)
            sendChat(chatbot, message)
        }

        getChatbotChats(chatbot)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun initRecyclerView() {
        binding.recyclerView.also {
            it.adapter = ChatsAdapter(chats)
            it.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun getChatbotChats(chatbot: Chatbot) {
        launch(Dispatchers.Main) {
            val resChats: List<Chat> = emptyList()
                HttpRequestHelper()
                    .get("api/chatbots/chats", GetChatsDto::class.java)
                    ?: GetChatsDto()

            chats.addAll(resChats)
            binding.recyclerView.adapter?.notifyItemRangeInserted(0, chats.size)
        }
    }

    private fun sendChat(chatbot: Chatbot, message: String) {
        launch(Dispatchers.Main) {
            val resChat = HttpRequestHelper().post("api/chatbots/chats", SendChatResDto::class.java) {
                contentType(ContentType.Application.Json)
                setBody(SendChatReqDto(chatbot.id, message))
            }

            chats.add(Chat(resChat?.reply ?: "다시 시도해주세요.", false))
            binding.recyclerView.adapter?.notifyItemInserted(chats.size - 1)
        }
    }
}