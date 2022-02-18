package com.zialab.searchgithub.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zialab.domain.entities.UserUI
import com.zialab.searchgithub.databinding.ItemUserLayoutBinding

class SearchUserAdapter(private val context: Context, val items: ArrayList<UserUI>) :
    ListAdapter<UserUI, RecyclerView.ViewHolder>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolderItem(
            binding =
            ItemUserLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as ViewHolderItem).bind(position)
    }

    inner class ViewHolderItem internal constructor(val binding: ItemUserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val user = items[position]

            Picasso.get()
                .load(user.avatarUrl)
                .fit()
//                .placeholder(errorPlaceHolder)
//                .error(errorPlaceHolder)
                .into(binding.imageView2)

            binding.textView.text = user.username
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

private class UserDiffCallBack : DiffUtil.ItemCallback<UserUI>() {
    override fun areItemsTheSame(
        oldItem: UserUI,
        newItem: UserUI
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: UserUI,
        newItem: UserUI
    ): Boolean =
        oldItem == newItem
}