graph = {}
start = (0,0)

pipechars = []

def parse():
    global start,pipechars
    with open("input10") as f:
        for row, line in enumerate(f):
            pipechars.append(list(line.strip()))
            for col, char in enumerate(line):
                match char:
                    case '|':
                        graph[(row,col)] = [(row-1,col),(row+1,col)]
                    case '-':
                        graph[(row,col)] = [(row,col-1),(row,col+1)]
                    case 'L':
                        graph[(row,col)] = [(row-1,col),(row,col+1)]
                    case 'J':
                        graph[(row,col)] = [(row-1,col),(row,col-1)]
                    case '7':
                        graph[(row,col)] = [(row,col-1),(row+1,col)]
                    case 'F':
                        graph[(row,col)] = [(row,col+1),(row+1,col)]
                    case 'S':
                        start = (row,col)
                    case _:
                        continue

    start_neighbors = []
    start_row,start_col = start
    for dr,dc in [(1,0),(0,1),(-1,0),(0,-1)]:
        neighbor = (start_row+dr, start_col+dc)
        if start in graph.get(neighbor, []):
            start_neighbors.append(neighbor)
    graph[start] = start_neighbors
    # print(graph)
    # print(start)
    # print(graph[start])

    pipechars[start_row][start_col] = \
            '|' if (start_row-1,start_col) in graph and (start_row+1,start_col) in graph else \
            '-' if (start_row,start_col-1) in graph and (start_row,start_col+1) in graph else \
            '7' if (start_row,start_col-1) in graph and (start_row+1,start_col) in graph else \
            'J' if (start_row-1,start_col) in graph and (start_row,start_col-1) in graph else \
            'L' if (start_row-1,start_col) in graph and (start_row,start_col+1) in graph else \
            'F'

    # calc path
    path = set([start])
    s1 = start
    s2 = graph[start][0]
    while s2 != start:
        path.add(s2)
        if graph[s2][0] != s1:
            s1 = s2
            s2 = graph[s2][0]
        else:
            s1 = s2
            s2 = graph[s2][1]

    # prune graph until only loop is involved
    keys_to_del = []
    for state in graph.keys():
        if state not in path:
            keys_to_del.append(state)
    for k in keys_to_del:
        r,c = k
        pipechars[r][c] = '.'
        del graph[k]

    # print('\n'.join([''.join(c for c in row) for row in pipechars]))


def part1():
    global graph
    print(len(graph)//2)


def part2():
    global graph
    count_inside = 0
    is_inside = False
    row = 0
    while row < len(pipechars):
        col = 0
        while col < len(pipechars[0]):
            pipe = pipechars[row][col]
            if pipe == 'F' or pipe == 'L':
                second_pipe = '.'
                while (second_pipe := pipechars[row][col]) != '7' and second_pipe != 'J':
                    col += 1
                # print(pipe, second_pipe)
                if pipe == 'F' and second_pipe == 'J' or pipe == 'L' and second_pipe == '7':
                    is_inside = not is_inside
            elif pipe == '|':
                is_inside = not is_inside
            elif is_inside:
                count_inside += 1
            col += 1
        row += 1
    print(count_inside)

parse()
part1()
part2()
