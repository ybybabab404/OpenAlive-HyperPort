package com.miui.aod.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import com.miui.aod.widget.IGradientView;
import com.miui.maml.folme.AnimatedProperty;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import miuix.view.animation.SineEaseInOutInterpolator;

/* JADX INFO: loaded from: classes.dex */
public final class ShaderUtils {
    public static final ColorMatrix sColorMatrix = new ColorMatrix();
    public static Class sGradientColorClass;
    public static Method sGradientColorGetShaderMethod;
    public static Field sGradientColorLongsField;
    public static Field sGradientColorsField;
    public static Field sGradientPositionsField;
    public static Class sVFullPathClass;
    public static Field sVFullPathFillColorField;
    public static Method sVectorDrawableCreateMethod;
    public static Method sVectorDrawableGetObjectMethod;

    public @interface IShaderMode {
    }

    public static class ShaderConfig {
        public int[] mColors;
        public float[] mPositions;
    }

    public static class ViewConfig {
        public static long sHueDuration = 2000;
        public static long sHueStartDelay;
        public long alphaDuration;
        public long alphaStartDelay;
        public View view;

        public ViewConfig(View view) {
            this.view = view;
            this.alphaDuration = 1500L;
            this.alphaStartDelay = 0L;
        }

        public ViewConfig(View view, long j, long j2) {
            this.view = view;
            this.alphaDuration = j2;
            this.alphaStartDelay = j;
        }
    }

    static {
        initVectorDrawableCreateMethod();
    }

    public static ColorMatrixColorFilter adjustHue(ColorMatrix colorMatrix, float f) {
        colorMatrix.reset();
        float fCleanValue = (cleanValue(f, 180.0f) / 180.0f) * 3.1415927f;
        if (fCleanValue == 0.0f) {
            return new ColorMatrixColorFilter(colorMatrix);
        }
        double d = fCleanValue;
        float fCos = (float) Math.cos(d);
        float fSin = (float) Math.sin(d);
        float f2 = (fCos * (-0.715f)) + 0.715f;
        float f3 = ((-0.072f) * fCos) + 0.072f;
        float f4 = ((-0.213f) * fCos) + 0.213f;
        colorMatrix.postConcat(new ColorMatrix(new float[]{(0.787f * fCos) + 0.213f + (fSin * (-0.213f)), ((-0.715f) * fSin) + f2, (fSin * 0.928f) + f3, 0.0f, 0.0f, (0.143f * fSin) + f4, (0.28500003f * fCos) + 0.715f + (0.14f * fSin), f3 + ((-0.283f) * fSin), 0.0f, 0.0f, f4 + ((-0.787f) * fSin), f2 + (0.715f * fSin), (fCos * 0.928f) + 0.072f + (fSin * 0.072f), 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f}));
        return new ColorMatrixColorFilter(colorMatrix);
    }

    public static float cleanValue(float f, float f2) {
        return Math.min(f2, Math.max(-f2, f));
    }

    public static void getColors() {
        if (sGradientColorLongsField == null) {
            try {
                Field declaredField = LinearGradient.class.getDeclaredField("mColorLongs");
                sGradientColorLongsField = declaredField;
                declaredField.setAccessible(true);
            } catch (NoSuchFieldException e) {
                Log.e("ShaderUtils", "error...", e);
            }
        }
    }

    public static LinearGradient getShader(Context context, int i, Rect rect) {
        initVectorDrawableCreateMethod();
        return getShader(getShaderConfig(context, i), rect);
    }

    public static LinearGradient getShader(ShaderConfig shaderConfig, Rect rect) {
        int[] iArr;
        if (shaderConfig == null || (iArr = shaderConfig.mColors) == null || iArr.length <= 0) {
            return null;
        }
        return new LinearGradient(rect.right, rect.top, rect.left, rect.bottom, shaderConfig.mColors, shaderConfig.mPositions, Shader.TileMode.CLAMP);
    }

