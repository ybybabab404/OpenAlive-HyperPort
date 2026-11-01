package com.miui.keyguard.editor.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.ColorUtils;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.preset.FilterColor;
import com.miui.keyguard.editor.data.preset.FontFilterSelectInfo;
import com.miui.keyguard.editor.edit.color.ColorProgressDrawable;
import com.miui.keyguard.editor.edit.color.DefaultColorProgressDrawable;
import com.miui.keyguard.editor.utils.DeviceUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: FontFilterCustomView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class FontFilterCustomView extends LinearLayout implements SeekBar.OnSeekBarChangeListener {
    public static final Companion Companion = new Companion(null);
    public FontColorChangeHandler colorChangeHandler;
    public final FontFilterSelectInfo currentSelectFilter;
    public LinearLayout hslContainer;
    public ConstraintLayout hslHead;
    public ImageView hslHeadIcon;
    public TextView hslHeadTitle;
    public TextView hueLabel;
    public miuix.androidbasewidget.widget.SeekBar hueSeekBar;
    public FontFilterCustomView$hueSeekBarBackground$1 hueSeekBarBackground;
    public TextView lightnessLabel;
    public miuix.androidbasewidget.widget.SeekBar lightnessSeekBar;
    public DefaultColorProgressDrawable lightnessSeekBarBackground;
    public final MiuiClockViewSafeHolder miuiClockViewSafeHolder;
    public final int pagerHeight;
    public final View rootView;
    public TextView saturationLabel;
    public miuix.androidbasewidget.widget.SeekBar saturationSeekBar;
    public DefaultColorProgressDrawable saturationSeekBarBackground;
    public final int targetIndex;

    /* JADX INFO: compiled from: FontFilterCustomView.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.miui.keyguard.editor.view.FontFilterCustomView$hueSeekBarBackground$1] */
    public FontFilterCustomView(final Context context, int i, FontFilterSelectInfo currentSelectFilter, MiuiClockViewSafeHolder miuiClockViewSafeHolder, int i2) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(currentSelectFilter, "currentSelectFilter");
        Intrinsics.checkNotNullParameter(miuiClockViewSafeHolder, "miuiClockViewSafeHolder");
        this.targetIndex = i;
        this.currentSelectFilter = currentSelectFilter;
        this.miuiClockViewSafeHolder = miuiClockViewSafeHolder;
        this.pagerHeight = i2;
        this.rootView = LayoutInflater.from(context).inflate(R.layout.kg_font_custom_color_view, (ViewGroup) this, true);
        final int[] intArray = context.getResources().getIntArray(R.array.kg_hue_gradient_colors);
        this.hueSeekBarBackground = new ColorProgressDrawable(context, intArray) { // from class: com.miui.keyguard.editor.view.FontFilterCustomView$hueSeekBarBackground$1
            {
                Intrinsics.checkNotNull(intArray);
            }
        };
        this.saturationSeekBarBackground = new DefaultColorProgressDrawable(context, -65536, R.color.kg_color_value_saturation_start_color, 0, 8, null);
        this.lightnessSeekBarBackground = new DefaultColorProgressDrawable(context, -65536, 0, 0, 12, null);
        initView();
        initStyle();
    }

    public final void initStyle() {
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, R.styleable.FontFilterView, R.attr.fontFilterViewStyle, 0);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.FontFilterView_fontHeadCancelIcon);
        typedArrayObtainStyledAttributes.getDrawable(R.styleable.FontFilterView_fontHeadBackIcon);
        int color = typedArrayObtainStyledAttributes.getColor(R.styleable.FontFilterView_fontHeadTitleColor, -16777216);
        int color2 = typedArrayObtainStyledAttributes.getColor(R.styleable.FontFilterView_hslLabelColor, -16777216);
        typedArrayObtainStyledAttributes.recycle();
        ImageView imageView = this.hslHeadIcon;
        if (imageView != null) {
            imageView.setImageDrawable(drawable);
        }
        TextView textView = this.hslHeadTitle;
        if (textView != null) {
            textView.setTextColor(color);
        }
        TextView textView2 = this.hueLabel;
        if (textView2 != null) {
            textView2.setTextColor(color2);
        }
        TextView textView3 = this.saturationLabel;
        if (textView3 != null) {
            textView3.setTextColor(color2);
        }
        TextView textView4 = this.lightnessLabel;
        if (textView4 != null) {
            textView4.setTextColor(color2);
        }
    }

    public final void initView() {
        ConstraintLayout constraintLayout = (ConstraintLayout) this.rootView.findViewById(R.id.hsl_head);
        this.hslHead = constraintLayout;
        Intrinsics.checkNotNull(constraintLayout);
        ViewGroup.LayoutParams layoutParams = constraintLayout.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        marginLayoutParams.topMargin = DeviceUtil.INSTANCE.isPhone() ? 0 : getResources().getDimensionPixelSize(R.dimen.kg_editor_dialog_header_margin_top);
        ConstraintLayout constraintLayout2 = this.hslHead;
        if (constraintLayout2 != null) {
            constraintLayout2.setLayoutParams(marginLayoutParams);
        }
        ConstraintLayout constraintLayout3 = this.hslHead;
        this.hslHeadIcon = constraintLayout3 != null ? (ImageView) constraintLayout3.findViewById(R.id.head_icon) : null;
        ConstraintLayout constraintLayout4 = this.hslHead;
        TextView textView = constraintLayout4 != null ? (TextView) constraintLayout4.findViewById(R.id.head_title) : null;
        this.hslHeadTitle = textView;
        if (textView != null) {
            textView.setText(R.string.kg_font_filter_hsl_title);
        }
        this.hslContainer = (LinearLayout) this.rootView.findViewById(R.id.hsl_container);
        this.hueLabel = (TextView) this.rootView.findViewById(R.id.color_value_editor_hue_label);
        miuix.androidbasewidget.widget.SeekBar seekBar = (miuix.androidbasewidget.widget.SeekBar) this.rootView.findViewById(R.id.hue_seek_bar);
        this.hueSeekBar = seekBar;
        if (seekBar != null) {
            seekBar.setProgressDrawable(this.hueSeekBarBackground);
        }
        miuix.androidbasewidget.widget.SeekBar seekBar2 = this.hueSeekBar;
        if (seekBar2 != null) {
            seekBar2.setOnSeekBarChangeListener(this);
        }
        this.saturationLabel = (TextView) this.rootView.findViewById(R.id.color_value_editor_saturation_label);
        miuix.androidbasewidget.widget.SeekBar seekBar3 = (miuix.androidbasewidget.widget.SeekBar) this.rootView.findViewById(R.id.saturation_seek_bar);
        this.saturationSeekBar = seekBar3;
        if (seekBar3 != null) {
            seekBar3.setProgressDrawable(this.saturationSeekBarBackground);
        }
        miuix.androidbasewidget.widget.SeekBar seekBar4 = this.saturationSeekBar;
        if (seekBar4 != null) {
            seekBar4.setOnSeekBarChangeListener(this);
        }
        this.lightnessLabel = (TextView) this.rootView.findViewById(R.id.color_value_editor_lightness_label);
        miuix.androidbasewidget.widget.SeekBar seekBar5 = (miuix.androidbasewidget.widget.SeekBar) this.rootView.findViewById(R.id.lightness_seek_bar);
        this.lightnessSeekBar = seekBar5;
        if (seekBar5 != null) {
            seekBar5.setProgressDrawable(this.lightnessSeekBarBackground);
        }
        miuix.androidbasewidget.widget.SeekBar seekBar6 = this.lightnessSeekBar;
        if (seekBar6 != null) {
            seekBar6.setOnSeekBarChangeListener(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        ViewGroup.LayoutParams layoutParams;
        super.onAttachedToWindow();
        if (this.pagerHeight == 0 || (layoutParams = getLayoutParams()) == null) {
            return;
        }
        layoutParams.height = this.pagerHeight;
        setLayoutParams(layoutParams);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (z) {
            float[] currentHslColor = this.currentSelectFilter.getCurrentHslColor(this.targetIndex);
            FilterColor currentFilterColor = this.currentSelectFilter.getCurrentFilterColor(this.targetIndex);
            if (Intrinsics.areEqual(seekBar, this.hueSeekBar)) {
                float f = i;
                currentHslColor[0] = f;
                if (currentFilterColor != null) {
                    currentFilterColor.setColor(ColorUtils.HSLToColor(currentHslColor));
                }
                this.saturationSeekBarBackground.setEndColor(ColorUtils.HSLToColor(new float[]{f, 1.0f, 0.5f}));
                this.lightnessSeekBarBackground.setProgressColor(ColorUtils.HSLToColor(new float[]{currentHslColor[0], currentHslColor[1], 0.5f}));
                FontColorChangeHandler fontColorChangeHandler = this.colorChangeHandler;
                if (fontColorChangeHandler != null) {
                    fontColorChangeHandler.onColorChange(this.currentSelectFilter.getTargetIndex());
                    return;
                }
                return;
            }
            if (Intrinsics.areEqual(seekBar, this.saturationSeekBar)) {
                currentHslColor[1] = i / 100.0f;
                if (currentFilterColor != null) {
                    currentFilterColor.setColor(ColorUtils.HSLToColor(currentHslColor));
                }
                this.lightnessSeekBarBackground.setProgressColor(ColorUtils.HSLToColor(new float[]{currentHslColor[0], currentHslColor[1], 0.5f}));
                FontColorChangeHandler fontColorChangeHandler2 = this.colorChangeHandler;
                if (fontColorChangeHandler2 != null) {
                    fontColorChangeHandler2.onColorChange(this.currentSelectFilter.getTargetIndex());
                    return;
                }
                return;
            }
            if (Intrinsics.areEqual(seekBar, this.lightnessSeekBar)) {
                currentHslColor[2] = i / 100.0f;
                if (currentFilterColor != null) {
                    currentFilterColor.setColor(ColorUtils.HSLToColor(currentHslColor));
                }
                FontColorChangeHandler fontColorChangeHandler3 = this.colorChangeHandler;
                if (fontColorChangeHandler3 != null) {
                    fontColorChangeHandler3.onColorChange(this.currentSelectFilter.getTargetIndex());
                }
            }
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0085, code lost:
    
        if (r7.equals("magazine_b") == false) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x008e, code lost:
    
        if (r7.equals("magazine_a") == false) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0097, code lost:
    
        if (r6.currentSelectFilter.getCurrentFilterId(1) != 0) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0099, code lost:
    
        if (r2 == null) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x009b, code lost:
    
        r6 = r2.getPrimaryColor();
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x009f, code lost:
    
        if (r8 == null) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00a1, code lost:
    
        r8.setColor(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00a5, code lost:
    
        if (r2 == null) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00a7, code lost:
    
        r6 = r2.getBlendColor();
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00ab, code lost:
    
        if (r8 == null) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00ad, code lost:
    
        r8.setColor(r6);
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void readColorFromClock(com.miui.keyguard.editor.view.MiuiClockViewSafeHolder r7, com.miui.keyguard.editor.data.preset.FilterColor r8) {
        /*
            Method dump skipped, instruction units count: 242
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.view.FontFilterCustomView.readColorFromClock(com.miui.keyguard.editor.view.MiuiClockViewSafeHolder, com.miui.keyguard.editor.data.preset.FilterColor):void");
    }

    public final void setColorChangeHandler(@NotNull FontColorChangeHandler handler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        this.colorChangeHandler = handler;
    }

    public final void setCurrentColor() {
        FilterColor currentFilterColor = this.currentSelectFilter.getCurrentFilterColor(this.targetIndex);
        float[] currentHslColor = this.currentSelectFilter.getCurrentHslColor(this.targetIndex);
        readColorFromClock(this.miuiClockViewSafeHolder, currentFilterColor);
        Intrinsics.checkNotNull(currentFilterColor);
        ColorUtils.colorToHSL(currentFilterColor.getColor(), currentHslColor);
        miuix.androidbasewidget.widget.SeekBar seekBar = this.hueSeekBar;
        if (seekBar != null) {
            seekBar.setProgress((int) currentHslColor[0]);
        }
        miuix.androidbasewidget.widget.SeekBar seekBar2 = this.saturationSeekBar;
        if (seekBar2 != null) {
            seekBar2.setProgress((int) (currentHslColor[1] * 100));
        }
        this.saturationSeekBarBackground.setEndColor(ColorUtils.HSLToColor(new float[]{currentHslColor[0], 1.0f, 0.5f}));
        miuix.androidbasewidget.widget.SeekBar seekBar3 = this.lightnessSeekBar;
        if (seekBar3 != null) {
            seekBar3.setProgress((int) (currentHslColor[2] * 100));
        }
        this.lightnessSeekBarBackground.setProgressColor(ColorUtils.HSLToColor(new float[]{currentHslColor[0], currentHslColor[1], 0.5f}));
    }

    public final void setDismissListener(@NotNull View.OnClickListener clickListener) {
        Intrinsics.checkNotNullParameter(clickListener, "clickListener");
        ImageView imageView = this.hslHeadIcon;
        if (imageView != null) {
            imageView.setOnClickListener(clickListener);
        }
    }
}
