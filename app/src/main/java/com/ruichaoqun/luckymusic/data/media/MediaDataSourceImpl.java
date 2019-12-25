package com.ruichaoqun.luckymusic.data.media;

import android.app.Application;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.media.MediaBrowserCompat;
import android.text.TextUtils;

import androidx.collection.ArrayMap;

import com.ruichaoqun.luckymusic.data.bean.SongBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


import static android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
import static android.provider.MediaStore.Audio.Media._ID;
import static android.provider.MediaStore.Audio.Media.ALBUM_ID;
import static android.provider.MediaStore.Audio.Media.ALBUM;
import static android.provider.MediaStore.Audio.Media.ARTIST;
import static android.provider.MediaStore.Audio.Media.ARTIST_ID;
import static android.provider.MediaStore.Audio.Media.TITLE;
import static android.provider.MediaStore.Audio.Media.DURATION;
import static android.provider.MediaStore.Audio.Media.TRACK;
import static android.provider.MediaStore.Audio.Media.DATA;
import static android.provider.MediaStore.Audio.Media.DEFAULT_SORT_ORDER;

@Singleton
public class MediaDataSourceImpl implements MediaDataSource {
    private ContentResolver mContentResolver;
    private List<MediaBrowserCompat.MediaItem> localSongs;
    private List<MediaBrowserCompat.MediaItem> searchSongs;
    private List<MediaBrowserCompat.MediaItem> artists;

    private Map<Long,List<MediaBrowserCompat.MediaItem>> songsByArtist;







    @Inject
    public MediaDataSourceImpl(Application context) {
        mContentResolver = context.getContentResolver();
    }

    @Override
    public Observable<List<SongBean>> getAllSongs() {
        return Observable.create(emitter -> {
            Cursor cursor = makeSongCursor(null,null);
            if(cursor == null){
                emitter.onError(new Throwable("获取本地音乐失败！-->getAllSongs"));
            }
            List<SongBean> list = new ArrayList<>();
            while (cursor.moveToNext()){
                list.add(SongBean.createFromCursor(cursor));
            }
            cursor.close();
            emitter.onNext(list);
            emitter.onComplete();
        });
    }

    @Override
    public Observable<SongBean> getSongFromId(long id) {
        return Observable.create(emitter -> {
            Cursor cursor = makeSongCursor("_id = "+id,null);
            if(cursor == null){
                emitter.onError(new Throwable("获取指定音乐失败！-->getAllSongs"));
            }
            List<SongBean> list = new ArrayList<>();
            while (cursor.moveToNext()){
                list.add(SongBean.createFromCursor(cursor));
            }
            cursor.close();
            if(list.size() > 0){
                emitter.onNext(list.get(0));
                emitter.onComplete();
            }else{
                emitter.onNext(new SongBean());
                emitter.onComplete();
            }
        });
    }

    @Override
    public Observable<List<SongBean>> getSongsFromIds(List<Long> ids) {
        return Observable.create(emitter -> {
            StringBuilder builder = new StringBuilder("_id IN (");
            for (int i = 0; i < ids.size(); i++) {
                builder.append(ids);
                if(i < ids.size() - 1){
                    builder.append(",");
                }
            }
            builder.append( ")");
            Cursor cursor = makeSongCursor(builder.toString(),null);
            if(cursor == null){
                emitter.onError(new Throwable("获取音乐失败！-->getAllSongs"));
            }
            List<SongBean> list = new ArrayList<>();
            while (cursor.moveToNext()){
                list.add(SongBean.createFromCursor(cursor));
            }
            cursor.close();
            emitter.onNext(list);
            emitter.onComplete();
        });
    }

    @Override
    public Observable<List<SongBean>> searchSongs(String searchKey) {
        return Observable.create(emitter -> {
            Cursor cursor = makeSongCursor("title LIKE ?",new String[]{searchKey});
            if(cursor == null){
                emitter.onError(new Throwable("搜索音乐失败！-->getAllSongs"));
            }
            List<SongBean> list = new ArrayList<>();
            while (cursor.moveToNext()){
                list.add(SongBean.createFromCursor(cursor));
            }
            cursor.close();
            emitter.onNext(list);
            emitter.onComplete();
        });
    }

    public synchronized Cursor makeSongCursor(String selection, String[] paramArrayOfString){
        StringBuilder builder = new StringBuilder("is_music=1 AND title != ''");
        if(!TextUtils.isEmpty(selection)){
            builder.append(" AND ");
            builder.append(selection);
        }
        String[] projection = new String[]{_ID,TITLE,ARTIST,ARTIST_ID,ALBUM,ALBUM_ID,DURATION,TRACK,DATA};
        return mContentResolver.query(EXTERNAL_CONTENT_URI,projection,builder.toString(),paramArrayOfString,DEFAULT_SORT_ORDER);
    }
}
