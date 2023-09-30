package knu.dong.capstone2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import knu.dong.capstone2.databinding.ComponentListviewBinding

class RolesAdapter(
    private val context: Context,
    private val data: List<String>
): BaseAdapter() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int = data.size

    override fun getItem(position: Int): String = data[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = ComponentListviewBinding.inflate(layoutInflater)

        binding.itemId.text = data[position]

        return binding.root
    }
}