package com.ruichaoqun.luckymusic.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 自定义均衡器
 */
@Entity
public class CustomEqBean {
    @Id
    private Long id;
    private String eqTitle;
    private String eqJson;

    @Generated(hash = 1575850941)
    public CustomEqBean(Long id, String eqTitle, String eqJson) {
        this.id = id;
        this.eqTitle = eqTitle;
        this.eqJson = eqJson;
    }

    @Generated(hash = 689531964)
    public CustomEqBean() {
    }

    public String getEqJson() {
        return eqJson;
    }

    public void setEqJson(String eqJson) {
        this.eqJson = eqJson;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEqTitle() {
        return eqTitle;
    }

    public void setEqTitle(String eqTitle) {
        this.eqTitle = eqTitle;
    }
}
