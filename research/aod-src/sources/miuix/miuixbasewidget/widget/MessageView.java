package miuix.miuixbasewidget.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import miuix.animation.Folme;
import miuix.animation.IHoverStyle;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;
import miuix.miuixbasewidget.R;

/* JADX INFO: loaded from: classes2.dex */
public class MessageView extends LinearLayout {
    public Drawable mCloseBackground;
    public ImageView mEndIcon;
    public String mEndIconContentDescription;
    public Drawable mEndIconDrawable;
    public int mIconMarginHorizontal;
    public int mIconSize;
    public OnMessageViewCloseListener mOnMessageViewCloseListener;
    public OnMessageViewEndIconClickListener mOnMessageViewEndIconClickListener;
    public ImageView mStartIcon;
    public String mStartIconContentDescription;
    public Drawable mStartIconDrawable;
    public TextView mTextView;

    /* JADX INFO: renamed from: miuix.miuixbasewidget.widget.MessageView$2, reason: invalid class name */
    class AnonymousClass2 implements View.OnClickListener {
        public final /* synthetic */ MessageView this$0;

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            this.this$0.setVisibility(8);
            if (this.this$0.mOnMessageViewCloseListener != null) {
                this.this$0.mOnMessageViewCloseListener.onClosed();
            }
        }
    }

    public interface OnMessageViewCloseListener {
        void onClosed();
    }

    public interface OnMessageViewEndIconClickListener {
        void onEndIconClicked();
    }

    public MessageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MessageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context, attributeSet, i);
    }

    private void setEndIconVisible(boolean z) {
        View viewFindViewById = findViewById(R.id.close);
        if (z) {
            if (viewFindViewById == null) {
                addEndIcon();
            }
        } else if (viewFindViewById != null) {
            removeView(viewFindViewById);
        }
    }

    private void setStartIconVisible(boolean z) {
        View viewFindViewById = findViewById(R.id.start);
        if (z) {
            if (viewFindViewById == null) {
                addStartIcon();
            }
        } else if (viewFindViewById != null) {
            removeView(viewFindViewById);
        }
    }

    public final void addEndIcon() {
        this.mEndIcon = new ImageView(getContext());
        int i = this.mIconSize;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i, i);
        layoutParams.setMarginStart(this.mIconMarginHorizontal);
        this.mEndIcon.setId(R.id.close);
        this.mEndIcon.setBackground(this.mEndIconDrawable);
        this.mEndIcon.setContentDescription(this.mEndIconContentDescription);
        this.mEndIcon.setOnClickListener(new View.OnClickListener() { // from class: miuix.miuixbasewidget.widget.MessageView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (MessageView.this.mOnMessageViewEndIconClickListener != null) {
                    MessageView.this.mOnMessageViewEndIconClickListener.onEndIconClicked();
                    return;
                }
                MessageView.this.setVisibility(8);
                if (MessageView.this.mOnMessageViewCloseListener != null) {
                    MessageView.this.mOnMessageViewCloseListener.onClosed();
                }
            }
        });
        addView(this.mEndIcon, layoutParams);
        Folme.useAt(this.mEndIcon).touch().handleTouchOf(this.mEndIcon, new AnimConfig[0]);
    }

    public final void addStartIcon() {
        this.mStartIcon = new ImageView(getContext());
        int i = this.mIconSize;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i, i);
        layoutParams.setMarginEnd(this.mIconMarginHorizontal);
        this.mStartIcon.setId(R.id.start);
        this.mStartIcon.setBackground(this.mStartIconDrawable);
        this.mStartIcon.setContentDescription(this.mStartIconContentDescription);
        addView(this.mStartIcon, layoutParams);
    }

    public final void initView(Context context, AttributeSet attributeSet, int i) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MessageView, i, R.style.Widget_MessageView);
        String string = typedArrayObtainStyledAttributes.getString(R.styleable.MessageView_android_text);
        ColorStateList colorStateList = AppCompatResources.getColorStateList(context, typedArrayObtainStyledAttributes.getResourceId(R.styleable.MessageView_android_textColor, R.color.miuix_appcompat_message_view_text_color_light));
        this.mIconMarginHorizontal = getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_message_view_icon_margin_horizontal);
        this.mIconSize = getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_message_view_icon_width);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(R.styleable.MessageView_closable, true);
        this.mCloseBackground = typedArrayObtainStyledAttributes.getDrawable(R.styleable.MessageView_closeBackground);
        this.mStartIconDrawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.MessageView_startIcon);
        Drawable drawable = this.mCloseBackground;
        if (drawable == null) {
            drawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.MessageView_endIcon);
        }
        this.mEndIconDrawable = drawable;
        String string2 = typedArrayObtainStyledAttributes.getString(R.styleable.MessageView_closeIconContentDescription);
        this.mEndIconContentDescription = string2;
        if (string2 == null) {
            this.mEndIconContentDescription = context.getResources().getString(R.string.close);
        }
        boolean z2 = this.mStartIconDrawable != null;
        typedArrayObtainStyledAttributes.recycle();
        setStartIconVisible(z2);
        this.mTextView = new TextView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.weight = 1.0f;
        this.mTextView.setId(android.R.id.text1);
        this.mTextView.setPaddingRelative(getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_message_view_text_padding_start), 0, 0, 0);
        this.mTextView.setText(string);
        this.mTextView.setTextColor(colorStateList);
        this.mTextView.setTextSize(0, getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_message_view_text_size));
        this.mTextView.setTextDirection(5);
        addView(this.mTextView, layoutParams);
        setEndIconVisible(z);
        setGravity(16);
        Folme.useAt(this).touch().setTintMode(0).setScale(1.0f, new ITouchStyle.TouchType[0]).handleTouchOf(this, new AnimConfig[0]);
        Folme.useAt(this).hover().setEffect(IHoverStyle.HoverEffect.FLOATED).handleHoverOf(this, new AnimConfig[0]);
    }

    public void setClosable(boolean z) {
        View viewFindViewById = findViewById(R.id.close);
        if (z) {
            if (viewFindViewById == null) {
                addEndIcon();
            }
        } else if (viewFindViewById != null) {
            removeView(viewFindViewById);
        }
    }

    @Deprecated
    public void setCloseIcon(@DrawableRes int i, String str) {
        if (this.mEndIcon != null) {
            setEndIcon(i, str);
        }
    }

    @Deprecated
    public void setCloseIcon(Drawable drawable, String str) {
        if (this.mEndIcon != null) {
            setEndIcon(drawable, str);
        }
    }

    public void setEndIcon(@DrawableRes int i, String str) {
        ImageView imageView = this.mEndIcon;
        if (imageView != null) {
            imageView.setBackgroundResource(i);
            this.mEndIcon.setContentDescription(str);
        }
    }

    public void setEndIcon(Drawable drawable, String str) {
        ImageView imageView = this.mEndIcon;
        if (imageView != null) {
            imageView.setBackground(drawable);
            this.mEndIcon.setContentDescription(str);
        }
    }

    public void setMessage(CharSequence charSequence) {
        this.mTextView.setText(charSequence);
    }

    public void setOnMessageViewCloseListener(OnMessageViewCloseListener onMessageViewCloseListener) {
        this.mOnMessageViewCloseListener = onMessageViewCloseListener;
    }

    public void setOnMessageViewEndIconClickListener(OnMessageViewEndIconClickListener onMessageViewEndIconClickListener) {
        this.mOnMessageViewEndIconClickListener = onMessageViewEndIconClickListener;
    }

    public void setStartIcon(@DrawableRes int i, String str) {
        ImageView imageView = this.mStartIcon;
        if (imageView != null) {
            imageView.setBackgroundResource(i);
            this.mStartIcon.setContentDescription(str);
        }
    }

    public void setStartIcon(Drawable drawable, String str) {
        ImageView imageView = this.mStartIcon;
        if (imageView != null) {
            imageView.setBackground(drawable);
            this.mStartIcon.setContentDescription(str);
        }
    }

    public void setTextColor(int i) {
        TextView textView = this.mTextView;
        if (textView != null) {
            textView.setTextColor(i);
        }
    }

    @SuppressLint({"ResourceAsColor"})
    public void setTextColorResource(@ColorRes int i) {
        if (this.mTextView == null || getContext() == null) {
            return;
        }
        this.mTextView.setTextColor(ContextCompat.getColor(getContext(), i));
    }
}
