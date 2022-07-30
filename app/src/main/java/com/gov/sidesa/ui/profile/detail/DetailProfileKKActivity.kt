package com.gov.sidesa.ui.profile.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.databinding.ActivityDetailProfileKkactivityBinding
import com.gov.sidesa.ui.profile.edit.EditProfileKKActivity

class DetailProfileKKActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailProfileKkactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProfileKkactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.buttonUpdateKk.setOnClickListener {
            val intent = Intent(this, EditProfileKKActivity::class.java)
            resultLauncher.launch(intent)
        }
    }
}