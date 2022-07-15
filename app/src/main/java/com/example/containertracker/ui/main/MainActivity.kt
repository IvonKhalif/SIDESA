package com.example.containertracker.ui.main

import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.example.containertracker.R
import com.example.containertracker.base.BaseActivity
import com.example.containertracker.data.location.models.Location
import com.example.containertracker.data.user.models.User
import com.example.containertracker.databinding.ActivityMainBinding
import com.example.containertracker.ui.selectlocation.SelectLocationBottomSheet
import com.example.containertracker.utils.PreferenceUtils
import com.example.containertracker.utils.PreferenceUtils.USER_PREFERENCE
import com.example.containertracker.widget.DropDownWidget
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.example.containertracker.databinding.LogoutDialogBinding
import com.example.containertracker.ui.login.LoginActivity
import com.example.containertracker.utils.extension.observeNonNull
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity(), NavController.OnDestinationChangedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private val user by lazy {
        PreferenceUtils.get<User>(USER_PREFERENCE)
    }
    private lateinit var placementDropDown: DropDownWidget
    private var isFirstInit: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getLocations(user?.id.orEmpty())

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val headerView = navView.getHeaderView(0)
        placementDropDown = headerView.findViewById<DropDownWidget>(R.id.placementDropDownWidget)
        val username = headerView.findViewById<TextView>(R.id.username)
        val btnLogOut = headerView.findViewById<TextView>(R.id.button_logout)
        navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_container, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        placementDropDown.setTitle("Location")
        username.text = user?.name
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        btnLogOut.setOnClickListener {
            drawerLayout.closeDrawers()
            logout()
        }

        observer()
        listener()
    }

    private fun listener() {
        placementDropDown.onClickDropDownListener {
            selectLocation()
        }
    }

    private fun observer() {
        viewModel.apply {
            locationLiveData.observe(this@MainActivity, ::handleUpdateLocation)
            locationListLiveData.observe(this@MainActivity, ::handleUpdateLocationList)
            loadingWidgetLiveData.observeNonNull(this@MainActivity, { handleLoadingWidget(it) })
        }
    }

    private fun handleUpdateLocationList(list: List<Location>) {
        if (isFirstInit) {
            if (list.size > 1) {
                placementDropDown.setEnabledClick(true)
                selectLocation()
            } else {
                placementDropDown.setEnabledClick(false)
                if (viewModel.locationListLiveData.value.isNullOrEmpty()) {
                    viewModel.locationLiveData.value =
                        Location(id = "", name = getString(R.string.general_action_select))
                } else {
                    viewModel.locationLiveData.value = viewModel.locationListLiveData.value?.first()
                    viewModel.locationListLiveData.value?.let { handleUpdateLocation(it.first()) }
                }
            }
            isFirstInit = false
        }
    }

    private fun handleUpdateLocation(location: Location) {
        if (location.id.isNotEmpty())
            placementDropDown.setText(location.name)
        navController.addOnDestinationChangedListener(this)
    }

    private fun selectLocation() {
        val selectPostBottomSheet = SelectLocationBottomSheet { location, list ->
            viewModel.locationListLiveData.value = list
            viewModel.locationLiveData.value = location
        }
        selectPostBottomSheet.isCancelable = false
        selectPostBottomSheet.show(supportFragmentManager, selectPostBottomSheet.tag)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun logout() {
        val dialog = Dialog(this)

        val bindingToast: LogoutDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            R.layout.logout_dialog, null, false
        )

        bindingToast.lifecycleOwner = this

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(bindingToast.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        bindingToast.btnNo.setOnClickListener {
            dialog.dismiss()
        }
        bindingToast.btnYes.setOnClickListener {
            PreferenceUtils.put(null, USER_PREFERENCE)
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            overridePendingTransition(R.anim.anim_slide_left, R.anim.nav_default_enter_anim)
            finish()
        }

        dialog.show()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.nav_home ->
                binding.appBarMain.toolbar.title = getString(
                    R.string.scanner_title_toolbar,
                    viewModel.locationLiveData.value?.name.toString()
                )
        }
    }
}