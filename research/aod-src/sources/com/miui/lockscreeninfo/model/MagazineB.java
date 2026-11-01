package com.miui.lockscreeninfo.model;

/* JADX INFO: loaded from: classes2.dex */
public class MagazineB extends SignatureInfo {
    public MagazineB(String str) {
        super(str);
    }

    @Override // com.miui.lockscreeninfo.model.SignatureInfo
    public void initData() {
        this.lines = this.verticalMode ? 4 : 3;
        this.fontFamily = "miclock-inter-scaled-medium";
    }
}
