package com.ruichaoqun.luckymusic.base.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;


import com.ruichaoqun.luckymusic.base.mvp.IBasePresenter;

/**
 * @author Rui Chaoqun
 * @date :2019/6/26 11:30
 * description:懒加载Fragment基类
 */
public abstract class BaseLazyFragment<T extends IBasePresenter> extends BaseMVPFragment<T> {
    /**
     * 懒加载过
     */
    private boolean isLazyLoaded;

    /**
     * Fragment的View加载完毕的标记
     */
    private boolean isPrepared;

    /**
     * 第一步,改变isPrepared标记
     * 当onViewCreated()方法执行时,表明View已经加载完毕,此时改变isPrepared标记为true,并调用lazyLoad()方法
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        //只有Fragment onCreateView好了
        //另外这里调用一次lazyLoad(）
        lazyLoad();
    }

    /**
     * 第二步
     * 此方法会在onCreateView(）之前执行
     * 当viewPager中fragment改变可见状态时也会调用
     * 当fragment 从可见到不见，或者从不可见切换到可见，都会调用此方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }

    /**
     * 调用懒加载
     * 第三步:在lazyLoad()方法中进行双重标记判断,通过后即可进行数据加载
     */
    private void lazyLoad() {
        if (getUserVisibleHint() && isPrepared && !isLazyLoaded) {
            onLazyLoad();
            isLazyLoaded = true;
        }
    }

    /**
     * 第四步:定义抽象方法onLazyLoad(),具体加载数据的工作,交给子类去完成
     */
    @UiThread
    public void onLazyLoad(){}

}
