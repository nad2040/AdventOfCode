
stacks = [[],[],[],[],[],[],[],[],[]]
def parse():
    with open("input5.txt") as f:
        for line in f:
            for i in range(9):
                if line[i*4+1] in "123456789":
                    for i in range(9):
                        stacks[i] = stacks[i][::-1]
                    return
                if line[i*4+1] != ' ':
                    stacks[i].append(line[i*4+1])
    

def moveCrate(n,a,b):
    moved = stacks[a][-n:]
    stacks[a] = stacks[a][:-n]
    moved = moved[::-1]
    stacks[b] = stacks[b] + moved

def part1():
    moves = []
    with open("input5.txt") as f:
        moves = f.readlines()[10:]
        for move in moves:
            n = int(move.split()[1])
            a = int(move.split()[3])-1
            b = int(move.split()[5])-1
            moveCrate(n,a,b)
    print(stacks)
    for i in range(9):
        print(stacks[i][-1])

def moveCrate2(n,a,b):
    moved = stacks[a][-n:]
    stacks[a] = stacks[a][:-n]
    stacks[b] = stacks[b] + moved

def part2():
    moves = []
    with open("input5.txt") as f:
        moves = f.readlines()[10:]
        for move in moves:
            n = int(move.split()[1])
            a = int(move.split()[3])-1
            b = int(move.split()[5])-1
            moveCrate2(n,a,b)
    print(stacks)
    for i in range(9):
        print(stacks[i][-1])
 
stacks = [[],[],[],[],[],[],[],[],[]]
parse()
part1()
stacks = [[],[],[],[],[],[],[],[],[]]
parse()
part2()
