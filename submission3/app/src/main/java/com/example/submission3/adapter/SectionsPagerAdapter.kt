package com.example.submission3.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submission3.fragments.FollowerFragment
import com.example.submission3.fragments.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val login: Bundle) : FragmentStateAdapter(activity) {
	override fun getItemCount(): Int {
		return 2
	}

	override fun createFragment(position: Int): Fragment {
		var fragment: Fragment? = null
		when (position) {
			0 -> fragment = FollowerFragment()
			1 -> fragment = FollowingFragment()
		}
		fragment?.arguments = login
		return fragment as Fragment
	}
}