package com.example.submission3.favorite.db

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import com.example.submission3.favorite.db.entity.FavoriteUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
	private val mFavoriteUserDao: FavoriteUserDao
	private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

	init {
		val db = FavoriteUserRoomDatabase.getDatabase(application)
		mFavoriteUserDao = db.favoriteUserDao()
	}

	fun getAllFavorites(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllUser()

	fun insert(user: FavoriteUser) {
		executorService.execute { mFavoriteUserDao.insertFavorite(user) }
	}

	fun delete(id: Int) {
		executorService.execute { mFavoriteUserDao.removeFavorite(id) }
	}
}

class FavoriteDiffCallback(private val mOldFavList: List<FavoriteUser>, private val mNewFavList: List<FavoriteUser>):
	DiffUtil.Callback() {
	override fun getOldListSize(): Int {
		return mOldFavList.size
	}

	override fun getNewListSize(): Int {
		return mNewFavList.size
	}

	override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		return mOldFavList[oldItemPosition].id == mNewFavList[newItemPosition].id
	}

	override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
		val oldFavList = mOldFavList[oldItemPosition]
		val newFavList = mNewFavList[newItemPosition]
		return oldFavList.login == newFavList.login && oldFavList.htmlUrl == newFavList.htmlUrl && oldFavList.avatarUrl == newFavList.avatarUrl
	}


}