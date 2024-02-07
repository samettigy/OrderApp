package com.android.orderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.android.orderapp.databinding.ActivityMainBinding
import com.android.orderapp.ui.base.ActivityInflate
import com.android.orderapp.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity() : BaseActivity<MainViewModel, ActivityMainBinding>() {


    override val viewModel: MainViewModel by viewModels()

    override val viewBindingInflater: ActivityInflate<ActivityMainBinding>
        get() = ActivityMainBinding::inflate



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



          createBottomNavigation()
    }


    private fun createBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView5) as NavHostFragment
        NavigationUI.setupWithNavController(binding.btmNavigation, navHostFragment.navController)
    }




}