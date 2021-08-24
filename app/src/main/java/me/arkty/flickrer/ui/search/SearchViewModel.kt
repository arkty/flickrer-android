package me.arkty.flickrer.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import me.arkty.flickrer.core.android.BaseViewModel
import me.arkty.flickrer.data.remote.FlickrApi
import me.arkty.flickrer.data.source.SearchDataSource
import me.arkty.flickrer.utils.extensions.debounceInput
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val api: FlickrApi
) : BaseViewModel() {
    val query = MutableStateFlow<String?>(null)

    private var currentDataSource: SearchDataSource? = null
    private val pager = Pager(PagingConfig(SearchDataSource.PAGE_SIZE)) {
        SearchDataSource(api, query.value ?: "").also {
            currentDataSource = it
        }
    }

    val isLoading = MutableLiveData(false)
    val errorMessage = MutableStateFlow<String?>(null)

    /**
     * Photos is
     */
    val photos = pager.flow.catch {
        isLoading.postValue(false)
        errorMessage.emit(it.message)
    }.onEach {
        errorMessage.emit(null)
        isLoading.postValue(false)
    }.cachedIn(viewModelScope)

    init {
        watchQuery()
    }

    /**
     * Invalidate source on each query.
     * Query is debounced by default input delay
     */
    private fun watchQuery() {
        query.filter {
            !it.isNullOrBlank()
        }.debounceInput().onEach {
            Timber.d("query = ${it}")
            isLoading.postValue(true)
            errorMessage.emit(null)
            currentDataSource?.invalidate()
        }.launchIn(viewModelScope)
    }
}