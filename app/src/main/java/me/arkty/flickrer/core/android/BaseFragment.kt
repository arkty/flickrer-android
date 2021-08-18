package me.arkty.flickrer.core.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import me.arkty.flickrer.BR
import me.arkty.flickrer.core.android.extensions.observeNotNull
import me.arkty.flickrer.core.android.utils.LayoutGroupInflation

abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {
    abstract val layout: LayoutGroupInflation<VB>
    abstract val vm: BaseViewModel
    protected lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = layout(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR.v, this)
        binding.setVariable(BR.vm, vm)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.setVariable(BR.v, null)
        binding.setVariable(BR.vm, null)
    }

    fun <T> LiveData<T>.observe(observer: (value: T?) -> Unit) {
        this.observe(viewLifecycleOwner, observer)
    }

    fun <T> LiveData<T?>.observeNotNull(observer: (value: T) -> Unit) {
        this.observeNotNull(viewLifecycleOwner, observer)
    }
}