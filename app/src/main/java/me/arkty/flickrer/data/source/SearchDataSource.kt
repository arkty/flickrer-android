package me.arkty.flickrer.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.arkty.flickrer.data.remote.FlickrApi
import me.arkty.flickrer.data.remote.data.PhotoEntity
import java.lang.Exception

class SearchDataSource(
    private val api: FlickrApi,
    private val query: String
) : PagingSource<Int, PhotoEntity>() {

    override fun getRefreshKey(state: PagingState<Int, PhotoEntity>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoEntity> {
        return try {
            val page = params.key ?: 0

            LoadResult.Page(
                api.search(query).photo, page, page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}