import zipfile
z=zipfile.ZipFile("research/lsp.apk")
print([n for n in z.namelist() if n.endswith(".jar") or "api" in n.lower()][:30])