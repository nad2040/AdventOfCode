import heapdict

next_valve = {}
graph = {}
flowrates = {}
to_be_opened = 0

def dijkstra(G,u):
    dist = {}
    prev = {}
    dist[u] = 0
    Q = heapdict.heapdict()
    for v in G:
        if v != u:
            dist[v] = float("inf")
        prev[v] = None
        Q[v] = dist[v]
    
    while len(Q) > 0:
        u = Q.popitem()[0]
        for v in G[u]:
            if v in Q:
                alt = dist[u] + 1 
                if alt < dist[v]:
                    dist[v] = alt
                    prev[v] = u
                    Q[v] = alt

    return dist

def makegraph(neighbors,graph):
    for u in neighbors:
        graph[u] = dijkstra(neighbors,u)
        

def parse():
    global next_valve,graph,flowrates,to_be_opened
    with open("input16.txt") as f:
        for line in f:
            line = line.strip()
            line = line.split()
            cur_valve = line[1]
            flowrates[line[1]] = int(line[4][5:-1])
            valves = line[9:]
            for i,valve in enumerate(valves):
                if valve[-1] == ',':
                    valves[i] = valve[:-1]
            next_valve[cur_valve] = valves

    count = 0
    for v in flowrates:
        if flowrates[v] != 0:
            count += 1 
    to_be_opened = count
    makegraph(next_valve,graph)

memo = {}
def recurse(valve="AA",step=0,opened=[],pressure=0):
    if len(opened)==to_be_opened or step >= 30:
        return pressure
    if (valve,step,tuple(opened),pressure) in memo:
        return memo[(valve,step,tuple(opened),pressure)]

    pressures = []
    if valve not in opened and flowrates[valve] != 0:
        pressures.append(recurse(valve,step+1,opened+[valve],pressure+(29-step)*flowrates[valve]))
    else:
        for v,flow in flowrates.items():
            if v not in opened and flow != 0:
                new_step = step+graph[valve][v]
                pressures.append(recurse(v,new_step,opened,pressure))
    memo[(valve,step,tuple(opened),pressure)] = max(pressures)
    return memo[(valve,step,tuple(opened),pressure)]

def part1():
    print(graph)
    print(recurse())

    
         
    
def part2():
    pass


parse()
part1()
part2()
