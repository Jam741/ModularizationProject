package com.yingwumeijia.android.ywmj.client.function.paysdk;

/**
 * Created by Jam on 2017/2/23 下午4:16.
 * Describe:
 */

public class PayInfo {


    /**
     * 0	成功	展示成功页面
     *-1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
     *-2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP。
     */

    /**
     * 微信支付分配的商户号
     */
    public String parentId;

    /**
     * 微信返回的支付交易会话ID
     */
    public String prepayId;

    /**
     * 随机字符串，不长于32位。推荐随机数生成算法
     */
    public String noncestr;

    /**
     * 时间戳，请见接口规则-参数规定
     */
    public String timestamp;

    /**
     * 签名，详见签名生成算法
     */
    public String sign;


    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
