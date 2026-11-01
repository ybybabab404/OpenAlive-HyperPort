package miuix.visual.check;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.appcompat.widget.AppCompatTextView;
import java.lang.ref.WeakReference;
import java.util.Collection;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ColorProperty;

/* JADX INFO: loaded from: classes3.dex */
public class VisualCheckedTextView extends AppCompatTextView implements VisualCheckItem {
    public static int[] CHECKED_STATE = {R.attr.state_checked};
    public static int[] UNCHECKED_STATE = {-16842912};
    public IStateStyle iCheckedStateStyle;
    public IStateStyle iUnCheckedStateStyle;
    public boolean mChecked;
    public int mCheckedColor;
    public ColorProperty mColorProperty;
    public TransitionListener mListener;
    public int mTextColorId;
    public int mUncheckedColor;

    public static class InnerTransitionListener extends TransitionListener {
        public WeakReference mRef;

        public InnerTransitionListener(VisualCheckedTextView visualCheckedTextView) {
            this.mRef = new WeakReference(visualCheckedTextView);
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onUpdate(Object obj, Collection collection) {
            super.onUpdate(obj, collection);
            VisualCheckedTextView visualCheckedTextView = (VisualCheckedTextView) this.mRef.get();
            UpdateInfo updateInfoFindByName = UpdateInfo.findByName(collection, "checkedTextView");
            if (visualCheckedTextView == null || updateInfoFindByName == null) {
                return;
            }
            visualCheckedTextView.setTextColor(updateInfoFindByName.getIntValue());
        }
    }

    public VisualCheckedTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mUncheckedColor = getTextColors().getColorForState(UNCHECKED_STATE, getResources().getColor(miuix.visualcheck.R.color.visual_check_textview_unchecked_text_color));
        this.mCheckedColor = getTextColors().getColorForState(CHECKED_STATE, getResources().getColor(miuix.visualcheck.R.color.visual_check_textview_checked_text_color));
        this.mListener = new InnerTransitionListener(this);
        this.mColorProperty = new ColorProperty("checkedTextView");
        Folme.clean("text_color_checked");
        Folme.clean("text_color_unchecked");
        this.iCheckedStateStyle = Folme.useValue("text_color_checked").setFlags(1L);
        this.iUnCheckedStateStyle = Folme.useValue("text_color_unchecked").setFlags(1L);
        initTextColorId(context, attributeSet);
    }

    public final void initTextColorId(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, miuix.visualcheck.R.styleable.TextAppearance, 0, 0);
        try {
            int i = miuix.visualcheck.R.styleable.TextAppearance_android_textColor;
            if (typedArrayObtainStyledAttributes.hasValue(i)) {
                this.mTextColorId = typedArrayObtainStyledAttributes.getResourceId(i, 0);
            } else {
                this.mTextColorId = -1;
            }
            typedArrayObtainStyledAttributes.close();
        } catch (Throwable th) {
            if (typedArrayObtainStyledAttributes != null) {
                try {
                    typedArrayObtainStyledAttributes.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public boolean isChecked() {
        return this.mChecked;
    }

    @Override // miuix.visual.check.VisualCheckItem
    public void onChecked(boolean z) {
        this.mChecked = z;
        if (z) {
            setTextColor(this.mCheckedColor);
        } else {
            setTextColor(this.mUncheckedColor);
        }
    }

    @Override // miuix.visual.check.VisualCheckItem
    public void onVisualCheckBoxTouchEvent(VisualCheckBox visualCheckBox, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 6) {
            if (isChecked()) {
                this.iUnCheckedStateStyle.setTo(this.mColorProperty, Integer.valueOf(this.mCheckedColor)).to(this.mColorProperty, Integer.valueOf(this.mUncheckedColor), this.mListener);
            } else {
                this.iCheckedStateStyle.setTo(this.mColorProperty, Integer.valueOf(this.mUncheckedColor)).to(this.mColorProperty, Integer.valueOf(this.mCheckedColor), this.mListener);
            }
        }
    }
}
