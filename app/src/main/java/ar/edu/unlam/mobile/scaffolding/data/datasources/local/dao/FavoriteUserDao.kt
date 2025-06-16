package ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.FavoriteUser
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteUserDao {
    @Query("Select * from FavoriteUser")
    fun getAllFavoriteUsers(): Flow<List<FavoriteUser>>

    @Insert
    suspend fun insertFavoriteUser(user: FavoriteUser)

    @Delete
    suspend fun deleteFavoriteUser(user: FavoriteUser)

    @Query("Select * from FavoriteUser where id = :favoriteUserId")
    suspend fun getFavoriteUser(favoriteUserId: Int): FavoriteUser?
}
