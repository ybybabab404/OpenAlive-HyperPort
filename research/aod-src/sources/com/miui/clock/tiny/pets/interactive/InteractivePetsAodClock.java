package com.miui.clock.tiny.pets.interactive;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.Guideline;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.Util;
import com.miui.clock.tiny.model.TinyClockInfo;
import com.miui.clock.tiny.model.TinyClockViewType;
import com.miui.clock.tiny.utils.BitmapUtils;
import com.miui.clock.tiny.utils.FontUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class InteractivePetsAodClock extends InteractivePetsClockBase {
    public TextView date;
    public ImageView petsImage;
    public boolean showInAod;

    public InteractivePetsAodClock(Context context) {
        super(context);
    }

    public InteractivePetsAodClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public InteractivePetsAodClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public static void addAnimationFrames(String str, AnimationDrawable animationDrawable, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            Drawable drawable = BitmapUtils.INSTANCE.getDrawable(str + str2, str2);
            if (drawable != null) {
                animationDrawable.addFrame(drawable, 66);
            }
        }
    }

    public static AnimationDrawable createFrameAnimation(int i, int i2) {
        AnimationDrawable animationDrawable = new AnimationDrawable();
        String[] strArr = InteractivePetsStyle.mPetsAodStaticAnimationFrameResource[i][i2];
        if (strArr.length == 0) {
            Log.d("InteractivePetsAodClock", "createFrameAnimation error,  typeIndex: " + i + " actionIndex: " + i2);
            return animationDrawable;
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(strArr));
        int i3 = i + 1;
        String strBuildInteractivePetsClockResourceDir = InteractivePetsStyle.InteractivePetsResourceUtil.buildInteractivePetsClockResourceDir(i3);
        ArrayList arrayList2 = new ArrayList(arrayList);
        Collections.reverse(arrayList2);
        int[] iArr = InteractivePetsStyle.InteractivePetsTypeInfo.getInteractivePetsTypeInfo(i3).aodFrameAnimationTypes;
        int i4 = i2 < iArr.length ? iArr[i2] : 2;
        if (i4 == 1) {
            addAnimationFrames(strBuildInteractivePetsClockResourceDir, animationDrawable, arrayList);
            addAnimationFrames(strBuildInteractivePetsClockResourceDir, animationDrawable, arrayList2);
            addAnimationFrames(strBuildInteractivePetsClockResourceDir, animationDrawable, arrayList);
            addAnimationFrames(strBuildInteractivePetsClockResourceDir, animationDrawable, arrayList2);
            addAnimationFrames(strBuildInteractivePetsClockResourceDir, animationDrawable, arrayList);
            return animationDrawable;
        }
        if (i4 == 2) {
            int size = 75 / arrayList.size();
            for (int i5 = 0; i5 < size; i5++) {
                addAnimationFrames(strBuildInteractivePetsClockResourceDir, animationDrawable, arrayList);
            }
        }
        return animationDrawable;
    }

    public static Drawable getAodStaticFrameDrawable(int i, int i2) {
        if (i >= 0) {
            String[][][] strArr = InteractivePetsStyle.mPetsAodStaticAnimationFrameResource;
            if (i < strArr.length && i2 >= 0) {
                String[][] strArr2 = strArr[i];
                if (i2 < strArr2.length) {
                    String[] strArr3 = strArr2[i2];
                    if (strArr3.length == 0) {
                        Log.d("InteractivePetsAodClock", "createFrameAnimation error,  typeIndex: " + i + " actionIndex: " + i2);
                        return null;
                    }
                    String strBuildInteractivePetsClockResourceDir = InteractivePetsStyle.InteractivePetsResourceUtil.buildInteractivePetsClockResourceDir(i + 1);
                    String str = strArr3[strArr3.length - 1];
                    return BitmapUtils.INSTANCE.getDrawable(strBuildInteractivePetsClockResourceDir + str, str);
                }
            }
        }
        Log.e("InteractivePetsAodClock", "getAodStaticFrameDrawable Invalid index parameters");
        return null;
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockBase, com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public View getIClockView(TinyClockViewType tinyClockViewType) {
        if (tinyClockViewType == TinyClockViewType.BATTERY) {
            return this.mBatteryView;
        }
        return tinyClockViewType == TinyClockViewType.IMAGE_CONTAINER ? this.petsImage : super.getIClockView(tinyClockViewType);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockBase
    public void initBatteryView() {
        Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(-2, -2);
        layoutParams.leftToLeft = 0;
        layoutParams.rightToRight = 0;
        layoutParams.topToBottom = this.date.getId();
        layoutParams.setMargins(0, getDimen(R.dimen.tiny_aod_interactive_pets_gap), 0, 0);
        this.mBatteryViewLayout.setLayoutParams(layoutParams);
        this.mBatteryView.setBatteryColor(-1);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockBase
    public void initClockView(int i) {
        ((Guideline) this.clockView.findViewById(R.id.guideline)).setGuidelinePercent(0.5f);
        int i2 = R.dimen.tiny_aod_interactive_pets_clock_width;
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(getDimen(i2), getDimen(R.dimen.tiny_aod_interactive_pets_clock_height));
        layoutParams.leftToLeft = 0;
        layoutParams.topToTop = 0;
        layoutParams.setMarginStart(getDimen(R.dimen.tiny_aod_interactive_pets_width) - getDimen(i2));
        this.clockView.setLayoutParams(layoutParams);
        Constraints.LayoutParams layoutParams2 = new Constraints.LayoutParams(0, -2);
        layoutParams2.leftToLeft = 0;
        layoutParams2.rightToRight = 0;
        layoutParams2.topToTop = 0;
        layoutParams2.setMargins(0, getDimen(R.dimen.tiny_aod_interactive_pets_date_margin_top), 0, 0);
        this.date.setLayoutParams(layoutParams2);
        updateDateVisibleByLanguage();
    }

    public final void initPetsImage() {
        if (this.showInAod) {
            resetFirstFramePetsImage();
        }
        Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(getDimen(R.dimen.tiny_aod_interactive_pets_image_width), getDimen(R.dimen.tiny_aod_interactive_pets_image_height));
        layoutParams.topToTop = 0;
        layoutParams.leftToLeft = 0;
        layoutParams.setMargins(getDimen(R.dimen.tiny_aod_interactive_pets_image_margin_left), getDimen(R.dimen.tiny_aod_interactive_pets_image_margin_top), 0, 0);
        this.petsImage.setLayoutParams(layoutParams);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockBase
    public void initSelf(int i) {
        boolean z = i == 0;
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        int i2 = z ? R.dimen.tiny_aod_interactive_pets_margin_top_0 : R.dimen.tiny_aod_interactive_pets_margin_top_180;
        int dimen = this.showInAod ? 0 : getDimen(z ? R.dimen.tiny_aod_interactive_pets_margin_left_0 : R.dimen.tiny_aod_interactive_pets_margin_left_180);
        int dimen2 = getDimen(i2);
        int i3 = R.dimen.tiny_aod_interactive_pets_padding_right;
        setPadding(dimen, dimen2, getDimen(i3), 0);
        if (this.showInAod) {
            layoutParams.width = getDimen(R.dimen.tiny_aod_interactive_pets_width) + getDimen(i3);
        }
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockBase, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.petsImage = (ImageView) findViewById(R.id.pets_image);
        this.showInAod = Util.isAOD(getContext());
        this.date = (TextView) findViewById(R.id.date);
    }

    public final void playFrameAnimation() {
        InteractivePetsClockInfo interactivePetsClockInfo = this.mClockInfo;
        if (interactivePetsClockInfo == null) {
            Log.d("InteractivePetsAodClock", "playFrameAnimation mClockInfo is null ");
            return;
        }
        int petsIndex = interactivePetsClockInfo.getPetsIndex();
        int actionIndex = this.mClockInfo.getActionIndex();
        AnimationDrawable animationDrawableCreateFrameAnimation = createFrameAnimation(petsIndex, actionIndex);
        if (animationDrawableCreateFrameAnimation.getNumberOfFrames() == 0) {
            animationDrawableCreateFrameAnimation = createFrameAnimation(petsIndex, 0);
        }
        this.petsImage.setImageDrawable(animationDrawableCreateFrameAnimation);
        animationDrawableCreateFrameAnimation.setOneShot(true);
        animationDrawableCreateFrameAnimation.start();
        Log.d("InteractivePetsAodClock", "playFrameAnimation" + actionIndex);
    }

    public final void resetFirstFramePetsImage() {
        InteractivePetsClockInfo interactivePetsClockInfo = this.mClockInfo;
        if (interactivePetsClockInfo == null) {
            return;
        }
        int petsIndex = interactivePetsClockInfo.getPetsIndex();
        Drawable aodStaticFrameDrawable = getAodStaticFrameDrawable(petsIndex, this.mClockInfo.getActionIndex());
        if (aodStaticFrameDrawable == null) {
            aodStaticFrameDrawable = getAodStaticFrameDrawable(petsIndex, 0);
        }
        this.petsImage.setImageDrawable(aodStaticFrameDrawable);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockBase, com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void setBatteryColor(int i) {
        this.mBatteryView.setBatteryColor(i);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockBase, com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void setBatteryLevel(int i) {
        this.mBatteryView.setBatteryLevel(i);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockBase, com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void setClockStyleInfo(TinyClockInfo tinyClockInfo) {
        super.setClockStyleInfo(tinyClockInfo);
        initPetsImage();
        updateTime();
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void toLinkageAodState() {
        playFrameAnimation();
    }

    public final void updateDateVisibleByLanguage() {
        boolean zIsLongDateLanguage = FontUtils.isLongDateLanguage(getContext());
        int i = zIsLongDateLanguage ? 0 : 4;
        int i2 = zIsLongDateLanguage ? 8 : 0;
        this.clockView.weekBottom.setVisibility(i2);
        this.clockView.mMonth.setVisibility(i2);
        this.clockView.weekToday.setVisibility(i2);
        this.date.setVisibility(i);
        this.date.getLayoutParams().height = zIsLongDateLanguage ? -2 : 0;
        TextView textView = this.date;
        textView.setLayoutParams(textView.getLayoutParams());
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockBase, com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        this.date.setTextColor(-1);
        this.date.setText(getResources().getString(R.string.date_format, this.clockView.mMonth.getText(), this.clockView.weekToday.getText()));
    }
}
