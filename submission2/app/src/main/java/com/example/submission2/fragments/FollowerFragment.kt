package com.example.submission2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.UserDetailActivity
import com.example.submission2.adapter.FollowerAdapter
import com.example.submission2.databinding.FragmentFollowerBinding
import com.example.submission2.models.DataUser
import com.example.submission2.viewmodels.FollowerViewModel


class FollowerFragment : Fragment() {
	private var _binding: FragmentFollowerBinding? = null
	private val binding get() = _binding!!
	private lateinit var followerViewModel: FollowerViewModel
	private val helper = Hide()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
			FollowerViewModel::class.java)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentFollowerBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		followerViewModel.isLoading.observe(viewLifecycleOwner, {
			helper.showLoading(it, binding.progressBar3)
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
			lvFollower.layoutManager = LinearLayoutManager(context)
			val adapter = FollowerAdapter(listFollower)
			lvFollower.adapter = adapter
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
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}

}