    public static ShaderConfig getShaderConfig(Context context, int i) {
        initVectorDrawableCreateMethod();
        ShaderConfig shaderConfig = new ShaderConfig();
        try {
            Object objInvoke = sVectorDrawableGetObjectMethod.invoke((VectorDrawable) sVectorDrawableCreateMethod.invoke(null, context.getResources(), Integer.valueOf(i)), "gradient_vector_path");
            Field field = sVFullPathFillColorField;
            if (field != null) {
                Method method = sGradientColorGetShaderMethod;
                Object obj = field.get(objInvoke);
                Shader shader = (Shader) method.invoke(obj, new Object[0]);
                if (shader instanceof LinearGradient) {
                    int[] iArr = new int[0];
                    long[] jArr = new long[0];
                    Field field2 = sGradientColorsField;
                    if (field2 != null) {
                        iArr = (int[]) field2.get(shader);
                    }
                    Field field3 = sGradientColorLongsField;
                    if (field3 != null) {
                        jArr = (long[]) field3.get(shader);
                    }
                    if (jArr != null && jArr.length > 0 && (iArr == null || iArr.length == 0)) {
                        int length = jArr.length;
                        int[] iArr2 = new int[length];
                        for (int i2 = 0; i2 < length; i2++) {
                            iArr2[i2] = Color.valueOf(jArr[i2]).toArgb();
                        }
                        iArr = iArr2;
                    }
                    if (iArr != null && iArr.length > 0) {
                        shaderConfig.mColors = iArr;
                        shaderConfig.mPositions = (float[]) sGradientPositionsField.get(shader);
                        return shaderConfig;
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            Log.e("ShaderUtils", "error...", e);
        }
        return shaderConfig;
    }

    public static Animator hueAnimateIn(AnimatorListenerAdapter animatorListenerAdapter, int i, View... viewArr) {
        ViewConfig[] viewConfigArr = new ViewConfig[viewArr.length];
        for (int i2 = 0; i2 < viewArr.length; i2++) {
            viewConfigArr[i2] = new ViewConfig(viewArr[i2]);
        }
        return hueAnimateIn(animatorListenerAdapter, i, viewConfigArr);
    }

    public static Animator hueAnimateIn(AnimatorListenerAdapter animatorListenerAdapter, final int i, final ViewConfig... viewConfigArr) {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(-180.0f, 0.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.aod.util.ShaderUtils.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                try {
                    for (ViewConfig viewConfig : viewConfigArr) {
                        if (i == 1) {
                            KeyEvent.Callback callback = viewConfig.view;
                            Drawable gradientOverlayDrawable = callback instanceof IGradientView ? ((IGradientView) callback).getGradientOverlayDrawable() : null;
                            if (gradientOverlayDrawable != null) {
                                gradientOverlayDrawable.setColorFilter(ShaderUtils.adjustHue(ShaderUtils.sColorMatrix, fFloatValue));
                                viewConfig.view.invalidate();
                            }
                        } else {
                            Paint paint = (Paint) ReflectUtil.callObjectMethod2(viewConfig.view, "getPaint", new Class[0], new Object[0]);
                            if (paint != null) {
                                paint.setColorFilter(ShaderUtils.adjustHue(ShaderUtils.sColorMatrix, fFloatValue));
                            }
                            viewConfig.view.invalidate();
                        }
                    }
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    Log.e("ShaderUtils", "error...", e);
                }
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.miui.aod.util.ShaderUtils.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
            }
        });
        valueAnimatorOfFloat.setStartDelay(ViewConfig.sHueStartDelay);
        valueAnimatorOfFloat.setDuration(ViewConfig.sHueDuration);
        ArrayList arrayList = new ArrayList();
        arrayList.add(valueAnimatorOfFloat);
        for (ViewConfig viewConfig : viewConfigArr) {
            View view = viewConfig.view;
            if (view != null) {
                view.setAlpha(0.0f);
            }
            ObjectAnimator duration = ObjectAnimator.ofFloat(viewConfig.view, AnimatedProperty.PROPERTY_NAME_ALPHA, 0.0f, 1.0f).setDuration(viewConfig.alphaDuration);
            duration.setStartDelay(viewConfig.alphaStartDelay);
            arrayList.add(duration);
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(arrayList);
        animatorSet.setInterpolator(new SineEaseInOutInterpolator());
        if (animatorListenerAdapter != null) {
            animatorSet.addListener(animatorListenerAdapter);
        }
        animatorSet.start();
        return animatorSet;
    }

    public static Animator hueAnimateIn(AnimatorListenerAdapter animatorListenerAdapter, View... viewArr) {
        return hueAnimateIn(animatorListenerAdapter, 1, viewArr);
    }

    public static void initVectorDrawableCreateMethod() {
        if (sVectorDrawableGetObjectMethod == null) {
            try {
                Method declaredMethod = VectorDrawable.class.getDeclaredMethod("getTargetByName", String.class);
                sVectorDrawableGetObjectMethod = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                Log.e("ShaderUtils", "error...", e);
            }
        }
        if (sVectorDrawableCreateMethod == null) {
            try {
                Method method = VectorDrawable.class.getMethod("create", Resources.class, Integer.TYPE);
                sVectorDrawableCreateMethod = method;
                method.setAccessible(true);
            } catch (NoSuchMethodException e2) {
                Log.e("ShaderUtils", "error...", e2);
            }
        }
        if (sVFullPathClass == null) {
            try {
                Class<?> cls = Class.forName(VectorDrawable.class.getCanonicalName() + "$VFullPath");
                sVFullPathClass = cls;
                Field declaredField = cls.getDeclaredField("mFillColors");
                sVFullPathFillColorField = declaredField;
                declaredField.setAccessible(true);
            } catch (ClassNotFoundException | NoSuchFieldException e3) {
                Log.e("ShaderUtils", "error...", e3);
            }
        }
        if (sGradientColorClass == null) {
            try {
                Class<?> cls2 = Class.forName("android.content.res.GradientColor");
                sGradientColorClass = cls2;
                sGradientColorGetShaderMethod = cls2.getMethod("getShader", new Class[0]);
            } catch (ClassNotFoundException | NoSuchMethodException e4) {
                Log.e("ShaderUtils", "error...", e4);
            }
        }
        getColors();
        if (sGradientPositionsField == null) {
            try {
                Field declaredField2 = LinearGradient.class.getDeclaredField("mPositions");
                sGradientPositionsField = declaredField2;
                declaredField2.setAccessible(true);
            } catch (NoSuchFieldException e5) {
                Log.e("ShaderUtils", "error...", e5);
            }
        }
    }
}
