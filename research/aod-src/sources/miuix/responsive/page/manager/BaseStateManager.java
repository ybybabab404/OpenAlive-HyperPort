package miuix.responsive.page.manager;

import android.content.Context;
import android.content.res.Configuration;
import java.util.Objects;
import miuix.core.util.EnvStateManager;
import miuix.responsive.ResponsiveStateHelper;
import miuix.responsive.map.ResponsiveState;

/* JADX INFO: loaded from: classes2.dex */
public abstract class BaseStateManager {
    public static boolean sEnableResponsive = true;
    public final ResponsiveState mOldState = new ResponsiveState();
    public ResponsiveState mState;

    public static boolean isSupportResponsive() {
        return sEnableResponsive;
    }

    public ResponsiveState computeResponsiveState() {
        return ResponsiveStateHelper.computeResponsiveState(getContext(), EnvStateManager.getWindowInfo(getContext()));
    }

    public ResponsiveState computeResponsiveStateFromConfig(Configuration configuration) {
        return ResponsiveStateHelper.computeResponsiveStateOnConfigChanged(getContext(), EnvStateManager.getWindowInfo(getContext()), configuration);
    }

    public abstract Context getContext();

    public boolean isStateEquals(ResponsiveState responsiveState, ResponsiveState responsiveState2) {
        return Objects.equals(responsiveState, responsiveState2);
    }

    public void onAfterConfigurationChanged(Configuration configuration) {
    }

    public void onBeforeConfigurationChanged(Configuration configuration) {
    }
}
