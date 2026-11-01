package com.miui.maml.data;

import android.content.ContentResolver;
import android.provider.Settings;
import com.miui.aod.components.BaseStyleSelectPresenter;
import com.miui.maml.ScreenElementRoot;
import java.util.Iterator;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes2.dex */
public class SettingsBinder extends VariableBinder {
    public static final String TAG_NAME = "SettingsBinder";
    private boolean mConst;
    private ContentResolver mContentResolver;

    /* JADX INFO: renamed from: com.miui.maml.data.SettingsBinder$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$data$SettingsBinder$Category;

        static {
            int[] iArr = new int[Category.values().length];
            $SwitchMap$com$miui$maml$data$SettingsBinder$Category = iArr;
            try {
                iArr[Category.System.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$maml$data$SettingsBinder$Category[Category.Secure.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$maml$data$SettingsBinder$Category[Category.Global.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public enum Category {
        Secure,
        System,
        Global
    }

    public class Variable extends VariableBinder.Variable {
        public Category mCategory;
        public String mKey;

        public Variable(Element element, Variables variables) {
            super(element, variables);
            String attribute = element.getAttribute(BaseStyleSelectPresenter.KEY_CATE);
            attribute.getClass();
            switch (attribute) {
                case "global":
                    this.mCategory = Category.Global;
                    break;
                case "secure":
                    this.mCategory = Category.Secure;
                    break;
                case "system":
                    this.mCategory = Category.System;
                    break;
                default:
                    this.mCategory = Category.Secure;
                    break;
            }
            this.mKey = element.getAttribute("key");
        }

        public void query() {
            int i = AnonymousClass1.$SwitchMap$com$miui$maml$data$SettingsBinder$Category[this.mCategory.ordinal()];
            if (i == 1) {
                int i2 = this.mType;
                if (i2 == 2) {
                    String string = Settings.System.getString(SettingsBinder.this.mContentResolver, this.mKey);
                    if (string == null) {
                        string = this.mDefStringValue;
                    }
                    set(string);
                    return;
                }
                if (i2 == 3) {
                    set(Settings.System.getInt(SettingsBinder.this.mContentResolver, this.mKey, (int) this.mDefNumberValue));
                    return;
                }
                if (i2 == 4) {
                    set(Settings.System.getLong(SettingsBinder.this.mContentResolver, this.mKey, (long) this.mDefNumberValue));
                    return;
                } else {
                    if (i2 == 5 || i2 == 6) {
                        set(Settings.System.getFloat(SettingsBinder.this.mContentResolver, this.mKey, (float) this.mDefNumberValue));
                        return;
                    }
                    return;
                }
            }
            if (i == 2) {
                int i3 = this.mType;
                if (i3 == 2) {
                    String string2 = Settings.Secure.getString(SettingsBinder.this.mContentResolver, this.mKey);
                    if (string2 == null) {
                        string2 = this.mDefStringValue;
                    }
                    set(string2);
                    return;
                }
                if (i3 == 3) {
                    set(Settings.Secure.getInt(SettingsBinder.this.mContentResolver, this.mKey, (int) this.mDefNumberValue));
                    return;
                }
                if (i3 == 4) {
                    set(Settings.Secure.getLong(SettingsBinder.this.mContentResolver, this.mKey, (long) this.mDefNumberValue));
                    return;
                } else {
                    if (i3 == 5 || i3 == 6) {
                        set(Settings.Secure.getFloat(SettingsBinder.this.mContentResolver, this.mKey, (float) this.mDefNumberValue));
                        return;
                    }
                    return;
                }
            }
            if (i != 3) {
                return;
            }
            int i4 = this.mType;
            if (i4 == 2) {
                String string3 = Settings.Global.getString(SettingsBinder.this.mContentResolver, this.mKey);
                if (string3 == null) {
                    string3 = this.mDefStringValue;
                }
                set(string3);
                return;
            }
            if (i4 == 3) {
                set(Settings.Global.getInt(SettingsBinder.this.mContentResolver, this.mKey, (int) this.mDefNumberValue));
                return;
            }
            if (i4 == 4) {
                set(Settings.Global.getLong(SettingsBinder.this.mContentResolver, this.mKey, (long) this.mDefNumberValue));
            } else if (i4 == 5 || i4 == 6) {
                set(Settings.Global.getFloat(SettingsBinder.this.mContentResolver, this.mKey, (float) this.mDefNumberValue));
            }
        }
    }

    public SettingsBinder(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mContentResolver = this.mRoot.getContext().mContext.getContentResolver();
        if (element != null) {
            loadVariables(element);
            this.mConst = !"false".equalsIgnoreCase(element.getAttribute("const"));
        }
    }

    @Override // com.miui.maml.data.VariableBinder
    public Variable onLoadVariable(Element element) {
        return new Variable(element, getContext().mVariables);
    }

    @Override // com.miui.maml.data.VariableBinder
    public void refresh() {
        super.refresh();
        startQuery();
    }

    @Override // com.miui.maml.data.VariableBinder
    public void resume() {
        super.resume();
        if (this.mConst) {
            return;
        }
        startQuery();
    }

    @Override // com.miui.maml.data.VariableBinder
    public void startQuery() {
        Iterator<VariableBinder.Variable> it = this.mVariables.iterator();
        while (it.hasNext()) {
            ((Variable) it.next()).query();
        }
        onUpdateComplete();
    }
}
