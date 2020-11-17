package com.raul.macias.wikiheroes

import android.app.Activity
import android.app.Application
import com.raul.macias.wikiheroes.di.DaggerAppComponent
import com.raul.macias.wikiheroes.utils.Utils
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MyApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {


    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<androidx.fragment.app.Fragment>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .application(this)
                .baseUrl(Utils.BASE_URL)
                .build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun supportFragmentInjector(): AndroidInjector<androidx.fragment.app.Fragment> = fragmentInjector


}