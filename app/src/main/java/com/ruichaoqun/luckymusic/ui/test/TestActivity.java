package com.ruichaoqun.luckymusic.ui.test;

import android.view.View;

import com.ruichaoqun.luckymusic.base.activity.BaseMVPActivity;
import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.databinding.TestActivityBinding;

/**
 * @author Rui Chaoqun
 * @date :2019-12-23 23:37:46
 * description:TestActivity
 */
public class TestActivity extends BaseMVPActivity<TestContact.Presenter> {
    private TestActivityBinding mBinding;

    @Override
    protected int getLayoutResId() {
        return R.layout.test_activity;
    }

    @Override
    protected View getContentView() {
        mBinding = TestActivityBinding.inflate(getLayoutInflater());
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
