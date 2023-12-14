blocks = set()
rocks = set()
n = 100

def parse():
    with open("input14") as f:
        for row,line in enumerate(f):
            for col,char in enumerate(line):
                if char == 'O':
                    rocks.add((row,col))
                if char == '#':
                    blocks.add((row,col))

def table():
    for r in range(n):
        for c in range(n):
            if (r,c) in blocks:
                print("#",end="")
            elif (r,c) in rocks:
                print("O",end="")
            else:
                print(".",end="")
        print()
    print()

def load(rcks=None):
    global rocks
    rks = set()
    if rcks is None:
        rks = rocks
    else:
        rks = rcks
    sum = 0
    for (r,_) in rks:
        sum += n - r
    print(sum)

def tilt(dir):
    global n,rocks,blocks
    if dir == 0: # tilt up
        for c in range(n):
            start_row = n-1
            end_row = start_row
            while end_row >= 0:
                count_rocks = 0
                while (end_row, c) not in blocks and end_row >= 0:
                    if (end_row, c) in rocks:
                        count_rocks += 1
                    end_row -= 1
                for r in range(end_row+1,start_row+1):
                    if r < end_row+count_rocks+1:
                        rocks.add((r,c))
                    else:
                        if (r,c) in rocks:
                            rocks.remove((r,c))
                start_row = end_row-1
                end_row = start_row
    elif dir == 1: # tilt west
        for r in range(n):
            start_col = n-1
            end_col = start_col
            while end_col >= 0:
                count_rocks = 0
                while (r, end_col) not in blocks and end_col >= 0:
                    if (r, end_col) in rocks:
                        count_rocks += 1
                    end_col -= 1
                for c in range(end_col+1,start_col+1):
                    if c < end_col+count_rocks+1:
                        rocks.add((r,c))
                    else:
                        if (r,c) in rocks:
                            rocks.remove((r,c))
                start_col = end_col-1
                end_col = start_col
    elif dir == 2: # tilt south
        for c in range(n):
            start_row = 0
            end_row = start_row
            while end_row < n:
                count_rocks = 0
                while (end_row, c) not in blocks and end_row < n:
                    if (end_row, c) in rocks:
                        count_rocks += 1
                    end_row += 1
                for r in range(end_row-1,start_row-1,-1):
                    if r >= end_row-count_rocks:
                        rocks.add((r,c))
                    else:
                        if (r,c) in rocks:
                            rocks.remove((r,c))
                start_row = end_row+1
                end_row = start_row
    elif dir == 3: # tilt east
        for r in range(n):
            start_col = 0
            end_col = start_col
            while end_col < n:
                count_rocks = 0
                while (r, end_col) not in blocks and end_col < n:
                    if (r, end_col) in rocks:
                        count_rocks += 1
                    end_col += 1
                for c in range(end_col-1,start_col-1,-1):
                    if c >= end_col-count_rocks:
                        rocks.add((r,c))
                    else:
                        if (r,c) in rocks:
                            rocks.remove((r,c))
                start_col = end_col+1
                end_col = start_col

def part1():
    global n,rocks,blocks
    tilt(0)
    table()
    load()

def part2():
    global rocks
    prev_rocks_list = []
    prev_rocks = set()
    while True:
        prev_rocks.add(frozenset(rocks))
        prev_rocks_list.append(frozenset(rocks))
        tilt(0)
        tilt(1)
        tilt(2)
        tilt(3)
        table()
        if frozenset(rocks) in prev_rocks:
            prev_rocks_list.append(frozenset(rocks))
            break

    cycle_start = prev_rocks_list.index(rocks)
    cycle_length = len(prev_rocks_list) - cycle_start - 1
    index = (1_000_000_000 - cycle_start) % cycle_length + cycle_start
    load(prev_rocks_list[index])






parse()
# part1()
part2()
