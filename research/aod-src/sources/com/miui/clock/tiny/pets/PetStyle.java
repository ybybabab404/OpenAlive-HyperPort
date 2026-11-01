package com.miui.clock.tiny.pets;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class PetStyle {
    public static final Map PETS_NAMES = new HashMap<Integer, String>() { // from class: com.miui.clock.tiny.pets.PetStyle.1
        {
            put(1, "dewen");
            put(2, "shuitun");
            put(3, "shuita");
            put(4, "bixiong");
            put(5, "xianluo");
            put(6, "heibao");
        }
    };
    public int colonColor;
    public int timeColor;

    public PetStyle(int i, int i2) {
        this.timeColor = i;
        this.colonColor = i2;
    }
}
