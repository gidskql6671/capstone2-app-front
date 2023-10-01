package knu.dong.capstone2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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
    private val chats = mutableListOf<Chat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        job = Job()

        val role = intent.getStringExtra("role")
        if (role == null) {
            finish()
            return
        }

        initRecyclerView()
        binding.titleBar.btnBack.setOnClickListener{
            onBackPressed()
        }

        getChatbotChats(role)
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

    private fun getChatbotChats(role: String) {
        launch(Dispatchers.Main) {
            val resChats: List<Chat> =
                HttpRequestHelper()
                    .get<List<Chat>>("api/chatbots/chats")
                    ?: emptyList()

            chats.addAll(resChats)
            binding.recyclerView.adapter?.notifyItemRangeInserted(0, chats.size)
        }
    }
}