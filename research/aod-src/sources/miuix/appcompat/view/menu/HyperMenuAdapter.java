package miuix.appcompat.view.menu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class HyperMenuAdapter extends HyperBaseAdapter {
    public final Map mGroupIdOverrideMap;
    public Map mGroupedMap;
    public boolean mOverflowOnly;
    public boolean mPrimaryCheckedMapFirstModified;
    public Map mPrimaryItemCheckedMap;
    public List mPrimaryMenuItems;
    public boolean mSavePrimaryStatusByIdEnabled;
    public boolean mSecondaryCheckedMapFirstModified;
    public Map mSecondaryItemCheckedMap;
    public Map mSecondaryMenuMap;
    public Map mUserPreCheckedPrimaryMap;
    public Map mUserPreCheckedSecondaryMap;

    public HyperMenuAdapter(Context context) {
        this(context, null, false);
    }

    public HyperMenuAdapter(Context context, miuix.appcompat.internal.view.menu.MenuBuilder menuBuilder, boolean z) {
        this.mPrimaryMenuItems = new ArrayList();
        this.mSecondaryMenuMap = new HashMap();
        this.mGroupedMap = new HashMap();
        this.mGroupIdOverrideMap = new HashMap();
        this.mPrimaryItemCheckedMap = new HashMap();
        this.mSavePrimaryStatusByIdEnabled = false;
        this.mSecondaryItemCheckedMap = new HashMap();
        this.mPrimaryCheckedMapFirstModified = true;
        this.mSecondaryCheckedMapFirstModified = true;
        this.mInflater = LayoutInflater.from(context);
        this.mMenuItemList = this.mPrimaryMenuItems;
        this.mOverflowOnly = z;
        if (menuBuilder != null) {
            buildGroupedMenuItems(menuBuilder);
        }
    }

    public final void assembleGroupData(Map map, ArrayList arrayList) {
        int iIntValue;
        int intExtra;
        this.mGroupIdOverrideMap.clear();
        for (int i = 0; i < arrayList.size(); i++) {
            miuix.appcompat.internal.view.menu.MenuItemImpl menuItemImpl = (miuix.appcompat.internal.view.menu.MenuItemImpl) arrayList.get(i);
            int groupId = menuItemImpl.getGroupId();
            Intent intent = menuItemImpl.getIntent();
            CharSequence subtitle = menuItemImpl.getSubtitle();
            if (intent != null) {
                iIntValue = intent.getIntExtra("miuix:hyperMenu:groupId", -1);
                if (iIntValue != -1) {
                    this.mGroupIdOverrideMap.put(Integer.valueOf(groupId), Integer.valueOf(iIntValue));
                } else {
                    iIntValue = groupId;
                }
                intExtra = intent.getIntExtra("miuix:hyperMenu:itemForeignKey", -1);
                if (subtitle == null) {
                    subtitle = intent.getStringExtra("miuix:hyperMenu:itemSubtitle");
                }
            } else {
                iIntValue = groupId;
                intExtra = -1;
            }
            if (iIntValue == groupId && this.mGroupIdOverrideMap.containsKey(Integer.valueOf(groupId)) && this.mGroupIdOverrideMap.get(Integer.valueOf(groupId)) != null) {
                iIntValue = ((Integer) this.mGroupIdOverrideMap.get(Integer.valueOf(groupId))).intValue();
            }
            ArrayList arrayList2 = (ArrayList) map.get(Integer.valueOf(iIntValue));
            if (arrayList2 == null) {
                arrayList2 = new ArrayList();
            }
            HyperMenuContract.HyperMenuTextItem hyperMenuTextItem = new HyperMenuContract.HyperMenuTextItem(menuItemImpl);
            if (intExtra != -1) {
                hyperMenuTextItem.isExpandable = true;
                hyperMenuTextItem.itemForeignKey = intExtra;
            } else {
                hyperMenuTextItem.isExpandable = false;
                hyperMenuTextItem.itemForeignKey = -1;
            }
            hyperMenuTextItem.setSubtitle(subtitle);
            if (intent != null) {
                hyperMenuTextItem.showSubtitle = intent.getBooleanExtra("miuix:hyperMenu:itemShowSubtitle", true);
            }
            arrayList2.add(hyperMenuTextItem);
            map.put(Integer.valueOf(iIntValue), arrayList2);
        }
        propagateGroupMultiSelect(map);
    }

    public final void assembleSecondaryMenu(Map map, ArrayList arrayList) {
        ArrayList<miuix.appcompat.internal.view.menu.MenuItemImpl> arrayList2 = new ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            miuix.appcompat.internal.view.menu.MenuItemImpl menuItemImpl = (miuix.appcompat.internal.view.menu.MenuItemImpl) arrayList.get(i);
            int groupForeignKey = getGroupForeignKey(menuItemImpl);
            if (groupForeignKey != -1 && menuItemImpl != null) {
                ArrayList arrayList3 = (ArrayList) map.get(Integer.valueOf(menuItemImpl.getGroupId()));
                HyperMenuContract.HyperMenuItem hyperMenuItemFindPrimaryItemByForeignKey = findPrimaryItemByForeignKey(this.mPrimaryMenuItems, groupForeignKey);
                if (arrayList3 != null && hyperMenuItemFindPrimaryItemByForeignKey != null && hyperMenuItemFindPrimaryItemByForeignKey.getMenuItem() != null) {
                    if (hyperMenuItemFindPrimaryItemByForeignKey.getMenuItem().isVisible() != HyperMenuUtils.hasAnyVisibleSubMenuItem(arrayList3)) {
                        arrayList2.add(hyperMenuItemFindPrimaryItemByForeignKey.getMenuItem());
                    }
                    buildSecondaryAdapter(arrayList3, hyperMenuItemFindPrimaryItemByForeignKey);
                }
            }
        }
        for (miuix.appcompat.internal.view.menu.MenuItemImpl menuItemImpl2 : arrayList2) {
            menuItemImpl2.setVisible(!menuItemImpl2.isVisible());
        }
        arrayList2.clear();
    }

    public final void buildDefaultSecondaryMenuData(Map map, ArrayList arrayList, ArrayList arrayList2) {
        Intent intent;
        ArrayList<miuix.appcompat.internal.view.menu.MenuItemImpl> arrayList3 = new ArrayList();
        for (int i = 0; i < arrayList.size(); i++) {
            miuix.appcompat.internal.view.menu.MenuItemImpl menuItemImpl = (miuix.appcompat.internal.view.menu.MenuItemImpl) arrayList.get(i);
            int groupId = menuItemImpl.getGroupId();
            if (!arrayList2.contains(Integer.valueOf(groupId))) {
                arrayList2.add(Integer.valueOf(groupId));
            }
            ArrayList arrayList4 = (ArrayList) map.get(Integer.valueOf(groupId));
            if (arrayList4 == null) {
                arrayList4 = new ArrayList();
            }
            boolean zHasSubMenu = menuItemImpl.hasSubMenu();
            HyperMenuContract.HyperMenuTextItem hyperMenuTextItem = new HyperMenuContract.HyperMenuTextItem(menuItemImpl);
            hyperMenuTextItem.isExpandable = zHasSubMenu;
            CharSequence subtitle = menuItemImpl.getSubtitle();
            if (subtitle == null && (intent = menuItemImpl.getIntent()) != null) {
                subtitle = intent.getStringExtra("miuix:hyperMenu:itemSubtitle");
            }
            hyperMenuTextItem.setSubtitle(subtitle);
            Intent intent2 = menuItemImpl.getIntent();
            if (intent2 != null) {
                hyperMenuTextItem.showSubtitle = intent2.getBooleanExtra("miuix:hyperMenu:itemShowSubtitle", true);
            }
            arrayList4.add(hyperMenuTextItem);
            map.put(Integer.valueOf(groupId), arrayList4);
            ArrayList arrayListBuildDefaultSubMenuData = (zHasSubMenu && (menuItemImpl.getSubMenu() instanceof miuix.appcompat.internal.view.menu.SubMenuBuilder)) ? buildDefaultSubMenuData((miuix.appcompat.internal.view.menu.SubMenuBuilder) menuItemImpl.getSubMenu(), menuItemImpl.getItemId()) : null;
            if (zHasSubMenu && menuItemImpl.isVisible() != HyperMenuUtils.hasAnyVisibleSubMenuItem(arrayListBuildDefaultSubMenuData)) {
                arrayList3.add(menuItemImpl);
            }
            if (arrayListBuildDefaultSubMenuData != null) {
                if (hyperMenuTextItem.showSubtitle) {
                    Intent intent3 = menuItemImpl.getIntent();
                    CharSequence checkedItemTitle = getCheckedItemTitle(arrayListBuildDefaultSubMenuData, menuItemImpl.getItemId(), intent3 != null ? intent3.getBooleanExtra("miuix:hyperMenu:itemMultiSelect", false) : false);
                    if (checkedItemTitle != null) {
                        hyperMenuTextItem.setSubtitle(checkedItemTitle);
                        menuItemImpl.setSubtitle(checkedItemTitle);
                    }
                }
                HyperMenuContract.HyperMenuTextItem hyperMenuTextItem2 = new HyperMenuContract.HyperMenuTextItem(menuItemImpl);
                hyperMenuTextItem2.isHeaderItem = true;
                hyperMenuTextItem2.setSubtitle(hyperMenuTextItem.getSubtitle());
                hyperMenuTextItem2.showSubtitle = hyperMenuTextItem.showSubtitle;
                arrayListBuildDefaultSubMenuData.add(0, hyperMenuTextItem2);
                arrayListBuildDefaultSubMenuData.add(1, new HyperMenuContract.HyperMenuDivider());
                HyperSecondaryAdapter hyperSecondaryAdapter = new HyperSecondaryAdapter(this.mInflater, arrayListBuildDefaultSubMenuData, this.mSecondaryItemCheckedMap);
                hyperSecondaryAdapter.setOptionalIconsVisible(getOptionalIconsVisible());
                Intent intent4 = menuItemImpl.getIntent();
                hyperSecondaryAdapter.setMultiSelectEnabled(intent4 != null ? intent4.getBooleanExtra("miuix:hyperMenu:itemMultiSelect", false) : false);
                this.mSecondaryMenuMap.put(Integer.valueOf(menuItemImpl.getItemId()), hyperSecondaryAdapter);
            }
        }
        for (miuix.appcompat.internal.view.menu.MenuItemImpl menuItemImpl2 : arrayList3) {
            menuItemImpl2.setVisible(!menuItemImpl2.isVisible());
        }
        arrayList3.clear();
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0048  */
    /* JADX WARN: Code duplicated, block: B:25:0x0051 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:26:0x0053  */
    /* JADX WARN: Code duplicated, block: B:34:0x007d  */
    /* JADX WARN: Code duplicated, block: B:37:0x008b  */
    /* JADX WARN: Code duplicated, block: B:39:0x0095  */
    /* JADX WARN: Code duplicated, block: B:40:0x0098  */
    /* JADX WARN: Code duplicated, block: B:49:0x00a6 A[SYNTHETIC] */
    public final ArrayList buildDefaultSubMenuData(miuix.appcompat.internal.view.menu.SubMenuBuilder subMenuBuilder, int i) {
        boolean z;
        miuix.appcompat.internal.view.menu.MenuItemImpl menuItemImpl;
        HyperMenuContract.HyperMenuTextItem hyperMenuTextItem;
        CharSequence subtitle;
        Intent intent;
        HyperMenuContract.CheckableType checkableType;
        Intent intent2;
        if (subMenuBuilder != null && i != -1) {
            ArrayList arrayList = new ArrayList();
            ArrayList visibleItems = subMenuBuilder.getVisibleItems();
            if (visibleItems != null && visibleItems.size() != 0) {
                Boolean[] boolArr = (Boolean[]) this.mSecondaryItemCheckedMap.get(Integer.valueOf(i));
                if (boolArr != null) {
                    if (boolArr.length != visibleItems.size()) {
                        boolArr = new Boolean[visibleItems.size()];
                    } else {
                        z = false;
                    }
                    for (int i2 = 0; i2 < visibleItems.size(); i2++) {
                        menuItemImpl = (miuix.appcompat.internal.view.menu.MenuItemImpl) visibleItems.get(i2);
                        if (menuItemImpl != null) {
                            if (z) {
                                boolArr[i2] = Boolean.valueOf(menuItemImpl.isChecked());
                            }
                            hyperMenuTextItem = new HyperMenuContract.HyperMenuTextItem(menuItemImpl);
                            subtitle = menuItemImpl.getSubtitle();
                            if (subtitle == null && (intent2 = menuItemImpl.getIntent()) != null) {
                                subtitle = intent2.getStringExtra("miuix:hyperMenu:itemSubtitle");
                            }
                            hyperMenuTextItem.setSubtitle(subtitle);
                            intent = menuItemImpl.getIntent();
                            if (intent != null) {
                                hyperMenuTextItem.showSubtitle = intent.getBooleanExtra("miuix:hyperMenu:itemShowSubtitle", true);
                            }
                            if (menuItemImpl.isCheckable()) {
                                if (Boolean.TRUE.equals(boolArr[i2])) {
                                    checkableType = HyperMenuContract.CheckableType.CHECKED;
                                } else {
                                    checkableType = HyperMenuContract.CheckableType.NOT_CHECKED;
                                }
                                hyperMenuTextItem.checkStatus = checkableType;
                                HyperMenuUtils.setMenuItemChecked(menuItemImpl, hyperMenuTextItem.isChecked());
                            }
                            arrayList.add(hyperMenuTextItem);
                        }
                    }
                    this.mSecondaryItemCheckedMap.put(Integer.valueOf(i), boolArr);
                    return arrayList;
                }
                boolArr = new Boolean[visibleItems.size()];
                z = true;
                while (i2 < visibleItems.size()) {
                    menuItemImpl = (miuix.appcompat.internal.view.menu.MenuItemImpl) visibleItems.get(i2);
                    if (menuItemImpl != null) {
                        if (z) {
                            boolArr[i2] = Boolean.valueOf(menuItemImpl.isChecked());
                        }
                        hyperMenuTextItem = new HyperMenuContract.HyperMenuTextItem(menuItemImpl);
                        subtitle = menuItemImpl.getSubtitle();
                        if (subtitle == null) {
                            subtitle = intent2.getStringExtra("miuix:hyperMenu:itemSubtitle");
                        }
                        hyperMenuTextItem.setSubtitle(subtitle);
                        intent = menuItemImpl.getIntent();
                        if (intent != null) {
                            hyperMenuTextItem.showSubtitle = intent.getBooleanExtra("miuix:hyperMenu:itemShowSubtitle", true);
                        }
                        if (menuItemImpl.isCheckable()) {
                            if (Boolean.TRUE.equals(boolArr[i2])) {
                                checkableType = HyperMenuContract.CheckableType.CHECKED;
                            } else {
                                checkableType = HyperMenuContract.CheckableType.NOT_CHECKED;
                            }
                            hyperMenuTextItem.checkStatus = checkableType;
                            HyperMenuUtils.setMenuItemChecked(menuItemImpl, hyperMenuTextItem.isChecked());
                        }
                        arrayList.add(hyperMenuTextItem);
                    }
                }
                this.mSecondaryItemCheckedMap.put(Integer.valueOf(i), boolArr);
                return arrayList;
            }
        }
        return null;
    }

    public final void buildGroupedMenuItems(miuix.appcompat.internal.view.menu.MenuBuilder menuBuilder) {
        Map map;
        if (menuBuilder == null || (map = this.mSecondaryMenuMap) == null || this.mPrimaryMenuItems == null || this.mGroupedMap == null) {
            return;
        }
        map.clear();
        this.mPrimaryMenuItems.clear();
        this.mGroupedMap.clear();
        HyperMenuUtils.checkPrimaryItemVisibility(menuBuilder);
        ArrayList nonActionItems = this.mOverflowOnly ? menuBuilder.getNonActionItems() : menuBuilder.getVisibleItems();
        if (nonActionItems != null) {
            assembleGroupData(this.mGroupedMap, nonActionItems);
        }
        selectPrimaryMenu(this.mGroupedMap);
        if (nonActionItems != null) {
            assembleSecondaryMenu(this.mGroupedMap, nonActionItems);
        }
        removeInvalidatePrimaryItem(true);
    }

    public final void buildMenuItems(miuix.appcompat.internal.view.menu.MenuBuilder menuBuilder) {
        List list;
        if (menuBuilder == null || this.mSecondaryMenuMap == null || (list = this.mPrimaryMenuItems) == null || this.mGroupedMap == null) {
            return;
        }
        list.clear();
        this.mSecondaryMenuMap.clear();
        this.mGroupedMap.clear();
        HyperMenuUtils.checkPrimaryItemVisibility(menuBuilder);
        ArrayList nonActionItems = this.mOverflowOnly ? menuBuilder.getNonActionItems() : menuBuilder.getVisibleItems();
        ArrayList arrayList = new ArrayList();
        if (nonActionItems != null) {
            buildDefaultSecondaryMenuData(this.mGroupedMap, nonActionItems, arrayList);
        }
        for (int i = 0; i < arrayList.size(); i++) {
            List listFilterVisibleItems = filterVisibleItems((ArrayList) this.mGroupedMap.get(arrayList.get(i)));
            if (!listFilterVisibleItems.isEmpty()) {
                List list2 = this.mPrimaryMenuItems;
                list2.addAll(list2.size(), listFilterVisibleItems);
                this.mPrimaryMenuItems.add(new HyperMenuContract.HyperMenuDivider());
            }
        }
        if (!this.mPrimaryMenuItems.isEmpty()) {
            List list3 = this.mPrimaryMenuItems;
            list3.remove(list3.size() - 1);
        }
        handleDefaultCheckedStatus(this.mPrimaryMenuItems, true, -1);
        removeInvalidatePrimaryItem(false);
    }

    public final void buildSecondaryAdapter(ArrayList arrayList, HyperMenuContract.HyperMenuItem hyperMenuItem) {
        ArrayList arrayList2 = new ArrayList(arrayList);
        handleDefaultCheckedStatus(arrayList2, false, hyperMenuItem.getMenuItem().getItemId());
        boolean zIsGroupMultiSelect = isGroupMultiSelect(arrayList);
        syncPrimarySubtitle(hyperMenuItem, arrayList2, zIsGroupMultiSelect);
        HyperMenuContract.HyperMenuTextItem hyperMenuTextItem = new HyperMenuContract.HyperMenuTextItem(hyperMenuItem.getMenuItem());
        hyperMenuTextItem.isHeaderItem = true;
        if (hyperMenuItem instanceof HyperMenuContract.HyperMenuTextItem) {
            HyperMenuContract.HyperMenuTextItem hyperMenuTextItem2 = (HyperMenuContract.HyperMenuTextItem) hyperMenuItem;
            hyperMenuTextItem.setSubtitle(hyperMenuTextItem2.getSubtitle());
            hyperMenuTextItem.showSubtitle = hyperMenuTextItem2.showSubtitle;
        }
        arrayList2.add(0, hyperMenuTextItem);
        arrayList2.add(1, new HyperMenuContract.HyperMenuDivider());
        HyperSecondaryAdapter hyperSecondaryAdapter = new HyperSecondaryAdapter(this.mInflater, arrayList2, this.mSecondaryItemCheckedMap);
        hyperSecondaryAdapter.setOptionalIconsVisible(getOptionalIconsVisible());
        hyperSecondaryAdapter.setMultiSelectEnabled(zIsGroupMultiSelect);
        this.mSecondaryMenuMap.put(Integer.valueOf(hyperMenuItem.getItemId()), hyperSecondaryAdapter);
    }

    public void copyPrimaryCheckedData(Map map) {
        Map map2;
        if (map == null || (map2 = this.mPrimaryItemCheckedMap) == null) {
            return;
        }
        for (Integer num : map2.keySet()) {
            num.intValue();
            Boolean bool = (Boolean) this.mPrimaryItemCheckedMap.get(num);
            if (bool != null) {
                map.put(num, bool);
            }
        }
    }

    public void copySecondaryCheckedData(Map map) {
        Map map2;
        if (map == null || (map2 = this.mSecondaryItemCheckedMap) == null) {
            return;
        }
        for (Integer num : map2.keySet()) {
            num.intValue();
            Boolean[] boolArr = (Boolean[]) this.mSecondaryItemCheckedMap.get(num);
            if (boolArr != null && boolArr.length > 0) {
                Boolean[] boolArr2 = new Boolean[boolArr.length];
                System.arraycopy(boolArr, 0, boolArr2, 0, boolArr.length);
                map.put(num, boolArr2);
            }
        }
    }

    public final List filterVisibleItems(List list) {
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                HyperMenuContract.HyperMenuItem hyperMenuItem = (HyperMenuContract.HyperMenuItem) it.next();
                if (hyperMenuItem.getMenuItem() != null && hyperMenuItem.getMenuItem().isVisible()) {
                    arrayList.add(hyperMenuItem);
                }
            }
        }
        return arrayList;
    }

    public final HyperMenuContract.HyperMenuItem findPrimaryItemByForeignKey(List list, int i) {
        int i2 = 0;
        while (true) {
            if (i2 >= list.size()) {
                return null;
            }
            HyperMenuContract.HyperMenuItem hyperMenuItem = (HyperMenuContract.HyperMenuItem) list.get(i2);
            Intent intent = hyperMenuItem.getMenuItem() != null ? hyperMenuItem.getMenuItem().getIntent() : null;
            int intExtra = intent != null ? intent.getIntExtra("miuix:hyperMenu:itemForeignKey", -1) : -1;
            if (intExtra != -1 && intExtra == i) {
                return hyperMenuItem;
            }
            i2++;
        }
    }

    public final CharSequence getCheckedItemTitle(List list, int i, boolean z) {
        Boolean[] boolArr = (Boolean[]) this.mSecondaryItemCheckedMap.get(Integer.valueOf(i));
        if (boolArr == null || list == null) {
            return null;
        }
        return z ? HyperMenuUtils.joinCheckedItemTitles(list, boolArr) : HyperMenuUtils.findFirstCheckedItemTitle(list, boolArr);
    }

    public final int getGroupForeignKey(miuix.appcompat.internal.view.menu.MenuItemImpl menuItemImpl) {
        Intent intent;
        if (menuItemImpl == null || (intent = menuItemImpl.getIntent()) == null) {
            return -1;
        }
        return intent.getIntExtra("miuix:hyperMenu:groupForeignKey", -1);
    }

    @Override // miuix.appcompat.view.menu.HyperBaseAdapter
    public HyperMenuContract.HyperMenuItem getHyperMenuItem(int i) {
        return (HyperMenuContract.HyperMenuItem) this.mPrimaryMenuItems.get(i);
    }

    @Override // miuix.appcompat.view.menu.HyperBaseAdapter, android.widget.Adapter
    public MenuItem getItem(int i) {
        return ((HyperMenuContract.HyperMenuItem) this.mPrimaryMenuItems.get(i)).getMenuItem();
    }

    @Override // miuix.appcompat.view.menu.HyperBaseAdapter, android.widget.Adapter
    public long getItemId(int i) {
        return ((HyperMenuContract.HyperMenuItem) this.mPrimaryMenuItems.get(i)).getItemId();
    }

    public BaseAdapter getSecondaryAdapterByItemId(long j) {
        return (BaseAdapter) this.mSecondaryMenuMap.get(Integer.valueOf((int) j));
    }

    public final void handleDefaultCheckedStatus(List list, boolean z, int i) {
        if (list == null || list.isEmpty()) {
            return;
        }
        boolean z2 = true;
        boolean z3 = (z || i == -1) ? false : true;
        Boolean[] boolArr = z3 ? (Boolean[]) this.mSecondaryItemCheckedMap.get(Integer.valueOf(i)) : null;
        if (z3 && boolArr == null) {
            boolArr = new Boolean[list.size()];
        } else {
            z2 = false;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            HyperMenuContract.HyperMenuItem hyperMenuItem = (HyperMenuContract.HyperMenuItem) list.get(i2);
            HyperMenuContract.HyperMenuTextItem hyperMenuTextItem = hyperMenuItem instanceof HyperMenuContract.HyperMenuTextItem ? (HyperMenuContract.HyperMenuTextItem) hyperMenuItem : null;
            miuix.appcompat.internal.view.menu.MenuItemImpl menuItem = hyperMenuTextItem != null ? hyperMenuTextItem.getMenuItem() : null;
            if (menuItem == null || !menuItem.isCheckable()) {
                if (z) {
                    int itemId = menuItem != null ? menuItem.getItemId() : i2;
                    if (!this.mSavePrimaryStatusByIdEnabled) {
                        itemId = i2;
                    }
                    this.mPrimaryItemCheckedMap.put(Integer.valueOf(itemId), Boolean.FALSE);
                }
            } else if (z) {
                int itemId2 = this.mSavePrimaryStatusByIdEnabled ? menuItem.getItemId() : i2;
                Boolean bool = (Boolean) this.mPrimaryItemCheckedMap.get(Integer.valueOf(itemId2));
                this.mPrimaryItemCheckedMap.put(Integer.valueOf(itemId2), Boolean.valueOf(bool != null ? bool.booleanValue() : menuItem.isChecked()));
                hyperMenuTextItem.checkStatus = Boolean.TRUE.equals(this.mPrimaryItemCheckedMap.get(Integer.valueOf(itemId2))) ? HyperMenuContract.CheckableType.CHECKED : HyperMenuContract.CheckableType.NOT_CHECKED;
                HyperMenuUtils.setMenuItemChecked(menuItem, hyperMenuTextItem.isChecked());
            } else if (z3) {
                if (z2) {
                    boolArr[i2] = Boolean.valueOf(menuItem.isChecked());
                }
                hyperMenuTextItem.checkStatus = Boolean.TRUE.equals(boolArr[i2]) ? HyperMenuContract.CheckableType.CHECKED : HyperMenuContract.CheckableType.NOT_CHECKED;
            }
        }
        if (z3) {
            this.mSecondaryItemCheckedMap.put(Integer.valueOf(i), boolArr);
        }
    }

    public boolean hasSubMenu() {
        return !this.mSecondaryMenuMap.isEmpty();
    }

    public final boolean isGroupMultiSelect(ArrayList arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            return false;
        }
        HyperMenuContract.HyperMenuItem hyperMenuItem = (HyperMenuContract.HyperMenuItem) arrayList.get(0);
        if (hyperMenuItem.getMenuItem() == null || hyperMenuItem.getMenuItem().getIntent() == null) {
            return false;
        }
        return hyperMenuItem.getMenuItem().getIntent().getBooleanExtra("miuix:hyperMenu:itemMultiSelect", false);
    }

    public final boolean isPrimaryGroup(ArrayList arrayList, int i) {
        miuix.appcompat.internal.view.menu.MenuItemImpl menuItem;
        if (arrayList == null || arrayList.isEmpty() || (menuItem = ((HyperMenuContract.HyperMenuItem) arrayList.get(0)).getMenuItem()) == null) {
            return true;
        }
        Intent intent = menuItem.getIntent();
        if (intent == null || !intent.hasExtra("miuix:hyperMenu:groupForeignKey")) {
            return menuItem.getGroupId() == 0 || menuItem.getGroupId() != i;
        }
        return false;
    }

    public void preCheckPrimaryItem(Map map) {
        if (map == null) {
            return;
        }
        this.mUserPreCheckedPrimaryMap = map;
        this.mPrimaryItemCheckedMap.clear();
        for (Integer num : map.keySet()) {
            num.intValue();
            this.mPrimaryItemCheckedMap.put(num, (Boolean) map.get(num));
        }
        this.mPrimaryCheckedMapFirstModified = true;
    }

    public void preCheckSecondaryItem(Map map) {
        if (map == null) {
            return;
        }
        this.mUserPreCheckedSecondaryMap = map;
        this.mSecondaryItemCheckedMap.clear();
        for (Integer num : map.keySet()) {
            num.intValue();
            this.mSecondaryItemCheckedMap.put(num, (Boolean[]) map.get(num));
        }
        this.mSecondaryCheckedMapFirstModified = true;
    }

    public final void propagateGroupMultiSelect(Map map) {
        for (ArrayList<HyperMenuContract.HyperMenuItem> arrayList : map.values()) {
            if (!arrayList.isEmpty()) {
                boolean booleanExtra = false;
                HyperMenuContract.HyperMenuItem hyperMenuItem = (HyperMenuContract.HyperMenuItem) arrayList.get(0);
                if (hyperMenuItem.getMenuItem() != null && hyperMenuItem.getMenuItem().getIntent() != null) {
                    booleanExtra = hyperMenuItem.getMenuItem().getIntent().getBooleanExtra("miuix:hyperMenu:itemMultiSelect", false);
                }
                if (booleanExtra) {
                    for (HyperMenuContract.HyperMenuItem hyperMenuItem2 : arrayList) {
                        if (hyperMenuItem2 instanceof HyperMenuContract.HyperMenuTextItem) {
                            ((HyperMenuContract.HyperMenuTextItem) hyperMenuItem2).isMultiSelect = true;
                        }
                    }
                }
            }
        }
    }

    public final void removeInvalidatePrimaryItem(boolean z) {
        if (this.mPrimaryMenuItems == null || this.mSecondaryMenuMap == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (HyperMenuContract.HyperMenuItem hyperMenuItem : this.mPrimaryMenuItems) {
            miuix.appcompat.internal.view.menu.MenuItemImpl menuItem = hyperMenuItem.getMenuItem();
            boolean z2 = false;
            boolean z3 = hyperMenuItem instanceof HyperMenuContract.HyperMenuTextItem ? ((HyperMenuContract.HyperMenuTextItem) hyperMenuItem).isExpandable : false;
            if (z) {
                z2 = z3;
            } else if (menuItem != null && menuItem.hasSubMenu()) {
                z2 = true;
            }
            if (menuItem != null && z2 && !this.mSecondaryMenuMap.containsKey(Integer.valueOf(menuItem.getItemId()))) {
                arrayList.add(hyperMenuItem);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            this.mPrimaryMenuItems.remove((HyperMenuContract.HyperMenuItem) it.next());
        }
        arrayList.clear();
    }

    public void resumePrimaryItemClickStatus(int i, int i2) {
        HyperMenuContract.HyperMenuItem hyperMenuItem;
        miuix.appcompat.internal.view.menu.MenuItemImpl menuItem;
        List list = this.mPrimaryMenuItems;
        if (list == null || list.size() == 0) {
            return;
        }
        boolean z = false;
        int i3 = 0;
        while (true) {
            if (i3 >= this.mPrimaryMenuItems.size()) {
                hyperMenuItem = null;
                break;
            }
            hyperMenuItem = (HyperMenuContract.HyperMenuItem) this.mPrimaryMenuItems.get(i3);
            if (hyperMenuItem.getItemId() == i) {
                break;
            } else {
                i3++;
            }
        }
        if (hyperMenuItem == null || (menuItem = hyperMenuItem.getMenuItem()) == null) {
            return;
        }
        boolean z2 = hyperMenuItem instanceof HyperMenuContract.HyperMenuTextItem;
        if (!(z2 ? ((HyperMenuContract.HyperMenuTextItem) hyperMenuItem).isMultiSelect : false)) {
            Intent intent = menuItem.getIntent();
            int intExtra = intent != null ? intent.getIntExtra("miuix:hyperMenu:groupId", menuItem.getGroupId()) : menuItem.getGroupId();
            if (this.mGroupIdOverrideMap.containsKey(Integer.valueOf(intExtra)) && this.mGroupIdOverrideMap.get(Integer.valueOf(intExtra)) != null) {
                intExtra = ((Integer) this.mGroupIdOverrideMap.get(Integer.valueOf(intExtra))).intValue();
            }
            ArrayList arrayList = (ArrayList) this.mGroupedMap.get(Integer.valueOf(intExtra));
            int groupIndexAndUpdateStatus = arrayList != null ? HyperMenuUtils.getGroupIndexAndUpdateStatus(arrayList, this.mPrimaryItemCheckedMap, i, this.mSavePrimaryStatusByIdEnabled) : -1;
            if (groupIndexAndUpdateStatus != -1 && arrayList != null && !this.mSavePrimaryStatusByIdEnabled) {
                HyperMenuUtils.updatePrimaryGroupStatusByPosition(this.mPrimaryItemCheckedMap, i2, groupIndexAndUpdateStatus, arrayList.size());
            }
            notifyDataSetChanged();
            return;
        }
        if (menuItem.isCheckable() && z2) {
            HyperMenuContract.HyperMenuTextItem hyperMenuTextItem = (HyperMenuContract.HyperMenuTextItem) hyperMenuItem;
            if (!hyperMenuTextItem.isExpandable) {
                if (!this.mSavePrimaryStatusByIdEnabled) {
                    i = i2;
                }
                Boolean bool = (Boolean) this.mPrimaryItemCheckedMap.get(Integer.valueOf(i));
                if (bool == null ? !menuItem.isChecked() : !bool.booleanValue()) {
                    z = true;
                }
                this.mPrimaryItemCheckedMap.put(Integer.valueOf(i), Boolean.valueOf(z));
                hyperMenuTextItem.checkStatus = z ? HyperMenuContract.CheckableType.CHECKED : HyperMenuContract.CheckableType.NOT_CHECKED;
                HyperMenuUtils.setMenuItemChecked(menuItem, z);
            }
        }
        notifyDataSetChanged();
    }

    public final void selectPrimaryMenu(Map map) {
        for (Integer num : map.keySet()) {
            ArrayList arrayList = (ArrayList) map.get(num);
            boolean zIsPrimaryGroup = isPrimaryGroup(arrayList, num.intValue());
            List listFilterVisibleItems = filterVisibleItems(arrayList);
            if (zIsPrimaryGroup && !listFilterVisibleItems.isEmpty()) {
                List list = this.mPrimaryMenuItems;
                list.addAll(list.size(), listFilterVisibleItems);
                this.mPrimaryMenuItems.add(new HyperMenuContract.HyperMenuDivider());
            }
        }
        if (!this.mPrimaryMenuItems.isEmpty()) {
            List list2 = this.mPrimaryMenuItems;
            list2.remove(list2.size() - 1);
        }
        handleDefaultCheckedStatus(this.mPrimaryMenuItems, true, -1);
    }

    public void setSavePrimaryStatusByIdEnabled(boolean z) {
        this.mSavePrimaryStatusByIdEnabled = z;
    }

    public final void syncPrimarySubtitle(HyperMenuContract.HyperMenuItem hyperMenuItem, List list, boolean z) {
        CharSequence checkedItemTitle;
        if ((hyperMenuItem instanceof HyperMenuContract.HyperMenuTextItem ? ((HyperMenuContract.HyperMenuTextItem) hyperMenuItem).showSubtitle : true) && (checkedItemTitle = getCheckedItemTitle(list, hyperMenuItem.getMenuItem().getItemId(), z)) != null && (hyperMenuItem instanceof HyperMenuContract.HyperMenuTextItem)) {
            ((HyperMenuContract.HyperMenuTextItem) hyperMenuItem).setSubtitle(checkedItemTitle);
            hyperMenuItem.getMenuItem().setSubtitle(checkedItemTitle);
        }
    }

    public void update(miuix.appcompat.internal.view.menu.MenuBuilder menuBuilder) {
        update(menuBuilder, false);
    }

    public void update(miuix.appcompat.internal.view.menu.MenuBuilder menuBuilder, boolean z) {
        Map map = this.mUserPreCheckedPrimaryMap;
        if (map != null && this.mPrimaryItemCheckedMap != null && !this.mPrimaryCheckedMapFirstModified) {
            preCheckPrimaryItem(map);
        }
        Map map2 = this.mUserPreCheckedSecondaryMap;
        if (map2 != null && this.mSecondaryItemCheckedMap != null && !this.mSecondaryCheckedMapFirstModified) {
            preCheckSecondaryItem(map2);
        }
        if (z) {
            buildGroupedMenuItems(menuBuilder);
        } else {
            buildMenuItems(menuBuilder);
        }
        this.mPrimaryCheckedMapFirstModified = false;
        this.mSecondaryCheckedMapFirstModified = false;
        notifyDataSetChanged();
    }
}
