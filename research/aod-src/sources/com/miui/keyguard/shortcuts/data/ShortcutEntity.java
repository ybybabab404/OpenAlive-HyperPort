package com.miui.keyguard.shortcuts.data;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.UserHandle;
import androidx.annotation.Keep;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import com.miui.keyguard.shortcuts.utils.DataUtils;
import com.miui.keyguard.shortcuts.utils.LogUtils;
import com.miui.keyguard.shortcuts.utils.PackageUtils;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
/* JADX INFO: compiled from: ShortcutEntity.kt */
/* JADX INFO: loaded from: classes2.dex */
@Keep
@Metadata
@SourceDebugExtension
public final class ShortcutEntity {

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final String EXTRA_FROM_KEYGUARD_SHORTCUT = "from_keyguard_shortcut";

    @NotNull
    private static final String TAG = "ShortcutEntity";

    @Nullable
    private transient String _uniqueTag;

    @Nullable
    private final String action;

    @Nullable
    private final String data;

    @Nullable
    private transient Drawable drawable;

    @Nullable
    private final String iconId;
    private transient int index;
    private transient boolean isAvailable;
    private boolean isOverlay;
    private boolean isSystemDefault;

    @Nullable
    private final String nameId;

    @Nullable
    private final String packageName;

    @Nullable
    private transient String shortcutName;

    @Nullable
    private transient String shortcutTrackName;

    @Nullable
    private final String tag;

    @Nullable
    private final String targetClass;

    @Nullable
    private final String targetPackage;

