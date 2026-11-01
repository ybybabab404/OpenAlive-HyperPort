package miuix.appcompat.view.menu;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import java.util.List;
import miuix.appcompat.R;
import miuix.internal.util.AnimHelper;

/* JADX INFO: loaded from: classes2.dex */
public class HyperBaseAdapter extends BaseAdapter {
    public LayoutInflater mInflater;
    public List mMenuItemList;
    public boolean mOptionalIconsVisible = true;
    public boolean mMultiSelectEnabled = false;
    public boolean mHeightAnimating = false;

    public class GroupTitleItemViewHolder {
        public TextView titleView;

        public GroupTitleItemViewHolder() {
        }
    }

    public class MenuDividerHolder {
        public MenuDividerHolder() {
        }
    }

    public class MenuItemViewHolder {
        public ImageView arrow;
        public ImageView checkMark;
        public ImageView iconView;
        public TextView subtitleView;
        public TextView titleView;

        public MenuItemViewHolder() {
        }
    }

    public HyperBaseAdapter() {
    }

    public HyperBaseAdapter(LayoutInflater layoutInflater, List list) {
        this.mInflater = layoutInflater;
        this.mMenuItemList = list;
    }

    public final void bindAccessibility(View view, HyperMenuContract.HyperMenuTextItem hyperMenuTextItem) {
        if (HyperMenuContract.CheckableType.NON_SUPPORT.equals(hyperMenuTextItem.checkStatus)) {
            setAccessibilityDelegateNonCheckable(view, hyperMenuTextItem);
        } else {
            setAccessibilityDelegateCheckable(view, hyperMenuTextItem);
        }
    }

    public final void bindCheckMark(MenuItemViewHolder menuItemViewHolder, HyperMenuContract.HyperMenuTextItem hyperMenuTextItem) {
        if (HyperMenuContract.CheckableType.NON_SUPPORT.equals(hyperMenuTextItem.checkStatus)) {
            menuItemViewHolder.checkMark.setVisibility(8);
        } else {
            menuItemViewHolder.checkMark.setVisibility(hyperMenuTextItem.isChecked() ? 0 : 4);
        }
    }

    public final void bindIcon(MenuItemViewHolder menuItemViewHolder, HyperMenuContract.HyperMenuTextItem hyperMenuTextItem) {
        if (!this.mOptionalIconsVisible || hyperMenuTextItem.getMenuItem() == null || hyperMenuTextItem.getMenuItem().getIcon() == null) {
            menuItemViewHolder.iconView.setVisibility(8);
            return;
        }
        menuItemViewHolder.iconView.setSelected(hyperMenuTextItem.getMenuItem().isChecked());
        menuItemViewHolder.iconView.setImageDrawable(hyperMenuTextItem.getMenuItem().getIcon());
        menuItemViewHolder.iconView.setEnabled(hyperMenuTextItem.getMenuItem().isEnabled());
        menuItemViewHolder.iconView.setVisibility(0);
    }

    public final void bindSubtitle(MenuItemViewHolder menuItemViewHolder, HyperMenuContract.HyperMenuTextItem hyperMenuTextItem) {
        if (!hyperMenuTextItem.showSubtitle) {
            menuItemViewHolder.subtitleView.setVisibility(8);
            return;
        }
        if (this.mHeightAnimating && (hyperMenuTextItem.isHeaderItem || hyperMenuTextItem.isExpandable)) {
            return;
        }
        CharSequence subtitle = hyperMenuTextItem.getSubtitle();
        if (TextUtils.isEmpty(subtitle)) {
            menuItemViewHolder.subtitleView.setVisibility(8);
            return;
        }
        menuItemViewHolder.subtitleView.setText(subtitle);
        menuItemViewHolder.subtitleView.setMaxLines(hyperMenuTextItem.isExpandable ? 1 : Integer.MAX_VALUE);
        menuItemViewHolder.subtitleView.setVisibility(0);
        if (hyperMenuTextItem.getMenuItem() != null) {
            menuItemViewHolder.subtitleView.setEnabled(hyperMenuTextItem.getMenuItem().isEnabled());
        }
        menuItemViewHolder.subtitleView.setSelected(hyperMenuTextItem.isChecked());
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mMenuItemList.size();
    }

    public HyperMenuContract.HyperMenuItem getHyperMenuItem(int i) {
        return (HyperMenuContract.HyperMenuItem) this.mMenuItemList.get(i);
    }

