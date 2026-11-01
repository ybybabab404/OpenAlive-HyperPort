package miuix.theme.symbol;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* JADX INFO: loaded from: classes3.dex */
public class SymbolLayerView extends FrameLayout {
    public int[] layerColors;
    public String[] layerTexts;
    public TextView[] layerViews;
    public float textSizePx;
    public Typeface typeface;

    public SymbolLayerView(@NonNull Context context) {
        super(context);
    }

    public SymbolLayerView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void rebuildLayers() {
        removeAllViews();
        this.layerViews = new TextView[this.layerTexts.length];
        for (int i = 0; i < this.layerTexts.length; i++) {
            TextView textView = new TextView(getContext());
            textView.setTypeface(this.typeface);
            textView.setTextSize(0, this.textSizePx);
            textView.setText(this.layerTexts[i]);
            textView.setTextColor(this.layerColors[i]);
            textView.setIncludeFontPadding(false);
            this.layerViews[i] = textView;
            addView(textView, new FrameLayout.LayoutParams(-2, -2));
        }
    }

    public void setFontVariationSettings(String str) {
        TextView[] textViewArr = this.layerViews;
        if (textViewArr != null) {
            for (TextView textView : textViewArr) {
                textView.setFontVariationSettings(str);
            }
        }
    }

    public void setLayerColor(int i, int i2) {
        int[] iArr = this.layerColors;
        if (iArr == null || i < 0 || i >= iArr.length) {
            return;
        }
        iArr[i] = i2;
        TextView[] textViewArr = this.layerViews;
        if (textViewArr == null || i >= textViewArr.length) {
            return;
        }
        textViewArr[i].setTextColor(i2);
    }

    public void setLayers(@NonNull Typeface typeface, float f, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (strArr.length != iArr.length) {
            throw new IllegalArgumentException("texts and colors must have same length");
        }
        this.typeface = typeface;
        this.textSizePx = f;
        this.layerTexts = strArr;
        this.layerColors = iArr;
        rebuildLayers();
    }

    public void setTextSize(float f) {
        this.textSizePx = f;
        TextView[] textViewArr = this.layerViews;
        if (textViewArr != null) {
            for (TextView textView : textViewArr) {
                textView.setTextSize(0, f);
            }
        }
    }

    public void setTypeface(@NonNull Typeface typeface) {
        this.typeface = typeface;
        TextView[] textViewArr = this.layerViews;
        if (textViewArr != null) {
            for (TextView textView : textViewArr) {
                textView.setTypeface(typeface);
            }
        }
    }
}
