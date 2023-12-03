symbol_locations = {}
numbers = {}
number_locations = {}

def parse():
    with open("input3") as f:
        for row, line in enumerate(f,0):
            for col, c in enumerate(line,0):
                c = line[col]
                if not c.isdigit() and c != '.' and c != '\n':
                    symbol_locations[(row,col)] = c

    with open("input3") as f:
        for row, line in enumerate(f,0):
            col = 0
            while col < len(line):
                c = line[col]
                if c.isdigit():
                    numstr = ""
                    numidx = col
                    while c.isdigit():
                        numstr += c
                        numidx += 1
                        c = line[numidx]
                    numbers[(row, col)] = (int(numstr), numidx-1)
                    col = numidx
                col += 1

    for (r,begin),(num,end) in numbers.items():
        for c in range(begin,end+1):
            number_locations[(r,c)] = num

    print(symbol_locations)
    print(numbers)
    print(number_locations)


def part1():
    sum = 0
    for (r,begin),(num,end) in numbers.items():
        found = False
        for row in range(r-1,r+2):
            for col in range(begin-1, end+2):
                if symbol_locations.get((row,col),None) is not None:
                    sum += num
                    print(f"symbol found for num {num} at {row},{col}")
                    found = True
                    break
            if found:
                break

    print(sum)

def part2():
    sum = 0
    for (r,c),symbol in symbol_locations.items():
        if symbol == '*':
            number_neighbors = set()
            for dr,dc in [(-1,-1),(-1,0),(-1,1),(0,-1),(0,1),(1,-1),(1,0),(1,1)]:
                num = number_locations.get((r+dr,c+dc),None)
                if num is not None:
                    number_neighbors.add(num)
            number_neighbors = list(number_neighbors)
            if len(list(number_neighbors)) != 2:
                continue
            else:
                sum += number_neighbors[0] * number_neighbors[1]

    print(sum)

parse()
part1()
part2()
