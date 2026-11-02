import sqlite3
c=sqlite3.connect(r'research/db/modules_config.db')
tabs=[r[0] for r in c.execute("select name from sqlite_master where type='table'")]
print(tabs)
for t in tabs:
    rows=list(c.execute(f'select * from "{t}" limit 3'))
    print(t, len(rows))