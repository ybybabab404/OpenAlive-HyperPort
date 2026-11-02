import zipfile
z=zipfile.ZipFile(r'dist\HyperAlive-0.5.0-preview-arm64-v8a.apk')
names=[n for n in z.namelist() if 'xposed' in n.lower() or n=='classes2.dex']
print(names)
print(z.read('assets/xposed_init') if 'assets/xposed_init' in z.namelist() else 'MISSING')