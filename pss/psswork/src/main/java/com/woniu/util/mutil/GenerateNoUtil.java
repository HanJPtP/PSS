package com.woniu.util.mutil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  自动生成编号
 */
public class GenerateNoUtil {

    /**
     *  根据时间自动生成orderTaskNo 派工单编号
     * @return
     */
    public static String getOrderTaskNo(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String str1 = sdf.format(date);
        String orderTaskNo = "OT" + str1;
        return orderTaskNo;
    }

    public static String getStaffNo(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String str = sdf.format(date);
        return str;
    }

}
