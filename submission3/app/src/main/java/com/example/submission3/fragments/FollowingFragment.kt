package com.example.submission3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3.UserDetailActivity
import com.example.submission3.adapter.followsadapter.FollowingAdapter
import com.example.submission3.databinding.FragmentFollowerBinding
import com.example.submission3.models.DataUser
import com.example.submission3.viewmodels.FollowingViewModel


class FollowingFragment : Fragment() {
	private var _binding: FragmentFollowerBinding? = null
	private val binding get() = _binding
	private lateinit var followingViewModel: FollowingViewModel
	private val helper = UserDetailActivity.Hide()


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowingViewModel::class.java]
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): ConstraintLayout? {
		_binding = FragmentFollowerBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		followingViewModel.isLoading.observe(viewLifecycleOwner, {
			binding?.let { it1 -> helper.showLoading(it, it1.progressBar3) }
		})
		followingViewModel.listFollowing.observe(viewLifecycleOwner, { listFollowing ->
			setDataToFragment(listFollowing)
		})

		followingViewModel.getFollowing(arguments?.getString(UserDetailActivity.EXTRA_FRAGMENT).toString())
	}


	private fun setDataToFragment(listFollowing: List<DataUser>) {
		val listUser = ArrayList<DataUser>()
		with(binding) {
			for (user in listFollowing) {
				listUser.clear()
				listUser.addAll(listFollowing)
			}
			this?.lvFollower?.layoutManager = LinearLayoutManager(context)
			val adapter = FollowingAdapter(listFollowing)
			this?.lvFollower?.adapter = adapter
		}
	}


	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}