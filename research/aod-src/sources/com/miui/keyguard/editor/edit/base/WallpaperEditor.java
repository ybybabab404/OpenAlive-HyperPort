package com.miui.keyguard.editor.edit.base;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.miui.aod.category.CategoryConstants;
import com.miui.keyguard.editor.EditorActivityForSettings;
import com.miui.keyguard.editor.EditorApplicationProxy;
import com.miui.keyguard.editor.data.bean.ExtraInfo;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.SensorWallpaperPrams;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.bean.WallpaperTypeInfo;
import com.miui.keyguard.editor.edit.wallpaper.GLRenderHelper;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.utils.BitmapCreator;
import com.miui.keyguard.editor.utils.BitmapUtil;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.FileUtil;
import com.miui.keyguard.editor.utils.Wallpaper;
import com.miui.keyguard.editor.utils.task.Task;
import com.miui.keyguard.editor.video.VideoCheckUtils;
import com.miui.maml.R;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import miuix.appcompat.app.AlertDialog;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: WallpaperEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class WallpaperEditor extends AbstractEditor {
    public static final String ACTION_THEME_IMAGE_LIST_PICKER_AOD;
    public static final Companion Companion;
    public static boolean isLockPick;
    public static boolean isPickerLaunching;

    /* JADX INFO: compiled from: WallpaperEditor.kt */
    @Metadata
    @SourceDebugExtension
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void launchThemePicker$default(Companion companion, Fragment fragment, String str, boolean z, boolean z2, ComponentName componentName, int i, Object obj) {
            if ((i & 8) != 0) {
                z2 = true;
            }
            boolean z3 = z2;
            if ((i & 16) != 0) {
                componentName = null;
            }
            companion.launchThemePicker(fragment, str, z, z3, componentName);
        }

        public final boolean canScale(String str) {
            return (Intrinsics.areEqual(str, CategoryConstants.CATEGORY_SUPER_WALLPAPER) || Intrinsics.areEqual(str, "video") || Intrinsics.areEqual(str, "sensor") || Intrinsics.areEqual(str, "linkage_video")) ? false : true;
        }

        public final String getACTION_THEME_IMAGE_LIST_PICKER_AOD() {
            return WallpaperEditor.ACTION_THEME_IMAGE_LIST_PICKER_AOD;
        }

        public final boolean isActionAvailable(Context context, String str) {
            ActivityInfo activityInfo;
            Intent intent = new Intent(str);
            intent.setPackage("com.android.thememanager");
            ResolveInfo resolveInfoResolveActivity = context.getPackageManager().resolveActivity(intent, 0);
            return resolveInfoResolveActivity != null && (activityInfo = resolveInfoResolveActivity.activityInfo) != null && activityInfo.isEnabled() && resolveInfoResolveActivity.activityInfo.exported;
        }

        public final boolean isDepthVideo(WallpaperTypeInfo wallpaperTypeInfo) {
            Integer videoType;
            return FileUtil.isSupportVideoDepth() && DeviceUtil.INSTANCE.isSupportHierarchyDevices() && wallpaperTypeInfo != null && (videoType = wallpaperTypeInfo.getVideoType()) != null && videoType.intValue() == 1;
        }

        public final boolean isLockPick() {
            return WallpaperEditor.isLockPick;
        }

        public final boolean isPickerLaunching() {
            return WallpaperEditor.isPickerLaunching;
        }

        public final void launchThemePicker(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            boolean z = activity instanceof EditorActivityForSettings;
            Intent intent = new Intent();
            intent.putExtra("openSource", z ? 4 : 3);
            intent.putExtra("editEntrance", false);
            intent.putExtra("hideVideoWallpaper", false);
            intent.putExtra("isSupportOnlineDepthVideo", true);
            intent.putExtra("StartActivityWhenLocked", true);
            intent.setAction(WallpaperEditor.Companion.getACTION_THEME_IMAGE_LIST_PICKER_AOD());
            activity.startActivityForResult(intent, R.styleable.AppCompatTheme_viewInflaterClass);
        }

        public final void launchThemePicker(Fragment fragment, String templateId, boolean z, boolean z2, ComponentName componentName) {
            Intent intent;
            Intrinsics.checkNotNullParameter(fragment, "fragment");
            Intrinsics.checkNotNullParameter(templateId, "templateId");
            if (isPickerLaunching()) {
                Log.d("WallpaperEditor", "launchThemePicker: picker is launching, skip duplicate launch");
                return;
            }
            setPickerLaunching(true);
            setLockPick(z);
            Intent intent2 = new Intent();
            FragmentActivity activity = fragment.getActivity();
            intent2.putExtra("openSource", (activity == null || (intent = activity.getIntent()) == null) ? null : Integer.valueOf(intent.getIntExtra("openSource", -1)));
            intent2.putExtra("editEntrance", z2);
            intent2.putExtra("hideVideoWallpaper", Intrinsics.areEqual(templateId, "smart_frame"));
            intent2.putExtra("isSupportOnlineDepthVideo", true);
            intent2.putExtra("StartActivityWhenLocked", true);
            intent2.setPackage("com.android.thememanager");
            intent2.setAction(WallpaperEditor.Companion.getACTION_THEME_IMAGE_LIST_PICKER_AOD());
            if (componentName != null) {
                intent2.setComponent(componentName);
            }
            fragment.startActivityForResult(intent2, R.styleable.AppCompatTheme_toolbarStyle);
        }

        public final void launchThemeWallpaperRecommend(Activity activity, String source) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            Intrinsics.checkNotNullParameter(source, "source");
            Intent intent = new Intent();
            intent.setAction("miui.intent.action.WALLPAPER_RECOMMEND");
            intent.putExtra("source", source);
            activity.startActivity(intent);
        }

        public final void setLockPick(boolean z) {
            WallpaperEditor.isLockPick = z;
        }

        public final void setPickerLaunching(boolean z) {
            WallpaperEditor.isPickerLaunching = z;
        }
    }

    /* JADX INFO: compiled from: WallpaperEditor.kt */
    @Metadata
    public interface WallpaperPickerLauncher {
        void launch();
    }

    static {
        Companion companion = new Companion(null);
        Companion = companion;
        ACTION_THEME_IMAGE_LIST_PICKER_AOD = companion.isActionAvailable(EditorApplicationProxy.Companion.getApplication(), "miui.intent.action.THEME_WALLPAPER_PICKER_PAGE_AOD") ? "miui.intent.action.THEME_WALLPAPER_PICKER_PAGE_AOD" : "miui.intent.action.THEME_WALLPAPER_PICKER_PAGE";
        isLockPick = true;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WallpaperEditor(EditCallback editCallback) {
        super(editCallback);
        Intrinsics.checkNotNullParameter(editCallback, "editCallback");
    }

    public static final boolean canScale(String str) {
        return Companion.canScale(str);
    }

    public static final WallpaperTypeInfo handleWallpaperTypeInfo$lambda$1(WallpaperEditor wallpaperEditor, Intent intent, Ref.ObjectRef objectRef) {
        return wallpaperEditor.processWallpaperInfo(intent, (WeakReference) objectRef.element);
    }

    public static final void handleWallpaperTypeInfo$lambda$3(WallpaperEditor wallpaperEditor, Intent intent, Context context, WallpaperTypeInfo wallpaperTypeInfo) {
        if (wallpaperTypeInfo != null) {
            wallpaperEditor.getEditCallback().onEdited(30, wallpaperTypeInfo);
            return;
        }
        Log.w("WallpaperEditor", "handleWallpaperTypeInfo error wallpaper info is null");
        if (Intrinsics.areEqual(intent != null ? intent.getStringExtra("resourceType") : null, "video")) {
            new AlertDialog.Builder(context).setTitle(com.miui.keyguard.editor.R.string.kg_choose_video_dialog_invalid_format_title).setMessage(com.miui.keyguard.editor.R.string.kg_choose_video_dialog_invalid_format_message).setCancelable(false).setPositiveButton(com.miui.keyguard.editor.R.string.kg_confirm, new DialogInterface.OnClickListener() { // from class: com.miui.keyguard.editor.edit.base.WallpaperEditor$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
    }

    public final WallpaperTypeInfo convertToWallpaperTypeInfo(WallpaperInfo wallpaperInfo) {
        if (wallpaperInfo == null) {
            return null;
        }
        WallpaperTypeInfo wallpaperTypeInfo = new WallpaperTypeInfo(wallpaperInfo.getCurrentWallpaper(), wallpaperInfo.getResourceType(), null, null, 0, false, null, false, false, null, null, null, null, false, false, null, null, null, null, null, 1048572, null);
        wallpaperTypeInfo.setSensorWallpaperPrams(wallpaperInfo.getSensorWallpaperPrams());
        wallpaperTypeInfo.setResourcePath(wallpaperInfo.getOriginResourcePath());
        wallpaperTypeInfo.setMaskPath(wallpaperInfo.getMask());
        wallpaperTypeInfo.setPositionInfo(wallpaperInfo.getPositionInfo());
        wallpaperTypeInfo.setNeedDark(wallpaperInfo.isNeedDark());
        wallpaperTypeInfo.setSupportLoop(Boolean.valueOf(wallpaperInfo.isSupportLoop()));
        wallpaperTypeInfo.setEnableBlur(wallpaperInfo.getEnableBlur());
        wallpaperTypeInfo.setVideoType(Integer.valueOf(wallpaperInfo.getVideoType()));
        ExtraInfo extraInfo = wallpaperInfo.getExtraInfo();
        wallpaperTypeInfo.setExtraPath(extraInfo != null ? extraInfo.getOriginLivePhotoImagePath() : null);
        wallpaperTypeInfo.setAiGenerated(wallpaperInfo.isAiGenerated());
        wallpaperTypeInfo.setMattingMode(wallpaperInfo.getMattingMode());
        wallpaperTypeInfo.setSupportFullScreenAod(wallpaperInfo.isSupportFullScreenAod());
        return wallpaperTypeInfo;
    }

    public final String findCmfHint(Intent intent, String str, String str2) {
        Bundle extras;
        Set<String> setKeySet;
        WallpaperController.Companion companion = WallpaperController.Companion;
        String strExtractCmfCode = companion.extractCmfCode(str);
        if (strExtractCmfCode != null) {
            return strExtractCmfCode;
        }
        String strExtractCmfCode2 = companion.extractCmfCode(str2);
        if (strExtractCmfCode2 != null) {
            return strExtractCmfCode2;
        }
        String strExtractCmfCode3 = companion.extractCmfCode(intent != null ? intent.getDataString() : null);
        if (strExtractCmfCode3 != null) {
            return strExtractCmfCode3;
        }
        if (intent != null && (extras = intent.getExtras()) != null && (setKeySet = extras.keySet()) != null) {
            for (String str3 : setKeySet) {
                Bundle extras2 = intent.getExtras();
                Object obj = extras2 != null ? extras2.get(str3) : null;
                Intrinsics.checkNotNull(str3);
                if (StringsKt.contains((CharSequence) str3, (CharSequence) "cmf", true)) {
                    String string = obj != null ? obj.toString() : null;
                    String strExtractCmfCode4 = WallpaperController.Companion.extractCmfCode(string);
                    if (strExtractCmfCode4 != null) {
                        return strExtractCmfCode4;
                    }
                    if (string != null) {
                        if (!new Regex("[A-Z]{2}").matches(string)) {
                            string = null;
                        }
                        if (string != null) {
                            return string;
                        }
                    }
                }
                String strExtractCmfCode5 = WallpaperController.Companion.extractCmfCode(obj != null ? obj.toString() : null);
                if (strExtractCmfCode5 != null) {
                    return strExtractCmfCode5;
                }
            }
        }
        return null;
    }

    public final boolean handleThemePickerResult(int i, int i2, Intent intent, Context context) {
        boolean z;
        Intrinsics.checkNotNullParameter(context, "context");
        Log.i("WallpaperEditor", "handleThemePickerResult: requestCode = " + i + ",resultCode = " + i2);
        if (i == 113 && i2 == 114) {
            handleWallpaperTypeInfo(intent, context);
            z = true;
        } else {
            z = false;
        }
        isPickerLaunching = false;
        return z;
    }

    public final void handleWallpaperTypeInfo(final Intent intent, final Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = new WeakReference(context);
        Task.create(new Supplier() { // from class: com.miui.keyguard.editor.edit.base.WallpaperEditor$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return WallpaperEditor.handleWallpaperTypeInfo$lambda$1(this.f$0, intent, objectRef);
            }
        }).post(new Consumer() { // from class: com.miui.keyguard.editor.edit.base.WallpaperEditor$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                WallpaperEditor.handleWallpaperTypeInfo$lambda$3(this.f$0, intent, context, (WallpaperTypeInfo) obj);
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0041  */
    public final String obtainFile(Context context, Uri uri) {
        String str;
        if (uri == null) {
            Log.w("WallpaperEditor", "get uri from picker is null! ");
            return null;
        }
        Log.d("WallpaperEditor", "wallpaper uri = " + uri);
        if (context == null) {
            Log.w("WallpaperEditor", "handleWallpaperTypeInfo error context is null");
            return null;
        }
        String strResolveFileSuffix = FileUtil.resolveFileSuffix(context, uri);
        if (strResolveFileSuffix != null) {
            str = "." + strResolveFileSuffix;
            if (str == null) {
                str = "";
            }
        } else {
            str = "";
        }
        Log.d("WallpaperEditor", "wallpaper fileSuffix = " + str);
        File file = new File(context.getExternalCacheDir(), "wallpaper" + str);
        if (Intrinsics.areEqual(uri.getScheme(), "history")) {
            FileUtil.fastCopy(uri.getPath(), file.getAbsolutePath(), false);
            return file.getAbsolutePath();
        }
        if (!FileUtil.copyExternalFile(context, uri, file)) {
            Log.w("WallpaperEditor", "copy wallpaper failed");
            return null;
        }
        String absolutePath = file.getAbsolutePath();
        Log.d("WallpaperEditor", "wallpaper path = " + absolutePath);
        if (TextUtils.isEmpty(absolutePath)) {
            Log.w("WallpaperEditor", "resolve wallpaper uri failed");
        }
        return absolutePath;
    }

    /* JADX WARN: Code duplicated, block: B:161:0x03dc  */
    /* JADX WARN: Code duplicated, block: B:162:0x03e3  */
    /* JADX WARN: Code duplicated, block: B:164:0x03e7  */
    /* JADX WARN: Code duplicated, block: B:165:0x03ee  */
    /* JADX WARN: Code duplicated, block: B:167:0x03f2  */
    /* JADX WARN: Code duplicated, block: B:168:0x03fe  */
    /* JADX WARN: Code duplicated, block: B:170:0x0403  */
    /* JADX WARN: Code duplicated, block: B:171:0x040e  */
    /* JADX WARN: Code duplicated, block: B:174:0x0414  */
    /* JADX WARN: Code duplicated, block: B:175:0x0421  */
    /* JADX WARN: Code duplicated, block: B:178:0x0427  */
    /* JADX WARN: Code duplicated, block: B:179:0x0433  */
    /* JADX WARN: Code duplicated, block: B:182:0x0439  */
    /* JADX WARN: Code duplicated, block: B:183:0x0445  */
    /* JADX WARN: Code duplicated, block: B:185:0x0449  */
    /* JADX WARN: Code duplicated, block: B:186:0x0450  */
    /* JADX WARN: Code duplicated, block: B:191:0x045e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:192:0x0460  */
    /* JADX WARN: Code duplicated, block: B:193:0x0467  */
    /* JADX WARN: Code duplicated, block: B:195:0x046b A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:196:0x046d  */
    /* JADX WARN: Code duplicated, block: B:197:0x0474  */
    /* JADX WARN: Code duplicated, block: B:200:0x047a  */
    /* JADX WARN: Code duplicated, block: B:201:0x0487  */
    /* JADX WARN: Code duplicated, block: B:204:0x0491  */
    /* JADX WARN: Code duplicated, block: B:219:0x051b  */
    /* JADX WARN: Code duplicated, block: B:222:0x053b  */
    /* JADX WARN: Code duplicated, block: B:223:0x0540  */
    /* JADX WARN: Code duplicated, block: B:232:0x0560  */
    /* JADX WARN: Code duplicated, block: B:268:0x0648  */
    /* JADX WARN: Code duplicated, block: B:271:0x065f  */
    /* JADX WARN: Code duplicated, block: B:272:0x066c  */
    /* JADX WARN: Code duplicated, block: B:274:0x0670  */
    /* JADX WARN: Code duplicated, block: B:275:0x067b  */
    /* JADX WARN: Code duplicated, block: B:277:0x067e  */
    /* JADX WARN: Code duplicated, block: B:278:0x0689  */
    /* JADX WARN: Code duplicated, block: B:280:0x068c  */
    /* JADX WARN: Code duplicated, block: B:281:0x0696  */
    /* JADX WARN: Code duplicated, block: B:283:0x069a  */
    /* JADX WARN: Code duplicated, block: B:284:0x06a7  */
    /* JADX WARN: Code duplicated, block: B:287:0x06bb  */
    /* JADX WARN: Code duplicated, block: B:288:0x06c2  */
    /* JADX WARN: Code duplicated, block: B:290:0x06c5  */
    /* JADX WARN: Code duplicated, block: B:292:0x06d3  */
    /* JADX WARN: Code duplicated, block: B:294:0x06d8  */
    /* JADX WARN: Code duplicated, block: B:297:0x06e3  */
    /* JADX WARN: Code duplicated, block: B:301:0x06ee  */
    /* JADX WARN: Code duplicated, block: B:302:0x06fb  */
    /* JADX WARN: Code duplicated, block: B:304:0x06fe  */
    /* JADX WARN: Code duplicated, block: B:305:0x0707  */
    /* JADX WARN: Code duplicated, block: B:309:0x073b  */
    /* JADX WARN: Code duplicated, block: B:314:0x074e  */
    /* JADX WARN: Code duplicated, block: B:317:0x0772  */
    /* JADX WARN: Code duplicated, block: B:319:0x0778  */
    /* JADX WARN: Code duplicated, block: B:322:0x0788  */
    /* JADX WARN: Code duplicated, block: B:323:0x0791  */
    /* JADX WARN: Code duplicated, block: B:324:0x0796  */
    /* JADX WARN: Code duplicated, block: B:326:0x07a0  */
    /* JADX WARN: Code duplicated, block: B:328:0x07a8  */
    /* JADX WARN: Code duplicated, block: B:331:0x07bf  */
    /* JADX WARN: Code duplicated, block: B:333:0x07dd  */
    /* JADX WARN: Code duplicated, block: B:341:0x0808  */
    /* JADX WARN: Code duplicated, block: B:343:0x0815  */
    /* JADX WARN: Code duplicated, block: B:344:0x0817  */
    /* JADX WARN: Code duplicated, block: B:346:0x0840 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:347:0x0842  */
    /* JADX WARN: Code duplicated, block: B:348:0x0847  */
    /* JADX WARN: Code duplicated, block: B:350:0x0865 A[PHI: r18 r22 r34
  0x0865: PHI (r18v12 java.lang.Boolean) = (r18v13 java.lang.Boolean), (r18v15 java.lang.Boolean) binds: [B:360:0x08ae, B:349:0x0848] A[DONT_GENERATE, DONT_INLINE]
  0x0865: PHI (r22v8 java.lang.Boolean) = (r22v9 java.lang.Boolean), (r22v11 java.lang.Boolean) binds: [B:360:0x08ae, B:349:0x0848] A[DONT_GENERATE, DONT_INLINE]
  0x0865: PHI (r34v6 java.lang.String) = (r34v7 java.lang.String), (r34v11 java.lang.String) binds: [B:360:0x08ae, B:349:0x0848] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:351:0x086b  */
    /* JADX WARN: Code duplicated, block: B:352:0x086e  */
    /* JADX WARN: Code duplicated, block: B:355:0x087b  */
    /* JADX WARN: Code duplicated, block: B:356:0x088c  */
    /* JADX WARN: Code duplicated, block: B:359:0x08aa  */
    /* JADX WARN: Code duplicated, block: B:361:0x08b0  */
    /* JADX WARN: Code duplicated, block: B:362:0x08b6  */
    /* JADX WARN: Code duplicated, block: B:365:0x08cf  */
    /* JADX WARN: Code duplicated, block: B:366:0x08d2  */
    /* JADX WARN: Code duplicated, block: B:369:0x08d9  */
    /* JADX WARN: Code duplicated, block: B:371:0x08f5  */
    /* JADX WARN: Code duplicated, block: B:372:0x08fc  */
    /* JADX WARN: Code duplicated, block: B:375:0x0916  */
    /* JADX WARN: Code duplicated, block: B:376:0x091f  */
    /* JADX WARN: Code duplicated, block: B:378:0x0922  */
    /* JADX WARN: Code duplicated, block: B:379:0x092b  */
    /* JADX WARN: Code duplicated, block: B:382:0x094c  */
    /* JADX WARN: Code duplicated, block: B:383:0x094f  */
    /* JADX WARN: Code duplicated, block: B:385:0x0953  */
    /* JADX WARN: Code duplicated, block: B:386:0x095a  */
    /* JADX WARN: Code duplicated, block: B:388:0x095e  */
    /* JADX WARN: Code duplicated, block: B:389:0x0965  */
    /* JADX WARN: Instruction removed from duplicated block: B:328:0x07a8, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:331:0x07bf, please report this as an issue */
    public final WallpaperTypeInfo processWallpaperInfo(Intent intent, WeakReference contextReference) {
        WallpaperEditor wallpaperEditor;
        String strObtainFile;
        Object obj;
        int i;
        String stringExtra;
        String stringExtra2;
        int i2;
        Integer numValueOf;
        Integer numValueOf2;
        Integer num;
        Integer numValueOf3;
        Integer numValueOf4;
        Float fValueOf;
        String stringExtra3;
        String stringExtra4;
        Object obj2;
        String stringExtra5;
        Float f;
        Object obj3;
        Integer num2;
        Integer num3;
        Integer num4;
        String str;
        String str2;
        String str3;
        String str4;
        boolean z;
        Boolean boolValueOf;
        Boolean boolValueOf2;
        Boolean boolValueOf3;
        String stringExtra6;
        Boolean boolValueOf4;
        int iWidth;
        int iHeight;
        String stringExtra7;
        Boolean bool;
        Boolean bool2;
        String str5;
        Bitmap bitmapResizeImageWallpaper;
        Bitmap bitmapDecodeFile$default;
        String str6;
        MagicType magicType;
        Object data;
        Integer num5;
        Bitmap depthBitmap;
        Integer numValueOf5;
        Integer numValueOf6;
        boolean z2;
        boolean zBooleanValue;
        boolean zBooleanValue2;
        Object data2;
        int pictureDegree;
        Bitmap bitmapResizeImageWallpaper2;
        int iHashCode;
        File file;
        Uri data3;
        Bitmap bitmapCreateVideoLastFrameThumbnail;
        boolean z3;
        Integer numValueOf7;
        Uri uri;
        String strObtainFile2;
        String str7;
        int pictureDegree2;
        Bitmap bitmapResizeImageWallpaper3;
        Bitmap bitmapResizeImageWallpaper4;
        WallpaperController.Companion companion;
        String dataString;
        boolean z4;
        Bundle extras;
        Set<String> setKeySet;
        String str8 = "video";
        Intrinsics.checkNotNullParameter(contextReference, "contextReference");
        Uri data4 = intent != null ? intent.getData() : null;
        String stringExtra8 = intent != null ? intent.getStringExtra("resourcePath") : null;
        String stringExtra9 = intent != null ? intent.getStringExtra("resourceType") : null;
        Integer numValueOf8 = intent != null ? Integer.valueOf(intent.getIntExtra("videoType", 0)) : null;
        String stringExtra10 = intent != null ? intent.getStringExtra("maskPath") : null;
        String stringExtra11 = intent != null ? intent.getStringExtra("imagePath") : null;
        String str9 = stringExtra10;
        Integer numValueOf9 = intent != null ? Integer.valueOf(intent.getIntExtra("mattingMode", 1)) : null;
        Log.d("WallpaperEditor", "processWallpaperInfo:  data=" + data4 + " resourcePath=" + stringExtra8 + " resourceType=" + stringExtra9 + " videoType=" + numValueOf8 + " maskPath=" + str9 + " imagePath=" + stringExtra11 + " mattingMode=" + numValueOf9 + " sensorThumbnail=" + (intent != null ? intent.getStringExtra("sensorThumbnail") : null) + " sensorSmallThumbnail=" + (intent != null ? intent.getStringExtra("sensorSmallThumbnail") : null) + " sensor_all_frames=" + (intent != null ? Integer.valueOf(intent.getIntExtra("sensor_all_frames", -1)) : null) + " sensor_small_frames=" + (intent != null ? Integer.valueOf(intent.getIntExtra("sensor_small_frames", -1)) : null) + " sensor_type_stiffness=" + (intent != null ? Integer.valueOf(intent.getIntExtra("sensor_type_stiffness", 10)) : null) + " sensor_duration=" + (intent != null ? Integer.valueOf(intent.getIntExtra("sensor_anim_duration", 0)) : null) + " sensor_factor=" + (intent != null ? Float.valueOf(intent.getFloatExtra("sensor_anim_factor", 0.0f)) : null) + " sensor_anim_type=" + (intent != null ? intent.getStringExtra("sensor_first_anim_type") : null) + " sensor_outer_video=" + (intent != null ? intent.getStringExtra("sensor_outer_video") : null) + " sensor_outer_video_preview=" + (intent != null ? intent.getStringExtra("sensor_outer_video_preview") : null) + " sensor_outer_video_home=" + (intent != null ? intent.getStringExtra("sensor_outer_video_home") : null) + " sensor_outer_video_lock=" + (intent != null ? intent.getStringExtra("sensor_outer_video_lock") : null) + " isSupportLoop=" + (intent != null ? Boolean.valueOf(intent.getBooleanExtra("isSupportLoop", false)) : null) + " livephoto_video_check_result_code=" + (intent != null ? Integer.valueOf(intent.getIntExtra("livephoto_video_check_result_code", 1000)) : null) + " videoUri=" + (intent != null ? (Uri) intent.getParcelableExtra("videoUri") : null) + " isDirectGenerate=" + (intent != null ? Boolean.valueOf(intent.getBooleanExtra("isDirectGenerate", false)) : null) + " isAiGenerated=" + (intent != null ? Boolean.valueOf(intent.getBooleanExtra("isAiGenerated", false)) : null) + " isSupportFullScreenAod=" + (intent != null ? Boolean.valueOf(intent.getBooleanExtra("isSupportFullScreenAod", false)) : null));
        Context context = (Context) contextReference.get();
        if (context == null) {
            return null;
        }
        Log.w("WallpaperEditor", "handleWallpaperTypeInfo: " + ((intent != null ? intent.getData() : null) != null));
        if ((intent != null ? intent.getData() : null) != null) {
            wallpaperEditor = this;
            strObtainFile = wallpaperEditor.obtainFile((Context) contextReference.get(), intent.getData());
        } else {
            wallpaperEditor = this;
            strObtainFile = null;
        }
        String stringExtra12 = intent != null ? intent.getStringExtra("resourcePath") : null;
        if (strObtainFile == null || strObtainFile.length() == 0) {
            strObtainFile = stringExtra12;
        }
        if (strObtainFile == null || strObtainFile.length() == 0) {
            Log.e("WallpaperEditor", "handleWallpaperTypeInfo: get path error");
            return null;
        }
        String stringExtra13 = intent != null ? intent.getStringExtra("resourceType") : null;
        Integer numValueOf10 = intent != null ? Integer.valueOf(intent.getIntExtra("videoType", 0)) : null;
        String stringExtra14 = intent != null ? intent.getStringExtra("maskPath") : null;
        int intExtra = intent != null ? intent.getIntExtra("mattingMode", 1) : 1;
        Log.i("WallpaperEditor", "handleWallpaperTypeInfo resourceType = " + stringExtra13 + ", mattingMode = " + intExtra);
        if (!Intrinsics.areEqual(stringExtra13, "image")) {
            obj = "livephoto";
            if (Intrinsics.areEqual(stringExtra13, obj)) {
            }
            if (intent != null) {
                stringExtra = intent.getStringExtra("sensorThumbnail");
            } else {
                stringExtra = null;
            }
            if (intent != null) {
                stringExtra2 = intent.getStringExtra("sensorSmallThumbnail");
            } else {
                stringExtra2 = null;
            }
            if (intent != null) {
                i2 = -1;
                numValueOf = Integer.valueOf(intent.getIntExtra("sensor_all_frames", -1));
            } else {
                i2 = -1;
                numValueOf = null;
            }
            if (intent != null) {
                numValueOf2 = Integer.valueOf(intent.getIntExtra("sensor_small_frames", i2));
            } else {
                numValueOf2 = null;
            }
            num = numValueOf10;
            if (intent != null) {
                numValueOf3 = Integer.valueOf(intent.getIntExtra("sensor_type_stiffness", 10));
            } else {
                numValueOf3 = null;
            }
            if (intent != null) {
                numValueOf4 = Integer.valueOf(intent.getIntExtra("sensor_anim_duration", 0));
            } else {
                numValueOf4 = null;
            }
            if (intent != null) {
                fValueOf = Float.valueOf(intent.getFloatExtra("sensor_anim_factor", 0.0f));
            } else {
                fValueOf = null;
            }
            if (intent != null) {
                stringExtra3 = intent.getStringExtra("sensor_first_anim_type");
            } else {
                stringExtra3 = null;
            }
            Object obj4 = obj;
            if (intent != null || (stringExtra4 = intent.getStringExtra("sensor_outer_video")) == null) {
                if (intent != null) {
                    stringExtra4 = intent.getStringExtra("sensor_outer_video_home");
                } else {
                    stringExtra4 = null;
                }
                if (stringExtra4 == null) {
                    if (intent != null) {
                        stringExtra4 = intent.getStringExtra("sensor_outer_video_lock");
                    } else {
                        stringExtra4 = null;
                    }
                }
            }
            if (intent != null) {
                stringExtra5 = intent.getStringExtra("sensor_outer_video_preview");
                obj2 = "sensor";
            } else {
                obj2 = "sensor";
                stringExtra5 = null;
            }
            if (Intrinsics.areEqual(stringExtra13, obj2)) {
                obj3 = obj2;
                StringBuilder sb = new StringBuilder();
                String str10 = stringExtra4;
                sb.append("sensor: allFrames=");
                sb.append(numValueOf);
                sb.append("， smallFrames=");
                sb.append(numValueOf2);
                sb.append("， stiffness=");
                sb.append(numValueOf3);
                sb.append("， duration=");
                sb.append(numValueOf4);
                sb.append("， factor=");
                sb.append(fValueOf);
                sb.append("， animType=");
                sb.append(stringExtra3);
                Log.i("WallpaperEditor", sb.toString());
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                if (intent != null || (extras = intent.getExtras()) == null || (setKeySet = extras.keySet()) == null) {
                    f = fValueOf;
                    num2 = numValueOf3;
                } else {
                    for (String str11 : setKeySet) {
                        Float f2 = fValueOf;
                        Integer num6 = numValueOf3;
                        Bundle extras2 = intent.getExtras();
                        linkedHashMap.put(str11, extras2 != null ? extras2.get(str11) : null);
                        numValueOf3 = num6;
                        fValueOf = f2;
                    }
                    f = fValueOf;
                    num2 = numValueOf3;
                    Unit unit = Unit.INSTANCE;
                }
                Log.i("WallpaperEditor", "processWallpaperInfo sensor Intent extras: " + linkedHashMap);
                String strFindCmfHint = wallpaperEditor.findCmfHint(intent, strObtainFile, stringExtra12);
                companion = WallpaperController.Companion;
                if (intent != null) {
                    dataString = intent.getDataString();
                } else {
                    dataString = null;
                }
                if (companion.shouldApplyOuterVideoWallpaper(stringExtra13, strObtainFile, null, stringExtra12, dataString, stringExtra, stringExtra2) && (!DeviceUtil.INSTANCE.isQ18() || strFindCmfHint == null || strFindCmfHint.length() == 0)) {
                    z4 = false;
                } else {
                    z4 = true;
                }
                boolean zIsQ18 = DeviceUtil.INSTANCE.isQ18();
                num3 = numValueOf4;
                StringBuilder sb2 = new StringBuilder();
                num4 = numValueOf2;
                sb2.append("processWallpaperInfo: isQ18=");
                sb2.append(zIsQ18);
                sb2.append(" shouldApplyOuterVideoWallpaper=");
                sb2.append(z4);
                sb2.append(" cmfHint=");
                sb2.append(strFindCmfHint);
                sb2.append(" outerVideoPath=");
                stringExtra4 = str10;
                sb2.append(stringExtra4);
                sb2.append(" outerVideoPreViewPath=");
                str = stringExtra5;
                sb2.append(str);
                Log.d("WallpaperEditor", sb2.toString());
                if (!z4 && (stringExtra4 == null || stringExtra4.length() == 0 || str == null || str.length() == 0)) {
                    String presetCmfDynamicVideoPath = companion.getPresetCmfDynamicVideoPath(stringExtra12 == null ? strObtainFile : stringExtra12, strFindCmfHint);
                    stringExtra2 = stringExtra2;
                    String presetCmfDynamicPreviewPath = companion.getPresetCmfDynamicPreviewPath(stringExtra12 == null ? strObtainFile : stringExtra12, strFindCmfHint);
                    StringBuilder sb3 = new StringBuilder();
                    numValueOf = numValueOf;
                    sb3.append("processWallpaperInfo sensor Q18 outer fallback: path=");
                    sb3.append(strObtainFile);
                    sb3.append(", resourcePath=");
                    sb3.append(stringExtra12);
                    sb3.append(", shouldApplyOuterVideoWallpaper=");
                    sb3.append(z4);
                    sb3.append(", cmfHint=");
                    sb3.append(strFindCmfHint);
                    sb3.append(", presetOuterVideoPath=");
                    sb3.append(presetCmfDynamicVideoPath);
                    sb3.append(", presetOuterPreviewPath=");
                    sb3.append(presetCmfDynamicPreviewPath);
                    sb3.append(", outerVideoPath=");
                    sb3.append(stringExtra4);
                    sb3.append(", outerVideoPreViewPath=");
                    sb3.append(str);
                    Log.i("WallpaperEditor", sb3.toString());
                    if (stringExtra4 == null || stringExtra4.length() == 0) {
                        stringExtra4 = presetCmfDynamicVideoPath;
                    }
                    if (str != null && str.length() != 0) {
                        presetCmfDynamicPreviewPath = str;
                    }
                    if (presetCmfDynamicPreviewPath == null || presetCmfDynamicPreviewPath.length() == 0) {
                        str3 = presetCmfDynamicPreviewPath;
                        str2 = stringExtra4;
                        str4 = stringExtra2;
                    } else {
                        str4 = presetCmfDynamicPreviewPath;
                        str3 = str4;
                        str2 = stringExtra4;
                    }
                }
                if (intent != null) {
                    z = false;
                    boolValueOf = Boolean.valueOf(intent.getBooleanExtra("isSupportLoop", false));
                } else {
                    z = false;
                    boolValueOf = null;
                }
                if (intent != null) {
                    boolValueOf2 = Boolean.valueOf(intent.getBooleanExtra("isDirectGenerate", z));
                } else {
                    boolValueOf2 = null;
                }
                if (intent != null) {
                    boolValueOf3 = Boolean.valueOf(intent.getBooleanExtra("isAiGenerated", z));
                } else {
                    boolValueOf3 = null;
                }
                if (intent != null) {
                    stringExtra6 = intent.getStringExtra("wallpaper_from");
                } else {
                    stringExtra6 = null;
                }
                if (intent != null) {
                    boolValueOf4 = Boolean.valueOf(intent.getBooleanExtra("isSupportFullScreenAod", z));
                } else {
                    boolValueOf4 = null;
                }
                Rect rectCurrentDisplaySize = DeviceUtil.INSTANCE.currentDisplaySize(context);
                iWidth = rectCurrentDisplaySize.width();
                iHeight = rectCurrentDisplaySize.height();
                if (intent != null) {
                    stringExtra7 = intent.getStringExtra("imagePath");
                } else {
                    stringExtra7 = null;
                }
                if (stringExtra13 != null) {
                    iHashCode = stringExtra13.hashCode();
                    bool = boolValueOf2;
                    bool2 = boolValueOf3;
                    if (iHashCode == -905948230) {
                        str5 = stringExtra7;
                        if (stringExtra13.equals(obj3)) {
                            BitmapCreator bitmapCreator = BitmapCreator.INSTANCE;
                            bitmapDecodeFile$default = BitmapCreator.decodeFile$default(bitmapCreator, str4, null, 2, null);
                            bitmapResizeImageWallpaper = BitmapCreator.decodeFile$default(bitmapCreator, stringExtra, null, 2, null);
                            num = num;
                            strObtainFile = strObtainFile;
                            str6 = null;
                        }
                        magicType = MagicType.INSTANCE;
                        data = wallpaperEditor.getEditCallback().getData(MagicType.MAGIC_TYPE, new Object[0]);
                        if (data instanceof Integer) {
                            num5 = (Integer) data;
                        } else {
                            num5 = null;
                        }
                        if (magicType.isFrontBack(num5)) {
                            data2 = wallpaperEditor.getEditCallback().getData("support_font_back", stringExtra13);
                            Intrinsics.checkNotNull(data2, "null cannot be cast to non-null type kotlin.Boolean");
                            if (((Boolean) data2).booleanValue()) {
                                depthBitmap = GLRenderHelper.INSTANCE.getDepthBitmap(bitmapResizeImageWallpaper);
                            } else {
                                depthBitmap = null;
                            }
                        } else {
                            depthBitmap = null;
                        }
                        boolean zIsSupportBlurWallpaperType = Wallpaper.Companion.isSupportBlurWallpaperType(stringExtra13, 0);
                        SensorWallpaperPrams sensorWallpaperPrams = new SensorWallpaperPrams(stringExtra, str4, numValueOf, num4, num2, num3, f, stringExtra3, str2, str3);
                        sensorWallpaperPrams.setCurrentSmallSensorWallpaper(bitmapDecodeFile$default);
                        if (bitmapResizeImageWallpaper != null) {
                            numValueOf5 = Integer.valueOf(bitmapResizeImageWallpaper.getWidth());
                        } else {
                            numValueOf5 = null;
                        }
                        if (bitmapResizeImageWallpaper != null) {
                            numValueOf6 = Integer.valueOf(bitmapResizeImageWallpaper.getHeight());
                        } else {
                            numValueOf6 = null;
                        }
                        Log.d("WallpaperEditor", "bitmap width:" + numValueOf5 + " height:" + numValueOf6);
                        if (i != 0) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (bool2 != null) {
                            zBooleanValue = bool2.booleanValue();
                        } else {
                            zBooleanValue = false;
                        }
                        if (bool != null) {
                            zBooleanValue2 = bool.booleanValue();
                        } else {
                            zBooleanValue2 = false;
                        }
                        return new WallpaperTypeInfo(bitmapResizeImageWallpaper, stringExtra13, strObtainFile, stringExtra14, i, z2, sensorWallpaperPrams, false, zIsSupportBlurWallpaperType, boolValueOf, depthBitmap, num, str6, zBooleanValue, zBooleanValue2, null, stringExtra6, null, boolValueOf4, str5, 163840, null);
                    }
                    if (iHashCode != 112202875) {
                        if (iHashCode == 1024344262 && stringExtra13.equals(obj4)) {
                            if (intent != null) {
                                numValueOf7 = Integer.valueOf(intent.getIntExtra("livephoto_video_check_result_code", 1000));
                            } else {
                                numValueOf7 = null;
                            }
                            if (intent != null) {
                                uri = (Uri) intent.getParcelableExtra("videoUri");
                            } else {
                                uri = null;
                            }
                            strObtainFile2 = wallpaperEditor.obtainFile(context, uri);
                            Log.d("WallpaperEditor", "live photo: livePhotoCheckCode=" + numValueOf7 + " imgPath=" + strObtainFile + " videoUri=" + uri + " videoPath=" + strObtainFile2);
                            if (numValueOf7 == null && numValueOf7.intValue() == 0 && FileUtil.isFileExist(strObtainFile2)) {
                                num = 2;
                                str7 = strObtainFile;
                            } else {
                                num = 3;
                                strObtainFile2 = strObtainFile;
                                str8 = "image";
                                str7 = null;
                            }
                            pictureDegree2 = wallpaperEditor.readPictureDegree(strObtainFile);
                            Log.i("WallpaperEditor", "degree:==" + pictureDegree2);
                            if (pictureDegree2 != 0) {
                                bitmapResizeImageWallpaper4 = wallpaperEditor.resizeImageWallpaper(strObtainFile);
                                if (bitmapResizeImageWallpaper4 != null) {
                                    bitmapResizeImageWallpaper3 = wallpaperEditor.revolveImageView(pictureDegree2, bitmapResizeImageWallpaper4);
                                } else {
                                    str6 = str7;
                                    strObtainFile = strObtainFile2;
                                    str5 = stringExtra7;
                                    stringExtra13 = str8;
                                    bitmapResizeImageWallpaper = null;
                                }
                            } else {
                                bitmapResizeImageWallpaper3 = wallpaperEditor.resizeImageWallpaper(strObtainFile);
                            }
                            Bitmap bitmap = bitmapResizeImageWallpaper3;
                            str6 = str7;
                            bitmapResizeImageWallpaper = bitmap;
                            strObtainFile = strObtainFile2;
                            str5 = stringExtra7;
                            stringExtra13 = str8;
                        }
                    } else if (!stringExtra13.equals(str8)) {
                        str5 = stringExtra7;
                    } else {
                        if (!VideoCheckUtils.Companion.isVideoCodecSupported(strObtainFile)) {
                            Log.e("WallpaperEditor", "handleWallpaperTypeInfo: unsupported video codec, path=" + strObtainFile);
                            return null;
                        }
                        Log.d("WallpaperEditor", "handleWallpaperTypeInfo: imagePath is " + stringExtra7 + ",path is " + strObtainFile);
                        if (stringExtra7 != null || stringExtra7.length() <= 0) {
                            str5 = stringExtra7;
                            file = new File(strObtainFile);
                            if (file.exists()) {
                                bitmapCreateVideoLastFrameThumbnail = BitmapUtil.INSTANCE.createVideoLastFrameThumbnail(file, new Size(iWidth, iHeight));
                                if (bitmapCreateVideoLastFrameThumbnail != null) {
                                    z3 = true;
                                } else {
                                    z3 = false;
                                }
                                Log.d("WallpaperEditor", "create video thumbnail bitmap=" + z3 + " screenWidth=" + iWidth + " screenHeight=" + iHeight);
                                bitmapResizeImageWallpaper = bitmapCreateVideoLastFrameThumbnail;
                            } else {
                                if (intent != null) {
                                    data3 = intent.getData();
                                } else {
                                    data3 = null;
                                }
                                Log.e("WallpaperEditor", "video file not exit path=" + strObtainFile + " uri=" + data3);
                                num = num;
                                bitmapResizeImageWallpaper = null;
                                str6 = null;
                            }
                        } else {
                            str5 = stringExtra7;
                            bitmapResizeImageWallpaper = BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, str5, 0, 0, null, 14, null);
                        }
                        str6 = null;
                    }
                    bitmapDecodeFile$default = null;
                    magicType = MagicType.INSTANCE;
                    data = wallpaperEditor.getEditCallback().getData(MagicType.MAGIC_TYPE, new Object[0]);
                    if (data instanceof Integer) {
                        num5 = (Integer) data;
                    } else {
                        num5 = null;
                    }
                    if (magicType.isFrontBack(num5)) {
                        data2 = wallpaperEditor.getEditCallback().getData("support_font_back", stringExtra13);
                        Intrinsics.checkNotNull(data2, "null cannot be cast to non-null type kotlin.Boolean");
                        if (((Boolean) data2).booleanValue()) {
                            depthBitmap = GLRenderHelper.INSTANCE.getDepthBitmap(bitmapResizeImageWallpaper);
                        } else {
                            depthBitmap = null;
                        }
                    } else {
                        depthBitmap = null;
                    }
                    boolean zIsSupportBlurWallpaperType2 = Wallpaper.Companion.isSupportBlurWallpaperType(stringExtra13, 0);
                    SensorWallpaperPrams sensorWallpaperPrams2 = new SensorWallpaperPrams(stringExtra, str4, numValueOf, num4, num2, num3, f, stringExtra3, str2, str3);
                    sensorWallpaperPrams2.setCurrentSmallSensorWallpaper(bitmapDecodeFile$default);
                    if (bitmapResizeImageWallpaper != null) {
                        numValueOf5 = Integer.valueOf(bitmapResizeImageWallpaper.getWidth());
                    } else {
                        numValueOf5 = null;
                    }
                    if (bitmapResizeImageWallpaper != null) {
                        numValueOf6 = Integer.valueOf(bitmapResizeImageWallpaper.getHeight());
                    } else {
                        numValueOf6 = null;
                    }
                    Log.d("WallpaperEditor", "bitmap width:" + numValueOf5 + " height:" + numValueOf6);
                    if (i != 0) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (bool2 != null) {
                        zBooleanValue = bool2.booleanValue();
                    } else {
                        zBooleanValue = false;
                    }
                    if (bool != null) {
                        zBooleanValue2 = bool.booleanValue();
                    } else {
                        zBooleanValue2 = false;
                    }
                    return new WallpaperTypeInfo(bitmapResizeImageWallpaper, stringExtra13, strObtainFile, stringExtra14, i, z2, sensorWallpaperPrams2, false, zIsSupportBlurWallpaperType2, boolValueOf, depthBitmap, num, str6, zBooleanValue, zBooleanValue2, null, stringExtra6, null, boolValueOf4, str5, 163840, null);
                    pictureDegree = wallpaperEditor.readPictureDegree(strObtainFile);
                    Log.i("WallpaperEditor", "degree:==" + pictureDegree);
                    if (pictureDegree != 0) {
                        bitmapResizeImageWallpaper2 = wallpaperEditor.resizeImageWallpaper(strObtainFile);
                        if (bitmapResizeImageWallpaper2 != null) {
                            bitmapResizeImageWallpaper = wallpaperEditor.revolveImageView(pictureDegree, bitmapResizeImageWallpaper2);
                        } else {
                            num = num;
                            bitmapResizeImageWallpaper = null;
                        }
                        str6 = null;
                        bitmapDecodeFile$default = null;
                        magicType = MagicType.INSTANCE;
                        data = wallpaperEditor.getEditCallback().getData(MagicType.MAGIC_TYPE, new Object[0]);
                        if (data instanceof Integer) {
                            num5 = (Integer) data;
                        } else {
                            num5 = null;
                        }
                        if (magicType.isFrontBack(num5)) {
                            data2 = wallpaperEditor.getEditCallback().getData("support_font_back", stringExtra13);
                            Intrinsics.checkNotNull(data2, "null cannot be cast to non-null type kotlin.Boolean");
                            if (((Boolean) data2).booleanValue()) {
                                depthBitmap = GLRenderHelper.INSTANCE.getDepthBitmap(bitmapResizeImageWallpaper);
                            } else {
                                depthBitmap = null;
                            }
                        } else {
                            depthBitmap = null;
                        }
                        boolean zIsSupportBlurWallpaperType3 = Wallpaper.Companion.isSupportBlurWallpaperType(stringExtra13, 0);
                        SensorWallpaperPrams sensorWallpaperPrams3 = new SensorWallpaperPrams(stringExtra, str4, numValueOf, num4, num2, num3, f, stringExtra3, str2, str3);
                        sensorWallpaperPrams3.setCurrentSmallSensorWallpaper(bitmapDecodeFile$default);
                        if (bitmapResizeImageWallpaper != null) {
                            numValueOf5 = Integer.valueOf(bitmapResizeImageWallpaper.getWidth());
                        } else {
                            numValueOf5 = null;
                        }
                        if (bitmapResizeImageWallpaper != null) {
                            numValueOf6 = Integer.valueOf(bitmapResizeImageWallpaper.getHeight());
                        } else {
                            numValueOf6 = null;
                        }
                        Log.d("WallpaperEditor", "bitmap width:" + numValueOf5 + " height:" + numValueOf6);
                        if (i != 0) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (bool2 != null) {
                            zBooleanValue = bool2.booleanValue();
                        } else {
                            zBooleanValue = false;
                        }
                        if (bool != null) {
                            zBooleanValue2 = bool.booleanValue();
                        } else {
                            zBooleanValue2 = false;
                        }
                        return new WallpaperTypeInfo(bitmapResizeImageWallpaper, stringExtra13, strObtainFile, stringExtra14, i, z2, sensorWallpaperPrams3, false, zIsSupportBlurWallpaperType3, boolValueOf, depthBitmap, num, str6, zBooleanValue, zBooleanValue2, null, stringExtra6, null, boolValueOf4, str5, 163840, null);
                    }
                    bitmapResizeImageWallpaper = wallpaperEditor.resizeImageWallpaper(strObtainFile);
                    str6 = null;
                    bitmapDecodeFile$default = null;
                    magicType = MagicType.INSTANCE;
                    data = wallpaperEditor.getEditCallback().getData(MagicType.MAGIC_TYPE, new Object[0]);
                    if (data instanceof Integer) {
                        num5 = (Integer) data;
                    } else {
                        num5 = null;
                    }
                    if (magicType.isFrontBack(num5)) {
                        data2 = wallpaperEditor.getEditCallback().getData("support_font_back", stringExtra13);
                        Intrinsics.checkNotNull(data2, "null cannot be cast to non-null type kotlin.Boolean");
                        if (((Boolean) data2).booleanValue()) {
                            depthBitmap = GLRenderHelper.INSTANCE.getDepthBitmap(bitmapResizeImageWallpaper);
                        } else {
                            depthBitmap = null;
                        }
                    } else {
                        depthBitmap = null;
                    }
                    boolean zIsSupportBlurWallpaperType4 = Wallpaper.Companion.isSupportBlurWallpaperType(stringExtra13, 0);
                    SensorWallpaperPrams sensorWallpaperPrams4 = new SensorWallpaperPrams(stringExtra, str4, numValueOf, num4, num2, num3, f, stringExtra3, str2, str3);
                    sensorWallpaperPrams4.setCurrentSmallSensorWallpaper(bitmapDecodeFile$default);
                    if (bitmapResizeImageWallpaper != null) {
                        numValueOf5 = Integer.valueOf(bitmapResizeImageWallpaper.getWidth());
                    } else {
                        numValueOf5 = null;
                    }
                    if (bitmapResizeImageWallpaper != null) {
                        numValueOf6 = Integer.valueOf(bitmapResizeImageWallpaper.getHeight());
                    } else {
                        numValueOf6 = null;
                    }
                    Log.d("WallpaperEditor", "bitmap width:" + numValueOf5 + " height:" + numValueOf6);
                    if (i != 0) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (bool2 != null) {
                        zBooleanValue = bool2.booleanValue();
                    } else {
                        zBooleanValue = false;
                    }
                    if (bool != null) {
                        zBooleanValue2 = bool.booleanValue();
                    } else {
                        zBooleanValue2 = false;
                    }
                    return new WallpaperTypeInfo(bitmapResizeImageWallpaper, stringExtra13, strObtainFile, stringExtra14, i, z2, sensorWallpaperPrams4, false, zIsSupportBlurWallpaperType4, boolValueOf, depthBitmap, num, str6, zBooleanValue, zBooleanValue2, null, stringExtra6, null, boolValueOf4, str5, 163840, null);
                }
                bool = boolValueOf2;
                bool2 = boolValueOf3;
                str5 = stringExtra7;
                pictureDegree = wallpaperEditor.readPictureDegree(strObtainFile);
                Log.i("WallpaperEditor", "degree:==" + pictureDegree);
                if (pictureDegree != 0) {
                    bitmapResizeImageWallpaper2 = wallpaperEditor.resizeImageWallpaper(strObtainFile);
                    if (bitmapResizeImageWallpaper2 != null) {
                        bitmapResizeImageWallpaper = wallpaperEditor.revolveImageView(pictureDegree, bitmapResizeImageWallpaper2);
                    } else {
                        num = num;
                        bitmapResizeImageWallpaper = null;
                    }
                    str6 = null;
                    bitmapDecodeFile$default = null;
                    magicType = MagicType.INSTANCE;
                    data = wallpaperEditor.getEditCallback().getData(MagicType.MAGIC_TYPE, new Object[0]);
                    if (data instanceof Integer) {
                        num5 = (Integer) data;
                    } else {
                        num5 = null;
                    }
                    if (magicType.isFrontBack(num5)) {
                        data2 = wallpaperEditor.getEditCallback().getData("support_font_back", stringExtra13);
                        Intrinsics.checkNotNull(data2, "null cannot be cast to non-null type kotlin.Boolean");
                        if (((Boolean) data2).booleanValue()) {
                            depthBitmap = GLRenderHelper.INSTANCE.getDepthBitmap(bitmapResizeImageWallpaper);
                        } else {
                            depthBitmap = null;
                        }
                    } else {
                        depthBitmap = null;
                    }
                    boolean zIsSupportBlurWallpaperType5 = Wallpaper.Companion.isSupportBlurWallpaperType(stringExtra13, 0);
                    SensorWallpaperPrams sensorWallpaperPrams5 = new SensorWallpaperPrams(stringExtra, str4, numValueOf, num4, num2, num3, f, stringExtra3, str2, str3);
                    sensorWallpaperPrams5.setCurrentSmallSensorWallpaper(bitmapDecodeFile$default);
                    if (bitmapResizeImageWallpaper != null) {
                        numValueOf5 = Integer.valueOf(bitmapResizeImageWallpaper.getWidth());
                    } else {
                        numValueOf5 = null;
                    }
                    if (bitmapResizeImageWallpaper != null) {
                        numValueOf6 = Integer.valueOf(bitmapResizeImageWallpaper.getHeight());
                    } else {
                        numValueOf6 = null;
                    }
                    Log.d("WallpaperEditor", "bitmap width:" + numValueOf5 + " height:" + numValueOf6);
                    if (i != 0) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (bool2 != null) {
                        zBooleanValue = bool2.booleanValue();
                    } else {
                        zBooleanValue = false;
                    }
                    if (bool != null) {
                        zBooleanValue2 = bool.booleanValue();
                    } else {
                        zBooleanValue2 = false;
                    }
                    return new WallpaperTypeInfo(bitmapResizeImageWallpaper, stringExtra13, strObtainFile, stringExtra14, i, z2, sensorWallpaperPrams5, false, zIsSupportBlurWallpaperType5, boolValueOf, depthBitmap, num, str6, zBooleanValue, zBooleanValue2, null, stringExtra6, null, boolValueOf4, str5, 163840, null);
                }
                bitmapResizeImageWallpaper = wallpaperEditor.resizeImageWallpaper(strObtainFile);
                str6 = null;
                bitmapDecodeFile$default = null;
                magicType = MagicType.INSTANCE;
                data = wallpaperEditor.getEditCallback().getData(MagicType.MAGIC_TYPE, new Object[0]);
                if (data instanceof Integer) {
                    num5 = (Integer) data;
                } else {
                    num5 = null;
                }
                if (magicType.isFrontBack(num5)) {
                    data2 = wallpaperEditor.getEditCallback().getData("support_font_back", stringExtra13);
                    Intrinsics.checkNotNull(data2, "null cannot be cast to non-null type kotlin.Boolean");
                    if (((Boolean) data2).booleanValue()) {
                        depthBitmap = GLRenderHelper.INSTANCE.getDepthBitmap(bitmapResizeImageWallpaper);
                    } else {
                        depthBitmap = null;
                    }
                } else {
                    depthBitmap = null;
                }
                boolean zIsSupportBlurWallpaperType6 = Wallpaper.Companion.isSupportBlurWallpaperType(stringExtra13, 0);
                SensorWallpaperPrams sensorWallpaperPrams6 = new SensorWallpaperPrams(stringExtra, str4, numValueOf, num4, num2, num3, f, stringExtra3, str2, str3);
                sensorWallpaperPrams6.setCurrentSmallSensorWallpaper(bitmapDecodeFile$default);
                if (bitmapResizeImageWallpaper != null) {
                    numValueOf5 = Integer.valueOf(bitmapResizeImageWallpaper.getWidth());
                } else {
                    numValueOf5 = null;
                }
                if (bitmapResizeImageWallpaper != null) {
                    numValueOf6 = Integer.valueOf(bitmapResizeImageWallpaper.getHeight());
                } else {
                    numValueOf6 = null;
                }
                Log.d("WallpaperEditor", "bitmap width:" + numValueOf5 + " height:" + numValueOf6);
                if (i != 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (bool2 != null) {
                    zBooleanValue = bool2.booleanValue();
                } else {
                    zBooleanValue = false;
                }
                if (bool != null) {
                    zBooleanValue2 = bool.booleanValue();
                } else {
                    zBooleanValue2 = false;
                }
                return new WallpaperTypeInfo(bitmapResizeImageWallpaper, stringExtra13, strObtainFile, stringExtra14, i, z2, sensorWallpaperPrams6, false, zIsSupportBlurWallpaperType6, boolValueOf, depthBitmap, num, str6, zBooleanValue, zBooleanValue2, null, stringExtra6, null, boolValueOf4, str5, 163840, null);
            }
            f = fValueOf;
            obj3 = obj2;
            num2 = numValueOf3;
            num3 = numValueOf4;
            num4 = numValueOf2;
            str = stringExtra5;
            str2 = stringExtra4;
            str3 = str;
            str4 = stringExtra2;
            if (intent != null) {
                z = false;
                boolValueOf = Boolean.valueOf(intent.getBooleanExtra("isSupportLoop", false));
            } else {
                z = false;
                boolValueOf = null;
            }
            if (intent != null) {
                boolValueOf2 = Boolean.valueOf(intent.getBooleanExtra("isDirectGenerate", z));
            } else {
                boolValueOf2 = null;
            }
            if (intent != null) {
                boolValueOf3 = Boolean.valueOf(intent.getBooleanExtra("isAiGenerated", z));
            } else {
                boolValueOf3 = null;
            }
            if (intent != null) {
                stringExtra6 = intent.getStringExtra("wallpaper_from");
            } else {
                stringExtra6 = null;
            }
            if (intent != null) {
                boolValueOf4 = Boolean.valueOf(intent.getBooleanExtra("isSupportFullScreenAod", z));
            } else {
                boolValueOf4 = null;
            }
            Rect rectCurrentDisplaySize2 = DeviceUtil.INSTANCE.currentDisplaySize(context);
            iWidth = rectCurrentDisplaySize2.width();
            iHeight = rectCurrentDisplaySize2.height();
            if (intent != null) {
                stringExtra7 = intent.getStringExtra("imagePath");
            } else {
                stringExtra7 = null;
            }
            if (stringExtra13 != null) {
                iHashCode = stringExtra13.hashCode();
                bool = boolValueOf2;
                bool2 = boolValueOf3;
                if (iHashCode == -905948230) {
                    str5 = stringExtra7;
                    if (stringExtra13.equals(obj3)) {
                        BitmapCreator bitmapCreator2 = BitmapCreator.INSTANCE;
                        bitmapDecodeFile$default = BitmapCreator.decodeFile$default(bitmapCreator2, str4, null, 2, null);
                        bitmapResizeImageWallpaper = BitmapCreator.decodeFile$default(bitmapCreator2, stringExtra, null, 2, null);
                        num = num;
                        strObtainFile = strObtainFile;
                        str6 = null;
                    }
                    magicType = MagicType.INSTANCE;
                    data = wallpaperEditor.getEditCallback().getData(MagicType.MAGIC_TYPE, new Object[0]);
                    if (data instanceof Integer) {
                        num5 = (Integer) data;
                    } else {
                        num5 = null;
                    }
                    if (magicType.isFrontBack(num5)) {
                        data2 = wallpaperEditor.getEditCallback().getData("support_font_back", stringExtra13);
                        Intrinsics.checkNotNull(data2, "null cannot be cast to non-null type kotlin.Boolean");
                        if (((Boolean) data2).booleanValue()) {
                            depthBitmap = GLRenderHelper.INSTANCE.getDepthBitmap(bitmapResizeImageWallpaper);
                        } else {
                            depthBitmap = null;
                        }
                    } else {
                        depthBitmap = null;
                    }
                    boolean zIsSupportBlurWallpaperType7 = Wallpaper.Companion.isSupportBlurWallpaperType(stringExtra13, 0);
                    SensorWallpaperPrams sensorWallpaperPrams7 = new SensorWallpaperPrams(stringExtra, str4, numValueOf, num4, num2, num3, f, stringExtra3, str2, str3);
                    sensorWallpaperPrams7.setCurrentSmallSensorWallpaper(bitmapDecodeFile$default);
                    if (bitmapResizeImageWallpaper != null) {
                        numValueOf5 = Integer.valueOf(bitmapResizeImageWallpaper.getWidth());
                    } else {
                        numValueOf5 = null;
                    }
                    if (bitmapResizeImageWallpaper != null) {
                        numValueOf6 = Integer.valueOf(bitmapResizeImageWallpaper.getHeight());
                    } else {
                        numValueOf6 = null;
                    }
                    Log.d("WallpaperEditor", "bitmap width:" + numValueOf5 + " height:" + numValueOf6);
                    if (i != 0) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (bool2 != null) {
                        zBooleanValue = bool2.booleanValue();
                    } else {
                        zBooleanValue = false;
                    }
                    if (bool != null) {
                        zBooleanValue2 = bool.booleanValue();
                    } else {
                        zBooleanValue2 = false;
                    }
                    return new WallpaperTypeInfo(bitmapResizeImageWallpaper, stringExtra13, strObtainFile, stringExtra14, i, z2, sensorWallpaperPrams7, false, zIsSupportBlurWallpaperType7, boolValueOf, depthBitmap, num, str6, zBooleanValue, zBooleanValue2, null, stringExtra6, null, boolValueOf4, str5, 163840, null);
                }
                if (iHashCode != 112202875) {
                    if (iHashCode == 1024344262) {
                        if (intent != null) {
                            numValueOf7 = Integer.valueOf(intent.getIntExtra("livephoto_video_check_result_code", 1000));
                        } else {
                            numValueOf7 = null;
                        }
                        if (intent != null) {
                            uri = (Uri) intent.getParcelableExtra("videoUri");
                        } else {
                            uri = null;
                        }
                        strObtainFile2 = wallpaperEditor.obtainFile(context, uri);
                        Log.d("WallpaperEditor", "live photo: livePhotoCheckCode=" + numValueOf7 + " imgPath=" + strObtainFile + " videoUri=" + uri + " videoPath=" + strObtainFile2);
                        if (numValueOf7 == null) {
                            num = 3;
                            strObtainFile2 = strObtainFile;
                            str8 = "image";
                            str7 = null;
                        } else {
                            num = 2;
                            str7 = strObtainFile;
                        }
                        pictureDegree2 = wallpaperEditor.readPictureDegree(strObtainFile);
                        Log.i("WallpaperEditor", "degree:==" + pictureDegree2);
                        if (pictureDegree2 != 0) {
                            bitmapResizeImageWallpaper4 = wallpaperEditor.resizeImageWallpaper(strObtainFile);
                            if (bitmapResizeImageWallpaper4 != null) {
                                bitmapResizeImageWallpaper3 = wallpaperEditor.revolveImageView(pictureDegree2, bitmapResizeImageWallpaper4);
                            } else {
                                str6 = str7;
                                strObtainFile = strObtainFile2;
                                str5 = stringExtra7;
                                stringExtra13 = str8;
                                bitmapResizeImageWallpaper = null;
                            }
                        } else {
                            bitmapResizeImageWallpaper3 = wallpaperEditor.resizeImageWallpaper(strObtainFile);
                        }
                        Bitmap bitmap2 = bitmapResizeImageWallpaper3;
                        str6 = str7;
                        bitmapResizeImageWallpaper = bitmap2;
                        strObtainFile = strObtainFile2;
                        str5 = stringExtra7;
                        stringExtra13 = str8;
                    }
                } else if (!stringExtra13.equals(str8)) {
                    str5 = stringExtra7;
                } else {
                    if (!VideoCheckUtils.Companion.isVideoCodecSupported(strObtainFile)) {
                        Log.e("WallpaperEditor", "handleWallpaperTypeInfo: unsupported video codec, path=" + strObtainFile);
                        return null;
                    }
                    Log.d("WallpaperEditor", "handleWallpaperTypeInfo: imagePath is " + stringExtra7 + ",path is " + strObtainFile);
                    if (stringExtra7 != null) {
                    }
                    str5 = stringExtra7;
                    file = new File(strObtainFile);
                    if (file.exists()) {
                        bitmapCreateVideoLastFrameThumbnail = BitmapUtil.INSTANCE.createVideoLastFrameThumbnail(file, new Size(iWidth, iHeight));
                        if (bitmapCreateVideoLastFrameThumbnail != null) {
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        Log.d("WallpaperEditor", "create video thumbnail bitmap=" + z3 + " screenWidth=" + iWidth + " screenHeight=" + iHeight);
                        bitmapResizeImageWallpaper = bitmapCreateVideoLastFrameThumbnail;
                        str6 = null;
                    } else {
                        if (intent != null) {
                            data3 = intent.getData();
                        } else {
                            data3 = null;
                        }
                        Log.e("WallpaperEditor", "video file not exit path=" + strObtainFile + " uri=" + data3);
                        num = num;
                        bitmapResizeImageWallpaper = null;
                        str6 = null;
                    }
                }
                bitmapDecodeFile$default = null;
                magicType = MagicType.INSTANCE;
                data = wallpaperEditor.getEditCallback().getData(MagicType.MAGIC_TYPE, new Object[0]);
                if (data instanceof Integer) {
                    num5 = (Integer) data;
                } else {
                    num5 = null;
                }
                if (magicType.isFrontBack(num5)) {
                    data2 = wallpaperEditor.getEditCallback().getData("support_font_back", stringExtra13);
                    Intrinsics.checkNotNull(data2, "null cannot be cast to non-null type kotlin.Boolean");
                    if (((Boolean) data2).booleanValue()) {
                        depthBitmap = GLRenderHelper.INSTANCE.getDepthBitmap(bitmapResizeImageWallpaper);
                    } else {
                        depthBitmap = null;
                    }
                } else {
                    depthBitmap = null;
                }
                boolean zIsSupportBlurWallpaperType8 = Wallpaper.Companion.isSupportBlurWallpaperType(stringExtra13, 0);
                SensorWallpaperPrams sensorWallpaperPrams8 = new SensorWallpaperPrams(stringExtra, str4, numValueOf, num4, num2, num3, f, stringExtra3, str2, str3);
                sensorWallpaperPrams8.setCurrentSmallSensorWallpaper(bitmapDecodeFile$default);
                if (bitmapResizeImageWallpaper != null) {
                    numValueOf5 = Integer.valueOf(bitmapResizeImageWallpaper.getWidth());
                } else {
                    numValueOf5 = null;
                }
                if (bitmapResizeImageWallpaper != null) {
                    numValueOf6 = Integer.valueOf(bitmapResizeImageWallpaper.getHeight());
                } else {
                    numValueOf6 = null;
                }
                Log.d("WallpaperEditor", "bitmap width:" + numValueOf5 + " height:" + numValueOf6);
                if (i != 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (bool2 != null) {
                    zBooleanValue = bool2.booleanValue();
                } else {
                    zBooleanValue = false;
                }
                if (bool != null) {
                    zBooleanValue2 = bool.booleanValue();
                } else {
                    zBooleanValue2 = false;
                }
                return new WallpaperTypeInfo(bitmapResizeImageWallpaper, stringExtra13, strObtainFile, stringExtra14, i, z2, sensorWallpaperPrams8, false, zIsSupportBlurWallpaperType8, boolValueOf, depthBitmap, num, str6, zBooleanValue, zBooleanValue2, null, stringExtra6, null, boolValueOf4, str5, 163840, null);
                pictureDegree = wallpaperEditor.readPictureDegree(strObtainFile);
                Log.i("WallpaperEditor", "degree:==" + pictureDegree);
                if (pictureDegree != 0) {
                    bitmapResizeImageWallpaper2 = wallpaperEditor.resizeImageWallpaper(strObtainFile);
                    if (bitmapResizeImageWallpaper2 != null) {
                        bitmapResizeImageWallpaper = wallpaperEditor.revolveImageView(pictureDegree, bitmapResizeImageWallpaper2);
                    } else {
                        num = num;
                        bitmapResizeImageWallpaper = null;
                    }
                    str6 = null;
                    bitmapDecodeFile$default = null;
                    magicType = MagicType.INSTANCE;
                    data = wallpaperEditor.getEditCallback().getData(MagicType.MAGIC_TYPE, new Object[0]);
                    if (data instanceof Integer) {
                        num5 = (Integer) data;
                    } else {
                        num5 = null;
                    }
                    if (magicType.isFrontBack(num5)) {
                        data2 = wallpaperEditor.getEditCallback().getData("support_font_back", stringExtra13);
                        Intrinsics.checkNotNull(data2, "null cannot be cast to non-null type kotlin.Boolean");
                        if (((Boolean) data2).booleanValue()) {
                            depthBitmap = GLRenderHelper.INSTANCE.getDepthBitmap(bitmapResizeImageWallpaper);
                        } else {
                            depthBitmap = null;
                        }
                    } else {
                        depthBitmap = null;
                    }
                    boolean zIsSupportBlurWallpaperType9 = Wallpaper.Companion.isSupportBlurWallpaperType(stringExtra13, 0);
                    SensorWallpaperPrams sensorWallpaperPrams9 = new SensorWallpaperPrams(stringExtra, str4, numValueOf, num4, num2, num3, f, stringExtra3, str2, str3);
                    sensorWallpaperPrams9.setCurrentSmallSensorWallpaper(bitmapDecodeFile$default);
                    if (bitmapResizeImageWallpaper != null) {
                        numValueOf5 = Integer.valueOf(bitmapResizeImageWallpaper.getWidth());
                    } else {
                        numValueOf5 = null;
                    }
                    if (bitmapResizeImageWallpaper != null) {
                        numValueOf6 = Integer.valueOf(bitmapResizeImageWallpaper.getHeight());
                    } else {
                        numValueOf6 = null;
                    }
                    Log.d("WallpaperEditor", "bitmap width:" + numValueOf5 + " height:" + numValueOf6);
                    if (i != 0) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (bool2 != null) {
                        zBooleanValue = bool2.booleanValue();
                    } else {
                        zBooleanValue = false;
                    }
                    if (bool != null) {
                        zBooleanValue2 = bool.booleanValue();
                    } else {
                        zBooleanValue2 = false;
                    }
                    return new WallpaperTypeInfo(bitmapResizeImageWallpaper, stringExtra13, strObtainFile, stringExtra14, i, z2, sensorWallpaperPrams9, false, zIsSupportBlurWallpaperType9, boolValueOf, depthBitmap, num, str6, zBooleanValue, zBooleanValue2, null, stringExtra6, null, boolValueOf4, str5, 163840, null);
                }
                bitmapResizeImageWallpaper = wallpaperEditor.resizeImageWallpaper(strObtainFile);
                str6 = null;
                bitmapDecodeFile$default = null;
                magicType = MagicType.INSTANCE;
                data = wallpaperEditor.getEditCallback().getData(MagicType.MAGIC_TYPE, new Object[0]);
                if (data instanceof Integer) {
                    num5 = (Integer) data;
                } else {
                    num5 = null;
                }
                if (magicType.isFrontBack(num5)) {
                    data2 = wallpaperEditor.getEditCallback().getData("support_font_back", stringExtra13);
                    Intrinsics.checkNotNull(data2, "null cannot be cast to non-null type kotlin.Boolean");
                    if (((Boolean) data2).booleanValue()) {
                        depthBitmap = GLRenderHelper.INSTANCE.getDepthBitmap(bitmapResizeImageWallpaper);
                    } else {
                        depthBitmap = null;
                    }
                } else {
                    depthBitmap = null;
                }
                boolean zIsSupportBlurWallpaperType10 = Wallpaper.Companion.isSupportBlurWallpaperType(stringExtra13, 0);
                SensorWallpaperPrams sensorWallpaperPrams10 = new SensorWallpaperPrams(stringExtra, str4, numValueOf, num4, num2, num3, f, stringExtra3, str2, str3);
                sensorWallpaperPrams10.setCurrentSmallSensorWallpaper(bitmapDecodeFile$default);
                if (bitmapResizeImageWallpaper != null) {
                    numValueOf5 = Integer.valueOf(bitmapResizeImageWallpaper.getWidth());
                } else {
                    numValueOf5 = null;
                }
                if (bitmapResizeImageWallpaper != null) {
                    numValueOf6 = Integer.valueOf(bitmapResizeImageWallpaper.getHeight());
                } else {
                    numValueOf6 = null;
                }
                Log.d("WallpaperEditor", "bitmap width:" + numValueOf5 + " height:" + numValueOf6);
                if (i != 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (bool2 != null) {
                    zBooleanValue = bool2.booleanValue();
                } else {
                    zBooleanValue = false;
                }
                if (bool != null) {
                    zBooleanValue2 = bool.booleanValue();
                } else {
                    zBooleanValue2 = false;
                }
                return new WallpaperTypeInfo(bitmapResizeImageWallpaper, stringExtra13, strObtainFile, stringExtra14, i, z2, sensorWallpaperPrams10, false, zIsSupportBlurWallpaperType10, boolValueOf, depthBitmap, num, str6, zBooleanValue, zBooleanValue2, null, stringExtra6, null, boolValueOf4, str5, 163840, null);
            }
            bool = boolValueOf2;
            bool2 = boolValueOf3;
            str5 = stringExtra7;
            pictureDegree = wallpaperEditor.readPictureDegree(strObtainFile);
            Log.i("WallpaperEditor", "degree:==" + pictureDegree);
            if (pictureDegree != 0) {
                bitmapResizeImageWallpaper2 = wallpaperEditor.resizeImageWallpaper(strObtainFile);
                if (bitmapResizeImageWallpaper2 != null) {
                    bitmapResizeImageWallpaper = wallpaperEditor.revolveImageView(pictureDegree, bitmapResizeImageWallpaper2);
                } else {
                    num = num;
                    bitmapResizeImageWallpaper = null;
                }
                str6 = null;
                bitmapDecodeFile$default = null;
                magicType = MagicType.INSTANCE;
                data = wallpaperEditor.getEditCallback().getData(MagicType.MAGIC_TYPE, new Object[0]);
                if (data instanceof Integer) {
                    num5 = (Integer) data;
                } else {
                    num5 = null;
                }
                if (magicType.isFrontBack(num5)) {
                    data2 = wallpaperEditor.getEditCallback().getData("support_font_back", stringExtra13);
                    Intrinsics.checkNotNull(data2, "null cannot be cast to non-null type kotlin.Boolean");
                    if (((Boolean) data2).booleanValue()) {
                        depthBitmap = GLRenderHelper.INSTANCE.getDepthBitmap(bitmapResizeImageWallpaper);
                    } else {
                        depthBitmap = null;
                    }
                } else {
                    depthBitmap = null;
                }
                boolean zIsSupportBlurWallpaperType11 = Wallpaper.Companion.isSupportBlurWallpaperType(stringExtra13, 0);
                SensorWallpaperPrams sensorWallpaperPrams11 = new SensorWallpaperPrams(stringExtra, str4, numValueOf, num4, num2, num3, f, stringExtra3, str2, str3);
                sensorWallpaperPrams11.setCurrentSmallSensorWallpaper(bitmapDecodeFile$default);
                if (bitmapResizeImageWallpaper != null) {
                    numValueOf5 = Integer.valueOf(bitmapResizeImageWallpaper.getWidth());
                } else {
                    numValueOf5 = null;
                }
                if (bitmapResizeImageWallpaper != null) {
                    numValueOf6 = Integer.valueOf(bitmapResizeImageWallpaper.getHeight());
                } else {
                    numValueOf6 = null;
                }
                Log.d("WallpaperEditor", "bitmap width:" + numValueOf5 + " height:" + numValueOf6);
                if (i != 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (bool2 != null) {
                    zBooleanValue = bool2.booleanValue();
                } else {
                    zBooleanValue = false;
                }
                if (bool != null) {
                    zBooleanValue2 = bool.booleanValue();
                } else {
                    zBooleanValue2 = false;
                }
                return new WallpaperTypeInfo(bitmapResizeImageWallpaper, stringExtra13, strObtainFile, stringExtra14, i, z2, sensorWallpaperPrams11, false, zIsSupportBlurWallpaperType11, boolValueOf, depthBitmap, num, str6, zBooleanValue, zBooleanValue2, null, stringExtra6, null, boolValueOf4, str5, 163840, null);
            }
            bitmapResizeImageWallpaper = wallpaperEditor.resizeImageWallpaper(strObtainFile);
            str6 = null;
            bitmapDecodeFile$default = null;
            magicType = MagicType.INSTANCE;
            data = wallpaperEditor.getEditCallback().getData(MagicType.MAGIC_TYPE, new Object[0]);
            if (data instanceof Integer) {
                num5 = (Integer) data;
            } else {
                num5 = null;
            }
            if (magicType.isFrontBack(num5)) {
                data2 = wallpaperEditor.getEditCallback().getData("support_font_back", stringExtra13);
                Intrinsics.checkNotNull(data2, "null cannot be cast to non-null type kotlin.Boolean");
                if (((Boolean) data2).booleanValue()) {
                    depthBitmap = GLRenderHelper.INSTANCE.getDepthBitmap(bitmapResizeImageWallpaper);
                } else {
                    depthBitmap = null;
                }
            } else {
                depthBitmap = null;
            }
            boolean zIsSupportBlurWallpaperType12 = Wallpaper.Companion.isSupportBlurWallpaperType(stringExtra13, 0);
            SensorWallpaperPrams sensorWallpaperPrams12 = new SensorWallpaperPrams(stringExtra, str4, numValueOf, num4, num2, num3, f, stringExtra3, str2, str3);
            sensorWallpaperPrams12.setCurrentSmallSensorWallpaper(bitmapDecodeFile$default);
            if (bitmapResizeImageWallpaper != null) {
                numValueOf5 = Integer.valueOf(bitmapResizeImageWallpaper.getWidth());
            } else {
                numValueOf5 = null;
            }
            if (bitmapResizeImageWallpaper != null) {
                numValueOf6 = Integer.valueOf(bitmapResizeImageWallpaper.getHeight());
            } else {
                numValueOf6 = null;
            }
            Log.d("WallpaperEditor", "bitmap width:" + numValueOf5 + " height:" + numValueOf6);
            if (i != 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (bool2 != null) {
                zBooleanValue = bool2.booleanValue();
            } else {
                zBooleanValue = false;
            }
            if (bool != null) {
                zBooleanValue2 = bool.booleanValue();
            } else {
                zBooleanValue2 = false;
            }
            return new WallpaperTypeInfo(bitmapResizeImageWallpaper, stringExtra13, strObtainFile, stringExtra14, i, z2, sensorWallpaperPrams12, false, zIsSupportBlurWallpaperType12, boolValueOf, depthBitmap, num, str6, zBooleanValue, zBooleanValue2, null, stringExtra6, null, boolValueOf4, str5, 163840, null);
        }
        obj = "livephoto";
        i = (intExtra == 1 && Wallpaper.Companion.isSystemWallpaper(strObtainFile)) ? 0 : intExtra;
        if (intent != null) {
            stringExtra = intent.getStringExtra("sensorThumbnail");
        } else {
            stringExtra = null;
        }
        if (intent != null) {
            stringExtra2 = intent.getStringExtra("sensorSmallThumbnail");
        } else {
            stringExtra2 = null;
        }
        if (intent != null) {
            i2 = -1;
            numValueOf = Integer.valueOf(intent.getIntExtra("sensor_all_frames", -1));
        } else {
            i2 = -1;
            numValueOf = null;
        }
        if (intent != null) {
            numValueOf2 = Integer.valueOf(intent.getIntExtra("sensor_small_frames", i2));
        } else {
            numValueOf2 = null;
        }
        num = numValueOf10;
        if (intent != null) {
            numValueOf3 = Integer.valueOf(intent.getIntExtra("sensor_type_stiffness", 10));
        } else {
            numValueOf3 = null;
        }
        if (intent != null) {
            numValueOf4 = Integer.valueOf(intent.getIntExtra("sensor_anim_duration", 0));
        } else {
            numValueOf4 = null;
        }
        if (intent != null) {
            fValueOf = Float.valueOf(intent.getFloatExtra("sensor_anim_factor", 0.0f));
        } else {
            fValueOf = null;
        }
        if (intent != null) {
            stringExtra3 = intent.getStringExtra("sensor_first_anim_type");
        } else {
            stringExtra3 = null;
        }
        Object obj5 = obj;
        if (intent != null) {
            if (intent != null) {
                stringExtra4 = intent.getStringExtra("sensor_outer_video_home");
            } else {
                stringExtra4 = null;
            }
            if (stringExtra4 == null) {
                if (intent != null) {
                    stringExtra4 = intent.getStringExtra("sensor_outer_video_lock");
                } else {
                    stringExtra4 = null;
                }
            }
        } else {
            if (intent != null) {
                stringExtra4 = intent.getStringExtra("sensor_outer_video_home");
            } else {
                stringExtra4 = null;
            }
            if (stringExtra4 == null) {
                if (intent != null) {
                    stringExtra4 = intent.getStringExtra("sensor_outer_video_lock");
                } else {
                    stringExtra4 = null;
                }
            }
        }
        if (intent != null) {
            stringExtra5 = intent.getStringExtra("sensor_outer_video_preview");
            obj2 = "sensor";
        } else {
            obj2 = "sensor";
            stringExtra5 = null;
        }
        if (Intrinsics.areEqual(stringExtra13, obj2)) {
            obj3 = obj2;
            StringBuilder sb4 = new StringBuilder();
            String str12 = stringExtra4;
            sb4.append("sensor: allFrames=");
            sb4.append(numValueOf);
            sb4.append("， smallFrames=");
            sb4.append(numValueOf2);
            sb4.append("， stiffness=");
            sb4.append(numValueOf3);
            sb4.append("， duration=");
            sb4.append(numValueOf4);
            sb4.append("， factor=");
            sb4.append(fValueOf);
            sb4.append("， animType=");
            sb4.append(stringExtra3);
            Log.i("WallpaperEditor", sb4.toString());
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            if (intent != null) {
                f = fValueOf;
                num2 = numValueOf3;
            } else {
                f = fValueOf;
                num2 = numValueOf3;
            }
            Log.i("WallpaperEditor", "processWallpaperInfo sensor Intent extras: " + linkedHashMap2);
            String strFindCmfHint2 = wallpaperEditor.findCmfHint(intent, strObtainFile, stringExtra12);
            companion = WallpaperController.Companion;
            if (intent != null) {
                dataString = intent.getDataString();
            } else {
                dataString = null;
            }
            if (companion.shouldApplyOuterVideoWallpaper(stringExtra13, strObtainFile, null, stringExtra12, dataString, stringExtra, stringExtra2)) {
                z4 = true;
            } else {
                z4 = true;
            }
            boolean zIsQ19 = DeviceUtil.INSTANCE.isQ18();
            num3 = numValueOf4;
            StringBuilder sb5 = new StringBuilder();
            num4 = numValueOf2;
            sb5.append("processWallpaperInfo: isQ18=");
            sb5.append(zIsQ19);
            sb5.append(" shouldApplyOuterVideoWallpaper=");
            sb5.append(z4);
            sb5.append(" cmfHint=");
            sb5.append(strFindCmfHint2);
            sb5.append(" outerVideoPath=");
            stringExtra4 = str12;
            sb5.append(stringExtra4);
            sb5.append(" outerVideoPreViewPath=");
            str = stringExtra5;
            sb5.append(str);
            Log.d("WallpaperEditor", sb5.toString());
            if (!z4) {
            }
        } else {
            f = fValueOf;
            obj3 = obj2;
            num2 = numValueOf3;
            num3 = numValueOf4;
            num4 = numValueOf2;
            str = stringExtra5;
        }
        str2 = stringExtra4;
        str3 = str;
        str4 = stringExtra2;
        if (intent != null) {
            z = false;
            boolValueOf = Boolean.valueOf(intent.getBooleanExtra("isSupportLoop", false));
        } else {
            z = false;
            boolValueOf = null;
        }
        if (intent != null) {
            boolValueOf2 = Boolean.valueOf(intent.getBooleanExtra("isDirectGenerate", z));
        } else {
            boolValueOf2 = null;
        }
        if (intent != null) {
            boolValueOf3 = Boolean.valueOf(intent.getBooleanExtra("isAiGenerated", z));
        } else {
            boolValueOf3 = null;
        }
        if (intent != null) {
            stringExtra6 = intent.getStringExtra("wallpaper_from");
        } else {
            stringExtra6 = null;
        }
        if (intent != null) {
            boolValueOf4 = Boolean.valueOf(intent.getBooleanExtra("isSupportFullScreenAod", z));
        } else {
            boolValueOf4 = null;
        }
        Rect rectCurrentDisplaySize3 = DeviceUtil.INSTANCE.currentDisplaySize(context);
        iWidth = rectCurrentDisplaySize3.width();
        iHeight = rectCurrentDisplaySize3.height();
        if (intent != null) {
            stringExtra7 = intent.getStringExtra("imagePath");
        } else {
            stringExtra7 = null;
        }
        if (stringExtra13 != null) {
            iHashCode = stringExtra13.hashCode();
            bool = boolValueOf2;
            bool2 = boolValueOf3;
            if (iHashCode == -905948230) {
                str5 = stringExtra7;
                if (stringExtra13.equals(obj3)) {
                    BitmapCreator bitmapCreator3 = BitmapCreator.INSTANCE;
                    bitmapDecodeFile$default = BitmapCreator.decodeFile$default(bitmapCreator3, str4, null, 2, null);
                    bitmapResizeImageWallpaper = BitmapCreator.decodeFile$default(bitmapCreator3, stringExtra, null, 2, null);
                    num = num;
                    strObtainFile = strObtainFile;
                    str6 = null;
                }
                magicType = MagicType.INSTANCE;
                data = wallpaperEditor.getEditCallback().getData(MagicType.MAGIC_TYPE, new Object[0]);
                if (data instanceof Integer) {
                    num5 = (Integer) data;
                } else {
                    num5 = null;
                }
                if (magicType.isFrontBack(num5)) {
                    data2 = wallpaperEditor.getEditCallback().getData("support_font_back", stringExtra13);
                    Intrinsics.checkNotNull(data2, "null cannot be cast to non-null type kotlin.Boolean");
                    if (((Boolean) data2).booleanValue()) {
                        depthBitmap = GLRenderHelper.INSTANCE.getDepthBitmap(bitmapResizeImageWallpaper);
                    } else {
                        depthBitmap = null;
                    }
                } else {
                    depthBitmap = null;
                }
                boolean zIsSupportBlurWallpaperType13 = Wallpaper.Companion.isSupportBlurWallpaperType(stringExtra13, 0);
                SensorWallpaperPrams sensorWallpaperPrams13 = new SensorWallpaperPrams(stringExtra, str4, numValueOf, num4, num2, num3, f, stringExtra3, str2, str3);
                sensorWallpaperPrams13.setCurrentSmallSensorWallpaper(bitmapDecodeFile$default);
                if (bitmapResizeImageWallpaper != null) {
                    numValueOf5 = Integer.valueOf(bitmapResizeImageWallpaper.getWidth());
                } else {
                    numValueOf5 = null;
                }
                if (bitmapResizeImageWallpaper != null) {
                    numValueOf6 = Integer.valueOf(bitmapResizeImageWallpaper.getHeight());
                } else {
                    numValueOf6 = null;
                }
                Log.d("WallpaperEditor", "bitmap width:" + numValueOf5 + " height:" + numValueOf6);
                if (i != 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (bool2 != null) {
                    zBooleanValue = bool2.booleanValue();
                } else {
                    zBooleanValue = false;
                }
                if (bool != null) {
                    zBooleanValue2 = bool.booleanValue();
                } else {
                    zBooleanValue2 = false;
                }
                return new WallpaperTypeInfo(bitmapResizeImageWallpaper, stringExtra13, strObtainFile, stringExtra14, i, z2, sensorWallpaperPrams13, false, zIsSupportBlurWallpaperType13, boolValueOf, depthBitmap, num, str6, zBooleanValue, zBooleanValue2, null, stringExtra6, null, boolValueOf4, str5, 163840, null);
            }
            if (iHashCode != 112202875) {
                if (iHashCode == 1024344262) {
                    if (intent != null) {
                        numValueOf7 = Integer.valueOf(intent.getIntExtra("livephoto_video_check_result_code", 1000));
                    } else {
                        numValueOf7 = null;
                    }
                    if (intent != null) {
                        uri = (Uri) intent.getParcelableExtra("videoUri");
                    } else {
                        uri = null;
                    }
                    strObtainFile2 = wallpaperEditor.obtainFile(context, uri);
                    Log.d("WallpaperEditor", "live photo: livePhotoCheckCode=" + numValueOf7 + " imgPath=" + strObtainFile + " videoUri=" + uri + " videoPath=" + strObtainFile2);
                    if (numValueOf7 == null) {
                        num = 3;
                        strObtainFile2 = strObtainFile;
                        str8 = "image";
                        str7 = null;
                    } else {
                        num = 2;
                        str7 = strObtainFile;
                    }
                    pictureDegree2 = wallpaperEditor.readPictureDegree(strObtainFile);
                    Log.i("WallpaperEditor", "degree:==" + pictureDegree2);
                    if (pictureDegree2 != 0) {
                        bitmapResizeImageWallpaper4 = wallpaperEditor.resizeImageWallpaper(strObtainFile);
                        if (bitmapResizeImageWallpaper4 != null) {
                            bitmapResizeImageWallpaper3 = wallpaperEditor.revolveImageView(pictureDegree2, bitmapResizeImageWallpaper4);
                        } else {
                            str6 = str7;
                            strObtainFile = strObtainFile2;
                            str5 = stringExtra7;
                            stringExtra13 = str8;
                            bitmapResizeImageWallpaper = null;
                        }
                    } else {
                        bitmapResizeImageWallpaper3 = wallpaperEditor.resizeImageWallpaper(strObtainFile);
                    }
                    Bitmap bitmap3 = bitmapResizeImageWallpaper3;
                    str6 = str7;
                    bitmapResizeImageWallpaper = bitmap3;
                    strObtainFile = strObtainFile2;
                    str5 = stringExtra7;
                    stringExtra13 = str8;
                }
            } else if (!stringExtra13.equals(str8)) {
                str5 = stringExtra7;
            } else {
                if (!VideoCheckUtils.Companion.isVideoCodecSupported(strObtainFile)) {
                    Log.e("WallpaperEditor", "handleWallpaperTypeInfo: unsupported video codec, path=" + strObtainFile);
                    return null;
                }
                Log.d("WallpaperEditor", "handleWallpaperTypeInfo: imagePath is " + stringExtra7 + ",path is " + strObtainFile);
                if (stringExtra7 != null) {
                }
                str5 = stringExtra7;
                file = new File(strObtainFile);
                if (file.exists()) {
                    bitmapCreateVideoLastFrameThumbnail = BitmapUtil.INSTANCE.createVideoLastFrameThumbnail(file, new Size(iWidth, iHeight));
                    if (bitmapCreateVideoLastFrameThumbnail != null) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    Log.d("WallpaperEditor", "create video thumbnail bitmap=" + z3 + " screenWidth=" + iWidth + " screenHeight=" + iHeight);
                    bitmapResizeImageWallpaper = bitmapCreateVideoLastFrameThumbnail;
                    str6 = null;
                } else {
                    if (intent != null) {
                        data3 = intent.getData();
                    } else {
                        data3 = null;
                    }
                    Log.e("WallpaperEditor", "video file not exit path=" + strObtainFile + " uri=" + data3);
                    num = num;
                    bitmapResizeImageWallpaper = null;
                    str6 = null;
                }
            }
            bitmapDecodeFile$default = null;
            magicType = MagicType.INSTANCE;
            data = wallpaperEditor.getEditCallback().getData(MagicType.MAGIC_TYPE, new Object[0]);
            if (data instanceof Integer) {
                num5 = (Integer) data;
            } else {
                num5 = null;
            }
            if (magicType.isFrontBack(num5)) {
                data2 = wallpaperEditor.getEditCallback().getData("support_font_back", stringExtra13);
                Intrinsics.checkNotNull(data2, "null cannot be cast to non-null type kotlin.Boolean");
                if (((Boolean) data2).booleanValue()) {
                    depthBitmap = GLRenderHelper.INSTANCE.getDepthBitmap(bitmapResizeImageWallpaper);
                } else {
                    depthBitmap = null;
                }
            } else {
                depthBitmap = null;
            }
            boolean zIsSupportBlurWallpaperType14 = Wallpaper.Companion.isSupportBlurWallpaperType(stringExtra13, 0);
            SensorWallpaperPrams sensorWallpaperPrams14 = new SensorWallpaperPrams(stringExtra, str4, numValueOf, num4, num2, num3, f, stringExtra3, str2, str3);
            sensorWallpaperPrams14.setCurrentSmallSensorWallpaper(bitmapDecodeFile$default);
            if (bitmapResizeImageWallpaper != null) {
                numValueOf5 = Integer.valueOf(bitmapResizeImageWallpaper.getWidth());
            } else {
                numValueOf5 = null;
            }
            if (bitmapResizeImageWallpaper != null) {
                numValueOf6 = Integer.valueOf(bitmapResizeImageWallpaper.getHeight());
            } else {
                numValueOf6 = null;
            }
            Log.d("WallpaperEditor", "bitmap width:" + numValueOf5 + " height:" + numValueOf6);
            if (i != 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (bool2 != null) {
                zBooleanValue = bool2.booleanValue();
            } else {
                zBooleanValue = false;
            }
            if (bool != null) {
                zBooleanValue2 = bool.booleanValue();
            } else {
                zBooleanValue2 = false;
            }
            return new WallpaperTypeInfo(bitmapResizeImageWallpaper, stringExtra13, strObtainFile, stringExtra14, i, z2, sensorWallpaperPrams14, false, zIsSupportBlurWallpaperType14, boolValueOf, depthBitmap, num, str6, zBooleanValue, zBooleanValue2, null, stringExtra6, null, boolValueOf4, str5, 163840, null);
            pictureDegree = wallpaperEditor.readPictureDegree(strObtainFile);
            Log.i("WallpaperEditor", "degree:==" + pictureDegree);
            if (pictureDegree != 0) {
                bitmapResizeImageWallpaper2 = wallpaperEditor.resizeImageWallpaper(strObtainFile);
                if (bitmapResizeImageWallpaper2 != null) {
                    bitmapResizeImageWallpaper = wallpaperEditor.revolveImageView(pictureDegree, bitmapResizeImageWallpaper2);
                } else {
                    num = num;
                    bitmapResizeImageWallpaper = null;
                }
                str6 = null;
                bitmapDecodeFile$default = null;
                magicType = MagicType.INSTANCE;
                data = wallpaperEditor.getEditCallback().getData(MagicType.MAGIC_TYPE, new Object[0]);
                if (data instanceof Integer) {
                    num5 = (Integer) data;
                } else {
                    num5 = null;
                }
                if (magicType.isFrontBack(num5)) {
                    data2 = wallpaperEditor.getEditCallback().getData("support_font_back", stringExtra13);
                    Intrinsics.checkNotNull(data2, "null cannot be cast to non-null type kotlin.Boolean");
                    if (((Boolean) data2).booleanValue()) {
                        depthBitmap = GLRenderHelper.INSTANCE.getDepthBitmap(bitmapResizeImageWallpaper);
                    } else {
                        depthBitmap = null;
                    }
                } else {
                    depthBitmap = null;
                }
                boolean zIsSupportBlurWallpaperType15 = Wallpaper.Companion.isSupportBlurWallpaperType(stringExtra13, 0);
                SensorWallpaperPrams sensorWallpaperPrams15 = new SensorWallpaperPrams(stringExtra, str4, numValueOf, num4, num2, num3, f, stringExtra3, str2, str3);
                sensorWallpaperPrams15.setCurrentSmallSensorWallpaper(bitmapDecodeFile$default);
                if (bitmapResizeImageWallpaper != null) {
                    numValueOf5 = Integer.valueOf(bitmapResizeImageWallpaper.getWidth());
                } else {
                    numValueOf5 = null;
                }
                if (bitmapResizeImageWallpaper != null) {
                    numValueOf6 = Integer.valueOf(bitmapResizeImageWallpaper.getHeight());
                } else {
                    numValueOf6 = null;
                }
                Log.d("WallpaperEditor", "bitmap width:" + numValueOf5 + " height:" + numValueOf6);
                if (i != 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (bool2 != null) {
                    zBooleanValue = bool2.booleanValue();
                } else {
                    zBooleanValue = false;
                }
                if (bool != null) {
                    zBooleanValue2 = bool.booleanValue();
                } else {
                    zBooleanValue2 = false;
                }
                return new WallpaperTypeInfo(bitmapResizeImageWallpaper, stringExtra13, strObtainFile, stringExtra14, i, z2, sensorWallpaperPrams15, false, zIsSupportBlurWallpaperType15, boolValueOf, depthBitmap, num, str6, zBooleanValue, zBooleanValue2, null, stringExtra6, null, boolValueOf4, str5, 163840, null);
            }
            bitmapResizeImageWallpaper = wallpaperEditor.resizeImageWallpaper(strObtainFile);
            str6 = null;
            bitmapDecodeFile$default = null;
            magicType = MagicType.INSTANCE;
            data = wallpaperEditor.getEditCallback().getData(MagicType.MAGIC_TYPE, new Object[0]);
            if (data instanceof Integer) {
                num5 = (Integer) data;
            } else {
                num5 = null;
            }
            if (magicType.isFrontBack(num5)) {
                data2 = wallpaperEditor.getEditCallback().getData("support_font_back", stringExtra13);
                Intrinsics.checkNotNull(data2, "null cannot be cast to non-null type kotlin.Boolean");
                if (((Boolean) data2).booleanValue()) {
                    depthBitmap = GLRenderHelper.INSTANCE.getDepthBitmap(bitmapResizeImageWallpaper);
                } else {
                    depthBitmap = null;
                }
            } else {
                depthBitmap = null;
            }
            boolean zIsSupportBlurWallpaperType16 = Wallpaper.Companion.isSupportBlurWallpaperType(stringExtra13, 0);
            SensorWallpaperPrams sensorWallpaperPrams16 = new SensorWallpaperPrams(stringExtra, str4, numValueOf, num4, num2, num3, f, stringExtra3, str2, str3);
            sensorWallpaperPrams16.setCurrentSmallSensorWallpaper(bitmapDecodeFile$default);
            if (bitmapResizeImageWallpaper != null) {
                numValueOf5 = Integer.valueOf(bitmapResizeImageWallpaper.getWidth());
            } else {
                numValueOf5 = null;
            }
            if (bitmapResizeImageWallpaper != null) {
                numValueOf6 = Integer.valueOf(bitmapResizeImageWallpaper.getHeight());
            } else {
                numValueOf6 = null;
            }
            Log.d("WallpaperEditor", "bitmap width:" + numValueOf5 + " height:" + numValueOf6);
            if (i != 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (bool2 != null) {
                zBooleanValue = bool2.booleanValue();
            } else {
                zBooleanValue = false;
            }
            if (bool != null) {
                zBooleanValue2 = bool.booleanValue();
            } else {
                zBooleanValue2 = false;
            }
            return new WallpaperTypeInfo(bitmapResizeImageWallpaper, stringExtra13, strObtainFile, stringExtra14, i, z2, sensorWallpaperPrams16, false, zIsSupportBlurWallpaperType16, boolValueOf, depthBitmap, num, str6, zBooleanValue, zBooleanValue2, null, stringExtra6, null, boolValueOf4, str5, 163840, null);
        }
        bool = boolValueOf2;
        bool2 = boolValueOf3;
        str5 = stringExtra7;
        pictureDegree = wallpaperEditor.readPictureDegree(strObtainFile);
        Log.i("WallpaperEditor", "degree:==" + pictureDegree);
        if (pictureDegree != 0) {
            bitmapResizeImageWallpaper2 = wallpaperEditor.resizeImageWallpaper(strObtainFile);
            if (bitmapResizeImageWallpaper2 != null) {
                bitmapResizeImageWallpaper = wallpaperEditor.revolveImageView(pictureDegree, bitmapResizeImageWallpaper2);
            } else {
                num = num;
                bitmapResizeImageWallpaper = null;
            }
            str6 = null;
            bitmapDecodeFile$default = null;
            magicType = MagicType.INSTANCE;
            data = wallpaperEditor.getEditCallback().getData(MagicType.MAGIC_TYPE, new Object[0]);
            if (data instanceof Integer) {
                num5 = (Integer) data;
            } else {
                num5 = null;
            }
            if (magicType.isFrontBack(num5)) {
                data2 = wallpaperEditor.getEditCallback().getData("support_font_back", stringExtra13);
                Intrinsics.checkNotNull(data2, "null cannot be cast to non-null type kotlin.Boolean");
                if (((Boolean) data2).booleanValue()) {
                    depthBitmap = GLRenderHelper.INSTANCE.getDepthBitmap(bitmapResizeImageWallpaper);
                } else {
                    depthBitmap = null;
                }
            } else {
                depthBitmap = null;
            }
            boolean zIsSupportBlurWallpaperType17 = Wallpaper.Companion.isSupportBlurWallpaperType(stringExtra13, 0);
            SensorWallpaperPrams sensorWallpaperPrams17 = new SensorWallpaperPrams(stringExtra, str4, numValueOf, num4, num2, num3, f, stringExtra3, str2, str3);
            sensorWallpaperPrams17.setCurrentSmallSensorWallpaper(bitmapDecodeFile$default);
            if (bitmapResizeImageWallpaper != null) {
                numValueOf5 = Integer.valueOf(bitmapResizeImageWallpaper.getWidth());
            } else {
                numValueOf5 = null;
            }
            if (bitmapResizeImageWallpaper != null) {
                numValueOf6 = Integer.valueOf(bitmapResizeImageWallpaper.getHeight());
            } else {
                numValueOf6 = null;
            }
            Log.d("WallpaperEditor", "bitmap width:" + numValueOf5 + " height:" + numValueOf6);
            if (i != 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (bool2 != null) {
                zBooleanValue = bool2.booleanValue();
            } else {
                zBooleanValue = false;
            }
            if (bool != null) {
                zBooleanValue2 = bool.booleanValue();
            } else {
                zBooleanValue2 = false;
            }
            return new WallpaperTypeInfo(bitmapResizeImageWallpaper, stringExtra13, strObtainFile, stringExtra14, i, z2, sensorWallpaperPrams17, false, zIsSupportBlurWallpaperType17, boolValueOf, depthBitmap, num, str6, zBooleanValue, zBooleanValue2, null, stringExtra6, null, boolValueOf4, str5, 163840, null);
        }
        bitmapResizeImageWallpaper = wallpaperEditor.resizeImageWallpaper(strObtainFile);
        str6 = null;
        bitmapDecodeFile$default = null;
        magicType = MagicType.INSTANCE;
        data = wallpaperEditor.getEditCallback().getData(MagicType.MAGIC_TYPE, new Object[0]);
        if (data instanceof Integer) {
            num5 = (Integer) data;
        } else {
            num5 = null;
        }
        if (magicType.isFrontBack(num5)) {
            data2 = wallpaperEditor.getEditCallback().getData("support_font_back", stringExtra13);
            Intrinsics.checkNotNull(data2, "null cannot be cast to non-null type kotlin.Boolean");
            if (((Boolean) data2).booleanValue()) {
                depthBitmap = GLRenderHelper.INSTANCE.getDepthBitmap(bitmapResizeImageWallpaper);
            } else {
                depthBitmap = null;
            }
        } else {
            depthBitmap = null;
        }
        boolean zIsSupportBlurWallpaperType18 = Wallpaper.Companion.isSupportBlurWallpaperType(stringExtra13, 0);
        SensorWallpaperPrams sensorWallpaperPrams18 = new SensorWallpaperPrams(stringExtra, str4, numValueOf, num4, num2, num3, f, stringExtra3, str2, str3);
        sensorWallpaperPrams18.setCurrentSmallSensorWallpaper(bitmapDecodeFile$default);
        if (bitmapResizeImageWallpaper != null) {
            numValueOf5 = Integer.valueOf(bitmapResizeImageWallpaper.getWidth());
        } else {
            numValueOf5 = null;
        }
        if (bitmapResizeImageWallpaper != null) {
            numValueOf6 = Integer.valueOf(bitmapResizeImageWallpaper.getHeight());
        } else {
            numValueOf6 = null;
        }
        Log.d("WallpaperEditor", "bitmap width:" + numValueOf5 + " height:" + numValueOf6);
        if (i != 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (bool2 != null) {
            zBooleanValue = bool2.booleanValue();
        } else {
            zBooleanValue = false;
        }
        if (bool != null) {
            zBooleanValue2 = bool.booleanValue();
        } else {
            zBooleanValue2 = false;
        }
        return new WallpaperTypeInfo(bitmapResizeImageWallpaper, stringExtra13, strObtainFile, stringExtra14, i, z2, sensorWallpaperPrams18, false, zIsSupportBlurWallpaperType18, boolValueOf, depthBitmap, num, str6, zBooleanValue, zBooleanValue2, null, stringExtra6, null, boolValueOf4, str5, 163840, null);
    }

    public final int readPictureDegree(String str) {
        try {
            int attributeInt = new ExifInterface(str).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
            if (attributeInt == 3) {
                return 180;
            }
            if (attributeInt != 6) {
                return attributeInt != 8 ? 0 : 270;
            }
            return 90;
        } catch (Exception e) {
            Log.e("WallpaperEditor", "readPictureDegree: " + e);
            return 0;
        }
    }

    public final void resetPickerLaunchingState() {
        isPickerLaunching = false;
    }

    public final Bitmap resizeImageWallpaper(String str) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        Log.i("WallpaperEditor", "options.outWidth :" + options.outWidth + "  outHeight:" + options.outHeight);
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inJustDecodeBounds = false;
        int i = options.outWidth;
        if (i >= 3333 || options.outHeight >= 3333) {
            options.inSampleSize = (int) (((double) ((RangesKt.coerceAtLeast(i, options.outHeight) * 1.0f) / 3333)) + 0.5d);
        }
        return BitmapCreator.INSTANCE.decodeFile(str, options);
    }

    public final Bitmap revolveImageView(int i, Bitmap bitmap) {
        Bitmap bitmapCreateBitmap = null;
        try {
            Matrix matrix = new Matrix();
            matrix.postRotate(i);
            bitmapCreateBitmap = BitmapCreator.INSTANCE.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (bitmapCreateBitmap == null) {
                bitmapCreateBitmap = bitmap;
            }
            if (Intrinsics.areEqual(bitmap, bitmapCreateBitmap)) {
                return bitmapCreateBitmap;
            }
            BitmapUtil.INSTANCE.recycleBitmap(bitmap, "rotate source bitmap");
            return bitmapCreateBitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return bitmapCreateBitmap;
        }
    }
}
