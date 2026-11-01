package miuix.appcompat.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.NumberFormat;
import miuix.androidbasewidget.widget.ProgressBar;
import miuix.appcompat.R;
import miuix.core.util.MiuixUIUtils;

/* JADX INFO: loaded from: classes2.dex */
public class ProgressDialog extends AlertDialog {
    public View mButtonPanel;
    public boolean mHasStarted;
    public int mIncrementBy;
    public int mIncrementSecondaryBy;
    public boolean mIndeterminate;
    public Drawable mIndeterminateDrawable;
    public int mLayoutRes;
    public int mMax;
    public CharSequence mMessage;
    public TextView mMessageView;
    public ProgressBar mProgress;
    public Drawable mProgressDrawable;
    public String mProgressNumberFormat;
    public NumberFormat mProgressPercentFormat;
    public TextView mProgressPercentView;
    public int mProgressStyle;
    public int mProgressVal;
    public int mSecondaryProgressVal;
    public Handler mViewUpdateHandler;

    public ProgressDialog(Context context) {
        super(context);
        this.mProgressStyle = 0;
        initFormats();
    }

    public ProgressDialog(Context context, int i) {
        super(context, i);
        this.mProgressStyle = 0;
        initFormats();
    }

    public final void adjustButtonPanelMarginTopIfNeed() {
        if (this.mButtonPanel != null) {
            return;
        }
        View viewFindViewById = findViewById(R.id.buttonPanel);
        this.mButtonPanel = viewFindViewById;
        ViewGroup.LayoutParams layoutParams = viewFindViewById.getLayoutParams();
        if ((layoutParams instanceof ViewGroup.MarginLayoutParams) && this.mLayoutRes == R.layout.miuix_appcompat_progress_dialog) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin * 2, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
            this.mButtonPanel.setLayoutParams(marginLayoutParams);
        }
    }

    public void incrementProgressBy(int i) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar == null) {
            this.mIncrementBy += i;
        } else {
            progressBar.incrementProgressBy(i);
            onProgressChanged();
        }
    }

    public void incrementSecondaryProgressBy(int i) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar == null) {
            this.mIncrementSecondaryBy += i;
        } else {
            progressBar.incrementSecondaryProgressBy(i);
            onProgressChanged();
        }
    }

    public final void initFormats() {
        this.mProgressNumberFormat = "%1d/%2d";
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        this.mProgressPercentFormat = percentInstance;
        percentInstance.setMaximumFractionDigits(0);
    }

    @Override // miuix.appcompat.app.AlertDialog, android.app.Dialog, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        adjustButtonPanelMarginTopIfNeed();
    }

    @Override // miuix.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    public void onCreate(Bundle bundle) {
        View viewInflate;
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(getContext());
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, R.styleable.AlertDialog, android.R.attr.alertDialogStyle, 0);
        TypedArray typedArrayObtainStyledAttributes2 = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.dialogProgressPercentColor});
        final int color = typedArrayObtainStyledAttributes2.getColor(0, getContext().getResources().getColor(R.color.miuix_appcompat_dialog_default_progress_percent_color));
        typedArrayObtainStyledAttributes2.recycle();
        boolean z = MiuixUIUtils.getFontLevel(getContext()) == 2;
        if (this.mProgressStyle == 1) {
            this.mViewUpdateHandler = new Handler() { // from class: miuix.appcompat.app.ProgressDialog.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    super.handleMessage(message);
                    ProgressDialog.this.mMessageView.setText(ProgressDialog.this.mMessage);
                    if (ProgressDialog.this.mProgressPercentFormat == null || ProgressDialog.this.mProgressPercentView == null) {
                        return;
                    }
                    double max = ((double) ProgressDialog.this.mProgressVal) / ((double) ProgressDialog.this.mProgress.getMax());
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                    String str = ProgressDialog.this.mProgressPercentFormat.format(max);
                    spannableStringBuilder.append((CharSequence) str);
                    spannableStringBuilder.setSpan(new ForegroundColorSpan(color), 0, str.length(), 34);
                    ProgressDialog.this.mProgress.setProgress(ProgressDialog.this.mProgressVal);
                    ProgressDialog.this.mProgressPercentView.setText(spannableStringBuilder);
                }
            };
            int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AlertDialog_horizontalProgressLayout, z ? R.layout.miuix_appcompat_alert_dialog_progress_xl_font : R.layout.miuix_appcompat_alert_dialog_progress);
            this.mLayoutRes = resourceId;
            viewInflate = layoutInflaterFrom.inflate(resourceId, (ViewGroup) null);
            this.mProgressPercentView = (TextView) viewInflate.findViewById(R.id.progress_percent);
        } else {
            int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AlertDialog_progressLayout, R.layout.miuix_appcompat_progress_dialog);
            this.mLayoutRes = resourceId2;
            viewInflate = layoutInflaterFrom.inflate(resourceId2, (ViewGroup) null);
        }
        this.mProgress = (ProgressBar) viewInflate.findViewById(android.R.id.progress);
        this.mMessageView = (TextView) viewInflate.findViewById(R.id.message);
        setView(viewInflate);
        typedArrayObtainStyledAttributes.recycle();
        int i = this.mMax;
        if (i > 0) {
            setMax(i);
        }
        int i2 = this.mProgressVal;
        if (i2 > 0) {
            setProgress(i2);
        }
        int i3 = this.mSecondaryProgressVal;
        if (i3 > 0) {
            setSecondaryProgress(i3);
        }
        int i4 = this.mIncrementBy;
        if (i4 > 0) {
            incrementProgressBy(i4);
        }
        int i5 = this.mIncrementSecondaryBy;
        if (i5 > 0) {
            incrementSecondaryProgressBy(i5);
        }
        Drawable drawable = this.mProgressDrawable;
        if (drawable != null) {
            setProgressDrawable(drawable);
        }
        Drawable drawable2 = this.mIndeterminateDrawable;
        if (drawable2 != null) {
            setIndeterminateDrawable(drawable2);
        }
        CharSequence charSequence = this.mMessage;
        if (charSequence != null) {
            setMessage(charSequence);
        }
        setIndeterminate(this.mIndeterminate);
        onProgressChanged();
        super.onCreate(bundle);
    }

    public final void onProgressChanged() {
        Handler handler;
        if (this.mProgressStyle != 1 || (handler = this.mViewUpdateHandler) == null || handler.hasMessages(0)) {
            return;
        }
        this.mViewUpdateHandler.sendEmptyMessage(0);
    }

    @Override // miuix.appcompat.app.AlertDialog, androidx.activity.ComponentDialog, android.app.Dialog
    public void onStart() {
        super.onStart();
        this.mHasStarted = true;
    }

    @Override // miuix.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    public void onStop() {
        super.onStop();
        this.mHasStarted = false;
    }

    public void setIndeterminate(boolean z) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setIndeterminate(z);
        } else {
            this.mIndeterminate = z;
        }
    }

    public void setIndeterminateDrawable(Drawable drawable) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setIndeterminateDrawable(drawable);
        } else {
            this.mIndeterminateDrawable = drawable;
        }
    }

    public void setMax(int i) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar == null) {
            this.mMax = i;
        } else {
            progressBar.setMax(i);
            onProgressChanged();
        }
    }

    public void setMessage(CharSequence charSequence) {
        if (this.mProgress == null) {
            this.mMessage = charSequence;
            return;
        }
        if (this.mProgressStyle == 1) {
            this.mMessage = charSequence;
        }
        this.mMessageView.setText(charSequence);
    }

    public void setProgress(int i) {
        this.mProgressVal = i;
        if (this.mHasStarted) {
            onProgressChanged();
        }
    }

    public void setProgressDrawable(Drawable drawable) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar != null) {
            progressBar.setProgressDrawable(drawable);
        } else {
            this.mProgressDrawable = drawable;
        }
    }

    public void setProgressStyle(int i) {
        this.mProgressStyle = i;
    }

    public void setSecondaryProgress(int i) {
        ProgressBar progressBar = this.mProgress;
        if (progressBar == null) {
            this.mSecondaryProgressVal = i;
        } else {
            progressBar.setSecondaryProgress(i);
            onProgressChanged();
        }
    }
}
