package com.example.kotlin.di.module

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wingsofts.gankclient.api.GankApi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers
import java.io.File

/**
 * Created by liubin on 2017/6/13.
 */
@Module(includes = arrayOf(AppModule::class))
class ApiModule {

    @Provides fun provideRetrofit(baseUrl: HttpUrl, client: OkHttpClient,gson: Gson) =
        Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()

    @Provides fun provideHttpUrl() = HttpUrl.parse("http://gank.io/api/")

    @Provides fun provideOkhttp(context : Context,intercept : HttpLoggingInterceptor) : OkHttpClient{
        val cacheSize = 1024*1024*5L
        val cacheDir = File(context.cacheDir, "http")
        val cache = Cache(cacheDir, cacheSize)
        return OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(intercept).build()
    }

    @Provides fun provideLogInterceptor() : HttpLoggingInterceptor{
        val interceptor = HttpLoggingInterceptor{
            message -> Log.d("ApiModule","provideLogInterceptor-->"+message)
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides fun provideGson() = GsonBuilder().create()

    @Provides fun provideApi(retrofit:Retrofit) = retrofit.create(GankApi::class.java)

}