package com.bw.project_demo.ui.fragment.widght;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

import com.bw.project_demo.R;

import static com.bw.project_demo.ui.fragment.widght.HorizontalExpandMenu.ButtonStyle.Right;


public class HorizontalExpandMenu extends RelativeLayout {
    Context context;
    private int defaultWidth;
    private int defaultHeight;
    private int viewWidth;
    private int viewHeight;
    private int color;
    private float dimension;
    private int strokecolor;
    private float cornerRadius;

    private float buttonIconDegrees;//按钮icon符号竖线的旋转角度
    private float buttonIconSize;//按钮icon符号的大小
    private float buttonIconStrokeWidth;//按钮icon符号的粗细
    private int buttonIconColor;//按钮icon颜色

    private int buttonStyle;//按钮类型
    private int buttonRadius;//按钮矩形区域内圆半径
    private float buttonTop;//按钮矩形区域top值
    private float buttonBottom;//按钮矩形区域bottom值

    private Point rightButtonCenter;//右按钮中点
    private float rightButtonLeft;//右按钮矩形区域left值
    private float rightButtonRight;//右按钮矩形区域right值

    private Point leftButtonCenter;//左按钮中点
    private float leftButtonLeft;//左按钮矩形区域left值
    private float leftButtonRight;//左按钮矩形区域right值
    private Paint buttonIconPaint;
    private Path path;
    private int expandAnimTime;
    private boolean isExpand;//菜单是否展开，默认为展开
    private float downX = -1;
    private float downY = -1;
    private ExpandMenuAnim expandMenuAnim;
    private boolean isFirstLayout;

    private float backPathWidth;//绘制子View区域宽度
    private float maxBackPathWidth;//绘制子View区域最大宽度
    private int menuLeft;//menu区域left值
    private int menuRight;//menu区域right值

    private boolean isAnimEnd;//动画是否结束
    private View childView;

    public HorizontalExpandMenu(Context context) {
        super(context);
        this.context=context;
        initView();
    }

