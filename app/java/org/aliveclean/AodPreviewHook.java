package org.aliveclean;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * com.miui.aod「息屏」页底部的预览小窗。
 *
 * 真宿主：com.miui.aod.settings.AodStyleCategoriesActivity 的列表第 1 项，
 * 由 com.miui.aod.stylelist.StyleListAdapter$PreviewViewHolder 承载，
 * 容器是 R.layout.fs_aod_preview 里的 FrameLayout R.id.template_view
 * （里面放系统 FullAodTemplateView，即“系统自己生成的预览”）。
 * AodShowModeSettingActivity 只是 ShowModeFragment 的 Preference 页，没有预览容器。
 *
 * 模块壁纸生效时，在 template_view 上盖一张我们自己的静态预览图；
 * 取不到图就什么都不加，系统原预览原样保留。
 */
public final class AodPreviewHook {
    private static final String TAG = "HyperAliveHooks";
    private static final String MARK = "alive_aod_preview";
    private static final Uri PREVIEW = Uri.parse("content://org.aliveclean.scene/configuration");
    private static final java.util.Map<ImageView, String> TOKENS =
            java.util.Collections.synchronizedMap(new java.util.WeakHashMap<>());

    private AodPreviewHook() {}

    public static void install(ClassLoader loader) {
        Class<?> holder = XposedHelpers.findClassIfExists(
                "com.miui.aod.stylelist.StyleListAdapter$PreviewViewHolder", loader);
        Class<?> activity = XposedHelpers.findClassIfExists(
                "com.miui.aod.settings.AodStyleCategoriesActivity", loader);
        Class<?> settings = XposedHelpers.findClassIfExists(
                "com.miui.aod.settings.SettingsView", loader);
        Log.i(TAG, "aod preview install holder=" + (holder != null)
                + " activity=" + (activity != null) + " settings=" + (settings != null));

        if (holder != null) {
            try {
                XposedHelpers.findAndHookConstructor(holder, View.class, new XC_MethodHook() {
                    @Override protected void afterHookedMethod(MethodHookParam param) {
                        scheduleHolder(param.thisObject, 20);
                    }
                });
                Log.i(TAG, "aod preview holder ctor hook ok");
            } catch (Throwable error) {
                Log.w(TAG, "aod preview holder ctor hook failed", error);
            }
            try {
                XposedHelpers.findAndHookMethod(holder, "showClockView", new XC_MethodHook() {
                    @Override protected void afterHookedMethod(MethodHookParam param) {
                        scheduleHolder(param.thisObject, 20);
                    }
                });
                Log.i(TAG, "aod preview showClockView hook ok");
            } catch (Throwable error) {
                Log.w(TAG, "aod preview showClockView hook failed", error);
            }
            Class<?> config = XposedHelpers.findClassIfExists(
                    "com.miui.keyguard.editor.data.bean.TemplateConfig", loader);
            if (config != null) {
                try {
                    XposedHelpers.findAndHookMethod(holder, "createOrLoadTemplateView",
                            String.class, config, new XC_MethodHook() {
                                @Override protected void afterHookedMethod(MethodHookParam param) {
                                    scheduleHolder(param.thisObject, 20);
                                }
                            });
                    Log.i(TAG, "aod preview template hook ok");
                } catch (Throwable error) {
                    Log.w(TAG, "aod preview template hook failed", error);
                }
            }
        }

        if (activity != null) {
            try {
                XC_MethodHook scan = new XC_MethodHook() {
                    @Override protected void afterHookedMethod(MethodHookParam param) {
                        if (param.thisObject instanceof Activity) {
                            final Activity page = (Activity) param.thisObject;
                            page.getWindow().getDecorView().post(
                                    () -> scan(page.getWindow().getDecorView(), "page"));
                        }
                    }
                };
                XposedHelpers.findAndHookMethod(activity, "onCreate", Bundle.class, scan);
                XposedHelpers.findAndHookMethod(activity, "onResume", scan);
                Log.i(TAG, "aod preview page hook ok");
            } catch (Throwable error) {
                Log.w(TAG, "aod preview page hook failed", error);
            }
        }

        if (settings != null) {
            try {
                XposedHelpers.findAndHookMethod(settings, "onFinishInflate", new XC_MethodHook() {
                    @Override protected void afterHookedMethod(MethodHookParam param) {
                        final View view = (View) param.thisObject;
                        view.post(() -> {
                            View root = view;
                            while (root.getParent() instanceof View) root = (View) root.getParent();
                            scan(root, "settings");
                        });
                    }
                });
                Log.i(TAG, "aod preview settings hook ok");
            } catch (Throwable error) {
                Log.w(TAG, "aod preview settings hook failed", error);
            }
        }
    }

