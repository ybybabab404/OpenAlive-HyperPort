package com.miui.clock.tiny.pets;

import android.content.Context;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.miui.clock.tiny.R;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class PetsBackgroundView extends FrameLayout {
    public boolean isChinese;
    public Context mContext;
    public Locale mCurrentLocale;
    public int mCurrentRotation;
    public ImageView mForegroundView;
    public int mType;
    public int mode;

    public PetsBackgroundView(@NonNull Context context) {
        this(context, null);
    }

    public PetsBackgroundView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mode = 0;
        this.mCurrentRotation = 0;
        this.mContext = context;
        Locale locale = Locale.getDefault();
        this.mCurrentLocale = locale;
        this.isChinese = Locale.SIMPLIFIED_CHINESE.equals(locale) || isTraditionalChinese();
    }

    public final boolean isTraditionalChinese() {
        return TextUtils.equals("zh_TW", Locale.getDefault().toString()) || TextUtils.equals("zh_HK", Locale.getDefault().toString());
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        boolean z;
        super.onConfigurationChanged(configuration);
        if (this.mode == 2) {
            return;
        }
        Locale locale = Locale.getDefault();
        boolean z2 = true;
        if (this.mCurrentLocale.equals(locale)) {
            z = false;
        } else {
            this.mCurrentLocale = locale;
            z = true;
        }
        if (!Locale.SIMPLIFIED_CHINESE.equals(this.mCurrentLocale) && !isTraditionalChinese()) {
            z2 = false;
        }
        this.isChinese = z2;
        if (z) {
            setForegroundResource();
        }
    }

    public final void setForegroundResource() {
        switch (this.mType) {
            case 1:
                if (this.mCurrentRotation != 2) {
                    if (!this.isChinese) {
                        this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style1_en_0);
                    } else {
                        this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style1_zh_0);
                    }
                } else if (!this.isChinese) {
                    this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style1_en_180);
                } else {
                    this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style1_zh_180);
                }
                break;
            case 2:
                if (this.mCurrentRotation != 2) {
                    if (!this.isChinese) {
                        this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style2_en_0);
                    } else {
                        this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style2_zh_0);
                    }
                } else if (!this.isChinese) {
                    this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style2_en_180);
                } else {
                    this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style2_zh_180);
                }
                break;
            case 3:
                if (this.mCurrentRotation != 2) {
                    if (!this.isChinese) {
                        this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style3_en_0);
                    } else {
                        this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style3_zh_0);
                    }
                } else if (!this.isChinese) {
                    this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style3_en_180);
                } else {
                    this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style3_zh_180);
                }
                break;
            case 4:
                if (this.mCurrentRotation != 2) {
                    if (!this.isChinese) {
                        this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style4_en_0);
                    } else {
                        this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style4_zh_0);
                    }
                } else if (!this.isChinese) {
                    this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style4_en_180);
                } else {
                    this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style4_zh_180);
                }
                break;
            case 5:
                if (this.mCurrentRotation != 2) {
                    if (!this.isChinese) {
                        this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style5_en_0);
                    } else {
                        this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style5_zh_0);
                    }
                } else if (!this.isChinese) {
                    this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style5_en_180);
                } else {
                    this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style5_zh_180);
                }
                break;
            case 6:
                if (this.mCurrentRotation != 2) {
                    if (!this.isChinese) {
                        this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style6_en_0);
                    } else {
                        this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style6_zh_0);
                    }
                } else if (!this.isChinese) {
                    this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style6_en_180);
                } else {
                    this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style6_zh_180);
                }
                break;
            default:
                if (this.mCurrentRotation != 2) {
                    if (!this.isChinese) {
                        this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style1_en_0);
                    } else {
                        this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style1_zh_0);
                    }
                } else if (!this.isChinese) {
                    this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style1_en_180);
                } else {
                    this.mForegroundView.setImageResource(R.drawable.tiny_pets_template_foreground_style1_zh_180);
                }
                break;
        }
    }

    public void setMode(int i) {
        this.mode = i;
    }
}
