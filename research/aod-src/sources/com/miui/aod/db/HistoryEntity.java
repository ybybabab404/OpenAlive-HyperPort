package com.miui.aod.db;

import android.content.ContentValues;
import android.database.Cursor;
import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes.dex */
public class HistoryEntity {

    @SerializedName("cateName")
    private String cateName;

    @SerializedName("id")
    private Long id;

    @SerializedName("jsonParameter")
    private String jsonParameter;

    @SerializedName("timeStamp")
    private Long timeStamp;

    public HistoryEntity() {
    }

    public HistoryEntity(Long l, Long l2, String str, String str2) {
        this.id = l;
        this.timeStamp = l2;
        this.cateName = str;
        this.jsonParameter = str2;
    }

    public static HistoryEntity translateFromCursor(Cursor cursor) {
        return new HistoryEntity(Long.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow("_id"))), Long.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow("TIMESTAMP"))), cursor.getString(cursor.getColumnIndexOrThrow("CATE_NAME")), cursor.getString(cursor.getColumnIndexOrThrow("JSON_PARAMETER")));
    }

    public static HistoryEntity translateToThemeEntity(ContentValues contentValues) {
        return new HistoryEntity(null, contentValues.getAsLong("TIMESTAMP"), contentValues.getAsString("CATE_NAME"), contentValues.getAsString("JSON_PARAMETER"));
    }

    public String getCateName() {
        return this.cateName;
    }

    public Long getId() {
        return this.id;
    }

    public String getJsonParameter() {
        return this.jsonParameter;
    }

    public Long getTimeStamp() {
        return this.timeStamp;
    }

    public boolean isContentSame(HistoryEntity historyEntity) {
        if (this.cateName.equals(historyEntity.cateName)) {
            return this.jsonParameter.equals(historyEntity.jsonParameter);
        }
        return false;
    }

    public void setCateName(String str) {
        this.cateName = str;
    }

    public void setId(Long l) {
        this.id = l;
    }

    public void setJsonParameter(String str) {
        this.jsonParameter = str;
    }

    public void setTimeStamp(Long l) {
        this.timeStamp = l;
    }

    public ContentValues translateToContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("TIMESTAMP", this.timeStamp);
        contentValues.put("CATE_NAME", this.cateName);
        contentValues.put("JSON_PARAMETER", this.jsonParameter);
        return contentValues;
    }
}
