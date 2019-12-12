package com.ruichaoqun.luckymusic.data.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class BannerListBean implements MultiItemEntity {
    private List<BannerItemBean> list;

    public List<BannerItemBean> getList() {
        return list;
    }

    public void setList(List<BannerItemBean> list) {
        this.list = list;
    }

    @Override
    public int getItemType() {
        return HomePageItemType.BANNER;
    }
}
