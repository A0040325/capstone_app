package com.example.capstone

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.databinding.ItemListAccidentBinding
import com.example.capstone.model.AccidentDetail

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    var data = ArrayList<AccidentDetail>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private lateinit var onItemClick: IOnItemClick

    fun setItemClick(onItemClick: IOnItemClick) {
        this.onItemClick = onItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], onItemClick)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder private constructor(private val binding: ItemListAccidentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding =
                    ItemListAccidentBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )

                return ViewHolder(binding)
            }
        }

        fun bind(item: AccidentDetail, onItemClick: IOnItemClick) {
            binding.itemCoord.text =
                "${item.coordinate.latitude}, ${item.coordinate.longitude}"
            binding.itemTitle.text = item.user
        }
    }

    interface IOnItemClick {
        fun onItemClick(data: AccidentDetail)
    }
}