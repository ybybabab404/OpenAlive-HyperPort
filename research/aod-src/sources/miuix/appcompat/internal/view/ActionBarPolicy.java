package miuix.appcompat.internal.view;

import android.content.Context;
import android.content.res.TypedArray;
import miuix.appcompat.R;
import miuix.internal.util.AttributeResolver;

/* JADX INFO: loaded from: classes2.dex */
public class ActionBarPolicy {
    public Context mContext;

    public ActionBarPolicy(Context context) {
        this.mContext = context;
    }

    public static ActionBarPolicy get(Context context) {
        return new ActionBarPolicy(context);
    }

    public boolean enableHomeButtonByDefault() {
        return this.mContext.getApplicationInfo().targetSdkVersion < 14;
    }

    public int getStackedTabMaxWidth() {
        return this.mContext.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_action_bar_stacked_tab_max_width);
    }

    public int getTabContainerHeight() {
        Context context = this.mContext;
        int[] iArr = R.styleable.ActionBar;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, iArr, android.R.attr.actionBarTabBarStyle, 0);
        int i = R.styleable.ActionBar_android_height;
        int layoutDimension = typedArrayObtainStyledAttributes.getLayoutDimension(i, 0);
        typedArrayObtainStyledAttributes.recycle();
        if (layoutDimension > 0) {
            return layoutDimension;
        }
        TypedArray typedArrayObtainStyledAttributes2 = this.mContext.obtainStyledAttributes(null, iArr, android.R.attr.actionBarStyle, 0);
        int layoutDimension2 = typedArrayObtainStyledAttributes2.getLayoutDimension(i, 0);
        typedArrayObtainStyledAttributes2.recycle();
        return layoutDimension2;
    }

    public boolean hasEmbeddedTabs() {
        return AttributeResolver.resolveBoolean(this.mContext, R.attr.actionBarEmbedTabs, false);
    }

    public boolean isTightTitle() {
        return AttributeResolver.resolveBoolean(this.mContext, R.attr.actionBarTightTitle, false);
    }

    public boolean isTitleEnableEllipsis() {
        return AttributeResolver.resolveBoolean(this.mContext, R.attr.actionBarTitleEnableEllipsis, false);
    }

    public boolean showsOverflowMenuButton() {
        return true;
    }
}
