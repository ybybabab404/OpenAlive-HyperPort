package com.miui.aod.db;

import java.util.Map;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

/* JADX INFO: loaded from: classes.dex */
public class DaoSession extends AbstractDaoSession {
    public final HistoryEntityDao historyEntityDao;
    public final DaoConfig historyEntityDaoConfig;
    public final ThemeEntityDao themeEntityDao;
    public final DaoConfig themeEntityDaoConfig;

    public DaoSession(Database database, IdentityScopeType identityScopeType, Map map) {
        super(database);
        DaoConfig daoConfigM1357clone = ((DaoConfig) map.get(HistoryEntityDao.class)).clone();
        this.historyEntityDaoConfig = daoConfigM1357clone;
        daoConfigM1357clone.initIdentityScope(identityScopeType);
        DaoConfig daoConfigM1357clone2 = ((DaoConfig) map.get(ThemeEntityDao.class)).clone();
        this.themeEntityDaoConfig = daoConfigM1357clone2;
        daoConfigM1357clone2.initIdentityScope(identityScopeType);
        HistoryEntityDao historyEntityDao = new HistoryEntityDao(daoConfigM1357clone, this);
        this.historyEntityDao = historyEntityDao;
        ThemeEntityDao themeEntityDao = new ThemeEntityDao(daoConfigM1357clone2, this);
        this.themeEntityDao = themeEntityDao;
        registerDao(HistoryEntity.class, historyEntityDao);
        registerDao(ThemeEntity.class, themeEntityDao);
    }

    public HistoryEntityDao getHistoryEntityDao() {
        return this.historyEntityDao;
    }

    public ThemeEntityDao getThemeEntityDao() {
        return this.themeEntityDao;
    }
}
