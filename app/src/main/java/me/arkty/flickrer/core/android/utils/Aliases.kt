package me.arkty.flickrer.core.android.utils

import android.view.LayoutInflater
import android.view.ViewGroup

typealias LayoutGroupInflation<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
typealias LayoutInflation<T> = (LayoutInflater) -> T
