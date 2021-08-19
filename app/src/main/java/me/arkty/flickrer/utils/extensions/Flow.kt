package me.arkty.flickrer.utils.extensions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
private const val INPUT_DEBOUNCE_TIME_MS = 500L
fun<T> Flow<T>.debounceInput() = debounce(INPUT_DEBOUNCE_TIME_MS)