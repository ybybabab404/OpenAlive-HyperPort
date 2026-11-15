package android.content;

public class Context {
    private final ContentResolver resolver = new ContentResolver();
    private String packageName = "test";

    public ContentResolver getContentResolver() { return resolver; }
    public String getPackageName() { return packageName; }

    public void setPackageNameForTest(String value) { packageName = value; }
}
