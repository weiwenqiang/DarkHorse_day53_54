package com.wwq.entity;

/**
 * Created by 魏文强 on 2016/7/30.
 */
public class AdEntity {
    private int iconResId;
    private String intro;

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public AdEntity(int iconResId, String intro) {
        this.iconResId = iconResId;
        this.intro = intro;
    }
}
