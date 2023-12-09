instruction = ""
mapping = {}
starting_states = set()
ending_states = set()

def parse():
    global instruction
    with open("input8") as f:
        instruction = f.readline().strip()
        f.readline()
        for line in f.readlines():
            line = line.strip().split(" = ")
            left_right = line[1][1:-1].split(", ")
            mapping[line[0]] = (left_right[0],left_right[1])
            if line[0][-1] == 'A':
                starting_states.add(line[0])
            if line[0][-1] == 'Z':
                ending_states.add(line[0])
    print(instruction)
    print(mapping)
    print(starting_states)
    print(ending_states)

def part1():
    state = "AAA"
    global instruction

    i = 0
    found = False
    while not found:
        for instr in instruction:
            if state == "ZZZ":
                print(i)
                found = True
                break
            if instr == 'L':
                state = mapping[state][0]
            else:
                state = mapping[state][1]
            i += 1

import math

def part2():
    states = starting_states
    global instruction

    cycle_lengths = []

    for state in states:
        i = 0
        found = False
        while not found:
            for instr in instruction:
                if state in ending_states:
                    found = True
                    break
                if instr == 'L':
                    state = mapping[state][0]
                else:
                    state = mapping[state][1]
                i += 1
        cycle_lengths.append(i)

    print(math.lcm(*cycle_lengths))

# def part2():
#     states = starting_states
#     global instruction
#
#     i = 0
#     found = False
#     while not found:
#         for instr in instruction:
#             print(states)
#             if states.issubset(ending_states):
#                 print(i)
#                 found = True
#                 break
#             next_states = set()
#             if instr == 'L':
#                 for s in states:
#                     next_states.add(mapping[s][0])
#             else:
#                 for s in states:
#                     next_states.add(mapping[s][1])
#             states = next_states
#             i += 1

parse()
# part1()
part2()
