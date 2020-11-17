package com.raul.macias.wikiheroes.di

import com.raul.macias.wikiheroes.view.ui.home.HomeActivity
import com.raul.macias.wikiheroes.view.ui.creator.CreatorDetailActivity
import com.raul.macias.wikiheroes.view.ui.detail.DetailActivity
import com.raul.macias.wikiheroes.view.ui.detail_items.detail_comic.DetailItemActivity
import com.raul.macias.wikiheroes.view.ui.home.splash.SplashActivity
import com.raul.macias.wikiheroes.view.ui.person.PersonDetailActivity
import com.raul.macias.wikiheroes.view.ui.search.SearchActivity
import com.raul.macias.wikiheroes.view.ui.seeall.SeeAllActivity
import com.raul.macias.wikiheroes.view.ui.seeall.series.SeriesSeeAllActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    /**
     * Asociemos nuestras actividades a dagger
     */

    @ContributesAndroidInjector
    internal abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    internal abstract fun contributeDetailActivity(): DetailActivity

    @ContributesAndroidInjector
    internal abstract fun contributeDeatilComicActivity(): DetailItemActivity

    @ContributesAndroidInjector
    internal abstract fun contributeSeeAllActivity() : SeeAllActivity

    @ContributesAndroidInjector
    internal abstract fun contributeSeriesSeeAllActivity() : SeriesSeeAllActivity

    @ContributesAndroidInjector
    internal abstract fun contributeCreatorActivity(): CreatorDetailActivity

    @ContributesAndroidInjector
    internal abstract fun contributeSplashActivity() : SplashActivity

    @ContributesAndroidInjector
    internal abstract fun contributeSearchActivity() : SearchActivity

    @ContributesAndroidInjector
    internal abstract fun contributePersonDetailActivity() : PersonDetailActivity


}