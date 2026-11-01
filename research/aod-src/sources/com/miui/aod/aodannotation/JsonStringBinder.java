package com.miui.aod.aodannotation;

import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes.dex */
public class JsonStringBinder {
    public static void bind(Object obj) {
        if (obj == null) {
            return;
        }
        try {
            Class<?> cls = obj.getClass();
            Class.forName(cls.getCanonicalName() + "_JsonBinding").getConstructor(cls).newInstance(obj);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
