package androidx.coordinatorlayout.widget;

import androidx.annotation.RestrictTo;
import androidx.collection.SimpleArrayMap;
import androidx.core.util.Pools;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public final class DirectedAcyclicGraph<T> {
    public final Pools.Pool mListPool = new Pools.SimplePool(10);
    public final SimpleArrayMap mGraph = new SimpleArrayMap();
    public final ArrayList mSortResult = new ArrayList();
    public final HashSet mSortTmpMarked = new HashSet();

    public void addEdge(Object obj, Object obj2) {
        if (!this.mGraph.containsKey(obj) || !this.mGraph.containsKey(obj2)) {
            throw new IllegalArgumentException("All nodes must be present in the graph before being added as an edge");
        }
        ArrayList emptyList = (ArrayList) this.mGraph.get(obj);
        if (emptyList == null) {
            emptyList = getEmptyList();
            this.mGraph.put(obj, emptyList);
        }
        emptyList.add(obj2);
    }

    public void addNode(Object obj) {
        if (this.mGraph.containsKey(obj)) {
            return;
        }
        this.mGraph.put(obj, null);
    }

    public void clear() {
        int size = this.mGraph.size();
        for (int i = 0; i < size; i++) {
            ArrayList arrayList = (ArrayList) this.mGraph.valueAt(i);
            if (arrayList != null) {
                poolList(arrayList);
            }
        }
        this.mGraph.clear();
    }

    public boolean contains(Object obj) {
        return this.mGraph.containsKey(obj);
    }

    public final void dfs(Object obj, ArrayList arrayList, HashSet hashSet) {
        if (arrayList.contains(obj)) {
            return;
        }
        if (hashSet.contains(obj)) {
            throw new RuntimeException("This graph contains cyclic dependencies");
        }
        hashSet.add(obj);
        ArrayList arrayList2 = (ArrayList) this.mGraph.get(obj);
        if (arrayList2 != null) {
            int size = arrayList2.size();
            for (int i = 0; i < size; i++) {
                dfs(arrayList2.get(i), arrayList, hashSet);
            }
        }
        hashSet.remove(obj);
        arrayList.add(obj);
    }

    public final ArrayList getEmptyList() {
        ArrayList arrayList = (ArrayList) this.mListPool.acquire();
        return arrayList == null ? new ArrayList() : arrayList;
    }

    public List getIncomingEdges(Object obj) {
        return (List) this.mGraph.get(obj);
    }

    public List getOutgoingEdges(Object obj) {
        int size = this.mGraph.size();
        ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            ArrayList arrayList2 = (ArrayList) this.mGraph.valueAt(i);
            if (arrayList2 != null && arrayList2.contains(obj)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(this.mGraph.keyAt(i));
            }
        }
        return arrayList;
    }

    public ArrayList getSortedList() {
        this.mSortResult.clear();
        this.mSortTmpMarked.clear();
        int size = this.mGraph.size();
        for (int i = 0; i < size; i++) {
            dfs(this.mGraph.keyAt(i), this.mSortResult, this.mSortTmpMarked);
        }
        return this.mSortResult;
    }

    public boolean hasOutgoingEdges(Object obj) {
        int size = this.mGraph.size();
        for (int i = 0; i < size; i++) {
            ArrayList arrayList = (ArrayList) this.mGraph.valueAt(i);
            if (arrayList != null && arrayList.contains(obj)) {
                return true;
            }
        }
        return false;
    }

    public final void poolList(ArrayList arrayList) {
        arrayList.clear();
        this.mListPool.release(arrayList);
    }
}
