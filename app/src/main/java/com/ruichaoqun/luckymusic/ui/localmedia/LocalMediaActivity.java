package com.ruichaoqun.luckymusic.ui.localmedia;

import android.content.Context;
import android.content.Intent;

import com.ruichaoqun.luckymusic.base.activity.BaseMVPActivity;
import com.ruichaoqun.luckymusic.R;

/**
 * @author Rui Chaoqun
 * @date :2019-12-26 9:42:11
 * description:LocalMediaActivity
 */
public class LocalMediaActivity extends BaseMVPActivity<LocalMediaContact.Presenter> {

    public static void launchFrom(Context context){
        context.startActivity(new Intent(context,LocalMediaActivity.class));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.local_media_activity;
    }

    @Override
    protected void initParams() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
