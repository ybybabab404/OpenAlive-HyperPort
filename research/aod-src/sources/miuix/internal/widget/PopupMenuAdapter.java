package miuix.internal.widget;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import miuix.appcompat.R;
import miuix.internal.util.AnimHelper;
import miuix.internal.util.TaggingDrawableUtil;

/* JADX INFO: loaded from: classes2.dex */
public class PopupMenuAdapter extends BaseAdapter {
    public ArrayList mAvailableItems;
    public LayoutInflater mInflater;

    public static class ViewHolder {
        public ImageView icon;
        public TextView title;

        public ViewHolder() {
        }
    }

    public final void buildMenuItems(Menu menu, ArrayList arrayList) {
        arrayList.clear();
        if (menu != null) {
            int size = menu.size();
            for (int i = 0; i < size; i++) {
                MenuItem item = menu.getItem(i);
                if (checkMenuItem(item)) {
                    arrayList.add(item);
                }
            }
        }
    }

    public boolean checkMenuItem(MenuItem menuItem) {
        return menuItem.isVisible();
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mAvailableItems.size();
    }

    @Override // android.widget.Adapter
    public MenuItem getItem(int i) {
        return (MenuItem) this.mAvailableItems.get(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.mInflater.inflate(R.layout.miuix_appcompat_popup_menu_item, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView) view.findViewById(android.R.id.icon);
            viewHolder.title = (TextView) view.findViewById(android.R.id.text1);
            view.setTag(R.id.tag_popup_menu_item, viewHolder);
            AnimHelper.addItemPressEffect(view, true);
        }
        TaggingDrawableUtil.updateItemBackground(view, i, getCount());
        Object tag = view.getTag(R.id.tag_popup_menu_item);
        if (tag != null) {
            ViewHolder viewHolder2 = (ViewHolder) tag;
            MenuItem item = getItem(i);
            boolean zIsEnabled = item.isEnabled();
            if (item.getIcon() != null) {
                viewHolder2.icon.setImageDrawable(item.getIcon());
                viewHolder2.icon.setVisibility(0);
                viewHolder2.icon.setEnabled(zIsEnabled);
            } else {
                viewHolder2.icon.setVisibility(8);
            }
            viewHolder2.title.setText(item.getTitle());
        }
        return view;
    }

    public void update(Menu menu) {
        buildMenuItems(menu, this.mAvailableItems);
        notifyDataSetChanged();
    }
}
