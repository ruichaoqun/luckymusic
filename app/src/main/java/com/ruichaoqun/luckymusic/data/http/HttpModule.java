package com.ruichaoqun.luckymusic.data.http;

import com.ruichaoqun.luckymusic.Constants;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class HttpModule {

    @Provides
    @Singleton
    public ApiService provideApiService(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(Retrofit.Builder builder, OkHttpClient okHttpClient){
        return builder.baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    public Retrofit.Builder provideRetrofitBuilder(){
        return new Retrofit.Builder();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder){
        return builder.build();
    }

    @Provides
    @Singleton
    public OkHttpClient.Builder provideOkHttpClientBuilder(){
        return new OkHttpClient.Builder()
                .callTimeout(10_000, TimeUnit.MILLISECONDS)
                .connectTimeout(10_000, TimeUnit.MILLISECONDS)
                .addInterceptor(new HttpLoggingInterceptor());
    }


}
