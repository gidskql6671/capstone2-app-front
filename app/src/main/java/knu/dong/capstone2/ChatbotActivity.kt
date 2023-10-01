package knu.dong.capstone2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import knu.dong.capstone2.adapter.ChatsAdapter
import knu.dong.capstone2.common.HttpRequestHelper
import knu.dong.capstone2.databinding.ActivityChatbotBinding
import knu.dong.capstone2.dto.Chat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class ChatbotActivity: AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private lateinit var binding: ActivityChatbotBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        job = Job()

        binding.titleBar.btnBack.setOnClickListener{
            onBackPressed()
        }

        val role = intent.getStringExtra("role")
        if (role == null) {
            finish()
            return
        }

        getChatbotChats(role)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun getChatbotChats(role: String) {
        launch(Dispatchers.Main) {
            val chats: List<Chat> =
                HttpRequestHelper()
                    .get<List<Chat>>("api/chatbots/chats")
                    ?: emptyList()


            val adapter = ChatsAdapter(this@ChatbotActivity, chats)
            binding.listView.adapter = adapter

            binding.listView.setSelection(adapter.count - 1);
        }
    }
}