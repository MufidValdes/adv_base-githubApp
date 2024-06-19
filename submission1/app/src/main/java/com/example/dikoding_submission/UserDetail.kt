package com.example.dikoding_submission

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dikoding_submission.databinding.UserDetailBinding



class UserDetail : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "0"
    }

    private lateinit var binding: UserDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listUser = intent.getParcelableExtra<UserData>(EXTRA_USER) as UserData
//		Using another binding.apply so we dont neet to write every binding again.

        binding.apply {
            listUser.avatar?.let { detailAvatar.setImageResource(it) }
            detailName.text = listUser.name
            detailUsername.text = listUser.username
            detailCompany.text = listUser.company
            detailLocation.text = listUser.location
            detailFollowers.text = getString(R.string.followers, listUser.followers)
            detailFollowing.text = getString(R.string.following, listUser.following)
            detailRepository.text = getString(R.string.repository, listUser.repository)
        }

        listUser.username?.let { setActionBarTitle(it) }

        binding.sharePic.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Follow @${listUser.username} on GitHub now!")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
}

