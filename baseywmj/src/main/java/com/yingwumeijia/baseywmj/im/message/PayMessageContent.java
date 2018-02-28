package com.yingwumeijia.baseywmj.im.message;



/**
 * Created by Jam on 2017/3/20 下午10:14.
 * Describe:
 */


//@MessageTag(value = "YWMJ:PayMsg", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class PayMessageContent {

//    private static final String TAG = "PayMessageContent";
//
//    private String content;//消息属性，可随意定义
//
//    private String extra;//扩展字段
//
//    private String billId;//账单ID
//
//
//    protected PayMessageContent() {
//    }
//
//    public static PayMessageContent obtain(String text, String billId, String extra) {
//        PayMessageContent model = new PayMessageContent();
//        model.setContent(text);
//        model.setBillId(billId);
//        model.setExtra(extra);
//        return model;
//    }
//
//
//    public PayMessageContent(byte[] data) {
//        String jsonStr = null;
//
//        try {
//            jsonStr = new String(data, "UTF-8");
//            Log.d(TAG, jsonStr);
//        } catch (UnsupportedEncodingException e1) {
//
//        }
//
//        try {
//            JSONObject jsonObj = new JSONObject(jsonStr);
//
//            if (jsonObj.has("content"))
//                content = jsonObj.optString("content");
//            if (jsonObj.has("extra"))
//                extra = jsonObj.optString("extra");
//            if (jsonObj.has("billId"))
//                billId = jsonObj.optString("billId");
//            if (jsonObj.has("user")) {
//                setUserInfo(parseJsonToUserInfo(jsonObj.getJSONObject("user")));
//            }
//        } catch (JSONException e) {
//            Log.e(TAG, e.getMessage());
//        }
//
//    }
//
//
//    //给消息赋值。
//    public PayMessageContent(Parcel in) {
//        content = ParcelUtils.readFromParcel(in);//该类为工具类，消息属性
//        extra = ParcelUtils.readFromParcel(in);//该类为工具类，消息属性
//        billId = ParcelUtils.readFromParcel(in);//该类为工具类，消息属性
//        setUserInfo(ParcelUtils.readFromParcel(in, UserInfo.class));
//        //这里可继续增加你消息的属性
//    }
//
//    @Override
//    public byte[] encode() {
//        JSONObject jsonObj = new JSONObject();
//        try {
//            if (!TextUtils.isEmpty(this.getContent())) {
//                jsonObj.put("content", this.getContent());
//            }
//
//            if (!TextUtils.isEmpty(this.getExtra())) {
//                jsonObj.put("extra", this.getExtra());
//            }
//
//            if (!TextUtils.isEmpty(this.getBillId())) {
//                jsonObj.put("billId", this.getBillId());
//            }
//
//            if (this.getJSONUserInfo() != null) {
//                jsonObj.putOpt("user", this.getJSONUserInfo());
//            }
//        } catch (JSONException e) {
//            Log.e("JSONException", e.getMessage());
//        }
//
//        try {
//            return jsonObj.toString().getBytes("UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    /**
//     * 描述了包含在 Parcelable 对象排列信息中的特殊对象的类型。
//     *
//     * @return 一个标志位，表明Parcelable对象特殊对象类型集合的排列。
//     */
//    public int describeContents() {
//        return 0;
//    }
//
//    /**
//     * 将类的数据写入外部提供的 Parcel 中。
//     *
//     * @param dest  对象被写入的 Parcel。
//     * @param flags 对象如何被写入的附加标志。
//     */
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        ParcelUtils.writeToParcel(dest, this.content);
//        ParcelUtils.writeToParcel(dest, this.getExtra());
//        ParcelUtils.writeToParcel(dest, this.billId);
//        ParcelUtils.writeToParcel(dest, this.getUserInfo());
//    }
//
//    /**
//     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
//     */
//    public static final Creator<PayMessageContent> CREATOR = new Creator<PayMessageContent>() {
//
//        @Override
//        public PayMessageContent createFromParcel(Parcel source) {
//            return new PayMessageContent(source);
//        }
//
//        @Override
//        public PayMessageContent[] newArray(int size) {
//            return new PayMessageContent[size];
//        }
//    };
//
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public String getExtra() {
//        return extra;
//    }
//
//    public void setExtra(String extra) {
//        this.extra = extra;
//    }
//
//    public String getBillId() {
//        return billId;
//    }
//
//    public void setBillId(String billId) {
//        this.billId = billId;
//    }
}