    @Override // android.widget.Adapter
    public MenuItem getItem(int i) {
        return ((HyperMenuContract.HyperMenuItem) this.mMenuItemList.get(i)).getMenuItem();
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return ((HyperMenuContract.HyperMenuItem) this.mMenuItemList.get(i)).getItemId();
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        HyperMenuContract.HyperMenuItem hyperMenuItem = (HyperMenuContract.HyperMenuItem) this.mMenuItemList.get(i);
        if (hyperMenuItem instanceof HyperMenuContract.HyperMenuDivider) {
            return 1;
        }
        return (hyperMenuItem == null || !HyperMenuUtils.isGroupTitleItem(hyperMenuItem.getMenuItem())) ? 0 : 2;
    }

    public boolean getOptionalIconsVisible() {
        return this.mOptionalIconsVisible;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        int itemViewType = getItemViewType(i);
        if (itemViewType == 0) {
            return handleMenuItem(i, view, viewGroup);
        }
        if (itemViewType == 1) {
            return handleDivider(view, viewGroup);
        }
        if (itemViewType == 2) {
            return handleGroupTitleItem(i, view, viewGroup);
        }
        return null;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        return 3;
    }

    public final View handleDivider(View view, ViewGroup viewGroup) {
        if (view != null && view.getTag().getClass() == MenuDividerHolder.class) {
            return view;
        }
        MenuDividerHolder menuDividerHolder = new MenuDividerHolder();
        View viewInflate = this.mInflater.inflate(R.layout.miuix_appcompat_popup_menu_divider, viewGroup, false);
        viewInflate.setTag(menuDividerHolder);
        return viewInflate;
    }

    public final View handleGroupTitleItem(int i, View view, ViewGroup viewGroup) {
        GroupTitleItemViewHolder groupTitleItemViewHolder;
        HyperMenuContract.HyperMenuItem hyperMenuItem = (HyperMenuContract.HyperMenuItem) this.mMenuItemList.get(i);
        HyperMenuContract.HyperMenuTextItem hyperMenuTextItem = hyperMenuItem instanceof HyperMenuContract.HyperMenuTextItem ? (HyperMenuContract.HyperMenuTextItem) hyperMenuItem : null;
        miuix.appcompat.internal.view.menu.MenuItemImpl menuItem = hyperMenuTextItem != null ? hyperMenuTextItem.getMenuItem() : null;
        if (view == null || view.getTag().getClass() != GroupTitleItemViewHolder.class) {
            GroupTitleItemViewHolder groupTitleItemViewHolder2 = new GroupTitleItemViewHolder();
            view = this.mInflater.inflate(R.layout.miuix_appcompat_hyper_popup_menu_group_title_item, viewGroup, false);
            groupTitleItemViewHolder2.titleView = (TextView) view.findViewById(android.R.id.text1);
            view.setTag(groupTitleItemViewHolder2);
            groupTitleItemViewHolder = groupTitleItemViewHolder2;
        } else {
            groupTitleItemViewHolder = (GroupTitleItemViewHolder) view.getTag();
        }
        TextView textView = groupTitleItemViewHolder.titleView;
        if (textView != null && menuItem != null) {
            textView.setText(menuItem.getTitle());
        }
        return view;
    }

    public final View handleMenuItem(int i, View view, ViewGroup viewGroup) {
        MenuItemViewHolder menuItemViewHolderInflateMenuItemView;
        HyperMenuContract.HyperMenuTextItem hyperMenuTextItem = (HyperMenuContract.HyperMenuTextItem) this.mMenuItemList.get(i);
        if (view == null || view.getTag().getClass() != MenuItemViewHolder.class) {
            view = this.mInflater.inflate(R.layout.miuix_appcompat_hyper_popup_menu_item, viewGroup, false);
            menuItemViewHolderInflateMenuItemView = inflateMenuItemView(view);
        } else {
            menuItemViewHolderInflateMenuItemView = (MenuItemViewHolder) view.getTag();
        }
        if (hyperMenuTextItem != null) {
            miuix.appcompat.internal.view.menu.MenuItemImpl menuItem = hyperMenuTextItem.getMenuItem();
            if (menuItem != null) {
                view.setEnabled(menuItem.isEnabled());
                menuItemViewHolderInflateMenuItemView.titleView.setText(menuItem.getTitle());
                menuItemViewHolderInflateMenuItemView.titleView.setEnabled(menuItem.isEnabled());
            }
            menuItemViewHolderInflateMenuItemView.titleView.setSelected(hyperMenuTextItem.isChecked());
            bindSubtitle(menuItemViewHolderInflateMenuItemView, hyperMenuTextItem);
            bindCheckMark(menuItemViewHolderInflateMenuItemView, hyperMenuTextItem);
            bindIcon(menuItemViewHolderInflateMenuItemView, hyperMenuTextItem);
            menuItemViewHolderInflateMenuItemView.arrow.setVisibility((hyperMenuTextItem.isExpandable || hyperMenuTextItem.isHeaderItem) ? 0 : 8);
            bindAccessibility(view, hyperMenuTextItem);
        }
        return view;
    }

