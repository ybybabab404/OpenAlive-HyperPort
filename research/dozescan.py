import zipfile,re
z=zipfile.ZipFile('research/SystemUI.apk')
pat=re.compile(rb'L(?:com|miui)/[A-Za-z0-9_/$]*(?:Doze|AodView|AodWindow|AodRoot|AodScreen|AodOverlay)[A-Za-z0-9_/$]*;')
found=set()
for n in z.namelist():
    if n.endswith('.dex'):
        for m in pat.finditer(z.read(n)): found.add(m.group()[1:-1].decode())
print(len(found))
print('\n'.join(sorted(found)[:40]))