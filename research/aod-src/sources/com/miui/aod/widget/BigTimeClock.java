package com.miui.aod.widget;

import android.content.Context;
import android.view.View;
import com.miui.aod.R;
import com.miui.aod.common.StyleInfo;
import java.util.TimeZone;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
/* JADX INFO: compiled from: BigTimeClock.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class BigTimeClock implements IAodClock, OnClockColorChangeListener {
    public static final Companion Companion = new Companion(null);
    public static final Integer[] POSITION_ARRAY = {0, 1, 2};
    public final boolean isDual;
    public BigTimeAodView mBigTimeAodView;
    public Context mContext;
    public int mLastPosition = 2;
    public int mSize = 3;
    public StyleInfo mStyleInfo;
    public TimeZone mTimeZone;
    public TimeZone mTimeZoneDual;
    public final int size;

    /* JADX INFO: compiled from: BigTimeClock.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BigTimeClock(boolean z, int i) {
        this.isDual = z;
        this.size = i;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void bindView(View clock) {
        Intrinsics.checkNotNullParameter(clock, "clock");
        this.mContext = clock.getContext();
        this.mBigTimeAodView = (BigTimeAodView) clock.findViewById(R.id.big_time_container);
    }

    @Override // com.miui.aod.widget.IAodClock
    public int getLayoutResource(StyleInfo styleInfo) {
        return R.layout.aod_content_big_time;
    }

    public final int getNextPosition(int i) {
        Integer[] numArr = POSITION_ARRAY;
        int iIndexOf = ArraysKt.indexOf(numArr, Integer.valueOf(i));
        if (iIndexOf == -1) {
            iIndexOf = numArr.length - 1;
        }
        return numArr[(iIndexOf + 1) % numArr.length].intValue();
    }

    @Override // com.miui.aod.widget.IAodClock
    public boolean handleUpdateNotificationIcons(IAodView iAodView) {
        BigTimeAodView bigTimeAodView;
        if (iAodView == null || (bigTimeAodView = this.mBigTimeAodView) == null) {
            return super.handleUpdateNotificationIcons(iAodView);
        }
        Intrinsics.checkNotNull(bigTimeAodView);
        return bigTimeAodView.handleUpdateNotificationIcons(iAodView);
    }

    @Override // com.miui.aod.widget.IAodClock
    public boolean isUpdatePositionBySelf() {
        return true;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setTimeZone(TimeZone timeZone) {
        this.mTimeZone = timeZone;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setTimeZone2(TimeZone timeZone) {
        this.mTimeZoneDual = timeZone;
    }

    @Override // com.miui.aod.components.view.IPreAodView
    public void update(StyleInfo styleInfo, int i) {
        this.mStyleInfo = styleInfo;
        this.mSize = i;
        BigTimeAodView bigTimeAodView = this.mBigTimeAodView;
        if (bigTimeAodView != null) {
            bigTimeAodView.update(styleInfo, i, this.isDual);
        }
        this.mLastPosition = 2;
        updateLayoutPosition(true, false);
    }

    @Override // com.miui.aod.widget.OnClockColorChangeListener
    public void updateClockColor(int i) {
        BigTimeAodView bigTimeAodView = this.mBigTimeAodView;
        if (bigTimeAodView != null) {
            bigTimeAodView.updateClockColor();
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public void updateLayoutPosition(boolean z, boolean z2) {
        int nextPosition;
        if (this.mSize == 3) {
            nextPosition = z ? getNextPosition(this.mLastPosition) : this.mLastPosition;
            if (nextPosition != this.mLastPosition) {
                this.mLastPosition = nextPosition;
            }
        } else {
            nextPosition = 0;
        }
        BigTimeAodView bigTimeAodView = this.mBigTimeAodView;
        if (bigTimeAodView != null) {
            bigTimeAodView.updatePosition(nextPosition, z2);
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public void updateTime(boolean z) {
        TimeZone timeZone = this.mTimeZone;
        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }
        BigTimeAodView bigTimeAodView = this.mBigTimeAodView;
        if (bigTimeAodView != null) {
            Intrinsics.checkNotNull(timeZone);
            bigTimeAodView.updateTime(z, timeZone, this.mTimeZoneDual);
        }
    }
}
