package me.arkty.flickrer.core.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import me.arkty.flickrer.BR
import me.arkty.flickrer.R
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

    open fun displayBack() = true

    private fun setupBackNavigation() {
        val toolbar = view?.findViewById<MaterialToolbar?>(R.id.toolbar)
        (activity as AppCompatActivity).let {
            it.setSupportActionBar(toolbar)
            it.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar?.setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }
    }
}