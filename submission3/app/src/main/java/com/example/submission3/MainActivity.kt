package com.example.submission3

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3.adapter.UserAdapter
import com.example.submission3.alarm.NotificationActivity
import com.example.submission3.databinding.ActivityMainBinding
import com.example.submission3.models.DataUser
import com.example.submission3.utils.settings.ThemeSettingsActivity
import com.example.submission3.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
	private var _binding: ActivityMainBinding? = null
	private val binding get() = _binding!!
	private val mainViewModel by viewModels<MainViewModel>()
	private var listGithubUser = ArrayList<DataUser>()
	private val helper = UserDetailActivity.Hide()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setTheme(R.style.Theme_Submission3)
		_binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		mainViewModel.listDataUser.observe(this) { listGithubUser ->
			setUserData(listGithubUser)
		}

		mainViewModel.isLoading.observe(this) {
			helper.showLoading(it, binding.progressBar)
		}

		mainViewModel.totalCount.observe(this, ::showText)
		mainViewModel.status.observe(this) { status ->
			status.let {
				Toast.makeText(this, status.toString(), Toast.LENGTH_SHORT).show()
			}
		}

		val layoutManager = LinearLayoutManager(this@MainActivity)
		binding.lvUser.layoutManager = layoutManager
	}


	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		val inflater = menuInflater
		inflater.inflate(R.menu.main_menu, menu)

		val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
		val searchView = menu.findItem(R.id.search).actionView as SearchView

		searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
		searchView.queryHint = resources.getString(R.string.search_hint)
		searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String?): Boolean {
				query?.let {
					binding.lvUser.visibility = View.VISIBLE
					mainViewModel.searchGithubUser(it)
					setUserData(listGithubUser)
				}
				hideKeyboard()
				return true
			}

			override fun onQueryTextChange(newText: String?): Boolean {
				return false
			}
		})
		return true
	}




	private fun setUserData(listDataUser: List<DataUser>) {
		val listUser = ArrayList<DataUser>()
		for (user in listDataUser) {
			listUser.clear()
			listUser.addAll(listDataUser)
		}
		val adapter = UserAdapter(listUser)
		binding.lvUser.adapter = adapter

		adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
			override fun onItemClicked(data: DataUser) {
				showSelectedUser(data)
			}
		})
	}

	private fun hideKeyboard() {
		val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		imm.hideSoftInputFromWindow(binding.lvUser.windowToken, 0)
	}
	private fun showSelectedUser(data: DataUser) {
		val intent = Intent(this@MainActivity, UserDetailActivity::class.java)
		intent.putExtra(UserDetailActivity.EXTRA_USER, data.login)
		startActivity(intent)
	}


	private fun showText(totalCount: Int) {
		with(binding) {
			if (totalCount == 0) {
				lvNotice.visibility = View.VISIBLE
				lvNotice2.visibility = View.VISIBLE
				lvNotice2.text = resources.getString(R.string.user_not_found)

			} else {
				lvNotice.visibility = View.INVISIBLE
				lvNotice2.visibility = View.INVISIBLE
			}
		}
	}
	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		selectOptionsMenu(item.itemId)
		return super.onOptionsItemSelected(item)
	}
	private fun selectOptionsMenu(selectedMenu: Int) {
		when(selectedMenu) {
			R.id.theme_setting ->{
				val intent = Intent(this@MainActivity, ThemeSettingsActivity::class.java)
				startActivity(intent)
			}
			R.id.favorites -> {
				val intent = Intent(this, FavoriteActivity::class.java)
				startActivity(intent)
			}
			R.id.action_change_notification -> {
				val mIntent = Intent(this, NotificationActivity::class.java)
				startActivity(mIntent)
			}
			R.id.action_language_settings -> {
				val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
				startActivity(mIntent)
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
			}
			R.id.action_close_app -> onBackPressed()

		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}