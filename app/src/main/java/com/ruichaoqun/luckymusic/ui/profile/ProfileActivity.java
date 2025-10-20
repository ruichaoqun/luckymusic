package com.ruichaoqun.luckymusic.ui.profile;

import android.os.Bundle;
import android.view.View;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.activity.BaseToolBarActivity;
import com.ruichaoqun.luckymusic.databinding.ActivityProfileBinding;

public class ProfileActivity extends BaseToolBarActivity {

    private ActivityProfileBinding mBinding;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View getContentView() {
        mBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
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
