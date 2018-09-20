package com.tj.deliverylist.di.moduels;


import android.content.Context;

import com.google.gson.Gson;
import com.tj.deliverylist.App;
import com.tj.deliverylist.BuildConfig;
import com.tj.deliverylist.net.Webservice;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {

    public static final String TAG = NetModule.class.getSimpleName();

    private final String baseUrl = "https://mock-api-mobile.dev.lalamove.com";

    @Provides
    @Singleton
    public GsonConverterFactory provideConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    Cache provideHttpCache() {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(App.getInstance().getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Cache cache) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .cache(cache);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        return builder.build();
    }


    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory) {

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    @Singleton
    public Webservice provideContactsApi(Retrofit retrofit) {
        return retrofit.create(Webservice.class);
    }

    @Provides
    @Singleton
    public Gson provideGson(){
        return new Gson();
    }

}

