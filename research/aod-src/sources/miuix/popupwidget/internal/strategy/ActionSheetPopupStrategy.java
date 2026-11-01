package miuix.popupwidget.internal.strategy;

import android.graphics.Rect;

/* JADX INFO: loaded from: classes2.dex */
public class ActionSheetPopupStrategy implements IPopupWindowStrategy {
    public int mPopupDirection;

    public final int calcXAlignLeft(PopupWindowSpec popupWindowSpec) {
        Rect rect = popupWindowSpec.mAnchorViewBounds;
        Rect rect2 = popupWindowSpec.mDecorViewBounds;
        Rect rect3 = popupWindowSpec.mSafeInsets;
        int i = rect3.left;
        int i2 = rect3.right;
        int i3 = popupWindowSpec.mFinalPopupWidth;
        int i4 = rect.left;
        int i5 = rect2.left;
        if (i4 < i5 + i) {
            i4 = i5 + i;
        }
        int i6 = i4 + i3;
        int i7 = rect2.right;
        if (i6 <= i7 - i2 || (i4 = (i7 - i2) - i3) >= i5 + i) {
            return i4;
        }
        int i8 = i5 + i;
        popupWindowSpec.mFinalPopupWidth = (i7 - i2) - i8;
        return i8;
    }

    public final int calcXAlignRight(PopupWindowSpec popupWindowSpec) {
        Rect rect = popupWindowSpec.mAnchorViewBounds;
        Rect rect2 = popupWindowSpec.mDecorViewBounds;
        Rect rect3 = popupWindowSpec.mSafeInsets;
        int i = rect3.left;
        int i2 = rect3.right;
        int i3 = popupWindowSpec.mFinalPopupWidth;
        int i4 = rect.right;
        int i5 = rect2.right;
        if (i4 > i5 - i2) {
            i4 = i5 - i2;
        }
        int i6 = i4 - i3;
        int i7 = rect2.left;
        if (i6 >= i7 + i) {
            return i6;
        }
        int i8 = i7 + i;
        if (i3 + i8 > i5 - i2) {
            popupWindowSpec.mFinalPopupWidth = (i5 - i2) - i8;
        }
        return i8;
    }

    public final int calcXCenter(PopupWindowSpec popupWindowSpec) {
        Rect rect = popupWindowSpec.mAnchorViewBounds;
        Rect rect2 = popupWindowSpec.mDecorViewBounds;
        Rect rect3 = popupWindowSpec.mSafeInsets;
        int i = rect3.left;
        int i2 = rect3.right;
        int i3 = popupWindowSpec.mFinalPopupWidth;
        int iCenterX = rect.centerX() - (i3 / 2);
        int i4 = rect2.left;
        if (iCenterX < i4 + i) {
            iCenterX = i4 + i;
        }
        int i5 = iCenterX + i3;
        int i6 = rect2.right;
        if (i5 <= i6 - i2 || (iCenterX = (i6 - i2) - i3) >= i4 + i) {
            return iCenterX;
        }
        int i7 = i4 + i;
        popupWindowSpec.mFinalPopupWidth = (i6 - i2) - i7;
        return i7;
    }

    public final int calcYAbove(PopupWindowSpec popupWindowSpec) {
        Rect rect = popupWindowSpec.mAnchorViewBounds;
        Rect rect2 = popupWindowSpec.mDecorViewBounds;
        Rect rect3 = popupWindowSpec.mSafeInsets;
        int i = rect3.top;
        int i2 = rect3.bottom;
        int i3 = popupWindowSpec.mFinalPopupHeight;
        int i4 = rect.top;
        int i5 = rect2.top;
        if (i4 < i5 + i) {
            i4 = i5 + i;
        }
        int i6 = i3 + i4;
        int i7 = rect2.bottom;
        if (i6 <= i7 - i2) {
            return i4;
        }
        int i8 = (i7 - i2) - i4;
        if (i8 > 0) {
            popupWindowSpec.mFinalPopupHeight = i8;
            return i4;
        }
        int i9 = i5 + i;
        popupWindowSpec.mFinalPopupHeight = (i7 - i2) - i9;
        return i9;
    }

