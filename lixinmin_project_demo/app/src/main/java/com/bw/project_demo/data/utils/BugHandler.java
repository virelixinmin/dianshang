package com.bw.project_demo.data.utils;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;


import com.bw.project_demo.ui.activity.MainActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 捕捉bug，并写入日志文件 自定义的 异常处理类 , 实现了 UncaughtExceptionHandler接口
 *
 * @author lsw
 * @date 2014-3-31
 */

@SuppressLint("SimpleDateFormat")

public class BugHandler implements Thread.UncaughtExceptionHandler {
    private final String TAG = "BugHandler";
    // 需求是 整个应用程序 只有一个 MyCrash-Handler

    private static BugHandler myCrashHandler;
    private Context context;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(

            "yyyy-MM-dd HH:mm:ss");

    private String path;
    private String url;

    // 1.私有化构造方法
    private BugHandler() {
    }

    public static synchronized BugHandler getInstance() {

        if (myCrashHandler != null) {
            return myCrashHandler;
        } else {
            myCrashHandler = new BugHandler();
            return myCrashHandler;
        }

    }


    /**
     * @param context
     * @param path    生成日志文件的本地路径，默认在应用名称底下创建log文件夹，所有日志放在改文件夹
     * @param url     日志发送到服务器的url
     */

