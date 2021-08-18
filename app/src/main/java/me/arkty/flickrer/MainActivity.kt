package me.arkty.flickrer

import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.arkty.flickrer.core.android.BaseActivity
import me.arkty.flickrer.core.android.utils.LayoutInflation
import me.arkty.flickrer.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layout: LayoutInflation<ActivityMainBinding> = ActivityMainBinding::inflate
    override val vm by viewModels<MainViewModel>()
}