import zipfile,re,sys
pat=re.compile(rb'L(?:com|miui)/(?:android|miui)/systemui/[A-Za-z0-9_/$]*[Aa][Oo][Dd][A-Za-z0-9_/$]*;')
for path in ['research/SystemUI.apk','research/SysUIPlugin.apk']:
    try: z=zipfile.ZipFile(path)
    except Exception: continue
    found=set()
    for n in z.namelist():
        if not n.endswith('.dex'): continue
        for m in pat.finditer(z.read(n)): found.add(m.group()[1:-1].decode())
    print(path, len(found))
    print('\n'.join(sorted(found)[:25]))