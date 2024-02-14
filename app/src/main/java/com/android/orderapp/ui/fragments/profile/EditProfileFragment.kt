package com.android.orderapp.ui.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.android.orderapp.R
import com.android.orderapp.databinding.FragmentEditProfileBinding
import com.android.orderapp.ui.base.BaseFragment
import com.android.orderapp.ui.base.FragmentInflate


class EditProfileFragment : BaseFragment<EditProfileViewModel, FragmentEditProfileBinding>() {

    override val viewModel: EditProfileViewModel by viewModels()
    override val viewBindingInflater: FragmentInflate<FragmentEditProfileBinding>
        get() = FragmentEditProfileBinding::inflate



}