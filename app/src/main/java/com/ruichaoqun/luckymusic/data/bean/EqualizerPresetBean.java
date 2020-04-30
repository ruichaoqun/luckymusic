package com.ruichaoqun.luckymusic.data.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class EqualizerPresetBean {
    private String title;
    private int type;
    private String resource;

    public EqualizerPresetBean(String title) {
        this.title = title;
        new Gson().fromJson("",new TypeToken<List<String>>() {}.getType());
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
}
