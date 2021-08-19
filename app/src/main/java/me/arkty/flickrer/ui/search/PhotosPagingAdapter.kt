package me.arkty.flickrer.ui.search

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import me.arkty.flickrer.R
import me.arkty.flickrer.data.remote.data.PhotoEntity
import me.arkty.flickrer.databinding.ItemPhotoBinding
import me.arkty.flickrer.utils.adapter.DataBindingViewHolder
import me.arkty.flickrer.utils.adapter.DataClassDiff

class PhotosPagingAdapter : PagingDataAdapter<
        PhotoEntity,
        DataBindingViewHolder<ItemPhotoBinding>>(
    DataClassDiff()
) {
    override fun onBindViewHolder(holder: DataBindingViewHolder<ItemPhotoBinding>, position: Int) {
        holder.binding?.item = getItem(position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataBindingViewHolder<ItemPhotoBinding> {
        return DataBindingViewHolder(parent, R.layout.item_photo)
    }
}