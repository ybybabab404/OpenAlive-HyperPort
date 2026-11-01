package com.xiaomi.taiyi.sdk.core.track;

import android.os.Bundle;
import com.xiaomi.onetrack.api.bf;
import com.xiaomi.taiyi.sdk.base.utils.DeviceUtil;
import com.xiaomi.taiyi.sdk.base.utils.ExtsKt;
import com.xiaomi.taiyi.sdk.transfer.data.AIHeaders;
import com.xiaomi.taiyi.sdk.transfer.data.AIRequest;
import com.xiaomi.taiyi.sdk.transfer.data.AIResponse;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ProcTrack {
    public static final Companion Companion = new Companion(null);
    public int b;
    public long d;
    public int f;
    public int g;
    public boolean h;
    public int i;
    public long j;
    public long k;
    public long l;
    public long m;
    public long n;
    public long o;
    public long p;
    public long q;
    public boolean t;
    public int u;
    public int v;
    public int w;
    public int x;
    public int y;
    public int z;
    public String a = "";
    public String c = "";
    public String e = "";
    public String r = "";
    public String s = "";
    public long A = ExtsKt.timestamp();
    public int B = 1;

    @Metadata
    public static final class Companion {
        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
        }

        public final long cost(String startKey, String endKey, AIHeaders headers) {
            Intrinsics.checkNotNullParameter(startKey, "startKey");
            Intrinsics.checkNotNullParameter(endKey, "endKey");
            Intrinsics.checkNotNullParameter(headers, "headers");
            long j = headers.getLong(startKey, 0L);
            if (j == 0) {
                return 0L;
            }
            long j2 = headers.getLong(endKey, 0L);
            if (j2 < j) {
                return 0L;
            }
            return j2 - j;
        }

        public final ProcTrack create(AIRequest request, AIResponse response) {
            Intrinsics.checkNotNullParameter(request, "request");
            Intrinsics.checkNotNullParameter(response, "response");
            ProcTrack procTrack = new ProcTrack();
            procTrack.setApi(request.getApi());
            procTrack.setLevel(request.getHeaders().getApiLevel());
            procTrack.setName(response.getHeaders().getAbilityName());
            procTrack.setVersion(response.getHeaders().getAbilityVersion());
            procTrack.setMethod(request.getHeaders().getMethod());
            procTrack.setCode(response.getCode());
            procTrack.setSpecCode(response.getBody().getSpecCode());
            procTrack.setBindCost(cost("track:start_bind", "track:end_bind", request.getHeaders()));
            procTrack.setLoadCost(cost("track:start_load", "track:end_load", request.getHeaders()));
            procTrack.setAppPreCost(cost("track:start_app_pre_proc", "track:end_app_pre_proc", request.getHeaders()));
            procTrack.setAppPostCost(cost("track:start_app_post_proc", "track:end_app_post_proc", response.getHeaders()));
            procTrack.setServicePreCost(cost("track:start_service_pre_proc", "track:end_service_pre_proc", response.getHeaders()));
            procTrack.setServicePostCost(cost("track:start_service_post_proc", "track:end_service_post_proc", response.getHeaders()));
            procTrack.setRunCost(cost("track:run_start", "track:run_end", response.getHeaders()));
            procTrack.setTotalCost(procTrack.computeTotalCost());
            procTrack.setRunInternal(response.getHeaders().getBoolean("track:run_internal", false));
            procTrack.setLoadSysMem(request.getBody().getInt("track:load_sys_mem", 0));
            procTrack.setLoadAppMem(request.getBody().getInt("track:load_app_mem", 0));
            procTrack.setLoadServiceMem(response.getBody().getInt("track:load_service_mem", 0));
            procTrack.setProcSysMem(DeviceUtil.getSysAvailMem());
            procTrack.setProcAppMem(DeviceUtil.getAppTotalPss());
            procTrack.setProcServiceMem(response.getBody().getInt("track:proc_service_mem", 0));
            procTrack.setAppProcess(ExtsKt.getMY_PROCESS_NAME());
            procTrack.setServiceProcess(response.getHeaders().getProcessName());
            return procTrack;
        }

        public final void enableLoad(AIRequest request, int i) {
            Intrinsics.checkNotNullParameter(request, "request");
            request.getBody().putBoolean("track:enable_load", true);
            request.getBody().putInt("track:load_code", i);
        }

        public final void endAppPostProcess(AIResponse response) {
            Intrinsics.checkNotNullParameter(response, "response");
            response.getHeaders().putLong("track:end_app_post_proc", ExtsKt.bootMills());
        }

        public final void endAppPreProcess(AIRequest request) {
            Intrinsics.checkNotNullParameter(request, "request");
            request.getHeaders().putLong("track:end_app_pre_proc", ExtsKt.bootMills());
        }

        public final void endBind(AIRequest request) {
            Intrinsics.checkNotNullParameter(request, "request");
            request.getHeaders().putLong("track:end_bind", ExtsKt.bootMills());
        }

        public final void endLoad(AIRequest request) {
            Intrinsics.checkNotNullParameter(request, "request");
            request.getHeaders().putLong("track:end_load", ExtsKt.bootMills());
        }

        public final void runInternal(AIResponse response, boolean z) {
            Intrinsics.checkNotNullParameter(response, "response");
            response.getHeaders().putBoolean("track:run_internal", z);
        }

        public final void startAppPostProcess(AIResponse response) {
            Intrinsics.checkNotNullParameter(response, "response");
            response.getHeaders().putLong("track:start_app_post_proc", ExtsKt.bootMills());
        }

        public final void startAppPreProcess(AIRequest request) {
            Intrinsics.checkNotNullParameter(request, "request");
            request.getHeaders().putLong("track:start_app_pre_proc", ExtsKt.bootMills());
        }

        public final void startBind(AIRequest request) {
            Intrinsics.checkNotNullParameter(request, "request");
            request.getHeaders().putLong("track:start_bind", ExtsKt.bootMills());
        }

        public final void startLoad(AIRequest request) {
            Intrinsics.checkNotNullParameter(request, "request");
            request.getHeaders().putLong("track:start_load", ExtsKt.bootMills());
            request.getBody().putInt("track:load_sys_mem", DeviceUtil.getSysAvailMem());
            request.getBody().putInt("track:load_app_mem", DeviceUtil.getAppTotalPss());
        }
    }

    public final Bundle bundle() {
        Bundle bundle = new Bundle();
        bundle.putString("api", this.a);
        bundle.putInt(com.xiaomi.onetrack.b.a.d, this.b);
        bundle.putString(bf.a, this.c);
        bundle.putLong("version", this.d);
        bundle.putString("method", this.e);
        bundle.putInt(com.xiaomi.onetrack.g.a.d, this.f);
        bundle.putInt("specCode", this.g);
        bundle.putBoolean("enableLoad", this.h);
        bundle.putInt("loadCode", this.i);
        bundle.putLong("bindCost", this.j);
        bundle.putLong("loadCost", this.k);
        bundle.putLong("appPreCost", this.l);
        bundle.putLong("appPostCost", this.m);
        bundle.putLong("servicePreCost", this.n);
        bundle.putLong("servicePostCost", this.o);
        bundle.putLong("totalCost", this.p);
        bundle.putLong("runCost", this.q);
        bundle.putString("appProcess", this.r);
        bundle.putString("serviceProcess", this.s);
        bundle.putBoolean("runInternal", this.t);
        bundle.putInt("loadAppMem", this.u);
        bundle.putInt("loadServiceMem", this.v);
        bundle.putInt("loadSysMem", this.w);
        bundle.putInt("procAppMem", this.x);
        bundle.putInt("procServiceMem", this.y);
        bundle.putInt("procSysMem", this.z);
        bundle.putLong("timestamp", this.A);
        bundle.putInt(com.xiaomi.onetrack.b.a.e, this.B);
        return bundle;
    }

    public final long computeTotalCost() {
        return this.j + this.k + this.l + this.m + this.n + this.o + this.q;
    }

    public final void setApi(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.a = str;
    }

    public final void setAppPostCost(long j) {
        this.m = j;
    }

    public final void setAppPreCost(long j) {
        this.l = j;
    }

    public final void setAppProcess(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.r = str;
    }

    public final void setBindCost(long j) {
        this.j = j;
    }

    public final void setCode(int i) {
        this.f = i;
    }

    public final void setLevel(int i) {
        this.b = i;
    }

    public final void setLoadAppMem(int i) {
        this.u = i;
    }

    public final void setLoadCost(long j) {
        this.k = j;
    }

    public final void setLoadServiceMem(int i) {
        this.v = i;
    }

    public final void setLoadSysMem(int i) {
        this.w = i;
    }

    public final void setMethod(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.e = str;
    }

    public final void setName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.c = str;
    }

    public final void setProcAppMem(int i) {
        this.x = i;
    }

    public final void setProcServiceMem(int i) {
        this.y = i;
    }

    public final void setProcSysMem(int i) {
        this.z = i;
    }

    public final void setRunCost(long j) {
        this.q = j;
    }

    public final void setRunInternal(boolean z) {
        this.t = z;
    }

    public final void setSample(int i) {
        this.B = i;
    }

    public final void setServicePostCost(long j) {
        this.o = j;
    }

    public final void setServicePreCost(long j) {
        this.n = j;
    }

    public final void setServiceProcess(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.s = str;
    }

    public final void setSpecCode(int i) {
        this.g = i;
    }

    public final void setTotalCost(long j) {
        this.p = j;
    }

    public final void setVersion(long j) {
        this.d = j;
    }
}
