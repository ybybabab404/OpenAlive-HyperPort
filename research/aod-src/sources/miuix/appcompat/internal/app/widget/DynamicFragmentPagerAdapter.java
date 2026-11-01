package miuix.appcompat.internal.app.widget;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;

/* JADX INFO: compiled from: ActionBarViewPagerController.java */
/* JADX INFO: loaded from: classes2.dex */
class DynamicFragmentPagerAdapter extends PagerAdapter {
    public Context mContext;
    public FragmentTransaction mCurTransaction;
    public Fragment mCurrentPrimaryItem;
    public ArrayList mFragmentInfos;
    public FragmentManager mFragmentManager;

    /* JADX INFO: compiled from: ActionBarViewPagerController.java */
    public class FragmentInfo {
        public Bundle args;
        public Class clazz;
        public Fragment fragment;
        public boolean hasActionMenu;
        public ActionBar.Tab tab;
        public String tag;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        this.mCurTransaction.detach((Fragment) obj);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void finishUpdate(ViewGroup viewGroup) {
        FragmentTransaction fragmentTransaction = this.mCurTransaction;
        if (fragmentTransaction != null) {
            fragmentTransaction.commitAllowingStateLoss();
            this.mCurTransaction = null;
            this.mFragmentManager.executePendingTransactions();
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.mFragmentInfos.size();
    }

    public Fragment getFragment(int i, boolean z) {
        return getFragment(i, z, true);
    }

    public Fragment getFragment(int i, boolean z, boolean z2) {
        Class cls;
        if (this.mFragmentInfos.isEmpty() || i < 0 || i > this.mFragmentInfos.size() - 1) {
            return null;
        }
        ArrayList arrayList = this.mFragmentInfos;
        if (z2) {
            i = toIndexForRTL(i);
        }
        FragmentInfo fragmentInfo = (FragmentInfo) arrayList.get(i);
        if (fragmentInfo.fragment == null) {
            Fragment fragmentFindFragmentByTag = this.mFragmentManager.findFragmentByTag(fragmentInfo.tag);
            fragmentInfo.fragment = fragmentFindFragmentByTag;
            if (fragmentFindFragmentByTag == null && z && (cls = fragmentInfo.clazz) != null) {
                fragmentInfo.fragment = Fragment.instantiate(this.mContext, cls.getName(), fragmentInfo.args);
                fragmentInfo.clazz = null;
                fragmentInfo.args = null;
            }
        }
        return fragmentInfo.fragment;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(Object obj) {
        int size = this.mFragmentInfos.size();
        for (int i = 0; i < size; i++) {
            if (obj == ((FragmentInfo) this.mFragmentInfos.get(i)).fragment) {
                return i;
            }
        }
        return -2;
    }

    public ActionBar.Tab getTabAt(int i) {
        return ((FragmentInfo) this.mFragmentInfos.get(i)).tab;
    }

    public boolean hasActionMenu(int i) {
        if (i < 0 || i >= this.mFragmentInfos.size()) {
            return false;
        }
        return ((FragmentInfo) this.mFragmentInfos.get(i)).hasActionMenu;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        Fragment fragment = getFragment(i, true, false);
        if (fragment.getFragmentManager() != null) {
            this.mCurTransaction.attach(fragment);
        } else {
            this.mCurTransaction.add(viewGroup.getId(), fragment, ((FragmentInfo) this.mFragmentInfos.get(i)).tag);
        }
        if (fragment != this.mCurrentPrimaryItem) {
            fragment.setMenuVisibility(false);
            fragment.setUserVisibleHint(false);
        }
        return fragment;
    }

    public boolean isRTL() {
        return this.mContext.getResources().getConfiguration().getLayoutDirection() == 1;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return ((Fragment) obj).getView() == view;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
        Fragment fragment = (Fragment) obj;
        Fragment fragment2 = this.mCurrentPrimaryItem;
        if (fragment != fragment2) {
            if (fragment2 != null) {
                fragment2.setMenuVisibility(false);
                this.mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment != null) {
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            this.mCurrentPrimaryItem = fragment;
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void startUpdate(ViewGroup viewGroup) {
    }

    public int toIndexForRTL(int i) {
        if (!isRTL()) {
            return i;
        }
        int size = this.mFragmentInfos.size() - 1;
        if (size > i) {
            return size - i;
        }
        return 0;
    }
}
