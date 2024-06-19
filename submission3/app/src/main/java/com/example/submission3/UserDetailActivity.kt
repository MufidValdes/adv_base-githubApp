package com.example.submission3

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submission3.adapter.SectionsPagerAdapter
import com.example.submission3.databinding.ActivityUserDetailBinding
import com.example.submission3.favorite.db.entity.FavoriteUser
import com.example.submission3.models.DetailResponse
import com.example.submission3.viewmodels.UserDetailViewModel
import com.example.submission3.viewmodels.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {
	private var _binding: ActivityUserDetailBinding? = null
	private val binding get() = _binding!!

	private var detailUser = DetailResponse()
	private var favoriteUser: FavoriteUser? = null

	private lateinit var userDetailViewModel: UserDetailViewModel

	private var buttonState: Boolean = false
	private val helper = Hide()


	companion object {
		const val EXTRA_USER = "extra_user"
		const val EXTRA_FRAGMENT = "extra_fragment"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityUserDetailBinding.inflate(layoutInflater)
		setContentView(binding.root)

		userDetailViewModel = obtainViewModel(this@UserDetailActivity)


		// Live data observe
		userDetailViewModel.isLoading.observe(this, {
			helper.showLoading(it, binding.progressBar2)
		})

		userDetailViewModel.status.observe(this) { status ->
			status?.let {
				Toast.makeText(this, status.toString(), Toast.LENGTH_SHORT).show()
			}
		}

		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		setTabLayoutView()
		// Check if user is favorited

		userDetailViewModel.listDetail.observe(this, { detailList ->
			detailUser = detailList
			setDataToView(detailUser)
			favoriteUser = FavoriteUser(detailUser.id, detailUser.login)
			userDetailViewModel.getAllFavorites().observe(this, { favoriteList ->
				if (favoriteList != null) {
					for (data in favoriteList) {
						if (detailUser.id == data.id) {
							buttonState = true
							binding.btnFavorite.setImageResource(R.drawable.ic_favorite_status)
						}
					}
				}
			})

			// Favorite event
			binding.btnFavorite.setOnClickListener {
				if (!buttonState) {
					buttonState = true
					binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
					insertToDatabase(detailUser)
				} else {
					buttonState = false
					binding.btnFavorite.setImageResource(R.drawable.ic_unfavorite)
					userDetailViewModel.delete(detailUser.id)
					helper.showToast(this, "Favorite user has been deleted.")
				}
			}
		})

	}



	@SuppressLint("StringFormatInvalid")
	private fun setDataToView(detailList: DetailResponse) {
		binding.apply {
			Glide.with(this@UserDetailActivity)
				.load(detailList.avatarUrl)
				.circleCrop()
				.into(detailsIvAvatar)
			detailsLvName.text = detailList.name ?: "No Name."
			detailsLvUsername.text = detailList.login
			detailsLvFollower.text = resources.getString(R.string.follower, detailList.followers)
			detailsLvCompany.text = detailList.company ?: "No company."
			detailsLvLocation.text = detailList.location ?: "No location."
			detailsLvBio.text =
				if (detailList.bio == null) "This person haven\'t set their bio yet."
				else detailList.bio.toString()
			detailsLvFollowing.text = resources.getString(R.string.following, detailList.following)
			detailsLvGist.text = resources.getString(R.string.gist, detailList.publicGists)
			detailsLvRepository.text = resources.getString(R.string.repository, detailList.publicRepos)


		}
		supportActionBar?.title = resources.getString(R.string.username_detail, detailList.login)
		binding.sharePic.setOnClickListener {
			val sendIntent: Intent = Intent().apply {
				action = Intent.ACTION_SEND
				putExtra(Intent.EXTRA_TEXT, "Follow @${detailList.name} on GitHub now!")
				type = "text/plain"
			}
			val shareIntent = Intent.createChooser(sendIntent, null)
			startActivity(shareIntent)

		}
	}
	override fun onSupportNavigateUp(): Boolean {
		finish()
		return true
	}


	private fun obtainViewModel(activity: AppCompatActivity): UserDetailViewModel {
		val factory = ViewModelFactory.getInstance(activity.application)
		return ViewModelProvider(activity, factory)[UserDetailViewModel::class.java]
	}

	private fun insertToDatabase(detailList: DetailResponse) {
		favoriteUser.let { favoriteUser ->
			favoriteUser?.id = detailList.id
			favoriteUser?.login = detailList.login
			favoriteUser?.htmlUrl = detailList.htmlUrl
			favoriteUser?.avatarUrl = detailList.avatarUrl
			userDetailViewModel.insert(favoriteUser as FavoriteUser)
			helper.showToast(this, "User has been favorited.")
		}
	}

	private fun setTabLayoutView() {
		val userIntent = intent.extras
		if (userIntent != null) {
			val userLogin = userIntent.getString(EXTRA_USER)
			if (userLogin != null) {
				userDetailViewModel.getGithubUser(userLogin)
				val login = Bundle()
				login.putString(EXTRA_FRAGMENT, userLogin)
				val sectionPagerAdapter = SectionsPagerAdapter(this, login)
				val viewPager: ViewPager2 = binding.viewPager

				viewPager.adapter = sectionPagerAdapter
				val tabs: TabLayout = binding.tabs
				val tabTitle = resources.getStringArray(R.array.tab_title)
				TabLayoutMediator(tabs, viewPager) { tab, position ->
					tab.text = tabTitle[position]
				}.attach()
			}
		}
	}
	class Hide {

		fun showLoading(isLoading: Boolean, view: View) {
			if (isLoading) {
				view.visibility = View.VISIBLE
			} else {
				view.visibility = View.INVISIBLE
			}
		}
		fun showToast(context: Context, message: String) {
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
		}
	}


	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}