package com.yingwumeijia.baseywmj.entity.bean


import com.google.gson.annotations.SerializedName

/**
 * Created by Jam on 16/6/22 上午11:37.
 * Describe:
 */
class CaseTypeEnum {

    @SerializedName("code")
    var id: Int = 0
    var categoryCode: Int = 0

    @SerializedName("desc")
    var name: String? = null
    var isSelected: Boolean = false
    var count = -1
}