    public final int calcYBelow(PopupWindowSpec popupWindowSpec) {
        Rect rect = popupWindowSpec.mAnchorViewBounds;
        Rect rect2 = popupWindowSpec.mDecorViewBounds;
        Rect rect3 = popupWindowSpec.mSafeInsets;
        int i = rect3.top;
        int i2 = rect3.bottom;
        int i3 = popupWindowSpec.mFinalPopupHeight;
        int i4 = rect.bottom;
        int i5 = rect2.bottom;
        if (i4 > i5 - i2) {
            i4 = i5 - i2;
        }
        int i6 = i4 - i3;
        int i7 = rect2.top;
        if (i6 >= i7 + i) {
            return i6;
        }
        int i8 = i7 + i;
        int i9 = i4 - i8;
        if (i9 > 0) {
            popupWindowSpec.mFinalPopupHeight = i9;
        }
        return i8;
    }

    public final int calcYCenter(PopupWindowSpec popupWindowSpec) {
        Rect rect = popupWindowSpec.mAnchorViewBounds;
        Rect rect2 = popupWindowSpec.mDecorViewBounds;
        Rect rect3 = popupWindowSpec.mSafeInsets;
        int i = rect3.top;
        int i2 = rect3.bottom;
        int i3 = popupWindowSpec.mFinalPopupHeight;
        int iCenterY = rect.centerY() - (i3 / 2);
        int i4 = rect2.top;
        if (iCenterY < i4 + i) {
            iCenterY = i4 + i;
        }
        int i5 = iCenterY + i3;
        int i6 = rect2.bottom;
        if (i5 <= i6 - i2 || (iCenterY = (i6 - i2) - i3) >= i4 + i) {
            return iCenterY;
        }
        int i7 = i4 + i;
        popupWindowSpec.mFinalPopupHeight = (i6 - i2) - i7;
        return i7;
    }

    public final void determineDirection(PopupWindowSpec popupWindowSpec) {
        Rect rect = popupWindowSpec.mAnchorViewBounds;
        Rect rect2 = popupWindowSpec.mDecorViewBounds;
        Rect rect3 = popupWindowSpec.mSafeInsets;
        int i = rect3.top;
        int i2 = rect3.bottom;
        int i3 = rect3.left;
        int i4 = rect3.right;
        int iWidth = (rect2.width() - i3) - i4;
        int iHeight = (rect2.height() - i) - i2;
        float fCenterX = ((rect.centerX() - rect2.left) - i3) / Math.max(iWidth, 1);
        float fCenterY = ((rect.centerY() - rect2.top) - i4) / Math.max(iHeight, 1);
        setDirection(fCenterY < 0.4f, fCenterY > 0.6f, fCenterX < 0.35f, fCenterX > 0.65f, popupWindowSpec.mFinalPopupHeight, (rect.top - rect2.top) - i, (rect2.bottom - i2) - rect.bottom);
    }

    @Override // miuix.popupwidget.internal.strategy.IPopupWindowStrategy
    public int getXInWindow(PopupWindowSpec popupWindowSpec) {
        determineDirection(popupWindowSpec);
        int i = this.mPopupDirection;
        if (i != 1) {
            if (i != 2) {
                if (i != 4) {
                    if (i != 5 && i != 7) {
                        return calcXAlignLeft(popupWindowSpec);
                    }
                }
            }
            return calcXAlignRight(popupWindowSpec);
        }
        return calcXCenter(popupWindowSpec);
    }

    @Override // miuix.popupwidget.internal.strategy.IPopupWindowStrategy
    public int getYInWindow(PopupWindowSpec popupWindowSpec) {
        int i = this.mPopupDirection;
        if (i == 3 || i == 4 || i == 5) {
            return calcYBelow(popupWindowSpec);
        }
        return (i == 6 || i == 7) ? calcYCenter(popupWindowSpec) : calcYAbove(popupWindowSpec);
    }

