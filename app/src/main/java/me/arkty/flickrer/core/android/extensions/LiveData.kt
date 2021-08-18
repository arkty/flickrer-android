package me.arkty.flickrer.core.android.extensions

import androidx.lifecycle.*

fun <T> LiveData<T>.observe(lifecycleOwner: LifecycleOwner, observer: (value: T?) -> Unit) {
    this.observe(lifecycleOwner, Observer(observer::invoke))
}

fun <T> LiveData<T?>.observeNotNull(lifecycleOwner: LifecycleOwner, observer: (value: T) -> Unit) {
    this.observe(lifecycleOwner, Observer { it?.let(observer::invoke) })
}

fun <T> liveDataOf(defValue: T? = null) = MutableLiveData<T>().apply {
    defValue?.apply { postValue(this) }
}

fun <T> LiveData<T>.observeForeverNotNull(observer: (value: T) -> Unit) {
    this.observeForever { it?.let(observer::invoke) }
}

fun <T, R> LiveData<T>.map(block: (T) -> R) = Transformations.map(this, block)