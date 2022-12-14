package com.engie.eea_tech_interview

import androidx.multidex.MultiDexApplication
import com.engie.eea_tech_interview.koin.networkModule
import com.engie.eea_tech_interview.koin.repositoryModule
import com.engie.eea_tech_interview.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

open class BaseApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(getDependencyModules())
        }
    }

    open fun getDependencyModules(): List<Module> {
        return listOf(
            viewModelModule,
            networkModule,
            repositoryModule
        )
    }
}
