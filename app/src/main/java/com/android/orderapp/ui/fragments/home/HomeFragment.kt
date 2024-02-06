package com.android.orderapp.ui.fragments.home

import androidx.fragment.app.activityViewModels
import com.android.orderapp.databinding.FragmentHomeBinding
import com.android.orderapp.ui.base.BaseFragment
import com.android.orderapp.ui.base.FragmentInflate
import com.android.orderapp.ui.fragments.home.HomeViewModel

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override val viewModel: HomeViewModel by activityViewModels()
    override val viewBindingInflater: FragmentInflate<FragmentHomeBinding>?
        get() = FragmentHomeBinding::inflate

}