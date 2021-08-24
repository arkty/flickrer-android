package me.arkty.flickrer.ui.photo

import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import me.arkty.flickrer.R
import me.arkty.flickrer.core.android.BaseFragment
import me.arkty.flickrer.core.android.utils.LayoutGroupInflation
import me.arkty.flickrer.databinding.FragmentPhotoBinding
import timber.log.Timber

@AndroidEntryPoint
class PhotoFragment : BaseFragment<FragmentPhotoBinding>() {
    override val layout: LayoutGroupInflation<FragmentPhotoBinding> =
        FragmentPhotoBinding::inflate
    override val vm: PhotoViewModel by viewModels()

    // not used, see PhotoViewModel for explanation
    private val args: PhotoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    fun onPhotoLongClick(): Boolean {
        // bzzz without vibrate permission
        binding.photo.performHapticFeedback(
            HapticFeedbackConstants.VIRTUAL_KEY,
            HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
        )
        saveOnDevice()
        return true
    }

    private fun saveOnDevice() {
        Timber.d("saveOnDevice")
        vm.onDownload()
        Toast.makeText(requireContext(), R.string.download_started, Toast.LENGTH_LONG).show()
    }

    private fun setupMenu() {
        setHasOptionsMenu(true)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.save -> {
                    saveOnDevice()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}