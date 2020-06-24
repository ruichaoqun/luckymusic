package com.ruichaoqun.luckymusic.ui.theme.themedetail;

import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.data.DataRepository;

import javax.inject.Inject;

/**
 * @author Rui Chaoqun
 * @date :2020/6/24 16:40
 * description:
 */
public class ThemeDetailPresenter extends BasePresenter<ThemeDetailContact.View> implements ThemeDetailContact.Presenter {
    @Inject
    public ThemeDetailPresenter(DataRepository dataRepository) {
        super(dataRepository);
    }

}

