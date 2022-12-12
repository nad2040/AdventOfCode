import heapdict

height_map = []
def parse():
    global height_map
    height_map = []
    start,end = None,None
    with open("input12.txt") as f:
        for i,line in enumerate(f):
            line = line.strip()
            row = []
            for j,c in enumerate(line):
                if c == 'S':
                    row.append(ord('a'))
                    start = (i,j)
                elif c == 'E':
                    row.append(ord('z'))
                    end = (i,j)
                else:
                    row.append(ord(c))
            height_map.append(row)
    return start,end

def in_range(p):
    return p[0] in range(len(height_map)) and p[1] in range(len(height_map[0]))

def can_step(c,p):
    return in_range(p) and height_map[p[0]][p[1]] <= height_map[c[0]][c[1]] + 1

def dijkstra(G,start,end):
    prev = {}
    dist = {}
    dist[start] = 0

    Q = heapdict.heapdict()
    for v in G:
        if v != start:
            dist[v] = float("inf")
            prev[v] = None
        Q[v] = dist[v]
    
    while len(Q) > 0:
        u = Q.popitem()[0]

        if u == end: # Ending condition
            path = []
            if u in prev or u == start:
                while u in prev:
                    path.append(u)
                    u = prev[u]
            return path

        for v in [(u[0]+1,u[1]), (u[0],u[1]+1), (u[0]-1,u[1]), (u[0],u[1]-1)]:
            if v in Q and can_step(u,v):
                # print(f"checking neighbor {v} of {u}")
                alt = dist[u] + 1 
                if alt < dist[v]:
                    dist[v] = alt
                    prev[v] = u
                    Q[v] = alt


def part1(start,end):
    graph = [(x,y) for x in range(len(height_map)) for y in range(len(height_map[0]))]
    path = dijkstra(graph,start,end)
    print(len(path))

def part2(end):
    graph = [(x,y) for x in range(len(height_map)) for y in range(len(height_map[0]))]
    starts = [(x,y) for (x,y) in graph if y < 8 and height_map[x][y] == ord('a')]
    
    min_dist = float("inf")
    for start in starts:
        path = dijkstra(graph,start,end)
        if len(path) == 1:
            continue
        if len(path) < min_dist:
            min_dist = len(path)

    print(min_dist)

s,e = parse()
part1(s,e)
part2(e)
