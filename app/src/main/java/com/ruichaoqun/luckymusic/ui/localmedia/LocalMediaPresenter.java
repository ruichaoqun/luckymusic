package com.ruichaoqun.luckymusic.ui.localmedia;

import com.ruichaoqun.luckymusic.base.mvp.BasePresenter;
import com.ruichaoqun.luckymusic.data.DataRepository;

import javax.inject.Inject;

/**
 * @author Rui Chaoqun
 * @date :2019-12-26 9:42:11
 * description:MVP 模板自动生成
 */
public class LocalMediaPresenter extends BasePresenter<LocalMediaContact.View> implements LocalMediaContact.Presenter {
    @Inject
    public LocalMediaPresenter(DataRepository dataRepository) {
        super(dataRepository);
    }
}
