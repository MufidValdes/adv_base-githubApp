package com.example.submission2

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submission2.adapter.SectionsPagerAdapter
import com.example.submission2.databinding.ActivityUserDetailBinding
import com.example.submission2.models.DataUser
import com.example.submission2.models.DetailResponse
import com.example.submission2.viewmodels.UserDetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {
	private var _binding: ActivityUserDetailBinding? = null
	private val binding get() = _binding!!
	private val userDetailViewModel by viewModels<UserDetailViewModel>()
	private val helper = Hide()

	companion object {
		const val EXTRA_USER = "extra_user"
		const val EXTRA_FRAGMENT = "extra_fragment"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityUserDetailBinding.inflate(layoutInflater)
		setContentView(binding.root)

		userDetailViewModel.listDetail.observe(this, { detailList ->
			setDataToView(detailList)
		})

		userDetailViewModel.isLoading.observe(this, {
			helper.showLoading(it, binding.progressBar2)
		})

		setTabLayoutView()
	}


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
			detailsLvBio.text = if (detailList.bio == null) "This person haven\'t set their bio yet." else detailList.bio.toString()
			detailsLvFollowing.text = resources.getString(R.string.following, detailList.following)
			detailsLvGist.text = resources.getString(R.string.gist, detailList.publicGists)
			detailsLvRepository.text = resources.getString(R.string.repository, detailList.publicRepos)


		}
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

	private fun setTabLayoutView() {
		val userIntent = intent.getParcelableExtra<DataUser>(EXTRA_USER) as DataUser
		userDetailViewModel.getGithubUser(userIntent.login)

		val login = Bundle()
		login.putString(EXTRA_FRAGMENT, userIntent.login)

		val sectionPagerAdapter = SectionsPagerAdapter(this, login)
		val viewPager: ViewPager2 = binding.viewPager

		viewPager.adapter = sectionPagerAdapter
		val tabs: TabLayout = binding.tabs
		val tabTitle = resources.getStringArray(R.array.tab_title)
		TabLayoutMediator(tabs, viewPager) { tab, position ->
			tab.text = tabTitle[position]
		}.attach()
	}

	class Hide {

		fun showLoading(isLoading: Boolean, view: View) {
			if (isLoading) {
				view.visibility = View.VISIBLE
			} else {
				view.visibility = View.INVISIBLE
			}
		}
	}


	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}