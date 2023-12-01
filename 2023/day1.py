def part1():
    sum = 0
    with open("input1.txt") as f:
        for line in f:
            num = ""
            for c in line:
                if c.isdigit():
                    num += c
                    break
            for c in line[::-1]:
                if c.isdigit():
                    num += c
                    break
            sum += int(num)

    print(sum)

import re

def part2():
    digit_dict = {"one": 1,"two": 2,"three": 3, "four":4, "five":5,"six":6,"seven":7,"eight":8,"nine":9}
    sum = 0
    with open("input1.txt") as f:
        for line in f:
            num = 0
            line = line.strip()
            matches = re.findall(r"(?=(one|two|three|four|five|six|seven|eight|nine|\d))", line)
            try:
                num += int(matches[0])
            except Exception:
                num += digit_dict[matches[0]]
            num *= 10
            try:
                num += int(matches[-1])
            except Exception:
                num += digit_dict[matches[-1]]
            sum += num

    print(sum)

part1()
part2()
