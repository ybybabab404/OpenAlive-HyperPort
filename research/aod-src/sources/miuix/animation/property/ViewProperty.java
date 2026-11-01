package miuix.animation.property;

import android.view.View;
import androidx.annotation.RequiresApi;
import com.miui.maml.folme.AnimatedProperty;
import miuix.animation.R;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ViewProperty extends FloatProperty<View> {
    public static final ViewProperty ALPHA;
    public static final ViewProperty AUTO_ALPHA;
    public static final ViewProperty BACKGROUND;
    public static final ViewProperty BIG_VIEW_SCALE_X;
    public static final ViewProperty BIG_VIEW_SCALE_Y;
    public static final ViewProperty ELEVATION;
    public static final ViewProperty FOREGROUND;
    public static final ViewProperty HEIGHT;
    public static final ViewProperty ROTATION;
    public static final ViewProperty ROTATION_X;
    public static final ViewProperty ROTATION_Y;
    public static final ViewProperty SCALE_X;
    public static final ViewProperty SCALE_Y;
    public static final ViewProperty SCROLL_X;
    public static final ViewProperty SCROLL_Y;

    @RequiresApi
    public static final ViewProperty TRANSITION_ALPHA;
    public static final ViewProperty TRANSLATION_X;
    public static final ViewProperty TRANSLATION_Y;
    public static final ViewProperty TRANSLATION_Z;
    public static final ViewProperty WIDTH;
    public static final ViewProperty X;
    public static final ViewProperty Y;
    public static final ViewProperty Z;

    static {
        float f = 1.0f;
        TRANSLATION_X = new ViewProperty("translationX", f) { // from class: miuix.animation.property.ViewProperty.1
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getTranslationX();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f2) {
                view.setTranslationX(f2);
            }
        };
        TRANSLATION_Y = new ViewProperty("translationY", f) { // from class: miuix.animation.property.ViewProperty.2
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getTranslationY();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f2) {
                view.setTranslationY(f2);
            }
        };
        TRANSLATION_Z = new ViewProperty("translationZ", f) { // from class: miuix.animation.property.ViewProperty.3
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getTranslationZ();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f2) {
                view.setTranslationZ(f2);
            }
        };
        float f2 = 0.004f;
        SCALE_X = new ViewProperty(AnimatedProperty.PROPERTY_NAME_SCALE_X, f2) { // from class: miuix.animation.property.ViewProperty.4
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getScaleX();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f3) {
                view.setScaleX(f3);
            }
        };
        SCALE_Y = new ViewProperty(AnimatedProperty.PROPERTY_NAME_SCALE_Y, f2) { // from class: miuix.animation.property.ViewProperty.5
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getScaleY();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f3) {
                view.setScaleY(f3);
            }
        };
        float f3 = 0.1f;
        ROTATION = new ViewProperty(AnimatedProperty.PROPERTY_NAME_ROTATION, f3) { // from class: miuix.animation.property.ViewProperty.6
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getRotation();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f4) {
                view.setRotation(f4);
            }
        };
        ROTATION_X = new ViewProperty(AnimatedProperty.PROPERTY_NAME_ROTATION_X, f3) { // from class: miuix.animation.property.ViewProperty.7
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getRotationX();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f4) {
                view.setRotationX(f4);
            }
        };
        ROTATION_Y = new ViewProperty(AnimatedProperty.PROPERTY_NAME_ROTATION_Y, f3) { // from class: miuix.animation.property.ViewProperty.8
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getRotationY();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f4) {
                view.setRotationY(f4);
            }
        };
        X = new ViewProperty(AnimatedProperty.PROPERTY_NAME_X, f) { // from class: miuix.animation.property.ViewProperty.9
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getX();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f4) {
                view.setX(f4);
            }
        };
        Y = new ViewProperty(AnimatedProperty.PROPERTY_NAME_Y, f) { // from class: miuix.animation.property.ViewProperty.10
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getY();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f4) {
                view.setY(f4);
            }
        };
        Z = new ViewProperty("z", f) { // from class: miuix.animation.property.ViewProperty.11
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getZ();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f4) {
                view.setZ(f4);
            }
        };
        HEIGHT = new ViewProperty("height", f) { // from class: miuix.animation.property.ViewProperty.12
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                int height = view.getHeight();
                Float f4 = (Float) view.getTag(R.id.miuix_animation_tag_set_height);
                if (f4 != null) {
                    return f4.floatValue();
                }
                if (height == 0 && ViewProperty.isInInitLayout(view)) {
                    height = view.getMeasuredHeight();
                }
                return height;
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f4) {
                view.getLayoutParams().height = (int) f4;
                view.setTag(R.id.miuix_animation_tag_set_height, Float.valueOf(f4));
                view.requestLayout();
            }
        };
        WIDTH = new ViewProperty("width", f) { // from class: miuix.animation.property.ViewProperty.13
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                int width = view.getWidth();
                Float f4 = (Float) view.getTag(R.id.miuix_animation_tag_set_width);
                if (f4 != null) {
                    return f4.floatValue();
                }
                if (width == 0 && ViewProperty.isInInitLayout(view)) {
                    width = view.getMeasuredWidth();
                }
                return width;
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f4) {
                view.getLayoutParams().width = (int) f4;
                view.setTag(R.id.miuix_animation_tag_set_width, Float.valueOf(f4));
                view.requestLayout();
            }
        };
        float f4 = 0.00390625f;
        ALPHA = new ViewProperty(AnimatedProperty.PROPERTY_NAME_ALPHA, f4) { // from class: miuix.animation.property.ViewProperty.14
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getAlpha();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f5) {
                view.setAlpha(f5);
            }
        };
        TRANSITION_ALPHA = new ViewProperty("transitionAlpha", f4) { // from class: miuix.animation.property.ViewProperty.15
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getTransitionAlpha();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f5) {
                view.setTransitionAlpha(f5);
            }
        };
        AUTO_ALPHA = new ViewProperty("autoAlpha", f4) { // from class: miuix.animation.property.ViewProperty.16
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getAlpha();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f5) {
                view.setAlpha(f5);
                boolean z = Math.abs(f5) <= 0.00390625f;
                if (view.getVisibility() != 0 && f5 > 0.0f && !z) {
                    view.setVisibility(0);
                } else if (z) {
                    view.setVisibility(8);
                }
            }
        };
        SCROLL_X = new ViewProperty("scrollX", f) { // from class: miuix.animation.property.ViewProperty.17
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getScrollX();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f5) {
                view.setScrollX((int) f5);
            }
        };
        SCROLL_Y = new ViewProperty("scrollY", f) { // from class: miuix.animation.property.ViewProperty.18
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getScrollY();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f5) {
                view.setScrollY((int) f5);
            }
        };
        FOREGROUND = new ViewProperty("deprecated_foreground", f4) { // from class: miuix.animation.property.ViewProperty.19
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return 0.0f;
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f5) {
            }
        };
        BACKGROUND = new ViewProperty("deprecated_background", f4) { // from class: miuix.animation.property.ViewProperty.20
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return 0.0f;
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f5) {
            }
        };
        ELEVATION = new ViewProperty("elevation", f3) { // from class: miuix.animation.property.ViewProperty.21
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getElevation();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f5) {
                view.setElevation(f5);
            }
        };
        float f5 = 3.3333333E-4f;
        BIG_VIEW_SCALE_X = new ViewProperty("bigViewScaleX", f5) { // from class: miuix.animation.property.ViewProperty.22
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getScaleX();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f6) {
                view.setScaleX(f6);
            }
        };
        BIG_VIEW_SCALE_Y = new ViewProperty("bigViewScaleY", f5) { // from class: miuix.animation.property.ViewProperty.23
            @Override // miuix.animation.property.FloatProperty
            public float getValue(View view) {
                return view.getScaleY();
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(View view, float f6) {
                view.setScaleY(f6);
            }
        };
    }

    public ViewProperty(String str) {
        super(str, 1.0f);
    }

    public ViewProperty(String str, float f) {
        super(str, f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isInInitLayout(View view) {
        return view.getTag(R.id.miuix_animation_tag_init_layout) != null;
    }

    @Override // miuix.animation.property.FloatProperty
    public String toString() {
        return "ViewProperty{name='" + this.mPropertyName + "',min='" + this.mMinVisibleChange + "'}";
    }
}
