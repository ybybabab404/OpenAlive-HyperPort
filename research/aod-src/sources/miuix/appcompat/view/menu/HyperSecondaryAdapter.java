package miuix.appcompat.view.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import java.util.Map;
import miuix.appcompat.R;

/* JADX INFO: loaded from: classes2.dex */
public class HyperSecondaryAdapter extends HyperBaseAdapter {
    public View mHeaderView;
    public Map mSecondaryItemCheckedMap;

    public HyperSecondaryAdapter(LayoutInflater layoutInflater, List list, Map map) {
        super(layoutInflater, list);
        this.mSecondaryItemCheckedMap = map;
    }

    public View getHeaderView() {
        return this.mHeaderView;
    }

    @Override // miuix.appcompat.view.menu.HyperBaseAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2 = super.getView(i, view, viewGroup);
        if (i != 0) {
            view2.setId(-1);
            return view2;
        }
        view2.setId(R.id.tag_secondary_popup_menu_item_head);
        this.mHeaderView = view2;
        return view2;
    }

    public final void handleMultiSelectClick(int i, int i2, Boolean[] boolArr, HyperMenuContract.HyperMenuTextItem hyperMenuTextItem) {
        HyperMenuContract.HyperMenuTextItem hyperMenuTextItem2;
        miuix.appcompat.internal.view.menu.MenuItemImpl menuItem;
        int i3;
        for (int i4 = 2; i4 < this.mMenuItemList.size(); i4++) {
            HyperMenuContract.HyperMenuItem hyperMenuItem = (HyperMenuContract.HyperMenuItem) this.mMenuItemList.get(i4);
            if ((hyperMenuItem instanceof HyperMenuContract.HyperMenuTextItem) && (menuItem = (hyperMenuTextItem2 = (HyperMenuContract.HyperMenuTextItem) hyperMenuItem).getMenuItem()) != null && menuItem.isCheckable() && !hyperMenuTextItem2.isHeaderItem && hyperMenuTextItem2.getItemId() == i && (i3 = i4 - 2) < boolArr.length) {
                boolean zEquals = Boolean.TRUE.equals(boolArr[i3]);
                boolean z = !zEquals;
                boolArr[i3] = Boolean.valueOf(z);
                hyperMenuTextItem2.checkStatus = !zEquals ? HyperMenuContract.CheckableType.CHECKED : HyperMenuContract.CheckableType.NOT_CHECKED;
                menuItem.setChecked(z);
                break;
            }
        }
        if (hyperMenuTextItem != null && hyperMenuTextItem.showSubtitle && this.mMenuItemList.size() >= 2) {
            List list = this.mMenuItemList;
            updateHeaderSubtitle(hyperMenuTextItem, HyperMenuUtils.joinCheckedItemTitles(list.subList(2, list.size()), boolArr));
        }
        this.mSecondaryItemCheckedMap.put(Integer.valueOf(i2), boolArr);
    }

    public final void handleSingleSelectClick(int i, int i2, Boolean[] boolArr, HyperMenuContract.HyperMenuTextItem hyperMenuTextItem) {
        HyperMenuContract.HyperMenuTextItem hyperMenuTextItem2;
        miuix.appcompat.internal.view.menu.MenuItemImpl menuItem;
        for (int i3 = 0; i3 < this.mMenuItemList.size(); i3++) {
            HyperMenuContract.HyperMenuItem hyperMenuItem = (HyperMenuContract.HyperMenuItem) this.mMenuItemList.get(i3);
            if ((hyperMenuItem instanceof HyperMenuContract.HyperMenuTextItem) && (menuItem = (hyperMenuTextItem2 = (HyperMenuContract.HyperMenuTextItem) hyperMenuItem).getMenuItem()) != null && menuItem.isCheckable() && !hyperMenuTextItem2.isHeaderItem && i3 >= 2) {
                boolean z = hyperMenuTextItem2.getItemId() == i;
                boolArr[i3 - 2] = Boolean.valueOf(z);
                hyperMenuTextItem2.checkStatus = z ? HyperMenuContract.CheckableType.CHECKED : HyperMenuContract.CheckableType.NOT_CHECKED;
                menuItem.setChecked(hyperMenuTextItem2.isChecked());
                if (z && hyperMenuTextItem != null && hyperMenuTextItem.showSubtitle) {
                    updateHeaderSubtitle(hyperMenuTextItem, menuItem.getTitle());
                }
            }
        }
        this.mSecondaryItemCheckedMap.put(Integer.valueOf(i2), boolArr);
        notifyDataSetChanged();
    }

    public void resumeSecondaryItemClickStatus(int i) {
        List list = this.mMenuItemList;
        if (list == null || list.size() <= 2) {
            return;
        }
        int itemId = ((HyperMenuContract.HyperMenuItem) this.mMenuItemList.get(0)).getItemId();
        Boolean[] boolArr = (Boolean[]) this.mSecondaryItemCheckedMap.get(Integer.valueOf(itemId));
        if (boolArr == null) {
            boolArr = new Boolean[this.mMenuItemList.size() - 2];
        }
        HyperMenuContract.HyperMenuTextItem hyperMenuTextItem = this.mMenuItemList.get(0) instanceof HyperMenuContract.HyperMenuTextItem ? (HyperMenuContract.HyperMenuTextItem) this.mMenuItemList.get(0) : null;
        if (this.mMultiSelectEnabled) {
            handleMultiSelectClick(i, itemId, boolArr, hyperMenuTextItem);
        } else {
            handleSingleSelectClick(i, itemId, boolArr, hyperMenuTextItem);
        }
    }

    public final void updateHeaderSubtitle(HyperMenuContract.HyperMenuTextItem hyperMenuTextItem, CharSequence charSequence) {
        if (hyperMenuTextItem == null) {
            return;
        }
        hyperMenuTextItem.setSubtitle(charSequence);
        miuix.appcompat.internal.view.menu.MenuItemImpl menuItem = hyperMenuTextItem.getMenuItem();
        if (menuItem != null) {
            menuItem.setSubtitle(charSequence);
        }
    }
}
