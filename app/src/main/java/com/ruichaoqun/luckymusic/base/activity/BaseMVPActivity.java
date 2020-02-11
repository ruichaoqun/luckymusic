package com.ruichaoqun.luckymusic.base.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.Toast;


import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;
import com.ruichaoqun.luckymusic.base.mvp.IBaseView;

import javax.inject.Inject;

/**
 * @author Rui Chaoqun
 * @date :2019/12/2 10:17
 * description:
 */
public abstract class BaseMVPActivity<T extends IBasePresenter> extends BaseMiniPlayerBarActivity implements IBaseView {
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
