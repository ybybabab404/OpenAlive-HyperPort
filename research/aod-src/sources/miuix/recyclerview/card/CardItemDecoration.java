package miuix.recyclerview.card;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import java.util.ArrayList;
import miuix.recyclerview.card.base.BaseDecoration;

/* JADX INFO: loaded from: classes2.dex */
public class CardItemDecoration extends BaseDecoration {
    public int firstVisiblePosition;
    public int lastVisiblePosition;
    public final ArrayList mCardAreas;
    public int mCardMarginBottom;
    public int mCardMarginTop;
    public int mCardPaddingBottom;
    public int mCardPaddingEnd;
    public int mCardPaddingStart;
    public int mCardPaddingTop;
    public CardTouchHelperCallback mDragCallback;
    public Drawable mGroupDrawable;
    public int mNoneCardMarginBottom;
    public int mNoneCardMarginTop;

    public static class CardArea {
        public boolean drawBottomRoundCorner;
        public boolean drawTopRoundCorner;
        public RectF rect;

        public CardArea() {
            this.rect = new RectF();
            this.drawTopRoundCorner = true;
            this.drawBottomRoundCorner = true;
        }
    }

    public final void calculateDeltaY(CardArea cardArea, RecyclerView recyclerView, int i, int i2, boolean z, CardGroupAdapter cardGroupAdapter) {
        int i3 = 0;
        boolean z2 = cardGroupAdapter.getItemViewGroup(i) == cardGroupAdapter.getRemovedGroupId();
        CardTouchHelperCallback cardTouchHelperCallback = this.mDragCallback;
        if ((cardTouchHelperCallback == null || !cardTouchHelperCallback.isInDragState()) && z2) {
            float fFindNearViewY = findNearViewY(recyclerView, i, i2, z);
            if (fFindNearViewY != -1.0f) {
                if (z) {
                    int i4 = i + 1;
                    if (i4 < i2) {
                        i3 = marginRect(cardGroupAdapter.getItemViewGroupType(i)).bottom + offsetsRect(cardGroupAdapter, i4).top;
                    }
                    cardArea.rect.bottom = fFindNearViewY - i3;
                    return;
                }
                int i5 = i - 1;
                if (i5 >= 0) {
                    i3 = marginRect(cardGroupAdapter.getItemViewGroupType(i)).top + offsetsRect(cardGroupAdapter, i5).bottom;
                }
                cardArea.rect.top = fFindNearViewY + i3;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0036  */
    @Override // miuix.recyclerview.card.base.BaseDecoration
    public void calculateGroupRectAndDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state, RecyclerView.Adapter adapter) {
        Drawable drawable;
        int i;
        int width;
        int i2;
        CardItemDecoration cardItemDecoration = this;
        RecyclerView recyclerView2 = recyclerView;
        if (adapter instanceof CardGroupAdapter) {
            cardItemDecoration.mCardAreas.clear();
            CardGroupAdapter cardGroupAdapter = (CardGroupAdapter) adapter;
            RecyclerView.LayoutManager layoutManager = recyclerView2.getLayoutManager();
            if (layoutManager == null || !cardItemDecoration.isSupportLayoutManager(layoutManager)) {
                return;
            }
            cardItemDecoration.calculateVisiblePositionLimit(layoutManager);
            AnonymousClass1 anonymousClass1 = null;
            int i3 = cardItemDecoration.firstVisiblePosition;
            CardArea cardArea = null;
            int i4 = 0;
            while (i3 <= cardItemDecoration.lastVisiblePosition) {
                RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = recyclerView2.findViewHolderForAdapterPosition(i3);
                if (viewHolderFindViewHolderForAdapterPosition != null) {
                    View view = viewHolderFindViewHolderForAdapterPosition.itemView;
                    int itemViewGroupType = cardGroupAdapter.getItemViewGroupType(recyclerView2.getChildAdapterPosition(view));
                    if (itemViewGroupType != 0) {
                        CardTouchHelperCallback cardTouchHelperCallback = cardItemDecoration.mDragCallback;
                        float y = (cardTouchHelperCallback == null || !cardTouchHelperCallback.isInDragState()) ? view.getY() : view.getTop();
                        if (cardArea == null) {
                            if (i4 < cardItemDecoration.mCardAreas.size()) {
                                cardArea = (CardArea) cardItemDecoration.mCardAreas.get(i4);
                            } else {
                                cardArea = new CardArea();
                                cardItemDecoration.mCardAreas.add(cardArea);
                            }
                            RectF rectF = cardArea.rect;
                            rectF.top = y - cardItemDecoration.mCardPaddingTop;
                            rectF.bottom = y + view.getHeight() + cardItemDecoration.mCardPaddingBottom;
                            cardArea.rect.left = cardItemDecoration.isLayoutRtl(recyclerView2) ? cardItemDecoration.mCardMarginEnd : cardItemDecoration.mCardMarginStart;
                            RectF rectF2 = cardArea.rect;
                            if (cardItemDecoration.isLayoutRtl(recyclerView2)) {
                                width = recyclerView2.getWidth();
                                i2 = cardItemDecoration.mCardMarginStart;
                            } else {
                                width = recyclerView2.getWidth();
                                i2 = cardItemDecoration.mCardMarginEnd;
                            }
                            rectF2.right = width - i2;
                            cardArea.drawTopRoundCorner = itemViewGroupType == 2;
                            cardArea.drawBottomRoundCorner = itemViewGroupType == 4;
                            if (itemViewGroupType == 2 || itemViewGroupType == 1) {
                                i = itemViewGroupType;
                                cardItemDecoration.calculateDeltaY(cardArea, recyclerView2, i3, 0, false, cardGroupAdapter);
                            } else {
                                i = itemViewGroupType;
                            }
                        } else {
                            i = itemViewGroupType;
                            cardArea.rect.bottom = y + view.getHeight() + cardItemDecoration.mCardPaddingBottom;
                            cardArea.drawBottomRoundCorner = i == 4;
                        }
                        if (i == 1) {
                            cardArea.drawTopRoundCorner = true;
                            cardArea.drawBottomRoundCorner = true;
                            cardArea.rect.bottom = y + view.getHeight() + cardItemDecoration.mCardPaddingBottom;
                            cardItemDecoration.calculateDeltaY(cardArea, recyclerView, i3, layoutManager.getItemCount(), true, cardGroupAdapter);
                            i4++;
                            cardArea = null;
                        }
                        if (i == 4) {
                            cardItemDecoration = this;
                            cardItemDecoration.calculateDeltaY(cardArea, recyclerView, i3, layoutManager.getItemCount(), true, cardGroupAdapter);
                            i4++;
                            cardArea = null;
                        } else {
                            cardItemDecoration = this;
                        }
                    }
                }
                i3++;
                recyclerView2 = recyclerView;
                anonymousClass1 = null;
            }
            for (int i5 = 0; i5 < cardItemDecoration.mCardAreas.size(); i5++) {
                CardArea cardArea2 = (CardArea) cardItemDecoration.mCardAreas.get(i5);
                RectF rectF3 = cardArea2.rect;
                if (rectF3.bottom - rectF3.top >= 0.0f && (drawable = cardItemDecoration.mGroupDrawable) != null) {
                    float f = cardArea2.drawTopRoundCorner ? cardItemDecoration.mCardRadius : 0.0f;
                    float f2 = cardArea2.drawBottomRoundCorner ? cardItemDecoration.mCardRadius : 0.0f;
                    float[] fArr = {f, f, f, f, f2, f2, f2, f2};
                    if (drawable instanceof ColorDrawable) {
                        cardItemDecoration.mPaint.setColor(((ColorDrawable) drawable).getColor());
                        cardItemDecoration.drawCardRect(canvas, cardArea2.rect, fArr, Path.Direction.CW);
                    } else {
                        cardItemDecoration.mCardPath.reset();
                        cardItemDecoration.mCardPath.addRoundRect(cardArea2.rect, fArr, Path.Direction.CW);
                        cardItemDecoration.clipDrawableRoundRect(canvas, cardArea2.rect, cardItemDecoration.mCardPath, cardItemDecoration.mGroupDrawable);
                    }
                }
            }
        }
    }

    public final void calculateVisiblePositionLimit(int i, int i2) {
        this.firstVisiblePosition = Math.max(i - 2, 0);
        this.lastVisiblePosition = i2 + 2;
    }

    public void calculateVisiblePositionLimit(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            if (gridLayoutManager.getSpanCount() == 1) {
                calculateVisiblePositionLimit(gridLayoutManager.findFirstVisibleItemPosition(), gridLayoutManager.findLastVisibleItemPosition());
                return;
            }
            return;
        }
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            calculateVisiblePositionLimit(linearLayoutManager.findFirstVisibleItemPosition(), linearLayoutManager.findLastVisibleItemPosition());
            return;
        }
        if (!(layoutManager instanceof StaggeredGridLayoutManager)) {
            if (layoutManager == null || !isLineLayout()) {
                return;
            }
            calculateVisiblePositionLimit(findFirstVisibleItemPosition(), findLastVisibleItemPosition());
            return;
        }
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        if (staggeredGridLayoutManager.getSpanCount() == 1) {
            int[] iArrFindFirstVisibleItemPositions = staggeredGridLayoutManager.findFirstVisibleItemPositions(null);
            int[] iArrFindLastVisibleItemPositions = staggeredGridLayoutManager.findLastVisibleItemPositions(null);
            if (iArrFindFirstVisibleItemPositions.length <= 0 || iArrFindLastVisibleItemPositions.length <= 0) {
                return;
            }
            calculateVisiblePositionLimit(iArrFindFirstVisibleItemPositions[0], iArrFindLastVisibleItemPositions[0]);
        }
    }