    @Override // miuix.popupwidget.internal.strategy.IPopupWindowStrategy
    public boolean isNeedScroll(int i, PopupWindowSpec popupWindowSpec) {
        int i2 = popupWindowSpec.mContentHeight;
        return i2 > i || i2 > popupWindowSpec.mMaxHeight;
    }

    @Override // miuix.popupwidget.internal.strategy.IPopupWindowStrategy
    public void measureContentSize(PopupWindowSpec popupWindowSpec) {
        int[][] iArr = popupWindowSpec.mItemViewBounds;
        if (iArr == null) {
            Rect rect = popupWindowSpec.mContentViewBounds;
            popupWindowSpec.mContentHeight = rect.height();
            popupWindowSpec.mFinalPopupWidth = rect.width();
            popupWindowSpec.mFinalPopupHeight = rect.height();
            return;
        }
        int i = popupWindowSpec.mMaxWidth;
        int i2 = popupWindowSpec.mMaxHeight;
        int i3 = 0;
        int iMax = 0;
        for (int[] iArr2 : iArr) {
            int i4 = iArr2[0];
            int i5 = iArr2[1];
            if (i4 > i) {
                i4 = i;
            }
            iMax = Math.max(i4, iMax);
            i3 += i5;
        }
        popupWindowSpec.mContentHeight = i3;
        if (i3 <= i2) {
            i2 = i3;
        }
        popupWindowSpec.mFinalPopupHeight = i2 + popupWindowSpec.mPaddingTop + popupWindowSpec.mPaddingBottom;
        int iMax2 = Math.max(Math.min(i, iMax + popupWindowSpec.mPaddingLeft + popupWindowSpec.mPaddingRight), popupWindowSpec.mMinWidth);
        popupWindowSpec.mContentWidth = iMax2;
        popupWindowSpec.mFinalPopupWidth = iMax2;
    }

    public final void setBottomDirection(boolean z, boolean z2) {
        if (z) {
            this.mPopupDirection = 3;
        } else if (z2) {
            this.mPopupDirection = 5;
        } else {
            this.mPopupDirection = 4;
        }
    }

    public final void setCenterDirection(int i, int i2, int i3) {
        if (i >= i2) {
            this.mPopupDirection = 4;
            return;
        }
        if (i3 >= i2) {
            this.mPopupDirection = 1;
        } else if (i >= i3) {
            this.mPopupDirection = 4;
        } else {
            this.mPopupDirection = 1;
        }
    }

    public final void setCenterLeftDirection(int i, int i2, int i3) {
        if (i >= i2) {
            this.mPopupDirection = 3;
        } else if (i3 >= i2) {
            this.mPopupDirection = 0;
        } else {
            this.mPopupDirection = 6;
        }
    }

    public final void setCenterRightDirection(int i, int i2, int i3) {
        if (i >= i2) {
            this.mPopupDirection = 5;
        } else if (i3 >= i2) {
            this.mPopupDirection = 2;
        } else {
            this.mPopupDirection = 7;
        }
    }

    public final void setDirection(boolean z, boolean z2, boolean z3, boolean z4, int i, int i2, int i3) {
        if (z) {
            setTopDirection(z3, z4);
            return;
        }
        if (z2) {
            setBottomDirection(z3, z4);
            return;
        }
        if (z3) {
            setCenterLeftDirection(i2, i, i3);
        } else if (z4) {
            setCenterRightDirection(i2, i, i3);
        } else {
            setCenterDirection(i2, i, i3);
        }
    }

    public final void setTopDirection(boolean z, boolean z2) {
        if (z) {
            this.mPopupDirection = 0;
        } else if (z2) {
            this.mPopupDirection = 2;
        } else {
            this.mPopupDirection = 1;
        }
    }
}
