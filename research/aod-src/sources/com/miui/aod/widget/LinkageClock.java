package com.miui.aod.widget;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.aod.R;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.linkagestyle.LinkageStyleClockView;
import com.miui.clock.MiuiClockView;
import java.util.TimeZone;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.pickerwidget.date.Calendar;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: LinkageClock.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public class LinkageClock implements ILinkageClock {
    public View clockContainer;
    public final Lazy mCal$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.aod.widget.LinkageClock$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return LinkageClock.mCal_delegate$lambda$0();
        }
    });
    public TextView mClockDate;
    public LinkageStyleClockView mClockViewContainer;
    public Context mContext;
    public TextView mDateLunar;
    public LinearLayout mFnContainer;
    public final int mSize;
    public StyleInfo mStyleInfo;

    public LinkageClock(int i) {
        this.mSize = i;
    }

    public static final Calendar mCal_delegate$lambda$0() {
        return new Calendar(TimeZone.getDefault());
    }

    @Override // com.miui.aod.widget.IAodClock
    public void bindView(View clock) {
        Intrinsics.checkNotNullParameter(clock, "clock");
        setMContext(clock.getContext());
        this.mClockViewContainer = (LinkageStyleClockView) clock.findViewById(R.id.linkage_clock_container);
        this.mFnContainer = (LinearLayout) clock.findViewById(R.id.fn_container);
        LinkageStyleClockView linkageStyleClockView = this.mClockViewContainer;
        if (linkageStyleClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
            linkageStyleClockView = null;
        }
        StyleInfo styleInfo = this.mStyleInfo;
        if (styleInfo == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
            styleInfo = null;
        }
        linkageStyleClockView.setStyleInfo(styleInfo);
        linkageStyleClockView.bindView(this.mSize);
        linkageStyleClockView.setViewGravity();
        linkageStyleClockView.setSize(this.mSize);
        View viewFindViewById = linkageStyleClockView.findViewById(R.id.clock_info_container);
        this.clockContainer = viewFindViewById;
        this.mClockDate = viewFindViewById != null ? (TextView) viewFindViewById.findViewById(R.id.date) : null;
        View view = this.clockContainer;
        this.mDateLunar = view != null ? (TextView) view.findViewById(R.id.date_lunar) : null;
        MiuiClockView miuiClockView = linkageStyleClockView.getMiuiClockView();
        if (miuiClockView != null) {
            miuiClockView.setGradientIndex(LinkageClockKt.getGradientIndex());
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public int getClockGravity() {
        LinkageStyleClockView linkageStyleClockView = this.mClockViewContainer;
        if (linkageStyleClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
            linkageStyleClockView = null;
        }
        return linkageStyleClockView.getClockGravity();
    }

    @Override // com.miui.aod.widget.IAodClock
    public int getLayoutResource(StyleInfo styleInfo) {
        if (styleInfo != null) {
            this.mStyleInfo = styleInfo;
        }
        return this.mSize == 2 ? R.layout.aod_content_linkage_small : R.layout.aod_content_linkage;
    }

    public final Calendar getMCal() {
        return (Calendar) this.mCal$delegate.getValue();
    }

    public final Context getMContext() {
        Context context = this.mContext;
        if (context != null) {
            return context;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mContext");
        return null;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void onInflateComplete() {
        updateClockColor(this.mSize);
    }

    @Override // com.miui.aod.widget.IAodClock
    public void preDestroyForChange() {
        View view = this.clockContainer;
        if (view != null) {
            view.setVisibility(8);
        }
    }

    @Override // com.miui.aod.widget.ILinkageClock
    public void releaseResources() {
    }

    @Override // com.miui.aod.widget.ILinkageClock
    public void setClockViewVisible(boolean z) {
        Log.d("LinkageClock", "setClockViewVisible: " + z);
        LinkageStyleClockView linkageStyleClockView = this.mClockViewContainer;
        if (linkageStyleClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
            linkageStyleClockView = null;
        }
        linkageStyleClockView.setClockViewVisible(z);
    }

    public final void setMContext(Context context) {
        Intrinsics.checkNotNullParameter(context, "<set-?>");
        this.mContext = context;
    }

    @Override // com.miui.aod.components.view.IPreAodView
    public void update(StyleInfo styleInfo, int i) {
        if (styleInfo == null) {
            return;
        }
        updateClockColor(this.mSize);
        this.mStyleInfo = styleInfo;
        LinkageStyleClockView linkageStyleClockView = this.mClockViewContainer;
        LinkageStyleClockView linkageStyleClockView2 = null;
        if (linkageStyleClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
            linkageStyleClockView = null;
        }
        linkageStyleClockView.updateClockViewLayout(i);
        LinkageStyleClockView linkageStyleClockView3 = this.mClockViewContainer;
        if (linkageStyleClockView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
        } else {
            linkageStyleClockView2 = linkageStyleClockView3;
        }
        linkageStyleClockView2.updateClockBeanChange();
    }

    @Override // com.miui.aod.widget.OnClockColorChangeListener
    public void updateClockColor(int i) {
        Log.d("LinkageClock", "updateClockColor: " + i);
        LinkageStyleClockView linkageStyleClockView = this.mClockViewContainer;
        if (linkageStyleClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
            linkageStyleClockView = null;
        }
        linkageStyleClockView.updateClockColor(i);
    }

    @Override // com.miui.aod.widget.IAodClock
    public void updateTime(boolean z) {
        TextView textView;
        LinkageStyleClockView linkageStyleClockView = this.mClockViewContainer;
        StyleInfo styleInfo = null;
        if (linkageStyleClockView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
            linkageStyleClockView = null;
        }
        linkageStyleClockView.updateTime();
        getMCal().setTimeInMillis(System.currentTimeMillis());
        TextView textView2 = this.mClockDate;
        if (textView2 != null) {
            String dateFormat = AODSettings.getDateFormat(getMContext(), z, false);
            Intrinsics.checkNotNullExpressionValue(dateFormat, "getDateFormat(...)");
            textView2.setText(getMCal().format(getMContext(), dateFormat));
        }
        LinkageStyleClockView linkageStyleClockView2 = this.mClockViewContainer;
        if (linkageStyleClockView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mClockViewContainer");
            linkageStyleClockView2 = null;
        }
        if (linkageStyleClockView2.notSupportLunarTime()) {
            TextView textView3 = this.mDateLunar;
            if (textView3 != null) {
                textView3.setVisibility(8);
                return;
            }
            return;
        }
        StyleInfo styleInfo2 = this.mStyleInfo;
        if (styleInfo2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mStyleInfo");
        } else {
            styleInfo = styleInfo2;
        }
        if (!styleInfo.isLunarSwitchOn(getMContext()) || (textView = this.mDateLunar) == null) {
            return;
        }
        textView.setText(getMCal().format(getMContext(), AODSettings.getDateFormat(getMContext(), z, true)));
    }
}
