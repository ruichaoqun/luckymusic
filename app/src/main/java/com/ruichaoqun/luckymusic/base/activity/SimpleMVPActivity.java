package com.ruichaoqun.luckymusic.base.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ruichaoqun.luckymusic.R;
import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;
import com.ruichaoqun.luckymusic.base.mvp.IBaseView;

import javax.inject.Inject;

public abstract class SimpleMVPActivity<T extends IBasePresenter> extends BaseActivity implements IBaseView {
    @Inject
    protected T mPresenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initPresenter() {
        mPresenter.takeView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public void showToast(int stringRes) {
        Toast.makeText(this,getString(stringRes),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(String message) {
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(this);
        }
        if(!mProgressDialog.isShowing()){
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if(mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }
}

