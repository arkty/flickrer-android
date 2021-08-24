package me.arkty.flickrer.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.arkty.flickrer.data.remote.FlickrApi
import me.arkty.flickrer.data.remote.data.PhotoEntity
import timber.log.Timber
import java.lang.Exception

class SearchDataSource(
    private val api: FlickrApi,
    private val query: String
) : PagingSource<Int, PhotoEntity>() {

    override fun getRefreshKey(state: PagingState<Int, PhotoEntity>): Int {
        return START_PAGE_INDEX
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoEntity> {
        Timber.d("load: ${params.key}, ${query}")
        val currentPageIndex = params.key ?: START_PAGE_INDEX
        return try {
            if (query.isNotBlank()) {
                val res = api.search(query, currentPageIndex, PAGE_SIZE).photos

                val prevPage = if (res.page > START_PAGE_INDEX) {
                    res.page - 1
                } else {
                    null
                }
                val nextPage = if (res.page < res.pages) {
                    res.page + 1
                } else {
                    null
                }

                // after loading we can also warm Glide cache:
                // prefetch images to show pictures faster

                LoadResult.Page(
                    res.photo,
                    prevPage,
                    nextPage
                )
            } else {
                LoadResult.Page(listOf(), null, null)
            }

        } catch (e: Exception) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    companion object {
        const val PAGE_SIZE = 100
        const val START_PAGE_INDEX = 1
    }
}