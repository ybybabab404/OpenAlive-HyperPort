package miuix.internal.widget;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.ListAdapter;
import android.widget.ListView;
import miuix.appcompat.R;
import miuix.appcompat.app.AccessibilityDelegateProvider;
import miuix.appcompat.app.AlertDialog;
import miuix.autodensity.DensityUtil;
import miuix.core.util.EnvStateManager;
import miuix.internal.util.ViewUtils;
import miuix.view.HapticCompat;
import miuix.view.HapticFeedbackConstants;

/* JADX INFO: loaded from: classes2.dex */
public class ArrowActionSheet extends AlertDialog implements ActionSheet.IActionSheet {
    public final ActionSheetController mActionController;
    public final View mAnchorView;
    public int mArrowIconLongSide;
    public int mArrowIconShortSide;
    public int mArrowLinkOffset;
    public ActionSheet.ArrowMode mArrowMode;
    public ActionSheet.ContentController mContentController;
    public Context mContext;
    public int mDefaultMargin;
    public int mFixedWidth;
    public boolean mIsDismissForShift;
    public boolean mIsFromAlertShape;
    public Point mOffset;
    public int mOffsetToPoint;

    /* JADX INFO: renamed from: miuix.internal.widget.ArrowActionSheet$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$miuix$internal$widget$ActionSheet$ArrowMode;

        static {
            int[] iArr = new int[ActionSheet.ArrowMode.values().length];
            $SwitchMap$miuix$internal$widget$ActionSheet$ArrowMode = iArr;
            try {
                iArr[ActionSheet.ArrowMode.ARROW_TOP_LEFT_MODE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$miuix$internal$widget$ActionSheet$ArrowMode[ActionSheet.ArrowMode.ARROW_TOP_MODE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$miuix$internal$widget$ActionSheet$ArrowMode[ActionSheet.ArrowMode.ARROW_TOP_RIGHT_MODE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$miuix$internal$widget$ActionSheet$ArrowMode[ActionSheet.ArrowMode.ARROW_LEFT_MODE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$miuix$internal$widget$ActionSheet$ArrowMode[ActionSheet.ArrowMode.ARROW_LEFT_TOP_MODE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$miuix$internal$widget$ActionSheet$ArrowMode[ActionSheet.ArrowMode.ARROW_RIGHT_MODE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$miuix$internal$widget$ActionSheet$ArrowMode[ActionSheet.ArrowMode.ARROW_RIGHT_TOP_MODE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$miuix$internal$widget$ActionSheet$ArrowMode[ActionSheet.ArrowMode.ARROW_LEFT_BOTTOM_MODE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$miuix$internal$widget$ActionSheet$ArrowMode[ActionSheet.ArrowMode.ARROW_RIGHT_BOTTOM_MODE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$miuix$internal$widget$ActionSheet$ArrowMode[ActionSheet.ArrowMode.ARROW_BOTTOM_LEFT_MODE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$miuix$internal$widget$ActionSheet$ArrowMode[ActionSheet.ArrowMode.ARROW_BOTTOM_MODE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$miuix$internal$widget$ActionSheet$ArrowMode[ActionSheet.ArrowMode.ARROW_BOTTOM_RIGHT_MODE.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    public ArrowActionSheet(Context context, int i, View view) {
        super(context, i);
        this.mOffset = new Point();
        this.mActionController = new ActionSheetController(context, this, getWindow(), ActionSheet.ActionSheetMode.ARROW_MODE);
        this.mAnchorView = view;
        this.mContext = context;
        init(context);
    }

    public ArrowActionSheet(Context context, View view) {
        this(context, 0, view);
    }

    private void init(Context context) {
        this.mArrowLinkOffset = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_action_sheet_arrow_icon_link_offset);
        this.mArrowIconLongSide = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_action_sheet_arrow_icon_long_side);
        this.mArrowIconShortSide = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_action_sheet_arrow_icon_short_side);
        this.mFixedWidth = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_action_sheet_content_arrow_fixed_width);
        this.mDefaultMargin = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_dialog_ime_margin);
        this.mOffsetToPoint = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_action_sheet_arrow_offset_to_point);
        setContentController();
    }

    private void setContentController() {
        ActionSheet.ContentController contentController = new ActionSheet.ContentController() { // from class: miuix.internal.widget.ArrowActionSheet.1
            @Override // miuix.internal.widget.ActionSheet.ContentController
            public int calcContentPanelHeight(Context context, ViewGroup viewGroup, ViewGroup viewGroup2, int i, WindowInsets windowInsets) {
                return -2;
            }

            @Override // miuix.internal.widget.ActionSheet.ContentController
            public int calcContentPanelWidth(Context context, ViewGroup viewGroup, ViewGroup viewGroup2, int i, WindowInsets windowInsets) {
                return ArrowActionSheet.this.mFixedWidth;
            }

            @Override // miuix.internal.widget.ActionSheet.ContentController
            public int[] calcHorizontalMargin(Context context, int i, WindowInsets windowInsets) {
                return new int[]{ArrowActionSheet.this.mDefaultMargin, ArrowActionSheet.this.mDefaultMargin};
            }

            @Override // miuix.internal.widget.ActionSheet.ContentController
            public int[] calcVerticalMargin(Context context, int i, WindowInsets windowInsets) {
                if (windowInsets == null) {
                    return new int[]{ArrowActionSheet.this.mDefaultMargin, ArrowActionSheet.this.mDefaultMargin};
                }
                Insets insetsIgnoringVisibility = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
                return new int[]{insetsIgnoringVisibility.top + ArrowActionSheet.this.mDefaultMargin, insetsIgnoringVisibility.bottom + ArrowActionSheet.this.mDefaultMargin};
            }

            @Override // miuix.internal.widget.ActionSheet.ContentController
            public Point computeContentPosition(Rect rect, Point point, Point point2, ViewGroup viewGroup, ViewGroup viewGroup2) {
                Point showPosition = getShowPosition(rect, point2, ArrowActionSheet.this.mArrowMode);
                Point pointReComputePosition = reComputePosition(rect, point2, viewGroup2, detectOverflow(rect, point, point2, viewGroup != null ? viewGroup.getRootWindowInsets() : null, showPosition));
                if (pointReComputePosition != null) {
                    showPosition.x = pointReComputePosition.x;
                    showPosition.y = pointReComputePosition.y;
                }
                return showPosition;
            }

            /* JADX WARN: Code duplicated, block: B:30:0x007d A[PHI: r5
  0x007d: PHI (r5v8 boolean) = (r5v5 boolean), (r5v13 boolean), (r5v21 boolean) binds: [B:44:0x00a6, B:37:0x0092, B:29:0x007b] A[DONT_GENERATE, DONT_INLINE]] */
            /* JADX WARN: Code duplicated, block: B:31:0x007f A[PHI: r5
  0x007f: PHI (r5v7 boolean) = (r5v5 boolean), (r5v13 boolean), (r5v21 boolean) binds: [B:44:0x00a6, B:37:0x0092, B:29:0x007b] A[DONT_GENERATE, DONT_INLINE]] */
            /* JADX WARN: Code duplicated, block: B:75:0x011f  */
            public final boolean[] detectOverflow(Rect rect, Point point, Point point2, WindowInsets windowInsets, Point point3) {
                boolean z;
                boolean z2;
                boolean z3;
                boolean z4;
                if (ArrowActionSheet.this.mAnchorView == null) {
                    return new boolean[]{false, false, false, false};
                }
                if (point.x > 0) {
                    int[] iArrCalcHorizontalMargin = calcHorizontalMargin(ArrowActionSheet.this.mContext, point.x, windowInsets);
                    if (ArrowActionSheet.this.mArrowMode == ActionSheet.ArrowMode.ARROW_TOP_MODE || ArrowActionSheet.this.mArrowMode == ActionSheet.ArrowMode.ARROW_BOTTOM_MODE) {
                        int i = point3.x;
                        z2 = i < iArrCalcHorizontalMargin[0];
                        if (i + point2.x + iArrCalcHorizontalMargin[1] > point.x) {
                            z = true;
                        } else {
                            z = false;
                        }
                    } else if (ArrowActionSheet.this.mArrowMode == ActionSheet.ArrowMode.ARROW_TOP_RIGHT_MODE || ArrowActionSheet.this.mArrowMode == ActionSheet.ArrowMode.ARROW_RIGHT_TOP_MODE || ArrowActionSheet.this.mArrowMode == ActionSheet.ArrowMode.ARROW_RIGHT_MODE || ArrowActionSheet.this.mArrowMode == ActionSheet.ArrowMode.ARROW_RIGHT_BOTTOM_MODE || ArrowActionSheet.this.mArrowMode == ActionSheet.ArrowMode.ARROW_BOTTOM_RIGHT_MODE) {
                        int i2 = point3.x;
                        z2 = i2 < iArrCalcHorizontalMargin[0];
                        if (i2 + point2.x + iArrCalcHorizontalMargin[1] > point.x) {
                            z = true;
                        } else {
                            z = false;
                        }
                    } else {
                        int i3 = point3.x;
                        z2 = i3 < iArrCalcHorizontalMargin[0];
                        if (i3 + point2.x + iArrCalcHorizontalMargin[1] > point.x) {
                            z = true;
                        } else {
                            z = false;
                        }
                    }
                } else {
                    z = false;
                    z2 = false;
                }
                if (point.y > 0) {
                    int[] iArrCalcVerticalMargin = calcVerticalMargin(ArrowActionSheet.this.mContext, point.y, windowInsets);
                    if (ArrowActionSheet.this.mArrowMode == ActionSheet.ArrowMode.ARROW_TOP_LEFT_MODE || ArrowActionSheet.this.mArrowMode == ActionSheet.ArrowMode.ARROW_TOP_MODE || ArrowActionSheet.this.mArrowMode == ActionSheet.ArrowMode.ARROW_TOP_RIGHT_MODE) {
                        z3 = rect.bottom < point2.y + iArrCalcVerticalMargin[1];
                        z4 = false;
                    } else if (ArrowActionSheet.this.mArrowMode == ActionSheet.ArrowMode.ARROW_BOTTOM_LEFT_MODE || ArrowActionSheet.this.mArrowMode == ActionSheet.ArrowMode.ARROW_BOTTOM_MODE || ArrowActionSheet.this.mArrowMode == ActionSheet.ArrowMode.ARROW_BOTTOM_RIGHT_MODE) {
                        int i4 = rect.top;
                        int i5 = point2.y;
                        int i6 = iArrCalcVerticalMargin[0];
                        int i7 = point3.y;
                        z4 = i7 < i6;
                        z3 = (i7 + i5) + iArrCalcVerticalMargin[1] > point.y;
                    } else {
                        z3 = false;
                        z4 = false;
                    }
                } else {
                    z3 = false;
                    z4 = false;
                }
                return new boolean[]{z2, z4, z, z3};
            }

