package uz.ipoteka.samplefullproject.utils.localeStorage

import android.content.Context
import android.content.SharedPreferences
import uz.ipotekabank.mk.local.BooleanPreference
import uz.ipotekabank.mk.local.IntPreference
import uz.ipotekabank.mk.local.StringPreference

/***
Created by Dostonbek eshmurodov on 27/07/23.
Company Ipoteka bank.
Contact t.me/Programmer_2001.
 **/
class LocalStorage(context: Context) {

    companion object {
        @Volatile
        lateinit var instance: LocalStorage
            private set

        fun init(context: Context) {
            synchronized(this) {
                instance = LocalStorage(context)
            }
        }
    }

    private val pref: SharedPreferences =
        context.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE)

    var logged: Boolean by BooleanPreference(pref, false)
    var completeIntro: Boolean by BooleanPreference(pref, false)
    var token: String by StringPreference(pref)
    var isMonitoring: Boolean by BooleanPreference(pref, false)
    var isLocation: Boolean by BooleanPreference(pref, false)
    var moneyType: Int by IntPreference(pref, 0)
    var refreshToken: String by StringPreference(pref, "")
    var accessToken: String by StringPreference(pref, "")
    var id: String by StringPreference(pref)
    var lan: String by StringPreference(pref)
    var markedLanguage: String by StringPreference(pref)
    var isOnBoarding: Boolean by BooleanPreference(pref, false)
    var theme: Boolean by BooleanPreference(pref, false)

    fun clearAllPreference() {
        pref.edit().clear().apply()
    }


}

