package com.example.submission3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3.adapter.FavoriteAdapter
import com.example.submission3.databinding.ActivityFavoriteBinding
import com.example.submission3.viewmodels.FavoriteUserViewModel
import com.example.submission3.viewmodels.ViewModelFactory

class FavoriteActivity :  AppCompatActivity() {

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: FavoriteAdapter

    private lateinit var favoriteUserViewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.title = getString(R.string.favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        favoriteUserViewModel = obtainViewModel(this@FavoriteActivity)
        favoriteUserViewModel.getAllFavorites().observe(this, { favoriteList ->
            if (favoriteList != null) {
                adapter.setFavorites(favoriteList)
            }
        })
        adapter = FavoriteAdapter()
        binding?.lvFavorite?.layoutManager = LinearLayoutManager(this)
        binding?.lvFavorite?.setHasFixedSize(false)
        binding?.lvFavorite?.adapter = adapter
    }


    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteUserViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}