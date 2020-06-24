package com.ruichaoqun.luckymusic.base.activity;

import android.app.ProgressDialog;
import android.widget.Toast;

import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;
import com.ruichaoqun.luckymusic.base.mvp.IBaseView;

import javax.inject.Inject;

/**
 * @author Rui Chaoqun
 * @date :2020/6/24 16:27
 * description:
 */
public abstract class BaseMvpToolbarActivity<T extends IBasePresenter> extends BaseToolBarActivity implements IBaseView {
    @Inject
    protected T mPresenter;
    private ProgressDialog mProgressDialog;


    @Override
    protected void initPresenter() {
        mPresenter.takeView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
        hideLoading();
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

