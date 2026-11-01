package com.miui.keyguard.editor.gemini;

import android.content.Context;
import android.content.Intent;
import com.google.android.appfunctions.schema.devicestate.v1.PerScreenDeviceStates;
import com.miui.keyguard.editor.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: GetEditorState.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class GetEditorState {
    public Context applicationContext;
    public List perScreenKeyList;

    public static /* synthetic */ String buildIntentUri$default(GetEditorState getEditorState, String str, Map map, Map map2, int i, Object obj) {
        if ((i & 2) != 0) {
            map = MapsKt.emptyMap();
        }
        if ((i & 4) != 0) {
            map2 = MapsKt.emptyMap();
        }
        return getEditorState.buildIntentUri(str, map, map2);
    }

    public final List buildEditorScreens(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.applicationContext = context;
        List editorScreenKey = getEditorScreenKey();
        this.perScreenKeyList = editorScreenKey;
        if (editorScreenKey == null) {
            Intrinsics.throwUninitializedPropertyAccessException("perScreenKeyList");
            editorScreenKey = null;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = editorScreenKey.iterator();
        while (it.hasNext()) {
            PerScreenDeviceStates perScreenDeviceStatesBuildPerScreenDeviceStates = buildPerScreenDeviceStates((String) it.next());
            if (perScreenDeviceStatesBuildPerScreenDeviceStates != null) {
                arrayList.add(perScreenDeviceStatesBuildPerScreenDeviceStates);
            }
        }
        return arrayList;
    }

    public final String buildIntentUri(String str, Map map, Map map2) {
        Intent intent = new Intent(str);
        intent.putExtra("is_adb", "adb");
        intent.putExtra("launched_from_package", "appfunction");
        for (Map.Entry entry : map.entrySet()) {
            intent.putExtra((String) entry.getKey(), (String) entry.getValue());
        }
        for (Map.Entry entry2 : map2.entrySet()) {
            intent.putExtra((String) entry2.getKey(), ((Number) entry2.getValue()).intValue());
        }
        String uri = intent.toUri(1);
        Intrinsics.checkNotNullExpressionValue(uri, "toUri(...)");
        return uri;
    }

    public final PerScreenDeviceStates buildPerScreenDeviceStates(String str) {
        return new PerScreenDeviceStates(editorScreenKeyDescriptionSet(str), editorIntentUriSet(str), null, 4, null);
    }

    public final String editorIntentUriSet(String str) {
        switch (str.hashCode()) {
            case -1143723586:
                return !str.equals("EDITOR_COMMON_LOCK_SCREEN") ? "" : buildIntentUri$default(this, "miui.keyguard.editor.common", null, null, 6, null);
            case -1012488310:
                return str.equals("EDITOR_COMMON_HOME_SCREEN") ? buildIntentUri$default(this, "miui.keyguard.editor.common", MapsKt.mapOf(TuplesKt.to("caller", "desktop")), null, 4, null) : "";
            case -892540667:
                return !str.equals("EDITOR_CROSS_BUILDING") ? "" : buildIntentUri$default(this, "miui.keyguard.editor.settings", MapsKt.mapOf(TuplesKt.to("is_navigate_to_my_sets", "navigate_to_my_sets")), null, 4, null);
            case -822373742:
                return !str.equals("EDITOR_CUSTOMIZE_LOCK_SCREEN") ? "" : buildIntentUri$default(this, "miui.keyguard.editor.settings", MapsKt.mapOf(TuplesKt.to("is_navigate_to_my_sets", "navigate_to_my_sets"), TuplesKt.to("is_my_customize", "my_customize")), null, 4, null);
            case -286955536:
                return !str.equals("EDITOR_POP_WALL_PAPER_DIALOG") ? "" : buildIntentUri$default(this, "miui.keyguard.editor.settings", MapsKt.mapOf(TuplesKt.to("is_pop_wallpaper_dialog", "is_pop"), TuplesKt.to("is_navigate_to_my_sets", "navigate_to_my_sets"), TuplesKt.to("is_my_customize", "my_customize")), null, 4, null);
            case 1408683582:
                return !str.equals("POP_CLOCK_FONT_FILTER_DIALOG") ? "" : buildIntentUri("miui.keyguard.editor.settings", MapsKt.mapOf(TuplesKt.to("is_navigate_to_my_sets", "navigate_to_my_sets"), TuplesKt.to("is_my_customize", "my_customize"), TuplesKt.to("is_pop_font_filter_dialog_value", "0")), MapsKt.mapOf(TuplesKt.to("is_pop_font_filter_dialog", 0)));
            default:
                return "";
        }
    }

    public final String editorScreenKeyDescriptionSet(String str) {
        Context context = null;
        switch (str) {
            case "EDITOR_COMMON_LOCK_SCREEN":
                Context context2 = this.applicationContext;
                if (context2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("applicationContext");
                } else {
                    context = context2;
                }
                String string = context.getString(R.string.kg_common_lock_screen_summary);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                return string;
            case "EDITOR_COMMON_HOME_SCREEN":
                Context context3 = this.applicationContext;
                if (context3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("applicationContext");
                } else {
                    context = context3;
                }
                String string2 = context.getString(R.string.kg_common_home_screen_summary);
                Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                return string2;
            case "EDITOR_CROSS_BUILDING":
                Context context4 = this.applicationContext;
                if (context4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("applicationContext");
                } else {
                    context = context4;
                }
                String string3 = context.getString(R.string.kg_cross_building_summary);
                Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
                return string3;
            case "EDITOR_CUSTOMIZE_LOCK_SCREEN":
                Context context5 = this.applicationContext;
                if (context5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("applicationContext");
                } else {
                    context = context5;
                }
                String string4 = context.getString(R.string.kg_customize_lock_screen_summary);
                Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
                return string4;
            case "EDITOR_POP_WALL_PAPER_DIALOG":
                Context context6 = this.applicationContext;
                if (context6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("applicationContext");
                } else {
                    context = context6;
                }
                String string5 = context.getString(R.string.kg_pop_wall_paper_dialog_summary);
                Intrinsics.checkNotNullExpressionValue(string5, "getString(...)");
                return string5;
            case "POP_CLOCK_FONT_FILTER_DIALOG":
                Context context7 = this.applicationContext;
                if (context7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("applicationContext");
                } else {
                    context = context7;
                }
                String string6 = context.getString(R.string.kg_pop_clock_font_filter_dialog_summary);
                Intrinsics.checkNotNullExpressionValue(string6, "getString(...)");
                return string6;
            default:
                return "";
        }
    }

    public final List getEditorScreenKey() {
        return CollectionsKt.listOf((Object[]) new String[]{"EDITOR_COMMON_LOCK_SCREEN", "EDITOR_CROSS_BUILDING", "EDITOR_COMMON_HOME_SCREEN", "EDITOR_POP_WALL_PAPER_DIALOG", "POP_CLOCK_FONT_FILTER_DIALOG", "EDITOR_CUSTOMIZE_LOCK_SCREEN"});
    }
}
