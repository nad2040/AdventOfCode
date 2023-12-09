
sequences = []

def parse():
    with open("input9") as f:
        for line in f:
            sequences.append(list(map(int, line.split())))

import numpy as np

def all_zero(arr):
    for n in arr:
        if n != 0:
            return False
    return True

def part1():
    total_pred = 0
    for seq in sequences:
        diffs = []
        diffs.append(np.array(seq))
        while not all_zero(diffs[-1]):
            diffs.append(np.diff(diffs[-1]))
        pred = 0
        for diff in diffs:
            pred += diff[-1]
        total_pred += pred
    print(total_pred)



def part2():
    total_pred = 0
    for seq in sequences:
        diffs = []
        diffs.append(np.array(seq))
        while not all_zero(diffs[-1]):
            diffs.append(np.diff(diffs[-1]))
        pred = 0
        for diff in diffs[::-1]:
            pred = diff[0] - pred
        total_pred += pred
    print(total_pred)

parse()
part1()
part2()
