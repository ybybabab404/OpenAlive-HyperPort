package com.miui.aod.notification;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.service.notification.NotificationListenerService;
import android.text.TextUtils;
import android.util.Log;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.util.ReflectUtil;
import com.miui.maml.util.AppIconsHelper;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import miui.content.res.IconCustomizer;
import miui.os.Build;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationUtils {
    public static final String MAIL_ICON_PKG;

    static {
        MAIL_ICON_PKG = Build.IS_INTERNATIONAL_BUILD ? "com.google.android.gm" : "com.android.email";
    }

    public static int calculateContrastColor(Context context, int i, int i2, int i3, boolean z) {
        if (i != 0) {
            i2 = resolveContrastColor(context, i, i3, z);
        } else if (i2 == 1) {
            i2 = resolveDefaultColor(context, i3, z);
        }
        return flattenAlpha(i2, i3);
    }

    public static int compositeColors(int i, int i2) {
        try {
            Class<?> cls = Class.forName("com.android.internal.util.ContrastColorUtil");
            Class cls2 = Integer.TYPE;
            return ((Integer) ReflectUtil.callStaticObjectMethod(cls, Integer.class, "compositeColors", new Class[]{cls2, cls2}, Integer.valueOf(i), Integer.valueOf(i2))).intValue();
        } catch (Throwable th) {
            Log.d("NotificationUtils", "compositeColors ERROR", th);
            return i2;
        }
    }

    public static LayerDrawable createIconWithBackground(Context context, Drawable drawable, int i, boolean z) {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.notification_right_icon_size);
        int dimensionPixelSize2 = dimensionPixelSize - (context.getResources().getDimensionPixelSize(R.dimen.notification_small_icon_background_padding) * 2);
        Resources resources = AODApplication.sysuiContext.getResources();
        int color = resources.getColor(z ? android.R.color.system_surface_container_high_dark : android.R.color.system_surface_container_high_light, AODApplication.sysuiContext.getTheme());
        int iCalculateContrastColor = calculateContrastColor(context, i, resources.getColor(z ? android.R.color.system_primary_dark : android.R.color.system_primary_light, AODApplication.sysuiContext.getTheme()), color, z);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(1);
        gradientDrawable.setColor(iCalculateContrastColor);
        drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        int i2 = dimensionPixelSize - dimensionPixelSize2;
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{gradientDrawable, drawable});
        layerDrawable.setLayerInset(1, i2, i2, i2, i2);
        layerDrawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
        return layerDrawable;
    }

    public static int flattenAlpha(int i, int i2) {
        return Color.alpha(i) == 255 ? i : compositeColors(i, i2);
    }

    public static Drawable getAppIcon(Context context, NotificationController.NotificationData notificationData, Notification notification) {
        if (!notificationData.mIsDefaultTheme) {
            return getAppIconInner(context, notification, notificationData.mPackageName, notificationData.mClassName);
        }
        if (notificationData.mUseSmallIcon) {
            return createIconWithBackground(AODApplication.sInstance, notification.getSmallIcon().loadDrawable(context), notificationData.mColor, notificationData.mIsNightMode);
        }
        Drawable customAppIcon = getCustomAppIcon(context, notification, notificationData.mPackageName);
        return customAppIcon == null ? getAppIconInner(context, notification, notificationData.mPackageName, notificationData.mClassName) : customAppIcon;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x004a  */
    /* JADX WARN: Code duplicated, block: B:25:0x0051  */
    /* JADX WARN: Code duplicated, block: B:27:0x0057  */
    /* JADX WARN: Code duplicated, block: B:28:0x005a  */
    /* JADX WARN: Code duplicated, block: B:41:? A[RETURN, SYNTHETIC] */
    public static Drawable getAppIconByPackageName(Context context, String str, String str2) {
        Drawable applicationIcon;
        String str3 = "com.android.contacts.activities.TwelveKeyDialer";
        String str4 = "com.android.contacts";
        if ("com.android.server.telecom".equals(str)) {
            str2 = "com.android.contacts.activities.TwelveKeyDialer";
            str = "com.android.contacts";
        } else if ("com.android.calendar".equals(str)) {
            str2 = "com.android.calendar.homepage.AllInOneActivity";
            str = "com.android.calendar";
        }
        if (!Build.IS_INTERNATIONAL_BUILD) {
            Drawable drawableTryGetAppIconWithComponent = tryGetAppIconWithComponent(context, str, str2);
            if (drawableTryGetAppIconWithComponent != null) {
                return drawableTryGetAppIconWithComponent;
            }
            try {
                return context.getPackageManager().getApplicationIcon(str);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("NotificationUtils", "error...", e);
                return drawableTryGetAppIconWithComponent;
            }
        }
        try {
            if (!"com.android.contacts".equals(str)) {
                str = "com.android.mms".equals(str) ? "com.google.android.apps.messaging" : "com.google.android.dialer";
                applicationIcon = context.getPackageManager().getApplicationIcon(str);
                if (applicationIcon == null) {
                    return applicationIcon;
                }
                if (!"com.google.android.dialer".equals(str)) {
                    if ("com.google.android.apps.messaging".equals(str)) {
                        str4 = "com.android.mms";
                        str3 = null;
                    } else {
                        str4 = str;
                        str3 = str2;
                    }
                }
                return tryGetAppIconWithComponent(context, str4, str3);
            }
            applicationIcon = context.getPackageManager().getApplicationIcon(str);
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e("NotificationUtils", "error...", e2);
            applicationIcon = null;
        }
        str2 = null;
        if (applicationIcon == null) {
            return applicationIcon;
        }
        if (!"com.google.android.dialer".equals(str)) {
            if ("com.google.android.apps.messaging".equals(str)) {
                str4 = "com.android.mms";
                str3 = null;
            } else {
                str4 = str;
                str3 = str2;
            }
        }
        return tryGetAppIconWithComponent(context, str4, str3);
    }

    public static Drawable getAppIconInner(Context context, Notification notification, String str, String str2) {
        Drawable appIconByPackageName = getAppIconByPackageName(context, str, str2);
        return appIconByPackageName == null ? notification.getSmallIcon().loadDrawable(context) : appIconByPackageName;
    }

    public static Drawable getCustomAppIcon(Context context, Notification notification, String str) {
        Icon miuiAppIcon;
        Drawable drawableLoadDrawable;
        if (!NotificationController.getInstance(context).canCustomAppIcon(str) || (miuiAppIcon = getMiuiAppIcon(notification)) == null || (drawableLoadDrawable = miuiAppIcon.loadDrawable(context)) == null) {
            return null;
        }
        return getIconStyleDrawable(drawableLoadDrawable, true);
    }

    public static Drawable getIconStyleDrawable(Drawable drawable, boolean z) {
        try {
            return IconCustomizer.generateIconStyleDrawable(drawable, z);
        } catch (Exception e) {
            Log.e("NotificationUtils", "getIconStyleDrawable.e", e);
            return null;
        }
    }

    public static Icon getMiuiAppIcon(Notification notification) {
        Parcelable parcelable = notification.extras.getParcelable("miui.appIcon");
        if (parcelable instanceof Icon) {
            return (Icon) parcelable;
        }
        return null;
    }

    public static String getNotificationTitle(Notification notification) {
        Bundle bundle;
        if (notification == null || (bundle = notification.extras) == null) {
            return "";
        }
        Object obj = bundle.get("android.title");
        if (obj instanceof String) {
            return (String) obj;
        }
        return obj == null ? "" : obj.toString();
    }

    public static String getTargetClass(Notification notification) {
        CharSequence charSequence;
        if (notification.extras.containsKey("miui.messageClassName")) {
            charSequence = notification.extras.getCharSequence("miui.messageClassName");
        } else {
            try {
                Field declaredField = notification.getClass().getDeclaredField("extraNotification");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(notification);
                Method declaredMethod = obj.getClass().getDeclaredMethod("getMessageClassName", new Class[0]);
                declaredMethod.setAccessible(true);
                charSequence = (CharSequence) declaredMethod.invoke(obj, new Object[0]);
            } catch (Exception e) {
                Log.e("NotificationUtils", "error...", e);
                charSequence = null;
            }
        }
        return charSequence == null ? "" : charSequence.toString();
    }

    public static CharSequence getTargetPkg(Notification notification) {
        if (notification.extras.containsKey("miui.targetPkg")) {
            return notification.extras.getCharSequence("miui.targetPkg");
        }
        try {
            Field declaredField = notification.getClass().getDeclaredField("extraNotification");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(notification);
            Method declaredMethod = obj.getClass().getDeclaredMethod("getTargetPkg", new Class[0]);
            declaredMethod.setAccessible(true);
            return (CharSequence) declaredMethod.invoke(obj, new Object[0]);
        } catch (Exception e) {
            Log.e("NotificationUtils", "error...", e);
            return "";
        }
    }

    public static Uri maybeAddUserId(Uri uri, int i) {
        return com.miui.aod.utils.NotificationUtils.maybeAddUserId(uri, i);
    }

    public static void registerAsSystemService(NotificationListenerService notificationListenerService, Context context, ComponentName componentName, int i) throws RemoteException {
        com.miui.aod.utils.NotificationUtils.registerAsSystemService(notificationListenerService, context, componentName, i);
    }

    public static int resolveContrastColor(Context context, int i, int i2, boolean z) {
        try {
            Class<?> cls = Class.forName("com.android.internal.util.ContrastColorUtil");
            Class cls2 = Integer.TYPE;
            return ((Integer) ReflectUtil.callStaticObjectMethod(cls, Integer.class, "resolveContrastColor", new Class[]{Context.class, cls2, cls2, Boolean.TYPE}, context, Integer.valueOf(i), Integer.valueOf(i2), Boolean.valueOf(z))).intValue();
        } catch (Throwable th) {
            Log.d("NotificationUtils", "resolveContrastColor ERROR", th);
            return i2;
        }
    }

    public static int resolveDefaultColor(Context context, int i, boolean z) {
        try {
            return ((Integer) ReflectUtil.callStaticObjectMethod(Class.forName("com.android.internal.util.ContrastColorUtil"), Integer.class, "resolveDefaultColor", new Class[]{Context.class, Integer.TYPE, Boolean.TYPE}, context, Integer.valueOf(i), Boolean.valueOf(z))).intValue();
        } catch (Throwable th) {
            Log.d("NotificationUtils", "resolveDefaultColor ERROR", th);
            return i;
        }
    }

    public static Drawable tryGetAppIconWithComponent(Context context, String str, String str2) {
        LauncherApps launcherApps;
        UserManager userManager;
        Context context2;
        String str3;
        String str4;
        if (TextUtils.isEmpty(str) || (launcherApps = (LauncherApps) context.getSystemService("launcherapps")) == null || (userManager = (UserManager) context.getSystemService("user")) == null) {
            return null;
        }
        List<UserHandle> userProfiles = userManager.getUserProfiles();
        if (userProfiles == null) {
            userProfiles = Collections.EMPTY_LIST;
        }
        Iterator<UserHandle> it = userProfiles.iterator();
        Drawable drawable = null;
        while (it.hasNext()) {
            for (LauncherActivityInfo launcherActivityInfo : launcherApps.getActivityList(str, it.next())) {
                String className = launcherActivityInfo.getComponentName().getClassName();
                if (str2 == null || !str2.equals(className)) {
                    context2 = context;
                    str3 = str;
                    str4 = str2;
                } else {
                    context2 = context;
                    str3 = str;
                    str4 = str2;
                    Drawable iconDrawable = AppIconsHelper.getIconDrawable(context2, str3, str4, 3600000L, launcherActivityInfo.getUser());
                    if (iconDrawable == null) {
                        iconDrawable = IconCustomizer.getCustomizedIcon(context2, str3, str4, (Drawable) null);
                    }
                    if (iconDrawable == null && launcherApps.getActivityList(str3, launcherActivityInfo.getUser()).size() == 1) {
                        iconDrawable = IconCustomizer.getCustomizedIcon(context2, str3, (String) null, (Drawable) null);
                    }
                    if (iconDrawable == null) {
                        iconDrawable = launcherActivityInfo.getIcon(0);
                    }
                    drawable = iconDrawable;
                }
                context = context2;
                str = str3;
                str2 = str4;
            }
        }
        return drawable == null ? IconCustomizer.getCustomizedIcon(context, str, (String) null, (Drawable) null) : drawable;
    }
}
