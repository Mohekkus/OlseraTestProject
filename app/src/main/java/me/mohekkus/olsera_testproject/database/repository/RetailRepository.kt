package me.mohekkus.olsera_testproject.database.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import me.mohekkus.olsera_testproject.database.dao.RetailDAO
import me.mohekkus.olsera_testproject.database.entity.RetailEntity
import javax.inject.Inject


class RetailRepository @Inject constructor(private val dao: RetailDAO) {

//    val get: Flow<List<RetailEntity>> = dao.getRetail()

    fun get() = dao.getRetail()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(retailEntity: RetailEntity) {
        dao.createRetail(retailEntity)
    }

    fun getCount(): Int =
        dao.countEntries()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteId(id: Int) {
        dao.deleteRetail(id)
    }

}