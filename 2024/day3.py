import re

def part1():
    sum = 0
    with open("input3") as f:
        for line in f:
            line = line.strip()
            matches = re.findall(r"mul\((\d+),(\d+)\)", line)
            for num1,num2 in matches:
                sum += int(num1) * int(num2)

    print(sum)

def part2():
    sum = 0
    do = True
    with open("input3") as f:
        for line in f:
            line = line.strip()
            matches = re.findall(r"mul\((\d+),(\d+)\)|(do)\(\)|(don't)\(\)", line)
            for num1,num2,doo,dont in matches:
                if doo == "do":
                    do = True
                elif dont == "don't":
                    do = False
                elif do:
                    sum += int(num1) * int(num2)

    print(sum)

part1()
part2()
