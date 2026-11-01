package com.miui.lockscreeninfo.model;

/* JADX INFO: loaded from: classes2.dex */
public class MagazineA extends SignatureInfo {
    public MagazineA(String str) {
        super(str);
    }

    @Override // com.miui.lockscreeninfo.model.SignatureInfo
    public void initData() {
        this.lines = this.verticalMode ? 4 : 3;
        this.fontFamily = "miclock-qinghe-75w";
    }
}
