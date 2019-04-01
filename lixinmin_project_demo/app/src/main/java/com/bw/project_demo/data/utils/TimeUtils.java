package com.bw.project_demo.data.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    public static String longToDate(long lo) {

        Date date = new Date(lo);

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sd.format(date);
    }

}
