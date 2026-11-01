package com.miui.keyguard.editor.data.preset;

import android.content.Context;
import com.miui.keyguard.editor.data.util.DataUtil;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.sync.Mutex;

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
/* JADX INFO: compiled from: FontFilter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class FontFilterSelectInfo {
    public FilterColor[][] colorData;
    public FontColorInfo[] colorItemSelectInfo;
    public FilterTypeSelectInfo[] colorSelectInfo;
    public FontFilterInfo currentFilterInfo;
    public List filterIdList;
    public List filterTitleResInfo;
    public int targetIndex;
    public final String templateId;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.data.preset.FontFilterSelectInfo$addCurrentModifyColor$1, reason: invalid class name */
    /* JADX INFO: compiled from: FontFilter.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.data.preset.FontFilterSelectInfo$addCurrentModifyColor$1", f = "FontFilter.kt", l = {2259}, m = "invokeSuspend")
    @SourceDebugExtension
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ FilterColor $color;
        public final /* synthetic */ int $targetIndex;
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public Object L$3;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(int i, FilterColor filterColor, Continuation continuation) {
            super(2, continuation);
            this.$targetIndex = i;
            this.$color = filterColor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return FontFilterSelectInfo.this.new AnonymousClass1(this.$targetIndex, this.$color, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            LockedHashSet currentModifyColorSet;
            FilterColor filterColor;
            LockedHashSet lockedHashSet;
            Mutex mutex;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    currentModifyColorSet = FontFilterSelectInfo.this.getCurrentModifyColorSet(this.$targetIndex);
                    if (currentModifyColorSet != null) {
                        FilterColor filterColor2 = this.$color;
                        Mutex mutexLock = currentModifyColorSet.getMutexLock();
                        this.L$0 = currentModifyColorSet;
                        this.L$1 = filterColor2;
                        this.L$2 = currentModifyColorSet;
                        this.L$3 = mutexLock;
                        this.label = 1;
                        if (mutexLock.lock(currentModifyColorSet, this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        filterColor = filterColor2;
                        lockedHashSet = currentModifyColorSet;
                        mutex = mutexLock;
                    }
                    return Unit.INSTANCE;
                }
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                mutex = (Mutex) this.L$3;
                currentModifyColorSet = (LockedHashSet) this.L$2;
                filterColor = (FilterColor) this.L$1;
                lockedHashSet = (LockedHashSet) this.L$0;
                ResultKt.throwOnFailure(obj);
                if (!lockedHashSet.contains(filterColor)) {
                    lockedHashSet.add(filterColor);
                }
                Unit unit = Unit.INSTANCE;
                return Unit.INSTANCE;
            } finally {
                mutex.unlock(currentModifyColorSet);
            }
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.data.preset.FontFilterSelectInfo$resetCurrentModifyColor$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FontFilter.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.data.preset.FontFilterSelectInfo$resetCurrentModifyColor$1", f = "FontFilter.kt", l = {2260}, m = "invokeSuspend")
    @SourceDebugExtension
    public static final class C00881 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int I$0;
        public int I$1;
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public Object L$3;
        public int label;

        public C00881(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return FontFilterSelectInfo.this.new C00881(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C00881) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:10:0x0044  */
        /* JADX WARN: Code duplicated, block: B:12:0x0057  */
        /* JADX WARN: Code duplicated, block: B:15:0x006c  */
        /* JADX WARN: Code duplicated, block: B:21:0x00b2 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:36:0x0081 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:38:0x007d A[SYNTHETIC] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:11:0x0055 -> B:31:0x00ce). Please report as a decompilation issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x00b0 -> B:34:0x00b3). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached at block B:12:0x0057
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final java.lang.Object invokeSuspend(java.lang.Object r12) {
            /*
                Method dump skipped, instruction units count: 212
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.data.preset.FontFilterSelectInfo.C00881.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public FontFilterSelectInfo(int i, FontColorInfo[] colorItemSelectInfo, List filterTitleResInfo, FilterTypeSelectInfo[] colorSelectInfo, FontFilterInfo currentFilterInfo, FilterColor[][] colorData, String str) {
        Intrinsics.checkNotNullParameter(colorItemSelectInfo, "colorItemSelectInfo");
        Intrinsics.checkNotNullParameter(filterTitleResInfo, "filterTitleResInfo");
        Intrinsics.checkNotNullParameter(colorSelectInfo, "colorSelectInfo");
        Intrinsics.checkNotNullParameter(currentFilterInfo, "currentFilterInfo");
        Intrinsics.checkNotNullParameter(colorData, "colorData");
        this.targetIndex = i;
        this.colorItemSelectInfo = colorItemSelectInfo;
        this.filterTitleResInfo = filterTitleResInfo;
        this.colorSelectInfo = colorSelectInfo;
        this.currentFilterInfo = currentFilterInfo;
        this.colorData = colorData;
        this.templateId = str;
        this.filterIdList = new ArrayList();
        if (Intrinsics.areEqual(str, "doodle")) {
            this.colorData[1][0].setSupportSelect(false);
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ FontFilterSelectInfo(int i, FontColorInfo[] fontColorInfoArr, List list, FilterTypeSelectInfo[] filterTypeSelectInfoArr, FontFilterInfo fontFilterInfo, FilterColor[][] filterColorArr, String str, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        FilterColor[][] filterColorArr2;
        if ((i2 & 32) != 0) {
            DataUtil dataUtil = DataUtil.INSTANCE;
            filterColorArr2 = new FilterColor[][]{DataUtil.copyFilterColorArray$default(dataUtil, FontFilterKt.getDEFAULT_FILTER_COLORS(), null, 2, null), DataUtil.copyFilterColorArray$default(dataUtil, FontFilterKt.getDEFAULT_FILTER_COLORS(), null, 2, null)};
        } else {
            filterColorArr2 = filterColorArr;
        }
        this(i, fontColorInfoArr, list, filterTypeSelectInfoArr, fontFilterInfo, filterColorArr2, str);
    }

    public final void addCurrentModifyColor(int i, Context context, FilterColor color) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(color, "color");
        BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(context), null, null, new AnonymousClass1(i, color, null), 3, null);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(FontFilterSelectInfo.class, obj != null ? obj.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.miui.keyguard.editor.data.preset.FontFilterSelectInfo");
        FontFilterSelectInfo fontFilterSelectInfo = (FontFilterSelectInfo) obj;
        return this.targetIndex == fontFilterSelectInfo.targetIndex && Arrays.equals(this.colorItemSelectInfo, fontFilterSelectInfo.colorItemSelectInfo) && Intrinsics.areEqual(this.filterTitleResInfo, fontFilterSelectInfo.filterTitleResInfo) && Intrinsics.areEqual(this.colorSelectInfo, fontFilterSelectInfo.colorSelectInfo);
    }

    public final FilterTypeSelectInfo[] getColorSelectInfo() {
        return this.colorSelectInfo;
    }

    public final int getCurrentARGBColor(int i) {
        FilterColor currentFilterColor = getCurrentFilterColor(i);
        if (currentFilterColor != null) {
            return currentFilterColor.getColor();
        }
        return -1;
    }

    public final FilterColor[] getCurrentColorData(int i) {
        FilterColor[][] filterColorArr = this.colorData;
        return i > filterColorArr.length + (-1) ? filterColorArr[0] : filterColorArr[i];
    }

    public final int getCurrentColorId(int i) {
        return this.colorSelectInfo[i].getSelectColorId();
    }

    public final int getCurrentColorIndex(int i) {
        return this.colorSelectInfo[i].getCurrentColorIndex();
    }

    public final FilterColor getCurrentFilterColor(int i) {
        return this.colorItemSelectInfo[i].getCurrentFilterColor();
    }

    public final int getCurrentFilterId(int i) {
        return this.currentFilterInfo.getFilterId();
    }

    public final List getCurrentFilterTitleResInfo() {
        return this.filterTitleResInfo;
    }

    public final int getCurrentFilterTypeIndex() {
        return this.currentFilterInfo.getCurrentFilterTypeIndex();
    }

    public final float[] getCurrentHslColor(int i) {
        return this.colorItemSelectInfo[i].getCurrentHslColor();
    }

    public final LockedHashSet getCurrentModifyColorSet(int i) {
        return this.colorSelectInfo[i].getModifyColor();
    }

    public final List getFilterIdList() {
        return this.filterIdList;
    }

    public final int getTargetIndex() {
        return this.targetIndex;
    }

    public int hashCode() {
        return (((((this.targetIndex * 31) + Arrays.hashCode(this.colorItemSelectInfo)) * 31) + this.filterTitleResInfo.hashCode()) * 31) + this.colorSelectInfo.hashCode();
    }

    public final void resetCurrentModifyColor(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        BuildersKt__Builders_commonKt.launch$default(EditorActivityViewModel.Companion.getCoroutineScope(context), null, null, new C00881(null), 3, null);
    }

    public final void setCurrentColor(int i, int i2, int i3) {
        this.colorSelectInfo[i].setCurrentColorIndex(i2);
        this.colorSelectInfo[i].setSelectColorId(i3);
    }

    public final void setCurrentFilterColor(int i, FilterColor filterColor) {
        Intrinsics.checkNotNullParameter(filterColor, "filterColor");
        this.colorItemSelectInfo[i].setCurrentFilterColor(filterColor);
    }

    public final void setCurrentFilterId(int i) {
        this.currentFilterInfo.setFilterId(i);
    }

    public final void setCurrentFilterTypeIndex(int i) {
        this.currentFilterInfo.setCurrentFilterTypeIndex(i);
    }

    public final void setTargetIndex(int i) {
        this.targetIndex = i;
    }

    public String toString() {
        return "FontFilterSelectInfo(targetIndex=" + this.targetIndex + ", colorItemSelectInfo=" + Arrays.toString(this.colorItemSelectInfo) + ", filterTitleResInfo=" + this.filterTitleResInfo + ", colorSelectInfo=" + Arrays.toString(this.colorSelectInfo) + ", currentFilterInfo=" + this.currentFilterInfo + ", colorData=" + Arrays.toString(this.colorData) + ", templateId=" + this.templateId + ")";
    }
}