    /* JADX INFO: compiled from: ShortcutEntity.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.shortcuts.data.ShortcutEntity$buildShortcutNameAndDrawableAsync$2, reason: invalid class name */
    /* JADX INFO: compiled from: ShortcutEntity.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.shortcuts.data.ShortcutEntity$buildShortcutNameAndDrawableAsync$2", f = "ShortcutEntity.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Context $context;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(Context context, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ShortcutEntity.this.new AnonymousClass2(this.$context, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ShortcutEntity.this.buildShortcutNameAndDrawable(this.$context);
            return Unit.INSTANCE;
        }
    }

    public ShortcutEntity() {
        this(0, null, null, null, null, null, null, null, null, false, false, null, null, null, false, 32767, null);
    }

    public ShortcutEntity(int i, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable String str8, boolean z, boolean z2, @Nullable String str9, @Nullable String str10, @Nullable Drawable drawable, boolean z3) {
        this.index = i;
        this.packageName = str;
        this.nameId = str2;
        this.action = str3;
        this.data = str4;
        this.iconId = str5;
        this.tag = str6;
        this.targetClass = str7;
        this.targetPackage = str8;
        this.isSystemDefault = z;
        this.isOverlay = z2;
        this.shortcutName = str9;
        this.shortcutTrackName = str10;
        this.drawable = drawable;
        this.isAvailable = z3;
    }

    public /* synthetic */ ShortcutEntity(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, boolean z, boolean z2, String str9, String str10, Drawable drawable, boolean z3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? null : str, (i2 & 4) != 0 ? null : str2, (i2 & 8) != 0 ? null : str3, (i2 & 16) != 0 ? null : str4, (i2 & 32) != 0 ? null : str5, (i2 & 64) != 0 ? null : str6, (i2 & 128) != 0 ? null : str7, (i2 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? null : str8, (i2 & 512) != 0 ? false : z, (i2 & 1024) == 0 ? z2 : false, (i2 & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0 ? null : str9, (i2 & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 ? null : str10, (i2 & AutoBottomSheetKt.ALIGN_SCREEN_START) == 0 ? drawable : null, (i2 & 16384) != 0 ? true : z3);
    }

    private final String buildUniqueTag() {
        return isEmpty() ? "empty_unique_tag" : DataUtils.INSTANCE.buildUniqueTag(this.packageName, this.tag);
    }

    private final boolean isNeedUpdateAvailable() {
        return isFashionGallery();
    }

    public final void buildShortcutNameAndDrawable(@NotNull Context context) {
        PackageUtils packageUtils;
        Resources resourcesForApplication;
        Intrinsics.checkNotNullParameter(context, "context");
        String shortcutNameForLanguage = "无";
        if (isEmptyOrUnknown()) {
            this.shortcutTrackName = "无";
            return;
        }
        String str = this.packageName;
        if (str != null && (resourcesForApplication = (packageUtils = PackageUtils.INSTANCE).getResourcesForApplication(context, str)) != null) {
            this.shortcutName = packageUtils.getStringFromApplication(resourcesForApplication, this.packageName, this.nameId);
            this.drawable = packageUtils.getDrawableFromApplication(context, resourcesForApplication, this.packageName, this.iconId);
        }
        String str2 = this.packageName;
        if (str2 != null && str2.length() != 0) {
            PackageUtils packageUtils2 = PackageUtils.INSTANCE;
            String str3 = this.packageName;
            Locale CHINESE = Locale.CHINESE;
            Intrinsics.checkNotNullExpressionValue(CHINESE, "CHINESE");
            shortcutNameForLanguage = packageUtils2.getShortcutNameForLanguage(context, str3, CHINESE, this.nameId);
            if (shortcutNameForLanguage == null) {
                shortcutNameForLanguage = "无_异常";
            }
        }
        this.shortcutTrackName = shortcutNameForLanguage;
        LogUtils.logD$default(LogUtils.INSTANCE, TAG, "buildShortcutNameAndDrawable, pkgName=" + this.packageName + ", shortcutName=" + this.shortcutName + ", shortcutTrackName=" + shortcutNameForLanguage + ", drawable=" + this.drawable, null, 4, null);
    }

    @Nullable
    public final Object buildShortcutNameAndDrawableAsync(@NotNull Context context, @NotNull Continuation<? super Unit> continuation) {
        Object objWithContext = BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass2(context, null), continuation);
        return objWithContext == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWithContext : Unit.INSTANCE;
    }

    public final int component1() {
        return this.index;
    }

    public final boolean component10() {
        return this.isSystemDefault;
    }

    public final boolean component11() {
        return this.isOverlay;
    }

    @Nullable
    public final String component12() {
        return this.shortcutName;
    }

    @Nullable
    public final String component13() {
        return this.shortcutTrackName;
    }

    @Nullable
    public final Drawable component14() {
        return this.drawable;
    }

    public final boolean component15() {
        return this.isAvailable;
    }

    @Nullable
    public final String component2() {
        return this.packageName;
    }

    @Nullable
    public final String component3() {
        return this.nameId;
    }

    @Nullable
    public final String component4() {
        return this.action;
    }

    @Nullable
    public final String component5() {
        return this.data;
    }

    @Nullable
    public final String component6() {
        return this.iconId;
    }

    @Nullable
    public final String component7() {
        return this.tag;
    }

    @Nullable
    public final String component8() {
        return this.targetClass;
    }

    @Nullable
    public final String component9() {
        return this.targetPackage;
    }

    @NotNull
    public final ShortcutEntity copy(int i, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable String str8, boolean z, boolean z2, @Nullable String str9, @Nullable String str10, @Nullable Drawable drawable, boolean z3) {
        return new ShortcutEntity(i, str, str2, str3, str4, str5, str6, str7, str8, z, z2, str9, str10, drawable, z3);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShortcutEntity)) {
            return false;
        }
        ShortcutEntity shortcutEntity = (ShortcutEntity) obj;
        return this.index == shortcutEntity.index && Intrinsics.areEqual(this.packageName, shortcutEntity.packageName) && Intrinsics.areEqual(this.nameId, shortcutEntity.nameId) && Intrinsics.areEqual(this.action, shortcutEntity.action) && Intrinsics.areEqual(this.data, shortcutEntity.data) && Intrinsics.areEqual(this.iconId, shortcutEntity.iconId) && Intrinsics.areEqual(this.tag, shortcutEntity.tag) && Intrinsics.areEqual(this.targetClass, shortcutEntity.targetClass) && Intrinsics.areEqual(this.targetPackage, shortcutEntity.targetPackage) && this.isSystemDefault == shortcutEntity.isSystemDefault && this.isOverlay == shortcutEntity.isOverlay && Intrinsics.areEqual(this.shortcutName, shortcutEntity.shortcutName) && Intrinsics.areEqual(this.shortcutTrackName, shortcutEntity.shortcutTrackName) && Intrinsics.areEqual(this.drawable, shortcutEntity.drawable) && this.isAvailable == shortcutEntity.isAvailable;
    }

    @Nullable
    public final String getAction() {
        return this.action;
    }

    @Nullable
    public final String getData() {
        return this.data;
    }

    @Nullable
    public final Drawable getDrawable() {
        return this.drawable;
    }

    @Nullable
    public final Drawable getDrawableWithAvailable() {
        if (this.isAvailable) {
            return this.drawable;
        }
        return null;
    }

    @Nullable
    public final String getIconId() {
        return this.iconId;
    }

    public final int getIndex() {
        return this.index;
    }

    @Nullable
    public final String getNameId() {
        return this.nameId;
    }

    @Nullable
    public final Object getNewDrawable(@NotNull Context context, @NotNull Continuation<? super Drawable> continuation) {
        if (isEmptyOrUnknown() || this.packageName == null) {
            return null;
        }
        Object objWithContext = BuildersKt.withContext(Dispatchers.getIO(), new ShortcutEntity$getNewDrawable$2$1(context, this, null), continuation);
        return objWithContext == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWithContext : (Drawable) objWithContext;
    }

    @Nullable
    public final Object getNewShortcutName(@NotNull Context context, @NotNull Continuation<? super String> continuation) {
        if (isEmptyOrUnknown() || this.packageName == null) {
            return null;
        }
        Object objWithContext = BuildersKt.withContext(Dispatchers.getIO(), new ShortcutEntity$getNewShortcutName$2$1(context, this, null), continuation);
        return objWithContext == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWithContext : (String) objWithContext;
    }

    @Nullable
    public final String getPackageName() {
        return this.packageName;
    }

    @Nullable
    public final String getShortcutName() {
        return this.shortcutName;
    }

    @Nullable
    public final String getShortcutTrackName() {
        return this.shortcutTrackName;
    }

    @Nullable
    public final String getTag() {
        return this.tag;
    }

    @Nullable
    public final String getTargetClass() {
        return this.targetClass;
    }

    @Nullable
    public final String getTargetPackage() {
        return this.targetPackage;
    }

    @NotNull
    public final String getUniqueTag() {
        String str = this._uniqueTag;
        if (str != null) {
            return str;
        }
        String strBuildUniqueTag = buildUniqueTag();
        this._uniqueTag = strBuildUniqueTag;
        return strBuildUniqueTag;
    }

    public int hashCode() {
        int iHashCode = Integer.hashCode(this.index) * 31;
        String str = this.packageName;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.nameId;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.action;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.data;
        int iHashCode5 = (iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.iconId;
        int iHashCode6 = (iHashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.tag;
        int iHashCode7 = (iHashCode6 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.targetClass;
        int iHashCode8 = (iHashCode7 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.targetPackage;
        int iHashCode9 = (((((iHashCode8 + (str8 == null ? 0 : str8.hashCode())) * 31) + Boolean.hashCode(this.isSystemDefault)) * 31) + Boolean.hashCode(this.isOverlay)) * 31;
        String str9 = this.shortcutName;
        int iHashCode10 = (iHashCode9 + (str9 == null ? 0 : str9.hashCode())) * 31;
        String str10 = this.shortcutTrackName;
        int iHashCode11 = (iHashCode10 + (str10 == null ? 0 : str10.hashCode())) * 31;
        Drawable drawable = this.drawable;
        return ((iHashCode11 + (drawable != null ? drawable.hashCode() : 0)) * 31) + Boolean.hashCode(this.isAvailable);
    }

    public final boolean isAvailable() {
        return this.isAvailable;
    }

    public final boolean isCurrentApp(@NotNull String packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        LogUtils.logD$default(LogUtils.INSTANCE, TAG, "isCurrentApp, this.packageName=" + this.packageName + ", packageName=" + packageName, null, 4, null);
        return Intrinsics.areEqual(this.packageName, packageName);
    }

    public final boolean isEmpty() {
        String str;
        String str2 = this.packageName;
        return str2 == null || str2.length() == 0 || (str = this.tag) == null || str.length() == 0;
    }

    public final boolean isEmptyOrUnknown() {
        return isEmpty() || isUnknown();
    }

    public final boolean isFashionGallery() {
        return Intrinsics.areEqual(this.packageName, "com.miui.android.fashiongallery");
    }

    @Nullable
    public final Object isNewAvailable(@NotNull Context context, @NotNull Continuation<? super Boolean> continuation) {
        return isNeedUpdateAvailable() ? DataUtils.INSTANCE.isFashionGalleryShortcutAvailable(context, continuation) : Boxing.boxBoolean(true);
    }

    public final boolean isOverlay() {
        return this.isOverlay;
    }

    public final boolean isSameShortcut(@Nullable ShortcutEntity shortcutEntity) {
        if (shortcutEntity != null) {
            return isSameShortcutFingerprint(shortcutEntity) && this.isSystemDefault == shortcutEntity.isSystemDefault && this.isOverlay == shortcutEntity.isOverlay;
        }
        return Intrinsics.areEqual(getUniqueTag(), "empty_unique_tag");
    }

    public final boolean isSameShortcutFingerprint(@NotNull ShortcutEntity otherEntity) {
        Intrinsics.checkNotNullParameter(otherEntity, "otherEntity");
        return Intrinsics.areEqual(getUniqueTag(), otherEntity.getUniqueTag()) && Intrinsics.areEqual(this.nameId, otherEntity.nameId) && Intrinsics.areEqual(this.action, otherEntity.action) && Intrinsics.areEqual(this.data, otherEntity.data) && Intrinsics.areEqual(this.iconId, otherEntity.iconId) && Intrinsics.areEqual(this.targetPackage, otherEntity.targetPackage) && Intrinsics.areEqual(this.targetClass, otherEntity.targetClass);
    }

    public final boolean isSystemDefault() {
        return this.isSystemDefault;
    }

    public final boolean isUniqueTagSame(@Nullable ShortcutEntity shortcutEntity) {
        return shortcutEntity != null && Intrinsics.areEqual(getUniqueTag(), shortcutEntity.getUniqueTag());
    }

    public final boolean isUnknown() {
        DataUtils dataUtils = DataUtils.INSTANCE;
        return this == dataUtils.getUNKNOWN_ENTITY() || Intrinsics.areEqual(getUniqueTag(), dataUtils.getUNKNOWN_ENTITY().getUniqueTag());
    }

    public final void setAvailable(boolean z) {
        this.isAvailable = z;
    }

    public final void setDrawable(@Nullable Drawable drawable) {
        this.drawable = drawable;
    }

    public final void setIndex(int i) {
        this.index = i;
    }

    public final void setOverlay(boolean z) {
        this.isOverlay = z;
    }

    public final void setShortcutName(@Nullable String str) {
        this.shortcutName = str;
    }

    public final void setShortcutTrackName(@Nullable String str) {
        this.shortcutTrackName = str;
    }

    public final void setSystemDefault(boolean z) {
        this.isSystemDefault = z;
    }

    public final boolean startActivity(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String str = this.targetPackage;
        if (str != null) {
            Intent intent = new Intent(this.action);
            intent.setFlags(276856832);
            intent.putExtra(EXTRA_FROM_KEYGUARD_SHORTCUT, true);
            intent.setPackage(str);
            String str2 = this.data;
            if (str2 != null) {
                intent.setData(Uri.parse(str2));
            } else {
                String str3 = this.targetClass;
                if (str3 != null) {
                    intent.setClassName(str, str3);
                }
            }
            LogUtils.logD$default(LogUtils.INSTANCE, TAG, "start shortcut Activity, intent=" + intent, null, 4, null);
            try {
                context.startActivityAsUser(intent, UserHandle.CURRENT);
                return true;
            } catch (ActivityNotFoundException e) {
                LogUtils.INSTANCE.logE(TAG, "start shortcut Activity error", e);
            }
        }
        return false;
    }

    @NotNull
    public String toString() {
        return "ShortcutEntity(index=" + this.index + ", packageName=" + this.packageName + ", nameId=" + this.nameId + ", action=" + this.action + ", data=" + this.data + ", iconId=" + this.iconId + ", tag=" + this.tag + ", targetClass=" + this.targetClass + ", targetPackage=" + this.targetPackage + ", isSystemDefault=" + this.isSystemDefault + ", isOverlay=" + this.isOverlay + ", shortcutName=" + this.shortcutName + ", shortcutTrackName=" + this.shortcutTrackName + ", drawable=" + this.drawable + ", isAvailable=" + this.isAvailable + ")";
    }

    public final boolean updateAvailable(@NotNull Context context) {
        boolean shortcutAvailable;
        Intrinsics.checkNotNullParameter(context, "context");
        if (!isNeedUpdateAvailable() || this.isAvailable == (shortcutAvailable = DataUtils.INSTANCE.getShortcutAvailable(context))) {
            return false;
        }
        this.isAvailable = shortcutAvailable;
        return true;
    }
}
