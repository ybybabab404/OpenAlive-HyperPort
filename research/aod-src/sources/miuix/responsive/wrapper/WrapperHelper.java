package miuix.responsive.wrapper;

import android.view.LayoutInflater;
import androidx.core.view.LayoutInflaterCompat;
import java.lang.reflect.InvocationTargetException;
import miuix.reflect.ReflectionHelper;

/* JADX INFO: loaded from: classes2.dex */
public class WrapperHelper {
    public static void setFactory2(LayoutInflater layoutInflater, LayoutInflater.Factory2 factory2) {
        LayoutInflater.Factory2 factory3 = layoutInflater.getFactory2();
        if (factory3 == null && layoutInflater.getFactory() == null) {
            LayoutInflaterCompat.setFactory2(layoutInflater, factory2);
            return;
        }
        if (factory3 instanceof Factory2Wrapper) {
            ((Factory2Wrapper) layoutInflater.getFactory2()).setOriginFactory2(factory2);
            return;
        }
        if (factory2 instanceof Factory2Wrapper) {
            ((Factory2Wrapper) factory2).setOriginFactory2(factory3);
        }
        try {
            ReflectionHelper.setFieldValue(layoutInflater.getClass().getSuperclass(), layoutInflater, "mFactory2", factory2);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