    AttributeSet attrs;
    public HorizontalExpandMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        this.attrs=attrs;
        initView();

    }

    public HorizontalExpandMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public class ButtonStyle {
        public static final int Right = 0;
        public static final int Left = 1;
    }
    private void initView() {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalExpandMenu);
        defaultWidth = DpOpPxutils.dip2px(context, 200);
        defaultHeight = DpOpPxutils.dip2px(context, 40);

        color = typedArray.getColor(R.styleable.HorizontalExpandMenu_back_color, Color.WHITE);
        dimension = typedArray.getDimension(R.styleable.HorizontalExpandMenu_stroke_size, 1);
        strokecolor = typedArray.getColor(R.styleable.HorizontalExpandMenu_stroke_color, Color.GRAY);
        cornerRadius = typedArray.getDimension(R.styleable.HorizontalExpandMenu_corner_radius,DpOpPxutils.dip2px(context,20));


        buttonStyle = typedArray.getInteger(R.styleable.HorizontalExpandMenu_button_style,Right);
        buttonIconDegrees = 0;
        buttonIconSize = typedArray.getDimension(R.styleable.HorizontalExpandMenu_button_icon_size,DpOpPxutils.dip2px(context,8));
        buttonIconStrokeWidth = typedArray.getDimension(R.styleable.HorizontalExpandMenu_button_icon_stroke_width,8);
        buttonIconColor = typedArray.getColor(R.styleable.HorizontalExpandMenu_button_icon_color,Color.GRAY);

        buttonIconPaint = new Paint();
        buttonIconPaint.setColor(buttonIconColor);
        buttonIconPaint.setStyle(Paint.Style.STROKE);
        buttonIconPaint.setStrokeWidth(buttonIconStrokeWidth);
        buttonIconPaint.setAntiAlias(true);

        path = new Path();
        leftButtonCenter = new Point();
        rightButtonCenter = new Point();

        buttonIconDegrees=90;
        expandAnimTime = typedArray.getInteger(R.styleable.HorizontalExpandMenu_expand_time, 400);
        isExpand=true;

        isFirstLayout = true;

        expandMenuAnim = new ExpandMenuAnim();

        isAnimEnd = false;
        expandMenuAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimEnd = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        typedArray.recycle();

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(isFirstLayout){
            menuLeft = getLeft();
            menuRight = getRight();
            isFirstLayout = false;
        }

        if(getChildCount()>0){
            childView = getChildAt(0);
            if(isExpand){
                if(buttonStyle == Right){
                    childView.layout(leftButtonCenter.x,(int) buttonTop,(int) rightButtonLeft,(int) buttonBottom);
                }else {
                    childView.layout((int)(leftButtonRight),(int) buttonTop,rightButtonCenter.x,(int) buttonBottom);
                }

                //限制子View在菜单内，LayoutParam类型和当前ViewGroup一致
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(viewWidth,viewHeight);
                layoutParams.setMargins(0,0,buttonRadius *3,0);
                childView.setLayoutParams(layoutParams);
            }else {
                childView.setVisibility(GONE);
            }
        }
        if(getChildCount()>1){//限制直接子View的数量
            throw new IllegalStateException("HorizontalExpandMenu can host only one direct child");
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;//当menu的宽度改变时，重新给viewWidth赋值

        if(isAnimEnd){//防止出现动画结束后菜单栏位置大小测量错误的bug
            if(buttonStyle == Right){
                if(!isExpand){
                    layout((int)(menuRight - buttonRadius *2-backPathWidth),getTop(), menuRight,getBottom());
                }
            }else {
                if(!isExpand){
                    layout(menuLeft,getTop(),(int)(menuLeft + buttonRadius *2+backPathWidth),getBottom());
                }
            }
        }
    }

    private class ExpandMenuAnim extends Animation {
        public ExpandMenuAnim() {}

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);

            float left = menuRight - buttonRadius *2;//按钮在右边，菜单收起时按钮区域left值
            float right = menuLeft + buttonRadius *2;//按钮在左边，菜单收起时按钮区域right值
            if(childView!=null) {
                childView.setVisibility(GONE);
            }
            if(isExpand){//展开菜单
                backPathWidth = maxBackPathWidth * interpolatedTime;
                buttonIconDegrees = 90 * interpolatedTime;

                if(backPathWidth==maxBackPathWidth){
                    if(childView!=null) {
                        childView.setVisibility(VISIBLE);
                    }
                }
            }else {//收起菜单
                backPathWidth = maxBackPathWidth - maxBackPathWidth * interpolatedTime;
                buttonIconDegrees = 90 - 90 * interpolatedTime;
            }

            if(buttonStyle == Right){
                layout((int)(left-backPathWidth),getTop(), menuRight,getBottom());//会调用onLayout重新测量子View位置
            }else {
                layout(menuLeft,getTop(),(int)(right+backPathWidth),getBottom());
            }
            postInvalidate();
        }
    }

    private void expandMenu(int time){
        expandMenuAnim.setDuration(time);
        isExpand = isExpand ?false:true;
        this.startAnimation(expandMenuAnim);
        isAnimEnd = false;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measuresize(defaultWidth, widthMeasureSpec);
        int height = measuresize(defaultHeight, heightMeasureSpec);
        viewWidth = width;
        viewHeight=height;
        setMeasuredDimension(viewWidth,viewHeight);

        buttonRadius = viewHeight/2;
        layoutRootButton();

        maxBackPathWidth = viewWidth- buttonRadius *2;
        backPathWidth = maxBackPathWidth;

    //如果不居中没有给背景颜色 那么将自动默认给出
        if(getBackground()==null){
            setMenuBackground();
        }
    }





    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                switch (buttonStyle){
                    case Right:
                        if(x==downX&&y==downY&&y>=buttonTop&&y<=buttonBottom&&x>=rightButtonLeft&&x<=rightButtonRight){
                            expandMenu(expandAnimTime);
                        }
                        break;
                    case ButtonStyle.Left:
                        if(x==downX&&y==downY&&y>=buttonTop&&y<=buttonBottom&&x>=leftButtonLeft&&x<=leftButtonRight){
                            expandMenu(expandAnimTime);
                        }
                        break;
                }
