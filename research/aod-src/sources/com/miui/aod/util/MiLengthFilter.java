package com.miui.aod.util;

import android.text.InputFilter;
import android.text.Spanned;
import com.miui.aod.Utils;

/* JADX INFO: loaded from: classes.dex */
public abstract class MiLengthFilter implements InputFilter {
    private static final int CJK_TIMES_E = 3;
    private final int mMax;

    public MiLengthFilter(int i) {
        this.mMax = i;
    }

    @Override // android.text.InputFilter
    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        int realCount = getRealCount(charSequence, i, i2);
        int realCount2 = this.mMax - (getRealCount(spanned, 0, spanned.length()) - getRealCount(spanned, i3, i4));
        if (realCount2 <= 0) {
            onExccedLimit(true);
            return "";
        }
        if (realCount2 >= realCount) {
            onExccedLimit(false);
            return null;
        }
        onExccedLimit(true);
        int keepCharCount = getKeepCharCount(charSequence, i, i2, realCount2);
        if (keepCharCount <= 0) {
            return "";
        }
        int i5 = keepCharCount + i;
        return (Character.isHighSurrogate(charSequence.charAt(i5 + (-1))) && (i5 = i5 + (-1)) == i) ? "" : charSequence.subSequence(i, i5);
    }

    public final int getKeepCharCount(CharSequence charSequence, int i, int i2, int i3) {
        int i4 = 0;
        int i5 = 0;
        while (i < i2) {
            int iCodePointAt = Character.codePointAt(charSequence, i);
            int iCharCount = Character.charCount(iCodePointAt);
            if (Utils.isCJKCharacter(iCodePointAt)) {
                i4 += 3;
            } else if (!Utils.isControlCharactor(iCodePointAt)) {
                i4++;
            }
            if (i4 == i3) {
                return i5;
            }
            if (i4 > i3) {
                return i5 - 1;
            }
            i += iCharCount;
            i5++;
        }
        return 0;
    }

    public int getMax() {
        return this.mMax;
    }

    public final int getRealCount(CharSequence charSequence, int i, int i2) {
        int i3 = 0;
        while (i < i2) {
            int iCodePointAt = Character.codePointAt(charSequence, i);
            int iCharCount = Character.charCount(iCodePointAt);
            if (Utils.isCJKCharacter(iCodePointAt)) {
                i3 += 3;
            } else if (!Utils.isControlCharactor(iCodePointAt)) {
                i3++;
            }
            i += iCharCount;
        }
        return i3;
    }

    public abstract void onExccedLimit(boolean z);
}
