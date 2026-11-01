package androidx.core.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.LocusId;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.RemoteViews;
import androidx.annotation.ColorInt;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.drawable.IconCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class NotificationCompat {

    public static class Action {
        public PendingIntent actionIntent;
        public int icon;
        public boolean mAllowGeneratedReplies;
        public boolean mAuthenticationRequired;
        public final RemoteInput[] mDataOnlyRemoteInputs;
        public final Bundle mExtras;
        public IconCompat mIcon;
        public final boolean mIsContextual;
        public final RemoteInput[] mRemoteInputs;
        public final int mSemanticAction;
        public boolean mShowsUserInterface;
        public CharSequence title;

        public static final class Builder {

            @RequiresApi
            public static class Api20Impl {
                @DoNotInline
                public static Bundle getExtras(Notification.Action action) {
                    return action.getExtras();
                }

                @DoNotInline
                public static android.app.RemoteInput[] getRemoteInputs(Notification.Action action) {
                    return action.getRemoteInputs();
                }
            }

            @RequiresApi
            public static class Api23Impl {
                @DoNotInline
                public static Icon getIcon(Notification.Action action) {
                    return action.getIcon();
                }
            }

            @RequiresApi
            public static class Api24Impl {
                @DoNotInline
                public static boolean getAllowGeneratedReplies(Notification.Action action) {
                    return action.getAllowGeneratedReplies();
                }
            }

            @RequiresApi
            public static class Api28Impl {
                @DoNotInline
                public static int getSemanticAction(Notification.Action action) {
                    return action.getSemanticAction();
                }
            }

            @RequiresApi
            public static class Api29Impl {
                @DoNotInline
                public static boolean isContextual(Notification.Action action) {
                    return action.isContextual();
                }
            }

            @RequiresApi
            public static class Api31Impl {
                @DoNotInline
                public static boolean isAuthenticationRequired(Notification.Action action) {
                    return action.isAuthenticationRequired();
                }
            }
        }

        public interface Extender {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface SemanticAction {
        }

        public static final class WearableExtender implements Extender {
            public CharSequence mCancelLabel;
            public CharSequence mConfirmLabel;
            public int mFlags = 1;
            public CharSequence mInProgressLabel;

            /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
            public WearableExtender m40clone() {
                WearableExtender wearableExtender = new WearableExtender();
                wearableExtender.mFlags = this.mFlags;
                wearableExtender.mInProgressLabel = this.mInProgressLabel;
                wearableExtender.mConfirmLabel = this.mConfirmLabel;
                wearableExtender.mCancelLabel = this.mCancelLabel;
                return wearableExtender;
            }
        }

        public Action(int i, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] remoteInputArr, RemoteInput[] remoteInputArr2, boolean z, int i2, boolean z2, boolean z3, boolean z4) {
            this(i != 0 ? IconCompat.createWithResource(null, "", i) : null, charSequence, pendingIntent, bundle, remoteInputArr, remoteInputArr2, z, i2, z2, z3, z4);
        }

        public Action(IconCompat iconCompat, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] remoteInputArr, RemoteInput[] remoteInputArr2, boolean z, int i, boolean z2, boolean z3, boolean z4) {
            this.mShowsUserInterface = true;
            this.mIcon = iconCompat;
            if (iconCompat != null && iconCompat.getType() == 2) {
                this.icon = iconCompat.getResId();
            }
            this.title = Builder.limitCharSequenceLength(charSequence);
            this.actionIntent = pendingIntent;
            this.mExtras = bundle == null ? new Bundle() : bundle;
            this.mRemoteInputs = remoteInputArr;
            this.mDataOnlyRemoteInputs = remoteInputArr2;
            this.mAllowGeneratedReplies = z;
            this.mSemanticAction = i;
            this.mShowsUserInterface = z2;
            this.mIsContextual = z3;
            this.mAuthenticationRequired = z4;
        }
    }

    @RequiresApi
    public static class Api20Impl {
        @DoNotInline
        public static boolean getAllowFreeFormInput(android.app.RemoteInput remoteInput) {
            return remoteInput.getAllowFreeFormInput();
        }

        @DoNotInline
        public static CharSequence[] getChoices(android.app.RemoteInput remoteInput) {
            return remoteInput.getChoices();
        }

        @DoNotInline
        public static Bundle getExtras(Notification.Action action) {
            return action.getExtras();
        }

        @DoNotInline
        public static Bundle getExtras(android.app.RemoteInput remoteInput) {
            return remoteInput.getExtras();
        }

        @DoNotInline
        public static String getGroup(Notification notification) {
            return notification.getGroup();
        }

        @DoNotInline
        public static CharSequence getLabel(android.app.RemoteInput remoteInput) {
            return remoteInput.getLabel();
        }

        @DoNotInline
        public static android.app.RemoteInput[] getRemoteInputs(Notification.Action action) {
            return action.getRemoteInputs();
        }

        @DoNotInline
        public static String getResultKey(android.app.RemoteInput remoteInput) {
            return remoteInput.getResultKey();
        }

        @DoNotInline
        public static String getSortKey(Notification notification) {
            return notification.getSortKey();
        }
    }

    @RequiresApi
    public static class Api23Impl {
        @DoNotInline
        public static Icon getIcon(Notification.Action action) {
            return action.getIcon();
        }
    }

    @RequiresApi
    public static class Api24Impl {
        @DoNotInline
        public static boolean getAllowGeneratedReplies(Notification.Action action) {
            return action.getAllowGeneratedReplies();
        }
    }

    @RequiresApi
    public static class Api26Impl {
        @DoNotInline
        public static int getBadgeIconType(Notification notification) {
            return notification.getBadgeIconType();
        }

        @DoNotInline
        public static String getChannelId(Notification notification) {
            return notification.getChannelId();
        }

        @DoNotInline
        public static int getGroupAlertBehavior(Notification notification) {
            return notification.getGroupAlertBehavior();
        }

        @DoNotInline
        public static CharSequence getSettingsText(Notification notification) {
            return notification.getSettingsText();
        }

        @DoNotInline
        public static String getShortcutId(Notification notification) {
            return notification.getShortcutId();
        }

        @DoNotInline
        public static long getTimeoutAfter(Notification notification) {
            return notification.getTimeoutAfter();
        }
    }

    @RequiresApi
    public static class Api28Impl {
        @DoNotInline
        public static int getSemanticAction(Notification.Action action) {
            return action.getSemanticAction();
        }
    }

    @RequiresApi
    public static class Api29Impl {
        @DoNotInline
        public static boolean getAllowSystemGeneratedContextualActions(Notification notification) {
            return notification.getAllowSystemGeneratedContextualActions();
        }

        @DoNotInline
        public static Notification.BubbleMetadata getBubbleMetadata(Notification notification) {
            return notification.getBubbleMetadata();
        }

        @DoNotInline
        public static int getEditChoicesBeforeSending(android.app.RemoteInput remoteInput) {
            return remoteInput.getEditChoicesBeforeSending();
        }

        @DoNotInline
        public static LocusId getLocusId(Notification notification) {
            return notification.getLocusId();
        }

        @DoNotInline
        public static boolean isContextual(Notification.Action action) {
            return action.isContextual();
        }
    }

    @RequiresApi
    public static class Api31Impl {
        @DoNotInline
        public static boolean isAuthenticationRequired(Notification.Action action) {
            return action.isAuthenticationRequired();
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface BadgeIconType {
    }

    public static class BigPictureStyle extends Style {

        @RequiresApi
        public static class Api23Impl {
        }

        @RequiresApi
        public static class Api31Impl {
        }
    }

    public static class BigTextStyle extends Style {
    }

    public static final class BubbleMetadata {

        @RequiresApi
        public static class Api29Impl {
        }

        @RequiresApi
        public static class Api30Impl {
        }

        public static final class Builder {
        }
    }

    public static class Builder {

        @RequiresApi
        public static class Api21Impl {
            @DoNotInline
            public static AudioAttributes build(AudioAttributes.Builder builder) {
                return builder.build();
            }

            @DoNotInline
            public static AudioAttributes.Builder createBuilder() {
                return new AudioAttributes.Builder();
            }

            @DoNotInline
            public static AudioAttributes.Builder setContentType(AudioAttributes.Builder builder, int i) {
                return builder.setContentType(i);
            }

            @DoNotInline
            public static AudioAttributes.Builder setLegacyStreamType(AudioAttributes.Builder builder, int i) {
                return builder.setLegacyStreamType(i);
            }

            @DoNotInline
            public static AudioAttributes.Builder setUsage(AudioAttributes.Builder builder, int i) {
                return builder.setUsage(i);
            }
        }

        @RequiresApi
        public static class Api23Impl {
            @DoNotInline
            public static Icon getLargeIcon(Notification notification) {
                return notification.getLargeIcon();
            }

            @DoNotInline
            public static Icon getSmallIcon(Notification notification) {
                return notification.getSmallIcon();
            }
        }

        @RequiresApi
        public static class Api24Impl {
            @DoNotInline
            public static RemoteViews createBigContentView(Notification.Builder builder) {
                return builder.createHeadsUpContentView();
            }

            @DoNotInline
            public static RemoteViews createContentView(Notification.Builder builder) {
                return builder.createContentView();
            }

            @DoNotInline
            public static RemoteViews createHeadsUpContentView(Notification.Builder builder) {
                return builder.createHeadsUpContentView();
            }

            @DoNotInline
            public static Notification.Builder recoverBuilder(Context context, Notification notification) {
                return Notification.Builder.recoverBuilder(context, notification);
            }
        }

        public static CharSequence limitCharSequenceLength(CharSequence charSequence) {
            return (charSequence != null && charSequence.length() > 5120) ? charSequence.subSequence(0, 5120) : charSequence;
        }
    }

    public static class CallStyle extends Style {

        @RequiresApi
        public static class Api20Impl {
            @DoNotInline
            public static Notification.Action.Builder addExtras(Notification.Action.Builder builder, Bundle bundle) {
                return builder.addExtras(bundle);
            }

            @DoNotInline
            public static Notification.Action.Builder addRemoteInput(Notification.Action.Builder builder, android.app.RemoteInput remoteInput) {
                return builder.addRemoteInput(remoteInput);
            }

            @DoNotInline
            public static Notification.Action build(Notification.Action.Builder builder) {
                return builder.build();
            }

            @DoNotInline
            public static Notification.Action.Builder createActionBuilder(int i, CharSequence charSequence, PendingIntent pendingIntent) {
                return new Notification.Action.Builder(i, charSequence, pendingIntent);
            }
        }

        @RequiresApi
        public static class Api21Impl {
            @DoNotInline
            public static Notification.Builder addPerson(Notification.Builder builder, String str) {
                return builder.addPerson(str);
            }

            @DoNotInline
            public static Notification.Builder setCategory(Notification.Builder builder, String str) {
                return builder.setCategory(str);
            }
        }

        @RequiresApi
        public static class Api23Impl {
            @DoNotInline
            public static Parcelable castToParcelable(Icon icon) {
                return icon;
            }

            @DoNotInline
            public static Notification.Action.Builder createActionBuilder(Icon icon, CharSequence charSequence, PendingIntent pendingIntent) {
                return new Notification.Action.Builder(icon, charSequence, pendingIntent);
            }

            @DoNotInline
            public static void setLargeIcon(Notification.Builder builder, Icon icon) {
                builder.setLargeIcon(icon);
            }
        }

        @RequiresApi
        public static class Api24Impl {
            @DoNotInline
            public static Notification.Action.Builder setAllowGeneratedReplies(Notification.Action.Builder builder, boolean z) {
                return builder.setAllowGeneratedReplies(z);
            }
        }

        @RequiresApi
        public static class Api28Impl {
            @DoNotInline
            public static Notification.Builder addPerson(Notification.Builder builder, android.app.Person person) {
                return builder.addPerson(person);
            }

            @DoNotInline
            public static Parcelable castToParcelable(android.app.Person person) {
                return person;
            }
        }

        @RequiresApi
        public static class Api31Impl {
            @DoNotInline
            public static Notification.CallStyle forIncomingCall(@NonNull android.app.Person person, @NonNull PendingIntent pendingIntent, @NonNull PendingIntent pendingIntent2) {
                return Notification.CallStyle.forIncomingCall(person, pendingIntent, pendingIntent2);
            }

            @DoNotInline
            public static Notification.CallStyle forOngoingCall(@NonNull android.app.Person person, @NonNull PendingIntent pendingIntent) {
                return Notification.CallStyle.forOngoingCall(person, pendingIntent);
            }

            @DoNotInline
            public static Notification.CallStyle forScreeningCall(@NonNull android.app.Person person, @NonNull PendingIntent pendingIntent, @NonNull PendingIntent pendingIntent2) {
                return Notification.CallStyle.forScreeningCall(person, pendingIntent, pendingIntent2);
            }

            @DoNotInline
            public static Notification.CallStyle setAnswerButtonColorHint(Notification.CallStyle callStyle, @ColorInt int i) {
                return callStyle.setAnswerButtonColorHint(i);
            }

            @DoNotInline
            public static Notification.Action.Builder setAuthenticationRequired(Notification.Action.Builder builder, boolean z) {
                return builder.setAuthenticationRequired(z);
            }

            @DoNotInline
            public static Notification.CallStyle setDeclineButtonColorHint(Notification.CallStyle callStyle, @ColorInt int i) {
                return callStyle.setDeclineButtonColorHint(i);
            }

            @DoNotInline
            public static Notification.CallStyle setIsVideo(Notification.CallStyle callStyle, boolean z) {
                return callStyle.setIsVideo(z);
            }

            @DoNotInline
            public static Notification.CallStyle setVerificationIcon(Notification.CallStyle callStyle, @Nullable Icon icon) {
                return callStyle.setVerificationIcon(icon);
            }

            @DoNotInline
            public static Notification.CallStyle setVerificationText(Notification.CallStyle callStyle, @Nullable CharSequence charSequence) {
                return callStyle.setVerificationText(charSequence);
            }
        }

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo
        public @interface CallType {
        }
    }

    public static final class CarExtender implements Extender {

        @RequiresApi
        public static class Api20Impl {
            @DoNotInline
            public static android.app.RemoteInput.Builder addExtras(android.app.RemoteInput.Builder builder, Bundle bundle) {
                return builder.addExtras(bundle);
            }

            @DoNotInline
            public static android.app.RemoteInput build(android.app.RemoteInput.Builder builder) {
                return builder.build();
            }

            @DoNotInline
            public static Parcelable castToParcelable(android.app.RemoteInput remoteInput) {
                return remoteInput;
            }

            @DoNotInline
            public static android.app.RemoteInput.Builder createBuilder(String str) {
                return new android.app.RemoteInput.Builder(str);
            }

            @DoNotInline
            public static boolean getAllowFreeFormInput(android.app.RemoteInput remoteInput) {
                return remoteInput.getAllowFreeFormInput();
            }

            @DoNotInline
            public static CharSequence[] getChoices(android.app.RemoteInput remoteInput) {
                return remoteInput.getChoices();
            }

            @DoNotInline
            public static Bundle getExtras(android.app.RemoteInput remoteInput) {
                return remoteInput.getExtras();
            }

            @DoNotInline
            public static CharSequence getLabel(android.app.RemoteInput remoteInput) {
                return remoteInput.getLabel();
            }

            @DoNotInline
            public static String getResultKey(android.app.RemoteInput remoteInput) {
                return remoteInput.getResultKey();
            }

            @DoNotInline
            public static android.app.RemoteInput.Builder setAllowFreeFormInput(android.app.RemoteInput.Builder builder, boolean z) {
                return builder.setAllowFreeFormInput(z);
            }

            @DoNotInline
            public static android.app.RemoteInput.Builder setChoices(android.app.RemoteInput.Builder builder, CharSequence[] charSequenceArr) {
                return builder.setChoices(charSequenceArr);
            }

            @DoNotInline
            public static android.app.RemoteInput.Builder setLabel(android.app.RemoteInput.Builder builder, CharSequence charSequence) {
                return builder.setLabel(charSequence);
            }
        }

        @RequiresApi
        public static class Api29Impl {
            @DoNotInline
            public static int getEditChoicesBeforeSending(android.app.RemoteInput remoteInput) {
                return remoteInput.getEditChoicesBeforeSending();
            }
        }

        @Deprecated
        public static class UnreadConversation {

            public static class Builder {
            }
        }
    }

    public static class DecoratedCustomViewStyle extends Style {

        @RequiresApi
        public static class Api24Impl {
            @DoNotInline
            public static Notification.Style createDecoratedCustomViewStyle() {
                return new Notification.DecoratedCustomViewStyle();
            }
        }
    }

    public interface Extender {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface GroupAlertBehavior {
    }

    public static class InboxStyle extends Style {
    }

    public static class MessagingStyle extends Style {

        @RequiresApi
        public static class Api24Impl {
            @DoNotInline
            public static Notification.MessagingStyle addMessage(Notification.MessagingStyle messagingStyle, Notification.MessagingStyle.Message message) {
                return messagingStyle.addMessage(message);
            }

            @DoNotInline
            public static Notification.MessagingStyle createMessagingStyle(CharSequence charSequence) {
                return new Notification.MessagingStyle(charSequence);
            }

            @DoNotInline
            public static Notification.MessagingStyle setConversationTitle(Notification.MessagingStyle messagingStyle, CharSequence charSequence) {
                return messagingStyle.setConversationTitle(charSequence);
            }
        }

        @RequiresApi
        public static class Api26Impl {
            @DoNotInline
            public static Notification.MessagingStyle addHistoricMessage(Notification.MessagingStyle messagingStyle, Notification.MessagingStyle.Message message) {
                return messagingStyle.addHistoricMessage(message);
            }
        }

        @RequiresApi
        public static class Api28Impl {
            @DoNotInline
            public static Notification.MessagingStyle createMessagingStyle(android.app.Person person) {
                return new Notification.MessagingStyle(person);
            }

            @DoNotInline
            public static Notification.MessagingStyle setGroupConversation(Notification.MessagingStyle messagingStyle, boolean z) {
                return messagingStyle.setGroupConversation(z);
            }
        }

        public static final class Message {

            @RequiresApi
            public static class Api24Impl {
                @DoNotInline
                public static Notification.MessagingStyle.Message createMessage(CharSequence charSequence, long j, CharSequence charSequence2) {
                    return new Notification.MessagingStyle.Message(charSequence, j, charSequence2);
                }

                @DoNotInline
                public static Notification.MessagingStyle.Message setData(Notification.MessagingStyle.Message message, String str, Uri uri) {
                    return message.setData(str, uri);
                }
            }

            @RequiresApi
            public static class Api28Impl {
                @DoNotInline
                public static Parcelable castToParcelable(android.app.Person person) {
                    return person;
                }

                @DoNotInline
                public static Notification.MessagingStyle.Message createMessage(CharSequence charSequence, long j, android.app.Person person) {
                    return new Notification.MessagingStyle.Message(charSequence, j, person);
                }
            }
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface NotificationVisibility {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface ServiceNotificationBehavior {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface StreamType {
    }

    public static abstract class Style {

        @RequiresApi
        public static class Api24Impl {
            @DoNotInline
            public static void setChronometerCountDown(RemoteViews remoteViews, int i, boolean z) {
                remoteViews.setChronometerCountDown(i, z);
            }
        }
    }

    public static final class TvExtender implements Extender {
    }

    public static final class WearableExtender implements Extender {
        public Bitmap mBackground;
        public String mBridgeTag;
        public int mContentIcon;
        public int mCustomContentHeight;
        public String mDismissalId;
        public PendingIntent mDisplayIntent;
        public int mHintScreenTimeout;
        public ArrayList mActions = new ArrayList();
        public int mFlags = 1;
        public ArrayList mPages = new ArrayList();
        public int mContentIconGravity = 8388613;
        public int mContentActionIndex = -1;
        public int mCustomSizePreset = 0;
        public int mGravity = 80;

        @RequiresApi
        public static class Api20Impl {
            @DoNotInline
            public static Notification.Action.Builder addExtras(Notification.Action.Builder builder, Bundle bundle) {
                return builder.addExtras(bundle);
            }

            @DoNotInline
            public static Notification.Action.Builder addRemoteInput(Notification.Action.Builder builder, android.app.RemoteInput remoteInput) {
                return builder.addRemoteInput(remoteInput);
            }

            @DoNotInline
            public static Notification.Action build(Notification.Action.Builder builder) {
                return builder.build();
            }

            @DoNotInline
            public static Notification.Action.Builder createBuilder(int i, CharSequence charSequence, PendingIntent pendingIntent) {
                return new Notification.Action.Builder(i, charSequence, pendingIntent);
            }

            @DoNotInline
            public static Action getActionCompatFromAction(ArrayList<Parcelable> arrayList, int i) {
                return NotificationCompat.getActionCompatFromAction((Notification.Action) arrayList.get(i));
            }
        }

        @RequiresApi
        public static class Api23Impl {
            @DoNotInline
            public static Notification.Action.Builder createBuilder(Icon icon, CharSequence charSequence, PendingIntent pendingIntent) {
                return new Notification.Action.Builder(icon, charSequence, pendingIntent);
            }
        }

        @RequiresApi
        public static class Api24Impl {
            @DoNotInline
            public static Notification.Action.Builder setAllowGeneratedReplies(Notification.Action.Builder builder, boolean z) {
                return builder.setAllowGeneratedReplies(z);
            }
        }

        @RequiresApi
        public static class Api31Impl {
            @DoNotInline
            public static Notification.Action.Builder setAuthenticationRequired(Notification.Action.Builder builder, boolean z) {
                return builder.setAuthenticationRequired(z);
            }
        }

        /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
        public WearableExtender m41clone() {
            WearableExtender wearableExtender = new WearableExtender();
            wearableExtender.mActions = new ArrayList(this.mActions);
            wearableExtender.mFlags = this.mFlags;
            wearableExtender.mDisplayIntent = this.mDisplayIntent;
            wearableExtender.mPages = new ArrayList(this.mPages);
            wearableExtender.mBackground = this.mBackground;
            wearableExtender.mContentIcon = this.mContentIcon;
            wearableExtender.mContentIconGravity = this.mContentIconGravity;
            wearableExtender.mContentActionIndex = this.mContentActionIndex;
            wearableExtender.mCustomSizePreset = this.mCustomSizePreset;
            wearableExtender.mCustomContentHeight = this.mCustomContentHeight;
            wearableExtender.mGravity = this.mGravity;
            wearableExtender.mHintScreenTimeout = this.mHintScreenTimeout;
            wearableExtender.mDismissalId = this.mDismissalId;
            wearableExtender.mBridgeTag = this.mBridgeTag;
            return wearableExtender;
        }
    }

    public static Action getActionCompatFromAction(Notification.Action action) {
        RemoteInput[] remoteInputArr;
        int i;
        android.app.RemoteInput[] remoteInputs = Api20Impl.getRemoteInputs(action);
        if (remoteInputs == null) {
            remoteInputArr = null;
        } else {
            RemoteInput[] remoteInputArr2 = new RemoteInput[remoteInputs.length];
            for (int i2 = 0; i2 < remoteInputs.length; i2++) {
                android.app.RemoteInput remoteInput = remoteInputs[i2];
                remoteInputArr2[i2] = new RemoteInput(Api20Impl.getResultKey(remoteInput), Api20Impl.getLabel(remoteInput), Api20Impl.getChoices(remoteInput), Api20Impl.getAllowFreeFormInput(remoteInput), Api29Impl.getEditChoicesBeforeSending(remoteInput), Api20Impl.getExtras(remoteInput), null);
            }
            remoteInputArr = remoteInputArr2;
        }
        boolean z = Api20Impl.getExtras(action).getBoolean("android.support.allowGeneratedReplies") || Api24Impl.getAllowGeneratedReplies(action);
        boolean z2 = Api20Impl.getExtras(action).getBoolean("android.support.action.showsUserInterface", true);
        int semanticAction = Api28Impl.getSemanticAction(action);
        boolean zIsContextual = Api29Impl.isContextual(action);
        boolean zIsAuthenticationRequired = Api31Impl.isAuthenticationRequired(action);
        if (Api23Impl.getIcon(action) != null || (i = action.icon) == 0) {
            return new Action(Api23Impl.getIcon(action) != null ? IconCompat.createFromIconOrNullIfZeroResId(Api23Impl.getIcon(action)) : null, action.title, action.actionIntent, Api20Impl.getExtras(action), remoteInputArr, (RemoteInput[]) null, z, semanticAction, z2, zIsContextual, zIsAuthenticationRequired);
        }
        return new Action(i, action.title, action.actionIntent, Api20Impl.getExtras(action), remoteInputArr, (RemoteInput[]) null, z, semanticAction, z2, zIsContextual, zIsAuthenticationRequired);
    }
}
