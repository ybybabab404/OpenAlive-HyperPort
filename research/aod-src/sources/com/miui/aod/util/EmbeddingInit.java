package com.miui.aod.util;

import android.content.ComponentName;
import android.content.Context;
import androidx.window.embedding.ActivityFilter;
import androidx.window.embedding.ActivityRule;
import androidx.window.embedding.RuleController;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public class EmbeddingInit {
    public static void init(Context context) {
        HashSet hashSet = new HashSet();
        hashSet.add(new ActivityFilter(new ComponentName(context.getApplicationContext().getPackageName(), "com.miui.aod.settings.AODStyleCategoryActivity"), (String) null));
        hashSet.add(new ActivityFilter(new ComponentName(context.getApplicationContext().getPackageName(), "com.miui.aod.settings.AodStyleCategoriesActivity"), (String) null));
        hashSet.add(new ActivityFilter(new ComponentName(context.getApplicationContext().getPackageName(), "com.miui.aod.settings.AODStyleActivity"), (String) null));
        hashSet.add(new ActivityFilter(new ComponentName(context.getApplicationContext().getPackageName(), "com.miui.aod.settings.AodStyleSelectActivity"), (String) null));
        hashSet.add(new ActivityFilter(new ComponentName(context.getApplicationContext().getPackageName(), "com.miui.aod.settings.HistoryListActivity"), (String) null));
        hashSet.add(new ActivityFilter(new ComponentName(context.getApplicationContext().getPackageName(), "com.miui.aod.settings.NotificationAnimationSelectActivity"), (String) null));
        hashSet.add(new ActivityFilter(new ComponentName(context.getApplicationContext().getPackageName(), "com.miui.aod.settings.AodShowModeSettingActivity"), (String) null));
        hashSet.add(new ActivityFilter(new ComponentName("com.android.thememanager", "com.android.thememanager.settings.ThemeAndWallpaperPickerSettingActivity"), (String) null));
        hashSet.add(new ActivityFilter(new ComponentName("com.android.thememanager", "com.android.thememanager.settings.ThemeAndWallpaperSettingDialogActivity"), (String) null));
        hashSet.add(new ActivityFilter(new ComponentName("com.android.thememanager", "com.android.thememanager.settings.WallpaperSettingsPickerActivity"), (String) null));
        hashSet.add(new ActivityFilter(new ComponentName(context.getApplicationContext().getPackageName(), "com.miui.keyguard.editor.EditorActivityForSettings"), (String) null));
        hashSet.add(new ActivityFilter(new ComponentName(context.getApplicationContext().getPackageName(), "com.miui.keyguard.editor.CommonEditorActivity"), "miui.keyguard.editor.common"));
        hashSet.add(new ActivityFilter(new ComponentName(context.getApplicationContext().getPackageName(), "com.miui.aod.settings.AodWidgetComponentActivity"), (String) null));
        RuleController.getInstance(context).addRule(new ActivityRule.Builder(hashSet).setAlwaysExpand(true).build());
    }
}
