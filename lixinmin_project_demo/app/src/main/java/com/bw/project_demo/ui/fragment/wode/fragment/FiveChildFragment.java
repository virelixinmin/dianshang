package com.bw.project_demo.ui.fragment.wode.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.project_demo.R;
import com.bw.project_demo.ui.fragment.wode.fragment.person.PersonBean;
import com.bw.project_demo.ui.fragment.wode.fragment.person.PersonConstance.PersonConstance;
import com.bw.project_demo.ui.fragment.wode.fragment.person.PersonPresenter.PersonPresenterImpl;
import com.bw.project_demo.ui.fragment.wode.fragment.person.ServiceApp.PersonServiceApp;
import com.bw.project_demo.ui.fragment.wode.fragment.person.constanccc;
import com.bw.project_demo.ui.fragment.wode.fragment.person.person_name;
import com.bw.project_demo.ui.fragment.wode.fragment.person.person_pwd;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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

public class FiveChildFragment extends Fragment implements PersonConstance.PersonView {
    @BindView(R.id.chile_my_person_image)
    SimpleDraweeView chileMyPersonImage;
    @BindView(R.id.chile_my_person_image_up)
    RelativeLayout chileMyPersonImageUp;
    @BindView(R.id.chile_my_person_name)
    TextView chileMyPersonName;
    @BindView(R.id.chile_my_person_name_update)
    RelativeLayout chileMyPersonNameUpdate;
    @BindView(R.id.chile_my_person_password)
    TextView chileMyPersonPassword;
    @BindView(R.id.chile_my_person_password_update)
    RelativeLayout chileMyPersonPasswordUpdate;
    Unbinder unbinder;

    private AlertDialog alertDialog;

    private TextView xiangce;
    private TextView paizhao;
    private PersonConstance.PersonPresenter presenter;
    private int userId;
    private String sessionId;
    String[] permiss={Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getActivity(), R.layout.five_child, null);
        unbinder = ButterKnife.bind(this, inflate);
        SharedPreferences data = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        userId = data.getInt("userId", 5);
        sessionId = data.getString("sessionId", "15011445417");
        initView();
        presenter = new PersonPresenterImpl();
        presenter.attagView(this);
        presenter.requestModel(userId, sessionId);


        return inflate;
    }

    private void initView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.cature, null);
        alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setView(view);
        xiangce = view.findViewById(R.id.xiangce);
        paizhao = view.findViewById(R.id.paizhao);

        xiangce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i, 100);
            }
        });
        paizhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(in, 111);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.chile_my_person_image_up, R.id.chile_my_person_name, R.id.chile_my_person_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.chile_my_person_image_up:
                alertDialog.show();
                break;
            case R.id.chile_my_person_name:
                Intent in = new Intent(getActivity(), person_name.class);
                startActivity(in);

                //person_name person_name = new person_name();
//                person_name.setOnClickListen(new person_name.CallBack() {
//                    @Override
//                    public void callBack(String s) {
//                        chileMyPersonName.setText(s);
//                    }
//                });
                break;
            case R.id.chile_my_person_password:
                Intent i = new Intent(getActivity(), person_pwd.class);
                i.putExtra("pwd", chileMyPersonPassword.getText().toString());
                startActivity(i);
                break;

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
//            Uri data1 = data.getData();
//            String path = ImageUtil.getPath(getActivity(), data1);
//            file = new File(path);
//            chileMyPersonImage.setImageURI(data1);
            Bundle extras = data.getExtras();
            Parcelable data1 = extras.getParcelable("data");


        }
        if (requestCode == 100) {

//            addHeaderPhoto(uri);
            try {
                Uri uri = data.getData();
//                chileMyPersonImage.setImageURI(uri);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                File file = compressImage(bitmap);
                addHeaderPhoto(file);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public void showData(PersonBean personBean) {
        Log.d("FiveChildFragment", "personBean:" + personBean);
        chileMyPersonName.setText(personBean.getResult().getNickName());
        chileMyPersonPassword.setText(personBean.getResult().getPassword());
        String headPic = personBean.getResult().getHeadPic();
        Uri parse = Uri.parse(headPic);
        chileMyPersonImage.setImageURI(parse);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.requestModel(userId, sessionId);
    }

    public void addHeaderPhoto(File file) {
//        //将uri类型转换成file
//        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
//        cursor.moveToFirst();
//        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//        String string = cursor.getString(columnIndex);
//        File file = new File(string);

        //将file类型转换成part
        final RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);


        Map<String, String> map = new HashMap<>();
        map.put("userId", userId + "");
        map.put("sessionId", sessionId);
        Retrofit build = new Retrofit.Builder()
                .baseUrl(constanccc.urlPersonString)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        PersonServiceApp personServiceApp = build.create(PersonServiceApp.class);
        MultipartBody image = new MultipartBody.Builder().addFormDataPart("image", file.getName(), requestBody).build();
        List<MultipartBody.Part> parts = image.parts();

        Observable<ResponseBody> imageData = personServiceApp.getImageData(map, part);
        imageData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String s = responseBody.string().toString();
                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        String message = throwable.getMessage();
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                });
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
