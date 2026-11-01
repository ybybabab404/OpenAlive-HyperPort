package com.miui.keyguard.editor.homepage;

import android.content.Context;
import android.util.Log;
import com.miui.keyguard.editor.homepage.bean.FloorItemBean;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.model.CrossListDataModel;
import com.miui.keyguard.editor.homepage.view.generator.BaseItemViewFactoryImpl;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

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
/* JADX INFO: compiled from: MainFragment.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "com.miui.keyguard.editor.homepage.MainFragment$reloadAllData$1$1", f = "MainFragment.kt", l = {378}, m = "invokeSuspend")
@SourceDebugExtension
public final class MainFragment$reloadAllData$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Context $it;
    public int label;
    public final /* synthetic */ MainFragment this$0;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.homepage.MainFragment$reloadAllData$1$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: MainFragment.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.homepage.MainFragment$reloadAllData$1$1$1", f = "MainFragment.kt", l = {}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Context $it;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Context context, Continuation continuation) {
            super(2, continuation);
            this.$it = context;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$it, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            new BaseItemViewFactoryImpl(this.$it, false, 2, null).initBaseItemCache();
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MainFragment$reloadAllData$1$1(MainFragment mainFragment, Context context, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mainFragment;
        this.$it = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invokeSuspend$lambda$2(WeakReference weakReference, List list) {
        List listFilterSpecialWallpapers;
        MainFragment mainFragment = (MainFragment) weakReference.get();
        if (mainFragment == null) {
            return;
        }
        Log.i("Keyguard-Editor-MainFragment", "reloadAllData result: " + list.size());
        CrossListDataModel crossListDataModel = mainFragment.mDataModel;
        if (crossListDataModel == null || (listFilterSpecialWallpapers = crossListDataModel.filterSpecialWallpapers(list)) == null) {
            return;
        }
        Iterator it = ((FloorItemBean) listFilterSpecialWallpapers.get(0)).getItems().iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            } else if (((TemplateItemBean) it.next()).getItemType() == 1) {
                break;
            } else {
                i++;
            }
        }
        mainFragment.hasCurrentTemplate = i != -1;
        Log.i("Keyguard-Editor-MainFragment", "reloadAllData hasCurrentTemplate: " + mainFragment.hasCurrentTemplate);
        mainFragment.reFillViewPagerData();
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MainFragment$reloadAllData$1$1(this.this$0, this.$it, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((MainFragment$reloadAllData$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineDispatcher io2 = Dispatchers.getIO();
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$it, null);
            this.label = 1;
            if (BuildersKt.withContext(io2, anonymousClass1, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        this.this$0.ensureDataModel();
        this.this$0.isDataListRefreshed = false;
        Log.i("Keyguard-Editor-MainFragment", "start load all data when fragment created.");
        final WeakReference weakReference = new WeakReference(this.this$0);
        CrossListDataModel crossListDataModel = this.this$0.mDataModel;
        if (crossListDataModel != null) {
            crossListDataModel.getAllDataList(new Consumer() { // from class: com.miui.keyguard.editor.homepage.MainFragment$reloadAllData$1$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj2) {
                    MainFragment$reloadAllData$1$1.invokeSuspend$lambda$2(weakReference, (List) obj2);
                }
            });
        }
        return Unit.INSTANCE;
    }
}
