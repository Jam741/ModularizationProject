package com.yingwumeijia.baseywmj.function.opinion;

/**
 * Created by Jam on 16/10/8 下午2:08.
 * Describe:
 */
public class PhotoBean {

    String imagPath;
    boolean isButton;

    public PhotoBean() {
    }

    public PhotoBean(String imagPath, boolean isButton) {
        this.imagPath = imagPath;
        this.isButton = isButton;
    }

    public String getImagPath() {
        return imagPath;
    }

    public void setImagPath(String imagPath) {
        this.imagPath = imagPath;
    }

    public boolean isButton() {
        return isButton;
    }

    public void setButton(boolean button) {
        isButton = button;
    }
}
