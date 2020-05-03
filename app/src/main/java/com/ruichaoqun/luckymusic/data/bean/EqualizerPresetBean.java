package com.ruichaoqun.luckymusic.data.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class EqualizerPresetBean {
    private String title;
    private int type;
    private String resource;
    private boolean isChecked;
    private int presetIndex;
    private List<Float> mDatas;

    public static EqualizerPresetBean newEmptyData() {
        EqualizerPresetBean bean = new EqualizerPresetBean();
        bean.setType(0);
        bean.setTitle("空");
        List<Float> floats = new ArrayList<>();
        for (int i = 0; i <10; i++) {
            floats.add(0.0f);
        }
        bean.setmDatas(floats);
        return bean;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getPresetIndex() {
        return presetIndex;
    }

    public void setPresetIndex(int presetIndex) {
        this.presetIndex = presetIndex;
    }

    public List<Float> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<Float> mDatas) {
        this.mDatas = mDatas;
    }
}
