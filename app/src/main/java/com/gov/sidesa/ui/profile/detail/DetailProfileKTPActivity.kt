package com.gov.sidesa.ui.profile.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.gov.sidesa.R
import com.gov.sidesa.databinding.ActivityDetailKtpprofileBinding
import com.gov.sidesa.ui.profile.edit.EditProfileKTPActivity

class DetailProfileKTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailKtpprofileBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailKtpprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            }

        initListener()
    }

    private fun initListener() {
        binding.customToolbar.toolbarDetailProfile.apply {
            title = "Berdasarkan KTP"
            navigationIcon =
                ContextCompat.getDrawable(this@DetailProfileKTPActivity, R.drawable.ic_arrow_left)
            setNavigationOnClickListener { finish() }
        }

        binding.buttonEditKtp.setOnClickListener {
            val intent = Intent(this, EditProfileKTPActivity::class.java)
            launcher.launch(intent)
        }

    }
}