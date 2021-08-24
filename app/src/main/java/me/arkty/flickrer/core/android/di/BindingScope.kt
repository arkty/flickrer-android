package me.arkty.flickrer.core.android.di

import androidx.databinding.DataBindingComponent
import dagger.hilt.DefineComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.BINARY

@Scope
@Retention(BINARY)
annotation class BindingScope

@BindingScope
@DefineComponent(parent = SingletonComponent::class)
interface BindingComponent

@DefineComponent.Builder
interface BindingComponentBuilder {
    fun build(): BindingComponent
}