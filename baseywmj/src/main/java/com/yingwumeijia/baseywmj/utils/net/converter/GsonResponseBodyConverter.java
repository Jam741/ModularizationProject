/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yingwumeijia.baseywmj.utils.net.converter;

import android.util.Log;

import com.google.gson.TypeAdapter;
import com.yingwumeijia.baseywmj.utils.net.ApiModel;
import com.yingwumeijia.baseywmj.utils.net.ErrorCode;
import com.yingwumeijia.baseywmj.utils.net.exception.ApiException;
import com.yingwumeijia.baseywmj.utils.net.exception.TokenInvalidException;
import com.yingwumeijia.baseywmj.utils.net.exception.TokenIsRefreshEdException;
import com.yingwumeijia.baseywmj.utils.net.exception.TokenIsTimeMatterException;
import com.yingwumeijia.baseywmj.utils.net.exception.TokenNotExistException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, Object> {

    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public Object convert(ResponseBody value) throws IOException {
        try {
            ApiModel apiModel = (ApiModel) adapter.fromJson(value.charStream());
            if (apiModel.errorCode == ErrorCode.TOKEN_NOT_EXIST) {
//                LoginActivity.start();
                throw new TokenNotExistException();
            } else if (apiModel.errorCode == ErrorCode.TOKEN_INVALID) {
                Log.d("jam", "token 过期了");
                throw new TokenInvalidException();
            } else if (apiModel.errorCode == ErrorCode.TOKEN_REFRESHED) {
                throw new TokenIsRefreshEdException();
            } else if (apiModel.errorCode == ErrorCode.TOKEN_TIME_MATTER) {
                throw new TokenIsTimeMatterException();
            } else if (!apiModel.success) {
                // 特定 API 的错误，在相应的 Subscriber 的 onError 的方法中进行处理
                throw new ApiException(apiModel.errorCode, apiModel.message);
            } else if (apiModel.success) {
                return apiModel.data;
            }
        } finally {
            value.close();
        }
        return null;
    }
}