            @Override // miuix.internal.widget.ActionSheet.ContentController
            public View getArrowAnchor() {
                return ArrowActionSheet.this.mAnchorView;
            }

            @Override // miuix.internal.widget.ActionSheet.ContentController
            public ActionSheet.ArrowMode getArrowMode() {
                return ArrowActionSheet.this.mArrowMode;
            }

            public final Point getReferencePoint(Rect rect, ActionSheet.ArrowMode arrowMode) {
                Point point = new Point();
                boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(ArrowActionSheet.this.mAnchorView);
                switch (AnonymousClass2.$SwitchMap$miuix$internal$widget$ActionSheet$ArrowMode[arrowMode.ordinal()]) {
                    case 1:
                    case 2:
                    case 3:
                        point.x = rect.left + (ArrowActionSheet.this.mAnchorView.getWidth() / 2);
                        point.y = rect.top + ArrowActionSheet.this.mAnchorView.getHeight();
                        break;
                    case 4:
                    case 5:
                    case 8:
                        if (!zIsLayoutRtl) {
                            point.x = rect.left + ArrowActionSheet.this.mAnchorView.getWidth();
                            point.y = rect.top + (ArrowActionSheet.this.mAnchorView.getHeight() / 2);
                        } else {
                            point.x = rect.left;
                            point.y = rect.top + (ArrowActionSheet.this.mAnchorView.getHeight() / 2);
                        }
                        break;
                    case 6:
                    case 7:
                    case 9:
                        if (!zIsLayoutRtl) {
                            point.x = rect.left;
                            point.y = rect.top + (ArrowActionSheet.this.mAnchorView.getHeight() / 2);
                        } else {
                            point.x = rect.left + ArrowActionSheet.this.mAnchorView.getWidth();
                            point.y = rect.top + (ArrowActionSheet.this.mAnchorView.getHeight() / 2);
                        }
                        break;
                    case 10:
                    case 11:
                    case 12:
                        point.x = rect.left + (ArrowActionSheet.this.mAnchorView.getWidth() / 2);
                        point.y = rect.top;
                        break;
                }
                return point;
            }