    public void init(Context context, String path, String url) {
        this.context = context;
        this.path = path;
        this.url = url;

    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        // 3.把错误的堆栈信息 获取出来
        String errorinfo = getErrorInfo(throwable);
//		outputFile(errorinfo);
//		outputFile(errorinfo,"/tmp");//将文件存放到临时目录下
//		LogUtil.e(errorinfo);
        //TODO 异常之后重启应用
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent restartIntent = PendingIntent.getActivity(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        //退出程序
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//		mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
//				restartIntent); // 1秒钟后重启应用
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void outputFile(String errorinfo) {
        // 1.获取当前程序的版本号. 版本的id
        String versioninfo = "app VERSION: " + getVersionInfo();
        // 2.获取手机的硬件信息.
        String mobileInfo = getMobileInfo();
        String date = dateFormat.format(new Date());

        // 4.创建日志文件，写入堆栈
        File file = null;
        File tmpFile = null;//写入临时文件夹
        if (path == null) {
            String dir = getDirPath();
            File parentFile = new File(dir);
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            String dateStr = date.replace(":", "").replace(" ", "T");
            String fileName = android.os.Build.MODEL + "(" + dateStr + ").log";
            path = dir + File.separator + fileName;
            file = new File(path);
            LogUtil.e("tag", file.getAbsolutePath() + "     ===========");
            //临时文件部分
            String dirtmp = getDirPath() + "/tmp";
            File tmpParentFile = new File(dirtmp);
            if (!tmpParentFile.exists()) {
                tmpParentFile.mkdirs();
            }
            String tmpPath = dirtmp + File.separator + fileName;
            tmpFile = new File(tmpPath);
            //////////////////////////////////////////////

        }
        FileOutputStream fos = null;
        try {
            String newline = System.getProperty("line.separator"); // 换行符
            fos = new FileOutputStream(file);
            fos.write(date.getBytes());
            fos.write(newline.getBytes());
            fos.write(versioninfo.toString().getBytes());
            fos.write(newline.getBytes());
            fos.write(mobileInfo.getBytes());
            fos.write("==================================================="
                    .getBytes());
            fos.write(newline.getBytes());
            fos.write(errorinfo.getBytes());
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fos = null;
            }

        }
        //拷贝日志文件到临时文件目录下
        try {
            copyFile(file, tmpFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        Log.e(TAG, errorinfo);
        Log.i(TAG, "日志文件路径：" + path);
        exit();
        // 干掉当前的程序
        // android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 复制文件
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @throws IOException
     */
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            sourceFile.delete();
            // 刷新此缓冲的输出流
            // outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    /**
     * 获取最后一个日志文件的文本信息
     *
     * @param
     */

    public String getTextFromLastFile() {
        String text = null;
        File file = getLastFile();
        if (file != null) {

            text = getTextFromFile(file);

        }
        return text;

    }

    /**
     * 读取文件里的文本内容
     *
     * @param file
     * @return
     */
    public String getTextFromFile(File file) {

        String text = null;
        BufferedReader bf = null;
        try {
            StringBuilder builder = new StringBuilder();
            InputStream in = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(in, "UTF-8");
            bf = new BufferedReader(reader);
            String line;
            while ((line = bf.readLine()) != null) {
                builder.append(line);
            }
            text = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                bf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return text;

    }


    /**
     * 删除文件夹下的创建时间比当前时间大 day 天的文件
     *
     * @param day
     * @return
     */

    public void deleteFilesMoreThenDay(int day) {

        String path = getDirPath();

        File file = new File(path);

        File[] files = file.listFiles();


        if (files != null) {

            for (File f : files) {

                Long time = f.lastModified();

                Calendar cd = Calendar.getInstance();

                String newDate = cd.getTime().toLocaleString();//系统当前时间

                cd.setTimeInMillis(time);

                String oldDate = cd.getTime().toLocaleString();//文件创建时间

                int length = dateDistance(newDate, oldDate);//相差多少天

                if (length >= day && !f.isDirectory()) {

                    f.delete();

                }

            }

        }

    }


    /**
     * 计算两个日期直接相差多少天
     *
     * @param oldDate 第一个日期
     * @param newDate 第二个日期
     * @return 相差天数
     */

    public int dateDistance(String newDate, String oldDate)

    {

        int day = 0;

        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd ");

        java.util.Date date;

        try {

            date = myFormatter.parse(newDate);

            java.util.Date mydate = myFormatter.parse(oldDate);

            day = (int) ((date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000));

        } catch (ParseException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        }

        return day;

    }


    /**
     * 删除日志文件夹下的所有日志，全部删除成功返回true
     *
     * @param extraPath 文件夹名称  参数格式 "/tmp"
     * @return
     */

    public boolean deleteFiles(String extraPath) {

        boolean isSuccess = true;

        String path = getDirPath() + extraPath;

        File file = new File(path);

        File[] files = file.listFiles();


        if (files != null) {

            for (File f : files) {

                if (!f.delete()) {

                    isSuccess = false;

                }

            }

        }

        return isSuccess;

    }


    /**
     * 删除日志文件夹下的所有日志，全部删除成功返回true
     */

    public boolean deleteFiles() {

        boolean isSuccess = true;

        String path = getDirPath();

        File file = new File(path);

        File[] files = file.listFiles();


        if (files != null) {

            for (File f : files) {

                if (!f.delete()) {

                    isSuccess = false;

                }

            }

        }

        return isSuccess;

    }


    /**
     * 获取日志文件夹下最后一个文件，该文件夹下没有日志文件返回null
     *
     * @return
     */

    private File getLastFile() {

//		String dir = getDirPath();

        String dir = getDirPath() + "/tmp";//现在获取tmp文件夹里面的最后一个文件

        File file = new File(dir);

        File[] files = file.listFiles();

        if (files != null) {

            int len = files.length;

            if (len > 0) {

                File f = files[len - 1];

                return f;

            }

        }

        return null;

    }


    public void sendLogToService() {

        if (url == null)

            return;


        new AlertDialog.Builder(context).setMessage("程序崩溃，是否发送日志到服务器？")

                .setTitle("提示")

                .setPositiveButton("确定", new DialogInterface.OnClickListener() {


                    @Override

                    public void onClick(DialogInterface dialog, int which) {

                        // 发送日志文件到服务器

                        // FileUploader.uploadFile("filedata", path, url);

                    }

                })

                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialog, int which) {

                    }

                }).show();

    }


    /**
     * 获取错误的信息
     *
     * @param arg1
     * @return
     */

    public String getErrorInfo(Throwable arg1) {

        Writer writer = new StringWriter();

        PrintWriter pw = new PrintWriter(writer);

        arg1.printStackTrace(pw);

        pw.close();

        String error = writer.toString();

        return error;

    }


    /**
     * 获取手机的硬件信息
     *
     * @return
     */

    public String getMobileInfo() {

        StringBuffer sb = new StringBuffer();

        sb.append("phone VERSION.SDK=" + Build.VERSION.SDK_INT);

        sb.append("\n");

        sb.append("phone VERSION.RELEASE=" + Build.VERSION.RELEASE);

        sb.append("\n");


        // 通过反射获取系统的硬件信息

        try {

            Field[] fields = Build.class.getDeclaredFields();

            for (Field field : fields) {

                // 暴力反射 ,获取私有的信息

                field.setAccessible(true);

                String name = field.getName();

                String value = field.get(null).toString();

                sb.append(name + "=" + value);

                sb.append("\n");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return sb.toString();

    }


    /**
     * 获取手机的版本信息
     *
     * @return
     */

    public String getVersionInfo() {

        try {

            PackageManager pm = context.getPackageManager();

            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);

            return info.versionName;

        } catch (Exception e) {

            e.printStackTrace();

            return "版本号未知";

        }

    }


    /**
     * 获取日志文件的根目录
     *
     * @return
     */

    public String getDirPath() {

        String path = null;

        // TODO 路径为 sdcard/包名/log

        // String name = getApplicationName();

        String name = context.getPackageName();

        // if (Environment.getExternalStorageState().equals(

        // Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中

        String mainPaths = Environment.getExternalStorageDirectory().getPath();

        path = mainPaths + SysCfgInfo.ROOTPATH + "/log";

//		BaseApplication app = (BaseApplication) context.getApplicationContext();

//		path = app.getSdCardPath() + File.separator + name + File.separator
//
//				+ "log";

        // } else {// 如果SD卡不存在，就保存到本应用的目录下

        // path = context.getFilesDir().getAbsolutePath() + File.separator

        // + name + File.separator + "log";

        // }


        return path;

    }


    private void exit() {

        if (context != null)

        {

//			BaseApplication app = (BaseApplication) context.getApplicationContext();

//			if(app != null)

//			{

//				app.exit();

//			}

        }

    }


    /**
     * 获取应用名称
     *
     * @return
     */

    public String getApplicationName() {

        PackageManager pm = null;

        ApplicationInfo info = null;

        try {

            pm = context.getApplicationContext().getPackageManager();

            info = pm.getApplicationInfo(context.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {

            info = null;

        }

        String name = (String) pm.getApplicationLabel(info);

        return name;

    }


}
