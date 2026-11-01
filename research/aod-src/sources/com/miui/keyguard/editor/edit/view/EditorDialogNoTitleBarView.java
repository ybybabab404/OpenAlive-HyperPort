package com.miui.keyguard.editor.edit.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import com.miui.maml.R;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: EditorDialogNoTitleBarView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class EditorDialogNoTitleBarView extends LinearLayout {
    public final Drawable cancelIcon;
    public final ImageView mCloseView;
    public FrameLayout mTitleFrameLayout;
    public final TextView mTitleTextView;
    public OnCloseDialogListener onCloseDialogListener;
    public final int titleColor;

    /* JADX INFO: compiled from: EditorDialogNoTitleBarView.kt */
    @Metadata
    public interface OnCloseDialogListener {
        void closeDialog();
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.view.EditorDialogNoTitleBarView$sendAccessibilityEvent$1, reason: invalid class name */
    /* JADX INFO: compiled from: EditorDialogNoTitleBarView.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.view.EditorDialogNoTitleBarView$sendAccessibilityEvent$1", f = "EditorDialogNoTitleBarView.kt", l = {R.styleable.AppCompatTheme_textAppearanceListItemSmall}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EditorDialogNoTitleBarView.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (DelayKt.delay(500L, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            ImageView imageView = EditorDialogNoTitleBarView.this.mCloseView;
            if (imageView != null) {
                Boxing.boxBoolean(imageView.requestFocus());
            }
            ImageView imageView2 = EditorDialogNoTitleBarView.this.mCloseView;
            if (imageView2 != null) {
                imageView2.sendAccessibilityEvent(128);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public EditorDialogNoTitleBarView(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public EditorDialogNoTitleBarView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.miui.keyguard.editor.R.styleable.WallpaperFilterView, com.miui.keyguard.editor.R.attr.wallpaperFilterViewStyle, 0);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(com.miui.keyguard.editor.R.styleable.WallpaperFilterView_wallpaperHeadCancelIcon);
        if (drawable == null) {
            drawable = getResources().getDrawable(com.miui.keyguard.editor.R.drawable.miuix_action_icon_cancel_light);
            Intrinsics.checkNotNullExpressionValue(drawable, "getDrawable(...)");
        }
        this.cancelIcon = drawable;
        int color = typedArrayObtainStyledAttributes.getColor(com.miui.keyguard.editor.R.styleable.WallpaperFilterView_wallpaperHeadTitleColor, -16777216);
        this.titleColor = color;
        typedArrayObtainStyledAttributes.recycle();
        FrameLayout frameLayout = new FrameLayout(context);
        ImageView imageView = new ImageView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        Resources resources = imageView.getResources();
        int i = com.miui.keyguard.editor.R.dimen.kg_editor_container_padding;
        layoutParams.leftMargin = resources.getDimensionPixelSize(i);
        layoutParams.rightMargin = imageView.getResources().getDimensionPixelSize(i);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        layoutParams.gravity = deviceUtil.isRtl(context) ? 21 : 19;
        imageView.setLayoutParams(layoutParams);
        imageView.setBackground(drawable);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.edit.view.EditorDialogNoTitleBarView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EditorDialogNoTitleBarView.mTitleFrameLayout$lambda$5$lambda$2$lambda$1(this.f$0, view);
            }
        });
        imageView.setVisibility(0);
        imageView.setContentDescription(imageView.getResources().getString(com.miui.keyguard.editor.R.string.kg_exit));
        this.mCloseView = imageView;
        TextView textView = new TextView(context);
        textView.setText("Title");
        textView.setGravity(17);
        textView.setTextColor(color);
        textView.setTextSize(2, 20.0f);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-2, -2);
        layoutParams2.gravity = 17;
        textView.setLayoutParams(layoutParams2);
        textView.setTypeface(Typeface.create(textView.getResources().getString(com.miui.keyguard.editor.R.string.kg_font_mi_sans_normal), 1));
        textView.setSingleLine();
        textView.setEllipsize(TextUtils.TruncateAt.END);
        this.mTitleTextView = textView;
        frameLayout.addView(imageView);
        frameLayout.addView(textView);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-1, getResources().getDimensionPixelSize(com.miui.keyguard.editor.R.dimen.kg_editor_container_title_high));
        layoutParams3.topMargin = (deviceUtil.isPad() || deviceUtil.isFoldLargeScreen(context)) ? getResources().getDimensionPixelSize(com.miui.keyguard.editor.R.dimen.kg_editor_margin_top) : 0;
        layoutParams3.bottomMargin = 0;
        frameLayout.setLayoutParams(layoutParams3);
        this.mTitleFrameLayout = frameLayout;
        setOrientation(1);
        addView(this.mTitleFrameLayout);
    }

    public /* synthetic */ EditorDialogNoTitleBarView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public static final void mTitleFrameLayout$lambda$5$lambda$2$lambda$1(EditorDialogNoTitleBarView editorDialogNoTitleBarView, View view) {
        OnCloseDialogListener onCloseDialogListener = editorDialogNoTitleBarView.onCloseDialogListener;
        if (onCloseDialogListener != null) {
            onCloseDialogListener.closeDialog();
        }
    }

    public final void onDestroy() {
        this.onCloseDialogListener = null;
    }

    public final void sendAccessibilityEvent() {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (!deviceUtil.isPad()) {
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            if (!deviceUtil.isFoldLargeScreen(context)) {
                return;
            }
        }
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context2), Dispatchers.getMain(), null, new AnonymousClass1(null), 2, null);
    }

    public final void setOnCloseDialogListener(@NotNull OnCloseDialogListener onCloseDialogListener) {
        Intrinsics.checkNotNullParameter(onCloseDialogListener, "onCloseDialogListener");
        this.onCloseDialogListener = onCloseDialogListener;
    }

    public final void setTitle(@NotNull String mName) {
        Intrinsics.checkNotNullParameter(mName, "mName");
        this.mTitleTextView.setText(mName);
    }

    public final void setTitleVisible(boolean z) {
        if (z) {
            this.mTitleTextView.setVisibility(0);
            this.mCloseView.setVisibility(0);
        } else {
            this.mTitleTextView.setVisibility(8);
            this.mCloseView.setVisibility(8);
            this.mTitleFrameLayout.setVisibility(8);
        }
    }
}
