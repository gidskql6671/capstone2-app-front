package knu.dong.capstone2.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import knu.dong.capstone2.databinding.ListviewItemChatBinding
import knu.dong.capstone2.dto.Chat

class ChatsAdapter(
    private val context: Context,
    private val data: List<Chat>
): BaseAdapter() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int = data.size

    override fun getItem(position: Int): Chat = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = ListviewItemChatBinding.inflate(layoutInflater)

        binding.message.text = data[position].content
        binding.messageContainer.gravity =
            if (data[position].isUser) { Gravity.START } else { Gravity.END }

        return binding.root
    }
}