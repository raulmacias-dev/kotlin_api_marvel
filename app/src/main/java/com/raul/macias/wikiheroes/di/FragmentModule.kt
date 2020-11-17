package com.raul.macias.wikiheroes.di

import com.raul.macias.wikiheroes.view.ui.detail_items.detail_comic.DetailImageFragment
import com.raul.macias.wikiheroes.view.ui.detail_items.detail_comic.more_comics.MoreGalleryFragment
import com.raul.macias.wikiheroes.view.ui.detail_items.detail_comic.more_info.MoreInfoFragment
import com.raul.macias.wikiheroes.view.ui.gallery.HorizontalGalleryFragment
import com.raul.macias.wikiheroes.view.ui.home.characters.HomeCharactersFragment
import com.raul.macias.wikiheroes.view.ui.home.comics.HomeComicsFragment
import com.raul.macias.wikiheroes.view.ui.home.desk.HomeDeskFragment
import com.raul.macias.wikiheroes.view.ui.person.comics.CreatorComicsFragment
import com.raul.macias.wikiheroes.view.ui.person.series.CreatorSeriesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    /**
     * Asociamos nuestros fragments a dagger
     */

    @ContributesAndroidInjector
    internal abstract fun contributeHorizontalGalleryFragment(): HorizontalGalleryFragment

    @ContributesAndroidInjector
    internal abstract fun contributeMoreGalleryFragment(): MoreGalleryFragment

    @ContributesAndroidInjector
    internal abstract fun contributeMoreInfoFragment(): MoreInfoFragment

    @ContributesAndroidInjector
    internal abstract fun contributeHomeCharactersFragment() : HomeCharactersFragment

    @ContributesAndroidInjector
    internal abstract fun contributeHomeComicsFragment() : HomeComicsFragment

    @ContributesAndroidInjector
    internal abstract fun contributeDetailImageFragment() : DetailImageFragment

    @ContributesAndroidInjector
    internal abstract fun contributeHomeDeskFragment() : HomeDeskFragment

    @ContributesAndroidInjector
    internal abstract fun contributeCreatorComicsFragment() : CreatorComicsFragment

    @ContributesAndroidInjector
    internal abstract fun contributeCreatorSeriesFragment() : CreatorSeriesFragment
}