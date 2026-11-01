package com.miui.aod.monitor;

import android.content.Context;
import android.content.res.Resources;
import android.os.SystemClock;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import com.miui.aod.R;
import com.miui.aod.category.LinkageClockCategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.view.AodContainerView;
import com.miui.aod.linkagestyle.LinkageStyleClockView;
import com.miui.aod.monitor.policy.ViewSelector;
import com.miui.aod.monitor.policy.ViewTarget;
import com.miui.aod.monitor.policy.ViewTargetSet;
import com.miui.aod.monitor.policy.VisibilityPolicyRegistry;
import com.miui.aod.monitor.policy.VisibilityPolicyRegistryImpl;
import com.miui.aod.notification.NotificationController;
import com.miui.aod.util.BuildTypeUtil;
import com.miui.clock.MiuiClockView;
import com.miui.maml.folme.AnimatedProperty;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class AodViewMonitor {
    public static final boolean DEBUG = Log.isLoggable("AodViewMonitor", 3);
    public static final int TAG_KEY_LAST_SAMPLE = (AodViewMonitor.class.getName() + "#lastSampleTs").hashCode();
    public static final VisibilityPolicyRegistry POLICY_REGISTRY = new VisibilityPolicyRegistryImpl();

    public static final class SamplingContext {
        public final Integer clockStyleId;
        public final View linkageClockContainer;
        public final View miuiClockView;
        public final String templateId;

        public SamplingContext(View view, View view2, String str, Integer num) {
            this.linkageClockContainer = view;
            this.miuiClockView = view2;
            this.templateId = str;
            this.clockStyleId = num;
        }
    }

    public static void debug(String str) {
        if (DEBUG) {
            Log.d("AodViewMonitor", str);
        }
    }

    public static String entryName(View view) {
        int id;
        Resources resources;
        if (view == null || (id = view.getId()) == -1 || (resources = view.getResources()) == null) {
            return "";
        }
        try {
            return resources.getResourceEntryName(id);
        } catch (Resources.NotFoundException unused) {
            debug("entryName: id not found: " + id);
            return "";
        }
    }

    public static int getNotificationCount(Context context) {
        List notificationList = NotificationController.getInstance(context).getNotificationList();
        if (notificationList != null) {
            return notificationList.size();
        }
        return 0;
    }

    public static boolean isLinkageExpected(StyleInfo styleInfo) {
        return styleInfo instanceof LinkageClockCategoryInfo;
    }

    public static boolean isScreenOn(View view) {
        Display display = view.getDisplay();
        return display != null && display.getState() == 2;
    }

    public static SamplingContext resolveSamplingContext(View view) {
        if (view == null) {
            return new SamplingContext(null, null, null, null);
        }
        View viewFindViewById = view.findViewById(R.id.linkage_clock_container);
        boolean z = viewFindViewById instanceof LinkageStyleClockView;
        MiuiClockView miuiClockView = z ? ((LinkageStyleClockView) viewFindViewById).getMiuiClockView() : null;
        LinkageStyleClockView linkageStyleClockView = z ? (LinkageStyleClockView) viewFindViewById : null;
        return new SamplingContext(viewFindViewById, miuiClockView, linkageStyleClockView != null ? linkageStyleClockView.getCurrentClockTemplateId() : null, linkageStyleClockView != null ? Integer.valueOf(linkageStyleClockView.getCurrentClockStyle()) : null);
    }

    public static void sampleIfExpected(Context context, StyleInfo styleInfo, String str, View view, boolean z) {
        if (!z || view == null) {
            return;
        }
        boolean zIsAnomalous = VisibilitySampler.isAnomalous(view);
        StringBuilder sb = new StringBuilder();
        sb.append("check: ");
        sb.append(str);
        sb.append(" → ");
        sb.append(zIsAnomalous ? "ANOMALY" : "OK");
        sb.append(" vis=");
        sb.append(view.getVisibility());
        sb.append(" alpha=");
        sb.append(view.getAlpha());
        sb.append(" size=");
        sb.append(view.getWidth());
        sb.append(AnimatedProperty.PROPERTY_NAME_X);
        sb.append(view.getHeight());
        sb.append(" scale=");
        sb.append(view.getScaleX());
        sb.append(AnimatedProperty.PROPERTY_NAME_X);
        sb.append(view.getScaleY());
        debug(sb.toString());
        if (zIsAnomalous) {
            VisibilitySampler.report(str, view);
        }
    }

    public static void sampleLinkageParts(Context context, StyleInfo styleInfo, LinkageStyleClockView linkageStyleClockView) {
        if (linkageStyleClockView == null) {
            return;
        }
        boolean z = false;
        boolean z2 = styleInfo != null && linkageStyleClockView.shouldShowDateByTemplate();
        if (styleInfo != null && linkageStyleClockView.shouldShowLunarByTemplate() && styleInfo.isLunarSwitchOn(context)) {
            z = true;
        }
        debug("sampleLinkageParts: expectDate=" + z2 + ", expectLunar=" + z);
        View viewFindViewById = linkageStyleClockView.findViewById(R.id.date);
        if (viewFindViewById != null && z2) {
            sampleIfExpected(context, styleInfo, "linkageview.date", viewFindViewById, true);
        }
        View viewFindViewById2 = linkageStyleClockView.findViewById(R.id.date_lunar);
        if (viewFindViewById2 == null || !z) {
            return;
        }
        sampleIfExpected(context, styleInfo, "linkageview.date_lunar", viewFindViewById2, true);
    }

    public static void sampleMiuiClockParts(Context context, LinkageStyleClockView linkageStyleClockView, StyleInfo styleInfo, View view) {
        String idName;
        if (view == null || linkageStyleClockView == null) {
            return;
        }
        ViewTargetSet viewTargetSetResolveTargets = POLICY_REGISTRY.resolveTargets(linkageStyleClockView.getCurrentClockTemplateId(), linkageStyleClockView.getCurrentClockStyle());
        HashSet hashSet = new HashSet();
        HashMap map = new HashMap();
        for (ViewTarget viewTarget : viewTargetSetResolveTargets.getViewTargets()) {
            if ((viewTarget.getSelector() instanceof ViewSelector.ByIdName) && (idName = ((ViewSelector.ByIdName) viewTarget.getSelector()).getIdName()) != null && !idName.isEmpty()) {
                String lowerCase = idName.toLowerCase(Locale.ROOT);
                hashSet.add(lowerCase);
                map.put(lowerCase, idName);
            }
        }
        if (hashSet.isEmpty()) {
            return;
        }
        try {
            ArrayDeque arrayDeque = new ArrayDeque();
            arrayDeque.push(view);
            while (!arrayDeque.isEmpty()) {
                View view2 = (View) arrayDeque.pop();
                String strEntryName = entryName(view2);
                if (!strEntryName.isEmpty()) {
                    String lowerCase2 = strEntryName.toLowerCase(Locale.ROOT);
                    if (hashSet.contains(lowerCase2)) {
                        sampleIfExpected(context, styleInfo, (String) map.get(lowerCase2), view2, true);
                        hashSet.remove(lowerCase2);
                        if (hashSet.isEmpty()) {
                            return;
                        }
                    }
                }
                if (view2 instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view2;
                    for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                        arrayDeque.push(viewGroup.getChildAt(childCount));
                    }
                }
            }
        } catch (Exception e) {
            Log.w("AodViewMonitor", "sampleMiuiClockParts(single-pass): failed ", e);
        }
    }

    public static void sampleNotifAndBatteryOnEnter(Context context, StyleInfo styleInfo, View view, int i) {
        if (styleInfo != null && styleInfo.isNotificationSwitchOn() && i > 0 && view != null) {
            View viewTryResolveNotificationView = tryResolveNotificationView(view);
            sampleIfExpected(context, styleInfo, "notificationcontainer", viewTryResolveNotificationView, viewTryResolveNotificationView != null);
        }
        if (styleInfo == null || !styleInfo.isBatterySwitchOn() || view == null) {
            return;
        }
        View viewFindViewById = view.findViewById(R.id.battery_container);
        if (viewFindViewById == null) {
            View viewFindViewById2 = view.findViewById(R.id.linkage_clock_container);
            if (viewFindViewById2 instanceof LinkageStyleClockView) {
                viewFindViewById = ((LinkageStyleClockView) viewFindViewById2).getSmartFrameBatterView();
            }
        }
        if (viewFindViewById != null) {
            sampleIfExpected(context, styleInfo, "adobatterymeterview", viewFindViewById, true);
        }
    }

    public static void sampleOnAodEnter(View view, StyleInfo styleInfo) {
        View view2;
        if (BuildTypeUtil.isDebugOrBeta() && view != null) {
            long jUptimeMillis = SystemClock.uptimeMillis();
            Context context = view.getContext();
            int i = TAG_KEY_LAST_SAMPLE;
            Object tag = view.getTag(i);
            if (tag instanceof Long) {
                long jLongValue = jUptimeMillis - ((Long) tag).longValue();
                if (jLongValue < 300) {
                    debug("sampleOnAodEnter: debounced (" + jLongValue + "ms)");
                    return;
                }
            }
            view.setTag(i, Long.valueOf(jUptimeMillis));
            boolean zIsScreenOn = isScreenOn(view);
            debug("sampleOnAodEnter: begin. isScreenOn=" + zIsScreenOn);
            SamplingContext samplingContextResolveSamplingContext = resolveSamplingContext(view);
            boolean zIsLinkageExpected = isLinkageExpected(styleInfo);
            int notificationCount = getNotificationCount(context);
            if (!zIsLinkageExpected) {
                debug("sampleOnAodEnter: non-linkage style, skip sampling");
                return;
            }
            sampleRoot(context, styleInfo, view);
            View view3 = samplingContextResolveSamplingContext.linkageClockContainer;
            if (view3 instanceof LinkageStyleClockView) {
                sampleLinkageParts(context, styleInfo, (LinkageStyleClockView) view3);
            }
            sampleNotifAndBatteryOnEnter(context, styleInfo, view, notificationCount);
            if (!zIsScreenOn && (view2 = samplingContextResolveSamplingContext.miuiClockView) != null) {
                View view4 = samplingContextResolveSamplingContext.linkageClockContainer;
                if (view4 instanceof LinkageStyleClockView) {
                    try {
                        sampleMiuiClockParts(context, (LinkageStyleClockView) view4, styleInfo, view2);
                    } catch (Exception e) {
                        Log.w("AodViewMonitor", "sampleOnAodEnter: sample clock/linkage error", e);
                    }
                }
            }
            debug("sampleOnAodEnter: total costMs=" + (SystemClock.uptimeMillis() - jUptimeMillis));
        }
    }

    public static void sampleOnNotificationsChanged(AodContainerView aodContainerView) {
        if (BuildTypeUtil.isDebugOrBeta() && aodContainerView != null) {
            Context context = aodContainerView.getContext();
            StyleInfo styleInfo = aodContainerView.mStyleInfo;
            if (isLinkageExpected(styleInfo)) {
                View viewTryResolveNotificationView = tryResolveNotificationView(aodContainerView);
                boolean z = false;
                if ((styleInfo.isNotificationSwitchOn() && getNotificationCount(context) > 0) && viewTryResolveNotificationView != null) {
                    z = true;
                }
                sampleIfExpected(context, styleInfo, "notificationcontainer", viewTryResolveNotificationView, z);
            }
        }
    }

    public static void sampleRoot(Context context, StyleInfo styleInfo, View view) {
        if (view == null) {
            return;
        }
        sampleIfExpected(context, styleInfo, "aodview", view, true);
    }

    public static View tryResolveNotificationView(View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        View viewFindViewById = view.findViewById(R.id.icons);
        return viewFindViewById != null ? viewFindViewById : view.findViewById(R.id.icons_container);
    }
}
