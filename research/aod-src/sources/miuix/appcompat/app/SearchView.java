package miuix.appcompat.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import miuix.androidbasewidget.widget.ClearableEditText;
import miuix.appcompat.R;

/* JADX INFO: loaded from: classes2.dex */
public class SearchView extends SearchModeStubView {
    public AppCompatImageView mBackIconViewEnd;
    public AppCompatImageView mBackIconViewStart;
    public ClearableEditText mEditText;
    public TextWatcher mEditTextWatcher;
    public SearchPopupManager mPopupManager;
    public Button mSearchButton;
    public ISearchActionCallback mSearchCallback;
    public AppCompatImageView mSearchIconView;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SearchView(Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.SearchView);
    }

    public SearchView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init(context, attributeSet, i, i2);
    }

    public void clearPopupManagerInternal() {
        this.mPopupManager = null;
    }

    public AppCompatImageView getBackIconView() {
        return this.mBackIconViewStart;
    }

    public AppCompatImageView getBackIconViewEnd() {
        return this.mBackIconViewEnd;
    }

    public ClearableEditText getEditText() {
        return this.mEditText;
    }

    @Override // miuix.appcompat.app.SearchModeStubView
    public ViewGroup getMaterialTargetParent() {
        return this;
    }

    public Button getSearchButton() {
        return this.mSearchButton;
    }

    public AppCompatImageView getSearchIconView() {
        return this.mSearchIconView;
    }

    public final void init(Context context, AttributeSet attributeSet, int i, int i2) {
        LinearLayout.inflate(context, R.layout.miuix_appcompat_search_view, this);
        initChildren();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SearchView, i, i2);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SearchView_showSearchIcon, true);
        boolean z2 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SearchView_showBackIconStart, false);
        boolean z3 = typedArrayObtainStyledAttributes.getBoolean(R.styleable.SearchView_showBackIconEnd, false);
        String string = typedArrayObtainStyledAttributes.getString(R.styleable.SearchView_android_hint);
        String string2 = typedArrayObtainStyledAttributes.getString(R.styleable.SearchView_android_text);
        String string3 = typedArrayObtainStyledAttributes.getString(R.styleable.SearchView_android_searchButtonText);
        typedArrayObtainStyledAttributes.recycle();
        setSearchIconVisible(z);
        setBackIconStartVisible(z2);
        setBackIconEndVisible(z3);
        setHint(string);
        setText(string2);
        setSearchButtonText(string3);
    }

    public final void initChildren() {
        this.mSearchIconView = (AppCompatImageView) findViewById(R.id.icon);
        this.mBackIconViewStart = (AppCompatImageView) findViewById(R.id.back_icon_start);
        this.mBackIconViewEnd = (AppCompatImageView) findViewById(R.id.back_icon_end);
        this.mEditText = (ClearableEditText) findViewById(android.R.id.input);
        this.mSearchButton = (Button) findViewById(R.id.search_button);
        this.mBackIconViewStart.setOnClickListener(new View.OnClickListener() { // from class: miuix.appcompat.app.SearchView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SearchView.this.mSearchCallback != null) {
                    SearchView.this.mSearchCallback.onBackIconStartClick(view);
                }
            }
        });
        this.mBackIconViewEnd.setOnClickListener(new View.OnClickListener() { // from class: miuix.appcompat.app.SearchView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SearchView.this.mSearchCallback != null) {
                    SearchView.this.mSearchCallback.onBackIconEndClick(view);
                }
            }
        });
        this.mSearchButton.setOnClickListener(new View.OnClickListener() { // from class: miuix.appcompat.app.SearchView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SearchView.this.mSearchCallback != null) {
                    SearchView.this.mSearchCallback.onSearchButtonClick(view);
                }
            }
        });
        this.mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: miuix.appcompat.app.SearchView.4
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view, boolean z) {
                if (SearchView.this.mSearchCallback != null) {
                    SearchView.this.mSearchCallback.onFocusChange(view, z);
                }
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        TextWatcher textWatcher = new TextWatcher() { // from class: miuix.appcompat.app.SearchView.5
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (SearchView.this.mSearchCallback != null) {
                    SearchView.this.mSearchCallback.afterSearchTextChanged(editable);
                }
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (SearchView.this.mSearchCallback != null) {
                    SearchView.this.mSearchCallback.beforeSearchTextChanged(charSequence, i, i2, i3);
                }
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (SearchView.this.mSearchCallback != null) {
                    SearchView.this.mSearchCallback.onSearchTextChanged(charSequence, i, i2, i3);
                }
            }
        };
        this.mEditTextWatcher = textWatcher;
        this.mEditText.addTextChangedListener(textWatcher);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mEditText.removeTextChangedListener(this.mEditTextWatcher);
        SearchPopupManager searchPopupManager = this.mPopupManager;
        if (searchPopupManager != null) {
            searchPopupManager.destroy();
        }
    }

    public void setBackIconEndVisible(boolean z) {
        getBackIconViewEnd().setVisibility(z ? 0 : 8);
    }

    public void setBackIconStartVisible(boolean z) {
        getBackIconView().setVisibility(z ? 0 : 8);
    }

    public void setHint(CharSequence charSequence) {
        getEditText().setHint(charSequence);
    }

    public void setOnSearchActionCallback(ISearchActionCallback iSearchActionCallback) {
        this.mSearchCallback = iSearchActionCallback;
    }

    public void setPopupManager(SearchPopupManager searchPopupManager) {
        SearchPopupManager searchPopupManager2 = this.mPopupManager;
        if (searchPopupManager2 != searchPopupManager) {
            this.mPopupManager = searchPopupManager;
            if (searchPopupManager2 != null) {
                searchPopupManager2.destroy();
            }
        }
    }

    public void setSearchButtonText(@Nullable CharSequence charSequence) {
        this.mSearchButton.setVisibility(TextUtils.isEmpty(charSequence) ? 8 : 0);
        this.mSearchButton.setText(charSequence);
    }

    public void setSearchIconVisible(boolean z) {
        getSearchIconView().setVisibility(z ? 0 : 8);
    }

    public void setText(CharSequence charSequence) {
        getEditText().setText(charSequence);
    }
}
