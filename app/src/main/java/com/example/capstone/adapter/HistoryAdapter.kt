package com.example.capstone.adapter

import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.databinding.ItemHistoryAccidentBinding
import com.example.capstone.databinding.ItemListAccidentBinding
import com.example.capstone.model.AccidentDetail

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

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

    class ViewHolder private constructor(private val binding: ItemHistoryAccidentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding =
                    ItemHistoryAccidentBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )

                return ViewHolder(binding)
            }
        }

        fun bind(item: AccidentDetail, onItemClick: IOnItemClick) {
            binding.itemCoord.text = binding.root.context.getString(
                R.string.coordinate,
                item.coordinate.latitude,
                item.coordinate.longitude
            )
            binding.itemTitle.text = item.user

            try {
                Glide.with(binding.root.context)
                    .asBitmap()
                    .load(Base64.decode(item.photo, Base64.DEFAULT))
                    .into(binding.itemImage)
            } catch (e: Exception) {

            }

            binding.root.setOnClickListener {
                onItemClick.onItemClick(item)
            }
        }
    }

    interface IOnItemClick {
        fun onItemClick(data: AccidentDetail)
    }
}
