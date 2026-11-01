package com.miui.keyguard.editor.viewmodel;

import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.EditorApplicationProxy;
import com.miui.keyguard.editor.homepage.bean.FloorItemBean;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import com.miui.keyguard.editor.homepage.model.CrossListDataModel;
import com.miui.keyguard.editor.homepage.view.generator.BaseItemViewFactoryImpl;
import com.miui.keyguard.editor.utils.DeviceScreenshotHelper;
import com.miui.maml.R;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
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
/* JADX INFO: compiled from: EditHistoryViewModel.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class EditHistoryViewModel extends ViewModel {
    public static final Companion Companion = new Companion(null);
    public MutableLiveData _currentTemplatePosition;
    public final MutableLiveData _deleteMode;
    public final MutableLiveData _historyListLiveData;
    public final MutableLiveData _nightMode;
    public final MutableLiveData _selectAllLiveData;
    public final MutableLiveData _selectedListLiveData;
    public LiveData currentTemplatePosition;
    public final LiveData deleteMode;
    public final MutableLiveData historyCountLiveData;
    public final LiveData historyListLiveData;
    public CrossListDataModel mDataModel;
    public final LiveData nightMode;
    public final LiveData selectAll;
    public final LiveData selectedListLiveData;

    /* JADX INFO: compiled from: EditHistoryViewModel.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final CoroutineScope getCoroutineScope(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            if (context instanceof LifecycleOwner) {
                return LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) context);
            }
            if (context instanceof DeviceScreenshotHelper.ScreenshotContext) {
                return CoroutineScopeKt.CoroutineScope(Dispatchers.getMain());
            }
            throw new IllegalArgumentException("error context " + context);
        }

        public final EditHistoryViewModel getViewModel(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            EditorActivity editorActivity = context instanceof EditorActivity ? (EditorActivity) context : null;
            if (editorActivity != null) {
                return (EditHistoryViewModel) new ViewModelProvider(editorActivity).get(EditHistoryViewModel.class);
            }
            Log.d("EditHistoryViewModel", "Convert context error:" + context);
            return null;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.viewmodel.EditHistoryViewModel$loadHistoryData$1, reason: invalid class name */
    /* JADX INFO: compiled from: EditHistoryViewModel.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.viewmodel.EditHistoryViewModel$loadHistoryData$1", f = "EditHistoryViewModel.kt", l = {R.styleable.AppCompatTheme_listChoiceIndicatorSingleAnimated}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Context $context;
        public int label;

        /* JADX INFO: renamed from: com.miui.keyguard.editor.viewmodel.EditHistoryViewModel$loadHistoryData$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: EditHistoryViewModel.kt */
        @Metadata
        @DebugMetadata(c = "com.miui.keyguard.editor.viewmodel.EditHistoryViewModel$loadHistoryData$1$1", f = "EditHistoryViewModel.kt", l = {}, m = "invokeSuspend")
        public static final class C00381 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            public final /* synthetic */ Context $context;
            public int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00381(Context context, Continuation continuation) {
                super(2, continuation);
                this.$context = context;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C00381(this.$context, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
                return ((C00381) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                new BaseItemViewFactoryImpl(this.$context, false, 2, null).initBaseItemCache();
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Context context, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
        }

        public static final void invokeSuspend$lambda$1(EditHistoryViewModel editHistoryViewModel, List list) {
            List listFilterSpecialWallpapers;
            List items;
            CrossListDataModel crossListDataModel = editHistoryViewModel.mDataModel;
            if (crossListDataModel == null || (listFilterSpecialWallpapers = crossListDataModel.filterSpecialWallpapers(list)) == null) {
                return;
            }
            editHistoryViewModel._historyListLiveData.setValue(listFilterSpecialWallpapers);
            int i = 0;
            FloorItemBean floorItemBean = (FloorItemBean) listFilterSpecialWallpapers.get(0);
            if (floorItemBean == null || (items = floorItemBean.getItems()) == null) {
                return;
            }
            Iterator it = items.iterator();
            while (it.hasNext()) {
                if (((TemplateItemBean) it.next()).getItemType() == 2) {
                    i++;
                }
            }
            editHistoryViewModel.historyCountLiveData.setValue(Integer.valueOf(i));
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return EditHistoryViewModel.this.new AnonymousClass1(this.$context, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineDispatcher io2 = Dispatchers.getIO();
                C00381 c00381 = new C00381(this.$context, null);
                this.label = 1;
                if (BuildersKt.withContext(io2, c00381, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            EditHistoryViewModel.this.ensureDataModel(this.$context);
            CrossListDataModel crossListDataModel = EditHistoryViewModel.this.mDataModel;
            if (crossListDataModel != null) {
                final EditHistoryViewModel editHistoryViewModel = EditHistoryViewModel.this;
                crossListDataModel.getAllHistoryList(new Consumer() { // from class: com.miui.keyguard.editor.viewmodel.EditHistoryViewModel$loadHistoryData$1$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj2) {
                        EditHistoryViewModel.AnonymousClass1.invokeSuspend$lambda$1(editHistoryViewModel, (List) obj2);
                    }
                });
            }
            return Unit.INSTANCE;
        }
    }

    public EditHistoryViewModel() {
        MutableLiveData mutableLiveData = new MutableLiveData();
        this._nightMode = mutableLiveData;
        this.nightMode = mutableLiveData;
        MutableLiveData mutableLiveData2 = new MutableLiveData();
        this._deleteMode = mutableLiveData2;
        this.deleteMode = mutableLiveData2;
        MutableLiveData mutableLiveData3 = new MutableLiveData();
        this._currentTemplatePosition = mutableLiveData3;
        this.currentTemplatePosition = mutableLiveData3;
        MutableLiveData mutableLiveData4 = new MutableLiveData();
        this._selectedListLiveData = mutableLiveData4;
        this.selectedListLiveData = mutableLiveData4;
        MutableLiveData mutableLiveData5 = new MutableLiveData();
        this._historyListLiveData = mutableLiveData5;
        this.historyListLiveData = mutableLiveData5;
        this.historyCountLiveData = new MutableLiveData();
        MutableLiveData mutableLiveData6 = new MutableLiveData();
        this._selectAllLiveData = mutableLiveData6;
        this.selectAll = mutableLiveData6;
    }

    public final void ensureDataModel(Context context) {
        if (this.mDataModel == null) {
            this.mDataModel = new CrossListDataModel(context, EditorApplicationProxy.Companion.getCrossListPreloader());
        }
    }

    public final LiveData getCurrentTemplatePosition() {
        return this.currentTemplatePosition;
    }

    public final LiveData getDeleteMode() {
        return this.deleteMode;
    }

    public final LiveData getHistoryListLiveData() {
        return this.historyListLiveData;
    }

    public final LiveData getNightMode() {
        return this.nightMode;
    }

    public final LiveData getSelectAll() {
        return this.selectAll;
    }

    public final LiveData getSelectedListLiveData() {
        return this.selectedListLiveData;
    }

    public final void loadHistoryData(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        BuildersKt__Builders_commonKt.launch$default(Companion.getCoroutineScope(context), null, null, new AnonymousClass1(context, null), 3, null);
    }

    public final void updateCurrentTemplatePosition(int i) {
        this._currentTemplatePosition.setValue(Integer.valueOf(i));
    }

    public final void updateDeleteMode(boolean z) {
        this._deleteMode.setValue(Boolean.valueOf(z));
    }

    public final void updateHistoryCount() {
        Integer numValueOf;
        MutableLiveData mutableLiveData = this.historyCountLiveData;
        Integer num = (Integer) mutableLiveData.getValue();
        if (num != null) {
            int iIntValue = num.intValue();
            List list = (List) this._selectedListLiveData.getValue();
            numValueOf = Integer.valueOf(iIntValue - (list != null ? list.size() : 0));
        } else {
            numValueOf = null;
        }
        mutableLiveData.setValue(numValueOf);
    }

    public final void updateNightMode(boolean z) {
        this._nightMode.setValue(Boolean.valueOf(z));
    }

    public final void updateSelectedList(List data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this._selectedListLiveData.setValue(data);
        MutableLiveData mutableLiveData = this._selectAllLiveData;
        int size = data.size();
        Integer num = (Integer) this.historyCountLiveData.getValue();
        mutableLiveData.setValue(Boolean.valueOf(num != null && size == num.intValue()));
    }
}
