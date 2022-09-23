package com.gov.sidesa.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.databinding.ActivityProfileBinding
import com.gov.sidesa.ui.login.LoginActivity
import com.gov.sidesa.ui.profile.detail.family.DetailProfileFamilyActivity
import com.gov.sidesa.ui.profile.detail.kk.DetailProfileKKActivity
import com.gov.sidesa.ui.profile.detail.ktp.DetailProfileKTPActivity
import com.gov.sidesa.utils.PreferenceUtils
import com.gov.sidesa.utils.PreferenceUtils.USER_PREFERENCE
import com.gov.sidesa.utils.PreferenceUtils.USER_RESPONSE_PREFERENCE

class ProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            }

        initListener()
        initView()
    }

    private fun initView() {
        val user = PreferenceUtils.getAccount()
        user?.let {
            binding.textName.text = it.name
            binding.textNik.text = it.nik
        }
    }

//    private fun showMediaDialog() {
//        val media = SelectImageBottomSheet.newInstance()
//
//        media.onImageSelected = {
//            viewModel.onImagePathChanged(it.absolutePath)
//            media.dismissAllowingStateLoss()
//        }
//
//        media.showNow(supportFragmentManager, media.javaClass.canonicalName)
//    }

    private fun initListener() {
        binding.toolbarProfile.setNavigationOnClickListener { finish() }
        binding.textDataKtp.setOnClickListener {
            val intent = Intent(this, DetailProfileKTPActivity::class.java)
            launcher.launch(intent)
        }

        binding.textDataKk.setOnClickListener {
            val intent = Intent(this, DetailProfileKKActivity::class.java)
            launcher.launch(intent)
        }

        binding.textDataFamily.setOnClickListener {
            val intent = Intent(this, DetailProfileFamilyActivity::class.java)
            launcher.launch(intent)
        }

        binding.textDataEmail.setOnClickListener {
            showErrorMessage("Feature sedang dalam pengembangan")
        }

        binding.buttonLogout.setOnClickListener {
            PreferenceUtils.put(null, USER_PREFERENCE)
            PreferenceUtils.put(null, USER_RESPONSE_PREFERENCE)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}