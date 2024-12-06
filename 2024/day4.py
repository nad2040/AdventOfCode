graph = {}
rows = 0
cols = 0
def parse():
    global rows, cols
    with open("input4") as f:
        for r,line in enumerate(f.readlines()):
            for c,letter in enumerate(line.strip()):
                graph[r,c] = letter
                cols = max(cols,c)
            rows = max(rows,r)

parse()

def part1():
    total = 0
    for r in range(rows+1):
        for c in range(cols+1):
            strs = []
            strs.append("".join(list(map(lambda p: graph.get(p,' '), [(r,c),(r-1,c-1),(r-2,c-2),(r-3,c-3)]))))
            strs.append("".join(list(map(lambda p: graph.get(p,' '), [(r,c),(r-1,c),(r-2,c),(r-3,c)]))))
            strs.append("".join(list(map(lambda p: graph.get(p,' '), [(r,c),(r-1,c+1),(r-2,c+2),(r-3,c+3)]))))
            strs.append("".join(list(map(lambda p: graph.get(p,' '), [(r,c),(r,c+1),(r,c+2),(r,c+3)]))))
            strs.append("".join(list(map(lambda p: graph.get(p,' '), [(r,c),(r+1,c+1),(r+2,c+2),(r+3,c+3)]))))
            strs.append("".join(list(map(lambda p: graph.get(p,' '), [(r,c),(r+1,c),(r+2,c),(r+3,c)]))))
            strs.append("".join(list(map(lambda p: graph.get(p,' '), [(r,c),(r+1,c-1),(r+2,c-2),(r+3,c-3)]))))
            strs.append("".join(list(map(lambda p: graph.get(p,' '), [(r,c),(r,c-1),(r,c-2),(r,c-3)]))))
            total += sum(1 for s in strs if s == "XMAS")
    print(total)

part1()


def part2():
    total = 0
    for r in range(rows+1):
        for c in range(cols+1):
            strs = []
            strs.append("".join(list(map(lambda p: graph.get(p,' '), [(r-1,c-1),(r,c),(r+1,c+1)]))))
            strs.append("".join(list(map(lambda p: graph.get(p,' '), [(r+1,c-1),(r,c),(r-1,c+1)]))))
            if sum(1 for s in strs if s == "MAS" or s == "SAM") == 2:
                total += 1
    print(total)

part2()
