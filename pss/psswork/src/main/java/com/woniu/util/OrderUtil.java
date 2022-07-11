package com.woniu.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderUtil {
    /**
     * 生成请假的编号
     * @return
     */
    public static String getLeaveNo(){
        String leaveNo="QJ";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssssss");
        String format = sdf.format(new Date());
        leaveNo+=format;
        return leaveNo;
    }

    /**
     * 生成出差的编号
     * @return
     */
    public static String getEvectionNo(){
        String evectionNo="CC";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssssss");
        String format = sdf.format(new Date());
        evectionNo+=format;
        return evectionNo;
    }

    /**
     * 生成补卡的编号
     * @return
     */
    public static String getMakeUpCardNo(){
        String CardNo="BK";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssssss");
        String format = sdf.format(new Date());
        CardNo+=format;
        return CardNo;
    }

    /**
     * 生成打卡编号
     * @return
     */
    public static String getPunchNo(){
        String CardNo="DK";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssssss");
        String format = sdf.format(new Date());
        CardNo+=format;
        return CardNo;
    }
}
