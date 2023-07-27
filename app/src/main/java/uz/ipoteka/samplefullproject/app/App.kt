package uz.ipoteka.samplefullproject.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import uz.ipoteka.samplefullproject.BuildConfig
import uz.ipoteka.samplefullproject.utils.localeStorage.LocalStorage

@HiltAndroidApp
class App:Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        instance = this
        LocalStorage.init(this)
    }
    companion object {
        lateinit var instance: App
    }
}