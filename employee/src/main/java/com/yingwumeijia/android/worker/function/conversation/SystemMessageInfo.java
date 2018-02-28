package com.yingwumeijia.android.worker.function.conversation;

/**
 * Created by Jam on 2017/3/25 下午9:44.
 * Describe:
 */


public class SystemMessageInfo {

    enum MessageType{
        COMMENT,
        COLLECT,
        SYSTEM
    }

    int icon;
    String title;
    MessageType type;
    String content;

    public SystemMessageInfo(int icon, String title, MessageType type) {
        this.icon = icon;
        this.title = title;
        this.type = type;
    }


    public SystemMessageInfo(int icon, String title, MessageType type, String content) {
        this.icon = icon;
        this.title = title;
        this.type = type;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