    public final MenuItemViewHolder inflateMenuItemView(View view) {
        MenuItemViewHolder menuItemViewHolder = new MenuItemViewHolder();
        menuItemViewHolder.titleView = (TextView) view.findViewById(android.R.id.text1);
        menuItemViewHolder.subtitleView = (TextView) view.findViewById(android.R.id.text2);
        menuItemViewHolder.iconView = (ImageView) view.findViewById(android.R.id.icon);
        menuItemViewHolder.checkMark = (ImageView) view.findViewById(R.id.check_mark);
        menuItemViewHolder.arrow = (ImageView) view.findViewById(R.id.arrow);
        view.setTag(menuItemViewHolder);
        AnimHelper.addItemPressEffect(view, true);
        return menuItemViewHolder;
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int i) {
        return getItemViewType(i) == 0;
    }

    public boolean isMultiSelectEnabled() {
        return this.mMultiSelectEnabled;
    }

    public final void setAccessibilityDelegateCheckable(View view, final HyperMenuContract.HyperMenuTextItem hyperMenuTextItem) {
        ViewCompat.setAccessibilityDelegate(view, new AccessibilityDelegateCompat() { // from class: miuix.appcompat.view.menu.HyperBaseAdapter.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCheckable(true);
                accessibilityNodeInfoCompat.setChecked(hyperMenuTextItem.isChecked());
                if (hyperMenuTextItem.getMenuItem() != null) {
                    CharSequence title = hyperMenuTextItem.getMenuItem().getTitle();
                    if (!TextUtils.isEmpty(title)) {
                        accessibilityNodeInfoCompat.setContentDescription(title);
                    }
                }
                if (hyperMenuTextItem.isChecked()) {
                    return;
                }
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
            }
        });
    }

    public final void setAccessibilityDelegateNonCheckable(View view, final HyperMenuContract.HyperMenuTextItem hyperMenuTextItem) {
        ViewCompat.setAccessibilityDelegate(view, new AccessibilityDelegateCompat() { // from class: miuix.appcompat.view.menu.HyperBaseAdapter.2
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setClickable(true);
                HyperMenuContract.HyperMenuTextItem hyperMenuTextItem2 = hyperMenuTextItem;
                if (hyperMenuTextItem2 != null) {
                    String string = hyperMenuTextItem2.getMenuItem() != null ? hyperMenuTextItem.getMenuItem().getTitle().toString() : null;
                    if (!TextUtils.isEmpty(string)) {
                        accessibilityNodeInfoCompat.setContentDescription(string);
                    }
                    HyperMenuContract.HyperMenuTextItem hyperMenuTextItem3 = hyperMenuTextItem;
                    if (hyperMenuTextItem3.isExpandable && !hyperMenuTextItem3.isHeaderItem) {
                        accessibilityNodeInfoCompat.setStateDescription(view2.getContext().getResources().getString(R.string.miuix_appcompat_accessibility_collapse_state));
                    } else if (hyperMenuTextItem3.isHeaderItem) {
                        accessibilityNodeInfoCompat.setStateDescription(view2.getContext().getResources().getString(R.string.miuix_appcompat_accessibility_expand_state));
                    }
                }
            }
        });
    }

    public void setHeightAnimating(boolean z) {
        this.mHeightAnimating = z;
    }

    public void setMultiSelectEnabled(boolean z) {
        this.mMultiSelectEnabled = z;
    }

    public void setOptionalIconsVisible(boolean z) {
        this.mOptionalIconsVisible = z;
    }
}
