package miuix.internal.log.util;

import java.io.IOException;
import java.util.Formatter;
import java.util.Locale;

/* JADX INFO: loaded from: classes2.dex */
public class AppendableFormatter {
    public AppendableWrapper mAppendableWrapper;
    public Formatter mFormatter;

    public static class AppendableWrapper implements Appendable {
        public Appendable iAppendable;

        public AppendableWrapper() {
        }

        @Override // java.lang.Appendable
        public Appendable append(char c) throws IOException {
            this.iAppendable.append(c);
            return this;
        }

        @Override // java.lang.Appendable
        public Appendable append(CharSequence charSequence) throws IOException {
            this.iAppendable.append(charSequence);
            return this;
        }

        @Override // java.lang.Appendable
        public Appendable append(CharSequence charSequence, int i, int i2) throws IOException {
            this.iAppendable.append(charSequence, i, i2);
            return this;
        }

        public void setAppendable(Appendable appendable) {
            this.iAppendable = appendable;
        }
    }

    public AppendableFormatter() {
        this(Locale.US);
    }

    public AppendableFormatter(Locale locale) {
        this.mAppendableWrapper = new AppendableWrapper();
        this.mFormatter = new Formatter(this.mAppendableWrapper, locale);
    }

    public AppendableFormatter format(Locale locale, String str, Object... objArr) {
        this.mFormatter.format(locale, str, objArr);
        return this;
    }

    public void setAppendable(Appendable appendable) {
        this.mAppendableWrapper.setAppendable(appendable);
    }
}
