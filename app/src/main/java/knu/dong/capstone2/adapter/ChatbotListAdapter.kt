package knu.dong.capstone2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import knu.dong.capstone2.databinding.ListviewItemRoleBinding
import knu.dong.capstone2.dto.Chatbot

class ChatbotListAdapter(context: Context, private val data: List<Chatbot>): BaseAdapter() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int = data.size

    override fun getItem(position: Int): Chatbot = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = ListviewItemRoleBinding.inflate(layoutInflater)

        binding.itemId.text = data[position].name

        return binding.root
    }
}