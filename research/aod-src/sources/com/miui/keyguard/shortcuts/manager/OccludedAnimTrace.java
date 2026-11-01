package com.miui.keyguard.shortcuts.manager;

import android.os.Trace;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: TraceTagManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class OccludedAnimTrace {
    public static final OccludedAnimTrace INSTANCE = new OccludedAnimTrace();
    public static final int TRACE_COOKIE_OCCLUDED_MOVE = 1114761323;
    public static final int TRACE_COOKIE_OCCLUDED_FULL = 1114558249;
    public static final int TRACE_COOKIE_OCCLUDED_DISMISS = 1595639728;
    public static final int TRACE_COOKIE_OCCLUDED_BACK = 1114419585;
    public static final int TRACE_COOKIE_OCCLUDED_FULL_HIDE_FOR_LEASH = -1026333685;
    public static final int TRACE_COOKIE_OCCLUDED_LEASH_SHOW = 1525107360;
    public static final int TRACE_COOKIE_OCCLUDED_SHADE_HIDE_WITH_MOVE = -391818868;
    public static final AtomicBoolean traceStartedForLeashShow = new AtomicBoolean(false);
    public static final AtomicBoolean traceStartedForFullAnim = new AtomicBoolean(false);
    public static final AtomicBoolean traceStartedForBackAnim = new AtomicBoolean(false);
    public static final AtomicBoolean traceStartedForMove = new AtomicBoolean(false);
    public static final AtomicBoolean traceStartedForDismissAnim = new AtomicBoolean(false);
    public static final AtomicBoolean traceStartedForShadeHideWithMove = new AtomicBoolean(false);

    public final void endForShadeHideWithMove() {
        if (traceStartedForShadeHideWithMove.getAndSet(false)) {
            Trace.endAsyncSection("KeyguardAnim-ShortcutOcc-shadeHideWithMove", TRACE_COOKIE_OCCLUDED_SHADE_HIDE_WITH_MOVE);
        }
    }

    public final void endTraceForBackAnim() {
        if (traceStartedForBackAnim.getAndSet(false)) {
            Trace.endAsyncSection("KeyguardAnim-ShortcutOcc-back", TRACE_COOKIE_OCCLUDED_BACK);
        }
    }

    public final void endTraceForDismissAnim() {
        if (traceStartedForDismissAnim.getAndSet(false)) {
            Trace.endAsyncSection("KeyguardAnim-ShortcutOcc-dismiss", TRACE_COOKIE_OCCLUDED_DISMISS);
        }
    }

    public final void endTraceForFullAnim() {
        if (traceStartedForFullAnim.getAndSet(false)) {
            Trace.endAsyncSection("KeyguardAnim-ShortcutOcc-full", TRACE_COOKIE_OCCLUDED_FULL);
        }
    }

    public final void endTraceForLeashShow() {
        if (traceStartedForLeashShow.getAndSet(false)) {
            Trace.endAsyncSection("KeyguardAnim-ShortcutOcc-leashShow", TRACE_COOKIE_OCCLUDED_LEASH_SHOW);
            Trace.endAsyncSection("KeyguardAnim-ShortcutOcc-fullHide", TRACE_COOKIE_OCCLUDED_FULL_HIDE_FOR_LEASH);
        }
    }

    public final void endTraceForMove() {
        if (traceStartedForMove.getAndSet(false)) {
            Trace.endAsyncSection("KeyguardAnim-ShortcutOcc-move", TRACE_COOKIE_OCCLUDED_MOVE);
        }
    }

    public final void startForBackAnim() {
        if (traceStartedForBackAnim.getAndSet(true)) {
            return;
        }
        Trace.beginAsyncSection("KeyguardAnim-ShortcutOcc-back", TRACE_COOKIE_OCCLUDED_BACK);
    }

    public final void startForDismissAnim() {
        if (traceStartedForDismissAnim.getAndSet(true)) {
            return;
        }
        Trace.beginAsyncSection("KeyguardAnim-ShortcutOcc-dismiss", TRACE_COOKIE_OCCLUDED_DISMISS);
    }

    public final void startForFullAnim() {
        if (traceStartedForFullAnim.getAndSet(true)) {
            return;
        }
        Trace.beginAsyncSection("KeyguardAnim-ShortcutOcc-full", TRACE_COOKIE_OCCLUDED_FULL);
    }

    public final void startForLeashShow() {
        if (traceStartedForLeashShow.getAndSet(true)) {
            return;
        }
        Trace.beginAsyncSection("KeyguardAnim-ShortcutOcc-leashShow", TRACE_COOKIE_OCCLUDED_LEASH_SHOW);
        Trace.beginAsyncSection("KeyguardAnim-ShortcutOcc-fullHide", TRACE_COOKIE_OCCLUDED_FULL_HIDE_FOR_LEASH);
    }

    public final void startForMove() {
        if (traceStartedForMove.getAndSet(true)) {
            return;
        }
        Trace.beginAsyncSection("KeyguardAnim-ShortcutOcc-move", TRACE_COOKIE_OCCLUDED_MOVE);
    }

    public final void startForShadeHideWithMove() {
        if (traceStartedForShadeHideWithMove.getAndSet(true)) {
            return;
        }
        Trace.beginAsyncSection("KeyguardAnim-ShortcutOcc-shadeHideWithMove", TRACE_COOKIE_OCCLUDED_SHADE_HIDE_WITH_MOVE);
    }
}
