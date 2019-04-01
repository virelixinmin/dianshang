package com.bw.project_demo.data;

import android.app.Application;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;

import com.bw.project_demo.data.utils.BugHandler;
import com.bw.project_demo.data.utils.LogUtil;
import com.bw.project_demo.data.utils.SysCfgInfo;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.tencent.bugly.crashreport.CrashReport;

public class MyApp extends Application {
    private static MyApp app;

    public static SysCfgInfo config = new SysCfgInfo();

    private long onTouchTime;
    @Override
    public void onCreate() {
        super.onCreate();
        super.onCreate();
        //设置磁盘缓存
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryName("images")
                .setBaseDirectoryPath(Environment.getExternalStorageDirectory())
                .build();
        //设置磁盘缓存的配置生成文件
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();

        Fresco.initialize(this,config);

        app=this;
        BugHandler.getInstance().init(this,null,null);
        Thread.setDefaultUncaughtExceptionHandler(BugHandler.getInstance());
        CrashReport.initCrashReport(getApplicationContext(), "06bde6fed3", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }
    public static MyApp getApp() {
        return app;
    }
    public void setTouchTime(long time){
        LogUtil.e( "tag",time +"   点击屏幕时间 --- " );
        this.onTouchTime = time;
    }
    public long getOnTouchTime() {
        return onTouchTime;
    }
}
