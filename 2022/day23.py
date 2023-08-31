

elves = {}

def parse():
    global elves
    elves = {}
    with open("test23.txt") as f:
        for row,line in enumerate(f):
            for col,ch in enumerate(line):
                if ch == "#":
                    elves[(row,col)] = (0,0)

    print(elves)


def find_move(directions=["N","S","W","E"]):
    global elves
    moves = {}
    for elf in elves:
        r,c = elf
        move = (0,0)
        neighbor_detected = False
        for dr in range(-1,2):
            for dc in range(-1,2):
                if (dc != 0 or dc != 0) and (r+dr,c+dc) in elves:
                    neighbor_detected = True
        if neighbor_detected:
            for dir in directions:
                if dir == "N":
                    if (r-1,c-1) not in elves and (r-1,c) not in elves and (r-1,c+1) not in elves:
                        move = (-1,0)
                        break
                if dir == "S":
                    if (r+1,c-1) not in elves and (r+1,c) not in elves and (r+1,c+1) not in elves:
                        move = (1,0)
                        break
                if dir == "W":
                    if (r-1,c-1) not in elves and (r,c-1) not in elves and (r+1,c-1) not in elves:
                        move = (0,-1)
                        break
                if dir == "E":
                    if (r-1,c+1) not in elves and (r,c+1) not in elves and (r+1,c+1) not in elves:
                        move = (0,1)
                        break
        else:
            move = (0,0)

        print(elf,move,end="")
        new_pos = (r+move[0],c+move[1])
        if new_pos in moves:
            moves[new_pos].append(elf)
        else:
            moves[new_pos] = [elf]

    for pos in moves:
        if len(moves[pos]) > 1:
            for elf in moves[pos]:
                elves[elf] = elf
        else:
            elves[moves[pos][0]] = pos

def move():
    global elves
    new_elves = {}
    for elf in elves:
        new_pos = elves[elf]
        new_elves[new_pos] = (0,0)
    elves = new_elves

def printboard():
    for row in range(-2,10):
        for col in range(-3,11):
            if (row,col) in elves:
                print("#",end="")
            else:
                print(".",end="")
        print()
    print()


def simulate(rounds=10):
    directions = ["N","S","W","E"]
    for _ in range(rounds):
        printboard()
        find_move(directions)
        print(elves)
        move()
        directions.append(directions.pop(0))

def part1():
    simulate()
    min_r,min_c,max_r,max_c = float("inf"),float("inf"),float("-inf"),float("-inf")
    count = 0
    for elf in elves:
        r,c = elf
        min_r = min(min_r,r)
        max_r = max(max_r,r)
        min_c = min(min_c,c)
        max_c = max(max_c,c)
        count += 1

    print(count)
    print(min_r,max_r)
    print(min_c,max_c)

    print((max_r - min_r + 1)*(max_c - min_c + 1) - count)



parse()
part1()


