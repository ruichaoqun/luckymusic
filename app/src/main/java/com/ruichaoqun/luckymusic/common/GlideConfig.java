package com.ruichaoqun.luckymusic.common;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.ruichaoqun.luckymusic.R;

import java.io.File;
import java.io.InputStream;

@GlideModule
public class GlideConfig extends AppGlideModule {
    private static final int IAMGE_DISK_CACHE_MAX_SIZE = 300*1024*1024;


    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        builder.setDiskCache(new DiskCache.Factory() {
            @Nullable
            @Override
            public DiskCache build() {
                File file = new File(context.getCacheDir(),"glide");
                if(!file.isDirectory()){
                    file.delete();
                }
                if(!file.exists()){
                    file.mkdir();
                }
                return DiskLruCacheWrapper.create(file,IAMGE_DISK_CACHE_MAX_SIZE);
            }
        });

        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();

        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2*defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2*defaultBitmapPoolSize);
        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

//        builder.setDefaultRequestOptions(new RequestOptions().placeholder(R.mipmap.ic_placeholder).error(R.mipmap.ic_load_error));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }
}
