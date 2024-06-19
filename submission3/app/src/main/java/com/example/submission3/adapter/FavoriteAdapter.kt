package com.example.submission3.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission3.UserDetailActivity
import com.example.submission3.databinding.ItemRowUserBinding
import com.example.submission3.favorite.db.FavoriteDiffCallback
import com.example.submission3.favorite.db.entity.FavoriteUser
import java.util.*

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteUserViewHolder>() {
    private val listFavorites = ArrayList<FavoriteUser>()

    fun setFavorites(favorites: List<FavoriteUser>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorites, favorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorites.clear()
        this.listFavorites.addAll(favorites)
        diffResult.dispatchUpdatesTo(this)
    }

    class FavoriteUserViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorites: FavoriteUser) {
            with(binding) {
                lvName.text = favorites.login
                lvUrl.text = favorites.htmlUrl
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, UserDetailActivity::class.java)
                    intent.putExtra(UserDetailActivity.EXTRA_USER, favorites.login)
                    itemView.context.startActivity(intent)
                }
            }
            Glide.with(itemView.context)
                .load(favorites.avatarUrl)
                .circleCrop()
                .into(binding.imgUserAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val itemRowUserBinding =
            ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(itemRowUserBinding)
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
            val favorites = listFavorites[position]
            holder.bind(favorites)

    }

    override fun getItemCount(): Int {
        return listFavorites.size
    }
}