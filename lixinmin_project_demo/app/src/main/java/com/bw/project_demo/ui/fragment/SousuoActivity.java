package com.bw.project_demo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.EventLog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bw.project_demo.R;
import com.bw.project_demo.ui.fragment.search.SearchActivity;
import com.bw.project_demo.ui.fragment.widght.JsonParse;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SousuoActivity extends AppCompatActivity {

    @BindView(R.id.neirong)
    ImageButton neirong;
    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.huoqu)
    ImageButton huoqu;
    @BindView(R.id.tab)
    FlowLayoutView tab;
    @BindView(R.id.clearData)
    Button clearData;


    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private static final String TAG = SousuoActivity.class.getSimpleName();
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sousuo);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initData();
        list.add("饿了么");
        list.add("美团外卖炸鸡送");
        list.add("初闻不知曲中意,再遇已是曲中人");
        tab.addData(list);


        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5c89a984");

    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getEvent(String event){

    }

    private void initData() {

        huoqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit.getText().toString().equals("")) {
                    Toast.makeText(SousuoActivity.this, "搜索框不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    list.add(edit.getText().toString());
                    Intent in = new Intent(SousuoActivity.this, SearchActivity.class);
                    in.putExtra("search_name", edit.getText().toString());
                    startActivity(in);

                }

            }
        });

        neirong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpeechDialog();
            }
        });

        tab.setFlowLayoutListener(new FlowLayoutView.FlowLayoutListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent in = new Intent(SousuoActivity.this, SearchActivity.class);
                in.putExtra("search_name", list.get(position).toString());
                startActivity(in);
            }
        });
        clearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SousuoActivity.this, "点击了", Toast.LENGTH_SHORT).show();
                list.clear();

            }
        });
    }



    private void startSpeechDialog() {

        //1. 创建RecognizerDialog对象

        RecognizerDialog mDialog = new RecognizerDialog(this, new MyInitListener());

        //2. 设置accent、 language等参数

        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");// 设置中文

        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");

        // 若要将UI控件用于语义理解，必须添加以下参数设置，设置之后 onResult回调返回将是语义理解

        // 结果

        // mDialog.setParameter("asr_sch", "1");

        // mDialog.setParameter("nlp_version", "2.0");

        //3.设置回调接口

        mDialog.setListener(new MyRecognizerDialogListener());

        //4. 显示dialog，接收语音输入

        mDialog.show();

    }

    class MyInitListener implements InitListener {


        @Override

        public void onInit(int code) {

            if (code != ErrorCode.SUCCESS) {

                showTip("初始化失败 ");

            }


        }

    }

    class MyRecognizerDialogListener implements RecognizerDialogListener {


        /**
         * @param results
         * @param isLast  是否说完了
         */

        @Override

        public void onResult(RecognizerResult results, boolean isLast) {

            String result = results.getResultString(); //为解析的

            showTip(result);

            System.out.println(" 没有解析的 :" + result);


            String text = JsonParse.parseIatResult(result);//解析过后的

            System.out.println(" 解析后的 :" + text);


            String sn = null;

            // 读取json结果中的 sn字段

            try {

                JSONObject resultJson = new JSONObject(results.getResultString());

                sn = resultJson.optString("sn");

            } catch (JSONException e) {

                e.printStackTrace();

            }


            mIatResults.put(sn, text);//没有得到一句，添加到


            StringBuffer resultBuffer = new StringBuffer();

            for (String key : mIatResults.keySet()) {

                resultBuffer.append(mIatResults.get(key));

            }


            edit.setText(resultBuffer.toString());// 设置输入框的文本

            edit.setSelection(edit.length());//把光标定位末尾

        }


        @Override

        public void onError(SpeechError speechError) {


        }

    }

    private void showTip(String data) {

        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
