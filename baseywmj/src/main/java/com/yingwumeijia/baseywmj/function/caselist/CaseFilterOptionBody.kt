package com.yingwumeijia.baseywmj.function.caselist

/**
 * Created by Jam on 2017/5/4 上午9:55.
 * Describe:
 */

class CaseFilterOptionBody {

    //v1.3.4 作品类型，1为竣工作品，2为设计作品
    var caseType = 1
    //
    var decorateType = 0
    //装修风格(byte)
    var style = 0//装修风格(byte)
    //房型
    var houseType = 0
    //面积类型
    var areaType = 0
    //查询关键字
    var queryKey: String? = null
    //城市id
    var cityType = 0
    //是否是刷新操作（true = 是刷新操作，false = 查询操作）
    var refresh = true

    fun reset() {
        caseType = 1
        decorateType = 0
        style = 0
        houseType = 0
        areaType = 0
        queryKey = null
        cityType = 0
        refresh = true
    }
}
