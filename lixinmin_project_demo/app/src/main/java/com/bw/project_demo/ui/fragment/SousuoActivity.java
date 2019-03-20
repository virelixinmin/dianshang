package com.bw.project_demo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

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
    TagFlowLayout tab;


    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private static final String TAG = SousuoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sousuo);
        ButterKnife.bind(this);


        huoqu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit.getText().toString().equals("")) {
                    Toast.makeText(SousuoActivity.this, "搜索框不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Intent in = new Intent(SousuoActivity.this, SearchActivity.class);
                    in.putExtra("search_name", edit.getText().toString());
                    startActivity(in);
                }

            }
        });
        final List<String> list = new ArrayList<>();
        list.add("鞋子");
        list.add("女装");
        list.add("男装");
        list.add("T恤衫");
        list.add("dasdasdsadas");
        list.add("android");
        list.add("css");
        list.add("祝我升班");
        list.add("叶志成末班");

        tab.setAdapter(new TagAdapter<String>(list){

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView inflate = (TextView) View.inflate(SousuoActivity.this, R.layout.tv, null);
                inflate.setText(s);

                return inflate;
            }
        });

        tab.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Intent in = new Intent(SousuoActivity.this, SearchActivity.class);
                in.putExtra("search_name", list.get(position).toString());
                startActivity(in);

                return true;
            }
        });
        neirong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpeechDialog();
            }
        });

        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5c89a984");

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
}
