package com.example.submission3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission3.databinding.ItemRowUserBinding
import com.example.submission3.models.DataUser

class UserAdapter(private val listUser: List<DataUser>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
	private lateinit var onItemClickCallback: OnItemClickCallback

	class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val user = listUser[position]

		with(holder.binding) {
			Glide.with(root.context)
				.load(user.avatarUrl)
				.circleCrop()
				.into(imgUserAvatar)
			lvName.text = user.login
			lvUrl.text = user.htmlUrl
		root.setOnClickListener { onItemClickCallback.onItemClicked(listUser[position]) }
		}
	}

	override fun getItemCount(): Int = listUser.size

	fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
		this.onItemClickCallback = onItemClickCallback
	}

	interface OnItemClickCallback {
		fun onItemClicked(data: DataUser)
	}
}