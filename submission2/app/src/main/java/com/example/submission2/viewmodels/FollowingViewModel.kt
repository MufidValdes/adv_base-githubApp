package com.example.submission2.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.api.ApiClient
import com.example.submission2.models.DataUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
	private val _listFollowing = MutableLiveData<List<DataUser>>()
	val listFollowing: LiveData<List<DataUser>> = _listFollowing

	private val _isLoading = MutableLiveData<Boolean>()
	val isLoading: LiveData<Boolean> = _isLoading

	companion object {
		private const val TAG = "FollowerViewModel"
	}

	internal fun getFollowing(username: String) {
		_isLoading.value = true
		val client = ApiClient.getApiService().getUserFollowings(username)
		client.enqueue(object : Callback<List<DataUser>> {
			override fun onResponse(
				call: Call<List<DataUser>>,
				response: Response<List<DataUser>>
			) {
				_isLoading.value = false
				if (response.isSuccessful) {
					val responseBody = response.body()
					if (responseBody != null) {
						_listFollowing.value = response.body()
					}
					else {
						Log.e(TAG, "onFailure: ${response.message()}")
					}
				}
			}

			override fun onFailure(call: Call<List<DataUser>>, eror: Throwable) {
				_isLoading.value = false
				Log.e(TAG, "onFailure: ${eror.message}")
			}
		})
	}
}
