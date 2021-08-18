package me.arkty.flickrer.core.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import me.arkty.flickrer.core.android.utils.LayoutInflation
import me.arkty.flickrer.BR

abstract class BaseActivity <VB : ViewDataBinding> : AppCompatActivity() {
    abstract val layout: LayoutInflation<VB>
    abstract val vm: BaseViewModel
    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout(layoutInflater).also {
            binding = it
            binding.lifecycleOwner = this
        }.root)

        binding.setVariable(BR.v, this)
        binding.setVariable(BR.vm, vm)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.setVariable(BR.v, null)
        binding.setVariable(BR.vm, null)
    }
}