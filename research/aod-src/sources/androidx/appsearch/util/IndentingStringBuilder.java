package androidx.appsearch.util;

import androidx.annotation.RestrictTo;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public class IndentingStringBuilder {
    public final StringBuilder mStringBuilder = new StringBuilder();
    public boolean mIndentNext = false;
    public int mIndentLevel = 0;

    public IndentingStringBuilder append(Object obj) {
        applyIndentToString(obj.toString());
        return this;
    }

    public IndentingStringBuilder append(String str) {
        applyIndentToString(str);
        return this;
    }

    public final void applyIndent() {
        for (int i = 0; i < this.mIndentLevel; i++) {
            this.mStringBuilder.append("  ");
        }
    }

    public final void applyIndentToString(String str) {
        int iIndexOf = str.indexOf("\n");
        if (iIndexOf == 0) {
            this.mStringBuilder.append("\n");
            this.mIndentNext = true;
            if (str.length() > 1) {
                applyIndentToString(str.substring(iIndexOf + 1));
                return;
            }
            return;
        }
        if (iIndexOf < 1) {
            if (this.mIndentNext) {
                applyIndent();
                this.mIndentNext = false;
            }
            this.mStringBuilder.append(str);
            return;
        }
        applyIndentToString(str.substring(0, iIndexOf));
        this.mStringBuilder.append("\n");
        this.mIndentNext = true;
        int i = iIndexOf + 1;
        if (str.length() > i) {
            applyIndentToString(str.substring(i));
        }
    }

    public IndentingStringBuilder decreaseIndentLevel() {
        int i = this.mIndentLevel;
        if (i == 0) {
            throw new IllegalStateException("Cannot set indent level below 0.");
        }
        this.mIndentLevel = i - 1;
        return this;
    }

    public IndentingStringBuilder increaseIndentLevel() {
        this.mIndentLevel++;
        return this;
    }

    public String toString() {
        return this.mStringBuilder.toString();
    }
}