            public final Point getShowOffset(ActionSheet.ArrowMode arrowMode, Point point) {
                Point point2 = new Point();
                boolean z = ArrowActionSheet.this.mAnchorView != null && ViewUtils.isLayoutRtl(ArrowActionSheet.this.mAnchorView);
                switch (AnonymousClass2.$SwitchMap$miuix$internal$widget$ActionSheet$ArrowMode[arrowMode.ordinal()]) {
                    case 1:
                        if (!z) {
                            point2.x = (ArrowActionSheet.this.mArrowLinkOffset + (ArrowActionSheet.this.mArrowIconLongSide / 2)) * (-1);
                            point2.y = 0;
                        } else {
                            point2.x = (point.x * (-1)) + ArrowActionSheet.this.mArrowLinkOffset + (ArrowActionSheet.this.mArrowIconLongSide / 2);
                            point2.y = 0;
                        }
                        break;
                    case 2:
                        point2.x = (point.x / 2) * (-1);
                        point2.y = 0;
                        break;
                    case 3:
                        if (!z) {
                            point2.x = (point.x * (-1)) + ArrowActionSheet.this.mArrowLinkOffset + (ArrowActionSheet.this.mArrowIconLongSide / 2);
                            point2.y = 0;
                        } else {
                            point2.x = (ArrowActionSheet.this.mArrowLinkOffset + (ArrowActionSheet.this.mArrowIconLongSide / 2)) * (-1);
                            point2.y = 0;
                        }
                        break;
                    case 4:
                        if (!z) {
                            point2.x = 0;
                            point2.y = (point.y / 2) * (-1);
                        } else {
                            point2.x = point.x * (-1);
                            point2.y = (point.y / 2) * (-1);
                        }
                        break;
                    case 5:
                        if (!z) {
                            point2.x = 0;
                            point2.y = (ArrowActionSheet.this.mArrowLinkOffset + (ArrowActionSheet.this.mArrowIconLongSide / 2)) * (-1);
                        } else {
                            point2.x = point.x * (-1);
                            point2.y = (ArrowActionSheet.this.mArrowLinkOffset + (ArrowActionSheet.this.mArrowIconLongSide / 2)) * (-1);
                        }
                        break;
                    case 6:
                        if (!z) {
                            point2.x = point.x * (-1);
                            point2.y = (point.y / 2) * (-1);
                        } else {
                            point2.x = 0;
                            point2.y = (point.y / 2) * (-1);
                        }
                        break;
                    case 7:
                        if (!z) {
                            point2.x = point.x * (-1);
                            point2.y = (ArrowActionSheet.this.mArrowLinkOffset + (ArrowActionSheet.this.mArrowIconLongSide / 2)) * (-1);
                        } else {
                            point2.x = 0;
                            point2.y = (ArrowActionSheet.this.mArrowLinkOffset + (ArrowActionSheet.this.mArrowIconLongSide / 2)) * (-1);
                        }
                        break;
                    case 8:
                        if (!z) {
                            point2.x = 0;
                            point2.y = (point.y * (-1)) + ArrowActionSheet.this.mArrowLinkOffset + (ArrowActionSheet.this.mArrowIconLongSide / 2);
                        } else {
                            point2.x = point.x * (-1);
                            point2.y = (point.y * (-1)) + ArrowActionSheet.this.mArrowLinkOffset + (ArrowActionSheet.this.mArrowIconLongSide / 2);
                        }
                        break;
                    case 9:
                        if (!z) {
                            point2.x = point.x * (-1);
                            point2.y = (point.y * (-1)) + ArrowActionSheet.this.mArrowLinkOffset + (ArrowActionSheet.this.mArrowIconLongSide / 2);
                        } else {
                            point2.x = 0;
                            point2.y = (point.y * (-1)) + ArrowActionSheet.this.mArrowLinkOffset + (ArrowActionSheet.this.mArrowIconLongSide / 2);
                        }
                        break;
                    case 10:
                        if (!z) {
                            point2.x = (ArrowActionSheet.this.mArrowLinkOffset + (ArrowActionSheet.this.mArrowIconLongSide / 2)) * (-1);
                            point2.y = point.y * (-1);
                        } else {
                            point2.x = (point.x * (-1)) + ArrowActionSheet.this.mArrowLinkOffset + (ArrowActionSheet.this.mArrowIconLongSide / 2);
                            point2.y = point.y * (-1);
                        }
                        break;
                    case 11:
                        point2.x = (point.x / 2) * (-1);
                        point2.y = point.y * (-1);
                        break;
                    case 12:
                        if (!z) {
                            point2.x = (point.x * (-1)) + ArrowActionSheet.this.mArrowLinkOffset + (ArrowActionSheet.this.mArrowIconLongSide / 2);
                            point2.y = point.y * (-1);
                        } else {
                            point2.x = (ArrowActionSheet.this.mArrowLinkOffset + (ArrowActionSheet.this.mArrowIconLongSide / 2)) * (-1);
                            point2.y = point.y * (-1);
                        }
                        break;
                }
                return point2;
            }

