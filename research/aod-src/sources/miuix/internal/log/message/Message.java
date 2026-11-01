package miuix.internal.log.message;

/* JADX INFO: loaded from: classes2.dex */
public interface Message {
    void format(Appendable appendable);

    Throwable getThrowable();

    void prepareForReuse();

    void recycle();
}
