package androidx.appsearch.ast;

import androidx.appsearch.app.ExperimentalAppSearchApi;
import androidx.appsearch.flags.FlaggedApi;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
@ExperimentalAppSearchApi
@FlaggedApi
public final class TextNode implements Node {
    public boolean mPrefix;
    public String mValue;
    public boolean mVerbatim;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && TextNode.class == obj.getClass()) {
            TextNode textNode = (TextNode) obj;
            if (this.mPrefix == textNode.mPrefix && this.mVerbatim == textNode.mVerbatim && Objects.equals(this.mValue, textNode.mValue)) {
                return true;
            }
        }
        return false;
    }

    public final void escapeString(StringBuilder sb) {
        for (int i = 0; i < this.mValue.length(); i++) {
            char cCharAt = this.mValue.charAt(i);
            if ((!this.mVerbatim && isSpecialCharacter(cCharAt)) || cCharAt == '\"') {
                sb.append('\\');
            } else if (!this.mVerbatim && isLatinLetter(cCharAt) && Character.isUpperCase(cCharAt)) {
                cCharAt = Character.toLowerCase(cCharAt);
            }
            sb.append(cCharAt);
        }
    }

    public int hashCode() {
        return Objects.hash(this.mValue, Boolean.valueOf(this.mPrefix), Boolean.valueOf(this.mVerbatim));
    }

    public final boolean isLatinLetter(char c) {
        if (c < 'A' || c > 'Z') {
            return c >= 'a' && c <= 'z';
        }
        return true;
    }

    public final boolean isSpecialCharacter(char c) {
        if (c == '\"' || c == '&' || c == ':' || c == '\\' || c == '|') {
            return true;
        }
        switch (c) {
            case '(':
            case ')':
            case '*':
                return true;
            default:
                switch (c) {
                    case ',':
                    case '-':
                    case '.':
                        return true;
                    default:
                        switch (c) {
                            case '<':
                            case '=':
                            case '>':
                                return true;
                            default:
                                return false;
                        }
                }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        if (this.mVerbatim) {
            sb.append('\"');
        }
        escapeString(sb);
        if (this.mVerbatim) {
            sb.append('\"');
        }
        if (this.mPrefix) {
            sb.append('*');
        }
        sb.append(')');
        return sb.toString();
    }
}
