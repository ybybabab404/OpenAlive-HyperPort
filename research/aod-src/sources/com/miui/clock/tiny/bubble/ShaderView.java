package com.miui.clock.tiny.bubble;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RuntimeShader;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import androidx.appcompat.widget.AppCompatImageView;
import com.miui.clock.tiny.Util;
import com.miui.clock.tiny.utils.BitmapUtils;
import com.miui.clock.tiny.utils.Task;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class ShaderView extends AppCompatImageView {
    public static String glslCode = "";
    public Context mContext;
    public Drawable mDrawable;
    public int mHexColor;
    public RuntimeShader mRuntimeShader;
    public float[][] palette;
    public int style;

    public ShaderView(Context context) {
        this(context, null);
    }

    public ShaderView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ShaderView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.style = 2;
        this.palette = new float[][]{new float[]{0.8392157f, 0.8980392f, 0.92941177f, 1.0f}, new float[]{0.84313726f, 0.84313726f, 0.93333334f, 1.0f}, new float[]{0.8862745f, 0.8352941f, 0.9254902f, 1.0f}, new float[]{0.92156863f, 0.83137256f, 0.9098039f, 1.0f}, new float[]{0.92156863f, 0.8352941f, 0.85490197f, 1.0f}, new float[]{0.9254902f, 0.8666667f, 0.83137256f, 1.0f}, new float[]{0.92156863f, 0.91764706f, 0.83137256f, 1.0f}, new float[]{0.8745098f, 0.92156863f, 0.8352941f, 1.0f}, new float[]{0.83137256f, 0.92156863f, 0.84313726f, 1.0f}, new float[]{0.8352941f, 0.92156863f, 0.8980392f, 1.0f}};
        this.mContext = context;
        init();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BitmapShader getBitmapShader() {
        Drawable drawable;
        if (getWidth() <= 0 || getHeight() <= 0 || (drawable = this.mDrawable) == null) {
            return null;
        }
        Bitmap bitmapUpdateBitmap = updateBitmap(BitmapUtils.INSTANCE.drawableToBitmap(drawable));
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        return new BitmapShader(bitmapUpdateBitmap, tileMode, tileMode);
    }

    private float getHue() {
        float[] fArrRgb2hsv = rgb2hsv(new float[]{Color.red(this.mHexColor) / 255.0f, Color.green(this.mHexColor) / 255.0f, Color.blue(this.mHexColor) / 255.0f, 1.0f});
        float f = Float.MAX_VALUE;
        int i = 0;
        int i2 = 0;
        while (true) {
            float[][] fArr = this.palette;
            if (i >= fArr.length) {
                float[] fArr2 = fArr[i2];
                return i2 * 0.1f;
            }
            float fAbs = Math.abs(rgb2hsv(fArr[i])[0] - fArrRgb2hsv[0]);
            if (fAbs < f) {
                i2 = i;
                f = fAbs;
            }
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setShader(BitmapShader bitmapShader) {
        RuntimeShader runtimeShader;
        if (bitmapShader == null || (runtimeShader = this.mRuntimeShader) == null) {
            return;
        }
        runtimeShader.setInputShader("uMainTx", bitmapShader);
        this.mRuntimeShader.setFloatUniform("uHue", getHue());
    }

    public final void freshShaderAsync() {
        Task.Companion.run(new Runnable() { // from class: com.miui.clock.tiny.bubble.ShaderView.1
            @Override // java.lang.Runnable
            public void run() {
                final BitmapShader bitmapShader = ShaderView.this.getBitmapShader();
                ShaderView.this.post(new Runnable() { // from class: com.miui.clock.tiny.bubble.ShaderView.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        BitmapShader bitmapShader2 = bitmapShader;
                        if (bitmapShader2 != null) {
                            ShaderView.this.setShader(bitmapShader2);
                            ShaderView.this.invalidate();
                        }
                    }
                });
            }
        });
    }

    @Override // android.widget.ImageView
    public Drawable getDrawable() {
        Drawable drawable = super.getDrawable();
        return drawable != null ? drawable : this.mDrawable;
    }

    public void init() {
        if (TextUtils.isEmpty(glslCode)) {
            try {
                glslCode = loadGLSLFromAssets("shader_code.glsl");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.mRuntimeShader = new RuntimeShader(glslCode);
        setLayerType(2, null);
    }

    public final String loadGLSLFromAssets(String str) throws IOException {
        InputStream inputStreamOpen = this.mContext.getAssets().open(str);
        byte[] bArr = new byte[inputStreamOpen.available()];
        inputStreamOpen.read(bArr);
        inputStreamOpen.close();
        return new String(bArr, "UTF-8");
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        if (!Util.isBubble3DTemplate("bubble", this.style)) {
            super.onDraw(canvas);
            return;
        }
        Paint paint = new Paint();
        paint.setShader(this.mRuntimeShader);
        paint.setAntiAlias(true);
        try {
            canvas.drawRect(0.0f, 0.0f, getWidth(), getHeight(), paint);
        } catch (IllegalArgumentException e) {
            Log.d("ShaderView", "IllegalArgumentException");
            e.printStackTrace();
        }
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        setShader(getBitmapShader());
    }

    public float[] rgb2hsv(float[] fArr) {
        float f;
        float f2;
        float f3;
        float fMax = Math.max(fArr[0], Math.max(fArr[1], fArr[2]));
        float fMin = fMax - Math.min(fArr[0], Math.min(fArr[1], fArr[2]));
        float f4 = 0.0f;
        float f5 = fMax != 0.0f ? fMin / fMax : 0.0f;
        if (f5 != 0.0f) {
            float f6 = fArr[0];
            float f7 = fArr[1];
            float[] fArr2 = {(fMax - f6) / fMin, (fMax - f7) / fMin, (fMax - fArr[2]) / fMin};
            if (f6 == fMax) {
                f3 = fArr2[2] - fArr2[1];
            } else {
                if (f7 == fMax) {
                    f = fArr2[0] + 2.0f;
                    f2 = fArr2[2];
                } else {
                    f = fArr2[1] + 4.0f;
                    f2 = fArr2[0];
                }
                f3 = f - f2;
            }
            float f8 = f3 / 6.0f;
            f4 = f8 < 0.0f ? f8 + 1.0f : f8;
        }
        return new float[]{f4, f5, fMax};
    }

    public void setClockDrawable(Drawable drawable) {
        if (drawable == null) {
            this.mDrawable = null;
            this.mRuntimeShader = null;
        } else {
            if (this.mRuntimeShader == null) {
                this.mRuntimeShader = new RuntimeShader(glslCode);
            }
            this.mDrawable = drawable;
            freshShaderAsync();
        }
    }

    public void setPrimaryColor(int i) {
        this.mHexColor = i;
        freshShaderAsync();
    }

    public void setStyle(int i) {
        this.style = i;
    }

    public final Bitmap updateBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(getWidth() / width, getHeight() / height);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, matrix, paint);
        bitmap.recycle();
        return bitmapCreateBitmap;
    }
}
