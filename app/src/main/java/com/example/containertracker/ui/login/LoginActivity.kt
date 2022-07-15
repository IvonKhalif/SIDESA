package com.example.containertracker.ui.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.containertracker.ui.main.MainActivity
import com.example.containertracker.base.BaseActivity
import com.example.containertracker.data.user.models.User
import com.example.containertracker.databinding.ActivityLoginBinding
import com.example.containertracker.utils.PreferenceUtils
import com.example.containertracker.utils.PreferenceUtils.USER_PREFERENCE
import com.example.containertracker.utils.constants.PermissionConstant
import com.example.containertracker.utils.extension.observeNonNull
import com.example.containertracker.utils.response.GenericErrorResponse
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        listener()
        initObserver()
    }

    private fun initObserver() {
        viewModel.apply {
            loadingWidgetLiveData.observeNonNull(this@LoginActivity, ::handleLoadingWidget)
            genericErrorLiveData.observeNonNull(this@LoginActivity, ::handleErrorLogin)
            networkErrorLiveData.observeNonNull(this@LoginActivity, ::handleErrorNetwork)
            loginLiveData.observeNonNull(this@LoginActivity, ::handleLogin)
        }
    }
    fun handleErrorNetwork(ioException: IOException) {
        showErrorMessage(ioException.message.orEmpty())
    }

    private fun handleErrorLogin(genericErrorResponse: GenericErrorResponse) {
        showErrorMessage(genericErrorResponse.status.orEmpty())
    }

    private fun handleLogin(user: User) {
        PreferenceUtils.put(user, USER_PREFERENCE)
        doRequestPermission()
    }

    private fun listener() {
        binding.buttonLogin.setOnClickListener {
            viewModel.signIn(
                binding.inputEmail.text.toString(),
                binding.inputPassword.text.toString()
            )
        }
    }

    private fun doRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), PermissionConstant.REQUEST_CODE_CAMERA
            )
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PermissionConstant.REQUEST_CODE_CAMERA -> {
                if (hasPermissions(Manifest.permission.CAMERA)
                    && hasPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                ) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }

    private fun hasPermissions(permissions: String): Boolean {
        return ActivityCompat.checkSelfPermission(this, permissions) == PackageManager.PERMISSION_GRANTED
    }
}
