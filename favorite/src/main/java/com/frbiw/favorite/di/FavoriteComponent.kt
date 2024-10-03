package com.frbiw.favorite.di

import android.content.Context
import com.frbiw.favorite.ui.FavoriteFragment
import com.frbiw.mytmdbapps.di.FavoriteModule
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModule::class])
interface FavoriteComponent {
    fun inject(activity: FavoriteFragment)

    @Component.Builder
    interface Builder{
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModule: FavoriteModule): Builder
        fun build(): FavoriteComponent
    }
}