package com.bw.project_demo.ui.fragment;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.animation.FastOutSlowInInterpolator;

import com.lxj.xpopup.animator.PopupAnimator;

public class RotateAnimator extends PopupAnimator {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initAnimator() {
        targetView.setScaleX(0);
        targetView.setScaleY(0);
        targetView.setAlpha(0);
        targetView.setRotation(360);
        targetView.setTranslationX(0);
        targetView.setTranslationY(1);
        targetView.setTranslationZ(1);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void animateShow() {
        targetView.animate().alpha(0).scaleX(1).scaleY(1).alpha(1).translationX(0).translationY(1).translationZ(1).setInterpolator(new FastOutSlowInInterpolator()).setDuration(2000).start();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void animateDismiss() {
        targetView.animate().alpha(1).scaleX(0).scaleY(0).alpha(0).translationX(0).translationY(1).translationZ(1).setInterpolator(new FastOutSlowInInterpolator()).setDuration(2000).start();
    }
}
