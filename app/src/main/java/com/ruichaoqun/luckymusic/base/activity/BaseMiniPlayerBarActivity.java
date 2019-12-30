package com.ruichaoqun.luckymusic.base.activity;

import android.view.GestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.widget.PlayPauseView;

/**
 * @author Rui Chaoqun
 * @date :2019/12/30 19:43
 * description:
 */
public abstract class BaseMiniPlayerBarActivity extends BaseMediaBrowserActivity implements GestureDetector.OnGestureListener {
    private ViewGroup mMusicContainer;
    private RelativeLayout mPlayBarContainer;
    private ImageView mPlayBarCover;
    private TextView mPlayBarTitle;
    private TextView mPlayBarArtist;
    private ImageView mPlayBarList;
    private PlayPauseView mPlayPauseView;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if(needToolBar()){
            super.setContentView(layoutResID);
        }else{
            super.setContentView(getLayoutInflater().inflate(R.layout.layout_play_bar, null));
            addChildContentView(layoutResID, 0);
        }
        findViews();
    }

    private void findViews() {
        this.mPlayBarContainer = findViewById(R.id.play_bar_parent);
        this.mPlayBarContainer.setOnClickListener();
        this.mPlayBarCover = findViewById(R.id.cover);
        this.mPlayBarTitle = findViewById(R.id.title);
        this.mPlayBarArtist = findViewById(R.id.artist);
        this.mPlayBarList = findViewById(R.id.play_list);
        this.mPlayPauseView = findViewById(R.id.playPause);
    }

    private void addChildContentView(int layoutResID, int index) {
        addChildContentView(getLayoutInflater().inflate(layoutResID,null),index);

    }

    private void addChildContentView(View view, int index) {
        this.mMusicContainer = findViewById(R.id.layout_root);
        this.mMusicContainer.addView(view,index,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

}
