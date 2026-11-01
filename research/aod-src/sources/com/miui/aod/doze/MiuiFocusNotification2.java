package com.miui.aod.doze;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import com.miui.aod.DozeLifecycleOwner;
import com.miui.aod.notification.FocusNotificationData;
import com.miui.aod.notification.NotificationController;
import com.miui.aod.notification.NotificationUtils;
import com.miui.aod.notification.NotificationViewModel;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class MiuiFocusNotification2 implements DozeMachine.Part, MiuiSystemUiCallback {
    public final Context mContext;
    public NotificationViewModel mNotificationViewModel;
    public final NotificationViewModel.FocusNotification views = new NotificationViewModel.FocusNotification();
    public final NotificationViewModel.FocusNotification nullViews = new NotificationViewModel.FocusNotification();
    public final Set viewsSet = new HashSet();

    /* JADX INFO: renamed from: com.miui.aod.doze.MiuiFocusNotification2$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$aod$doze$DozeMachine$State;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$miui$aod$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.INITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$aod$doze$DozeMachine$State[DozeMachine.State.FINISH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static class FocusNotificationWarp {
        public int bits;
        public Bundle bundle;
        public View view;

        public FocusNotificationWarp(int i, View view, Bundle bundle) {
            this.bits = i;
            this.view = view;
            this.bundle = bundle;
        }
    }

    public interface Ids {
    }

    public static /* synthetic */ int $r8$lambda$juMI8ImpN4M1GtR9YnFntMYd87A(NotificationController.NotificationData notificationData, NotificationController.NotificationData notificationData2) {
        return (int) (notificationData2.mPostTime - notificationData.mPostTime);
    }

    public MiuiFocusNotification2(Context context) {
        this.mContext = context;
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public boolean onRequestFocusView(int i, View view, Bundle bundle) {
        if ((i & AutoBottomSheetKt.ALIGN_SCREEN_END) == 0) {
            return super.onRequestFocusView(i, view, bundle);
        }
        boolean z = bundle.getBoolean("add", false);
        boolean z2 = bundle.getBoolean("remove", false);
        String string = bundle.getString("packageName");
        String string2 = bundle.getString("className");
        String string3 = bundle.getString("key");
        long j = bundle.getLong("postTime", -1L);
        Log.d("MiuiFocusNotification2", "onRequestFocusView: , add = " + z + ", remove = " + z2 + ", packageName = " + string + ", className = " + string2 + ", key = " + string3 + ", postTime = " + j);
        if (string == null || j == -1 || string2 == null || view == null || string3 == null) {
            return false;
        }
        if (z) {
            if (this.viewsSet.contains(string3)) {
                FocusNotificationData focusNotificationData = new FocusNotificationData(string, view);
                focusNotificationData.mClassName = string2;
                focusNotificationData.mPostTime = j;
                focusNotificationData.mKey = string3;
                this.views.remove(focusNotificationData);
            }
            FocusNotificationData focusNotificationData2 = new FocusNotificationData(string, view);
            focusNotificationData2.mClassName = string2;
            focusNotificationData2.mPostTime = j;
            focusNotificationData2.mKey = string3;
            focusNotificationData2.mIconDrawable = NotificationUtils.getAppIconByPackageName(this.mContext, focusNotificationData2.mPackageName, string2);
            this.viewsSet.add(string3);
            this.views.add(focusNotificationData2);
        } else if (z2 && this.viewsSet.contains(string3)) {
            FocusNotificationData focusNotificationData3 = new FocusNotificationData(string, view);
            focusNotificationData3.mClassName = string2;
            focusNotificationData3.mPostTime = j;
            focusNotificationData3.mKey = string3;
            this.viewsSet.remove(string3);
            this.views.remove(focusNotificationData3);
        }
        this.views.sort(new Comparator() { // from class: com.miui.aod.doze.MiuiFocusNotification2$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return MiuiFocusNotification2.$r8$lambda$juMI8ImpN4M1GtR9YnFntMYd87A((NotificationController.NotificationData) obj, (NotificationController.NotificationData) obj2);
            }
        });
        this.mNotificationViewModel.postFocusNotificationValue(this.views);
        return true;
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public void preDestroyForChange() {
        this.mNotificationViewModel.postFocusNotificationValue(this.nullViews);
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public void rebindForChange() {
        this.mNotificationViewModel.postFocusNotificationValue(this.views);
    }

    @Override // com.miui.aod.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        int i = AnonymousClass1.$SwitchMap$com$miui$aod$doze$DozeMachine$State[state2.ordinal()];
        if (i == 1) {
            this.mNotificationViewModel = (NotificationViewModel) new ViewModelProvider(DozeLifecycleOwner.getInstance()).get(NotificationViewModel.class);
        } else {
            if (i != 2) {
                return;
            }
            this.views.clear();
            this.viewsSet.clear();
        }
    }
}
