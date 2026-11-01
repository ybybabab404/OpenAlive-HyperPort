package com.google.android.material.datepicker;

import android.app.DatePickerDialog;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.RestrictTo;
import com.google.android.material.R;
import com.google.android.material.dialog.InsetDialogOnTouchListener;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public class MaterialStyledDatePickerDialog extends DatePickerDialog {
    public static final int DEF_STYLE_RES = R.style.MaterialAlertDialog_MaterialComponents_Picker_Date_Spinner;
    public final Drawable background;
    public final Rect backgroundInsets;

    @Override // android.app.AlertDialog, android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setBackgroundDrawable(this.background);
        getWindow().getDecorView().setOnTouchListener(new InsetDialogOnTouchListener(this, this.backgroundInsets));
    }
}
