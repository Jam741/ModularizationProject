package com.yingwumeijia.baseywmj.entity.bean;


import com.yingwumeijia.baseywmj.entity.UserSession;

/**
 * Created by Jam
 * on 2016/6/1 17:49
 * Describe:
 */
public class UserBean {


    /**
     * id : 0
     * userName :
     * userPhone :
     * userType :
     * userTypeId : 0
     * deleted : false
     * available : false
     * name :
     * gender :
     * mail :
     * idCard :
     * idImageA :
     * idImageB :
     * idHoldImage :
     * createUserId : 0
     * createTime :
     * updateTime :
     * lastLoginTime :
     * entityId : 0
     * nickName :
     * headImage :
     * showName :
     * showHead :
     */


    private int userDetailType = 0;
    private int companyId;
    private String headImage;
    private String lifePhotos;
    private String resume;

    private int id;
    private String userName;
    private String userPhone;
    private String userType;
    private int userTypeId;
    private boolean deleted;
    private boolean available;
    private String name;
    private String gender;
    private String mail;
    private String idCard;
    private String idImageA;
    private String idImageB;
    private String idHoldImage;
    private int createUserId;
    private String createTime;
    private String updateTime;
    private String lastLoginTime;
    private int entityId;
    private String nickName;
    private String showName;
    private String userDetailTypeDesc;
    private String showHead;
    private String userTitle;
    private String imUid;
    private UserSession userSession;
    /*用户类型扩展，用于本地管理用户细分类型*/
    private int userTypeExtension;

    public int getUserTypeExtension() {
        return userTypeExtension;
    }

    public void setUserTypeExtension(int userTypeExtension) {
        this.userTypeExtension = userTypeExtension;
    }

    public int getUserDetailType() {
        return userDetailType;
    }

    public void setUserDetailType(int userDetailType) {
        this.userDetailType = userDetailType;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getLifePhotos() {
        return lifePhotos;
    }

    public void setLifePhotos(String lifePhotos) {
        this.lifePhotos = lifePhotos;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getUserDetailTypeDesc() {
        return userDetailTypeDesc;
    }

    public void setUserDetailTypeDesc(String userDetailTypeDesc) {
        this.userDetailTypeDesc = userDetailTypeDesc;
    }

    public UserSession getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdImageA() {
        return idImageA;
    }

    public void setIdImageA(String idImageA) {
        this.idImageA = idImageA;
    }

    public String getIdImageB() {
        return idImageB;
    }

    public void setIdImageB(String idImageB) {
        this.idImageB = idImageB;
    }

    public String getIdHoldImage() {
        return idHoldImage;
    }

    public void setIdHoldImage(String idHoldImage) {
        this.idHoldImage = idHoldImage;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getShowHead() {
        return showHead;
    }

    public void setShowHead(String showHead) {
        this.showHead = showHead;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public String getImUid() {
        return imUid;
    }

    public void setImUid(String imUid) {
        this.imUid = imUid;
    }
}
