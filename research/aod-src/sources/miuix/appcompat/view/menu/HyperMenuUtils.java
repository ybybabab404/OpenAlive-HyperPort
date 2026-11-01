package miuix.appcompat.view.menu;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.SubMenu;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class HyperMenuUtils {
    public static boolean adjustListItemIsDiff(ArrayList arrayList, ArrayList arrayList2) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (!arrayList2.contains((miuix.appcompat.internal.view.menu.MenuItemImpl) arrayList.get(i))) {
                return true;
            }
        }
        return false;
    }

    public static void appendItemTitle(StringBuilder sb, HyperMenuContract.HyperMenuItem hyperMenuItem) {
        if (hyperMenuItem == null || hyperMenuItem.getMenuItem() == null || hyperMenuItem.getMenuItem().getTitle() == null) {
            return;
        }
        if (sb.length() > 0) {
            sb.append("、");
        }
        sb.append(hyperMenuItem.getMenuItem().getTitle());
    }

    public static void checkPrimaryItemVisibility(miuix.appcompat.internal.view.menu.MenuBuilder menuBuilder) {
        boolean zSubMenuHasAnyVisibleItem;
        if (menuBuilder == null || menuBuilder.size() == 0) {
            return;
        }
        for (int i = 0; i < menuBuilder.size(); i++) {
            MenuItem item = menuBuilder.getItem(i);
            boolean zIsVisible = item.isVisible();
            if (item.hasSubMenu() && zIsVisible != (zSubMenuHasAnyVisibleItem = subMenuHasAnyVisibleItem(item.getSubMenu()))) {
                item.setVisible(zSubMenuHasAnyVisibleItem);
            }
        }
        if (visibleItemListIsChanged(menuBuilder)) {
            menuBuilder.updateVisibleItemCountLimit();
        }
    }

    public static CharSequence findFirstCheckedItemTitle(List list, Boolean[] boolArr) {
        HyperMenuContract.HyperMenuItem hyperMenuItem;
        if (list != null && boolArr != null) {
            int iMin = Math.min(list.size(), boolArr.length);
            for (int i = 0; i < iMin; i++) {
                if (Boolean.TRUE.equals(boolArr[i]) && (hyperMenuItem = (HyperMenuContract.HyperMenuItem) list.get(i)) != null && hyperMenuItem.getMenuItem() != null) {
                    return hyperMenuItem.getMenuItem().getTitle();
                }
            }
        }
        return null;
    }

    public static int getGroupIndexAndUpdateStatus(ArrayList arrayList, Map map, int i, boolean z) {
        int i2 = -1;
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            HyperMenuContract.HyperMenuItem hyperMenuItem = (HyperMenuContract.HyperMenuItem) arrayList.get(i3);
            if (hyperMenuItem instanceof HyperMenuContract.HyperMenuTextItem) {
                HyperMenuContract.HyperMenuTextItem hyperMenuTextItem = (HyperMenuContract.HyperMenuTextItem) hyperMenuItem;
                miuix.appcompat.internal.view.menu.MenuItemImpl menuItem = hyperMenuTextItem.getMenuItem();
                if (hyperMenuTextItem.getItemId() == i) {
                    i2 = i3;
                }
                if (menuItem != null) {
                    if (menuItem.isCheckable() && !hyperMenuTextItem.isExpandable) {
                        hyperMenuTextItem.checkStatus = hyperMenuTextItem.getItemId() == i ? HyperMenuContract.CheckableType.CHECKED : HyperMenuContract.CheckableType.NOT_CHECKED;
                        menuItem.setChecked(hyperMenuTextItem.isChecked());
                    }
                    if (z) {
                        map.put(Integer.valueOf(menuItem.getItemId()), Boolean.valueOf(menuItem.getItemId() == i));
                    }
                }
            }
        }
        return i2;
    }

    public static void getRecentVisibleItems(miuix.appcompat.internal.view.menu.MenuBuilder menuBuilder, ArrayList arrayList) {
        for (int i = 0; i < menuBuilder.size(); i++) {
            MenuItem item = menuBuilder.getItem(i);
            if (item.isVisible() && (item instanceof miuix.appcompat.internal.view.menu.MenuItemImpl)) {
                arrayList.add((miuix.appcompat.internal.view.menu.MenuItemImpl) item);
            }
        }
    }

    public static boolean hasAnyVisibleSubMenuItem(List list) {
        if (list != null && !list.isEmpty()) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                miuix.appcompat.internal.view.menu.MenuItemImpl menuItem = ((HyperMenuContract.HyperMenuItem) it.next()).getMenuItem();
                if (menuItem != null && menuItem.isVisible()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isGroupTitleItem(miuix.appcompat.internal.view.menu.MenuItemImpl menuItemImpl) {
        if (menuItemImpl == null || menuItemImpl.getTitle() == null) {
            return false;
        }
        return TextUtils.equals(menuItemImpl.getTitleCondensed(), "#" + ((Object) menuItemImpl.getTitle()) + "!");
    }

    public static CharSequence joinCheckedItemTitles(List list, Boolean[] boolArr) {
        if (list != null && boolArr != null) {
            StringBuilder sb = new StringBuilder();
            int iMin = Math.min(list.size(), boolArr.length);
            for (int i = 0; i < iMin; i++) {
                if (Boolean.TRUE.equals(boolArr[i])) {
                    appendItemTitle(sb, (HyperMenuContract.HyperMenuItem) list.get(i));
                }
            }
            if (sb.length() > 0) {
                return sb.toString();
            }
        }
        return null;
    }

    public static void setMenuItemChecked(miuix.appcompat.internal.view.menu.MenuItemImpl menuItemImpl, boolean z) {
        if (menuItemImpl == null || menuItemImpl.isChecked() == z) {
            return;
        }
        menuItemImpl.setChecked(z);
    }

    public static boolean subMenuHasAnyVisibleItem(SubMenu subMenu) {
        if (subMenu != null && subMenu.size() != 0) {
            for (int i = 0; i < subMenu.size(); i++) {
                MenuItem item = subMenu.getItem(i);
                if (item != null && item.isVisible()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void updatePrimaryGroupStatusByPosition(Map map, int i, int i2, int i3) {
        int i4 = i - i2;
        int i5 = ((i + i3) - i2) - 1;
        int i6 = i4;
        while (i6 >= i4 && i6 <= i5) {
            map.put(Integer.valueOf(i6), Boolean.valueOf(i6 == i4 + i2));
            i6++;
        }
    }

    public static boolean visibleItemListIsChanged(miuix.appcompat.internal.view.menu.MenuBuilder menuBuilder) {
        if (menuBuilder == null || menuBuilder.size() == 0) {
            return false;
        }
        ArrayList visibleItems = menuBuilder.getVisibleItems();
        ArrayList arrayList = new ArrayList();
        getRecentVisibleItems(menuBuilder, arrayList);
        if (visibleItems == null || visibleItems.isEmpty()) {
            return true ^ arrayList.isEmpty();
        }
        if (visibleItems.size() != arrayList.size()) {
            return true;
        }
        return adjustListItemIsDiff(arrayList, visibleItems);
    }
}
