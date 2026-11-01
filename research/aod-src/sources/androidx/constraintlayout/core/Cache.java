package androidx.constraintlayout.core;

import com.miui.keyguard.editor.view.AutoBottomSheetKt;

/* JADX INFO: loaded from: classes.dex */
public class Cache {
    public Pools.Pool optimizedArrayRowPool = new Pools.SimplePool(AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL);
    public Pools.Pool arrayRowPool = new Pools.SimplePool(AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL);
    public Pools.Pool solverVariablePool = new Pools.SimplePool(AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL);
    public SolverVariable[] mIndexedVariables = new SolverVariable[32];
}
