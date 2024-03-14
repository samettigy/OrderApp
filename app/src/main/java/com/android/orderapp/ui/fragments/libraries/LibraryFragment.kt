package com.android.orderapp.ui.fragments.libraries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.android.orderapp.R
import com.android.orderapp.databinding.FragmentLibraryBinding
import com.android.orderapp.ui.base.BaseFragment
import com.android.orderapp.ui.base.FragmentInflate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LibraryFragment : BaseFragment<LibraryViewModel, FragmentLibraryBinding>() {

    override val viewModel: LibraryViewModel by viewModels()
    override val viewBindingInflater: FragmentInflate<FragmentLibraryBinding>
        get() = FragmentLibraryBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

}