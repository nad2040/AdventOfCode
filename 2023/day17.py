
from heapdict import heapdict
from collections import defaultdict

board = []

def parse():
    global board
    with open("input17") as f:
        for line in f:
            board.append(list(map(lambda x: int(x), list(line.strip()))))
    # print(board)

def trace_path(prev, curr):
    global board
    path = [curr]
    while curr in prev:
        curr = prev[curr]
        path.insert(0,curr)
    total = 0
    print(path[0])
    for v in path[1:]:
        r,c,*_ = v
        total += board[r][c]
        print(v)
    print("total heat:",total)

def a_star(start, goal, h, must_go_straight, must_turn):
    global board
    pq = heapdict()

    prev = dict()

    gScore = defaultdict(lambda:float("inf"))
    gScore[start] = 0

    fScore = defaultdict(lambda:float("inf"))
    fScore[start] = h(start)

    pq[start] = fScore[start]

    while pq:
        curr,_ = pq.popitem()
        # print("current:",curr)
        r,c,dr,dc,dir_count = curr
        if not must_go_straight(dir_count) and (r,c) == goal:
            trace_path(prev, curr)
            return
        prev_dir = (dr,dc)
        backward = (-dr,-dc)
        for dir in [(1,0),(0,1),(-1,0),(0,-1)]:
            dr,dc = dir
            new_r = r + dr
            new_c = c + dc
            if curr != start:
                if dir == backward:
                    continue
                if new_r not in range(len(board)) or new_c not in range(len(board[0])):
                    continue
                if dir != prev_dir and must_go_straight(dir_count) or dir == prev_dir and must_turn(dir_count):
                    continue
            new_dir_count = dir_count + 1 if dir == prev_dir else 1
            neighbor = (new_r,new_c,dr,dc,new_dir_count)
            new_gScore = gScore[curr] + board[new_r][new_c]
            if new_gScore < gScore[neighbor]:
                prev[neighbor] = curr
                gScore[neighbor] = new_gScore
                fScore[neighbor] = new_gScore + h(neighbor)
                pq[neighbor] = fScore[neighbor]

def part1():
    global board
    num_row = len(board)
    num_col = len(board[0])
    goal = (num_row-1,num_col-1)
    print("goal:",goal)
    a_star(
        start=(0,0,0,0,0),
        goal=goal,
        h=lambda _: 0,
        must_go_straight=lambda x: x<0,
        must_turn=lambda x: x == 3
    )

def part2():
    global board
    num_row = len(board)
    num_col = len(board[0])
    goal = (num_row-1,num_col-1)
    print("goal:",goal)
    a_star(
        start=(0,0,0,0,0),
        goal=goal,
        h=lambda _: 0,
        must_go_straight=lambda x: x<4,
        must_turn=lambda x: x == 10
    )

parse()
part1()
part2()
