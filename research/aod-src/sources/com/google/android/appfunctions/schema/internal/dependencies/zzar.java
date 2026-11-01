package com.google.android.appfunctions.schema.internal.dependencies;

import com.google.android.appfunctions.schema.internal.dependencies.zzar;
import com.google.android.appfunctions.schema.internal.dependencies.zzax;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
public class zzar<MessageType extends zzax<MessageType, BuilderType>, BuilderType extends zzar<MessageType, BuilderType>> extends zzb<MessageType, BuilderType> {
    public final zzax defaultInstance;
    public zzax instance;

    public zzar(zzax zzaxVar) {
        this.defaultInstance = zzaxVar;
        if (zzaxVar.isMutable()) {
            throw new IllegalArgumentException("Default instance must be immutable.");
        }
        this.instance = newMutableInstance();
    }

    private zzax newMutableInstance() {
        return this.defaultInstance.newMutableInstance();
    }

    public zzax buildPartial() {
        if (!this.instance.isMutable()) {
            return this.instance;
        }
        this.instance.makeImmutable();
        return this.instance;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzb
    /* JADX INFO: renamed from: clone */
    public zzar mo89clone() {
        zzar zzarVarNewBuilderForType = getDefaultInstanceForType().newBuilderForType();
        zzarVarNewBuilderForType.instance = buildPartial();
        return zzarVarNewBuilderForType;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcf
    public zzax getDefaultInstanceForType() {
        return this.defaultInstance;
    }
}
