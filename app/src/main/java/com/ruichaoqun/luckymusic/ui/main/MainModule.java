package com.ruichaoqun.luckymusic.ui.main;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MainModule {

    @Binds
    abstract MainContact.Presenter mainPresenter(MainPresenter mainPresenter);

}
