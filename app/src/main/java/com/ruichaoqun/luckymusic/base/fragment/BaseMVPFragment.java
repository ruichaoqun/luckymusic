package com.ruichaoqun.luckymusic.base.fragment;

import android.app.ProgressDialog;
import android.widget.Toast;


import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;
import com.ruichaoqun.luckymusic.base.mvp.IBaseView;

import javax.inject.Inject;

/**
 * @author Rui Chaoqun
 * @date :2019/12/2 10:27
 * description:
 */
public abstract class BaseMVPFragment<T extends IBasePresenter> extends BaseFragment implements IBaseView {

    @Inject
    protected T mPresenter;

    private ProgressDialog mProgressDialog;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void showToast(String message) {
        if(!isAdded()){
            return;
        }
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading(String message) {
        if(!isAdded()){
            return;
        }
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(getContext());
        }
        if(!mProgressDialog.isShowing()){
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if(isAdded() && mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }
}
