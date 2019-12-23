package com.ruichaoqun.luckymusic.ui.test;

import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.data.DataRepository;

import javax.inject.Inject;

/**
 * @author Rui Chaoqun
 * @date :2019-12-23 23:37:46
 * description:MVP 模板自动生成
 */
public class TestPresenter extends BasePresenter<TestContact.View> implements TestContact.Presenter {
    @Inject
    public TestPresenter(DataRepository dataRepository) {
        super(dataRepository);
    }
}
