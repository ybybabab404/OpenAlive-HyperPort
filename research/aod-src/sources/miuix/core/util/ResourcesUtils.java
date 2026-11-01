package miuix.core.util;

import android.content.res.AssetManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes2.dex */
public class ResourcesUtils {
    public static Method ASSET_MANAGER_ADD_ASSET_PATH;
    public static Constructor ASSET_MANAGER_CONSTRUCTOR;

    static {
        try {
            ASSET_MANAGER_ADD_ASSET_PATH = AssetManager.class.getMethod("addAssetPath", String.class);
            ASSET_MANAGER_CONSTRUCTOR = AssetManager.class.getConstructor(new Class[0]);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
