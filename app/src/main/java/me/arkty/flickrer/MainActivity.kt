package me.arkty.flickrer

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.arkty.flickrer.core.android.BaseActivity
import me.arkty.flickrer.core.android.utils.LayoutInflation
import me.arkty.flickrer.databinding.ActivityMainBinding
import me.arkty.flickrer.utils.helper.NsfwDetectHelper
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layout: LayoutInflation<ActivityMainBinding> = ActivityMainBinding::inflate
    override val vm by viewModels<MainViewModel>()

    @Inject
    lateinit var detector: NsfwDetectHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        // Switch from Splash to Normal theme
        setTheme(R.style.Theme_Flickrer)
        super.onCreate(savedInstanceState)
    }
}