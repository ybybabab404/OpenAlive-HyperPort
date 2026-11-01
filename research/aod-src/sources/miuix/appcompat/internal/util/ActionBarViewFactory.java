package miuix.appcompat.internal.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import miuix.animation.Folme;
import miuix.animation.IHoverStyle;
import miuix.animation.base.AnimConfig;
import miuix.appcompat.R;
import miuix.appcompat.internal.app.widget.actionbar.CollapseTitle;
import miuix.appcompat.internal.app.widget.actionbar.ExpandTitle;
import miuix.appcompat.internal.app.widget.actionbar.MediumTitle;
import miuix.appcompat.internal.graphics.drawable.RoundStateDrawable;
import miuix.core.util.MiuixUIUtils;
import miuix.internal.util.AttributeResolver;

/* JADX INFO: loaded from: classes2.dex */
public class ActionBarViewFactory {
    public static /* synthetic */ void $r8$lambda$3xoOfBCT1_zgXEH5nHduz7io2es(AppCompatImageView appCompatImageView) {
        Folme.useAt(appCompatImageView).hover().setFeedbackRadius(60.0f);
        Folme.useAt(appCompatImageView).hover().setEffect(IHoverStyle.HoverEffect.FLOATED_WRAPPED).handleHoverOf((View) appCompatImageView.getParent(), new AnimConfig[0]);
    }

    public static CollapseTitle generateCollapseTitle(Context context, int i, int i2) {
        CollapseTitle collapseTitle = new CollapseTitle(context, i, i2);
        collapseTitle.init();
        return collapseTitle;
    }

    public static ExpandTitle generateExpandTitle(Context context) {
        ExpandTitle expandTitle = new ExpandTitle(context);
        expandTitle.init();
        return expandTitle;
    }

    public static MediumTitle generateMediumTitle(Context context, int i, int i2) {
        MediumTitle mediumTitle = new MediumTitle(context, i, i2);
        mediumTitle.init();
        return mediumTitle;
    }

    public static View generateTitleUpView(Context context, ViewGroup viewGroup) {
        int dimensionPixelOffset = context.getResources().getDimensionPixelOffset(R.dimen.miuix_appcompat_action_bar_title_view_padding_horizontal);
        FrameLayout frameLayout = new FrameLayout(context);
        if (context.getResources().getConfiguration().getLayoutDirection() == 1) {
            frameLayout.setPadding(dimensionPixelOffset, 0, 0, 0);
        } else {
            frameLayout.setPadding(0, 0, dimensionPixelOffset, 0);
        }
        frameLayout.setId(R.id.up);
        frameLayout.setVisibility(8);
        frameLayout.setContentDescription(context.getResources().getString(R.string.actionbar_button_up_description));
        ViewCompat.setAccessibilityDelegate(frameLayout, new AccessibilityDelegateCompat() { // from class: miuix.appcompat.internal.util.ActionBarViewFactory.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setClassName(Button.class.getName());
            }
        });
        frameLayout.setClipChildren(false);
        frameLayout.setClipToPadding(false);
        final AppCompatImageView appCompatImageView = new AppCompatImageView(context);
        appCompatImageView.setDuplicateParentStateEnabled(true);
        boolean zIsLightTheme = MiuixUIUtils.isLightTheme(context);
        appCompatImageView.setBackground(new RoundStateDrawable(zIsLightTheme ? 16777215 : 0, !zIsLightTheme));
        appCompatImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.miuix_theme_title_button_width);
        int dimensionPixelSize2 = context.getResources().getDimensionPixelSize(R.dimen.miuix_theme_title_button_height);
        appCompatImageView.setImageDrawable(AttributeResolver.resolveDrawable(context, android.R.attr.homeAsUpIndicator));
        frameLayout.addView(appCompatImageView, new FrameLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize2));
        appCompatImageView.post(new Runnable() { // from class: miuix.appcompat.internal.util.ActionBarViewFactory$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ActionBarViewFactory.$r8$lambda$3xoOfBCT1_zgXEH5nHduz7io2es(appCompatImageView);
            }
        });
        if (viewGroup != null) {
            viewGroup.addView(frameLayout);
        }
        return frameLayout;
    }
}
