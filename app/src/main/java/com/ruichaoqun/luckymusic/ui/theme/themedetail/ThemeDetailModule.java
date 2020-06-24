package com.ruichaoqun.luckymusic.ui.theme.themedetail;

import dagger.Binds;
import dagger.Module;

/**
 * @author Rui Chaoqun
 * @date :2020/6/24 16:42
 * description:
 */
@Module
public abstract class ThemeDetailModule {
    @Binds
    abstract ThemeDetailContact.Presenter mThemeDetailContact(ThemeDetailPresenter themeDetailPresenter);
}

