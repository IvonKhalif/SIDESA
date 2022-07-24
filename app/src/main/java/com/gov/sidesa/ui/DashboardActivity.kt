package com.gov.sidesa.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.databinding.ActivityDashboardBinding
import com.gov.sidesa.ui.profile.edit.EditProfileActivity

class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainView()
    }

    private fun mainView() {
        with(binding) {
            buttonAccount.setOnClickListener {
                startActivity(Intent(this@DashboardActivity, EditProfileActivity::class.java))
            }
        }
    }
}