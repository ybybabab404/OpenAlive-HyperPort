package androidx.appcompat.widget;

import android.R;
import android.graphics.Insets;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.DoNotInline;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public class DrawableUtils {
    public static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    public static final int[] EMPTY_STATE_SET = new int[0];
    public static final Rect INSETS_NONE = new Rect();

    @RequiresApi
    public static class Api18Impl {
        public static final Field sBottom;
        public static final Method sGetOpticalInsets;
        public static final Field sLeft;
        public static final boolean sReflectionSuccessful;
        public static final Field sRight;
        public static final Field sTop;

        /* JADX WARN: Code duplicated, block: B:25:0x004d  */
        /* JADX WARN: Code duplicated, block: B:26:0x005a  */
        static {
            Method method;
            Field field;
            Field field2;
            Field field3;
            Field field4;
            boolean z;
            try {
                Class<?> cls = Class.forName("android.graphics.Insets");
                method = Drawable.class.getMethod("getOpticalInsets", new Class[0]);
                try {
                    field = cls.getField("left");
                    try {
                        field2 = cls.getField("top");
                        try {
                            field3 = cls.getField("right");
                            try {
                                field4 = cls.getField("bottom");
                                z = true;
                            } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException unused) {
                                field4 = null;
                                z = false;
                            }
                        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException unused2) {
                            field3 = null;
                        }
                    } catch (ClassNotFoundException unused3) {
                        field2 = null;
                        field3 = field2;
                        field4 = null;
                        z = false;
                        if (z) {
                            sGetOpticalInsets = method;
                            sLeft = field;
                            sTop = field2;
                            sRight = field3;
                            sBottom = field4;
                            sReflectionSuccessful = true;
                            return;
                        }
                        sGetOpticalInsets = null;
                        sLeft = null;
                        sTop = null;
                        sRight = null;
                        sBottom = null;
                        sReflectionSuccessful = false;
                    } catch (NoSuchFieldException unused4) {
                        field2 = null;
                        field3 = field2;
                        field4 = null;
                        z = false;
                        if (z) {
                            sGetOpticalInsets = method;
                            sLeft = field;
                            sTop = field2;
                            sRight = field3;
                            sBottom = field4;
                            sReflectionSuccessful = true;
                            return;
                        }
                        sGetOpticalInsets = null;
                        sLeft = null;
                        sTop = null;
                        sRight = null;
                        sBottom = null;
                        sReflectionSuccessful = false;
                    } catch (NoSuchMethodException unused5) {
                        field2 = null;
                        field3 = field2;
                        field4 = null;
                        z = false;
                        if (z) {
                            sGetOpticalInsets = method;
                            sLeft = field;
                            sTop = field2;
                            sRight = field3;
                            sBottom = field4;
                            sReflectionSuccessful = true;
                            return;
                        }
                        sGetOpticalInsets = null;
                        sLeft = null;
                        sTop = null;
                        sRight = null;
                        sBottom = null;
                        sReflectionSuccessful = false;
                    }
                } catch (ClassNotFoundException unused6) {
                    field = null;
                    field2 = field;
                    field3 = field2;
                    field4 = null;
                    z = false;
                    if (z) {
                        sGetOpticalInsets = method;
                        sLeft = field;
                        sTop = field2;
                        sRight = field3;
                        sBottom = field4;
                        sReflectionSuccessful = true;
                        return;
                    }
                    sGetOpticalInsets = null;
                    sLeft = null;
                    sTop = null;
                    sRight = null;
                    sBottom = null;
                    sReflectionSuccessful = false;
                } catch (NoSuchFieldException unused7) {
                    field = null;
                    field2 = field;
                    field3 = field2;
                    field4 = null;
                    z = false;
                    if (z) {
                        sGetOpticalInsets = method;
                        sLeft = field;
                        sTop = field2;
                        sRight = field3;
                        sBottom = field4;
                        sReflectionSuccessful = true;
                        return;
                    }
                    sGetOpticalInsets = null;
                    sLeft = null;
                    sTop = null;
                    sRight = null;
                    sBottom = null;
                    sReflectionSuccessful = false;
                } catch (NoSuchMethodException unused8) {
                    field = null;
                    field2 = field;
                    field3 = field2;
                    field4 = null;
                    z = false;
                    if (z) {
                        sGetOpticalInsets = method;
                        sLeft = field;
                        sTop = field2;
                        sRight = field3;
                        sBottom = field4;
                        sReflectionSuccessful = true;
                        return;
                    }
                    sGetOpticalInsets = null;
                    sLeft = null;
                    sTop = null;
                    sRight = null;
                    sBottom = null;
                    sReflectionSuccessful = false;
                }
            } catch (ClassNotFoundException unused9) {
                method = null;
                field = null;
            } catch (NoSuchFieldException unused10) {
                method = null;
                field = null;
            } catch (NoSuchMethodException unused11) {
                method = null;
                field = null;
            }
            if (z) {
                sGetOpticalInsets = method;
                sLeft = field;
                sTop = field2;
                sRight = field3;
                sBottom = field4;
                sReflectionSuccessful = true;
                return;
            }
            sGetOpticalInsets = null;
            sLeft = null;
            sTop = null;
            sRight = null;
            sBottom = null;
            sReflectionSuccessful = false;
        }
    }

    @RequiresApi
    public static class Api29Impl {
        @DoNotInline
        public static Insets getOpticalInsets(Drawable drawable) {
            return drawable.getOpticalInsets();
        }
    }

    public static boolean canSafelyMutateDrawable(Drawable drawable) {
        return true;
    }

    public static void fixDrawable(Drawable drawable) {
        drawable.getClass();
    }

    public static Rect getOpticalBounds(Drawable drawable) {
        Insets opticalInsets = Api29Impl.getOpticalInsets(drawable);
        return new Rect(opticalInsets.left, opticalInsets.top, opticalInsets.right, opticalInsets.bottom);
    }

    public static PorterDuff.Mode parseTintMode(int i, PorterDuff.Mode mode) {
        if (i == 3) {
            return PorterDuff.Mode.SRC_OVER;
        }
        if (i == 5) {
            return PorterDuff.Mode.SRC_IN;
        }
        if (i == 9) {
            return PorterDuff.Mode.SRC_ATOP;
        }
        switch (i) {
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return mode;
        }
    }
}
