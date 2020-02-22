package br.com.gabriellferreira.recipelist.presentation.di

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import br.com.gabriellferreira.recipelist.presentation.di.scope.ControllerScope
import dagger.Module
import dagger.Provides

@Module
class ControllerModule(private val mActivity: AppCompatActivity) {

    @Provides
    @ControllerScope
    fun activity(): Activity {
        return mActivity
    }
}