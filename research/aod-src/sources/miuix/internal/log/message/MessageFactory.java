package miuix.internal.log.message;

import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.HashMap;

/* JADX INFO: loaded from: classes2.dex */
public class MessageFactory {
    public static HashMap mCacheMap = new HashMap();

    public static class MessageCache<T extends Message> {
        public Message[] iCache;
        public Constructor iConstructor;
        public int iPointer = 0;

        public MessageCache(Constructor constructor, Message[] messageArr) {
            this.iConstructor = constructor;
            this.iCache = messageArr;
        }

        public static MessageCache createInstance(Class cls, int i) {
            try {
                return new MessageCache(cls.getConstructor(new Class[0]), (Message[]) Array.newInstance((Class<?>) cls, i));
            } catch (NoSuchMethodException unused) {
                throw new IllegalArgumentException("Class " + cls.getName() + " must have a public empty constructor");
            }
        }

        public final Message create() {
            try {
                return (Message) this.iConstructor.newInstance(new Object[0]);
            } catch (Exception e) {
                Log.e("MessageFactory", "Fail to construct new instance of class: " + this.iConstructor.getDeclaringClass().getName(), e);
                return null;
            }
        }

        public synchronized Message obtain() {
            int i = this.iPointer;
            if (i <= 0) {
                return create();
            }
            int i2 = i - 1;
            this.iPointer = i2;
            Message message = this.iCache[i2];
            message.prepareForReuse();
            return message;
        }

        public synchronized void recycle(Message message) {
            int i = this.iPointer;
            Message[] messageArr = this.iCache;
            if (i < messageArr.length) {
                messageArr[i] = message;
                this.iPointer = i + 1;
            }
        }
    }

    public static Message obtain(Class cls) {
        MessageCache messageCacheCreateInstance = (MessageCache) mCacheMap.get(cls);
        if (messageCacheCreateInstance == null) {
            messageCacheCreateInstance = MessageCache.createInstance(cls, 10);
            mCacheMap.put(cls, messageCacheCreateInstance);
        }
        return messageCacheCreateInstance.obtain();
    }

    public static void recycle(Message message) {
        MessageCache messageCache = (MessageCache) mCacheMap.get(message.getClass());
        if (messageCache != null) {
            messageCache.recycle(message);
        }
    }
}
