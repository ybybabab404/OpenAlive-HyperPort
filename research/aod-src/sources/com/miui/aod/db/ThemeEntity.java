package com.miui.aod.db;

import android.content.ContentValues;
import com.google.gson.annotations.SerializedName;
import com.xiaomi.onetrack.api.bf;

/* JADX INFO: loaded from: classes.dex */
public class ThemeEntity {
    public Long id;

    @SerializedName("jsonParameter")
    private String jsonParameter;

    @SerializedName(bf.a)
    private String name;

    @SerializedName("themeDescription")
    private String themeDescription;

    @SerializedName("themeDirName")
    private String themeDirName;

    @SerializedName("themeName")
    private String themeName;

    @SerializedName("typeName")
    private String typeName;

    public ThemeEntity() {
    }

    public ThemeEntity(Long l, String str, String str2, String str3, String str4, String str5, String str6) {
        this.id = l;
        this.name = str;
        this.typeName = str2;
        this.jsonParameter = str3;
        this.themeDirName = str4;
        this.themeName = str5;
        this.themeDescription = str6;
    }

    public static ThemeEntity translateToThemeEntity(ContentValues contentValues) {
        return new ThemeEntity(null, contentValues.getAsString("NAME"), contentValues.getAsString("TYPE_NAME"), contentValues.getAsString("JSON_PARAMETER"), contentValues.getAsString("THEME_DIR_NAME"), contentValues.getAsString("THEME_NAME"), contentValues.getAsString("THEME_DESCRIPTION"));
    }

    public Long getId() {
        return this.id;
    }

    public String getJsonParameter() {
        return this.jsonParameter;
    }

    public String getName() {
        return this.name;
    }

    public String getThemeDescription() {
        return this.themeDescription;
    }

    public String getThemeDirName() {
        return this.themeDirName;
    }

    public String getThemeName() {
        return this.themeName;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public void setId(Long l) {
        this.id = l;
    }

    public void setJsonParameter(String str) {
        this.jsonParameter = str;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setThemeDescription(String str) {
        this.themeDescription = str;
    }

    public void setThemeDirName(String str) {
        this.themeDirName = str;
    }

    public void setThemeName(String str) {
        this.themeName = str;
    }

    public void setTypeName(String str) {
        this.typeName = str;
    }

    public ContentValues translateToContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", this.name);
        contentValues.put("TYPE_NAME", this.typeName);
        contentValues.put("JSON_PARAMETER", this.jsonParameter);
        contentValues.put("THEME_DIR_NAME", this.themeDirName);
        contentValues.put("THEME_NAME", this.themeName);
        contentValues.put("THEME_DESCRIPTION", this.themeDescription);
        return contentValues;
    }
}
