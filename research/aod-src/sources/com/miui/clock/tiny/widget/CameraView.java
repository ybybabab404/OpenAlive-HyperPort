package com.miui.clock.tiny.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.Util;
import com.miui.clock.tiny.model.CameraBean;
import com.miui.clock.tiny.utils.DeviceConfig;
import com.miui.clock.tiny.utils.MiuiBlurUtils;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;

/* JADX INFO: loaded from: classes.dex */
public class CameraView extends FrameLayout {
    public static final int DEFAULT_CAMERA_BG_COLOR = Color.parseColor("#ffffff");
    public static final int DEFAULT_CAMERA_ICON_COLOR_BLACK = Color.parseColor("#000000");
    public static final int DEFAULT_CAMERA_ICON_COLOR_WHITE = Color.parseColor("#ffffff");
    public static final int TINY_KG_EDIT_PAGE_DARK = Color.parseColor("#33000000");
    public static final int TINY_KG_EDIT_PAGE_WHITE = Color.parseColor("#33FFFFFF");
    public Configuration currentConfiguration;
    public CameraBean mCameraBean;
    public ImageView mCameraBg;
    public ImageView mCameraIcon;
    public ImageView mCameraTwoCircle;
    public Context mContext;
    public Drawable mDrawableCameraTwoCircle;
    public float mRadio;
    public int mRotation;
    public int mType;

    public CameraView(Context context) {
        this(context, null);
    }

