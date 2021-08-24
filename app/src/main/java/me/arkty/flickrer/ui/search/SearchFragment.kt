package me.arkty.flickrer.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import me.arkty.flickrer.core.android.BaseFragment
import me.arkty.flickrer.core.android.utils.LayoutGroupInflation
import me.arkty.flickrer.databinding.FragmentSearchBinding
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val layout: LayoutGroupInflation<FragmentSearchBinding> =
        FragmentSearchBinding::inflate
    override val vm by viewModels<SearchViewModel>()

    @Inject
    lateinit var photosAdapter: PhotosPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchResultList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.searchResultList.adapter = photosAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            vm.photos.collectLatest {
                photosAdapter.submitData(it)
            }
        }

        // Process loading state
        photosAdapter.addLoadStateListener {

            // drop UI state
            binding.initialLoading.visibility = View.GONE
            binding.moreLoading.visibility = View.GONE
            binding.emptyResultsLabel.visibility = View.GONE

            // process initial loading
            if (it.source.refresh is LoadState.Loading) {
                binding.initialLoading.visibility = View.VISIBLE
            }

            // process append loading
            if (it.source.append is LoadState.Loading) {
                binding.moreLoading.visibility = View.VISIBLE
            }

            // process empty results message
            if (it.source.refresh is LoadState.NotLoading &&
                it.source.append.endOfPaginationReached &&
                photosAdapter.itemCount == 0 &&
                !vm.query.value.isNullOrBlank()
            ) {
                binding.emptyResultsLabel.visibility = View.VISIBLE
            }

            // We can also add error processing and initial message here
        }
    }
}