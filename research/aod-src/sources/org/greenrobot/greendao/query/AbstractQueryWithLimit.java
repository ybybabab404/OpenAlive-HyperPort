package org.greenrobot.greendao.query;

import org.greenrobot.greendao.AbstractDao;

/* JADX INFO: loaded from: classes3.dex */
abstract class AbstractQueryWithLimit<T> extends AbstractQuery<T> {
    protected final int limitPosition;
    protected final int offsetPosition;

    public AbstractQueryWithLimit(AbstractDao<T, ?> abstractDao, String str, String[] strArr, int i, int i2) {
        super(abstractDao, str, strArr);
        this.limitPosition = i;
        this.offsetPosition = i2;
    }

    public void setLimit(int i) {
        checkThread();
        int i2 = this.limitPosition;
        if (i2 == -1) {
            throw new IllegalStateException("Limit must be set with QueryBuilder before it can be used here");
        }
        this.parameters[i2] = Integer.toString(i);
    }

    public void setOffset(int i) {
        checkThread();
        int i2 = this.offsetPosition;
        if (i2 == -1) {
            throw new IllegalStateException("Offset must be set with QueryBuilder before it can be used here");
        }
        this.parameters[i2] = Integer.toString(i);
    }

    @Override // org.greenrobot.greendao.query.AbstractQuery
    public AbstractQueryWithLimit<T> setParameter(int i, Object obj) {
        if (i < 0 || !(i == this.limitPosition || i == this.offsetPosition)) {
            return (AbstractQueryWithLimit) super.setParameter(i, obj);
        }
        throw new IllegalArgumentException("Illegal parameter index: " + i);
    }
}
