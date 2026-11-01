package miuix.bottomsheet;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.xiaomi.onetrack.util.ac;
import miuix.internal.util.AttributeResolver;

/* JADX INFO: loaded from: classes2.dex */
public class BottomSheetDragHandleView extends AppCompatImageView implements AccessibilityManager.AccessibilityStateChangeListener {
    public final AccessibilityManager accessibilityManager;
    public BottomSheetBehavior bottomSheetBehavior;
    public final BottomSheetBehavior.BottomSheetCallback bottomSheetCallback;

    public BottomSheetDragHandleView(@NonNull Context context) {
        this(context, null);
    }

    public BottomSheetDragHandleView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.bottomSheetDragHandleStyle);
    }

    public BottomSheetDragHandleView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(wrap(context), attributeSet, i);
        this.bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() { // from class: miuix.bottomsheet.BottomSheetDragHandleView.1
            @Override // miuix.bottomsheet.BottomSheetBehavior.BottomSheetCallback
            public void onSlide(View view, float f) {
            }

            @Override // miuix.bottomsheet.BottomSheetBehavior.BottomSheetCallback
            public void onStateChanged(View view, int i2) {
                BottomSheetDragHandleView.this.onBottomSheetStateChanged(i2);
            }
        };
        this.accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        updateInteractableState();
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat() { // from class: miuix.bottomsheet.BottomSheetDragHandleView.2
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setClickable(false);
                accessibilityNodeInfoCompat.setClassName(View.class.getName());
                accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
            }
        });
    }

    private String getContentDescriptionByCurrentState() {
        String string;
        Context context = getContext();
        BottomSheetBehavior bottomSheetBehavior = this.bottomSheetBehavior;
        if (bottomSheetBehavior == null || !bottomSheetBehavior.isDraggable()) {
            return context.getString(R.string.bottomsheet_drag_handle_content_description);
        }
        int state = this.bottomSheetBehavior.getState();
        BottomSheetBehavior bottomSheetBehavior2 = this.bottomSheetBehavior;
        boolean z = false;
        boolean z2 = bottomSheetBehavior2.hideable && bottomSheetBehavior2.getSkipCollapsed();
        if (!this.bottomSheetBehavior.shouldSkipHalfExpanded() && !this.bottomSheetBehavior.shouldSkipHalfExpandedStateWhenDragging()) {
            z = true;
        }
        if (state == 3) {
            string = context.getString(R.string.miuix_bottom_sheet_accessibility_state_maximized);
        } else if (state == 6) {
            string = context.getString(R.string.miuix_bottom_sheet_accessibility_state_half_screen);
        } else {
            string = state == 4 ? context.getString(R.string.miuix_bottom_sheet_accessibility_state_minimized) : null;
        }
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(string)) {
            sb.append(string);
            sb.append(ac.b);
        }
        sb.append(context.getString(R.string.bottomsheet_drag_handle_content_description));
        sb.append(ac.b);
        if (state == 3) {
            if (z2) {
                sb.append(context.getString(R.string.miuix_bottom_sheet_accessibility_scroll_down_to_close));
                return sb.toString();
            }
            if (z) {
                sb.append(context.getString(R.string.miuix_bottom_sheet_accessibility_scroll_down_to_half_screen));
                return sb.toString();
            }
            sb.append(context.getString(R.string.miuix_bottom_sheet_accessibility_scroll_down_to_minimized));
            return sb.toString();
        }
        if (state == 6) {
            sb.append(context.getString(R.string.miuix_bottom_sheet_accessibility_scroll_up_to_maximized));
            sb.append(ac.b);
            if (z2) {
                sb.append(context.getString(R.string.miuix_bottom_sheet_accessibility_scroll_down_to_close));
            } else {
                sb.append(context.getString(R.string.miuix_bottom_sheet_accessibility_scroll_down_to_minimized));
            }
            return sb.toString();
        }
        if (state != 4) {
            return sb.toString();
        }
        if (z) {
            sb.append(context.getString(R.string.miuix_bottom_sheet_accessibility_scroll_up_to_half_screen));
            sb.append(ac.b);
        } else {
            sb.append(context.getString(R.string.miuix_bottom_sheet_accessibility_scroll_up_to_maximized));
            sb.append(ac.b);
        }
        if (this.bottomSheetBehavior.isHideable()) {
            sb.append(context.getString(R.string.miuix_bottom_sheet_accessibility_scroll_down_to_close));
            sb.append(ac.b);
        }
        sb.append(context.getString(R.string.miuix_bottom_sheet_accessibility_scroll_up_to_maximized));
        return sb.toString();
    }

    private static View getParentView(View view) {
        Object parent = view.getParent();
        if (parent instanceof View) {
            return (View) parent;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBottomSheetStateChanged(int i) {
        BottomSheetBehavior bottomSheetBehavior = this.bottomSheetBehavior;
        if (bottomSheetBehavior == null || bottomSheetBehavior.isDraggable()) {
            if (i == 4) {
                announceForAccessibility(getContext().getString(R.string.miuix_bottom_sheet_accessibility_switch_to_minimized));
            } else if (i == 3) {
                announceForAccessibility(getContext().getString(R.string.miuix_bottom_sheet_accessibility_switch_to_maximized));
            } else if (i == 6) {
                announceForAccessibility(getContext().getString(R.string.miuix_bottom_sheet_accessibility_switch_to_half_screen));
            }
            setContentDescription(getContentDescriptionByCurrentState());
        }
    }

    private void setBottomSheetBehavior(@Nullable BottomSheetBehavior<?> bottomSheetBehavior) {
        BottomSheetBehavior bottomSheetBehavior2 = this.bottomSheetBehavior;
        if (bottomSheetBehavior2 != null) {
            bottomSheetBehavior2.removeBottomSheetCallback(this.bottomSheetCallback);
            this.bottomSheetBehavior.setAccessibilityDelegateView(null);
        }
        this.bottomSheetBehavior = bottomSheetBehavior;
        if (bottomSheetBehavior != null) {
            onBottomSheetStateChanged(bottomSheetBehavior.getState());
            this.bottomSheetBehavior.addBottomSheetCallback(this.bottomSheetCallback);
        }
        updateInteractableState();
    }

    private void updateInteractableState() {
        BottomSheetBehavior bottomSheetBehavior = this.bottomSheetBehavior;
        ViewCompat.setImportantForAccessibility(this, (bottomSheetBehavior == null || !bottomSheetBehavior.isDraggable()) ? 2 : 1);
        setClickable(this.bottomSheetBehavior != null);
    }

    public static Context wrap(Context context) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(R.attr.bottomSheetDragHandleStyle, typedValue, true);
        if (typedValue.resourceId == 0) {
            if (AttributeResolver.resolveBoolean(context, R.attr.isLightTheme, true)) {
                theme.applyStyle(R.style.Widget_Miuix_BottomSheet_DragHandle_DefaultStyle_Light, false);
                return context;
            }
            theme.applyStyle(R.style.Widget_Miuix_BottomSheet_DragHandle_DefaultStyle_Dark, false);
        }
        return context;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [miuix.bottomsheet.BottomSheetDragHandleView] */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.view.View] */
    /* JADX WARN: Type inference failed for: r2v2, types: [android.view.View] */
    public final BottomSheetBehavior findParentBottomSheetBehavior() {
        while (true) {
            this = getParentView(this);
            if (this == 0) {
                return null;
            }
            ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
            if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
                CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
                if (behavior instanceof BottomSheetBehavior) {
                    return (BottomSheetBehavior) behavior;
                }
            }
        }
    }

    @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
    public void onAccessibilityStateChanged(boolean z) {
        updateInteractableState();
    }

    @Override // android.widget.ImageView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setBottomSheetBehavior(findParentBottomSheetBehavior());
        setContentDescription(getContentDescriptionByCurrentState());
        AccessibilityManager accessibilityManager = this.accessibilityManager;
        if (accessibilityManager != null) {
            accessibilityManager.addAccessibilityStateChangeListener(this);
            onAccessibilityStateChanged(this.accessibilityManager.isEnabled() && this.accessibilityManager.isTouchExplorationEnabled());
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDetachedFromWindow() {
        AccessibilityManager accessibilityManager = this.accessibilityManager;
        if (accessibilityManager != null) {
            accessibilityManager.removeAccessibilityStateChangeListener(this);
        }
        setBottomSheetBehavior(null);
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        setContentDescription(getContentDescriptionByCurrentState());
    }
}
