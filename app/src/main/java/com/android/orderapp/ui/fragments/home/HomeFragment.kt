package com.android.orderapp.ui.fragments.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.android.orderapp.databinding.FragmentHomeBinding
import com.android.orderapp.ui.base.BaseFragment
import com.android.orderapp.ui.base.FragmentInflate
import com.android.orderapp.ui.fragments.home.HomeViewModel

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override val viewModel: HomeViewModel by activityViewModels()
    override val viewBindingInflater: FragmentInflate<FragmentHomeBinding>?
        get() = FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())
                alertDialogBuilder.setMessage("Do you want to exit the application?")
                alertDialogBuilder.setPositiveButton("Evet") { dialog, which ->
                    requireActivity().finish()
                }
                alertDialogBuilder.setNegativeButton("Hayır") { dialog, which ->

                }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
                true
            } else {
                false
            }
        }
    }

}