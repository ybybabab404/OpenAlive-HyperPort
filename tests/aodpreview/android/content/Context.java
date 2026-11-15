package android.content;

public class Context {
    private final ContentResolver resolver = new ContentResolver();

    public ContentResolver getContentResolver() { return resolver; }
}
