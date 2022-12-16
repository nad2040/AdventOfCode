sensors = {}
beacons = set()
def parse():
    with open("input15.txt") as f:
        for line in f:
            line = line.strip()
            line = line.split()
            sx = int(line[2][2:-1])
            sy = int(line[3][2:-1])
            bx = int(line[8][2:-1])
            by = int(line[9][2:])
            beacons.add((bx,by))
            sensors[(sx,sy)] = abs(sx-bx) + abs(sy-by)
    
    # print(sensors)

def part1(y=10,upper=20):
    covered = set()
    for sensor in sensors:
        if abs(sensor[1] - y) <= sensors[sensor]:
            for i in range(sensor[0]-abs(sensors[sensor]-abs(sensor[1]-y)), sensor[0]+abs(sensors[sensor]-abs(sensor[1]-y)+1)):
                covered.add(i)
    counted_beacons = 0
    for x in covered:
        if (x,y) in beacons:
            counted_beacons += 1
    result = len(covered)-counted_beacons
    print(result)
    return covered.intersection(range(upper+1))

def distance(a,b):
    return abs(a[0]-b[0]) + abs(a[1]-b[1])

def line(a,b,upper=20):
    if b[1]-a[1] > 0:
        dy=1 
    else:
        dy=-1
    y=a[1]
    for x in range(a[0],b[0],1 if b[0]-a[0] > 0 else -1):
        if x in range(upper+1) and y in range(upper+1):
            yield (x,y)
        y+=dy

def boundary_gen(a,b,c,d,upper):
    for p in line(a,b,upper):
        yield p
    for p in line(b,c,upper):
        yield p
    for p in line(c,d,upper):
        yield p
    for p in line(d,a,upper):
        yield p

def part2(upper=20):
    point = (0,0)
    checked = set()
    for sensor,dist in sensors.items():
        a=(sensor[0]+dist+1,sensor[1])
        b=(sensor[0],sensor[1]+dist+1)
        c=(sensor[0]-dist-1,sensor[1])
        d=(sensor[0],sensor[1]-dist-1)
        for p in boundary_gen(a,b,c,d,upper):
            if p in checked:
                continue
            for s in sensors:
                if distance(p,s) <= sensors[s]:
                    checked.add(p)
                    break
            if p not in checked:
                point = p
                break
        else:
            continue
        break

    print(point[0]*4000000+point[1])
    
parse()
part1(2000000)
part2(4000000)
