package miuix.appcompat.internal.util;

/* JADX INFO: loaded from: classes2.dex */
final class FloatingAppliedState {
    public final boolean floatingMode;
    public final boolean isLightTheme;
    public final boolean materialEnabled;

    public FloatingAppliedState(boolean z, boolean z2, boolean z3) {
        this.floatingMode = z;
        this.isLightTheme = z2;
        this.materialEnabled = z3;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FloatingAppliedState)) {
            return false;
        }
        FloatingAppliedState floatingAppliedState = (FloatingAppliedState) obj;
        return this.floatingMode == floatingAppliedState.floatingMode && this.isLightTheme == floatingAppliedState.isLightTheme && this.materialEnabled == floatingAppliedState.materialEnabled;
    }

    public int hashCode() {
        return ((((this.floatingMode ? 1 : 0) * 31) + (this.isLightTheme ? 1 : 0)) * 31) + (this.materialEnabled ? 1 : 0);
    }
}
