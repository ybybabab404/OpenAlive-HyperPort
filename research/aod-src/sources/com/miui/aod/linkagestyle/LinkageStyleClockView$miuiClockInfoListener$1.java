package com.miui.aod.linkagestyle;

import android.util.Log;
import android.view.ViewParent;
import com.miui.aod.AODView;
import com.miui.aod.DozeHost;
import com.miui.aod.doze.DozeMachine;
import com.miui.clock.MiuiClockController;
import com.miui.clock.module.ClockBean;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: LinkageStyleClockView.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class LinkageStyleClockView$miuiClockInfoListener$1 implements MiuiClockController.MiuiClockInfoListener {
    public final /* synthetic */ LinkageStyleClockView this$0;

    public LinkageStyleClockView$miuiClockInfoListener$1(LinkageStyleClockView linkageStyleClockView) {
        this.this$0 = linkageStyleClockView;
    }

    @Override // com.miui.clock.MiuiClockController.MiuiClockInfoListener
    public void onClockBeanChange(ClockBean clockBean, boolean z) {
        this.this$0.updateBattery();
        if (this.this$0.isBindView) {
            this.this$0.isBindView = false;
        } else {
            this.this$0.setMClockIsChange(true);
        }
    }

    @Override // com.miui.clock.MiuiClockController.MiuiClockInfoListener
    public void onDualClockChange(boolean z) {
        LinkageStyleClockView linkageStyleClockView = this.this$0;
        linkageStyleClockView.updateClockViewLayout(linkageStyleClockView.mSize);
    }

    @Override // com.miui.clock.MiuiClockController.MiuiClockInfoListener
    public void onMiuiClockInfoChange(ClockBean clockBean, boolean z) {
        DozeHost host;
        DozeMachine.Service service;
        DozeMachine dozeMachine;
        DozeHost host2;
        DozeHost host3;
        DozeMachine.Service service2;
        DozeMachine dozeMachine2;
        ViewParent parent;
        ViewParent parent2;
        Log.d(this.this$0.getTAG(), "onMiuiClockInfoChange: " + (clockBean != null ? clockBean.getTemplateId() : null));
        if (clockBean != null) {
            if (Intrinsics.areEqual(this.this$0.mCurrentClockTemplateId, clockBean.getTemplateId()) && this.this$0.mClockViewStyle == clockBean.getStyle()) {
                return;
            }
            ViewParent parent3 = this.this$0.getParent();
            ViewParent parent4 = (parent3 == null || (parent = parent3.getParent()) == null || (parent2 = parent.getParent()) == null) ? null : parent2.getParent();
            AODView aODView = parent4 instanceof AODView ? (AODView) parent4 : null;
            if (aODView != null && (host3 = aODView.getHost()) != null && (service2 = host3.getService()) != null && (dozeMachine2 = service2.getDozeMachine()) != null) {
                dozeMachine2.preDestroyForChange();
            }
            if (aODView != null && (host2 = aODView.getHost()) != null) {
                host2.updateClockTypeForChange();
            }
            if (aODView != null && (host = aODView.getHost()) != null && (service = host.getService()) != null && (dozeMachine = service.getDozeMachine()) != null) {
                dozeMachine.rebindForChange();
            }
            if (aODView != null) {
                aODView.handleUpdateIcons(true);
            }
        }
    }
}
