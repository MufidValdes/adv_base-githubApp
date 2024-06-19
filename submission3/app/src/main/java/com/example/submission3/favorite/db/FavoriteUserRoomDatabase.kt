package com.example.submission3.favorite.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submission3.favorite.db.entity.FavoriteUser

@Database(entities = [FavoriteUser::class], version = 5)
abstract class FavoriteUserRoomDatabase : RoomDatabase() {
	abstract fun favoriteUserDao(): FavoriteUserDao

	companion object {
		@Volatile
		private var INSTANCE: FavoriteUserRoomDatabase? = null

		@JvmStatic
		fun getDatabase(context: Context): FavoriteUserRoomDatabase {
			if (INSTANCE == null) {
				synchronized(FavoriteUserRoomDatabase::class.java) {
					INSTANCE = Room.databaseBuilder(
						context.applicationContext,
						FavoriteUserRoomDatabase::class.java, "favorite_user_database"
					)
						.fallbackToDestructiveMigration()
						.build()
				}
			}
			return INSTANCE as FavoriteUserRoomDatabase
		}
	}
}