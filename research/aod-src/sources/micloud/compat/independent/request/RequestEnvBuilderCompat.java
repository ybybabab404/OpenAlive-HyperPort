package micloud.compat.independent.request;

import android.accounts.Account;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.xiaomi.micloudsdk.data.ExtendedAuthToken;
import com.xiaomi.micloudsdk.remote.RemoteMethodInvoker;
import com.xiaomi.micloudsdk.request.utils.RequestContext;
import com.xiaomi.micloudsdk.utils.MiCloudSDKDependencyUtil;

/* JADX INFO: loaded from: classes2.dex */
public class RequestEnvBuilderCompat {
    private static final String TAG = "RequestEvnBuilderCompat";
    private static final IRequestEnvBuilderCompat sRequestEnvBuilderCompatImpl;

    static {
        if (MiCloudSDKDependencyUtil.SDKEnvironment >= 18) {
            sRequestEnvBuilderCompatImpl = new RequestEnvBuilderCompat_V18();
        } else {
            sRequestEnvBuilderCompatImpl = new RequestEnvBuilderCompat_Base();
        }
    }

    private RequestEnvBuilderCompat() {
    }

    public static RequestContext.RequestEnv build() {
        final IRequestEnvBuilderCompat.RequestEnv requestEnvBuild = sRequestEnvBuilderCompatImpl.build();
        if (requestEnvBuild == null) {
            return null;
        }
        return new RequestContext.RequestEnv() { // from class: micloud.compat.independent.request.RequestEnvBuilderCompat.1
            public String getAccountName() {
                Account systemAccount = requestEnvBuild.getSystemAccount(RequestContext.getContext());
                if (systemAccount == null) {
                    return null;
                }
                return systemAccount.name;
            }

            public long getAutoRetryInterval() {
                return requestEnvBuild.getAutoRetryInterval();
            }

            public int getMaxRetryCount() {
                return requestEnvBuild.getMaxRetryCount();
            }

            public String getUserAgent() {
                return requestEnvBuild.getUserAgent();
            }

            public void invalidateAuthToken() {
                requestEnvBuild.invalidateAuthToken(RequestContext.getContext());
            }

            /* JADX INFO: renamed from: queryAuthToken, reason: merged with bridge method [inline-methods] */
            public ExtendedAuthToken m1285queryAuthToken() {
                String strQueryAuthToken = requestEnvBuild.queryAuthToken(RequestContext.getContext());
                if (strQueryAuthToken == null) {
                    return null;
                }
                return ExtendedAuthToken.parse(strQueryAuthToken);
            }

            public String queryEncryptedAccountName() {
                final Account systemAccount = requestEnvBuild.getSystemAccount(RequestContext.getContext());
                if (systemAccount == null) {
                    return null;
                }
                return new RemoteMethodInvoker<String>(RequestContext.getContext()) { // from class: micloud.compat.independent.request.RequestEnvBuilderCompat.1.1
                    @Override // com.xiaomi.micloudsdk.remote.RemoteMethodInvoker
                    public boolean bindService(Context context, ServiceConnection serviceConnection) {
                        return BindAccountServiceCompat.bindAccountService(context, serviceConnection);
                    }

                    @Override // com.xiaomi.micloudsdk.remote.RemoteMethodInvoker
                    public String invokeRemoteMethod(IBinder iBinder) throws RemoteException {
                        return requestEnvBuild.getEncryptedUserId(RequestContext.getContext(), iBinder, systemAccount);
                    }
                }.invoke();
            }

            public boolean shouldUpdateHost() {
                return requestEnvBuild.shouldUpdateHost();
            }
        };
    }
}
