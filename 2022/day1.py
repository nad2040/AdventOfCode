def part1():
    elves = []
    with open("input1.txt") as f:
        sum = 0
        for line in f:
            line = line.strip()
            if len(line) == 0:
                elves.append(sum)
                sum = 0
                continue
            sum += int(line)
    print(max(elves))

def part2():
    elves = []
    with open("input1.txt") as f:
        sum = 0
        for line in f:
            line = line.strip()
            if len(line) == 0:
                elves.append(sum)
                sum = 0
                continue
            sum += int(line)
    
    elves.sort(reverse=True)
    print(elves[0]+elves[1]+elves[2])

part1()
part2()
