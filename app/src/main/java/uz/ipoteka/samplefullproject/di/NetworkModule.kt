package uz.ipoteka.samplefullproject.di

import android.util.Log
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.intuit.sdp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.ipoteka.samplefullproject.utils.interCeptor.TokenInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val READ_TIMEOUT = 60L
private const val WRITE_TIMEOUT = 60L
private const val CONNECTION_TIMEOUT = 60L
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @[Provides Singleton]
    fun providesBaseUrl():String {
        return uz.ipoteka.samplefullproject.BuildConfig.BASE_URL
    }

    @[Provides Singleton]
    fun providesOkHttpClient(
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
//            .addInterceptor(tokenInterceptor)
        okHttpClient.addInterceptor(
            LoggingInterceptor.Builder()
                .setLevel(Level.BASIC)
                .log(Log.VERBOSE)
                .build())

        return okHttpClient.build()
    }


    @[Provides Singleton]
    fun providesRetrofit(baseUrl:String,okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .build()
    }
}