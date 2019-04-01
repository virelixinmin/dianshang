package com.bw.project_demo.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.project_demo.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.Nullable;

public class FlowLayoutView extends ViewGroup implements View.OnClickListener{
    //这是记录所有字view 的集合
    private List<List<View>> listview =  new ArrayList<List<View>>();
    //这是记录每一行的最大高度
    private List<Integer> listheight =  new ArrayList<>();
    //这是回调方法的接口
    private FlowLayoutListener mFlowLayoutListener;

    public FlowLayoutView(Context context) {
        super(context);
    }

    public FlowLayoutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        listview.clear();
//        listheight.clear();
//
//        //获取当前父控件给子控件的大小和模式
//        int widthsize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightsize = MeasureSpec.getSize(heightMeasureSpec);
//        int widthmode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightmode = MeasureSpec.getMode(heightMeasureSpec);
//
//        //每一行的list
//        ArrayList<View> list = new ArrayList<>();
//
//        //如果是warp_content的话 那么就记录宽和高
//        int width=0;
//        int height=0;
//
//        //记录每一行的宽度  width不断取最大宽度
//        int lineWidth=0;
//
//        //每一行的高度 累加至height
//        int lineHeight=0;
//
//        //当前控件的宽度
//        int ccount = getChildCount();
//
//        for (int i = 0; i < ccount; i++) {
//
//        }
        listheight.clear();

        listview.clear();



        // 获取当前父容器给当前控件的大小和模式

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);

        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);

        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);



        //每一行的List

        List<View> lineView = new ArrayList<>();



        //   Log.e(TAG, sizeWidth + "," + sizeHeight);



        // 如果是warp_content情况下，记录宽和高

        int width = 0;

        int height = 0;



        /**

         * 记录每一行的宽度，width不断取最大宽度

         */

        int lineWidth = 0;

        /**

         * 每一行的高度，累加至height

         */

        int lineHeight = 0;



        /**

         * 当前控件的宽度

         */

        int cCount = getChildCount();



        // 遍历每个子元素

        for (int i = 0; i < cCount; i++)

        {

            View child = getChildAt(i);

            // 测量每一个child的宽和高

            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            // 得到child的lp

            MarginLayoutParams lp = (MarginLayoutParams) child

                    .getLayoutParams();



            // 当前子空间实际占据的宽度

            int childWidth = child.getMeasuredWidth() + lp.leftMargin

                    + lp.rightMargin;

            // 当前子空间实际占据的高度

            int childHeight = child.getMeasuredHeight() + lp.topMargin

                    + lp.bottomMargin;

            /**

             * 如果加入当前child，则超出最大宽度，则的到目前最大宽度给width，类加height 然后开启新行

             */

            if (lineWidth + childWidth > sizeWidth)

            {



                width = Math.max(lineWidth,width);// 取最大的



                lineWidth = childWidth; // 重新开启新行，开始记录



                //记录View

                listview.add(lineView);

                lineView = new ArrayList<>();

                lineView.add(child);



                // 叠加当前高度，

                height += lineHeight;

                listheight.add(lineHeight);



                // 开启记录下一行的高度

                lineHeight = childHeight;

            } else

            // 否则累加值lineWidth,lineHeight取最大高度

            {

                lineView.add(child);

                lineWidth += childWidth;

                lineHeight = Math.max(lineHeight, childHeight);

            }

            // 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较

            if (i == cCount - 1)

            {

                width = Math.max(width, lineWidth);

                height += lineHeight;

                listview.add(lineView);

                listheight.add(lineHeight);

            }



        }

        //设置当前控件的宽高

        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth

                : width, (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight

                : height);

    }

    public void setFlowLayoutListener(FlowLayoutListener flowLayoutListener){

        mFlowLayoutListener = flowLayoutListener;

        for (int i = 0;i<getChildCount();i++){

            getChildAt(i).setOnClickListener(this);

        }

    }





    @Override

    protected void onLayout(boolean changed, int l, int t, int r, int b) {



        // 存储每一行所有的childView

        List<View> lineViews = new ArrayList<View>();

        int lineHeight = 0;

        int left = 0;

        int top = 0;

        // 得到总行数

        int lineNums = listview.size();

        for (int i = 0; i < lineNums; i++)

        {

            // 每一行的所有的views

            lineViews = listview.get(i);

            // 当前行的最大高度

            lineHeight = listheight.get(i);



            // 遍历当前行所有的View

            for (int j = 0; j < lineViews.size(); j++)

            {

                View child = lineViews.get(j);

                if (child.getVisibility() == View.GONE)

                {

                    continue;

                }

                ViewGroup.MarginLayoutParams lp = (MarginLayoutParams) child

                        .getLayoutParams();



                //计算childView的left,top,right,bottom

                int lc = left + lp.leftMargin;

                int tc = top + lp.topMargin;

                int rc =lc + child.getMeasuredWidth();

                int bc = tc + child.getMeasuredHeight();



                child.layout(lc, tc, rc, bc);



                left += child.getMeasuredWidth() + lp.rightMargin

                        + lp.leftMargin;

            }

            left = 0;

            top += lineHeight;

        }

    }





    /**

     * 添加数据

     */

    public void addData(List<String> datas){

        for(String data: datas){

            addTextView(data);

        }

        requestLayout();

    }







    /**

     * 动态添加布局

     * @param str

     */

    private void addTextView(String str) {

        TextView child = new TextView(getContext());

        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);

        params.setMargins(15, 15, 15, 15);

        child.setLayoutParams(params);

       // child.setBackgroundResource(R.drawable.shape_text_border);

        child.setText(str);

        child.setTextSize(18);

        child.setTextColor(Color.BLACK);

        //initEvents(child);

        this.addView(child);



    }



    @Override

    public void onClick(View v) {

        for (int i = 0;i<getChildCount();i++){

            if(getChildAt(i)==v){

                mFlowLayoutListener.onItemClick(v,i);

                break;

            }

        }

    }



    /**

     * 标签点击的回调

     */

    public interface  FlowLayoutListener{

        void onItemClick(View view, int poition);

    }





    @Override

    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs)

    {

        return new MarginLayoutParams(getContext(), attrs);

    }


}
