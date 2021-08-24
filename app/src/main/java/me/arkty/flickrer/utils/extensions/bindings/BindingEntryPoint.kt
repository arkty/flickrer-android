package me.arkty.flickrer.utils.extensions.bindings

import androidx.databinding.DataBindingComponent
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import me.arkty.flickrer.core.android.di.BindingComponent
import me.arkty.flickrer.core.android.di.BindingScope

@EntryPoint
@BindingScope
@InstallIn(BindingComponent::class)
interface BindingEntryPoint : DataBindingComponent {

    @BindingScope
    override fun getNsfwImageAdapters(): NsfwImageAdapters
}