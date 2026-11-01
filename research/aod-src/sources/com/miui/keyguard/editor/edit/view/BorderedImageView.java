package com.miui.keyguard.editor.edit.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.view.AlertDialogsKtKt;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: BorderedImageView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class BorderedImageView extends AppCompatImageView {
    public final float cornerRadius;
    public final Path path;
    public final Paint strokePaint;
    public final float strokeWidth;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public BorderedImageView(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public BorderedImageView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public BorderedImageView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        this.cornerRadius = context.getResources().getDimensionPixelSize(R.dimen.kg_template_view_alert_dialog_preview_radius);
        float dimensionPixelOffset = context.getResources().getDimensionPixelOffset(R.dimen.kg_editor_stroke_width);
        this.strokeWidth = dimensionPixelOffset;
        Paint paint = new Paint();
        paint.setColor(context.getResources().getColor(AlertDialogsKtKt.isDarkMode(context) ? R.color.kg_white_30 : R.color.kg_black_30));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dimensionPixelOffset);
        paint.setAntiAlias(true);
        this.strokePaint = paint;
        this.path = new Path();
    }

    public /* synthetic */ BorderedImageView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        this.path.reset();
        Path path = this.path;
        float f = this.strokeWidth;
        float f2 = 2;
        RectF rectF = new RectF(f / f2, f / f2, getWidth() - (this.strokeWidth / f2), getHeight() - (this.strokeWidth / f2));
        float f3 = this.cornerRadius;
        path.addRoundRect(rectF, f3, f3, Path.Direction.CW);
        canvas.clipPath(this.path);
        super.onDraw(canvas);
        canvas.drawPath(this.path, this.strokePaint);
    }
}
