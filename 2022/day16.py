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
def recurse1(valve="AA",step=0,opened=[],pressure=0):
    if len(opened) == to_be_opened or step >= 30:
        return pressure
    if (valve,step,tuple(opened),pressure) in memo:
        return memo[(valve,step,tuple(opened),pressure)]

    pressures = []
    if valve not in opened and flowrates[valve] != 0:
        pressures.append(recurse1(valve,step+1,opened+[valve],pressure+(29-step)*flowrates[valve]))
    else:
        for v,flow in flowrates.items():
            if v not in opened and flow != 0:
                pressures.append(recurse1(v,step+graph[valve][v],opened,pressure))
    memo[(valve,step,tuple(opened),pressure)] = max(pressures)
    return memo[(valve,step,tuple(opened),pressure)]

def part1():
    global memo
    memo = {}
    print(recurse1())

memo = {}
def recurse2(you="AA",ele="AA",you_step=0,ele_step=0,opened=[],pressure=0):
    if len(opened) == to_be_opened or you_step >= 26 and ele_step >= 26:
        return pressure
    if (you,ele,you_step,ele_step,tuple(opened),pressure) in memo:
        return memo[(you,ele,you_step,ele_step,tuple(opened),pressure)]

    pressures = []
    if you_step > ele_step: # ele takes step
        if ele not in opened and flowrates[ele] != 0:
            pressures.append(recurse2(you,ele,you_step,ele_step+1,opened+[ele],pressure+(25-ele_step)*flowrates[ele]))
        else:
            for new_ele,flow in flowrates.items():
                if new_ele not in opened and flow != 0:
                    pressures.append(recurse2(you,new_ele,you_step,ele_step+graph[ele][new_ele],opened,pressure))
    else: # you take step
        if you not in opened and flowrates[you] != 0:
            pressures.append(recurse2(you,ele,you_step+1,ele_step,opened+[you],pressure+(25-you_step)*flowrates[you]))
        else:
            for new_you,flow in flowrates.items():
                if new_you not in opened and flow != 0:
                    pressures.append(recurse2(new_you,ele,you_step+graph[you][new_you],ele_step,opened,pressure))
    memo[(you,ele,you_step,ele_step,tuple(opened),pressure)] = max(pressures)
    return memo[(you,ele,you_step,ele_step,tuple(opened),pressure)]

def part2():
    global memo
    memo = {}
    print(recurse2())

parse()
part1()
part2()