    public int findFirstVisibleItemPosition() {
        return 0;
    }

    public int findLastVisibleItemPosition() {
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        if (isSupportLayoutManager(recyclerView.getLayoutManager())) {
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter instanceof CardGroupAdapter) {
                int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
                Rect rectOffsetsRect = offsetsRect((CardGroupAdapter) adapter, childAdapterPosition);
                if (childAdapterPosition == 0) {
                    rectOffsetsRect.top = 0;
                    rectOffsetsRect.bottom = 0;
                }
                if (isLayoutRtl(recyclerView)) {
                    rect.left = this.mCardMarginStart + this.mCardPaddingStart;
                    rect.right = this.mCardMarginEnd + this.mCardPaddingEnd;
                } else {
                    rect.right = this.mCardMarginStart + this.mCardPaddingStart;
                    rect.left = this.mCardMarginEnd + this.mCardPaddingEnd;
                }
                rect.top = rectOffsetsRect.top;
                rect.bottom = rectOffsetsRect.bottom;
            }
        }
    }

    public boolean isLineLayout() {
        return false;
    }

    public boolean isSupportLayoutManager(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) layoutManager).getSpanCount() == 1;
        }
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getSpanCount() == 1;
        }
        return (layoutManager != null && isLineLayout()) || (layoutManager instanceof LinearLayoutManager);
    }

    public final Rect marginRect(int i) {
        Rect rect = new Rect();
        if (i == 2) {
            rect.top = this.mCardMarginTop;
            return rect;
        }
        if (i == 4) {
            rect.bottom = this.mCardMarginBottom;
            return rect;
        }
        if (i == 1) {
            rect.top = this.mCardMarginTop;
            rect.bottom = this.mCardMarginBottom;
            return rect;
        }
        if (i == 0) {
            rect.top = this.mNoneCardMarginTop;
            rect.bottom = this.mNoneCardMarginBottom;
        }
        return rect;
    }

    public Rect offsetsRect(CardGroupAdapter cardGroupAdapter, int i) {
        Rect rect = new Rect();
        if (i >= 0) {
            int itemViewGroupType = cardGroupAdapter.getItemViewGroupType(i);
            if (itemViewGroupType == 2) {
                rect.top = this.mCardMarginTop + this.mCardPaddingTop;
                return rect;
            }
            if (itemViewGroupType == 4) {
                rect.bottom = this.mCardMarginBottom + this.mCardPaddingBottom;
                return rect;
            }
            if (itemViewGroupType == 1) {
                rect.top = this.mCardMarginTop + this.mCardPaddingTop;
                rect.bottom = this.mCardMarginBottom + this.mCardPaddingBottom;
                return rect;
            }
            if (itemViewGroupType == 0) {
                rect.top = this.mNoneCardMarginTop;
                rect.bottom = this.mNoneCardMarginBottom;
            }
        }
        return rect;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        super.onDrawOver(canvas, recyclerView, state);
    }
}
