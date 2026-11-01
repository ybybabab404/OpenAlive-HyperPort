package io.reactivex.rxjava3.subjects;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

/* JADX INFO: loaded from: classes2.dex */
public abstract class Subject<T> extends Observable<T> implements Observer<T> {
    public final Subject toSerialized() {
        return this instanceof SerializedSubject ? this : new SerializedSubject(this);
    }
}
