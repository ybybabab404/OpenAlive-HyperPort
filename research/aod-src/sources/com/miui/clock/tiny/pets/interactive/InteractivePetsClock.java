package com.miui.clock.tiny.pets.interactive;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.model.TinyClockInfo;
import com.miui.clock.tiny.model.TinyClockViewType;
import com.miui.clock.tiny.utils.FontUtils;

/* JADX INFO: loaded from: classes.dex */
public class InteractivePetsClock extends InteractivePetsClockBase {
    public ImageView petsImage;

    /* JADX INFO: renamed from: com.miui.clock.tiny.pets.interactive.InteractivePetsClock$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType;

        static {
            int[] iArr = new int[TinyClockViewType.values().length];
            $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType = iArr;
            try {
                iArr[TinyClockViewType.TIME_ACCESSIBILITY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.IMAGE_CONTAINER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public InteractivePetsClock(Context context) {
        super(context);
    }

    public InteractivePetsClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public InteractivePetsClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private float getSmallScaleMultiple() {
        return getDimen(R.dimen.tiny_pets_time_small_width) / getDimen(R.dimen.tiny_pets_time_width);
    }

    private void initPetsImage() {
        boolean z = this.mClockInfo.getRotation() == 0;
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.petsImage.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.tiny_aod_interactive_pets_image_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.tiny_aod_interactive_pets_image_height);
        layoutParams.topToTop = 0;
        layoutParams.leftToLeft = 0;
        layoutParams.setMargins(getDimen(z ? R.dimen.tiny_interactive_pets_image_margin_left_0 : R.dimen.tiny_interactive_pets_image_margin_left_180), getDimen(z ? R.dimen.tiny_interactive_pets_image_margin_top_0 : R.dimen.tiny_interactive_pets_image_margin_top_180), 0, 0);
        this.petsImage.setLayoutParams(layoutParams);
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public TinyClockInfo getClockStyleInfo() {
        return this.mClockInfo;
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockBase, com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public View getIClockView(TinyClockViewType tinyClockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[tinyClockViewType.ordinal()];
        if (i != 1) {
            return i != 2 ? super.getIClockView(tinyClockViewType) : this.petsImage;
        }
        return this.clockView.mTimeDesc;
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockBase
    public void initClockView(int i) {
        boolean z = i == 0;
        ((Guideline) this.clockView.findViewById(R.id.guideline)).setGuidelinePercent(0.5f);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.clockView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.tiny_pets_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.tiny_pets_height);
        layoutParams.leftToLeft = 0;
        layoutParams.topToTop = 0;
        layoutParams.setMargins(getDimen(z ? R.dimen.tiny_pets_rotation_0_margin_left : R.dimen.tiny_pets_rotation_180_margin_left), getDimen(R.dimen.tiny_pets_rotation_0_margin_top), 0, 0);
        this.clockView.setLayoutParams(layoutParams);
        if (!z) {
            this.clockView.setPivotX(((ViewGroup.MarginLayoutParams) layoutParams).width / 2.0f);
            this.clockView.setPivotY(((ViewGroup.MarginLayoutParams) layoutParams).height / 2.0f);
            this.clockView.setScaleX(1.0f);
            this.clockView.setScaleY(1.0f);
            return;
        }
        this.clockView.setPivotX(0.0f);
        this.clockView.setPivotY(0.0f);
        this.clockView.setScaleX(getSmallScaleMultiple());
        this.clockView.setScaleY(getSmallScaleMultiple());
        ((TextView) this.clockView.findViewById(R.id.week)).setTextSize(0, (getDimen(FontUtils.isTibetan(getContext()) ? R.dimen.tiny_interactive_pets_week_tibetan_text_size_0 : R.dimen.tiny_interactive_pets_week_text_size_0) * 1.0f) / getSmallScaleMultiple());
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockBase
    public void initSelf(int i) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = -1;
        layoutParams.width = -1;
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockBase, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.petsImage = (ImageView) findViewById(R.id.svg_container);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockBase, com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void setClockStyleInfo(TinyClockInfo tinyClockInfo) {
        super.setClockStyleInfo(tinyClockInfo);
        initPetsImage();
    }
}
