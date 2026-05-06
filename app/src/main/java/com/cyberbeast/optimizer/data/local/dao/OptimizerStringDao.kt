package com.cyberbeast.optimizer.data.local.dao

import androidx.room.*
import com.cyberbeast.optimizer.data.local.entity.OptimizerStringEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OptimizerStringDao {
    @Query("SELECT * FROM optimizer_strings ORDER BY popularity DESC, name ASC")
    fun getAllStrings(): Flow<List<OptimizerStringEntity>>

    @Query("SELECT * FROM optimizer_strings WHERE category = :category ORDER BY popularity DESC")
    fun getStringsByCategory(category: String): Flow<List<OptimizerStringEntity>>

    @Query("SELECT * FROM optimizer_strings WHERE name LIKE '%' || :query || '%' OR key LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchStrings(query: String): Flow<List<OptimizerStringEntity>>

    @Query("SELECT * FROM optimizer_strings WHERE is_favorite = 1 ORDER BY name ASC")
    fun getFavoriteStrings(): Flow<List<OptimizerStringEntity>>

    @Query("SELECT * FROM optimizer_strings WHERE is_custom = 1 ORDER BY created_at DESC")
    fun getCustomStrings(): Flow<List<OptimizerStringEntity>>

    @Query("SELECT * FROM optimizer_strings WHERE id = :id")
    suspend fun getStringById(id: Long): OptimizerStringEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertString(string: OptimizerStringEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(strings: List<OptimizerStringEntity>)

    @Update
    suspend fun updateString(string: OptimizerStringEntity)

    @Delete
    suspend fun deleteString(string: OptimizerStringEntity)

    @Query("DELETE FROM optimizer_strings WHERE is_custom = 1")
    suspend fun deleteAllCustomStrings()

    @Query("UPDATE optimizer_strings SET is_favorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Long, isFavorite: Boolean)

    @Query("UPDATE optimizer_strings SET popularity = popularity + 1 WHERE id = :id")
    suspend fun incrementPopularity(id: Long)

    @Query("SELECT COUNT(*) FROM optimizer_strings")
    suspend fun getCount(): Int

    @Query("SELECT DISTINCT category FROM optimizer_strings ORDER BY category ASC")
    fun getAllCategories(): Flow<List<String>>
}
