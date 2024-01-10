

board = []
size = 0
center = (0,0)

def parse():
    global board, size, center
    with open("input21") as f:
        for line in f:
            board.append(list(line.strip()))
    size = len(board)
    center = (size//2,size//2)

def display(positions):
    global size, board
    for r in range(size):
        for c in range(size):
            if (r,c) in positions:
                print('O',end='')
            else:
                print(board[r][c],end='')
        print()
    print()


def part1():
    global center, size, board
    steps = 64
    pos = set()
    pos.add(center)
    for _ in range(steps):
        new_pos = set()
        for (r,c) in pos:
            for (dr,dc) in [(-1,0),(0,1),(1,0),(0,-1)]:
                if r + dr in range(size) and c + dc in range(size) and board[r + dr][c + dc] != '#':
                    new_pos.add((r+dr,c+dc))
        pos = new_pos
    print(pos)
    display(pos)
    print(len(pos))


def part2():
    pass


parse()
part1()
part2()
