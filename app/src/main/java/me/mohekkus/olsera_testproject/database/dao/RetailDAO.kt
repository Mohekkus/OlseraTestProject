package me.mohekkus.olsera_testproject.database.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.mohekkus.olsera_testproject.database.entity.RetailEntity

@Dao
interface RetailDAO {

    @Query("SELECT * from Retail Order By id")
    fun getRetail(): PagingSource<Int, RetailEntity>

    @Query("SELECT * from Retail Order By id DESC LIMIT :limit OFFSET :offset")
    fun getRetail(limit: Int, offset: Int): List<RetailEntity>

    @Query("SELECT COUNT(*) FROM Retail")
    fun countEntries(): Int

    @Query("SELECT COUNT(status) FROM Retail")
    fun countInactive(): Int

    @Query("SELECT * from Retail where id= :itemID")
    fun getRetailItem(itemID: Int): RetailEntity

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun createRetail(retailEntity: RetailEntity)

    @Update
    fun updateRetail(entity: RetailEntity)

    @Query("DELETE from Retail where id = :itemID")
    fun deleteRetail(itemID: Int)



    @Query("DELETE from Retail")
    fun deleteAll()
}