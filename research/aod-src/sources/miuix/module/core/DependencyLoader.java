package miuix.module.core;

import android.util.Log;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes2.dex */
class DependencyLoader extends ClassLoader {
    public Map mDependencies;
    public Map mDependencyClasses;
    public Set mIgnoredClasses;

    @Override // java.lang.ClassLoader
    public Class loadClass(String str, boolean z) {
        Log.d("DependencyLoader", "loading class: " + str);
        Class cls = (Class) this.mDependencyClasses.get(str);
        if (cls != null) {
            return cls;
        }
        ClassLoader classLoader = (ClassLoader) this.mDependencies.get(str);
        if (classLoader != null) {
            return classLoader.loadClass(str);
        }
        if (this.mIgnoredClasses.contains(str)) {
            return null;
        }
        return super.loadClass(str, z);
    }
}
