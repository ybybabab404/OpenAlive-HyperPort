package com.miui.aod.other.step;

import android.database.Cursor;
import com.xiaomi.onetrack.g.a;

/* JADX INFO: loaded from: classes.dex */
public final class StepBean {
    public final String code;
    public final String content;
    public final String icon;
    public final String privacy_grant_uri;
    public final String setup_uri;
    public final String title;
    public final String unit;

    public StepBean(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this.code = str;
        this.title = str2;
        this.content = str3;
        this.unit = str4;
        this.icon = str5;
        this.setup_uri = str6;
        this.privacy_grant_uri = str7;
    }

    public static StepBean createStepBean(Cursor cursor) {
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(a.d);
        String string = columnIndex >= 0 ? cursor.getString(columnIndex) : null;
        int columnIndex2 = cursor.getColumnIndex("title");
        String string2 = columnIndex2 >= 0 ? cursor.getString(columnIndex2) : null;
        int columnIndex3 = cursor.getColumnIndex("content");
        String string3 = columnIndex3 >= 0 ? cursor.getString(columnIndex3) : null;
        int columnIndex4 = cursor.getColumnIndex("unit");
        String string4 = columnIndex4 >= 0 ? cursor.getString(columnIndex4) : null;
        int columnIndex5 = cursor.getColumnIndex("icon");
        String string5 = columnIndex5 >= 0 ? cursor.getString(columnIndex5) : null;
        int columnIndex6 = cursor.getColumnIndex("setup_uri");
        String string6 = columnIndex6 >= 0 ? cursor.getString(columnIndex6) : null;
        int columnIndex7 = cursor.getColumnIndex("privacy_grant_uri");
        return new StepBean(string, string2, string3, string4, string5, string6, columnIndex7 >= 0 ? cursor.getString(columnIndex7) : null);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof StepBean)) {
            return false;
        }
        StepBean stepBean = (StepBean) obj;
        String str = this.code;
        if (str == null && stepBean.code != null) {
            return false;
        }
        if (str != null && !str.equals(stepBean.code)) {
            return false;
        }
        String str2 = this.title;
        if (str2 == null && stepBean.title != null) {
            return false;
        }
        if (str2 != null && !str2.equals(stepBean.title)) {
            return false;
        }
        String str3 = this.content;
        if (str3 == null && stepBean.content != null) {
            return false;
        }
        if (str3 != null && !str3.equals(stepBean.content)) {
            return false;
        }
        String str4 = this.unit;
        if (str4 == null && stepBean.unit != null) {
            return false;
        }
        if (str4 != null && !str4.equals(stepBean.unit)) {
            return false;
        }
        String str5 = this.icon;
        if (str5 == null && stepBean.icon != null) {
            return false;
        }
        if (str5 != null && !str5.equals(stepBean.icon)) {
            return false;
        }
        String str6 = this.privacy_grant_uri;
        if (str6 == null && stepBean.privacy_grant_uri != null) {
            return false;
        }
        if (str6 != null && !str6.equals(stepBean.privacy_grant_uri)) {
            return false;
        }
        String str7 = this.setup_uri;
        if (str7 != null || stepBean.setup_uri == null) {
            return str7 == null || str7.equals(stepBean.setup_uri);
        }
        return false;
    }

    public String toBeanString() {
        return "code:" + this.code + ",title:" + this.title + ",content:" + this.content + ",unit:" + this.unit + ",icon:" + this.icon + ",setup_uri:" + this.setup_uri + ",privacy_grant_uri:" + this.privacy_grant_uri;
    }
}
