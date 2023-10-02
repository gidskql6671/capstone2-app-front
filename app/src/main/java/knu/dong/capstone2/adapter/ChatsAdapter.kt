package knu.dong.capstone2.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import knu.dong.capstone2.common.dp
import knu.dong.capstone2.databinding.ListviewItemChatBinding
import knu.dong.capstone2.dto.Chat

class ChatsAdapter(
    val datas: MutableList<Chat> = mutableListOf()
) : RecyclerView.Adapter<ChatsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListviewItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(private val binding: ListviewItemChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.message.text = chat.message

            if (chat.isUserMessage) {
                binding.messageContainer.apply {
                    gravity = Gravity.END
                    setPaddingRelative(60.dp, 10.dp, 10.dp, 10.dp)
                }

            } else {
                binding.messageContainer.apply {
                    gravity = Gravity.START
                    setPaddingRelative(10.dp, 10.dp, 60.dp, 10.dp)
                }
            }

        }
    }
}