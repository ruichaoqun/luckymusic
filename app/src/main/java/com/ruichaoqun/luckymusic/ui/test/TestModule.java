package com.ruichaoqun.luckymusic.ui.test;

import dagger.Binds;
import dagger.Module;

/**
 * @author Rui Chaoqun
 * @date :2019-12-23 23:37:46
 * description:MVP 模板自动生成
 */
@Module
public abstract class TestModule {

    @Binds
    abstract TestContact.Presenter mTestPresenter(TestPresenter mTestPresenter);

}
