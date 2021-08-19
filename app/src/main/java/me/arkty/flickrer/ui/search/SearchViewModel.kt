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
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val api: FlickrApi
) : BaseViewModel() {
    val query = MutableStateFlow("")

    private var currentDataSource: SearchDataSource? = null
    private val pager = Pager(PagingConfig(SearchDataSource.PAGE_SIZE)) {
        SearchDataSource(api, query.value).also {
            currentDataSource = it
        }
    }

    val isLoading = MutableLiveData(false)
    val errorMessage = MutableLiveData<String?>()

    val list = pager.flow.catch {
        isLoading.postValue(false)
        errorMessage.postValue(it.message ?: "")
    }.onEach {
        errorMessage.postValue(null)
        isLoading.postValue(false)
    }.cachedIn(viewModelScope)

    init {
        watchQuery()
    }

    private fun watchQuery() {
        query.debounceInput().onEach {
            isLoading.postValue(true)
            errorMessage.postValue(null)
            currentDataSource?.invalidate()
        }.launchIn(viewModelScope)
    }
}