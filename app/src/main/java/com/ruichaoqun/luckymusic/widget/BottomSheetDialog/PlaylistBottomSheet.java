package com.ruichaoqun.luckymusic.widget.BottomSheetDialog;

import android.content.Context;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.StyleRes;

import com.ruichaoqun.luckymusic.R;

public class PlaylistBottomSheet extends BaseBottomSheet {
    private MediaSessionCompat.QueueItem mCurrentQueueItem;

    public PlaylistBottomSheet(Context context,  @StyleRes int themeResId,MediaSessionCompat.QueueItem queueItem) {
        super(context, themeResId);
        this.mCurrentQueueItem = queueItem;
    }

    @Override
    public void initCustomView() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_play_list,  null);
        this.mDialogView.addView(inflate);
//        this.mDialogView.mTarget = this.listView;
//        this.mDialogView.addIgnoreScrollView(inflate.findViewById(R.id.bwr));
        setContentView(this.mDialogView);

    }

    @Override
    public void onBottomSheetDismiss() {
//        cancelCalcuPlayListCacheTask();
    }

    @Override
    public void onBottomSheetShow() {
//        scrollToTargetMusic(false);
//        calcuPlayListCacheTask();
    }

    public static PlaylistBottomSheet showMusicPlayList(Context context, MediaSessionCompat.QueueItem queueItem) {
        PlaylistBottomSheet playlistBottomSheet = new PlaylistBottomSheet(context, R.style.f3, queueItem);
        playlistBottomSheet.show();
        return playlistBottomSheet;
    }

}
