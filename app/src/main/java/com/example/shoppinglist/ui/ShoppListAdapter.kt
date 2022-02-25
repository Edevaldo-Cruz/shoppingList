package com.example.shoppinglist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ItemShoppBinding
import com.example.shoppinglist.model.Shopp


class ShoppListAdapter : ListAdapter<Shopp, ShoppListAdapter.ShoppViewHolder>(DifCallback()) {

    var listenerEdit : (Shopp) -> Unit = {}
    var listenerDelete : (Shopp) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemShoppBinding.inflate(inflater, parent, false)
        return ShoppViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ShoppViewHolder(
        private val binding: ItemShoppBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Shopp) {
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description
            binding.ivMenu.setOnClickListener {
                showPopup(item)
            }
        }
        private fun showPopup(item: Shopp) {
            val ivMenu = binding.ivMenu
            val popupMenu = PopupMenu(ivMenu.context, ivMenu)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_edit -> listenerEdit(item)
                    R.id.action_delete -> listenerDelete(item)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }

        }

    class DifCallback : DiffUtil.ItemCallback<Shopp>() {
        override fun areItemsTheSame(oldItem: Shopp, newItem: Shopp) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Shopp, newItem: Shopp) = oldItem.id == newItem.id
    }

}