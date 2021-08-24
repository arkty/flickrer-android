package me.arkty.flickrer.ui.photo

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import me.arkty.flickrer.core.android.BaseViewModel
import me.arkty.flickrer.data.remote.FlickrUrlBuilder
import me.arkty.flickrer.data.remote.data.PhotoEntity
import me.arkty.flickrer.utils.helper.DownloadHelper
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val downloadHelper: DownloadHelper
) : BaseViewModel() {
    val photo = MutableStateFlow<PhotoEntity?>(null)

    init {
        // 1. We should load photo info from API
        // instead of passing PhotoEntity through nav args

        // 2. For now Dagger Hilt is not supporting @AssistedInject for ViewModels,
        // so we can't pass PhotoFragmentArgs to ViewModel
        launch {
            photo.emit(state.get<PhotoEntity>("photo"))
        }
    }

    fun onDownload() {
        photo.value?.let {
            downloadHelper.enqueue(
                it.fullUrl,
                FlickrUrlBuilder.buildLocalFilename(it)
            )
        }
    }
}