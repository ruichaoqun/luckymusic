package com.ruichaoqun.luckymusic.data.bean;

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
    private String mediaId;
    private String caller = CALLER_SELF;

    public MediaID() {
    }

    public MediaID(String type, String mediaId) {
        this.type = type;
        this.mediaId = mediaId;
    }

    public MediaID(String type, String mediaId, String caller) {
        this.type = type;
        this.mediaId = mediaId;
        this.caller = caller;
    }

    public String asString(){
        return TYPE + type + SEPARATOR + MEDIA_ID + mediaId + SEPARATOR + CALLER + caller;
    }

    public static MediaID fromString(String s){
        MediaID mediaID = new MediaID();
        mediaID.type = s.substring(6, s.indexOf(SEPARATOR));
        mediaID.mediaId = s.substring(s.indexOf(SEPARATOR) + 3 + 10, s.lastIndexOf(SEPARATOR));
        mediaID.caller = s.substring(s.lastIndexOf(SEPARATOR) + 3 + 8, s.length());
        return mediaID;
    }
}
