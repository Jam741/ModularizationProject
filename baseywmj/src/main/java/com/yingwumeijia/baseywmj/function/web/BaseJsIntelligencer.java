package com.yingwumeijia.baseywmj.function.web;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tencent.smtt.sdk.WebView;
import com.yingwumeijia.baseywmj.function.user.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by Jam on 2017/3/20 上午10:29.
 * Describe:
 */

public class BaseJsIntelligencer implements Serializable{


//    protected Activity activity;
//
//    protected WebView mWebView;
//
//    protected KProgressHUD progressBar;
//
//    protected Gson gson;
//
//    protected AlertDialog shareDialog;
//
//    protected JsBridgeInfo currentJsbridgeInfo;
//
//    protected OptionPicker picker;
//
//    protected AddressPicker addressPicker;
//
//    protected CameraSdkParameterInfo mCameraSdkParameterInfo;
//
//
//    protected boolean neenLoginCallback = false;
//
//    protected WbShareHandler shareHandler;
//
//    public BaseJsIntelligencer(Activity activity, WebView mWebView, WbShareHandler shareHandler) {
//        this.activity = activity;
//        this.mWebView = mWebView;
//        this.shareHandler = shareHandler;
//    }
//
//
//    @JavascriptInterface
//    public void closePage() {
//        ActivityCompat.finishAfterTransition(activity);
//    }
//
//
//    @JavascriptInterface
//    public void toLogin() {
//        LoginActivity.Companion.startCurrent(activity);
//    }
//
//    /**
//     * 显示弹窗信息
//     *
//     * @param msg
//     */
//    @JavascriptInterface
//    public void showToastMessage(String msg) {
//        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
//    }
//
//
//    @JavascriptInterface
//    public void showDialog() {
//        if (progressBar == null) {
//            progressBar = KProgressHUD.create(activity);
//        }
//        progressBar.show();
//    }
//
//    @JavascriptInterface
//    public void dismisDialog() {
//        if (progressBar != null && progressBar.isShowing())
//            progressBar.dismiss();
//    }
//
//
//    @JavascriptInterface
//    public void shareActivity(String json) {
//        Log.d("jam", "-shareActivity===" + json);
//        if (progressBar == null)
//            progressBar = KProgressHUD.create(activity);
//
//        Gson gson = new Gson();
//        final ShareModel shareModel = gson.fromJson(json, ShareModel.class);
//        Observable.just(shareModel)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<ShareModel>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        progressBar.dismiss();
//                    }
//
//                    @Override
//                    public void onNext(final ShareModel shareModel1) {
//                        Glide.with(activity).load(shareModel1.getImg() + "?imageView2/1/w/100").asBitmap().into(new SimpleTarget<Bitmap>() {
//                            @Override
//                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                                Bitmap shareBitmap = resource;
//                                shareModel1.setmShareImg(shareBitmap);
//                                showShareDialog(activity, shareModel1);
//                                progressBar.dismiss();
//                            }
//                        });
//                    }
//                });
//    }
//
//
//    public void showShareDialog(final Context context, final ShareModel shareModel) {
//
//
//        final ShareManager mShareManager = new ShareManager(
//                context,
//                shareModel, shareHandler);
//        TextView btnShareToWx;
//        TextView btnShareToFriends;
//        TextView btnShareToWb;
//        TextView btnCopyLink;
//        View shareView = LayoutInflater.from(context).inflate(R.layout.share_dialog_layout, null);
//        btnShareToWx = (TextView) shareView.findViewById(R.id.btn_shareToWechat);
//        btnShareToFriends = (TextView) shareView.findViewById(R.id.btn_shareToFriends);
//        btnShareToWb = (TextView) shareView.findViewById(R.id.btn_shareToWeibo);
//        btnCopyLink = (TextView) shareView.findViewById(R.id.btn_copyLink);
//        shareDialog = new AlertDialog.Builder(context)
//                .setView(shareView)
//                .create();
//
//        btnShareToWx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shareDialog.dismiss();
//                mShareManager.wechatShare(0);
//
//
//            }
//        });
//
//        btnShareToFriends.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shareDialog.dismiss();
//                mShareManager.wechatShare(1);
//            }
//        });
//
//        btnShareToWb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shareDialog.dismiss();
//                mShareManager.sendSingleMessage();
//            }
//        });
//
//        btnCopyLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//                ClipData clipData = ClipData.newPlainText("text", shareModel.getmShareUrl());
//                cm.setPrimaryClip(clipData);
//                shareDialog.dismiss();
//                T.showShort(context, "复制成功");
//
//            }
//        });
//
//        shareDialog.show();
//
//    }
//
//
//    @JavascriptInterface
//    public void getToken() {
//        showDialog();
//        MyApp
//                .getApiService()
//                .getH5Token()
//                .compose(RetrofitUtil.<String>applySchedulers())
//                .subscribe(RetrofitUtil.newSubscriber(activity, new RetrofitUtil.ActionRequest<String>() {
//                    @Override
//                    public void onError(String errorMsg, int statusCode) {
//                        showToastMessage(errorMsg);
//                    }
//
//                    @Override
//                    public void finished() {
//                        dismisDialog();
//                    }
//
//                    @Override
//                    public void call(final String s) {
//
//                        mWebView.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                mWebView.loadUrl("javascript:getTokenReturn('" + s + "')");
//                            }
//                        });
//                    }
//                }));
//    }
//
//    @JavascriptInterface
//    public void getAccountToken(final String name) {
//        Log.d("jam", "tokenStr:" + name);
//        if (!Constant.isLogin(activity)) {
//            UserSession userSession = new UserSession();
//            userSession.setName(name);
//            try {
//                mWebView.loadUrl("javascript:getAccountTokenReturn('" + Base64Utils.encryptBASE64(new Gson().toJson(userSession).getBytes()) + "')");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return;
//        }
//        Observable.just(name)
//                .map(new Func1<String, UserSession>() {
//                    @Override
//                    public UserSession call(String s) {
//                        return new UserSession(AccountManager.sessionId(), AccountManager.accessToken(), name);
//                    }
//                })
//                .map(new Func1<UserSession, String>() {
//                    @Override
//                    public String call(UserSession userSession) {
//                        Gson gson = new Gson();
//                        return gson.toJson(userSession);
//                    }
//                })
//                .map(new Func1<String, String>() {
//                    @Override
//                    public String call(String s) {
//                        try {
//                            return Base64Utils.encryptBASE64(s.getBytes());
//                        } catch (Exception e) {
//                            return "";
//                        }
//                    }
//                })
//                .subscribe(RetrofitUtil.newSubscriber(activity, new RetrofitUtil.ActionRequest<String>() {
//                    @Override
//                    public void onError(String errorMsg, int statusCode) {
//                        showToastMessage(errorMsg);
//                    }
//
//                    @Override
//                    public void finished() {
//
//                    }
//
//                    @Override
//                    public void call(final String s) {
//                        Log.d("jam", "token:" + s);
//                        mWebView.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                mWebView.loadUrl("javascript:getAccountTokenReturn('" + s + "')");
//                            }
//                        });
//                    }
//                }));
//    }
//
//
//    @JavascriptInterface
//    public void refreshToken(final String name) {
//        showDialog();
//        if (!Constant.isLogin(activity)) {
//            UserSession userSession = new UserSession();
//            userSession.setName(name);
//            try {
//                mWebView.loadUrl("javascript:getAccountTokenReturn('" + Base64Utils.encryptBASE64(new Gson().toJson(userSession).getBytes()) + "')");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return;
//        }
//        MyApp
//                .getApiService()
//                .refreshToken(AccountManager.sessionId(), AccountManager.refreshToken())
//                .compose(RetrofitUtil.<UserSession>applySchedulers())
//                .subscribe(RetrofitUtil.newSubscriber(activity, new RetrofitUtil.ActionRequest<UserSession>() {
//                    @Override
//                    public void onError(String errorMsg, int statusCode) {
//                        showToastMessage(errorMsg);
//                    }
//
//                    @Override
//                    public void finished() {
//                        dismisDialog();
//                    }
//
//                    @Override
//                    public void call(final UserSession userSession) {
//                        AccountManager.refreshAccount(userSession);
//                        userSession.setRefreshToken(null);
//                        userSession.setName(name);
//                        mWebView.post(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                try {
//                                    Log.d("refreshToken", Base64Utils.encryptBASE64(new Gson().toJson(userSession).getBytes()));
//                                    mWebView.loadUrl("javascript:getAccountTokenReturn('" + Base64Utils.encryptBASE64(new Gson().toJson(userSession).getBytes()) + "')");
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                    }
//                }));
//    }
//
//
//    @JavascriptInterface
//    public void toPay(String message) {
//        YWMJCashierActivity.start(activity, message);
//    }
//
//    @JavascriptInterface
//    public void openWebview(String url) {
//        OneWebActivity.start(activity, null, url, false);
//    }
//
//    /**
//     * 跳转到案例
//     *
//     * @param caseId
//     */
//    @JavascriptInterface
//    public void caseSkip(String caseId) {
//        Log.d("jam", "caseId:" + caseId);
//        StartActivityManager.startCaseDetailActivity(activity, Integer.valueOf(caseId));
//    }
//
//
//    @JavascriptInterface
//    public void getUserInfo() {
//        String phone = Constant.getUserPhone(activity);
//        if (TextUtils.isEmpty(phone)) {
//            return;
//        }
//        PhoneBean phoneBean = new PhoneBean();
//        phoneBean.setPhone(phone);
//        phone = new Gson().toJson(phoneBean);
//        try {
//            mWebView.loadUrl("javascript:getUserInfoReturn('" + Base64Utils.encryptBASE64(phone.getBytes()) + "')");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @JavascriptInterface
//    public void payResult(String isSuccess) {
//        dismisDialog();
//        Intent intent = new Intent();
//        intent.putExtra(PAY_SUCCESS_KEY, isSuccess.equals("true") ? true : false);
//        activity.setResult(Activity.RESULT_OK, intent);
//        activity.finish();
//    }
//
//
//    @JavascriptInterface
//    public void invokeNative(String jsonParam) {
//        Log.d("Jam", "=====jsonParam" + jsonParam);
//        JsBridgeInfo bridgeInfo = getJsBridgeBean(jsonParam);
//        currentJsbridgeInfo = bridgeInfo;
//        String data = null;
//        try {
//            data = getDataJson(jsonParam);
//        } catch (Exception e) {
//
//        }
//        switch (bridgeInfo.code) {
//            case Config.CODE_DIALOG_SHOW:
//                //显示加载圈
//                showDialog();
//                break;
//            case Config.CODE_DIALOG_HIDE:
//                //隐藏加载圈
//                dismisDialog();
//                break;
//            case Config.CODE_CLOSE:
//                //关闭页面
//                ActivityCompat.finishAfterTransition(activity);
//                break;
//            case Config.CODE_TOAST:
//                //显示弹窗消息
//                showToastMessage((String) bridgeInfo.data);
//                break;
//            case Config.CODE_LOGIN:
//                //去登陆页面
//                neenLoginCallback = true;
//                LoginActivity.start(activity, "h5", true);
//                break;
//            case Config.CODE_SHARE:
//                //分享
//                if (!TextUtils.isEmpty(data))
//                    shareActivity(data);
//                break;
//            case Config.CODE_PAY:
//                //去支付
//                if (!TextUtils.isEmpty(data))
//                    YWMJCashierActivity.start(activity, data);
//                break;
//            case Config.CODE_OPEN:
//                //打开新的WebView页面
//                if (!TextUtils.isEmpty(data))
//                    OneWebActivity.start(activity, null, data, false);
//                break;
//            case Config.CODE_PAY_RESULT:
//                //支付完成
//                if (!TextUtils.isEmpty(data)) {
//                    dismisDialog();
//                    Intent intent = new Intent();
//                    intent.putExtra(PAY_SUCCESS_KEY, data.equals("true") ? true : false);
//                    activity.setResult(Activity.RESULT_OK, intent);
//                    activity.finish();
//                }
//                break;
//            case Config.CODE_GET_SINGLE_PHOTO:
//                //获取一张图片
//                getSinglePhoto();
//                break;
//            case Config.CODE_OPEN_CASE_DETIAL:
//                //打开作品详情
//                StartActivityManager.startCaseDetailActivity(activity, (Integer) bridgeInfo.data);
//                break;
//            case Config.CODE_CHOOSE_ADRESS:
//                //获取地址
//                chooseAddress(data);
//                break;
//            case Config.CODE_GET_CONTACT_INFO:
//                //获取联系人信息
//                getContactInfo();
//                break;
//            case Config.CODE_PICK_SINGLE_STING:
//                singleChoosePick(data);
//                break;
//            case Config.CODE_CALL_PHONE_NUMBER:
//                if (data != null)
//                    CallUtils.callPhone(data, activity);
//                break;
//            case Config.CODE_ORDER_PAY_MESSAGE:
//
//                break;
//            case Config.CODE_ORDER_BILL_PAY:
//                YWMJCashierActivityForOrder.start(activity, data);
//                break;
//            case Config.CODE_GET_BILL_ID:
//                currentJsbridgeInfo.data = SPUtils.get(activity, "KEY_CURRENT_BILL_ID", "");
//                invokeH5(currentJsbridgeInfo);
//                break;
//        }
//    }
//
//    /**
//     * 打开通讯了获取联系人信息
//     */
//    public void getContactInfo() {
//        Intent i = new Intent();
//        i.setAction(Intent.ACTION_PICK);
//        i.setData(ContactsContract.Contacts.CONTENT_URI);
//        activity.startActivityForResult(i, Config.CODE_GET_CONTACT_INFO);
//    }
//
//    private void singleChoosePick(String data) {
//        Log.d("jam---", "" + data);
//        Gson gson = new Gson();
//        final JsSingleChooseBean jsSingleChooseBean = gson.fromJson(data, JsSingleChooseBean.class);
//        Log.d("jam---", "jsSingleChooseBean" + jsSingleChooseBean.getTitle());
//        Observable.just(jsSingleChooseBean.getArray())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(new Func1<List<JsSingleChooseBean.ArrayBean>, List<String>>() {
//                    @Override
//                    public List<String> call(List<JsSingleChooseBean.ArrayBean> emnuses) {
//                        List<String> list = new ArrayList<String>();
//                        for (JsSingleChooseBean.ArrayBean b : emnuses) {
//                            list.add(b.getDesc());
//                        }
//                        return list;
//                    }
//                })
//                .map(new Func1<List<String>, OptionPicker>() {
//                    @Override
//                    public OptionPicker call(List<String> strings) {
//                        if (picker == null) {
//                            picker = new OptionPicker(activity, strings);
//                            picker.setCancelTextColor(activity.getResources().getColor(R.color.color_6));
//                            picker.setCancelText(R.string.btn_cancel);
//                            picker.setCancelTextSize(16);
//                            picker.setSubmitTextColor(activity.getResources().getColor(R.color.color_6));
//                            picker.setSubmitText(R.string.btn_confirm);
//                            picker.setTextColor(activity.getResources().getColor(R.color.color_6), activity.getResources().getColor(R.color.color_5));
//                            picker.setSubmitTextSize(16);
//                            picker.setCycleDisable(true);
//                            picker.setHeight(ScreenUtils.getScreenHeight() / 3);
//                            picker.setLineColor(activity.getResources().getColor(R.color.color_3));
//                            picker.setSelectedIndex(0);
//                            picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
//                                @Override
//                                public void onOptionPicked(int position, String option) {
//                                    Log.d("jam", "jsSingleChooseBean.getArray():" + jsSingleChooseBean.getArray().size());
//
//                                    Log.d("jam", "position:" + position);
//                                    currentJsbridgeInfo.data = jsSingleChooseBean.getArray().get(position);
//                                    invokeH5(currentJsbridgeInfo);
//                                }
//                            });
//                        }
//                        return picker;
//                    }
//                })
//                .subscribe(new Action1<OptionPicker>() {
//                    @Override
//                    public void call(OptionPicker optionPicker) {
//                        optionPicker.show();
//                    }
//                });
//    }
//
//    private String getDataJson(String jsonParam) {
//        String data = null;
//        try {
//            JSONObject jsonObject = new JSONObject(jsonParam);
//            if (jsonObject != null)
//                data = jsonObject.getString("data");
//            Log.d("jam", data);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return data;
//    }
//
//
//    public JsBridgeInfo getJsBridgeBean(String jsonParam) {
//        if (gson == null) gson = new Gson();
//        JsBridgeInfo bridgeInfo = gson.fromJson(jsonParam, JsBridgeInfo.class);
//        return bridgeInfo;
//    }
//
//    public void getSinglePhoto() {
//        if (mCameraSdkParameterInfo == null) {
//            mCameraSdkParameterInfo = new CameraSdkParameterInfo();
//            mCameraSdkParameterInfo.setFilter_image(false);
//            mCameraSdkParameterInfo.setShow_camera(true);
//            mCameraSdkParameterInfo.setSingle_mode(true);
//            mCameraSdkParameterInfo.setCroper_image(true);
//            mCameraSdkParameterInfo.setCroper_image_rectangle(true);
//        }
//        Intent intent = new Intent();
//        intent.setClassName(activity.getApplication(), "com.muzhi.camerasdk.PhotoPickActivity");
//        Bundle b = new Bundle();
//        b.putSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER, mCameraSdkParameterInfo);
//        intent.putExtras(b);
//        ActivityCompat.startActivityForResult(activity, intent, CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY, null);
//    }
//
//
//    public void chooseAddress(String data) {
//
//        JsAreaCallbackBean jsAreaCallbackBean = null;
//        if (TextUtils.isEmpty(data)) {
//            Gson gson = new Gson();
//            jsAreaCallbackBean = gson.fromJson(data, JsAreaCallbackBean.class);
//        }
//
//        Observable
//                .just(jsAreaCallbackBean)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(new Func1<JsAreaCallbackBean, AddressPicker>() {
//                    @Override
//                    public AddressPicker call(JsAreaCallbackBean jsAreaCallbackBean) {
//                        ArrayList<Province> provinceArrayList = getProvinces();
//                        return createAddressPick(provinceArrayList, jsAreaCallbackBean);
//                    }
//                })
//                .subscribe(new Subscriber<AddressPicker>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(AddressPicker addressPicker) {
//                        addressPicker.show();
//                    }
//                });
//    }
//
//    private AddressPicker createAddressPick(ArrayList<Province> provinces, JsAreaCallbackBean jsAreaCallbackBean) {
//        if (addressPicker == null) {
//            addressPicker = new AddressPicker(activity, provinces);
//            addressPicker.setCancelTextColor(activity.getResources().getColor(R.color.color_6));
//            addressPicker.setCancelText(R.string.btn_cancel);
//            addressPicker.setCancelTextSize(16);
//            addressPicker.setSubmitTextColor(activity.getResources().getColor(R.color.color_6));
//            addressPicker.setSubmitText(R.string.btn_confirm);
//            addressPicker.setTextColor(activity.getResources().getColor(R.color.color_6), activity.getResources().getColor(R.color.color_5));
//            addressPicker.setSubmitTextSize(16);
//            addressPicker.setCycleDisable(true);
//            addressPicker.setHeight(ScreenUtils.getScreenHeight() / 3);
//            addressPicker.setLineColor(activity.getResources().getColor(R.color.color_3));
//            addressPicker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
//                @Override
//                public void onAddressPicked(Province province, City city, County county) {
//                    JsAreaCallbackBean jsAreaCallbackBean = new JsAreaCallbackBean(
//                            new JsAreaCallbackBean.ProvinceBean(province.getAreaName(), Integer.valueOf(province.getAreaId())),
//                            new JsAreaCallbackBean.CityBean(city.getAreaName(), Integer.valueOf(city.getAreaId())),
//                            new JsAreaCallbackBean.AreaBean(county.getAreaName(), Integer.valueOf(county.getAreaId()))
//                    );
//                    JsBridgeInfo<JsAreaCallbackBean> jsAreaCallbackBeanJsBridgeInfo = currentJsbridgeInfo;
//                    jsAreaCallbackBeanJsBridgeInfo.data = jsAreaCallbackBean;
//                    invokeH5(jsAreaCallbackBeanJsBridgeInfo.callback, jsAreaCallbackBeanJsBridgeInfo);
//                }
//            });
//            addressPicker.setSelectedItem("四川省", "成都市", "锦江区");
//        } else {
//            if (jsAreaCallbackBean != null)
//                addressPicker.setSelectedItem(jsAreaCallbackBean.getProvince().getName(), jsAreaCallbackBean.getCity().getName(), jsAreaCallbackBean.getArea().getName());
//        }
//        return addressPicker;
//    }
//
//
//    public void invokeH5(final String callbackName, final Object o) {
//        Log.d("jam", "web" + mWebView);
//        mWebView.post(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    mWebView.loadUrl("javascript:" + callbackName + "('" + Base64Utils.encryptBASE64(new Gson().toJson(o).getBytes()) + "')");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    /**
//     * 登录回调H5
//     */
//    public void invokeH5ForLogin(boolean loginSuccess) {
//        if (neenLoginCallback) {
//            neenLoginCallback = false;
//            currentJsbridgeInfo.data = loginSuccess;
//            invokeH5(currentJsbridgeInfo);
//        }
//    }
//
//    public void invokeH5(Object o) {
//        invokeH5(currentJsbridgeInfo.callback, o);
//    }
//
//
//    private ArrayList<Province> getProvinces() {
//        String addressJson = null;
//        try {
//            addressJson = ConvertUtils.toString(activity.getAssets().open("allarea.json"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        String addressJsonLast = addressJson.replace("id", "areaId").replace("name", "areaName").replace("areas", "counties");
//
//        Gson gson = new Gson();
//        ArrayList<Province> provinces = gson.fromJson(addressJsonLast, new TypeToken<List<Province>>() {
//        }.getType());
//
//        return provinces;
//    }

}
