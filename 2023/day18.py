border = set()
min_r = min_c = float("inf")
max_r = max_c = float("-inf")
instructions = []

def parse():
    global instructions, border, pool, min_r, min_c, max_r, max_c
    r,c = (0,0)
    border.add((0,0))
    with open("input18") as f:
        for line in f:
            line = line.strip().split()
            direction = line[0]
            dir = (0,1) if direction == 'R' else (1,0) if direction == 'D' else (0,-1) if direction == 'L' else (-1,0)
            length = int(line[1])
            color = line[2][2:8]
            instructions.append((dir,length,color))
            for _ in range(length):
                r += dir[0]
                c += dir[1]
                max_r = max(max_r,r)
                max_c = max(max_c,c)
                min_r = min(min_r,r)
                min_c = min(min_c,c)
                border.add((r,c))

def display(s):
    global min_r,max_r,min_c,max_c
    min_r = int(min_r)
    min_c = int(min_c)
    max_r = int(max_r)
    max_c = int(max_c)
    for r in range(min_r-1,max_r+2):
        for c in range(min_c-1,max_c+2):
            print('#' if (r,c) in s else '.',end='')
        print()
    print()


def part1():
    # jordan curve theorem, slightly more general code than my day10 solution
    global min_r,max_r,min_c,max_c
    min_r = int(min_r)
    min_c = int(min_c)
    max_r = int(max_r)
    max_c = int(max_c)
    vol = len(border)
    pool = border.copy()
    print("border size is",vol)
    r = min_r-1
    while r <= max_r+1:
        inside = False
        c = min_c-1
        while c <= max_c+1:
            pipe = (r,c)
            if {(r+1,c),(r,c),(r,c+1)}.issubset(border) or {(r-1,c),(r,c),(r,c+1)}.issubset(border):
                while not ({(r,c-1),(r,c),(r+1,c)}.issubset(border) or {(r,c-1),(r,c),(r-1,c)}.issubset(border)):
                    c += 1
                second_pipe = r,c
                r1,c1 = pipe
                r2,c2 = second_pipe
                if {(r1+1,c1),(r1,c1),(r1,c1+1)}.issubset(border) and {(r2,c2-1),(r2,c2),(r2-1,c2)}.issubset(border) or \
                   {(r1-1,c1),(r1,c1),(r1,c1+1)}.issubset(border) and {(r2,c2-1),(r2,c2),(r2+1,c2)}.issubset(border):
                        inside = not inside
            elif {(r-1,c),(r,c),(r+1,c)}.issubset(border):
                inside = not inside
            elif inside:
                pool.add((r,c))
                vol += 1
                # display(pool)
            c+=1
        r+=1
    # display(pool)
    print("total vol is",vol)

def part2():
    # shoelace theorem, the efficient solution
    global instructions
    coords = []
    start = (0,0)
    coords.append(start)
    total = 0
    missed_area = 0
    for _,_,hexcode in instructions:
        length = int(hexcode[:5],16)
        direction = "RDLU"[int(hexcode[5])]
        dir = (0,1) if direction == 'R' else (1,0) if direction == 'D' else (0,-1) if direction == 'L' else (-1,0)
        dr,dc = dir
        r1,c1 = coords[-1]
        r2,c2 = (r1 + dr * length, c1 + dc * length)
        coords.append((r2,c2))
        total += r1 * c2 - r2 * c1
        # for each long bar, subtract 1 to ignore the corners (dealt with below) and multiply by 1/2
        # to find the area missed. One side must be ouside, and the other, inside.
        missed_area += (length - 1) / 2
    num_corners = len(instructions)
    # there must be 4 more turns of one kind than the other (depending on CW or CCW movement)
    # the area missed for an outside corner is 3/4 and the area missed for an inside corner is 1/4,
    # hence (corners - 4) / 2. the last 4 must be outside corners and each miss 3/4, so + 3 at the end
    missed_area += (num_corners - 4) / 2 + 3
    total = abs(total) / 2
    total += missed_area
    print(int(total))

parse()
part1()
part2()



