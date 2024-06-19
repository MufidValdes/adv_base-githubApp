package com.example.submission3.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submission3.favorite.db.FavoriteUserRepository
import com.example.submission3.favorite.db.entity.FavoriteUser

class FavoriteUserViewModel(application: Application) : ViewModel() {
	private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

	fun getAllFavorites(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavorites()
}
class ViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
	companion object {
		@Volatile
		private var INSTANCE: ViewModelFactory? = null

		@JvmStatic
		fun getInstance(application: Application): ViewModelFactory {
			if (INSTANCE == null) {
				synchronized(ViewModelFactory::class.java) {
					INSTANCE = ViewModelFactory(application)
				}
			}
			return INSTANCE as ViewModelFactory
		}
	}

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
			return UserDetailViewModel(mApplication) as T
		} else if (modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)) {
			return FavoriteUserViewModel(mApplication) as T
		}
		throw IllegalArgumentException("Uknown ViewModel class: ${modelClass.name}")
	}
}