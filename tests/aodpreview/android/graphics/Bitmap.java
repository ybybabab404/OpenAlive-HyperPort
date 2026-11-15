package android.graphics;

/** Minimal immutable bitmap used by the preview hooks. */
public class Bitmap {
    public static Bitmap lastScaledSource;
    public static int lastScaledWidth;
    public static int lastScaledHeight;
    public static int scaledCalls;

    private final int width;
    private final int height;
    private boolean recycled;

    public Bitmap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isRecycled() { return recycled; }
    public void recycle() { recycled = true; }

    public static Bitmap createScaledBitmap(Bitmap source, int width, int height, boolean filter) {
        scaledCalls++;
        lastScaledSource = source;
        lastScaledWidth = width;
        lastScaledHeight = height;
        return new Bitmap(width, height);
    }
}
