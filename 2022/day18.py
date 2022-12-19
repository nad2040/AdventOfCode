
min_x = float("inf")
min_y = float("inf")
min_z = float("inf")
max_x = float("-inf")
max_y = float("-inf")
max_z = float("-inf")
blocks = {}
neighbor = [(1,0,0),(-1,0,0),(0,1,0),(0,-1,0),(0,0,1),(0,0,-1)]

def parse():
    global max_x,max_y,max_z,min_x,min_y,min_z,neighbor
    with open("input18.txt") as f:
        for line in f:
            line = line.strip()
            x,y,z = eval(line)
            count = 6
            min_x = min(min_x,x)
            min_y = min(min_y,y)
            min_z = min(min_z,z)
            max_x = max(max_x,x)
            max_y = max(max_y,y)
            max_z = max(max_z,z)
            for dx,dy,dz in neighbor:
                if (x+dx,y+dy,z+dz) in blocks:
                    blocks[(x+dx,y+dy,z+dz)] -= 1 
                    count -= 1 
            blocks[(x,y,z)] = count
            
    # print(blocks)

def part1():
    sum = 0
    for p in blocks:
        sum += blocks[p]
    print(sum)

def part2():
    start = (min_x-1,min_y-1,min_z-1)
    outside = 0
    visited = set()
    Q = []
    Q.append(start)
    while len(Q) > 0:
        p = Q.pop(0)
        x,y,z = p
        if p not in visited and x in range(min_x-1,max_x+2) and y in range(min_y-1,max_y+2) and z in range(min_z-1,max_z+2):
            visited.add(p)
            for dx,dy,dz in neighbor:
                if (x+dx,y+dy,z+dz) in blocks:
                    outside+=1 
                else:
                    Q.append((x+dx,y+dy,z+dz))
    print(outside)



parse()
part1()
part2()