    public CameraView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRadio = 1.0f;
        this.mRotation = 0;
        this.mType = 2;
        this.mContext = context;
        this.currentConfiguration = getResources().getConfiguration();
        initView();
    }

    private int getCarmaResourceID() {
        return DeviceConfig.isN8() ? R.drawable.tiny_image_camera_n8 : R.drawable.tiny_image_camera_o8;
    }

    private int getCurrentRotation() {
        return this.mRotation;
    }

    private void setCameraTwoCircleStyle(int i) {
        if (i != 3) {
            CameraBean cameraBean = this.mCameraBean;
            this.mDrawableCameraTwoCircle.setColorFilter(cameraBean != null ? cameraBean.isCameraBlockAreaWallpaperDark() : false ? TINY_KG_EDIT_PAGE_WHITE : TINY_KG_EDIT_PAGE_DARK, PorterDuff.Mode.SRC_IN);
            this.mCameraTwoCircle.setImageDrawable(this.mDrawableCameraTwoCircle);
            return;
        }
        if (this.mRotation == 2) {
            this.mCameraTwoCircle.setImageDrawable(getResources().getDrawable(getCarmaResourceID()));
            this.mCameraTwoCircle.animate().rotation(180.0f).setDuration(0L);
        } else {
            this.mCameraTwoCircle.setImageDrawable(getResources().getDrawable(getCarmaResourceID()));
            this.mCameraTwoCircle.animate().rotation(0.0f).setDuration(0L);
        }
    }

    public void changeRotation(int i) {
        if (this.mCameraTwoCircle == null) {
            return;
        }
        this.mRotation = i;
        updateCameraTwoCircleParams();
        setCameraTwoCircleStyle(getType());
        Log.d("TinyMiuiClockView.CameraView", "camera view rotation changed to " + this.mRotation);
    }

    public final void changeRotationIfNeed(int i) {
        if (i != 0) {
            changeRotation(i);
        }
    }

    public CameraBean getCameraBean() {
        return this.mCameraBean;
    }

    public final float getDimen(int i) {
        return this.mContext.getResources().getDimensionPixelSize(i) * this.mRadio;
    }

    public int getType() {
        return this.mType;
    }

    public void init(CameraBean cameraBean) {
        if (cameraBean == null) {
            return;
        }
        this.mCameraBean = cameraBean;
        setType(cameraBean.getType());
        setScaleRadio(cameraBean.getScale());
        setCameraTwoCircleStyle(getType());
        changeRotationIfNeed(this.mRotation);
        Log.d("TinyMiuiClockView.CameraView", cameraBean.toString());
        MiuiBlurUtils.clearMemberBlendColor(this.mCameraBg);
        if (!cameraBean.isCameraIconBgBlur() || !DeviceConfig.supportBackgroundBlur(getContext())) {
            if (cameraBean.getCameraIconColor() != 0) {
                this.mCameraIcon.getDrawable().setTint(cameraBean.getCameraIconColor());
            } else {
                this.mCameraIcon.getDrawable().setTint(DEFAULT_CAMERA_ICON_COLOR_BLACK);
            }
            if (cameraBean.getCameraIconBgColor() != 0) {
                this.mCameraBg.getDrawable().setTint(cameraBean.getCameraIconBgColor());
                return;
            } else {
                this.mCameraBg.getDrawable().setTint(DEFAULT_CAMERA_BG_COLOR);
                return;
            }
        }
        MiuiBlurUtils.setContainerPassBlur(this, this.mContext.getResources().getDimensionPixelSize(R.dimen.tiny_camera_blur_radius));
        boolean zIsDeepCameraWallpaper = cameraBean.isDeepCameraWallpaper();
        this.mCameraIcon.getDrawable().setTint(cameraBean.getCameraIconColor());
        if (Util.isPetsTemplate(cameraBean.getTemplateId())) {
            MiuiBlurUtils.setCameraBlendColors(this.mCameraBg, Color.parseColor("#33878787"), 19, Color.parseColor("#4dffffff"), 3);
        } else if (zIsDeepCameraWallpaper) {
            MiuiBlurUtils.setCameraBlendColors(this.mCameraBg, Color.parseColor("#b2797979"), 18, Color.parseColor("#99ffffff"), 3);
        } else {
            MiuiBlurUtils.setCameraBlendColors(this.mCameraBg, Color.parseColor("#75737373"), 18, Color.parseColor("#8a262626"), 3);
        }
    }

    public final void initLayoutParams() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mCameraBg.getLayoutParams();
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mCameraIcon.getLayoutParams();
        int i = R.dimen.camera_margin_bottom;
        layoutParams.bottomMargin = (int) getDimen(i);
        int i2 = R.dimen.camera_margin_start;
        layoutParams.setMarginStart((int) getDimen(i2));
        int i3 = R.dimen.tiny_camera_icon_bg_size;
        layoutParams.height = (int) getDimen(i3);
        layoutParams.width = (int) getDimen(i3);
        layoutParams2.bottomMargin = (int) getDimen(i);
        layoutParams2.setMarginStart((int) getDimen(i2));
        layoutParams2.height = (int) getDimen(i3);
        layoutParams2.width = (int) getDimen(i3);
        updateCameraTwoCircleParams();
        this.mCameraBg.setLayoutParams(layoutParams);
        this.mCameraIcon.setLayoutParams(layoutParams2);
    }

    public final void initView() {
        removeAllViews();
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.layout_camera_view, (ViewGroup) this, false);
        this.mCameraIcon = (ImageView) viewInflate.findViewById(R.id.camera_icon);
        this.mCameraBg = (ImageView) viewInflate.findViewById(R.id.camera_bg);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.camera_two_circle);
        this.mCameraTwoCircle = imageView;
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        this.mDrawableCameraTwoCircle = ContextCompat.getDrawable(this.mContext, R.drawable.shape_camera_two_circle);
        addView(viewInflate);
        changeRotation(this.mRotation);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Configuration configuration2 = this.currentConfiguration;
        if (configuration2 == null || (configuration.diff(configuration2) & AutoBottomSheetKt.ALIGN_SCREEN_TOP) == 0) {
            return;
        }
        initView();
        init(this.mCameraBean);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    public void setScaleRadio(float f) {
        this.mRadio = f;
        if (f == 0.0f) {
            this.mRadio = 1.0f;
        }
        initLayoutParams();
    }

    public void setType(int i) {
        this.mType = i;
    }

    public final void updateCameraTwoCircleParams() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mCameraTwoCircle.getLayoutParams();
        int i = DeviceConfig.isN8() ? R.dimen.camera_two_circle_margin_n8_top_bottom_0 : R.dimen.camera_two_circle_margin_o8_top_bottom_0;
        int i2 = DeviceConfig.isN8() ? R.dimen.tiny_camera_two_circle_n8_width : R.dimen.tiny_camera_two_circle_o8_width;
        int i3 = DeviceConfig.isN8() ? R.dimen.tiny_camera_two_circle_n8_height : R.dimen.tiny_camera_two_circle_o8_height;
        int i4 = this.mRotation;
        if (i4 == 0) {
            layoutParams.gravity = 8388659;
            layoutParams.topMargin = (int) getDimen(i);
            layoutParams.setMarginStart((int) getDimen(R.dimen.camera_two_circle_margin_start_0));
        } else if (i4 == 2) {
            layoutParams.gravity = 8388693;
            layoutParams.bottomMargin = (int) getDimen(i);
            layoutParams.setMarginEnd((int) getDimen(R.dimen.camera_two_circle_margin_start_0));
        }
        layoutParams.width = (int) getDimen(i2);
        layoutParams.height = (int) getDimen(i3);
        this.mCameraTwoCircle.setLayoutParams(layoutParams);
    }
}
