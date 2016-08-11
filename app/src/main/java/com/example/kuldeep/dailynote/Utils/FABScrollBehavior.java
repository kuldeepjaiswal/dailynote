package com.example.kuldeep.dailynote.Utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Kuldeep on 1/8/2016.
 */
public class FABScrollBehavior extends FloatingActionButton.Behavior {
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout,final FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            child.hide();

            new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    child.show();

                }
            }.start();


        }
        else if (dyConsumed < 0 && child.getVisibility() == View.VISIBLE) {
            child.hide();

            new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    child.show();

                }
            }.start();
        }
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    public FABScrollBehavior(Context context, AttributeSet attributeSet) {
        super();
    }
}
