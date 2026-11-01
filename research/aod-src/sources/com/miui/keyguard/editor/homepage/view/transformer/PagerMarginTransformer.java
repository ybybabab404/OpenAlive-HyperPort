package com.miui.keyguard.editor.homepage.view.transformer;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder;
import com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder;
import com.miui.keyguard.editor.homepage.view.adapter.FloorViewHolder;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.view.viewpager2.ViewPager2;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: PagerMarginTransformer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class PagerMarginTransformer extends ScrollStateChangedPageTransformer {
    public final String _tag;
    public final float halfMinScale;
    public int mContainerPadding;
    public FloorViewHolder mFloorViewHolder;
    public int mHorizontalOffscreenOffset;
    public RecyclerView.LayoutManager mLayoutManager;
    public final Map mSettlePositionsForPage;
    public final Map mSettleTranslateForPage;
    public int mVerticalBottomOffset;
    public int mVerticalTopOffset;
    public final float minScale;
    public final float minScaleComplement;

    public PagerMarginTransformer(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this._tag = "Keyguard-Editor-PagerMarginTransformer";
        float fProvideMinScaleForNeighboringItem = ResourcesProvider.INSTANCE.provideMinScaleForNeighboringItem(context);
        this.minScale = fProvideMinScaleForNeighboringItem;
        float f = 1;
        this.minScaleComplement = f - fProvideMinScaleForNeighboringItem;
        this.halfMinScale = fProvideMinScaleForNeighboringItem + ((f - fProvideMinScaleForNeighboringItem) / 2);
        this.mSettlePositionsForPage = new LinkedHashMap();
        this.mSettleTranslateForPage = new LinkedHashMap();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public PagerMarginTransformer(Context context, float f, float f2) {
        this(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mVerticalTopOffset = (int) f;
        this.mVerticalBottomOffset = (int) f2;
        Log.d(this._tag, "topOffset = " + f + ", bottomOffset = " + f2);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public PagerMarginTransformer(ViewPager2 pager2, FloorViewHolder floorViewHolder, int i) {
        Intrinsics.checkNotNullParameter(pager2, "pager2");
        Context context = pager2.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        this(context);
        this.mFloorViewHolder = floorViewHolder;
        this.mHorizontalOffscreenOffset = i;
        this.mLayoutManager = pager2.getRecyclerViewImpl().getLayoutManager();
    }

    public final Float calculateFractionIn01(float f, float f2) {
        int verticallyScrollDirection = getVerticallyScrollDirection();
        if (verticallyScrollDirection == 1) {
            return f >= f2 ? Float.valueOf(1.0f) : Float.valueOf(1 - ((f2 - f) / f2));
        }
        if (verticallyScrollDirection != 2) {
            return null;
        }
        return f <= f2 ? Float.valueOf(0.0f) : Float.valueOf((f - f2) / (1 - f2));
    }

    public final Float calculateFractionIn12(float f, float f2) {
        int verticallyScrollDirection = getVerticallyScrollDirection();
        if (verticallyScrollDirection == 1) {
            return f >= f2 ? Float.valueOf(0.0f) : Float.valueOf((f2 - f) / (f2 - 1));
        }
        if (verticallyScrollDirection != 2) {
            return null;
        }
        return f <= f2 ? Float.valueOf(1.0f) : Float.valueOf(1 - ((f - f2) / (2 - f2)));
    }

    public final Float calculateFractionInMinus10(float f, float f2) {
        int verticallyScrollDirection = getVerticallyScrollDirection();
        if (verticallyScrollDirection == 1) {
            return f >= f2 ? Float.valueOf(0.0f) : Float.valueOf((f2 - f) / (f2 + 1));
        }
        if (verticallyScrollDirection != 2) {
            return null;
        }
        return f <= f2 ? Float.valueOf(1.0f) : Float.valueOf(1 - ((f - f2) / (-f2)));
    }

    public final Float calculateFractionInMinus21(float f, float f2) {
        int verticallyScrollDirection = getVerticallyScrollDirection();
        if (verticallyScrollDirection == 1) {
            return f >= f2 ? Float.valueOf(1.0f) : Float.valueOf(1 - ((f2 - f) / (f2 + 2)));
        }
        if (verticallyScrollDirection != 2) {
            return null;
        }
        return f <= f2 ? Float.valueOf(0.0f) : Float.valueOf((f - f2) / ((-1) - f2));
    }

    public final float calculateScaleFactor(float f) {
        float f2;
        float f3;
        float f4;
        if (f < -1.0f || f > 1.0f) {
            return this.minScale;
        }
        if (f < 0.0f) {
            f2 = 1;
            f3 = f + f2;
            f4 = this.minScale;
        } else {
            f2 = 1;
            f3 = f2 - f;
            f4 = this.minScale;
        }
        return (f3 * (f2 - f4)) + f4;
    }

    public final Float calculateTranslateIn01(View view, float f, float f2) {
        int verticallyScrollDirection = getVerticallyScrollDirection();
        if (verticallyScrollDirection == 1) {
            return Float.valueOf(f + ((1 - f2) * (-f)));
        }
        if (verticallyScrollDirection != 2) {
            return null;
        }
        return Float.valueOf(f + (f2 * ((-this.mVerticalTopOffset) - f)));
    }

    public final Float calculateTranslateIn12(View view, float f, float f2) {
        int verticallyScrollDirection = getVerticallyScrollDirection();
        if (verticallyScrollDirection == 1) {
            return Float.valueOf(f + (f2 * ((-this.mVerticalTopOffset) - f)));
        }
        if (verticallyScrollDirection != 2) {
            return null;
        }
        return Float.valueOf(f + ((1 - f2) * (-f)));
    }

    public final Float calculateTranslateInMinus10(View view, float f, float f2) {
        int verticallyScrollDirection = getVerticallyScrollDirection();
        if (verticallyScrollDirection == 1) {
            return Float.valueOf(f + (f2 * (this.mVerticalBottomOffset - f)));
        }
        if (verticallyScrollDirection != 2) {
            return null;
        }
        return Float.valueOf(f + ((1 - f2) * (-f)));
    }

    public final Float calculateTranslateInMinus21(View view, float f, float f2) {
        int verticallyScrollDirection = getVerticallyScrollDirection();
        if (verticallyScrollDirection == 1) {
            return Float.valueOf(f + ((1 - f2) * (-f)));
        }
        if (verticallyScrollDirection != 2) {
            return null;
        }
        return Float.valueOf(f + (f2 * (this.mVerticalBottomOffset - f)));
    }

    public final float getViewScaleWidth(int i) {
        BaseItemViewHolder baseItemViewHolder;
        View targetContentView;
        if (i < 0) {
            return 0.0f;
        }
        FloorViewHolder floorViewHolder = this.mFloorViewHolder;
        RecyclerView.ViewHolder viewHolder = floorViewHolder != null ? floorViewHolder.getViewHolder(i) : null;
        if (!(viewHolder instanceof BaseItemViewHolder) || (targetContentView = (baseItemViewHolder = (BaseItemViewHolder) viewHolder).getTargetContentView()) == null) {
            return 0.0f;
        }
        View itemView = baseItemViewHolder.itemView;
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        return ((1 - itemView.getScaleX()) * targetContentView.getWidth()) / 2;
    }

    @Override // com.miui.keyguard.editor.homepage.view.transformer.ScrollStateChangedPageTransformer, com.miui.keyguard.editor.view.viewpager2.ViewPager2.OnPageChangeCallback
    public void onPageScrollStateChanged(int i) {
        super.onPageScrollStateChanged(i);
        if (i == 0) {
            this.mSettlePositionsForPage.clear();
            this.mSettleTranslateForPage.clear();
        } else {
            if (i != 1) {
                return;
            }
            this.mSettlePositionsForPage.clear();
            this.mSettleTranslateForPage.clear();
        }
    }

    public final ViewPager2 requireViewPager(View view) {
        ViewParent parent = view.getParent();
        ViewParent parent2 = parent.getParent();
        if ((parent instanceof RecyclerView) && (parent2 instanceof ViewPager2)) {
            return (ViewPager2) parent2;
        }
        return null;
    }

    public final void setPageFinalLocation(View view, float f) {
        Float fValueOf = Float.valueOf(0.0f);
        if (f != -2.0f) {
            if (f == -1.0f) {
                fValueOf = Float.valueOf(this.mVerticalBottomOffset);
            } else if (f != 0.0f) {
                if (f == 1.0f) {
                    fValueOf = Float.valueOf(-this.mVerticalTopOffset);
                } else if (f != 2.0f) {
                    fValueOf = null;
                }
            }
        }
        if (fValueOf != null) {
            view.setTranslationY(fValueOf.floatValue());
        }
    }

    public final void setupPageIdleLocation(View view, float f) {
        float f2;
        int i;
        float f3 = 0.0f;
        if (f >= -1.0f) {
            if (f <= 0.0f) {
                i = this.mVerticalBottomOffset;
            } else if (f <= 1.0f) {
                i = this.mVerticalTopOffset;
            } else if (f < 2.0f) {
                f2 = this.mVerticalTopOffset;
                f -= 2;
                f3 = f2 * f;
            }
            f2 = -i;
            f3 = f2 * f;
        }
        view.setTranslationY(f3);
    }

    @Override // com.miui.keyguard.editor.view.viewpager2.ViewPager2.PageTransformer
    public void transformPage(View page, float f) {
        BaseViewHolder baseViewHolder;
        View targetContentView;
        ViewPager2 viewPager2RequireViewPager;
        Intrinsics.checkNotNullParameter(page, "page");
        Object tag = page.getTag(R.id.kg_template_holder);
        if (!(tag instanceof BaseViewHolder) || (targetContentView = (baseViewHolder = (BaseViewHolder) tag).getTargetContentView()) == null || (viewPager2RequireViewPager = requireViewPager(page)) == null) {
            return;
        }
        if (viewPager2RequireViewPager.getOrientation() == 0) {
            transformPageHorizontally(page, targetContentView, f, baseViewHolder.getLayoutPosition());
        } else {
            transformPageVertically(page, f, baseViewHolder.getY());
        }
    }

    public final void transformPageHorizontally(View view, View view2, float f, int i) {
        float viewScaleWidth;
        Object tag = view.getTag(R.id.kg_template_holder);
        if (!(tag instanceof BaseItemViewHolder)) {
            Log.w(this._tag, "transformPageHorizontally failed: invalid pageHolder.");
            return;
        }
        if (this.mContainerPadding == 0) {
            ResourcesProvider resourcesProvider = ResourcesProvider.INSTANCE;
            Context context = view.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            this.mContainerPadding = (int) resourcesProvider.provideTemplateContainerPadding(context);
        }
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context2 = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        boolean zIsRtl = deviceUtil.isRtl(context2);
        BaseItemViewHolder baseItemViewHolder = (BaseItemViewHolder) tag;
        int x = baseItemViewHolder.getX();
        int horizontallyScrollDirection = getHorizontallyScrollDirection();
        float fCalculateScaleFactor = calculateScaleFactor(f);
        float width = ((view2.getWidth() * this.minScaleComplement) / 2) + (this.mContainerPadding * fCalculateScaleFactor);
        ResourcesProvider resourcesProvider2 = ResourcesProvider.INSTANCE;
        Context context3 = view.getContext();
        Intrinsics.checkNotNullExpressionValue(context3, "getContext(...)");
        float fProvideHorizontalPagerMargin = width - resourcesProvider2.provideHorizontalPagerMargin(context3);
        int i2 = x - 1;
        int i3 = x + 1;
        FloorViewHolder floorViewHolder = this.mFloorViewHolder;
        BaseItemViewHolder baseItemViewHolder2 = (BaseItemViewHolder) (floorViewHolder != null ? floorViewHolder.getViewHolder(i3) : null);
        FloorViewHolder floorViewHolder2 = this.mFloorViewHolder;
        BaseItemViewHolder baseItemViewHolder3 = (BaseItemViewHolder) (floorViewHolder2 != null ? floorViewHolder2.getViewHolder(i2) : null);
        float f2 = 0.0f;
        if (f < -1.0f) {
            viewScaleWidth = -getViewScaleWidth(i3);
        } else {
            viewScaleWidth = f > 1.0f ? getViewScaleWidth(i2) : 0.0f;
        }
        if (horizontallyScrollDirection != 1) {
            if (horizontallyScrollDirection == 2 && ((baseItemViewHolder2 == null || !baseItemViewHolder2.isSelected()) && !baseItemViewHolder.isSelected() && (baseItemViewHolder3 == null || !baseItemViewHolder3.isSelected()))) {
                f2 = this.mContainerPadding * fCalculateScaleFactor;
                viewScaleWidth -= f2;
            }
        } else if ((baseItemViewHolder2 == null || !baseItemViewHolder2.isSelected()) && !baseItemViewHolder.isSelected() && (baseItemViewHolder3 == null || !baseItemViewHolder3.isSelected())) {
            f2 = this.mContainerPadding * fCalculateScaleFactor;
            viewScaleWidth += f2;
        }
        float f3 = f2;
        float f4 = (fProvideHorizontalPagerMargin * f) + viewScaleWidth;
        Log.d(this._tag, "item position: " + i + " position: " + f + "  adjustPixels:" + viewScaleWidth + " scrollDirection" + horizontallyScrollDirection + " translateX:" + fProvideHorizontalPagerMargin + "  offset:" + f4 + " currentPosition:" + x + "  contain padding:" + f3 + "  left select:" + (baseItemViewHolder3 != null ? Boolean.valueOf(baseItemViewHolder3.isSelected()) : null) + " right position: " + i3 + " right select:" + (baseItemViewHolder2 != null ? Boolean.valueOf(baseItemViewHolder2.isSelected()) : null));
        if (!zIsRtl) {
            f4 = -f4;
        }
        view.setTranslationX(f4);
    }

    public final void transformPageVertically(View view, float f, int i) {
        int scrollState = getScrollState();
        if (scrollState == 0) {
            setupPageIdleLocation(view, f);
            return;
        }
        if (scrollState == 1) {
            setPageFinalLocation(view, f);
            return;
        }
        if (scrollState != 2) {
            return;
        }
        Float f2 = (Float) this.mSettlePositionsForPage.get(view);
        if (f2 == null) {
            this.mSettlePositionsForPage.put(view, Float.valueOf(f));
        }
        Float f3 = (Float) this.mSettleTranslateForPage.get(view);
        if (f3 == null) {
            this.mSettleTranslateForPage.put(view, Float.valueOf(view.getTranslationY()));
        }
        if (f2 == null || f3 == null) {
            return;
        }
        updateSettlingTranslateYByPosition(view, f, f2.floatValue(), f3.floatValue(), i);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0023  */
    public final void updateSettlingTranslateYByPosition(View view, float f, float f2, float f3, int i) {
        Float fValueOf = Float.valueOf(0.0f);
        if (f > -2.0f) {
            if (f < -1.0f) {
                Float fCalculateFractionInMinus21 = calculateFractionInMinus21(f, f2);
                if (fCalculateFractionInMinus21 != null) {
                    fValueOf = calculateTranslateInMinus21(view, f3, fCalculateFractionInMinus21.floatValue());
                } else {
                    fValueOf = null;
                }
            } else if (f == -1.0f) {
                fValueOf = Float.valueOf(this.mVerticalBottomOffset);
            } else if (f < 0.0f) {
                Float fCalculateFractionInMinus10 = calculateFractionInMinus10(f, f2);
                if (fCalculateFractionInMinus10 != null) {
                    fValueOf = calculateTranslateInMinus10(view, f3, fCalculateFractionInMinus10.floatValue());
                } else {
                    fValueOf = null;
                }
            } else if (f != 0.0f) {
                if (f < 1.0f) {
                    Float fCalculateFractionIn01 = calculateFractionIn01(f, f2);
                    if (fCalculateFractionIn01 != null) {
                        fValueOf = calculateTranslateIn01(view, f3, fCalculateFractionIn01.floatValue());
                    } else {
                        fValueOf = null;
                    }
                } else if (f == 1.0f) {
                    fValueOf = Float.valueOf(-this.mVerticalTopOffset);
                } else if (f < 2.0f) {
                    Float fCalculateFractionIn12 = calculateFractionIn12(f, f2);
                    if (fCalculateFractionIn12 != null) {
                        fValueOf = calculateTranslateIn12(view, f3, fCalculateFractionIn12.floatValue());
                    } else {
                        fValueOf = null;
                    }
                }
            }
        }
        if (fValueOf != null) {
            view.setTranslationY(fValueOf.floatValue());
        }
    }
}