    private static void scheduleHolder(final Object holder, final int tries) {
        View item = item(holder);
        if (item == null || tries <= 0) {
            Log.w(TAG, "aod preview holder item missing");
            return;
        }
        item.postDelayed(() -> {
            Object raw = XposedHelpers.getObjectField(holder, "mTemplateContainer");
            if (raw instanceof FrameLayout) attach((FrameLayout) raw, "holder", 12);
            else if (tries > 1) scheduleHolder(holder, tries - 1);
        }, 250);
    }

    private static View item(Object holder) {
        Object value = XposedHelpers.getObjectField(holder, "itemView");
        return value instanceof View ? (View) value : null;
    }

    private static void scan(View root, String from) {
        if (root == null) return;
        try {
            int id = root.getContext().getResources().getIdentifier("template_view", "id", "com.miui.aod");
            List<FrameLayout> boxes = new ArrayList<>();
            collect(root, id, boxes);
            Log.i(TAG, "aod preview scan(" + from + ") id=" + id + " found=" + boxes.size()
                    + " root=" + root.getWidth() + "x" + root.getHeight());
            for (FrameLayout box : boxes) attach(box, from, 12);
        } catch (Throwable error) {
            Log.w(TAG, "aod preview scan failed", error);
        }
    }

    private static void collect(View view, int id, List<FrameLayout> out) {
        if (view instanceof FrameLayout && view.getId() == id) out.add((FrameLayout) view);
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) collect(group.getChildAt(i), id, out);
        }
    }

    private static void attach(final FrameLayout box, final String from, final int tries) {
        try {
            if (box.getWidth() <= 0 || box.getHeight() <= 0) {
                if (tries > 0) box.postDelayed(() -> attach(box, from, tries - 1), 250);
                else Log.w(TAG, "aod preview box never laid out (" + from + ")");
                return;
            }
            ImageView image = box.findViewWithTag(MARK);
            if (image == null) {
                image = new ImageView(box.getContext());
                image.setTag(MARK);
                image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                image.setContentDescription("HyperAlive 息屏预览");
                box.addView(image, new FrameLayout.LayoutParams(-1, -1));
            }
            box.bringChildToFront(image);
            Log.i(TAG, "aod preview attach(" + from + ") box=" + box.getWidth() + "x" + box.getHeight()
                    + " image=" + image.getWidth() + "x" + image.getHeight());
            load(box.getContext(), image, box);
        } catch (Throwable error) {
            Log.w(TAG, "aod preview attach failed", error);
        }
    }

    private static void load(final Context context, final ImageView image, final FrameLayout box) {
        final String token = String.valueOf(System.nanoTime());
        TOKENS.put(image, token);
        new Thread(() -> {
            Bitmap bitmap = null;
            try {
                Bundle result = context.getContentResolver().call(PREVIEW, "aod_preview", null, null);
                byte[] bytes = result == null ? null : result.getByteArray("image");
                if (bytes != null) bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Log.i(TAG, "aod preview provider bytes=" + (bytes == null ? 0 : bytes.length));
            } catch (Throwable error) {
                Log.w(TAG, "aod preview provider failed", error);
            }
            final Bitmap ready = bitmap;
            if (ready == null) {
                Log.w(TAG, "aod preview provider empty, keep system preview");
                image.post(() -> box.removeView(image));
                return;
            }
            image.post(() -> {
                if (!token.equals(TOKENS.get(image)) || image.getParent() != box) {
                    ready.recycle();
                    return;
                }
                image.setImageBitmap(ready);
                box.bringChildToFront(image);
                Log.i(TAG, "aod preview image " + ready.getWidth() + "x" + ready.getHeight());
            });
        }, "AliveAodPreview").start();
    }
}
