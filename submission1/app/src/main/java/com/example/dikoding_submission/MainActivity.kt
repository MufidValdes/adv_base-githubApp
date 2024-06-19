package com.example.dikoding_submission

import android.content.Intent
import android.content.res.Configuration
import android.icu.text.Transliterator
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dikoding_submission.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var users = ArrayList<UserData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lvList.setHasFixedSize(true)
        users.addAll(addItem)
        showRecyclerList()
    }

    private fun showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.lvList.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.lvList.layoutManager = LinearLayoutManager(this)
        }

        val adapter = UserAdapter(users)
        binding.lvList.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserData) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: UserData) {

        val moveWithParcelableIntent = Intent(this@MainActivity, UserDetail::class.java)
        moveWithParcelableIntent.putExtra(UserDetail.EXTRA_USER, user)


        startActivity(moveWithParcelableIntent)
    }

    private val addItem: ArrayList<UserData>
        get() {
            val dataName = resources.getStringArray(R.array.name)
            val dataUsername = resources.getStringArray(R.array.username)
            val dataLocation = resources.getStringArray(R.array.location)
            val dataRepository = resources.getStringArray(R.array.repository)
            val dataCompany = resources.getStringArray(R.array.company)
            val dataFollowers = resources.getStringArray(R.array.followers)
            val dataFollowing = resources.getStringArray(R.array.following)
            val dataAvatar = resources.obtainTypedArray(R.array.avatar)
            val users = ArrayList<UserData>()
            for (i in dataName.indices) {
                val user = UserData()
                user.avatar = dataAvatar.getResourceId(i, -1)
                user.username = dataUsername[i]
                user.name = dataName[i]
                user.company = dataCompany[i]
                user.location = dataLocation[i]
                user.repository = dataRepository[i]
                user.followers = dataFollowers[i]
                user.following = dataFollowing[i]
                users.add(user)
            }
            dataAvatar.recycle()
            return users
        }
}