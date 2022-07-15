package com.gov.sidesa

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.gov.sidesa.utils.PreferenceUtils
import com.gov.sidesa.utils.modules.NetworkModule
import com.gov.sidesa.utils.modules.RepositoryModule
import com.gov.sidesa.utils.modules.UseCaseModule
import com.gov.sidesa.utils.modules.ViewModelModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@App)
            modules(
                listOf(
                    ViewModelModule,
                    UseCaseModule,
                    NetworkModule,
                    RepositoryModule
                )
            )
        }
        AndroidThreeTen.init(this)
        PreferenceUtils.with(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}