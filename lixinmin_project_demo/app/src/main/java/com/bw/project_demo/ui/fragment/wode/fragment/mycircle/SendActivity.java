package com.bw.project_demo.ui.fragment.wode.fragment.mycircle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.project_demo.R;

import com.bw.project_demo.data.contractPath.CheckPath;
import com.bw.project_demo.data.contractPath.ServiceApp;
import com.bw.project_demo.data.utils.RetrofitUtils;
import com.bw.project_demo.ui.fragment.wode.fragment.mycircle.circleConstance.CircleConstance;
import com.whyalwaysmea.circular.AnimUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class SendActivity extends AppCompatActivity {

    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.btn_img)
    Button btnImg;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.img_quanzi)
    ImageView img;
    @BindView(R.id.back_img)
    ImageView backImg;

    private int userId;
    private String sessionId;
    String commid = "29";
    private View ll;
    private List<Object> files = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        ButterKnife.bind(this);


        SharedPreferences data = getSharedPreferences("data", Context.MODE_PRIVATE);
        userId = data.getInt("userId", 184);
        sessionId = data.getString("sessionId", "15011445417");

        AnimUtils.animhpel((Activity) this,R.id.ll);

        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_GET_CONTENT);
                in.setType("image/*");
                startActivityForResult(in, 100);
            }
        });
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void InterNet(List<Object> files) {
        String s = editContent.getText().toString();

        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("userId", userId + "");
        objectObjectHashMap.put("sessionId", sessionId);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int i = 0; i <files.size() ; i++) {
            File file = (File) files.get(i);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("image", file.getName(), requestBody);
        }
        builder.addFormDataPart("commodityId", commid)
                .addFormDataPart("content", s)
                .build();
        MultipartBody build1 = builder.build();
        List<MultipartBody.Part> parts = build1.parts();
        RetrofitUtils.getRetrofitUtils().getApiService(CheckPath.allString,ServiceApp.class)
                .getResponseData(objectObjectHashMap,parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String s1 = responseBody.string();
                        Toast.makeText(SendActivity.this, s1, Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(SendActivity.this, "throwable:" + throwable, Toast.LENGTH_SHORT).show();
                        Log.d("SendActivity", throwable.getMessage());
                    }
                });
    }
//    IRequest iRequest = NotWorkUtils.getInstance().create(IRequest.class);
//    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//        builder.addFormDataPart("content", String.valueOf(args[2]));
//    List<Object> list = (List<Object>) args[3];
//        if (list.size()>0) {
//        for (int i = 0; i < list.size(); i++) {
//            File file = (File) list.get(i);
//            builder.addFormDataPart("file", file.getName(),
//                    RequestBody.create(MediaType.parse("multipart/octet-stream"), file));
//        }
//    }
//        return iRequest.fabuquanzi((int)args[0],(String)args[1],builder.build());

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            try {
                Uri data1 = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data1);
                img.setImageBitmap(bitmap);
                final File file = compressImage(bitmap);
                Toast.makeText(this, "file:" + file, Toast.LENGTH_SHORT).show();
                files.add(file);
                btnSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InterNet(files);
                        finish();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    public static File compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
        }
        //以当前时间命名
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        //图片名
        String filename = format.format(date);
        //存储到外存空间
        File file = new File(Environment.getExternalStorageDirectory(), filename + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }


}
