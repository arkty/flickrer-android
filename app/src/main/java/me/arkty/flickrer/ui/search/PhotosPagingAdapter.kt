package me.arkty.flickrer.ui.search

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import me.arkty.flickrer.R
import me.arkty.flickrer.data.remote.data.PhotoEntity
import me.arkty.flickrer.databinding.ItemPhotoBinding
import me.arkty.flickrer.utils.adapter.DataBindingViewHolder
import me.arkty.flickrer.utils.adapter.DataClassDiff
import javax.inject.Inject

class PhotosPagingAdapter @Inject constructor() : PagingDataAdapter<
        PhotoEntity,
        DataBindingViewHolder<ItemPhotoBinding>>(
    DataClassDiff()
) {
    var onPhotoSelected: (PhotoEntity) -> Unit = {}

    override fun onBindViewHolder(holder: DataBindingViewHolder<ItemPhotoBinding>, position: Int) {
        val photo = getItem(position)
        holder.binding?.item = photo
        holder.itemView.setOnClickListener {
            photo?.let(onPhotoSelected)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataBindingViewHolder<ItemPhotoBinding> {
        return DataBindingViewHolder(parent, R.layout.item_photo)
    }
}