            public final Point getShowPosition(Rect rect, Point point, ActionSheet.ArrowMode arrowMode) {
                Point point2 = new Point();
                Point referencePoint = getReferencePoint(rect, arrowMode);
                Point showOffset = getShowOffset(arrowMode, point);
                point2.x = referencePoint.x + showOffset.x + ArrowActionSheet.this.mOffset.x;
                point2.y = referencePoint.y + showOffset.y + ArrowActionSheet.this.mOffset.y;
                handleReservedSpace(point2, arrowMode);
                return point2;
            }

            public final void handleReservedSpace(Point point, ActionSheet.ArrowMode arrowMode) {
                boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(ArrowActionSheet.this.mAnchorView);
                switch (AnonymousClass2.$SwitchMap$miuix$internal$widget$ActionSheet$ArrowMode[arrowMode.ordinal()]) {
                    case 1:
                    case 2:
                    case 3:
                        point.y += ArrowActionSheet.this.mOffsetToPoint;
                        break;
                    case 4:
                    case 5:
                    case 8:
                        if (!zIsLayoutRtl) {
                            point.x += ArrowActionSheet.this.mOffsetToPoint;
                        } else {
                            point.x -= ArrowActionSheet.this.mOffsetToPoint;
                        }
                        break;
                    case 6:
                    case 7:
                    case 9:
                        if (!zIsLayoutRtl) {
                            point.x -= ArrowActionSheet.this.mOffsetToPoint;
                        } else {
                            point.x += ArrowActionSheet.this.mOffsetToPoint;
                        }
                        break;
                    case 10:
                    case 11:
                    case 12:
                        point.y -= ArrowActionSheet.this.mOffsetToPoint;
                        break;
                }
            }

            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            public final ActionSheet.ArrowMode overflowCompactStrategy(boolean[] zArr) {
                ActionSheet.ArrowMode arrowMode = ArrowActionSheet.this.mArrowMode;
                boolean z = zArr[0];
                boolean z2 = zArr[1];
                boolean z3 = zArr[2];
                boolean z4 = zArr[3];
                switch (AnonymousClass2.$SwitchMap$miuix$internal$widget$ActionSheet$ArrowMode[ArrowActionSheet.this.mArrowMode.ordinal()]) {
                    case 1:
                        if (z && !z3) {
                            return ActionSheet.ArrowMode.ARROW_LEFT_TOP_MODE;
                        }
                        if (z4) {
                            return ActionSheet.ArrowMode.ARROW_BOTTOM_LEFT_MODE;
                        }
                        return arrowMode;
                    case 2:
                        if (z4) {
                            return ActionSheet.ArrowMode.ARROW_BOTTOM_MODE;
                        }
                        return arrowMode;
                    case 3:
                        if (z3 && !z) {
                            return ActionSheet.ArrowMode.ARROW_RIGHT_TOP_MODE;
                        }
                        if (z4) {
                            return ActionSheet.ArrowMode.ARROW_BOTTOM_RIGHT_MODE;
                        }
                        return arrowMode;
                    case 4:
                    case 5:
                        if (z3 && !z4) {
                            return ActionSheet.ArrowMode.ARROW_TOP_LEFT_MODE;
                        }
                        if (z3) {
                            return ActionSheet.ArrowMode.ARROW_BOTTOM_LEFT_MODE;
                        }
                        return arrowMode;
                    case 6:
                    case 7:
                        if (z && !z4) {
                            return ActionSheet.ArrowMode.ARROW_TOP_RIGHT_MODE;
                        }
                        if (z) {
                            return ActionSheet.ArrowMode.ARROW_BOTTOM_RIGHT_MODE;
                        }
                        return arrowMode;
                    case 8:
                        if (z3 && !z2) {
                            return ActionSheet.ArrowMode.ARROW_BOTTOM_LEFT_MODE;
                        }
                        if (!z3 && z2) {
                            return ActionSheet.ArrowMode.ARROW_LEFT_TOP_MODE;
                        }
                        return arrowMode;
                    case 9:
                        if (z && !z2) {
                            return ActionSheet.ArrowMode.ARROW_BOTTOM_RIGHT_MODE;
                        }
                        if (!z && z2) {
                            return ActionSheet.ArrowMode.ARROW_RIGHT_TOP_MODE;
                        }
                        return arrowMode;
                    case 10:
                        if (z && !z2) {
                            return ActionSheet.ArrowMode.ARROW_LEFT_BOTTOM_MODE;
                        }
                        if (z2) {
                            return ActionSheet.ArrowMode.ARROW_LEFT_TOP_MODE;
                        }
                        return arrowMode;
                    case 11:
                        if (z2) {
                            return ActionSheet.ArrowMode.ARROW_TOP_MODE;
                        }
                        return arrowMode;
                    case 12:
                        if (z3 && !z2) {
                            return ActionSheet.ArrowMode.ARROW_RIGHT_BOTTOM_MODE;
                        }
                        if (z2) {
                            return ActionSheet.ArrowMode.ARROW_RIGHT_TOP_MODE;
                        }
                        return arrowMode;
                    default:
                        return arrowMode;
                }
            }

