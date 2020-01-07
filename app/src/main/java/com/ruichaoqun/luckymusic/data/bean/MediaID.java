package com.ruichaoqun.luckymusic.data.bean;

import android.support.v4.media.MediaMetadataCompat;
import android.text.TextUtils;

/**
 * @author Rui Chaoqun
 * @date :2019/12/25 11:44
 * description:
 */
public class MediaID {
    private final static String TYPE = "type: ";
    private final static String MEDIA_ID = "media_id: ";
    private final static String CALLER = "caller: ";
    private final static String SEPARATOR = " | ";
    private final static String CALLER_SELF = "self";
    private final static String CALLER_OTHER = "other";


    private String type;
    private long mediaId;
    private String caller = CALLER_SELF;

    public MediaID() {
    }

    public MediaID(String type, long mediaId) {
        this.type = type;
        this.mediaId = mediaId;
    }

    public MediaID(String type, long mediaId, String caller) {
        this.type = type;
        this.mediaId = mediaId;
        this.caller = caller;
    }

    public String asString(){
        return TYPE + type + SEPARATOR + MEDIA_ID + mediaId + SEPARATOR + CALLER + caller;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getMediaId() {
        return mediaId;
    }

    public void setMediaId(long mediaId) {
        this.mediaId = mediaId;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public static MediaID fromString(String s){
        MediaID mediaID = new MediaID();
        mediaID.type = s.substring(6, s.indexOf(SEPARATOR));
        mediaID.mediaId = Long.valueOf(s.substring(s.indexOf(SEPARATOR) + 3 + 10, s.lastIndexOf(SEPARATOR)));
        mediaID.caller = s.substring(s.lastIndexOf(SEPARATOR) + 3 + 8, s.length());
        return mediaID;
    }

    public static MediaID  fromMediaMetaData(MediaMetadataCompat metadataCompat){
        if(metadataCompat == null){
            return null;
        }
        String id = metadataCompat.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID);
        if(TextUtils.isEmpty(id)){
            return null;
        }
        return MediaID.fromString(id);
    }
}