//                if(backPathWidth==maxBackPathWidth || backPathWidth==0){//动画结束时按钮才生效
//                    switch (buttonStyle){
//                        case ButtonStyle.Right:
//                            if(x==downX&&y==downY&&y>=buttonTop&&y<=buttonBottom&&x>=rightButtonLeft&&x<=rightButtonRight){
//                                expandMenu(expandAnimTime);
//                            }
//                            break;
//                        case ButtonStyle.Left:
//                            if(x==downX&&y==downY&&y>=buttonTop&&y<=buttonBottom&&x>=leftButtonLeft&&x<=leftButtonRight){
//                                expandMenu(expandAnimTime);
//                            }
//                            break;
//                    }
//                }

                break;
        }
        return true;
    }



    @Override
    protected void onDraw(Canvas canvas) {

        layoutRootButton();
        if (buttonStyle==Right){
            drawRightIcon(canvas);
        }else{
            drawLeftIcon(canvas);
        }
        super.onDraw(canvas);
    }

    private void drawLeftIcon(Canvas canvas) {
        path.reset();
        path.moveTo(leftButtonCenter.x- buttonIconSize, leftButtonCenter.y);
        path.lineTo(leftButtonCenter.x+ buttonIconSize, leftButtonCenter.y);
        canvas.drawPath(path, buttonIconPaint);//划横线

        canvas.save();
        canvas.rotate(-buttonIconDegrees, leftButtonCenter.x, leftButtonCenter.y);//旋转画布，让竖线可以随角度旋转
        path.reset();
        path.moveTo(leftButtonCenter.x, leftButtonCenter.y- buttonIconSize);
        path.lineTo(leftButtonCenter.x, leftButtonCenter.y+ buttonIconSize);
        canvas.drawPath(path, buttonIconPaint);//画竖线
        canvas.restore();
    }

    private void drawRightIcon(Canvas canvas) {
        path.reset();
        path.moveTo(rightButtonCenter.x- buttonIconSize, rightButtonCenter.y);
        path.lineTo(rightButtonCenter.x+ buttonIconSize, rightButtonCenter.y);
        canvas.drawPath(path, buttonIconPaint);//划横线

        canvas.save();
        canvas.rotate(buttonIconDegrees, rightButtonCenter.x, rightButtonCenter.y);//旋转画布，让竖线可以随角度旋转
        path.reset();
        path.moveTo(rightButtonCenter.x, rightButtonCenter.y- buttonIconSize);
        path.lineTo(rightButtonCenter.x, rightButtonCenter.y+ buttonIconSize);
        canvas.drawPath(path, buttonIconPaint);//画竖线
        canvas.restore();
    }

    private void layoutRootButton() {
        buttonTop = 0;
        buttonBottom = viewHeight;

        rightButtonCenter.x = viewWidth- buttonRadius;
        rightButtonCenter.y = viewHeight/2;
        rightButtonLeft = rightButtonCenter.x- buttonRadius;
        rightButtonRight = rightButtonCenter.x+ buttonRadius;

        leftButtonCenter.x = buttonRadius;
        leftButtonCenter.y = viewHeight/2;
        leftButtonLeft = leftButtonCenter.x- buttonRadius;
        leftButtonRight = leftButtonCenter.x+ buttonRadius;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setMenuBackground() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(color);
        gradientDrawable.setStroke((int) dimension,strokecolor);
        gradientDrawable.setCornerRadius(cornerRadius);
        setBackground(gradientDrawable);
    }

    private int measuresize(int defaultWidth, int widthMeasureSpec) {
        int reseult = defaultWidth;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode==View.MeasureSpec.EXACTLY){
            reseult = size;
        }else if (mode==MeasureSpec.AT_MOST){
            reseult = Math.min(reseult, size);

        }
        return  reseult;


    }
}
