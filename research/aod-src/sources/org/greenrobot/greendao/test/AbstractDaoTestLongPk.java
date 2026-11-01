package org.greenrobot.greendao.test;

import android.test.AndroidTestCase;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.DaoLog;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AbstractDaoTestLongPk<D extends AbstractDao<T, Long>, T> extends AbstractDaoTestSinglePk<D, T, Long> {
    public AbstractDaoTestLongPk(Class<D> cls) {
        super(cls);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.test.AbstractDaoTestSinglePk
    public Long createRandomPk() {
        return Long.valueOf(this.random.nextLong());
    }

    public void testAssignPk() {
        if (!this.daoAccess.isEntityUpdateable()) {
            DaoLog.d("Skipping testAssignPk for not updateable " + this.daoClass);
            return;
        }
        T tCreateEntity = createEntity(null);
        if (tCreateEntity == null) {
            DaoLog.d("Skipping testAssignPk for " + this.daoClass + " (createEntity returned null for null key)");
            return;
        }
        T tCreateEntity2 = createEntity(null);
        this.dao.insert(tCreateEntity);
        this.dao.insert(tCreateEntity2);
        Long l = (Long) this.daoAccess.getKey(tCreateEntity);
        AndroidTestCase.assertNotNull(l);
        Long l2 = (Long) this.daoAccess.getKey(tCreateEntity2);
        AndroidTestCase.assertNotNull(l2);
        AndroidTestCase.assertFalse(l.equals(l2));
        AndroidTestCase.assertNotNull(this.dao.load(l));
        AndroidTestCase.assertNotNull(this.dao.load(l2));
    }
}
