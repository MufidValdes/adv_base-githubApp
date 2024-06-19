package com.example.submission2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.submission2.databinding.ItemRowUserBinding
import com.example.submission2.models.DataUser

class FollowingAdapter(private val listFollowing: List<DataUser>) : RecyclerView.Adapter<FollowingAdapter.ViewHolder>(){
	class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val following = listFollowing[position]

		with(holder.binding) {
			com.bumptech.glide.Glide.with(root.context)
				.load(following.avatarUrl)
				.circleCrop()
				.into(imgUserAvatar)
			lvName.text = following.login
			lvUrl.text = following.htmlUrl
		}
	}

	override fun getItemCount(): Int = listFollowing.size
}