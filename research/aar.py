import zipfile, pathlib
src = "vendor/libxposed/api-102.0.0.aar"
z = zipfile.ZipFile(src)
print(z.namelist())
pathlib.Path("vendor/libxposed/classes.jar").write_bytes(z.read("classes.jar"))