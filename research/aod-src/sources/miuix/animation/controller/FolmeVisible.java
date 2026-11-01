package miuix.animation.controller;

import com.miui.aod.components.view.BaseStyleSelectView;
import java.util.Collection;
import miuix.animation.FolmeEase;
import miuix.animation.IAnimTarget;
import miuix.animation.IVisibleStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.CommonUtils;

/* JADX INFO: loaded from: classes2.dex */
public class FolmeVisible extends FolmeBase implements IVisibleStyle {
    private static final String ALIAS_VISIBLE_HIDE = "visibleHide";
    private static final String ALIAS_VISIBLE_SHOW = "visibleShow";
    private final AnimConfig mDefConfig;
    private boolean mHasMove;
    private boolean mHasScale;
    private boolean mSetBound;

    public FolmeVisible(IAnimTarget... iAnimTargetArr) {
        super(iAnimTargetArr);
        this.mDefConfig = new AnimConfig().addListeners(new TransitionListener() { // from class: miuix.animation.controller.FolmeVisible.1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj, Collection<UpdateInfo> collection) {
                if (obj.equals(IVisibleStyle.VisibleType.SHOW) && FolmeVisible.this.mSetBound) {
                    AnimState.alignState(FolmeVisible.this.mState.getState(IVisibleStyle.VisibleType.HIDE), collection);
                }
            }
        });
        this.mState.addState(new AnimState(IVisibleStyle.VisibleType.SHOW, ALIAS_VISIBLE_SHOW));
        this.mState.addState(new AnimState(IVisibleStyle.VisibleType.HIDE, ALIAS_VISIBLE_HIDE));
        useAutoAlpha(true);
    }

    private AnimConfig[] getConfig(IVisibleStyle.VisibleType visibleType, AnimConfig... animConfigArr) {
        boolean z = this.mHasScale;
        if (!z && !this.mHasMove) {
            this.mDefConfig.setEase(visibleType == IVisibleStyle.VisibleType.SHOW ? FolmeEase.sinInOut(300L) : FolmeEase.spring(1.0f, 0.15f));
        } else if (z && !this.mHasMove) {
            this.mDefConfig.setEase(visibleType == IVisibleStyle.VisibleType.SHOW ? FolmeEase.spring(0.6f, 0.35f) : FolmeEase.spring(0.75f, 0.2f));
        } else if (z) {
            this.mDefConfig.setEase(visibleType == IVisibleStyle.VisibleType.SHOW ? FolmeEase.spring(0.65f, 0.35f) : FolmeEase.spring(0.75f, 0.25f));
        } else {
            this.mDefConfig.setEase(visibleType == IVisibleStyle.VisibleType.SHOW ? FolmeEase.spring(0.75f, 0.35f) : FolmeEase.spring(0.75f, 0.25f));
        }
        return (AnimConfig[]) CommonUtils.mergeArray(animConfigArr, this.mDefConfig);
    }

    private IVisibleStyle.VisibleType getType(IVisibleStyle.VisibleType... visibleTypeArr) {
        return visibleTypeArr.length > 0 ? visibleTypeArr[0] : IVisibleStyle.VisibleType.HIDE;
    }

    @Override // miuix.animation.controller.FolmeBase, miuix.animation.IStateContainer
    public void clean() {
        super.clean();
        this.mHasScale = false;
        this.mHasMove = false;
    }

    @Override // miuix.animation.IVisibleStyle
    public void hide(AnimConfig... animConfigArr) {
        IFolmeStateStyle iFolmeStateStyle = this.mState;
        IVisibleStyle.VisibleType visibleType = IVisibleStyle.VisibleType.HIDE;
        iFolmeStateStyle.to(visibleType, getConfig(visibleType, animConfigArr));
    }

    @Override // miuix.animation.IVisibleStyle
    public IVisibleStyle setAlpha(float f, IVisibleStyle.VisibleType... visibleTypeArr) {
        this.mState.getState(getType(visibleTypeArr)).add(ViewProperty.AUTO_ALPHA, f);
        return this;
    }

    @Override // miuix.animation.IVisibleStyle
    public IVisibleStyle setBound(int i, int i2, int i3, int i4) {
        this.mSetBound = true;
        this.mState.getState(IVisibleStyle.VisibleType.SHOW).add(ViewProperty.X, i).add(ViewProperty.Y, i2).add(ViewProperty.WIDTH, i3).add(ViewProperty.HEIGHT, i4);
        return this;
    }

    @Override // miuix.animation.IVisibleStyle
    public IVisibleStyle setFlags(long j) {
        this.mState.setFlags(j);
        return this;
    }

    @Override // miuix.animation.IVisibleStyle
    public IVisibleStyle setHide() {
        this.mState.setTo(IVisibleStyle.VisibleType.HIDE);
        return this;
    }

    @Override // miuix.animation.IVisibleStyle
    public IVisibleStyle setMove(int i, int i2) {
        return setMove(i, i2, IVisibleStyle.VisibleType.HIDE);
    }

    @Override // miuix.animation.IVisibleStyle
    public IVisibleStyle setMove(int i, int i2, IVisibleStyle.VisibleType... visibleTypeArr) {
        boolean z = Math.abs(i) > 0 || Math.abs(i2) > 0;
        this.mHasMove = z;
        if (z) {
            this.mState.getState(getType(visibleTypeArr)).add(ViewProperty.X, i, 1).add(ViewProperty.Y, i2, 1);
        }
        return this;
    }

    @Override // miuix.animation.IVisibleStyle
    public IVisibleStyle setScale(float f, IVisibleStyle.VisibleType... visibleTypeArr) {
        this.mHasScale = true;
        double d = f;
        this.mState.getState(getType(visibleTypeArr)).add(ViewProperty.SCALE_Y, d).add(ViewProperty.SCALE_X, d);
        return this;
    }

    @Override // miuix.animation.IVisibleStyle
    public IVisibleStyle setShow() {
        this.mState.setTo(IVisibleStyle.VisibleType.SHOW);
        return this;
    }

    @Override // miuix.animation.IVisibleStyle
    public IVisibleStyle setShowDelay(long j) {
        this.mState.getState(IVisibleStyle.VisibleType.SHOW).getConfig().delay = j;
        return this;
    }

    @Override // miuix.animation.IVisibleStyle
    public void show(AnimConfig... animConfigArr) {
        IFolmeStateStyle iFolmeStateStyle = this.mState;
        IVisibleStyle.VisibleType visibleType = IVisibleStyle.VisibleType.SHOW;
        iFolmeStateStyle.to(visibleType, getConfig(visibleType, animConfigArr));
    }

    @Override // miuix.animation.IVisibleStyle
    public IVisibleStyle useAutoAlpha(boolean z) {
        ViewProperty viewProperty = ViewProperty.AUTO_ALPHA;
        ViewProperty viewProperty2 = ViewProperty.ALPHA;
        if (z) {
            this.mState.getState(IVisibleStyle.VisibleType.SHOW).remove(viewProperty2).add(viewProperty, 1.0d);
            this.mState.getState(IVisibleStyle.VisibleType.HIDE).remove(viewProperty2).add(viewProperty, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
            return this;
        }
        this.mState.getState(IVisibleStyle.VisibleType.SHOW).remove(viewProperty).add(viewProperty2, 1.0d);
        this.mState.getState(IVisibleStyle.VisibleType.HIDE).remove(viewProperty).add(viewProperty2, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
        return this;
    }
}
