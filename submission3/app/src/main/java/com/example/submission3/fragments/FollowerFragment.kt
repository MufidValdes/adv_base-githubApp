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
import com.example.submission3.adapter.followsadapter.FollowerAdapter
import com.example.submission3.databinding.FragmentFollowerBinding
import com.example.submission3.models.DataUser
import com.example.submission3.viewmodels.FollowerViewModel


class FollowerFragment : Fragment() {
	private var _binding: FragmentFollowerBinding? = null
	private val binding get() = _binding
	private lateinit var followerViewModel: FollowerViewModel
	private val helper = UserDetailActivity.Hide()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowerViewModel::class.java]
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

		followerViewModel.isLoading.observe(viewLifecycleOwner, {
			binding?.let { it1 -> helper.showLoading(it, it1.progressBar3) }
		})
		followerViewModel.listFollower.observe(viewLifecycleOwner, { listFollower ->
			setDataToFragment(listFollower)
		})

		followerViewModel.getFollower(arguments?.getString(UserDetailActivity.EXTRA_FRAGMENT).toString())
	}

	private fun setDataToFragment(listFollower: List<DataUser>) {
		val listUser = ArrayList<DataUser>()
		with(binding) {
			for (user in listFollower) {
				listUser.clear()
				listUser.addAll(listFollower)
			}
			this?.lvFollower?.layoutManager = LinearLayoutManager(context)
			val adapter = FollowerAdapter(listFollower)
			this?.lvFollower?.adapter = adapter
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

}