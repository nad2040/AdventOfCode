

def part1():
    visited = {(0,0):True}
    head = (0,0)
    tail = (0,0)
    with open("input9.txt") as f:
        for line in f:
            line = line.split()
            x,y = (1,0) if line[0] == "R" else (0,1) if line[0] == "U" else (-1,0) if line[0] == "L" else (0,-1)
            for i in range(int(line[1])):
                head = (head[0]+x,head[1]+y)
                if tail[0] not in range(head[0]-1,head[0]+2) or tail[1] not in range(head[1]-1,head[1]+2):
                    if tail[0] != head[0] and tail[1] != head[1]:
                        tail = (tail[0] + (1 if tail[0] < head[0] else -1), tail[1] + (1 if tail[1] < head[1] else -1))
                    else:
                        tail = (tail[0]+x,tail[1]+y)
                    visited[tail] = True
    print(len(visited))


def part2():
    visited = {(0,0):True}
    links = [(0,0) for _ in range(10)]
    with open("input9.txt") as f:
        for line in f:
            line = line.split()
            x,y = (1,0) if line[0] == "R" else (0,1) if line[0] == "U" else (-1,0) if line[0] == "L" else (0,-1)
            for i in range(int(line[1])):
                links[0] = (links[0][0]+x,links[0][1]+y)
                for link in range(1,10):
                    if links[link][0] not in range(links[link-1][0]-1,links[link-1][0]+2) or links[link][1] not in range(links[link-1][1]-1,links[link-1][1]+2):
                        if links[link][0] != links[link-1][0] and links[link][1] != links[link-1][1]:
                            links[link] = (links[link][0] + (1 if links[link][0] < links[link-1][0] else -1), links[link][1] + (1 if links[link][1] < links[link-1][1] else -1))
                        elif links[link][0] == links[link-1][0]:
                            links[link] = (links[link][0], links[link][1] + (1 if links[link][1] < links[link-1][1] else -1))
                        else:
                            links[link] = (links[link][0] + (1 if links[link][0] < links[link-1][0] else -1), links[link][1])
                        visited[links[-1]] = True
    print(len(visited))


part1()
part2()
