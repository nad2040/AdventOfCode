max_depth = 0
boundary = {}
def parse():
    global max_depth,boundary
    max_depth = 0
    boundary = {}
    with open("input14.txt") as f:
        for line in f:
            line = line.strip()
            line = line.split(" -> ")
            line = list(map(eval,line))
            depth = max(list(map(lambda x:x[1], line)))
            if depth > max_depth:
                max_depth = depth
            for i in range(1,len(line)):
                if line[i][0] == line[i-1][0]:
                    for j in range(line[i-1][1],line[i][1],1 if line[i-1][1] < line[i][1] else -1):
                        boundary[(line[i-1][0],j)] = True
                else:
                    for j in range(line[i-1][0],line[i][0],1 if line[i-1][0] < line[i][0] else -1):
                        boundary[(j,line[i-1][1])] = True

            boundary[line[-1]] = True

source = (500,0)

def part1():
    count = 0
    while True:
        sand = source
        while True:
            if sand[1] > max_depth:
                break
            if (sand[0],sand[1]+1) not in boundary:
                sand = (sand[0],sand[1]+1)
            elif (sand[0]-1,sand[1]+1) not in boundary:
                sand = (sand[0]-1,sand[1]+1)
            elif (sand[0]+1,sand[1]+1) not in boundary:
                sand = (sand[0]+1,sand[1]+1)
            else:
                boundary[sand] = True
                count += 1
                break
        if sand[1] > max_depth:
            break
    print(count)


def part2():
    count = 0
    while True:
        sand = source
        while True:
            if sand[1] == max_depth + 1:
                boundary[sand] = True
                count += 1
                break
            if (sand[0],sand[1]+1) not in boundary:
                sand = (sand[0],sand[1]+1)
            elif (sand[0]-1,sand[1]+1) not in boundary:
                sand = (sand[0]-1,sand[1]+1)
            elif (sand[0]+1,sand[1]+1) not in boundary:
                sand = (sand[0]+1,sand[1]+1)
            else:
                boundary[sand] = True
                count += 1
                break
        if source in boundary:
            break

    print(count)

parse()
part1()

parse()
part2()
