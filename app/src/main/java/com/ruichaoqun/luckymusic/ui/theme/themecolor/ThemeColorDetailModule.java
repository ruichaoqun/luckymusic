package com.ruichaoqun.luckymusic.ui.theme.themecolor;

import dagger.Binds;
import dagger.Module;

/**
 * @author Rui Chaoqun
 * @date :2020/6/24 16:42
 * description:
 */
@Module
public abstract class ThemeColorDetailModule {
    @Binds
    abstract ThemeColorDetailContact.Presenter mThemeDetailContact(ThemeColorDetailPresenter themeDetailPresenter);
}

