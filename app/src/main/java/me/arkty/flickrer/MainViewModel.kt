package me.arkty.flickrer

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.arkty.flickrer.core.android.BaseViewModel
import me.arkty.flickrer.data.remote.FlickrApi
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val api: FlickrApi
) : BaseViewModel() {
    init {
        test()
    }

    fun test() {
        launch {
            withContext(Dispatchers.IO) {
                try {
//                    api.search("Dogs").also {
//                        //Timber.d("items = ${it.photo}")
//                    }
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
    }
}