            public final Point reComputePosition(Rect rect, Point point, ViewGroup viewGroup, boolean[] zArr) {
                ActionSheet.ArrowMode arrowModeOverflowCompactStrategy = overflowCompactStrategy(zArr);
                if (arrowModeOverflowCompactStrategy != ArrowActionSheet.this.mArrowMode) {
                    return getShowPosition(rect, point, arrowModeOverflowCompactStrategy);
                }
                return null;
            }
        };
        this.mContentController = contentController;
        ActionSheetController actionSheetController = this.mActionController;
        if (actionSheetController != null) {
            actionSheetController.setContentController(contentController);
        }
    }

    public AlertActionSheet createAlertActionSheet(View view) {
        AlertActionSheet alertActionSheet = new AlertActionSheet(this.mContext);
        alertActionSheet.setArrowActionAnchor(view);
        alertActionSheet.setArrowMode(this.mArrowMode);
        alertActionSheet.setCanceledOnTouchOutside(isCanceledOnTouchOutside());
        alertActionSheet.setHapticFeedbackEnabled(isHapticFeedbackEnabled());
        if (this.mActionController.getMessage() != null) {
            alertActionSheet.setMessage(this.mActionController.getMessage());
        }
        if (this.mActionController.getActionItems() != null && this.mActionController.getItemClickListener() != null) {
            alertActionSheet.setActionItems(this.mActionController.getActionItems(), this.mActionController.getItemClickListener());
        }
        if (this.mActionController.getActionItems() != null && this.mActionController.getItemClickListener() != null && this.mActionController.getItemTypes() != null) {
            alertActionSheet.setActionItems(this.mActionController.getActionItems(), this.mActionController.getItemTypes(), this.mActionController.getItemClickListener());
        }
        if (this.mActionController.getShowAnimListener() != null) {
            alertActionSheet.setOnShowAnimListener(this.mActionController.getShowAnimListener());
        }
        if (this.mActionController.getOnShowListener() != null) {
            alertActionSheet.setActionSheetOnShowListener(this.mActionController.getOnShowListener());
        }
        if (this.mActionController.getOnDismissListener() != null) {
            alertActionSheet.setActionSheetOnDismissListener(this.mActionController.getOnDismissListener());
        }
        if (this.mActionController.getOnKeyListener() != null) {
            alertActionSheet.setOnKeyListener(this.mActionController.getOnKeyListener());
            alertActionSheet.setActionSheetOnKeyListener(this.mActionController.getOnKeyListener());
        }
        if (this.mActionController.getListViewAdapter() != null) {
            alertActionSheet.setListViewAdapter(this.mActionController.getListViewAdapter());
        }
        if (this.mActionController.getOnCancelListener() != null) {
            alertActionSheet.setOnCancelListener(this.mActionController.getOnCancelListener());
            alertActionSheet.setActionSheetOnCancelListener(this.mActionController.getOnCancelListener());
        }
        if (this.mActionController.getConfigurationChangedListener() != null) {
            alertActionSheet.setConfigurationChangedListener(this.mActionController.getConfigurationChangedListener());
        }
        if (this.mActionController.getItemProvider() != null) {
            alertActionSheet.setItemAccessibilityProvider(this.mActionController.getItemProvider());
        }
        return alertActionSheet;
    }

    @Override // miuix.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        Window window = getWindow();
        View decorView = window != null ? window.getDecorView() : null;
        if (this.mActionController.isShowingAnimation()) {
            this.mActionController.setPendingDismiss(true);
            return;
        }
        this.mActionController.setPendingDismiss(false);
        if ((decorView != null ? DensityUtil.findAutoDensityContextWrapper(decorView.getContext()) : null) != null) {
            EnvStateManager.removeInfoOfContext(decorView.getContext());
        }
        Activity associatedActivity = getAssociatedActivity();
        if (associatedActivity == null || !associatedActivity.isFinishing()) {
            dismissWithAnimationOrNot(decorView);
        } else {
            dismissIfAttachedToWindow(decorView);
        }
    }

    public void dismissForShiftWithoutAnimation() {
        setIsDismissForShift(true);
        dismissWithoutAnimation();
    }

    @Override // miuix.appcompat.app.AlertDialog
    public void dismissWithAnimationExistDecorView(View view) {
        if (Thread.currentThread() == view.getHandler().getLooper().getThread()) {
            this.mActionController.dismiss(this.mOnDismiss);
        } else {
            view.post(new Runnable() { // from class: miuix.internal.widget.ArrowActionSheet$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ArrowActionSheet arrowActionSheet = this.f$0;
                    arrowActionSheet.mActionController.dismiss(arrowActionSheet.mOnDismiss);
                }
            });
        }
    }

    @Override // miuix.appcompat.app.AlertDialog
    public void dismissWithoutAnimation() {
        Window window = getWindow();
        View decorView = window != null ? window.getDecorView() : null;
        if (decorView == null || decorView.isAttachedToWindow()) {
            if (this.mActionController.isShowingAnimation()) {
                this.mActionController.setPendingDismiss(true);
                return;
            }
            this.mActionController.setPendingDismiss(false);
            if ((decorView != null ? DensityUtil.findAutoDensityContextWrapper(decorView.getContext()) : null) != null) {
                EnvStateManager.removeInfoOfContext(decorView.getContext());
            }
            realDismiss();
        }
    }

    @Override // miuix.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, android.app.Dialog, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (this.mActionController.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        return miuixSuperDispatchKeyEvent(keyEvent);
    }

    public View getArrowAnchor() {
        return this.mAnchorView;
    }

    public ActionSheet.ArrowMode getArrowMode() {
        return this.mArrowMode;
    }

    @Override // miuix.appcompat.app.AlertDialog
    public ListView getListView() {
        return this.mActionController.getListView();
    }

    public boolean isCanceledOnTouchOutside() {
        return this.mActionController.isCanceledOnTouchOutside();
    }

    public boolean isDismissForShift() {
        return this.mIsDismissForShift;
    }

    public boolean isFromAlertShape() {
        return this.mIsFromAlertShape;
    }

    public boolean isHapticFeedbackEnabled() {
        return this.mActionController.mHapticFeedbackEnabled;
    }

    @Override // miuix.appcompat.app.AlertDialog, android.app.Dialog, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        View decorView = window != null ? window.getDecorView() : null;
        if (this.mActionController.mHapticFeedbackEnabled && decorView != null) {
            HapticCompat.performHapticFeedbackAsync(decorView, HapticFeedbackConstants.MIUI_ALERT, HapticFeedbackConstants.MIUI_POPUP_NORMAL);
        }
        this.mActionController.onAttachedToWindow();
        setAccessibilityDelegate(decorView);
    }

    @Override // miuix.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    public void onCreate(Bundle bundle) {
        AlertDialog.LifecycleOwnerCompat lifecycleOwnerCompat;
        if (isSystemSpecialUiThread() && (lifecycleOwnerCompat = this.mLifecycleOwnerCompat) != null) {
            lifecycleOwnerCompat.onCreate();
        }
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(0);
        }
        superOnCreate(bundle);
        this.mActionController.installContent(bundle);
    }

    @Override // miuix.appcompat.app.AlertDialog, android.app.Dialog, android.view.Window.Callback
    public void onDetachedFromWindow() {
        superOnDetachedFromWindow();
        this.mActionController.onDetachedFromWindow();
    }

    @Override // miuix.appcompat.app.AlertDialog, androidx.activity.ComponentDialog, android.app.Dialog
    public void onStart() {
        superOnStart();
        this.mActionController.onStart();
    }

    @Override // miuix.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    public void onStop() {
        AlertDialog.LifecycleOwnerCompat lifecycleOwnerCompat;
        AlertDialog.LifecycleOwnerCompat lifecycleOwnerCompat2;
        if (isSystemSpecialUiThread() && (lifecycleOwnerCompat2 = this.mLifecycleOwnerCompat) != null) {
            lifecycleOwnerCompat2.onStopBefore();
        }
        superOnStop();
        if (!isSystemSpecialUiThread() || (lifecycleOwnerCompat = this.mLifecycleOwnerCompat) == null) {
            return;
        }
        lifecycleOwnerCompat.onStopAfter();
    }

    public void setActionItems(CharSequence[] charSequenceArr, DialogInterface.OnClickListener onClickListener) {
        this.mActionController.setActionItems(charSequenceArr, onClickListener);
    }

    public void setActionItems(CharSequence[] charSequenceArr, ActionSheet.ActionSheetItemType[] actionSheetItemTypeArr, DialogInterface.OnClickListener onClickListener) {
        this.mActionController.setActionItems(charSequenceArr, actionSheetItemTypeArr, onClickListener);
    }

    public void setActionSheetOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        this.mActionController.setOnCancelListener(onCancelListener);
    }

    public void setActionSheetOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.mActionController.setOnDismissListener(onDismissListener);
    }

    public void setActionSheetOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        this.mActionController.setOnKeyListener(onKeyListener);
    }

    public void setActionSheetOnShowListener(DialogInterface.OnShowListener onShowListener) {
        this.mActionController.setOnShowListener(onShowListener);
    }

    public void setArrowMode(ActionSheet.ArrowMode arrowMode) {
        this.mArrowMode = arrowMode;
    }

    @Override // miuix.appcompat.app.AlertDialog, android.app.Dialog
    public void setCanceledOnTouchOutside(boolean z) {
        this.mActionController.setCanceledOnTouchOutside(z);
    }

    @Override // miuix.appcompat.app.AlertDialog
    public void setConfigurationChangedListener(AlertDialog.OnConfigurationChangedListener onConfigurationChangedListener) {
        this.mActionController.setConfigurationChangedListener(onConfigurationChangedListener);
    }

    public void setEnableEnterAnim(boolean z) {
        this.mActionController.setEnableEnterAnim(z);
    }

    public void setHapticFeedbackEnabled(boolean z) {
        this.mActionController.mHapticFeedbackEnabled = z;
    }

    public void setIsDismissForShift(boolean z) {
        this.mIsDismissForShift = z;
    }

    public void setIsFromAlertShape(boolean z) {
        this.mIsFromAlertShape = z;
    }

    public void setItemAccessibilityProvider(AccessibilityDelegateProvider accessibilityDelegateProvider) {
        this.mActionController.setItemProvider(accessibilityDelegateProvider);
    }

    public void setListViewAdapter(ListAdapter listAdapter) {
        this.mActionController.setListViewAdapter(listAdapter);
    }

    public void setMessage(CharSequence charSequence) {
        this.mActionController.setMessage(charSequence);
    }

    public void setOffset(int i, int i2) {
        Point point = this.mOffset;
        point.x = i;
        point.y = i2;
    }

    @Override // miuix.appcompat.app.AlertDialog
    public void setOnShowAnimListener(AlertDialog.OnDialogShowAnimListener onDialogShowAnimListener) {
        this.mActionController.setShowAnimListener(onDialogShowAnimListener);
    }

    @Override // miuix.appcompat.app.AlertDialog, android.app.Dialog
    public void show() {
        superShow();
    }
}
