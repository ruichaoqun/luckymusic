package com.ruichaoqun.luckymusic.ui.theme.themecolor;

import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.data.DataRepository;

import javax.inject.Inject;

/**
 * @author Rui Chaoqun
 * @date :2020/6/24 16:40
 * description:
 */
public class ThemeColorDetailPresenter extends BasePresenter<ThemeColorDetailContact.View> implements ThemeColorDetailContact.Presenter {
    @Inject
    public ThemeColorDetailPresenter(DataRepository dataRepository) {
        super(dataRepository);
    }

}

