
from PIL import Image
im = Image.open(r'C:\Users\ybybabab\app\项目开发\HyperAlive\research\aod_live.png').convert('RGB')
print('size', im.size)
small = im.resize((60, 120))
px = list(small.getdata())
n = len(px)
avg = tuple(sum(p[i] for p in px)//n for i in range(3))
mx = max(max(p) for p in px)
nonblack = sum(1 for p in px if max(p) > 12) / n
print('avg', avg, 'max', mx, 'nonblack%', round(nonblack*100, 1))
# dominant bright colors
from collections import Counter
q = Counter((p[0]//32*32, p[1]//32*32, p[2]//32*32) for p in px)
print('top', q.most_common(5))
