package me.arkty.flickrer.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.arkty.flickrer.core.android.BaseFragment
import me.arkty.flickrer.core.android.utils.LayoutGroupInflation
import me.arkty.flickrer.databinding.FragmentSearchBinding

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val layout: LayoutGroupInflation<FragmentSearchBinding> =
        FragmentSearchBinding::inflate
    override val vm by viewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}