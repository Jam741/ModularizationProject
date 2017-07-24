package com.yingwumeijia.baseywmj.entity.bean;

import java.util.List;

/**
 * Created by Jam on 2016/12/8 上午11:22.
 * Describe:
 */

public class AddSessionMember {


    /**
     * sessionId : 0
     * membersId : [""]
     */

    private String sessionId;
    private List<String> membersId;

    public AddSessionMember(List<String> membersId, String sessionId) {
        this.membersId = membersId;
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<String> getMembersId() {
        return membersId;
    }

    public void setMembersId(List<String> membersId) {
        this.membersId = membersId;
    }
}
