package com.ruichaoqun.luckymusic.theme;

/**
 * @author Rui Chaoqun
 * @date :2019/10/14 16:17
 * description:
 */
public class ThemeInfo {
    //主题样式     白色模式：-1   自定义颜色：-2  夜晚模式：-3  当前背景主题：-4    红色模式：-5
    private int id;
    private String name;

    public ThemeInfo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 是否是内部主题
     * @return
     */
    public boolean isInternal() {
        return this.id <= -1;
    }
}
