import sqlite3
c=sqlite3.connect('research/db/modules_config.db')
print('MOD:', list(c.execute("select * from modules where mid like '%aliveclean%'")))
print('STATE:', [r for r in c.execute('select * from modules_state') if 'aliveclean' in str(r)])
print('SCOPE:', [r for r in c.execute('select * from scope') if 'aliveclean' in str(r)][:10])