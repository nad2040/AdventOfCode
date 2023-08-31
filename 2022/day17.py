
'''
List index is from bottom to top

========= 0
|  #### | 1 
|   #   | 2
|  ###  | 3 
|   #   | 4 
|       | 5 
|   .   | 6 
    . 
    .
'''

shapes = {
        "-": [0b0011110],
        "+": [0b0001000,0b0011100,0b0001000],
        "L": [0b0011100,0b0000100,0b0000100],
        "|": [0b0010000,0b0010000,0b0010000,0b0010000],
        "#": [0b0011000,0b0011000]
        }

winds = []

def parse():
    with open("test17.txt") as f:
        for line in f:
            line = line.strip()
            for c in line:
                if c == ">":
                    winds.append(1)
                else:
                    winds.append(-1)

    print(winds)


def part1():
    print()






def part2():
    pass





parse()
part1()
part2()
