package androidx.appsearch.app;

import androidx.annotation.AnyThread;
import androidx.annotation.RestrictTo;
import androidx.appsearch.exceptions.AppSearchException;
import androidx.core.util.Preconditions;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@AnyThread
@RestrictTo
public final class DocumentClassFactoryRegistry {
    public static volatile DocumentClassFactoryRegistry sInstance;
    public final Map mFactories = new HashMap();

    public static DocumentClassFactoryRegistry getInstance() {
        if (sInstance == null) {
            synchronized (DocumentClassFactoryRegistry.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new DocumentClassFactoryRegistry();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return sInstance;
    }

    public DocumentClassFactory getOrCreateFactory(Class cls) throws AppSearchException {
        DocumentClassFactory documentClassFactory;
        Preconditions.checkNotNull(cls);
        synchronized (this) {
            documentClassFactory = (DocumentClassFactory) this.mFactories.get(cls);
        }
        if (documentClassFactory != null) {
            return documentClassFactory;
        }
        DocumentClassFactory documentClassFactoryLoadFactoryByReflection = loadFactoryByReflection(cls);
        synchronized (this) {
            try {
                DocumentClassFactory documentClassFactory2 = (DocumentClassFactory) this.mFactories.get(cls);
                if (documentClassFactory2 == null) {
                    this.mFactories.put(cls, documentClassFactoryLoadFactoryByReflection);
                } else {
                    documentClassFactoryLoadFactoryByReflection = documentClassFactory2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return documentClassFactoryLoadFactoryByReflection;
    }

    public final DocumentClassFactory loadFactoryByReflection(Class cls) throws AppSearchException {
        String str;
        Package r0 = cls.getPackage();
        String canonicalName = cls.getCanonicalName();
        if (canonicalName == null) {
            throw new AppSearchException(2, "Failed to find simple name for document class \"" + cls + "\". Perhaps it is anonymous?");
        }
        if (r0 != null) {
            str = r0.getName() + ".";
            canonicalName = canonicalName.substring(str.length()).replace(".", "$$__");
        } else {
            str = "";
        }
        String str2 = str + "$$__AppSearch__" + canonicalName;
        try {
            try {
                return (DocumentClassFactory) Class.forName(str2).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (Exception e) {
                throw new AppSearchException(2, "Failed to construct document class converter \"" + str2 + "\"", e);
            }
        } catch (ClassNotFoundException e2) {
            Class superclass = cls.getSuperclass();
            Class<?>[] interfaces = cls.getInterfaces();
            if (superclass == Object.class) {
                superclass = null;
            }
            int length = interfaces.length;
            if (superclass != null) {
                length++;
            }
            if (length == 1) {
                return superclass != null ? loadFactoryByReflection(superclass) : loadFactoryByReflection(interfaces[0]);
            }
            String str3 = "Failed to find document class converter \"" + str2 + "\". Perhaps the annotation processor was not run or the class was proguarded out?";
            if (length > 1) {
                str3 = str3 + " Or, this class may not have been annotated with @Document, and there is an ambiguity to determine a unique @Document annotated parent class/interface.";
            }
            throw new AppSearchException(2, str3, e2);
        }
    }
}
