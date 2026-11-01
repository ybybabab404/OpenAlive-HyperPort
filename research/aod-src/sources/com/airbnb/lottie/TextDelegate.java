package com.airbnb.lottie;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TextDelegate {
    public boolean cacheText;
    public final Map stringMap;

    public String getText(String str) {
        return str;
    }

    public String getText(String str, String str2) {
        return getText(str2);
    }

    public final String getTextInternal(String str, String str2) {
        if (this.cacheText && this.stringMap.containsKey(str2)) {
            return (String) this.stringMap.get(str2);
        }
        String text = getText(str, str2);
        if (this.cacheText) {
            this.stringMap.put(str2, text);
        }
        return text;
    }
}
