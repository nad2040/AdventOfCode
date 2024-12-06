from collections import defaultdict

graph = defaultdict(set)
lists = []
def parse():
    global rows, cols
    with open("input5") as f:
        a = True
        for line in f:
            if line.strip() == "":
                a = not a
                continue
            if a:
                num1,num2 = tuple(map(int, line.strip().split('|')))
                graph[num1].add(num2)
            else:
                lists.append(list(map(int, line.strip().split(','))))

parse()
# print("all lists")
# for l in lists:
#     print(l)

incorrect_lists = []

def part1():
    total = 0
    for l in lists:
        valid = True
        for i in range(len(l)-1):
            if not (set(l[i+1:]) <= graph[l[i]]):
                valid = False
                incorrect_lists.append(l)
                break
        if valid:
            total += l[len(l)//2]

    print(total)

part1()
# print("incorrect lists")
# for l in incorrect_lists:
#     print(l)

from functools import cmp_to_key


def part2():
    total = 0
    for l in incorrect_lists:
        l = sorted(l, key=cmp_to_key(lambda x,y: -1 if y in graph[x] else 1))
        # print(l)
        total += l[len(l)//2]

    print(total)

part2()
