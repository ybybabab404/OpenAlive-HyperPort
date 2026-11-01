package com.miui.miwallpaper.linkage;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/* JADX INFO: loaded from: classes2.dex */
public class LinkageVideoParam {

    @SerializedName("aodToHomeAnim")
    SpringAnimation aodToHomeAnim;

    @SerializedName("aodToLockAnim")
    SpringAnimation aodToLockAnim;

    @SerializedName("homeToAodAnim")
    SpringAnimation homeToAodAnim;

    @SerializedName("homeToLockAnim")
    SpringAnimation homeToLockAnim;

    @SerializedName("lockFrame")
    int lockFrame;

    @SerializedName("lockToAodAnim")
    SpringAnimation lockToAodAnim;

    @SerializedName("lockToHomeAnim")
    SpringAnimation lockToHomeAnim;

    public String toString() {
        return new Gson().toJson(this);
    }
}
