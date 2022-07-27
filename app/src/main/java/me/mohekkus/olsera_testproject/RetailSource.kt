package me.mohekkus.olsera_testproject

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.mohekkus.olsera_testproject.database.RetailDatabase
import me.mohekkus.olsera_testproject.database.dao.RetailDAO
import me.mohekkus.olsera_testproject.database.entity.RetailEntity
import me.mohekkus.olsera_testproject.database.repository.RetailRepository

class RetailSource(
    val backend: RetailDAO
) : PagingSource<Int, RetailEntity>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, RetailEntity> {
        val page = params.key ?: 1

        return try {
            val entities = backend.getRetail(params.loadSize, page * params.loadSize)

            Log.e("wtf", entities.size.toString())
            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RetailEntity>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
