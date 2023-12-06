races = []
times = []
distances = []

def parse():
    global races, times, distances
    with open("input6") as f:
        times = list(map(int, f.readline().split()[1:]))
        distances = list(map(int, f.readline().split()[1:]))

        races = list(zip(times, distances))
        # print(races)

from functools import reduce

def part1():
    ways = []
    for time,dist in races:
        count = 0
        for t in range(1,time):
            if t * (time - t) > dist:
                count += 1
        ways.append(count)
    print(reduce(lambda x,y: x*y, ways))


def part2():
    time = int(reduce(lambda x,y: str(x)+str(y), times))
    dist = int(reduce(lambda x,y: str(x)+str(y), distances))
    count = 0
    for t in range(1,time):
        if t * (time - t) > dist:
            count += 1
    print(count)

parse()
part1()
part2()
