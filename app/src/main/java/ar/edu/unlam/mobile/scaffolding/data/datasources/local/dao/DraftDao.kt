package ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.DraftEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DraftDao {
    @Query("Select * from DraftEntity")
    fun getAllDraft(): Flow<List<DraftEntity>>

    @Insert
    suspend fun insertDraft(draft: DraftEntity)

    @Delete
    suspend fun deleteDraft(draft: DraftEntity)

    @Query("DELETE FROM DraftEntity WHERE id = :draftId")
    suspend fun deleteDraftById(draftId: Int)
}
