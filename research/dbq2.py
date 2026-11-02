import sqlite3
c=sqlite3.connect('research/db/modules_config.db')
print('modules:', list(c.execute('select * from modules')))
print('state cols:', [d[0] for d in c.execute('pragma table_info(modules_state)')])
print('state:', list(c.execute('select * from modules_state')))
print('scope cols:', [d[0] for d in c.execute('pragma table_info(scope)')])
print('scope:', list(c.execute('select * from scope'))[:8])