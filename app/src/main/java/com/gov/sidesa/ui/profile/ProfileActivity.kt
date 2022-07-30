package com.gov.sidesa.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.gov.sidesa.databinding.ActivityProfileBinding
import com.gov.sidesa.ui.profile.detail.DetailProfileFamilyActivity
import com.gov.sidesa.ui.profile.detail.DetailProfileKKActivity
import com.gov.sidesa.ui.profile.detail.DetailProfileKTPActivity

class ProfileActivity : AppCompatActivity() {

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
    }
}