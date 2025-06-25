package ar.edu.unlam.mobile.scaffolding.data.datasources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.DraftDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.FavoriteUserDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.DraftEntity
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.FavoriteUser

@Database(entities = [FavoriteUser::class, DraftEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao

    abstract fun draftDao(): DraftDao
}
