package com.example.submission3.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission3.api.ApiClient
import com.example.submission3.models.DataUser
import com.example.submission3.models.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

	private val _listGithubUser = MutableLiveData<List<DataUser>>()
	val listDataUser: LiveData<List<DataUser>> = _listGithubUser

	private val _isLoading = MutableLiveData<Boolean>()
	val isLoading: LiveData<Boolean> = _isLoading

	private val _totalCount = MutableLiveData<Int>()
	val totalCount: LiveData<Int> = _totalCount

	private val _status = MutableLiveData<String>()
	val status: LiveData<String> = _status

	companion object {
		private const val TAG = "MainViewModel"
	}

	internal fun searchGithubUser(query: String) {
		_isLoading.value = true
		val client = ApiClient.getApiService().searchUser(query)
		client.enqueue(object : Callback<UserResponse> {
			override fun onResponse(
				call: Call<UserResponse>,
				response: Response<UserResponse>
			) {
				_isLoading.value = false
				if (response.isSuccessful) {
					val responseBody = response.body()
					if (responseBody != null) {
						_listGithubUser.value = response.body()?.dataUsers
						_totalCount.value = response.body()?.totalCount
					}
				} else {
					Log.e(TAG, "onFailure: ${response.message()}")
				}
			}

			override fun onFailure(call: Call<UserResponse>, eror: Throwable) {
				_isLoading.value = false
				Log.e(TAG, "onFailure: ${eror.message}")
			}
		})
	}


}