package miuix.appcompat.internal.app.widget;

import android.view.ViewGroup;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.OriginalViewPager;
import java.util.ArrayList;
import java.util.Iterator;
import miuix.viewpager.widget.ViewPager;

/* JADX INFO: loaded from: classes2.dex */
public class ActionBarViewPagerController {
    public ActionBarImpl mActionBar;
    public ArrayList mListeners;
    public DynamicFragmentPagerAdapter mPagerAdapter;
    public ViewPager mViewPager;

    /* JADX INFO: renamed from: miuix.appcompat.internal.app.widget.ActionBarViewPagerController$1, reason: invalid class name */
    public class AnonymousClass1 implements ActionBar.TabListener {
        public final /* synthetic */ ActionBarViewPagerController this$0;

        @Override // androidx.appcompat.app.ActionBar.TabListener
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        }

        @Override // androidx.appcompat.app.ActionBar.TabListener
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            int count = this.this$0.mPagerAdapter.getCount();
            for (int i = 0; i < count; i++) {
                if (this.this$0.mPagerAdapter.getTabAt(i) == tab) {
                    this.this$0.mViewPager.setCurrentItem(i, tab instanceof ActionBarImpl.TabImpl ? ((ActionBarImpl.TabImpl) tab).mWithAnim : true);
                    return;
                }
            }
        }

        @Override // androidx.appcompat.app.ActionBar.TabListener
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        }
    }

    /* JADX INFO: renamed from: miuix.appcompat.internal.app.widget.ActionBarViewPagerController$2, reason: invalid class name */
    class AnonymousClass2 implements OriginalViewPager.OnPageChangeListener {
        public ScrollStatus mStatus;
        public final /* synthetic */ ActionBarViewPagerController this$0;

        @Override // androidx.viewpager.widget.OriginalViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
            if (this.this$0.mListeners != null) {
                Iterator it = this.this$0.mListeners.iterator();
                while (it.hasNext()) {
                    ((miuix.appcompat.app.ActionBar.FragmentViewPagerChangeListener) it.next()).onPageScrollStateChanged(i);
                }
            }
        }

        @Override // androidx.viewpager.widget.OriginalViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
            this.mStatus.update(i, f);
            if (this.mStatus.mScrollBegin || this.this$0.mListeners == null) {
                return;
            }
            boolean zHasActionMenu = this.this$0.mPagerAdapter.hasActionMenu(this.mStatus.mFromPos);
            boolean zHasActionMenu2 = this.this$0.mPagerAdapter.hasActionMenu(this.mStatus.mToPos);
            if (this.this$0.mPagerAdapter.isRTL()) {
                i = this.this$0.mPagerAdapter.toIndexForRTL(i);
                if (!this.mStatus.mScrollEnd) {
                    i--;
                    f = 1.0f - f;
                }
            }
            Iterator it = this.this$0.mListeners.iterator();
            while (it.hasNext()) {
                ((miuix.appcompat.app.ActionBar.FragmentViewPagerChangeListener) it.next()).onPageScrolled(i, f, zHasActionMenu, zHasActionMenu2);
            }
        }

        @Override // androidx.viewpager.widget.OriginalViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            int indexForRTL = this.this$0.mPagerAdapter.toIndexForRTL(i);
            this.this$0.mActionBar.setSelectedNavigationItem(indexForRTL);
            this.this$0.mPagerAdapter.setPrimaryItem((ViewGroup) this.this$0.mViewPager, i, (Object) this.this$0.mPagerAdapter.getFragment(i, false, false));
            if (this.this$0.mListeners != null) {
                Iterator it = this.this$0.mListeners.iterator();
                while (it.hasNext()) {
                    ((miuix.appcompat.app.ActionBar.FragmentViewPagerChangeListener) it.next()).onPageSelected(indexForRTL);
                }
            }
        }
    }

    public class ActionMenuChangeAnimatorObject {
    }

    public static class ScrollStatus {
        public int mFromPos;
        public float mOffsetAtScroll;
        public int mPosAtScroll;
        public boolean mScrollBegin;
        public boolean mScrollEnd;
        public int mToPos;

        public final void onScrollBegin(int i, float f) {
            this.mScrollBegin = false;
            boolean z = f > this.mOffsetAtScroll;
            this.mFromPos = z ? i : i + 1;
            if (z) {
                i++;
            }
            this.mToPos = i;
        }

        public final void onScrollEnd() {
            this.mFromPos = this.mToPos;
            this.mPosAtScroll = -1;
            this.mOffsetAtScroll = 0.0f;
            this.mScrollEnd = true;
        }

        public final void onScrollPositionChange(int i, float f) {
            this.mPosAtScroll = i;
            this.mOffsetAtScroll = f;
            this.mScrollBegin = true;
            this.mScrollEnd = false;
        }

        public void update(int i, float f) {
            if (f < 1.0E-4f) {
                onScrollEnd();
            } else if (this.mPosAtScroll != i) {
                onScrollPositionChange(i, f);
            } else if (this.mScrollBegin) {
                onScrollBegin(i, f);
            }
        }
    }
}
