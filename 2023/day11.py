from bisect import bisect

n = 10

galaxies = []
row_empty = []
col_empty = []

def parse():
    global n, row_empty, col_empty

    with open("input11") as f:
        lines = f.readlines()
        n = len(lines)
        row_empty = [True for _ in range(n)]
        col_empty = [True for _ in range(n)]
        for row,line in enumerate(lines):
            for col,ch in enumerate(line):
                if ch == '#':
                    galaxies.append((row,col))
                    row_empty[row] = False
                    col_empty[col] = False

    row_empty = [i for i,b in enumerate(row_empty) if b]
    col_empty = [i for i,b in enumerate(col_empty) if b]

    print(row_empty)
    print(col_empty)


def mapped(loc,mult=2):
    global row_empty,col_empty
    r,c = loc
    return (r + (mult - 1) * bisect(row_empty,r), c + (mult - 1) * bisect(col_empty,c))

def dist(g1,g2):
    r1,c1 = g1
    r2,c2 = g2
    return abs(r2-r1) + abs(c2-c1)

def part1():
    total = 0
    for i in range(len(galaxies)-1):
        for j in range(i+1,len(galaxies)):
            total += dist(mapped(galaxies[i]), mapped(galaxies[j]))
    print(total)


def part2():
    total = 0
    for i in range(len(galaxies)-1):
        for j in range(i+1,len(galaxies)):
            total += dist(mapped(galaxies[i],mult=1000000), mapped(galaxies[j],mult=1000000))
    print(total)

parse()
part1()
part2()
