package com.example.dikoding_submission

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dikoding_submission.databinding.UserItemBinding

class UserAdapter(private val listUser: ArrayList<UserData>) : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: UserData)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    @Suppress("DEPRECATION")
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]


//		Use holder.binding.apply to apply all the binding to the view so we dont need to write every single holder.binding again.
        holder.binding.apply {
            Glide.with(root.context)
                .load(user.avatar)
                .circleCrop()
                .into(imgAvatar)
            txtName.text = user.name
            txtCompany.text = user.company
            detailFollowers.text = "${user.followers} followers"
            detailRepository.text = "${user.repository} repositories"

            root.setOnClickListener {Toast.makeText(
                holder.itemView.context,
                " " + listUser[holder.adapterPosition].name,
                Toast.LENGTH_SHORT
            ).show()
                onItemClickCallback.onItemClicked(listUser[position]) }
        }

    }

    override fun getItemCount(): Int = listUser.size
}