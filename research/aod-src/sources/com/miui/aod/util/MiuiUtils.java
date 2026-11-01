package com.miui.aod.util;

import android.view.MotionEvent;
import android.view.View;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;
import miuix.appcompat.widget.Spinner;

/* JADX INFO: loaded from: classes.dex */
public class MiuiUtils {
    public void setSpinnerDisplayLocation(final Spinner spinner, final View view, final int i) {
        if (view == null) {
            return;
        }
        spinner.setClickable(false);
        spinner.setLongClickable(false);
        spinner.setContextClickable(false);
        spinner.setOnSpinnerDismissListener(new Spinner.OnSpinnerDismissListener() { // from class: com.miui.aod.util.MiuiUtils.1
            @Override // miuix.appcompat.widget.Spinner.OnSpinnerDismissListener
            public void onSpinnerDismiss() {
                Folme.useAt(view).touch().touchUp(new AnimConfig[0]);
                view.setBackgroundColor(0);
            }
        });
        view.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.aod.util.MiuiUtils.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                if (!spinner.isEnabled()) {
                    return false;
                }
                int action = motionEvent.getAction();
                if (action == 0) {
                    Folme.useAt(view2).touch().setScale(1.0f, new ITouchStyle.TouchType[0]).touchDown(new AnimConfig[0]);
                    view.setBackgroundColor(i);
                } else if (action == 1) {
                    spinner.performClick(motionEvent.getX(), motionEvent.getY());
                } else if (action == 3) {
                    Folme.useAt(view2).touch().touchUp(new AnimConfig[0]);
                    view.setBackgroundColor(0);
                }
                return true;
            }
        });
    }
}
