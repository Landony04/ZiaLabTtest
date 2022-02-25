package com.zialab.searchgithub.ui.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zialab.domain.entities.UserUI
import com.zialab.searchgithub.databinding.ItemUserLayoutBinding
import java.util.stream.IntStream

class SearchUserAdapter(
    private val context: Context,
    val items: ArrayList<UserUI>,
    private val onCallInfoUser: (user: String) -> Unit
) :
    ListAdapter<UserUI, SearchUserAdapter.ViewHolderItem>(UserDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        return ViewHolderItem(
            binding =
            ItemUserLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        holder.bind(position)
    }

    inner class ViewHolderItem internal constructor(val binding: ItemUserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val user = items[position]
            Picasso.get()
                .load(user.avatarUrl)
                .fit()
                .into(binding.imageView2)

            binding.textView.text = user.username

            if (user.numberRepos == null && !user.isCall) {
                user.isCall = true
                onCallInfoUser(user.username)
            } else {
                binding.progressBarItem.visibility = View.GONE
                binding.numberRepos.visibility = View.VISIBLE
                val textRepos =
                    if (user.numberRepos == null) "without repositories" else "${user.numberRepos} repositories"
                binding.numberRepos.text = textRepos
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun updateItem(userUI: UserUI) {
        val index =
            IntStream.range(0, items.size).filter { i -> items[i].id == userUI.id }
                .findFirst().orElse(0)
        items[index] = userUI
        notifyItemChanged(index)
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
        oldItem.id == newItem.id
}