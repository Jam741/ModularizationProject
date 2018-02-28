package com.yingwumeijia.baseywmj.utils;

import java.math.BigDecimal;

/**
 * Created by Jam on 2017/3/7 下午4:57.
 * Describe:
 */

public class MoneyFormatUtils {


    public static BigDecimal fromatWan(BigDecimal bigDecimal) {

        BigDecimal b2 = new BigDecimal(10000);

        return bigDecimal.divide(b2, 2,BigDecimal.ROUND_HALF_DOWN);
    }
}
